package com.light.service;


import java.util.*;
import java.net.*;

import com.light.bo.NodeDetails;
import com.light.ui.Node;
import com.light.ui.Service;
import com.light.utility.StaticInfo;


public class WirelessReceiver extends Thread {

	public static Vector neighNodes = new Vector();

	MulticastSocket ms;

	String s = null;

	String nodename;

	int distance;
	
	public String type;

	public WirelessReceiver(String nodename, int distance,String type) {
		this.nodename = nodename;
		this.distance = distance;
		this.type=type;
		start();
	}

	public void run() {
		try {
			while (true) {
				ms = new MulticastSocket(5454);
				InetAddress ia = InetAddress.getByName("228.2.5.1");
				ms.joinGroup(ia);
				byte[] b = new byte[1000];
				DatagramPacket dp = new DatagramPacket(b, b.length);
				ms.receive(dp);
				s = new String(dp.getData()).trim();
				GetInfo(s);
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void GetInfo(String info) {
		StringTokenizer st = new StringTokenizer(info, ":");
		String tempnodename = st.nextToken();
		String tempportno = st.nextToken();
		int tempdistance = Integer.parseInt(st.nextToken());
		String tempaddress = st.nextToken();
			NodeDetails node=new NodeDetails();
			node.name=tempnodename;
			node.port=(Integer.parseInt(tempportno));
			node.address=tempaddress;
			node.distance=tempdistance;
		
		if (!tempnodename.equals(nodename)) {
				if (!neighNodes.contains(tempnodename)) {
				
						if (tempdistance >= (distance - 50) && tempdistance <= (distance + 50)) {
							//	String strdet = tempaddress + "$" + tempportno;
							//	MainP.stodet.put(tempnodename, strdet);
								StaticInfo.neighbours.put(tempnodename,node);
								neighNodes.add(tempnodename);
								System.out.println(neighNodes);
							//	if(this.type.equalsIgnoreCase("node")){
									Node.neighList.setListData(neighNodes);
							/*	}else{
									Service.neighList.setListData(neighNodes);
								}*/
						}
					}
				}
			}
		}

