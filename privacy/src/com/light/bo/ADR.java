package com.light.bo;

import java.io.Serializable;
import java.util.HashMap;

public class ADR implements Serializable {
	
	public String sourceNode;
	
	public int sourcePort;
	
	public int packetSize;
	
	public HashMap<Integer,String> path;
	
}
