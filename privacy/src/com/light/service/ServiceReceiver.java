package com.light.service;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.light.algorithm.CryptoUtils;
import com.light.algorithm.Encryption;
import com.light.bo.ADR;
import com.light.bo.Content;
import com.light.bo.NodeDetails;
import com.light.bo.Packet;
import com.light.ui.Audit;
import com.light.ui.Node;
import com.light.ui.Service;
import com.light.utility.StaticInfo;

public class ServiceReceiver extends Thread{
	
	Socket socket=null;
	ServerSocket serverSocket=null;
	ObjectInputStream ois=null;
	Sender sender=new Sender();
	
	public ServiceReceiver(int port) {
		try{
			serverSocket=new ServerSocket(port);
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
				
				if(obj instanceof String){
					String str=(String)obj;
					
					if(StaticInfo.HLA.containsKey(str)){
						int count=StaticInfo.HLA.get(str);
						StaticInfo.HLA.remove(str);
						StaticInfo.HLA.put(str, count+1);
					}else{
						StaticInfo.HLA.put(str, 1);
					}
					System.out.println(StaticInfo.HLA);
				}else if(obj instanceof ADR){
					ADR adr=(ADR)obj;
					String result="";
					for(int i=0;i<adr.path.size()-1;i++){
						if(StaticInfo.HLA.containsKey(adr.path.get(i+1))){
							if(StaticInfo.HLA.get(adr.path.get(i+1))==adr.packetSize){
								result="success";
							}else{
								result="Packet Dropped";
								break;
							}
						}else{
							result="Packet Dropped";
							break;
						}
					}
					Vector vec=new Vector();
					vec.add(""+adr.sourceNode);
					vec.add(result);
					Audit.mistakeModel.addRow(vec);
					StaticInfo.msg(result);
				}
				
				
			}
				
			/*if(obj instanceof Packet){
				Packet packet=(Packet) obj;
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
						
						while(i.hasNext()){
							System.out.println("while"+packet.pathNodes);
							String nodeNm=i.next().toString();
							System.out.println("whilefff"+nodeNm);
							if(!packet.pathNodes.contains(nodeNm)){
								NodeDetails tempNode=StaticInfo.neighbours.get(nodeNm);
									ArrayList<String> tempList=packet.pathNodes;
									HashMap<Integer,String> tempMap=packet.path;
									tempList.add(StaticInfo.myInfo.name);
									tempMap.put(tempMap.size()+1,StaticInfo.myInfo.name);
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
				}
				
			}else if(obj instanceof Content){
					Content con=(Content)obj;
					
					if(con.type.equalsIgnoreCase("req")){
						
						System.out.println("Received Request");
						con.reqTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
						
							String decLati="";
							String decLong="";
							String result="";
							String encResult="";
							String encType;
							if(con.encryptType.equalsIgnoreCase(("normal"))){
								decLati=CryptoUtils.decrypt(StaticInfo.NORMAL_KEY, con.encLatitude);
								decLong=CryptoUtils.decrypt(StaticInfo.NORMAL_KEY, con.encLongitude);
								result=StaticInfo.url(decLati,decLong);
								//encResult=CryptoUtils.encrypt(StaticInfo.NORMAL_KEY,result);
								encType="Normal";
							}else{
								Encryption enc=new Encryption();
								decLati=enc.decrypt(con.encLatitude);
								decLong=enc.decrypt(con.longitude);
								result=StaticInfo.url(decLati,decLong);
								//encResult=enc.encrypt(result);
								encType="PEF";
							}
							
							//
							Vector v=new Vector();
							v.add(con.sourceNode.name);
							v.add(decLati+"::"+decLong);
							v.add(encType);
							Service.reqModel.addRow(v);
							
							//implement google search
							ArrayList<String> pacList=StaticInfo.packetSplit(result);
							int index=StaticInfo.getIndexOfMap(con.path,StaticInfo.myInfo.name);
							NodeDetails temp=StaticInfo.neighbours.get(con.path.get(index-1));
							con.type="res";
							for(int i=0;i<pacList.size();i++){
								con.totalPacketSize=pacList.size();
								con.packetNumber=i+1;
								
								con.resTimeMap=new HashMap<String, String>();
								con.resTimeMap.put(StaticInfo.myInfo.name,""+new Date().getTime());
								
								if(con.encryptType.equalsIgnoreCase(("normal"))){
									con.output=CryptoUtils.encrypt(StaticInfo.NORMAL_KEY, pacList.get(i));
									try{
										Thread.sleep(1000);
									}catch (Exception e) {
										e.printStackTrace();
									}
								}else{
									con.output=new Encryption().encrypt(pacList.get(i));
								}
								sender.sendContent(temp.address, temp.port, con);
							}
					}
					
				}
			}*/
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
