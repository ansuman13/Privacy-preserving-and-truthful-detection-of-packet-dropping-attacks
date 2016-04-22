package com.light.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.light.bo.NodeDetails;
import com.light.service.Receiver;
import com.light.service.ServiceReceiver;
import com.light.service.WirelessReceiver;
import com.light.service.WirelessSender;
import com.light.utility.StaticInfo;

public class Service implements ActionListener {

	
	public JFrame mainform;
	
	public JPanel mainpanel;

	public JPanel leftPanel;
	
	
	//left panel
	
	public JLabel neighLabel,nameLabel,distanceLabel,reqLabel;
	
	public JButton exit;
	
	public static JList neighList;
	
	public JTable reqTable;
	
	public static DefaultTableModel reqModel;
	
	public static JScrollPane neighScroll,reqScroll;
	
	//variables
	
	public String nodeName;
	
	public int portNo,distance;
	
	
	static {
		try {
			SubstanceLookAndFeel
					.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBinaryWatermark");
			SubstanceLookAndFeel
					.setCurrentTheme("org.jvnet.substance.theme.SubstanceInvertedTheme");
			SubstanceLookAndFeel
					.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
			SubstanceLookAndFeel
					.setCurrentButtonShaper("org.jvnet.substance.button.ClassicButtonShaper");
			UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Service() {
		Random r = new Random();
		distance = Integer.parseInt(JOptionPane.showInputDialog("Distance"));
		nodeName = "S" + r.nextInt(10) + "" + r.nextInt(10) + ""+ r.nextInt(10) + "" + r.nextInt(10);
		portNo = Integer.parseInt(r.nextInt(10) + "" + r.nextInt(10) + ""+ r.nextInt(10) + "" + r.nextInt(10));
		
		try{
				NodeDetails node=new NodeDetails();
				node.distance=distance;
				node.port=portNo;
				node.name=nodeName;
				node.address=""+InetAddress.getLocalHost().getHostName();
				StaticInfo.myInfo=node;
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		new ServiceReceiver(portNo);
		new WirelessReceiver(nodeName, distance,"Service");
		new WirelessSender(nodeName, portNo, distance);
	
	}
	
	public void UI(){
		mainform=new JFrame("SNode - "+this.nodeName);
		
		mainpanel=new JPanel();
		
		mainpanel.setLayout(new GridLayout(1,1));
		mainpanel.add(left());
		
		mainform.add(mainpanel);
		mainform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainform.setResizable(false);
		mainform.setSize(450,650);
		mainform.setVisible(true);
	}
	
	public JPanel left(){
		leftPanel=new JPanel();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		leftPanel.setLayout(null);
		
		nameLabel=new JLabel("SNode Name : "+this.nodeName);
		nameLabel.setBounds(20,30,150,30);
		leftPanel.add(nameLabel);
		
		distanceLabel=new JLabel("Distance : "+this.distance);
		distanceLabel.setBounds(180,30,150,30);
		leftPanel.add(distanceLabel);
		
		exit=new JButton("Exit");
		exit.setBounds(310,580,100,30);
		leftPanel.add(exit);
		
		neighLabel=new JLabel("Neighbour Nodes");
		neighLabel.setBounds(20,80,150,30);
		leftPanel.add(neighLabel);
		
		neighList=new JList();
		neighScroll=new JScrollPane(neighList);
		neighScroll.setBounds(20,110,390,200);
		leftPanel.add(neighScroll);
		
		reqLabel=new JLabel("Request Information");
		reqLabel.setBounds(20,330,100,30);
		leftPanel.add(reqLabel);
		
		reqModel=new DefaultTableModel();
		reqTable=new JTable(reqModel);
		reqModel.addColumn("Request Node");
		reqModel.addColumn("Query");
		reqModel.addColumn("Security Type");
		reqScroll=new JScrollPane(reqTable);
		reqScroll.setBounds(20,360,390,200);
		leftPanel.add(reqScroll);
		
		
		//listener
		exit.addActionListener(this);
		
		return(leftPanel);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Service().UI();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		
		if(obj==exit){
			System.exit(0);
		}
		
	}

}
