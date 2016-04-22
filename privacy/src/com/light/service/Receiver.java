package com.light.service;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.light.bo.Content;
import com.light.bo.NodeDetails;
import com.light.bo.Packet;
import com.light.ui.DisplayResult;
import com.light.ui.Node;
import com.light.utility.StaticInfo;

public class Receiver extends Thread{
	
	Socket socket=null;
	ServerSocket serverSocket=null;
	ObjectInputStream ois=null;
	Sender sender=new Sender();
	
	public Receiver(int port) {
		System.out.println("Re:port:"+port);
		try{
			serverSocket=new ServerSocket(port);
			System.out.println("aafter:"+port);
			start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void run(){
		try{
			while(true){
				socket=serverSocket.accept();
				ois=new ObjectInputStream(socket.getInputStream());
				Object obj=ois.readObject();
				
				if(obj instanceof Packet){
					Packet packet=(Packet)obj;
					if(packet.type.equalsIgnoreCase("pathReq")){
						System.out.println("packet req::");
						if(StaticInfo.myInfo.name.equalsIgnoreCase(packet.destination)){
							//destination sent response
							ArrayList<String> tempList=packet.pathNodes;
							HashMap<Integer,String> tempMap=packet.path;
							tempList.add(StaticInfo.myInfo.name);
							tempMap.put(tempMap.size()+1,StaticInfo.myInfo.name);
							packet.pathNodes=tempList;
							packet.path=tempMap;
							packet.type="pathRes";
							
							sender.sendFindPath(packet.sourcecNode.address,packet.sourcecNode.port,packet);
							
						}else{
							//path forward
							System.out.println("packet forward::");
							Iterator i=StaticInfo.neighbours.keySet().iterator();
							ArrayList<String> tempList=packet.pathNodes;
							HashMap<Integer,String> tempMap=packet.path;
							tempList.add(StaticInfo.myInfo.name);
							tempMap.put(tempMap.size()+1,StaticInfo.myInfo.name);
							while(i.hasNext()){
								System.out.println("while"+packet.pathNodes);
								String nodeNm=i.next().toString();
								System.out.println("whilefff"+nodeNm);
								if(!packet.pathNodes.contains(nodeNm)){
									NodeDetails tempNode=StaticInfo.neighbours.get(nodeNm);
										
										
										packet.pathNodes=tempList;
										packet.path=tempMap;
										sender.sendFindPath(tempNode.address,tempNode.port,packet);
								}
							}
							
						}
						
					}else if(packet.type.equalsIgnoreCase("pathRes")){
						System.out.println("packet res ::");
						StaticInfo.path.add(packet.path);
						String pathString="";
						for(int i=1;i<=packet.path.size();i++){
							if(i==packet.path.size()){
								pathString=pathString+packet.path.get(i);
							}else{
								pathString=pathString+packet.path.get(i)+" ->";
							}
						}
						String[] str={pathString};
						Node.pathModel.addRow(str);
						System.out.println(":::::::"+pathString);
					}
				}else if(obj instanceof Content){
					Content con=(Content)obj;
					
					sender.sendReply(con.replyIp, con.replyPort,""+ con.packetNumber);
					sender.sendReply(StaticInfo.IP,StaticInfo.PORT,con.replyName);
					if(con.type.equalsIgnoreCase("req")){
						
						if(con.destination.equalsIgnoreCase(StaticInfo.myInfo.name)){
							if(con.packetNumber==1){
								//StaticInfo.output="";
								StaticInfo.output=con.content;
							}else{
								StaticInfo.output=StaticInfo.output+con.content;
							}
							
							if(con.packetNumber==con.totalPacketSize){
								StaticInfo.output=StaticInfo.output+con.content;
								StaticInfo.msg("Content Received");
								new DisplayResult(StaticInfo.output);
							}
						}else{
							con.reqTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
							int index=StaticInfo.getIndexOfMap(con.path,StaticInfo.myInfo.name);
							
							if(con.encryptType.equalsIgnoreCase("normal")){
								try{
									Thread.sleep(1000);
									}catch (Exception ex) {
											ex.printStackTrace();
									}
							}
							if(!Node.drop.isSelected()){
								con.replyIp=StaticInfo.myInfo.address;
								con.replyPort=StaticInfo.myInfo.port;
								con.replyName=StaticInfo.myInfo.name;
								NodeDetails temp=StaticInfo.neighbours.get(con.path.get(index+1));
								sender.sendContent(temp.address, temp.port, con);
								Vector v=new Vector();
								v.add(""+con.packetNumber);
								v.add(con.sourceNode.name);
								v.add(temp.name);
								v.add(con.destination);
								//v.add(con.encryptType);
								//v.add("Request");
								Node.forwardModel.addRow(v);
							}
						}
						
					}else{
						
							if(con.sourceNode.name.equalsIgnoreCase(StaticInfo.myInfo.name)){
								con.resTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
								if(con.encryptType.equalsIgnoreCase("normal")){
									System.out.println("Source Node reply received Normal");
									StaticInfo.normalContent.put(con.packetNumber,con);
									if(StaticInfo.normalContent.size()==con.totalPacketSize){
										StaticInfo.msg("Normal encryption process Complete");
									}
								}else{
									System.out.println("Source Node reply received PEF");
									StaticInfo.pefContent.put(con.packetNumber,con);
									if(StaticInfo.pefContent.size()==con.totalPacketSize){
										StaticInfo.msg("PEF encryption process Complete");
									}
								}
								
							}else{
								con.resTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
								int index=StaticInfo.getIndexOfMap(con.path,StaticInfo.myInfo.name);
								
								if(con.encryptType.equalsIgnoreCase("normal")){
									try{
										Thread.sleep(1000);
										}catch (Exception ex) {
												ex.printStackTrace();
										}
								}
								con.replyIp=StaticInfo.myInfo.address;
								con.replyPort=StaticInfo.myInfo.port;
								con.replyName=StaticInfo.myInfo.name;
								NodeDetails temp=StaticInfo.neighbours.get(con.path.get(index-1));
								
								Vector v=new Vector();
								v.add(""+con.packetNumber);
								v.add(con.sourceNode.name);
								v.add(temp.name);
								v.add(con.destination);
							//	v.add(con.encryptType);
								//v.add("Response");
								Node.forwardModel.addRow(v);
								
								sender.sendContent(temp.address, temp.port, con);
							}
						
					}
					
				}else if(obj instanceof String){
					String str=(String)obj;
					Vector vec=new Vector();
					vec.add(str);
					vec.add("Success");
					Node.replyModel.addRow(vec);
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
