package it.webred.cs.jsf.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.naming.NamingException;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FoglioAmministrativo;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbMotivoChiusuraInt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

@ManagedBean
@NoneScoped
public class DatiFglInterventoBean extends CsUiCompBaseBean implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	private long tipoDiario;
	private long casoId;
	
	private Date dataAmministrativa;

	private Boolean flagRespinto;
	private String motivoRespinto;
	private String motivoChiusura;
	private String flagAttivazione;
	private String tipoAttivazione;
	private String descrSospensione;
	private Date dtTipoAttDa;
	private Date dtTipoAttA;
	private Long idRelazione;
	
	private Long punteggio;
	private String fascia;
	
	private Long visSecondoLivello;
	
	public Date getDataAmministrativa() {
		if(dataAmministrativa==null)
			dataAmministrativa=new Date();
		return dataAmministrativa;
	}

	public void setDataAmministrativa(Date dataAmm) {
		this.dataAmministrativa = dataAmm;
	}

	public Boolean getFlagRespinto() {
		return flagRespinto;
	}

	public void setFlagRespinto(Boolean flagRespinto) {
		this.flagRespinto = flagRespinto;
	}

	public String getMotivoRespinto() {
		return motivoRespinto;
	}

	public void setMotivoRespinto(String motivoRespinto) {
		this.motivoRespinto = motivoRespinto;
	}

	public String getFlagAttivazione() {
		return flagAttivazione;
	}

	public void setFlagAttivazione(String flagAttivazione) {
		this.flagAttivazione = flagAttivazione;
	}

	public String getTipoAttivazione() {
		return tipoAttivazione;
	}

	public void setTipoAttivazione(String tipoAttivazione) {
		this.tipoAttivazione = tipoAttivazione;
	}

	public String getDescrSospensione() {
		return descrSospensione;
	}

	public void setDescrSospensione(String descrSospensione) {
		this.descrSospensione = descrSospensione;
	}

	public Date getDtTipoAttDa() {
		return dtTipoAttDa;
	}

	public void setDtTipoAttDa(Date dtTipoAttDa) {
		this.dtTipoAttDa = dtTipoAttDa;
	}

	public Date getDtTipoAttA() {
		return dtTipoAttA;
	}

	public void setDtTipoAttA(Date dtTipoAttA) {
		this.dtTipoAttA = dtTipoAttA;
	}

	
	public String getMotivoChiusura() {
		return motivoChiusura;
	}

	public void setMotivoChiusura(String motivoChiusura) {
		this.motivoChiusura = motivoChiusura;
	}

	public long getCasoId() {
		return casoId;
	}

	public void setCasoId(long casoId) {
		this.casoId = casoId;
	}

	public long getTipoDiario() {
		return tipoDiario;
	}

	public void setTipoDiario(long tipoDiario) {
		this.tipoDiario = tipoDiario;
	}
	
	public void valorizzaBean(CsFlgIntervento cs){
		
		this.setDataAmministrativa(cs.getCsDDiario().getDtAmministrativa());
		
		this.setDescrSospensione(cs.getDescrSospensione());

		String stato = cs.getFlagAttSospC();
		this.setFlagAttivazione(stato);
		Date dataDa = null;
		Date dataA = null;		
		if(FoglioAmministrativo.STATO.ATTIVAZIONE.getCodice().equals(stato)){
			dataDa = cs.getCsDDiario().getDtAttivazioneDa();
			dataA = cs.getCsDDiario().getDtAttivazioneA();			
		}
		if(FoglioAmministrativo.STATO.SOSPENSIONE.getCodice().equals(stato)){
			dataDa = cs.getCsDDiario().getDtSospensioneDa();
			dataA = cs.getCsDDiario().getDtSospensioneA();			
		}
		if(FoglioAmministrativo.STATO.CHIUSURA.getCodice().equals(stato)){
			dataDa = cs.getCsDDiario().getDtChiusuraDa();
			dataA = cs.getCsDDiario().getDtChiusuraA();			
		}	
		if(!FoglioAmministrativo.STATO.VALUTAZIONE.getCodice().equals(stato)){		
			this.setDtTipoAttDa(dataDa);
			this.setDtTipoAttA(dataA);			
		}
		if(!FoglioAmministrativo.STATO.GRADUATORIA.getCodice().equals(stato)){		
			this.setDtTipoAttDa(dataDa);
			this.setDtTipoAttA(dataA);			
		}
				
		this.setFlagRespinto(cs.getFlagRespinto()!=null ? cs.getFlagRespinto().booleanValue() : Boolean.FALSE); 
		
		this.setMotivoChiusura(cs.getCsTbMotivoChiusuraInt()!=null ? String.valueOf(cs.getCsTbMotivoChiusuraInt().getId()): null);
		this.setMotivoRespinto(cs.getMotivoRespinto());
		this.setTipoAttivazione(cs.getTipoAttivazione());
		this.setPunteggio(cs.getPunteggio());
		this.setFascia(cs.getFascia());
				
		//valorizzare relazione_diario
		List<CsDDiario> lstDiario = new LinkedList<CsDDiario>( cs.getCsDDiario().getCsDDiariFiglio() );
		if(lstDiario!=null && lstDiario.size()>0)
			for(CsDDiario d : lstDiario)  {
				if(d.getCsTbTipoDiario().getId() == DataModelCostanti.TipoDiario.RELAZIONE_ID )
					this.setIdRelazione(d.getId());
			}
	}
	
	public void valorizzaJpa(CsFlgIntervento cs) throws NamingException{
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		
		cs.getCsDDiario().setDtAmministrativa(this.getDataAmministrativa());
		
		cs.setDescrSospensione(this.getDescrSospensione());
		
		cs.setFlagAttSospC(this.getFlagAttivazione());
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		cs.getCsDDiario().setCsOOperatoreSettore(opSettore);
		cs.getCsDDiario().setDtAttivazioneDa(null);
		cs.getCsDDiario().setDtAttivazioneA(null);
		cs.getCsDDiario().setDtSospensioneDa(null);
		cs.getCsDDiario().setDtSospensioneA(null);
		cs.getCsDDiario().setDtChiusuraDa(null);
		cs.getCsDDiario().setDtChiusuraA(null);
		if(isAttivazione()){
			cs.getCsDDiario().setDtAttivazioneDa(this.getDtTipoAttDa());
			cs.getCsDDiario().setDtAttivazioneA(this.getDtTipoAttA());
		}
		if(isSospensione()){
			cs.getCsDDiario().setDtSospensioneDa(this.getDtTipoAttDa());
			cs.getCsDDiario().setDtSospensioneA(this.getDtTipoAttA());
		}
		if(isChiusura()){
			cs.getCsDDiario().setDtChiusuraDa(this.getDtTipoAttDa());
			cs.getCsDDiario().setDtChiusuraA(this.getDtTipoAttA());
		}
			
		cs.setFlagRespinto((this.getFlagRespinto()!=null && this.getFlagRespinto()) ? Boolean.TRUE : Boolean.FALSE); 
		cs.getCsDDiario().setVisSecondoLivello(visSecondoLivello);
		
		//Recupero motivo chiusura
		
		b.setObj(this.getMotivoChiusura());
		CsTbMotivoChiusuraInt tb = confService.getMotivoChiusuraIntervento(b);
		cs.setCsTbMotivoChiusuraInt(tb);
		
		cs.setMotivoRespinto(this.getMotivoRespinto());
		cs.setTipoAttivazione(this.getTipoAttivazione());
		
		cs.setPunteggio(getPunteggio());
		cs.setFascia(getFascia());
		
	}

	public Long getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(Long idRelazione) {
		this.idRelazione = idRelazione;
	}
	
	public void resetDatiAttivita(){
		if (!isAttivazione())
			setTipoAttivazione(null);

		if (isChiusura()) {
			setDtTipoAttA(null);
		}else{
			setMotivoChiusura(null);
		}
		
		if (!isSospensione())
			setDescrSospensione(null);
		
		if (isValutazione() || this.isGraduatoria()) {
			setDtTipoAttA(null);
			setDtTipoAttDa(null);
		}
		
		if(!isGraduatoria()){
			this.punteggio=null;
			this.fascia=null;
		}
	}

	public boolean isChiusura(){
		return FoglioAmministrativo.STATO.CHIUSURA.getCodice().equalsIgnoreCase(this.flagAttivazione);
	}
	public boolean isSospensione(){
		return FoglioAmministrativo.STATO.SOSPENSIONE.getCodice().equalsIgnoreCase(this.flagAttivazione);
	}
	public boolean isValutazione(){
		return FoglioAmministrativo.STATO.VALUTAZIONE.getCodice().equalsIgnoreCase(this.flagAttivazione);
	}
	public boolean isAttivazione(){
		return FoglioAmministrativo.STATO.ATTIVAZIONE.getCodice().equalsIgnoreCase(this.flagAttivazione);
	}
	public boolean isGraduatoria(){
		return FoglioAmministrativo.STATO.GRADUATORIA.getCodice().equalsIgnoreCase(this.flagAttivazione);
	}

	public Long getVisSecondoLivello() {
		return visSecondoLivello;
	}

	public void setVisSecondoLivello(Long visSecondoLivello) {
		this.visSecondoLivello = visSecondoLivello;
	}

	public Long getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(Long punteggio) {
		this.punteggio = punteggio;
	}

	public String getFascia() {
		return fascia;
	}

	public void setFascia(String fascia) {
		this.fascia = fascia;
	}
	
	public String getLabelDettaglioOperazione(){
		String s = "";
		if(this.isAttivazione()) 
			s = "Tipo Attivazione";
		else if(this.isSospensione()) 
			s = "Tipo Sospensione";
		else if(this.isChiusura()) 
			s = "Motivo Chiusura";
		return s;
	}
	
}
