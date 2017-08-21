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
	JTextField jzid = new JTextField(10);//���Ӻ���
	JTextField xjdate = new JTextField(10);//�»�ʱ��
	
	PoliceListen listener;
	Box baseBox,boxV1,boxV2;
	JButton btn1 = new JButton("ȷ��");
	
	public xiajijm(){
		this.setBounds(300,200,310,260);
		this.setTitle("�»�ϵͳ");
		this.setLayout(new java.awt.FlowLayout());
		init();
		this.setVisible(true);	
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);//ֻ�رյ�ǰ����
	}
	
void init(){
		listener = new PoliceListen();
		btn1.addActionListener(listener);//����ť�Ӽ���
		
		
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("�������룺"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("����ʱ�䣺"));
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
				Records res = new Records();//ʵ�������� records
				Connection con = res.getConn();//��ȡ�������ݿ�
				
				String jm = jzid.getText();//��ȡ�����
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
				String time = df.format(new Date());
				xjdate.setText(time);  //����Ϊ��ǰʱ��
				
				res.xiaji(jm);
			}
		}
		
	}
}
