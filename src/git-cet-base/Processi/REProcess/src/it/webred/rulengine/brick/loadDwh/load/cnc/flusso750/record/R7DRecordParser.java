package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7DRecordParser extends RecordParser {

	public R7DRecordParser() {
		super("001004011016019023029030036040125185193194254601676");	
	}

	@Override
	public void finishParseJob() throws CNCParseException {
		
	}

}
