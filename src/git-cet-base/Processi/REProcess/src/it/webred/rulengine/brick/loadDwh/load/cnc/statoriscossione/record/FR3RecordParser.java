package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class FR3RecordParser extends RecordParser {

	public FR3RecordParser() {
		super("001004011014019023029125128131135143145148151155156172180195210225240255270273274276284309312315323326327342370385");
	}
	
	
	@Override
	public void finishParseJob() throws CNCParseException {
		
	}

	public List<String> getCNCRecord() {
		List<String> results = super.getCNCRecord();
		//System.out.println("Before ["+results.get(5)+"]");
		String tmp = results.get(5).replace(" ", "-");
		//System.out.println("Tmp ["+tmp+"]");
		results.remove(5);
		results.add(5, tmp);
		//System.out.println("After ["+results.get(5)+"]");
		return results;
	}
}
