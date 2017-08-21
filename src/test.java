import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class test extends JFrame{
	JButton btn1 = null;
	JButton btn2 = null;
	JButton btn3 = null;
	JButton btn4 = null;
	JButton btn5 = null;
	Box boxV1;
	PoliceListen listener1,listener2,listener3,listener4,listener5;
	
	public test(){
		FlowLayout flow = new FlowLayout();
		this.setTitle("希狸网咖");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setBounds(100,100,300,300);  //前两个代表左上角坐标,后俩是宽度和高度
		//this.setResizable(false);//窗体大小不可变
		//this.setLayout(flow);
		this.setLayout(new java.awt.FlowLayout());
		menu();
		//this.pack();
		this.setVisible(true);   //显示一下JFream
	}
	
	public void menu(){
		
		
		btn1 = new JButton("1.上机操作");
		//btn1.setPreferredSize(new Dimension(200,50));
		listener1 = new PoliceListen();
		btn1.addActionListener(listener1);//给按钮加监听
		
		
		btn2 = new JButton("2.下机操作");
		//btn2.setPreferredSize(new Dimension(200,50));
		listener2 = new PoliceListen();
		btn2.addActionListener(listener2);//给按钮加监听
		
		btn3 = new JButton("3.注册新会员");
		//btn3.setPreferredSize(new Dimension(200,50));
		listener3 = new PoliceListen();
		btn3.addActionListener(listener3);//给按钮加监听
		
		btn4 = new JButton("4.查看历史纪录");
		//btn4.setPreferredSize(new Dimension(200,50));
		listener4 = new PoliceListen();
		btn4.addActionListener(listener4);//给按钮加监听
		
		btn5 = new JButton("5.退出");
		//btn5.setPreferredSize(new Dimension(200,50));
		listener5 = new PoliceListen();
		btn5.addActionListener(listener5);//给按钮加监听
		
		boxV1 = Box.createVerticalBox();
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(btn1);
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(btn2);
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(btn3);
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(btn4);
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(btn5);
		add(boxV1);

	}
	public class PoliceListen implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == btn1){
				shangjijm sjj = new shangjijm();
			}
			else if (e.getSource() == btn2){
				xiajijm xjj = new xiajijm();
			}
			else if (e.getSource() == btn3){
				zhucejm zj = new zhucejm();
			}
			else if (e.getSource() == btn4){
				hisjilujm his = new hisjilujm();
			}
			else if(e.getSource() == btn5){
				System.exit(0);
			}
		}
	}
	public static void main(String[] args) {
		test t1 = new test();
	}
}
