package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R3ZRecordParser extends RecordParser {
	
	public R3ZRecordParser() {
		super("001004009012015020023027032040048051058065072079086093100107114121128143158173188203218233248736737744");
	}
	
	@Override
	public void finishParseJob() throws CNCParseException {
	}
	
	@Override
	public List<String> getCNCRecord() {
		List<String> results = super.getElements();
		results.remove(0);
		System.out.println("Results ["+results+"]");
		return results;
	}	

}
