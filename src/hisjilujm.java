import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class hisjilujm extends JFrame{
	
	JTextField cxkh = new JTextField(10);//Ҫ��ѯ�Ŀ���
	JLabel xsjg = new JLabel();//��ʾ�����ǩ
	PoliceListen listener;
	Box baseBox,boxV1,boxV2;
	JButton btn1 = new JButton("��ѯ");
	
	public hisjilujm(){
		this.setBounds(100,100,310,260);
		this.setTitle("��ʷ��¼");
		this.setLayout(new java.awt.FlowLayout());
		init();
		this.setVisible(true);	
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);//ֻ�رյ�ǰ����
	}
	
	void init(){
		listener = new PoliceListen();
		btn1.addActionListener(listener);//����ť�Ӽ���
		
		
		boxV1 = Box.createVerticalBox();
		boxV1.add(new JLabel("������Ҫ��ѯ�Ŀ��ţ�"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("��ѯ������£�"));
		boxV1.add(Box.createVerticalStrut(8));
		
		boxV2 = Box.createVerticalBox();
		boxV2.add(cxkh);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(btn1);
		
		baseBox = Box.createHorizontalBox();
		baseBox.add(boxV1);
		boxV2.add(Box.createVerticalStrut(10));
		baseBox.add(boxV2);
		add(baseBox);
		add(xsjg);
		
		
	}
	public class PoliceListen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==btn1){
				String cadid = cxkh.getText();
				Records res = new Records();
				Connection con = res.getConn();
				StringBuffer sb = new StringBuffer();
				res.history(cadid);
			}
		}
		}
	}

	
