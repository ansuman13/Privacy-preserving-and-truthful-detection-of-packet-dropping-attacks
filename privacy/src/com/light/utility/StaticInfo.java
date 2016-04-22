package com.light.utility;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.light.bo.Content;
import com.light.bo.NodeDetails;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class StaticInfo {

	public static HashMap<String,NodeDetails> neighbours=new HashMap<String, NodeDetails>();
	
	public static ArrayList<HashMap<Integer,String>> path=new ArrayList<HashMap<Integer,String>>();
	
	public static NodeDetails myInfo;

	public static String NORMAL_KEY="dfjdiufpodqwjjdk";
	
	public static HashMap<Integer,Content> normalContent=new HashMap<Integer,Content>();
	
	public static HashMap<Integer,Content> pefContent=new HashMap<Integer,Content>();
	
	public static HashMap<String,Integer> HLA=new HashMap<String,Integer>();
	
	public static String IP="";
	
	public static int PORT=0;
	
	public static String keyPath="";
	
	public static String output;
	
	public static void getAuditInfo(){
		try{
			Properties prop=new Properties();
			InputStream input=new FileInputStream("config.properties");
			prop.load(input);
			
			IP=prop.getProperty("ip");
			PORT=Integer.parseInt(prop.getProperty("port"));
			keyPath=prop.getProperty("key");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static int getIndexOfMap(HashMap<Integer,String> path,String name){
		int index=0;
		for(int i=1;i<=path.size();i++){
			if(path.get(i).equalsIgnoreCase(name)){
				index=i;
				break;
			}
		}
		return(index);
	}
	
	public static String url(String latitude,String longitude){
		String url="https://maps.googleapis.com/maps/api/staticmap?center="+latitude+","+longitude+"&zoom=12&size=400x400&maptype=roadmap";
		return(url);
		
	}
	
	public static void msg(String msg){
		JOptionPane.showMessageDialog(null,msg);
	}
	
	public static ArrayList<String> packetSplit(String str){
		ArrayList<String> packetList=new ArrayList<String>();
		int length=str.length()/10;
		
		if(length>0){
			for(int i=0;i<length;i++){
				int start=i*10;
				if(i==(length-1)){
					packetList.add(str.substring(start,str.length()));
					System.out.println();
				}else{
					packetList.add(str.substring(start,start+10));
				}
			}
		}else{
			packetList.add(str);
		}
		return(packetList);
	}
}
