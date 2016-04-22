package com.light.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Packet implements Serializable{

	public String source;
	
	public String destination;
	
	public NodeDetails sourcecNode;
	
	public HashMap<Integer,String> path;
	
	public String type;
	
	public ArrayList<String> pathNodes;
	
	
}