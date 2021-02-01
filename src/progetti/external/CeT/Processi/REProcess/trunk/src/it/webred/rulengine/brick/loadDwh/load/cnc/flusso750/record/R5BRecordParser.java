package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R5BRecordParser extends RecordParser {

	public R5BRecordParser() {
		super("001004011016019023029030036040043047049052067082097112113115119121126127133134");
	}
	
	@Override
	public void finishParseJob() throws CNCParseException {		
	}
	
	
}
