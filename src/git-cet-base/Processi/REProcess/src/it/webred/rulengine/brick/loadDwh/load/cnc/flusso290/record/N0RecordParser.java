/**
 * F.A 03/ago/2010 10.45.47 
 * 
 * 
 */
package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.record;



import java.util.List;


/**
 * @author Francesco
 *
 */
public class N0RecordParser extends it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser {

	public N0RecordParser() {
		super("001003008016");
	}
	
	@Override
	public List<String> getCNCRecord() {
		/*
		N0InizioFile inizio = new N0InizioFile();
		
		inizio.setCodEnteImpositore(super.getElemenet(2));
		inizio.setDtInvioFornitura(super.getDate(super.getElemenet(3)));
		*/
		
		return super.getCNCRecord();
	
	}

	
	@Override
	public void finishParseJob() {}

	

	

}
