/**
 * F.A 03/ago/2010 12.19.01 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Francesco
 *
 */
public class N2RecordParser extends RecordParser {
	
	public N2RecordParser() {
		super("001003009011025041049051057087092094100105109130136166171173179184188209210");
	}

	@Override
	public List<String> getCNCRecord() {	
		return super.getCNCRecord();
	}

	@Override
	public void finishParseJob() throws CNCParseException {
		// Recupero il tipo di intestatario
		String tipo = getElemenet(24);
		
		String lineRule = null;
		if (tipo.equals("1"))
			lineRule = "210234254255263267286287";
		else {
			lineRule = "210286287";			
			super.addEmptyElements("ANANANNNANAN");
		}
		
		String line = getElemenet(25);
		super.removeElement(25);
		
		super.setLineRule(lineRule);
		super.parseLine(super.getLine());
		
		if (tipo.equals("1")) {
			super.addEmptyElements("AN");
		}
	}

	
}
