import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Cards {
	Connection conn = null;	//��������
	String id=null;
	String una = null;
	String upw = null;
	static String url = "jdbc:oracle:thin:@localhost:1521:ORCL";//����oracle��url
	static String user="cajava";		//�˻���
	String psw ="123456";	//�˻�����

	public Connection getConn(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //����������Ŀ¼��
			conn = DriverManager.getConnection(url,user,psw);//ͨ�������������
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public String zhuce(String na,String pw,String mo){
		String caid = null;
		int i =0;
		PreparedStatement ps = null;
		PreparedStatement psm = null;
		PreparedStatement psm1 = null;
		ResultSet nn = null;
		if(conn != null){
			try {
				//�ж��Ƿ�����
				String sq3="select id from Cards where name = ?";
				ps = conn.prepareStatement(sq3);
				ps.setString(1, na);
				nn = ps.executeQuery();
				while(nn.next()){
					i++;
				}
				if(i>=1){//���>=1��˵����ѯ��������һ���ģ����ظ�
					JOptionPane.showMessageDialog(null, "�����ظ�!", "����",JOptionPane.ERROR_MESSAGE);
				}
				else{
					
					String sql="INSERT INTO cards (name,pwd,yue) VALUES(?,?,?)";
					String sql2="select id from Cards where name=? and pwd =? ";
					
					psm =  conn.prepareStatement(sql);
					psm.setString(1, na);
					psm.setString(2, pw);
					psm.setString(3, mo);
					boolean n = psm.execute();
				
					if(n = true){
						//�����˻�����id��������Ŀ��ţ���Ϊ������������Ҫ���ҳ���
					    psm1 =  conn.prepareStatement(sql2);
						psm1.setString(1, na);
						psm1.setString(2, pw);
						ResultSet cid = psm1.executeQuery();
						while(cid.next()){
							 caid  = cid.getString("id");
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "ע��ʧ��!", "����",JOptionPane.ERROR_MESSAGE);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					nn.close();
					ps.close();
					psm.close();
					psm1.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return caid;
	}
	public void chongzhi(String jg,String id,String pw){
		PreparedStatement psm = null;
		if(conn != null){
			String sql = "update Cards set yue = yue + ? where id = ? and pwd = ?";
			try {
				psm = conn.prepareStatement(sql);
				psm.setString(1, jg);
				psm.setString(2, id);
				psm.setString(3, pw);
				int s = psm.executeUpdate();
				if(s>0){
					JOptionPane.showMessageDialog(null, "��ֵ�ɹ�!", "suess",JOptionPane.PLAIN_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "��ֵʧ��!", "����",JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
