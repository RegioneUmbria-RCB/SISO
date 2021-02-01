package it.webred.AMProfiler.util;

import java.util.ArrayList;
import java.util.List;

public class OreMinuti {

	public List<String> getOre()
	 {
		List<String> ore = new ArrayList<String>();
		for(int i = 0; i<24; i++){
			if(i<10)
				ore.add("0" + String.valueOf(i));
			else ore.add(String.valueOf(i));
		}
		return ore;
	 }
	
	public List<String> getMinuti()
	 {
		List<String> minuti = new ArrayList<String>();
		for(int i = 0; i<60; i+=5){
			if(i<10)
				minuti.add("0" + String.valueOf(i));
			else minuti.add(String.valueOf(i));
		}
		return minuti;
	 }
}
