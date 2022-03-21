/**
 * F.A 03/ago/2010 11.41.27 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.record;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Francesco
 *
 */
public class N9RecordParser extends it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser {

	
	public N9RecordParser() {
		super("001003008015022029036043050");
	}
	
	@Override
	public List<String> getCNCRecord() {
		/*
		N9FineFile fine = new N9FineFile();
		
		fine.setCodEnteImpositore(getElemenet(2));
		fine.setTotRec(new BigDecimal(getElemenet(3)));
		fine.setTotRecN1(new BigDecimal(getElemenet(4)));
		fine.setTotRecN2(new BigDecimal(getElemenet(5)));
		fine.setTotRecN3(new BigDecimal(getElemenet(6)));
		fine.setTotRecN4(new BigDecimal(getElemenet(7)));
		fine.setTotRecN4(new BigDecimal(getElemenet(8)));
		
		return fine;
		*/
		
		return super.getCNCRecord();
	}

	@Override
	public void finishParseJob() {}

}
