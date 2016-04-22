package com.light.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jvnet.substance.SubstanceLookAndFeel;

import com.light.algorithm.CryptoUtils;
import com.light.algorithm.Encryption;
import com.light.algorithm.RSAKeyPair;
import com.light.bo.ADR;
import com.light.bo.Content;
import com.light.bo.NodeDetails;
import com.light.bo.Packet;
import com.light.service.ImageTool;
import com.light.service.Receiver;
import com.light.service.Sender;
import com.light.service.WirelessReceiver;
import com.light.service.WirelessSender;
import com.light.utility.StaticInfo;

public class Node implements ActionListener{

	
	public JFrame mainform;
	
	public JPanel mainpanel;

	public JPanel leftPanel,rightPanel;
	
	//left panel
	
	public JLabel neighLabel,nameLabel,distanceLabel,destinationLabel,contentLabel,langLabel,graphLabel,titleLabel;
	
	public JButton findPath,clear,exit,sendButton,keyButton,auditButton;
	
	public JTextField destinationText,latiText,langText;
	
	public JTextArea contentText;
	
	public static JList neighList;
	
	public static JScrollPane neighScroll;
	
	public static JCheckBox drop;
	
	//right panel
	
	public JLabel pathLabel,forwardLabel,replyLabel;
	
	public JTable forwardTable,pathTable,replyTable;
	
	public static DefaultTableModel forwardModel,pathModel,replyModel;
	
	public JScrollPane pathScroll,forwardScroll,replyScroll;
	
	//variables
	
	public String nodeName;
	
	public int portNo,distance;
	
	public int totalSize=0;
	
	public Sender sender=new Sender();
	
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
	
