package com.light.ui;

import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.JFrame;


public class DisplayResult {

	public JFrame mainform=new JFrame();
	
	public DisplayResult(String list) {
		
		
		TextArea ta=new TextArea(list);
		mainform.add(ta);
		mainform.setSize(400,400);
		mainform.setResizable(false);
		mainform.setVisible(true);
	}
	
	/*public static void main(String args[]){
		new DisplayResult();
	}*/
	
}
