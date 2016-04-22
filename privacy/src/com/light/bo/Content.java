package com.light.bo;

import java.io.Serializable;
import java.util.HashMap;

public class Content implements Serializable {

	public NodeDetails sourceNode;
	
	public String destination;
	
	public String latitude;
	
	public String longitude;
	
	public String encLatitude;
	
	public String encLongitude;
	
	public String encryptType;
	
	public String type;
	
	public HashMap<Integer,String> path;
	
	public String output;
	
	public String encOutput;
	
	public int packetNumber;
	
	public int totalPacketSize;
	
	public HashMap<String,String> reqTimeMap;
	
	public HashMap<String,String> resTimeMap;
	
	public String content;
	
	public String replyIp;
	
	public int replyPort;
	
	public String replyName;
	
}
