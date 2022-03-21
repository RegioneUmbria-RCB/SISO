package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;




import com.sun.istack.internal.logging.Logger;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDSinba;

public class SinbaMinoriSearchResultDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//private BigDecimal idAnagrafica;
	
	//private long idCaso;
	
	private int eta;
	
	private CsASoggettoLAZY soggettoBeneficiario;
	
	/*private Date dt_nascita;

	private String nome;

	private String cognome;

	private String cFbeneficiario;*/

	private String categoriaSociale;
	
	private List<InfoExportSinbaDTO> csDSinbas; 
	
	private boolean isMinoreEsportatoSinba; //mi dice se il minore può considerarsi da esportare o già esportato per scheda sinba

	private Long lastInsertedSinbaId;//id dell'ultimo sinba inserito in ordine cronologico
	
	private Long lastInsertedExportedSinbaId;//id dell'ultimo sinba inserito ESPORTATO in ordine cronologico
	
    public void checkLastSinbaInserted(){ 
		
		List<CsDSinba> tempSinbas=new ArrayList<CsDSinba>();
		if (this.getCsDSinbas() != null){
		//rimuovo sinba esportati
		Iterator<InfoExportSinbaDTO> iterator=this.getCsDSinbas().iterator();
		while(iterator.hasNext()){
			CsDSinba elem=iterator.next().getCsDSinba();
			if(elem.getDataExport()==null){
				tempSinbas.add(elem);
			}
		}
		Collections.sort(tempSinbas, new Comparator<CsDSinba>(){

			@Override
			public int compare(CsDSinba o1, CsDSinba o2) {
				// TODO Auto-generated method stub
				return o1.getCsDDiario().getDtIns().compareTo(o2.getCsDDiario().getDtIns());
			}
			
		});
		
		}
		if(tempSinbas.size()>0){
			CsDSinba lastSinba=tempSinbas.get(tempSinbas.size()-1);
		    this.setLastInsertedSinbaId(lastSinba.getDiarioId());
		}
		else  this.setLastInsertedSinbaId(0L);
	    
	  
	}
public void checkLastSinbaInsertedExported(){ 
		
		List<CsDSinba> tempSinbas=new ArrayList<CsDSinba>();
		if (this.getCsDSinbas() != null){
		//rimuovo sinba esportati
		Iterator<InfoExportSinbaDTO> iterator=this.getCsDSinbas().iterator();
		while(iterator.hasNext()){
			CsDSinba elem=iterator.next().getCsDSinba();
			if(elem.getDataExport()!=null){
				tempSinbas.add(elem);
			}
		}
		Collections.sort(tempSinbas, new Comparator<CsDSinba>(){

			@Override
			public int compare(CsDSinba o1, CsDSinba o2) {
				// TODO Auto-generated method stub
				return o1.getCsDDiario().getDtIns().compareTo(o2.getCsDDiario().getDtIns());
			}
			
		});
		
		}
		if(tempSinbas.size()>0){
			CsDSinba lastSinba=tempSinbas.get(tempSinbas.size()-1);
		    this.setLastInsertedExportedSinbaId(lastSinba.getDiarioId());
		}
		else  this.setLastInsertedExportedSinbaId(0L);
	    
	  
	}

	public String getCategoriaSociale() {
		return categoriaSociale;
	}

	public void setCategoriaSociale(String categoriaSociale) {
		this.categoriaSociale = categoriaSociale;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		this.eta = eta;
	}

	public List<InfoExportSinbaDTO> getCsDSinbas() {
		return csDSinbas;
	}

	public void setCsDSinbas(List<InfoExportSinbaDTO> csDSinbas) {
		this.csDSinbas = csDSinbas;
	}

	public boolean isMinoreEsportatoSinba() {
		return isMinoreEsportatoSinba;
	}

	public void setMinoreEsportatoSinba(boolean isMinoreEsportatoSinba) {
		this.isMinoreEsportatoSinba = isMinoreEsportatoSinba;
	}

	public Long getLastInsertedSinbaId() {
		return lastInsertedSinbaId;
	}

	public void setLastInsertedSinbaId(Long lastInsertedSinbaId) {
		this.lastInsertedSinbaId = lastInsertedSinbaId;
	}
	public Long getLastInsertedExportedSinbaId() {
		return lastInsertedExportedSinbaId;
	}
	public void setLastInsertedExportedSinbaId(Long lastInsertedExportedSinbaId) {
		this.lastInsertedExportedSinbaId = lastInsertedExportedSinbaId;
	}
	public CsASoggettoLAZY getSoggettoBeneficiario() {
		return soggettoBeneficiario;
	}
	public void setSoggettoBeneficiario(CsASoggettoLAZY soggettoBeneficiario) {
		this.soggettoBeneficiario = soggettoBeneficiario;
	}
	public String getCfBeneficiario(){
		return this.soggettoBeneficiario.getCsAAnagrafica().getCf().toUpperCase();
	}
	public Long getIdCaso(){
		return this.soggettoBeneficiario.getCsACaso().getId();
	}
	public Long getIdentificativo(){
		return this.soggettoBeneficiario.getCsACaso().getIdentificativo();
	}
	public String getCognome(){
		return this.soggettoBeneficiario.getCsAAnagrafica().getCognome();
	}
	public String getNome(){
		return this.soggettoBeneficiario.getCsAAnagrafica().getNome();
	}
}

