package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R5ZRecordParser extends RecordParser {

	public R5ZRecordParser() {
		super("001004011016019023029036043050057064071078085092107122137152167182197212227736737744");
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
