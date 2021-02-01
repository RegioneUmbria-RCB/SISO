/**
 * F.A 03/ago/2010 17.19.02 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;


/**
 * @author Francesco
 *
 */
public class N4RecordParser extends it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser {

	public N4RecordParser() {
		super("001003009011025029033046059061069071146");
	}

	
	@Override
	public void finishParseJob() throws CNCParseException {
		String lineRule = null;
		String line = getElemenet(13);
		System.out.println("Line13 ["+line+"]");
		super.removeElement(13);
		
		if (line.startsWith("E")) 
			lineRule = "146147158173";		
		else if (line.startsWith("S")) {
			super.addEmptyElements("ANANANNNAN");
			lineRule = "146147149161167";
		}
		else if (line.startsWith("M")) {
			lineRule = "146147157199212213";
			super.addEmptyElements("ANANANANANANANNNAN");			
		}
		else
			lineRule="146178";
		
		super.setLineRule(lineRule);
		super.parseLine(super.getLine());
		
		if (line.startsWith("E"))
			super.addEmptyElements("ANANANNNANANANANNNAN");
		else if (line.startsWith("S")) {
			
			super.addEmptyElements("ANANANNNAN");
		}
		else if (line.startsWith("M")) {}
		else {
			super.removeLastElement();
			super.addEmptyElements("ANANANNNANANANANNNAN");
		}
	}
}
