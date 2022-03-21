package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R5ARecordParser extends RecordParser {

	public R5ARecordParser() {
		super("001004011016019023029030036037038039044052053677678685693694703704712713721722729737738741745");
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
