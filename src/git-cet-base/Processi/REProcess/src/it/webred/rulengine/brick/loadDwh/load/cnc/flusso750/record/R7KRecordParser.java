package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7KRecordParser extends RecordParser {

	public R7KRecordParser() {
		super("001004011016019023029030036040125141149150158169171178179180181196220240241249253255331366379384386390411419454459461467472474478499500535540542548553555559580738741");		
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