	public Node() {
		Random r = new Random();
		distance = Integer.parseInt(JOptionPane.showInputDialog("Distance"));
		nodeName = "N" + r.nextInt(10) + "" + r.nextInt(10) + ""+ r.nextInt(10) + "" + r.nextInt(10);
		portNo = Integer.parseInt(r.nextInt(10) + "" + r.nextInt(10) + ""+ r.nextInt(10) + "" + r.nextInt(10));
	
		//myInfo
		try{
			NodeDetails node=new NodeDetails();
			node.distance=distance;
			node.port=portNo;
			node.name=nodeName;
			//node.address=""+InetAddress.getLocalHost().getHostName();
			node.address="localhost";
			StaticInfo.myInfo=node;
		
			StaticInfo.getAuditInfo();
			RSAKeyPair rsaKeyPair = new RSAKeyPair(2048);
	    	System.out.println("2");
	        rsaKeyPair.toFileSystem(StaticInfo.keyPath+"private_"+nodeName+".key", StaticInfo.keyPath+"public_"+nodeName+".key");
		System.out.println("port:"+portNo);
			new Receiver(portNo);
			new WirelessReceiver(nodeName, distance,"Node");
			new WirelessSender(nodeName, portNo, distance);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void UI(){
		mainform=new JFrame("Node - "+this.nodeName);
		
		mainpanel=new JPanel();
		
		mainpanel.setLayout(new GridLayout(1,2));
		mainpanel.add(left());
		mainpanel.add(right());
		
		mainform.add(mainpanel);
		mainform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainform.setResizable(false);
		mainform.setSize(900,650);
		mainform.setVisible(true);
	}
	
	public JPanel left(){
		leftPanel=new JPanel();
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		leftPanel.setLayout(null);
		
		nameLabel=new JLabel("Node Name : "+this.nodeName);
		nameLabel.setBounds(20,30,150,30);
		leftPanel.add(nameLabel);
		
		distanceLabel=new JLabel("Distance : "+this.distance);
		distanceLabel.setBounds(180,30,150,30);
		leftPanel.add(distanceLabel);
		
		destinationLabel=new JLabel("Destination");
		destinationLabel.setBounds(20,80,100,30);
		leftPanel.add(destinationLabel);
		
		destinationText=new JTextField();
		destinationText.setBounds(120,80,290,30);
		leftPanel.add(destinationText);
		
		findPath=new JButton("Find Path");
		findPath.setBounds(50, 150,100,30);
		leftPanel.add(findPath);
		
		clear=new JButton("Clear");
		clear.setBounds(180,150,100,30);
		leftPanel.add(clear);
		
		exit=new JButton("Exit");
		exit.setBounds(310,150,100,30);
		leftPanel.add(exit);
		
		contentLabel=new JLabel("Content");
		contentLabel.setBounds(20,200,100,30);
		leftPanel.add(contentLabel);
		
		contentText=new JTextArea();
		contentText.setBounds(120,200,290,80);
		leftPanel.add(contentText);
		
		
		keyButton=new JButton("Key Distribution");
		keyButton.setBounds(50, 320,100,30);
		leftPanel.add(keyButton);
		keyButton.setEnabled(false);
		
		sendButton=new JButton("Send Packets");
		sendButton.setBounds(180,320,100,30);
		leftPanel.add(sendButton);
		sendButton.setEnabled(false);
		
		auditButton=new JButton("Audit Request");
		auditButton.setBounds(310,320,100,30);
		leftPanel.add(auditButton);
		auditButton.setEnabled(false);
		
		neighLabel=new JLabel("Neighbour Nodes");
		neighLabel.setBounds(20,380,150,30);
		leftPanel.add(neighLabel);
		
		neighList=new JList();
		neighScroll=new JScrollPane(neighList);
		neighScroll.setBounds(20,410,390,200);
		leftPanel.add(neighScroll);
		
		//listeners
		findPath.addActionListener(this);
		exit.addActionListener(this);
		clear.addActionListener(this);
		keyButton.addActionListener(this);
		sendButton.addActionListener(this);
		auditButton.addActionListener(this);
		
		return(leftPanel);
	}
	
	public JPanel right(){
		rightPanel=new JPanel();
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		rightPanel.setLayout(null);
		
		drop=new JCheckBox("Packet Drop");
		drop.setBounds(300,10,100,30);
		rightPanel.add(drop);
		
		pathLabel=new JLabel("Path List");
		pathLabel.setBounds(20,30,100,30);
		rightPanel.add(pathLabel);
		
		pathModel=new DefaultTableModel();
		pathTable=new JTable(pathModel);
		pathModel.addColumn("Paths");
		pathScroll=new JScrollPane(pathTable);
		pathScroll.setBounds(20,60,390,150);
		rightPanel.add(pathScroll);
		
		forwardLabel=new JLabel("Forward Information");
		forwardLabel.setBounds(20,230,100,30);
		rightPanel.add(forwardLabel);
		
		forwardModel=new DefaultTableModel();
		forwardTable=new JTable(forwardModel);
		forwardModel.addColumn("Packet Number");
		forwardModel.addColumn("Source");
		forwardModel.addColumn("Send To");
		forwardModel.addColumn("Destination");
		forwardScroll=new JScrollPane(forwardTable);
		forwardScroll.setBounds(20,260,390,150);
		rightPanel.add(forwardScroll);
		
		
		replyLabel=new JLabel("Reply Information");
		replyLabel.setBounds(20,420,100,30);
		rightPanel.add(replyLabel);
		
		replyModel=new DefaultTableModel();
		replyTable=new JTable(replyModel);
		replyModel.addColumn("Packet No");
		replyModel.addColumn("Status");
		replyScroll=new JScrollPane(replyTable);
		replyScroll.setBounds(20,450,390,160);
		rightPanel.add(replyScroll);
	
		return(rightPanel);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Node().UI();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object obj=e.getSource();
		
		if(obj==exit){
			System.exit(0);
		}else if(obj==clear){
			destinationText.setText("");
			latiText.setText("");
			langText.setText("");
		}else if(obj==findPath){
			if(destinationText.getText()!=null && destinationText.getText().trim().length()>0){
				Packet pac=new Packet();
				pac.destination=destinationText.getText();
				pac.source=StaticInfo.myInfo.name;
				pac.sourcecNode=StaticInfo.myInfo;
				pac.type="pathReq";
					ArrayList<String> temp=new ArrayList<String>();
					temp.add(StaticInfo.myInfo.name);
				pac.pathNodes=temp;
					HashMap<Integer,String> tempMap=new HashMap<Integer, String>();
					tempMap.put(1,StaticInfo.myInfo.name);
				pac.path=tempMap;	
					
					
				Iterator i=StaticInfo.neighbours.keySet().iterator();
				
				while(i.hasNext()){
					NodeDetails tempNode=StaticInfo.neighbours.get(i.next());
					sender.sendFindPath(tempNode.address,tempNode.port,pac);
				}
			}else{
				StaticInfo.msg("Destination Field Empty");
			}
			keyButton.setEnabled(true);
		}else if(obj==sendButton){
			if(contentText.getText().trim().equals("")){
				StaticInfo.msg("Empty field not allowed");
			}else{
				String val=contentText.getText().toString();
				totalSize=val.length();
				for(int i=0;i<val.length();i++){
					
				
				Content con=new Content();
				con.content=""+val.charAt(i);
				con.packetNumber=i+1;
				con.totalPacketSize=val.length();
				con.destination=destinationText.getText();
				con.encryptType="normal";
				con.type="req";
				con.path=StaticInfo.path.get(0);
				con.sourceNode=StaticInfo.myInfo;
				con.reqTimeMap=new HashMap<String, String>();
				con.reqTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
				con.replyIp=StaticInfo.myInfo.address;
				con.replyPort=StaticInfo.myInfo.port;
				con.replyName=StaticInfo.myInfo.name;
				int index=StaticInfo.getIndexOfMap(con.path,StaticInfo.myInfo.name);
				
				try{
				Thread.sleep(1000);
				}catch (Exception ex) {
						ex.printStackTrace();
				}
				NodeDetails temp=StaticInfo.neighbours.get(con.path.get(index+1));
				sender.sendContent(temp.address, temp.port, con);
				
				Vector v=new Vector();
				v.add(""+con.packetNumber);
				v.add(con.sourceNode.name);
				v.add(temp.name);
				v.add(con.destination);
				//v.add(con.encryptType);
				//v.add("Request");
				forwardModel.addRow(v);
				}
			}
			auditButton.setEnabled(true);
		}else if(obj==keyButton){
			HashMap<Integer, String> map=StaticInfo.path.get(0);
			Iterator it=map.keySet().iterator();
			while(it.hasNext()){
				String str=it.next().toString();
				try{
					RSAKeyPair rsaKeyPair = new RSAKeyPair(2048);
			        rsaKeyPair.toFileSystem(StaticInfo.keyPath+"private_"+str+".key", StaticInfo.keyPath+"public_"+str+".key");
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			StaticInfo.msg("Symmetric Key distribution finished");
			sendButton.setEnabled(true);
		}else if(obj==auditButton){
			ADR adr=new ADR();
			adr.sourceNode=StaticInfo.myInfo.name;
			adr.sourcePort=StaticInfo.myInfo.port;
			adr.path=StaticInfo.path.get(0);
			adr.packetSize=totalSize;
			sender.sendADR(StaticInfo.IP, StaticInfo.PORT, adr);
		}
		
		
		
		/*else if(obj==pefProcess){
			Content con=new Content();
			con.packetNumber=1;
			con.totalPacketSize=1;
			con.destination=destinationText.getText();
			con.encryptType="pef";
			con.type="req";
			con.latitude=latiText.getText();
			con.longitude=langText.getText();
			con.encLatitude=new Encryption().encrypt(con.latitude);
			con.encLongitude=new Encryption().encrypt(con.longitude);
			con.path=StaticInfo.path.get(0);
			con.sourceNode=StaticInfo.myInfo;
			con.reqTimeMap=new HashMap<String, String>();
			con.reqTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
			int index=StaticInfo.getIndexOfMap(con.path,StaticInfo.myInfo.name);
			
			NodeDetails temp=StaticInfo.neighbours.get(con.path.get(index+1));
			sender.sendContent(temp.address, temp.port, con);
			
			Vector v=new Vector();
			v.add(con.sourceNode.name);
			v.add(temp.name);
			v.add(con.destination);
			v.add(con.encryptType);
			v.add("Request");
			forwardModel.addRow(v);
			
			showOutput.setEnabled(true);
			showGraph.setEnabled(true);
		
		}*//*else if(obj==showOutput){
			
			
			
			System.out.println(StaticInfo.normalContent.get(1).reqTimeMap);
			System.out.println(StaticInfo.normalContent.get(1).resTimeMap);
			
			System.out.println(StaticInfo.pefContent.get(1).reqTimeMap);
			System.out.println(StaticInfo.pefContent.get(1).resTimeMap);
			
			Encryption enc=new Encryption();
			String outputContent="";
			for(int i=1;i<=StaticInfo.pefContent.size();i++){
				outputContent=enc.decrypt(outputContent+StaticInfo.pefContent.get(i).output);
			}
			System.out.println("Outout Content:::"+outputContent);
			try{
				URL url = new URL(outputContent);
		        JPanel panel = new ImageTool(url);
		        JFrame frame = new JFrame("Output");
		        frame.add(panel);
		        frame.pack();
		        frame.setVisible(true);
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if(obj==showGraph){
			try{
				Content con=StaticInfo.normalContent.get(1);
				 DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
	            
				for(int i=1;i<=con.path.size();i++){
					if(i==1){
						System.out.println(con.path.get(i));
						System.out.println(con.reqTimeMap.get(con.path.get(i)));
						line_chart_dataset.addValue(0, "Normal Encryption", con.path.get(i));
					}else{
						Long cur=Long.parseLong(con.reqTimeMap.get(con.path.get(i)));
						Long pre=Long.parseLong(con.reqTimeMap.get(con.path.get(i-1)));
						System.out.println(con.path.get(i));
						System.out.println(""+(cur-pre));
						line_chart_dataset.addValue((cur-pre), "Normal Encryption", con.path.get(i));
					}
				}
				
				Content con1=StaticInfo.pefContent.get(1);
				for(int i=1;i<=con1.path.size();i++){
					if(i==1){
						System.out.println(con1.path.get(i));
						System.out.println(con1.reqTimeMap.get(con1.path.get(i)));
						line_chart_dataset.addValue(0, "PEF Encryption", con.path.get(i));
					}else{
						Long cur=Long.parseLong(con1.reqTimeMap.get(con1.path.get(i)));
						Long pre=Long.parseLong(con1.reqTimeMap.get(con1.path.get(i-1)));
						System.out.println(con1.path.get(i));
						System.out.println(""+(cur-pre));
						line_chart_dataset.addValue((cur-pre), "PEF Encryption", con.path.get(i));
					}
				}
				
				JFreeChart lineChartObject=ChartFactory.createLineChart("Transmission Time Vs Node","Nodes","Transmission Time(Ms)",line_chart_dataset,PlotOrientation.VERTICAL,true,true,false);                
	            
	             Step -3 : Write line chart to a file                
	             int width=650;  Width of the image 
	             int height=500;  Height of the image                 
	             File lineChart=new File("line_Chart_example.png");              
	             ChartUtilities.saveChartAsPNG(lineChart,lineChartObject,width,height);
	             System.out.println(lineChart.getAbsolutePath());
	             URL url = new URL("file:/"+lineChart.getAbsolutePath().replace("\\","/"));
	             JPanel panel = new ImageTool(url);
	             JFrame frame = new JFrame("Output");
	             frame.add(panel);
	             frame.pack();
	             frame.setVisible(true);
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}*/
		
	}

}
