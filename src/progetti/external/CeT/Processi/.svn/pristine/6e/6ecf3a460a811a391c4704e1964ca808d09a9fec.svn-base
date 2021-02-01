package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7GRecordParser extends RecordParser {

	public R7GRecordParser() {
		super("001004011016019023029030036040125128131135137152154169171186188203205220222237239254256271273288290305307322324339341356358373375390392407409424426441443458460");		
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
