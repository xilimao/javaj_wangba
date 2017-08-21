import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class shangjijm extends JFrame {
	
	JTextField jzid = new JTextField(10);//���Ӻ���
	JTextField caid = new JTextField(10);//�û�id
	JPasswordField upwd = new JPasswordField(10);//����
	JTextField sjdate = new JTextField(10);//�ϻ�ʱ��
	JTextField chongzhi = new JTextField(10);//��ֵ���
	
	PoliceListen listener,listener2,listener3;
	Box baseBox,boxV1,boxV2;
	JButton btn1 = new JButton("ȷ���ϻ�");
	JButton btn2 = new JButton("��ѯ��ǰ��Щ���ӿ���");
	JButton btn3 = new JButton("��ֵ");
	
	public shangjijm(){
		this.setBounds(300,200,310,300);
		this.setTitle("�ϻ�ϵͳ");
		this.setLayout(new java.awt.FlowLayout());
		init();
		this.setVisible(true);	
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);//ֻ�رյ�ǰ����
	}
	
void init(){
	
		listener = new PoliceListen();
		btn1.addActionListener(listener);//����ť�Ӽ���
		listener2 = new PoliceListen();
		btn2.addActionListener(listener2);//����ť�Ӽ���
		listener3 = new PoliceListen();
		btn3.addActionListener(listener3);//����ť�Ӽ���
		
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("���ţ�"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("���Ӻ��룺"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("���룺"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("��ʼʱ�䣺"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("�����ֵ�����룺"));
		boxV1.add(Box.createVerticalStrut(8));
		
		boxV2 = Box.createVerticalBox();
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(jzid);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(caid);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(upwd);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(sjdate);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(chongzhi);
		boxV2.add(Box.createVerticalStrut(8));

		baseBox = Box.createHorizontalBox();
		baseBox.add(boxV1);
		boxV2.add(Box.createVerticalStrut(10));
		baseBox.add(boxV2);
		
		add(btn2);
		baseBox.add(Box.createVerticalStrut(8));
		add(baseBox);
		add(btn1);
		add(btn3);
	}

	public class PoliceListen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==btn2){
				Computers com = new Computers();
				Connection con = com.getConn();
				com.comid(1);
			}
			else if(e.getSource()==btn1){
				Records res = new Records();
				Connection con = res.getConn();
				
				String ji = jzid.getText();
				String ca = caid.getText();
				String pw = upwd.getText();
				
				res.shangji(ji, ca, pw);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
				String time = df.format(new Date());
				sjdate.setText(time);
			}
			else if(e.getSource()==btn3){
				Cards ca = new Cards();
				Connection con = ca.getConn();
				String ji = jzid.getText();
				String pw = upwd.getText();
				
				String czje = chongzhi.getText();
				
				System.out.println(czje);
				ca.chongzhi(czje,ji,pw);
			}
		
		}
		
	}
}

