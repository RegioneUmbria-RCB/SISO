/**
 * F.A 03/ago/2010 17.08.35 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.record;

import java.util.ArrayList;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;



/**
 * @author Francesco
 *
 */
public class N3RecordParser extends it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser {

	
	/**
	 * @param lineRule
	 */
	public N3RecordParser() {
		super("001003009011025041047077082084090095099120126156161163169174178199");
	}

	@Override
	public void finishParseJob() throws CNCParseException {
		// Recupero il tipo di intestatario
		String tipo = getElemenet(22);
		
		String lineRule = null;
		if (tipo.equals("1"))
			lineRule = "200224244245253257200276";
		else {
			lineRule = "200276";
			List<String> emptyList = new ArrayList<String>();
			emptyList.add("");
			emptyList.add("");
			emptyList.add("");
			emptyList.add("0");
			emptyList.add("");
		}
		
		String line = getElemenet(23);
		super.setLineRule(lineRule);
		super.parseLine(super.getLine());
				
		
	}
	
	

}
