package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R3ARecordParser extends RecordParser {

	public R3ARecordParser() {
		super("001004009012015020023027032040048051054731");
	}
	
	
	@Override
	public void finishParseJob() throws CNCParseException {
		
	}
	
	@Override
	public List<String> getCNCRecord() {
		List<String> results = super.getElements();
		results.remove(0);
		return results;
	}

}
