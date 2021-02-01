package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7HRecordParser extends RecordParser {

	public R7HRecordParser() {
		super("001004011016019029030036040125141142158208248249257261263339374379381387390393396400405407411432");		
	}

	@Override
	public void finishParseJob() throws CNCParseException {
		// TODO Auto-generated method stub
		
	}

}
