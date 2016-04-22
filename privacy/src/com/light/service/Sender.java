package com.light.service;

import java.io.ObjectOutputStream;
import java.net.Socket;

import com.light.bo.ADR;
import com.light.bo.Content;
import com.light.bo.Packet;

public class Sender {

	Socket socket=null;
	ObjectOutputStream oos=null;
	
	public void sendFindPath(String sysname,int port,Packet obj){
		try{
			System.out.println("send:"+port+"sys:"+sysname);
			socket=new Socket(sysname,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(obj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendContent(String sysname,int port,Content obj){
		try{
			
			socket=new Socket(sysname,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(obj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void sendReply(String sysname,int port,String obj){
		try{
			
			socket=new Socket(sysname,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(obj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendADR(String sysname,int port,ADR obj){
		try{
			
			socket=new Socket(sysname,port);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(obj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
