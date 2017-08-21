import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class zhucejm extends JFrame{
	JTextField una = new JTextField(10);
	JPasswordField upwd1 = new JPasswordField(10);
	JPasswordField upwd2 = new JPasswordField(10);
	JTextField money = new JTextField(10);
	JTextField idcard = new JTextField(10);
	
	PoliceListen listener;
	Box baseBox,boxV1,boxV2;
	JButton btn1 = new JButton("确认");
	public zhucejm(){
		
		this.setBounds(300,200,310,260);
		this.setTitle("注册");
		this.setLayout(new java.awt.FlowLayout());
		init();
		this.setVisible(true);	
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);//只关闭当前窗口
	}
	void init(){
		
		listener = new PoliceListen();
		btn1.addActionListener(listener);//给按钮加监听
		
		
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("用户名："));
		boxV1.add(Box.createVerticalStrut(8));
		
		boxV1.add(new JLabel("密码："));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("确认密码："));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("充值金额："));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("你的卡号为："));
		boxV1.add(Box.createVerticalStrut(8));
		
		boxV2 = Box.createVerticalBox();
		boxV2.add(una);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(upwd1);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(upwd2);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(money);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(idcard);
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
			if (e.getSource() == btn1){
				String mo = null;
				String s1 = una.getText();
				String pw1 = upwd1.getText();
				String pw2 = upwd2.getText();
				
				mo = money.getText();
				System.out.println(mo);
				Cards ca = new Cards();
				Connection con = ca.getConn();
				
				if(pw1.equals(pw2)){
					idcard.setText(ca.zhuce(s1, pw1, mo));
				}else{
					JOptionPane.showMessageDialog(null, "请输入相通的密码", "错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
