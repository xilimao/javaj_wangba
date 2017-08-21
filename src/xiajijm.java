import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class xiajijm extends JFrame{
	JTextField jzid = new JTextField(10);//机子号码
	JTextField xjdate = new JTextField(10);//下机时间
	
	PoliceListen listener;
	Box baseBox,boxV1,boxV2;
	JButton btn1 = new JButton("确认");
	
	public xiajijm(){
		this.setBounds(300,200,310,260);
		this.setTitle("下机系统");
		this.setLayout(new java.awt.FlowLayout());
		init();
		this.setVisible(true);	
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);//只关闭当前窗口
	}
	
void init(){
		listener = new PoliceListen();
		btn1.addActionListener(listener);//给按钮加监听
		
		
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("机器号码："));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("结束时间："));
		boxV1.add(Box.createVerticalStrut(8));
		
		boxV2 = Box.createVerticalBox();
		boxV2.add(jzid);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(xjdate);
		boxV2.add(Box.createVerticalStrut(8));


		baseBox = Box.createHorizontalBox();
		baseBox.add(boxV1);
		boxV2.add(Box.createVerticalStrut(10));
		baseBox.add(boxV2);
		add(baseBox);
		add(btn1);
	}
	public class PoliceListen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==btn1){
				Records res = new Records();//实例化对象 records
				Connection con = res.getConn();//获取连接数据库
				
				String jm = jzid.getText();//获取输出的
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String time = df.format(new Date());
				xjdate.setText(time);  //设置为当前时间
				
				res.xiaji(jm);
			}
		}
		
	}
}
