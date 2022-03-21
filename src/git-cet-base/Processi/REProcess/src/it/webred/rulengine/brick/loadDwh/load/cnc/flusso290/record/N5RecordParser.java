/**
 * F.A 03/ago/2010 12.14.46 
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
public class N5RecordParser  extends it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser {
	
	public N5RecordParser() {
		super("001003009011018025032039054");
	}

	/* (non-Javadoc)
	 * @see it.webred.git.cnc.parser.RecordParser#getCNCRecord()
	 */
	@Override
	public List<String> getCNCRecord() {
		/*
		N5FineRuolo fineRuolo = new N5FineRuolo();
		
		fineRuolo.setCodComuneIscrRuolo(getElemenet(2));
		fineRuolo.setProgressivoMinuta(new BigDecimal(getElemenet(3)));
		fineRuolo.setTotRec(new BigDecimal(getElemenet(4)));
		fineRuolo.setTotRecN2(new BigDecimal(getElemenet(5)));
		fineRuolo.setTotRecN3(new BigDecimal(getElemenet(6)));
		fineRuolo.setTotRecN4(new BigDecimal(getElemenet(7)));
		fineRuolo.setTotImposta(new BigDecimal(getElemenet(8)));
		
		return fineRuolo;
		*/
		return super.getCNCRecord();
	}

	@Override
	public void finishParseJob() {}

}
