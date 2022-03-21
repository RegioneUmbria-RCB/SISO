package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7ERecordParser extends RecordParser {

	public R7ERecordParser() {
		super("001004011016019023029030036040125128131135136138141142157172187202217232237238244259274289304306310312313314315319321396471475476660661736");
	}

	@Override
	public void finishParseJob() throws CNCParseException {
		// TODO Auto-generated method stub
		
	}

	public List<String> getCNCRecord() {
		List<String> results = super.getElements();
		results.remove(0);
		return results;
	}
}
