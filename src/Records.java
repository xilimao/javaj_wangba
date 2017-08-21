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
				
				//�ж��ʺ�������ʺ�״̬
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
				//������ȷ
				if(i==1 && fee>0){  
					
					//���˻���Ϊ������stateΪ0
					String sql3="UPDATE CARDS SET STATE = 0 where ID = ?";
					psm = conn.prepareStatement(sql3);
					psm.setString(1, caid);
					int n3 = psm.executeUpdate();
					
					//�����ϻ���Ϣ
					String sql2="insert into Records(cardid,comid) values(?,?)";
					psm = conn.prepareStatement(sql2);
					psm.setString(1, caid);
					psm.setString(2, jiid);
					boolean n2 = psm.execute();
					
					if(n2 = true){ //����ɹ�
							JOptionPane.showMessageDialog(null, "��ʼ��ʱ", "��ʱ",JOptionPane.PLAIN_MESSAGE);
							//��������Ϊ��ǰ�����ã�״ֵ̬Ϊ0
							String sql4="UPDATE COMPUTERS SET onUse = 0,NOTE ='��ǰ������' where ID = ?";
							psm = conn.prepareStatement(sql4);
							psm.setString(1, jiid);
							int n4 = psm.executeUpdate();
						}else{//����ʧ��
							JOptionPane.showMessageDialog(null, "�ϻ�ʧ��", "����",JOptionPane.ERROR_MESSAGE);
						}	
				}else{
					
					JOptionPane.showMessageDialog(null, "���Ż������������ߵ�ǰ������ʹ��", "����",JOptionPane.ERROR_MESSAGE);
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
		double fee=0.000;//��������
		if(conn != null){
			int reid = 0;//�����˻�id
			try {
				String sql1="select DISTINCT r.id FROM RECORDS r,COMPUTERS c " +
						"WHERE r.COMID= ? AND c.ONUSE = 0 AND r.fee = 0";//��ѯδ�»��ļ�¼
				psm = conn.prepareStatement(sql1);
				psm.setString(1, jzid);
				rs = psm.executeQuery();
				while (rs.next()){
					reid = rs.getInt("id");//����ѯ�������Ϊreid
				}
				//reid = 0,����û�в�ѯ�� feeΪ0 (��û�»���),�����ǰ�˵����Ѳ�Ϊ0���������˵��ѹ��ڣ�������ʾ����
				if(reid == 0){		
						JOptionPane.showMessageDialog(null, "��ǰ���»��������ظ�����", "����",JOptionPane.ERROR_MESSAGE);
					}
				//��ǰδ�»�����������
				else{			
					String sql2="UPDATE RECORDS SET ENDTIME = SYSDATE " +
					"WHERE id = ?";	//�����»�ʱ��
					String sql3="UPDATE COMPUTERS SET onUse = 1,NOTE ='��ǰ����' where ID = ?";//���»��ӿ���
					String sqll="select (ENDTIME - BEGINTIME)*24*5 as a from RECORDS where id = ?";//���㻨��
					String sql6="select yue from  CARDS where id = (select cardid from records where id = ?)";//�������
					String sql5="UPDATE RECORDS SET fee=((ENDTIME - BEGINTIME)*24*5) where id = ?";//���»���
					
					//�����»�ʱ��
					psm =  conn.prepareStatement(sql2);
					psm.setInt(1, reid);
					int u2 = psm.executeUpdate();
					
					//���㻨��
					psm =  conn.prepareStatement(sqll);
					psm.setInt(1, reid);
					ResultSet ul = psm.executeQuery();
					while(ul.next()){
						fee = ul.getDouble(1);
						System.out.println(fee);
					}
					
					//�������
					psm =  conn.prepareStatement(sql6);
					psm.setInt(1, reid);
					u6 = psm.executeQuery();
					while(u6.next()){
						yue = u6.getDouble("yue");//��ȡ���
						System.out.println(yue);
					}
					if(yue>=fee){//�ж�����Ƿ���ڻ��ѣ��������
						
						psm =  conn.prepareStatement(sql5);
						psm.setInt(1, reid);
						int u5 = psm.executeUpdate();//���»���
						
						String sql4="UPDATE CARDS SET STATE = 1 , YUE = YUE - ? where ID = " +
								"(select cardid from Records where id =?)";//�����˻����ã��������
						psm =  conn.prepareStatement(sql4);
						psm.setDouble(1, fee);
						psm.setInt(2, reid);
						//stmt.clearParameters();//�������
						u4 = psm.executeUpdate();
						
						
	
						psm =  conn.prepareStatement(sql3);
						psm.setString(1, jzid);
						int u3 = psm.executeUpdate();//���»��ӿ���
						
						double time = fee/5.0;//������ʱ
						sb.append("������ʱ��:"+time+"Сʱ,���ѽ�Ǯ��"+fee);
						JOptionPane.showMessageDialog(null, sb, "�»��ɹ�",JOptionPane.PLAIN_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, sb, "���㣬���ֵ���ڽ����»�",JOptionPane.PLAIN_MESSAGE);
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
			sb.append("���� ��     �ϻ�ʱ�䣺                      �»�ʱ�䣺                        ���ã�                                 \r\n");
			
			try {
				//��ѯ��Ϣ
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
				//ͨ��StringBUffer ���
				JOptionPane.showMessageDialog(null, sb, "��ʷ��¼",JOptionPane.PLAIN_MESSAGE);
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
