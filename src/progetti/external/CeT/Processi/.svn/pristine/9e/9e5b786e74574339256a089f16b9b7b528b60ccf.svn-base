package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750.record;

import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

public class R7ARecordParser extends RecordParser {

	public R7ARecordParser() {
		super("001004011016019023029030036040125141149150158169171178179180181196220240241249253255331366379384386390411419454459461467472474478499500535540542548553555559580663666669672676736737738741745");
	}

	@Override
	public void finishParseJob() throws CNCParseException {
	}
	
	public List<String> getCNCRecord() {
		List<String> results = super.getElements();
		results.remove(0);
		return results;
	}
	
	/*
	public static void main(String[] args) {
		try {
			R7ARecordParser p = new R7ARecordParser();
			p.parseLine("R7A0000008046992982003001222A1     2001000000000309001793970                                                                GHZPLA68C41F205P0000000000000000000000000000         1 1               GHEZZI                  PAOLA               F19680301F205MI                                                                            VIALE SCALA GRECA 321 SC B P 2                  96100SRI754                     00000000VIA SCALA GRECA                    00321B 00000096100  I754                     1                                          00000000000                                                                                                                                                                                         0000000000000");
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
	}
	*/
}
