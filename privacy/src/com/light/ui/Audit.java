package com.light.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.light.service.ServiceReceiver;
import com.light.utility.StaticInfo;



public class Audit {
	
	public JFrame mainform;
	
	public JPanel mainpanel;

	public JPanel rightPanel;
	
	
	//right
	
	public JLabel mistakeLabel;
	
	public static DefaultTableModel mistakeModel;
	
	public JTable mistakeTable;
	
	public JScrollPane mistakeScroll;
	
	
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
	
	public Audit() {
		StaticInfo.getAuditInfo();
		new ServiceReceiver(StaticInfo.PORT);
	}
	
	
	public void UI(){
		mainform=new JFrame("Audit node");
		
		mainpanel=new JPanel();
		
		mainpanel.setLayout(new GridLayout(1,1));
		mainpanel.add(mainUI());
		
		mainform.add(mainpanel);
		mainform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainform.setResizable(false);
		mainform.setSize(500,550);
		mainform.setVisible(true);
	}
	
	
	
	public JPanel mainUI(){
		int x=0;
		rightPanel=new JPanel();
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		rightPanel.setLayout(null);
		
		mistakeLabel=new JLabel("Audit Request");
		mistakeLabel.setBounds(x+20,20,200,30);
		rightPanel.add(mistakeLabel);
		
		mistakeModel=new DefaultTableModel();
		mistakeTable=new JTable(mistakeModel);
		mistakeModel.addColumn("Source Node");
		mistakeModel.addColumn("Status");
		mistakeScroll=new JScrollPane(mistakeTable);
		mistakeScroll.setBounds(x+20,50,450,450);
		rightPanel.add(mistakeScroll);
		
		
		return(rightPanel);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Audit().UI();
	}


}
