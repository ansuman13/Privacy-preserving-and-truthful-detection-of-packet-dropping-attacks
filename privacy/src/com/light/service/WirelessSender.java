package com.light.service;

import java.net.*;
import javax.swing.*;
import java.util.Properties;
public class WirelessSender  implements Runnable
{
    MulticastSocket ms;
    byte[] sendbyte;
    public boolean status=true;
    Thread t;
    String nodename;
    int distance,portno,bandwidthObj;
   

public WirelessSender(String nodeNameObj, int port, int distanceObj)
{
	this.nodename=nodeNameObj;
	this.portno=port;
	this.distance=distanceObj;
	
    t=new Thread(this);
    t.start();
}

public void run()
{

    try
    {
        ms=new MulticastSocket(5454);
        InetAddress ia=InetAddress.getByName("228.2.5.1");
        ms.joinGroup(ia);
        Thread.sleep(5000);
        while(status)
        {
        String sendmsg=nodename+":"+portno+":"+distance+":"+"localhost";
        sendbyte=sendmsg.getBytes();
		DatagramPacket dp=new DatagramPacket(sendbyte,sendbyte.length,ia,5454);
		ms.send(dp);
        Thread.sleep(1200);
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}

}
