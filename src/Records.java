import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.ldap.Rdn;
import javax.swing.JOptionPane;


public class Records extends Cards{
	public void shangji(String caid,String jiid,String pwd){
		PreparedStatement psm = null;
		ResultSet rs  = null;
		double fee = 0;
		if(conn != null){
			try {
				int i =0;
				
				//判断帐号密码和帐号状态
				String sql="select yue from Cards where id= ? and pwd = ? and STATE = 1";
				psm = conn.prepareStatement(sql);
				psm.setString(1, caid);
				psm.setString(2, pwd);
				rs = psm.executeQuery();
				while (rs.next()){
					i=i+1;
					fee = rs.getDouble(1);
					System.out.println(fee);
				}
				//若都正确
				if(i==1 && fee>0){  
					
					//将账户设为不可用state为0
					String sql3="UPDATE CARDS SET STATE = 0 where ID = ?";
					psm = conn.prepareStatement(sql3);
					psm.setString(1, caid);
					int n3 = psm.executeUpdate();
					
					//导入上机信息
					String sql2="insert into Records(cardid,comid) values(?,?)";
					psm = conn.prepareStatement(sql2);
					psm.setString(1, caid);
					psm.setString(2, jiid);
					boolean n2 = psm.execute();
					
					if(n2 = true){ //倒入成功
							JOptionPane.showMessageDialog(null, "开始计时", "计时",JOptionPane.PLAIN_MESSAGE);
							//将机子设为当前不可用，状态值为0
							String sql4="UPDATE COMPUTERS SET onUse = 0,NOTE ='当前不可用' where ID = ?";
							psm = conn.prepareStatement(sql4);
							psm.setString(1, jiid);
							int n4 = psm.executeUpdate();
						}else{//导入失败
							JOptionPane.showMessageDialog(null, "上机失败", "错误",JOptionPane.ERROR_MESSAGE);
						}	
				}else{
					
					JOptionPane.showMessageDialog(null, "卡号或者密码错误或者当前卡正在使用", "错误",JOptionPane.ERROR_MESSAGE);
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					rs.close();
					psm.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	} 
	public void xiaji(String jzid){
		StringBuffer sb =new StringBuffer();
		PreparedStatement psm = null;
		ResultSet rs = null;
		ResultSet u6 = null;
		int u4 = 0;
		double yue = 0.000;
		double fee=0.000;//声明花费
		if(conn != null){
			int reid = 0;//声明账户id
			try {
				String sql1="select DISTINCT r.id FROM RECORDS r,COMPUTERS c " +
						"WHERE r.COMID= ? AND c.ONUSE = 0 AND r.fee = 0";//查询未下机的记录
				psm = conn.prepareStatement(sql1);
				psm.setString(1, jzid);
				rs = psm.executeQuery();
				while (rs.next()){
					reid = rs.getInt("id");//将查询结果设置为reid
				}
				//reid = 0,代表没有查询到 fee为0 (还没下机的),如果当前账单花费不为0，则代表该账单已过期，所以提示报错
				if(reid == 0){		
						JOptionPane.showMessageDialog(null, "当前已下机，请勿重复操作", "操作",JOptionPane.ERROR_MESSAGE);
					}
				//当前未下机，继续操作
				else{			
					String sql2="UPDATE RECORDS SET ENDTIME = SYSDATE " +
					"WHERE id = ?";	//更新下机时间
					String sql3="UPDATE COMPUTERS SET onUse = 1,NOTE ='当前可用' where ID = ?";//更新机子可用
					String sqll="select (ENDTIME - BEGINTIME)*24*5 as a from RECORDS where id = ?";//计算花费
					String sql6="select yue from  CARDS where id = (select cardid from records where id = ?)";//查找余额
					String sql5="UPDATE RECORDS SET fee=((ENDTIME - BEGINTIME)*24*5) where id = ?";//更新花费
					
					//更新下机时间
					psm =  conn.prepareStatement(sql2);
					psm.setInt(1, reid);
					int u2 = psm.executeUpdate();
					
					//计算花费
					psm =  conn.prepareStatement(sqll);
					psm.setInt(1, reid);
					ResultSet ul = psm.executeQuery();
					while(ul.next()){
						fee = ul.getDouble(1);
						System.out.println(fee);
					}
					
					//查找余额
					psm =  conn.prepareStatement(sql6);
					psm.setInt(1, reid);
					u6 = psm.executeQuery();
					while(u6.next()){
						yue = u6.getDouble("yue");//提取余额
						System.out.println(yue);
					}
					if(yue>=fee){//判断余额是否大于花费，如果大于
						
						psm =  conn.prepareStatement(sql5);
						psm.setInt(1, reid);
						int u5 = psm.executeUpdate();//更新花费
						
						String sql4="UPDATE CARDS SET STATE = 1 , YUE = YUE - ? where ID = " +
								"(select cardid from Records where id =?)";//更新账户可用，更新余额
						psm =  conn.prepareStatement(sql4);
						psm.setDouble(1, fee);
						psm.setInt(2, reid);
						//stmt.clearParameters();//清除参数
						u4 = psm.executeUpdate();
						
						
	
						psm =  conn.prepareStatement(sql3);
						psm.setString(1, jzid);
						int u3 = psm.executeUpdate();//更新机子可用
						
						double time = fee/5.0;//计算用时
						sb.append("共花费时间:"+time+"小时,花费金钱："+fee);
						JOptionPane.showMessageDialog(null, sb, "下机成功",JOptionPane.PLAIN_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, sb, "余额不足，请充值后在进行下机",JOptionPane.PLAIN_MESSAGE);
						shangjijm sj = new shangjijm();
					}
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					u6.close();
					rs.close();
					psm.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void history(String caid){
		StringBuffer sb =new StringBuffer();
		PreparedStatement psm = null;
		ResultSet rs = null;
		if(conn != null){
			sb.append("卡号 ：     上机时间：                      下机时间：                        费用：                                 \r\n");
			
			try {
				//查询信息
				String sql1="select cardid,beginTime,endTime,fee from records where cardid = ? ";
				psm =  conn.prepareStatement(sql1);
				psm.setString(1,caid);
				rs = psm.executeQuery();
				while(rs.next()){
					sb.append(rs.getInt("cardid")+"    ");
					sb.append(rs.getString("beginTime")+"    ");
					sb.append(rs.getString("endTime")+"  ");
					sb.append(rs.getInt("fee")+"  ");
					sb.append(" \r\n ");
				}
				//通过StringBUffer 输出
				JOptionPane.showMessageDialog(null, sb, "历史记录",JOptionPane.PLAIN_MESSAGE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					rs.close();
					psm.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
