package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R00CRecordParser extends RecordParser {

	public R00CRecordParser() {
		super("001004006009013021029");
	}
	
	@Override
	public void finishParseJob() throws CNCParseException {
	}

}
