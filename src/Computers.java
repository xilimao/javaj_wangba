import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class Computers extends Cards {
	
	public void comid(int sta){
		PreparedStatement psm = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		if(conn != null){
			try {
				String sql="select id from computers where onUse = ?";
				psm = conn.prepareStatement(sql);
				psm.setInt(1, sta);
				rs = psm.executeQuery();
				while (rs.next()){
					sb.append(rs.getString("id")+"\r\n");
				}
				JOptionPane.showMessageDialog(null, sb, "可用机子id",JOptionPane.PLAIN_MESSAGE);
			
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
