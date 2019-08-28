package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatiCasoBean {
	
	private CsASoggettoLAZY soggetto;
	private CsOOperatore operatore;
	private IterInfoStatoMan lastIterStepInfo;
	private String nInterventi;
	private List<InterventoBaseDTO> listaInterventi;
	private List<ErogazioneBaseDTO> listaErogazioni;
	private List<CsASoggettoCategoriaSocLAZY> listaCatSociale;
	private Date dataApertura;
	private List<CsACasoOpeTipoOpe> lstOperatori;
	//private String catSociale;
	
	public DatiCasoBean(){
	}
	
	public DatiCasoBean(CsASoggettoLAZY soggetto, List<CsACasoOpeTipoOpe> operatori, IterInfoStatoMan lastIterStepInfo){
		this.lstOperatori = new ArrayList<CsACasoOpeTipoOpe>();
		this.soggetto = soggetto;
		this.lastIterStepInfo = lastIterStepInfo;
		int i = 0;
		this.operatore = null;
		while(i<operatori.size()){
			CsACasoOpeTipoOpe ope  = operatori.get(i);
			if(ope.getFlagResponsabile())
				operatore = ope.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore();
			else if(!ope.getDataFineApp().before(new Date()))
				lstOperatori.add(ope);
			i++;
		}
	}
	
	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}
	
	public IterInfoStatoMan getLastIterStepInfo() {
		return lastIterStepInfo;
	}

	public void setLastIterStepInfo(IterInfoStatoMan lastIterStepInfo) {
		this.lastIterStepInfo = lastIterStepInfo;
	}

	public String getnInterventi() {
		return nInterventi;
	}

	public void setnInterventi(String nInterventi) {
		this.nInterventi = nInterventi;
	}

	public List<CsASoggettoCategoriaSocLAZY> getListaCatSociale() {
		return listaCatSociale;
	}

	public void setListaCatSociale(List<CsASoggettoCategoriaSocLAZY> listaCatSociale) {
		this.listaCatSociale = listaCatSociale;
	}
	
	public String getCategoriaPrevalente(){
		boolean trovato = false;
		int i=0;
		CsASoggettoCategoriaSocLAZY prevalente=null;
		while(i<this.listaCatSociale.size() && !trovato){
			CsASoggettoCategoriaSocLAZY cs = this.listaCatSociale.get(i);
			if(cs.getPrevalente().intValue()==1){
				trovato=true;
				prevalente = cs;
			}
			i++;
		}
		
		if(prevalente!=null)
			return prevalente.getCsCCategoriaSociale().getTooltip();
		else 
			return null;
		
	}
	
	public String getCategorieSecondarie(){
		String catSoc = "";
		for(CsASoggettoCategoriaSocLAZY soggCat: this.listaCatSociale) {
			if(soggCat.getPrevalente().intValue()!=1)
			    catSoc += ", " + soggCat.getCsCCategoriaSociale().getTooltip();
		}
		if(catSoc.length() > 1)
		   return catSoc.substring(2);
		else
		   return null;
	}

	public Date getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}

	public List<InterventoBaseDTO> getListaInterventi() {
		return listaInterventi;
	}

	public List<ErogazioneBaseDTO> getListaErogazioni() {
		return listaErogazioni;
	}

	public void setListaInterventi(List<InterventoBaseDTO> listaInterventi) {
		this.listaInterventi = listaInterventi;
	}

	public void setListaErogazioni(List<ErogazioneBaseDTO> listaErogazioni) {
		this.listaErogazioni = listaErogazioni;
	}

	public CsOOperatore getOperatore() {
		return operatore;
	}

	public void setOperatore(CsOOperatore operatore) {
		this.operatore = operatore;
	}

	public List<CsACasoOpeTipoOpe> getLstOperatori() {
		return lstOperatori;
	}

	public void setLstOperatori(List<CsACasoOpeTipoOpe> lstOperatori) {
		this.lstOperatori = lstOperatori;
	}

}
