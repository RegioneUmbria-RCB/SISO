package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R99CRecordParser extends RecordParser {

	public R99CRecordParser() {
		super("001004006009013021");
	}
	
	@Override
	public void finishParseJob() throws CNCParseException {
	}
	
	

}
