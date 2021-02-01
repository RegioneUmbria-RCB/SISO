package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class FR5RecordParser extends RecordParser {

	public FR5RecordParser() {
		super("001004011014019020026028032034035043051059067075076084092107122137152155170");
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
