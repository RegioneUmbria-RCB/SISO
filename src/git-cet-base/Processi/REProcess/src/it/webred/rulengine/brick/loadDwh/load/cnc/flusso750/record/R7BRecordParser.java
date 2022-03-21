package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7BRecordParser extends RecordParser {

	public R7BRecordParser() {
		super("001004011016019023029030036040125141157168192212221223227262275280282286307315350355357363368370374395396737740743746750");		
	}

	@Override
	public void finishParseJob() throws CNCParseException {
		
		
	}

	public List<String> getCNCRecord() {
		List<String> results = super.getElements();
		results.remove(0);
		return results;
	}
}
