/**
 * F.A 03/ago/2010 12.06.23 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.record;

import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Francesco
 *
 */
public class N1RecordParser extends RecordParser {
	
	public N1RecordParser() {
		super("001003009011012016018019023024042043045046");
	}

	/* (non-Javadoc)
	 * @see it.webred.git.cnc.parser.RecordParser#getCNCRecord()
	 */
	@Override
	public List<String> getCNCRecord() {
		/*
		N1InizioRuolo inizioRuolo = new N1InizioRuolo();
		
		inizioRuolo.setCodComuneIscrRuolo(getElemenet(2));
		inizioRuolo.setProgressivoMinuta(new BigDecimal(getElemenet(3)));
		inizioRuolo.setCodTipoRuolo(getElemenet(4));
		inizioRuolo.setNumeroRuolo(new BigDecimal(getElemenet(5)));
		inizioRuolo.setRateRuolo(new BigDecimal(getElemenet(6)));
		inizioRuolo.setCodRuoloCoattivo(getElemenet(7));
		inizioRuolo.setCodSede(getElemenet(8));
		inizioRuolo.setCodTipoCompenso(getElemenet(9));
		inizioRuolo.setCodRuoloIciap(getElemenet(10));
		inizioRuolo.setNumeroConvenzione(new BigDecimal(getElemenet(11)));
		inizioRuolo.setCodArt(getElemenet(12));
		
		return inizioRuolo;
		*/
		
		return super.getCNCRecord();
	}

	@Override
	public void finishParseJob() {}

}
