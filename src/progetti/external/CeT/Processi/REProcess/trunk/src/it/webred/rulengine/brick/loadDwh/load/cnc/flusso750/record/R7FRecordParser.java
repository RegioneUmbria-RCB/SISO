package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7FRecordParser extends RecordParser {

	public R7FRecordParser() {
		super("001004011016019023029030036040125128131135140141147162177179183185200215");
	}

	@Override
	public void finishParseJob() throws CNCParseException {
	}
	

}
