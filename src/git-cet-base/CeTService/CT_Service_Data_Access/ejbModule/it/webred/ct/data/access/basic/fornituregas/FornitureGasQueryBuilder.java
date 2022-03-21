package it.webred.ct.data.access.basic.fornituregas;

import it.webred.ct.data.access.basic.fornituregas.dto.FornituraGasDTO;


public class FornitureGasQueryBuilder {
	
	private final String HQL_UTENZE_BY_PARAMS = "from SitUGas UTENZE where 1 = 1 @HQL_UTENZE_WHERE@ order by UTENZE.annoRiferimento, UTENZE.identificativoUtenza ";
	
	private final String HQL_UTENTI_BY_PARAMS = "from SitUGas UTENTI where 1 = 1 @HQL_UTENTI_WHERE@ order by UTENTI.cognomeUtente, UTENTI.ragioneSociale, UTENTI.nomeUtente ";
	
	/*
	 * XXX AAA Se viene cambiata la clausola ORDER BY dovrà essere rivista anche la logica di popolamento 
	 * della lista restituita da GITOUT 
	 */
	private final String HQL_UTENZE_UTENTI_BY_PARAMS = "from SitUGas UTENZE where 1 = 1 @HQL_UTENZE_UTENTI_WHERE@ order by UTENZE.cognomeUtente, UTENZE.ragioneSociale, UTENZE.cfTitolareUtenza, UTENZE.nomeUtente, UTENZE.annoRiferimento, UTENZE.identificativoUtenza ";
	
	public String createQueryListaUtenzeByCriteria(FornituraGasDTO criteria){
		
		String hql = this.HQL_UTENZE_BY_PARAMS;
		
		String condSogg = "";
		String indirizzo = "";
		indirizzo += ( ( criteria.getTipoArea() == null || criteria.getTipoArea().trim().equalsIgnoreCase("") ) ? "" : criteria.getTipoArea().trim() + " " );
		indirizzo += ( ( criteria.getVia() == null || criteria.getVia().trim().equalsIgnoreCase("") ) ? "" : criteria.getVia().trim() + " " );
		indirizzo += ( ( criteria.getCivico() == null || criteria.getCivico().trim().equalsIgnoreCase("") ) ? "" : criteria.getCivico().trim() + " " );
		
		condSogg = ( ( indirizzo == null || indirizzo.trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.indirizzoUtenza LIKE '%"+ indirizzo.trim() +"%' " );
		condSogg = ( ( criteria.getAnnoUtenza() == null || criteria.getAnnoUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.annoRiferimento = '"+ criteria.getAnnoUtenza().trim() +"' " );
		condSogg = ( ( criteria.getCodiceUtenza() == null || criteria.getCodiceUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.identificativoUtenza = '"+ criteria.getCodiceUtenza().trim() +"' " );
		condSogg = ( ( criteria.getDenominazione() == null || criteria.getDenominazione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.ragioneSociale LIKE '%"+ criteria.getDenominazione().trim() +"%' " );
		condSogg = ( ( criteria.getCognome() == null || criteria.getCognome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.cognomeUtente LIKE '%"+ criteria.getCognome().trim() +"%' " );
		condSogg = ( ( criteria.getNome() == null || criteria.getNome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.nomeUtente LIKE '%"+ criteria.getNome().trim() +"%' " );
		/*
		 * Non è necessario distinguere attraverso la tipologia perchè cf e piva sono nello stesso campo 
		 */
		condSogg = ( ( criteria.getIdentificativo() == null || criteria.getIdentificativo().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.cfTitolareUtenza = '"+ criteria.getIdentificativo().trim() +"' " );
		
		hql = hql.replace("@HQL_UTENZE_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaUtentiByCriteria(FornituraGasDTO criteria){
		
		String hql = this.HQL_UTENTI_BY_PARAMS;
		
		String condSogg = "";
		condSogg = ( ( criteria.getDenominazione() == null || criteria.getDenominazione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.ragioneSociale LIKE '%"+ criteria.getDenominazione().trim() +"%' " );
		condSogg = ( ( criteria.getCognome() == null || criteria.getCognome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.cognomeUtente LIKE '%"+ criteria.getCognome().trim() +"%' " );
		condSogg = ( ( criteria.getNome() == null || criteria.getNome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.nomeUtente LIKE '%"+ criteria.getNome().trim() +"%' " );
		/*
		 * Non è necessario distinguere attraverso la tipologia perchè cf e piva sono nello stesso campo 
		 */
		condSogg = ( ( criteria.getIdentificativo() == null || criteria.getIdentificativo().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.cfTitolareUtenza = '"+ criteria.getIdentificativo().trim() +"' " );
				
		hql = hql.replace("@HQL_UTENTI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaFornitureGasByCriteria(FornituraGasDTO criteria){
		
		String hql = this.HQL_UTENZE_UTENTI_BY_PARAMS;
		
		String condSogg = "";
		String indirizzo = "";
		indirizzo += ( ( criteria.getTipoArea() == null || criteria.getTipoArea().trim().equalsIgnoreCase("") ) ? "" : criteria.getTipoArea().trim() + " " );
		indirizzo += ( ( criteria.getVia() == null || criteria.getVia().trim().equalsIgnoreCase("") ) ? "" : criteria.getVia().trim() + " " );
		indirizzo += ( ( criteria.getCivico() == null || criteria.getCivico().trim().equalsIgnoreCase("") ) ? "" : criteria.getCivico().trim() + " " );
		
		condSogg = ( ( indirizzo == null || indirizzo.trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.indirizzoUtenza LIKE '%"+ indirizzo.trim() +"%' " );
		condSogg = ( ( criteria.getAnnoUtenza() == null || criteria.getAnnoUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.annoRiferimento = '"+ criteria.getAnnoUtenza().trim() +"' " );
		condSogg = ( ( criteria.getCodiceUtenza() == null || criteria.getCodiceUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.identificativoUtenza = '"+ criteria.getCodiceUtenza().trim() +"' " );
		
		condSogg = ( ( criteria.getDenominazione() == null || criteria.getDenominazione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.ragioneSociale LIKE '%"+ criteria.getDenominazione().trim() +"%' " );
		condSogg = ( ( criteria.getCognome() == null || criteria.getCognome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.cognomeUtente LIKE '%"+ criteria.getCognome().trim() +"%' " );
		condSogg = ( ( criteria.getNome() == null || criteria.getNome().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.nomeUtente LIKE '%"+ criteria.getNome().trim() +"%' " );
		/*
		 * Non è necessario distinguere attraverso la tipologia perchè cf e piva sono nello stesso campo 
		 */
		condSogg = ( ( criteria.getIdentificativo() == null || criteria.getIdentificativo().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.cfTitolareUtenza = '"+ criteria.getIdentificativo().trim() +"' " );
		
		hql = hql.replace("@HQL_UTENZE_UTENTI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}

