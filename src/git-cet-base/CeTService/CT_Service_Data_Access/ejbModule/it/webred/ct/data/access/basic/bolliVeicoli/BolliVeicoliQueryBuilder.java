package it.webred.ct.data.access.basic.bolliVeicoli;

import it.webred.ct.data.access.basic.bolliVeicoli.dto.RicercaBolliVeicoliDTO;


public class BolliVeicoliQueryBuilder {
	
	
	private final String HQL_BOLLI = "FROM BolloVeicolo BOLLOVEICOLO "
			+ " where 1=1 @BOLLOVEICOLO_WHERE@ "
			+ " order by BOLLOVEICOLO.ragioneSociale, BOLLOVEICOLO.cognome, BOLLOVEICOLO.nome ";
	
	public String createQueryBolliVeicoliByCriteria(RicercaBolliVeicoliDTO criteria){
		
		String hql = this.HQL_BOLLI;
		
		String condSogg = "";
		condSogg = ( ( criteria.getCodiceFiscalePartitaIva() == null || criteria.getCodiceFiscalePartitaIva().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " upper(BOLLOVEICOLO.codiceFiscalePiva) = upper('"+ criteria.getCodiceFiscalePartitaIva().trim() + "') " );
		condSogg = ( ( criteria.getCognome() == null || criteria.getCognome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " upper(BOLLOVEICOLO.cognome) = upper('"+ criteria.getCognome().trim() + "') " );
		condSogg = ( ( criteria.getNome() == null || criteria.getNome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " upper(BOLLOVEICOLO.nome) = upper('"+ criteria.getNome().trim() + "') " );
		condSogg = ( ( criteria.getRagioneSociale() == null || criteria.getRagioneSociale().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " upper(BOLLOVEICOLO.ragioneSociale) = upper('"+ criteria.getRagioneSociale().trim() + "') " );
		condSogg = ( ( criteria.getTarga() == null || criteria.getTarga().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " upper(BOLLOVEICOLO.targa) = upper('"+ criteria.getTarga().trim() + "') " );
		condSogg = ( ( criteria.getDataPrimaImmatricolazioneDal() != null ) ? condSogg : addOperator(condSogg) +  " '"+ criteria.getDataPrimaImmatricolazioneDal() + "' >=  BOLLOVEICOLO.dtPrimaImmatricolazione " );
		condSogg = ( ( criteria.getDataPrimaImmatricolazioneAl() != null ) ? condSogg : addOperator(condSogg) +  " '"+ criteria.getDataPrimaImmatricolazioneAl() + "' <=  BOLLOVEICOLO.dtPrimaImmatricolazione " );
		condSogg = ( ( criteria.getDataPrimaImmatricolazioneIl() != null ) ? condSogg : addOperator(condSogg) +  " '"+ criteria.getDataPrimaImmatricolazioneIl() + "' =  BOLLOVEICOLO.dtPrimaImmatricolazione " );
		
		hql = hql.replace("@BOLLOVEICOLO_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}



