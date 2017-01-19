package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbMotivoChiusuraInt;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.naming.NamingException;

import org.primefaces.expression.impl.ThisExpressionResolver;

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
		if(DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equals(stato)){
			dataDa = cs.getCsDDiario().getDtAttivazioneDa();
			dataA = cs.getCsDDiario().getDtAttivazioneA();			
		}
		if(DataModelCostanti.FoglioAmministrativo.STATO_SOSPENSIONE.equals(stato)){
			dataDa = cs.getCsDDiario().getDtSospensioneDa();
			dataA = cs.getCsDDiario().getDtSospensioneA();			
		}
		if(DataModelCostanti.FoglioAmministrativo.STATO_CHIUSURA.equals(stato)){
			dataDa = cs.getCsDDiario().getDtChiusuraDa();
			dataA = cs.getCsDDiario().getDtChiusuraA();			
		}	
		if(!DataModelCostanti.FoglioAmministrativo.STATO_VALUTAZIONE.equals(stato)){		
			this.setDtTipoAttDa(dataDa);
			this.setDtTipoAttA(dataA);			
		}
				
		this.setFlagRespinto((cs.getFlagRespinto()!=null && cs.getFlagRespinto().intValue()==1) ? Boolean.TRUE : Boolean.FALSE); 
		
		this.setMotivoChiusura(cs.getCsTbMotivoChiusuraInt()!=null ? String.valueOf(cs.getCsTbMotivoChiusuraInt().getId()): null);
		this.setMotivoRespinto(cs.getMotivoRespinto());
		this.setTipoAttivazione(cs.getTipoAttivazione());
				
		//valorizzare relazione_diario
		List<CsDDiario> lstDiario = new LinkedList<CsDDiario>( cs.getCsDDiario().getCsDDiariFiglio() );
		if(lstDiario!=null && lstDiario.size()>0)
			for(CsDDiario d : lstDiario)  {
				if(d.getCsTbTipoDiario().getId().equals( DataModelCostanti.TipoDiario.RELAZIONE_ID ))
					this.setIdRelazione(d.getId());
			}
	}
	
	public void valorizzaJpa(CsFlgIntervento cs) throws NamingException{
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		
		cs.getCsDDiario().setDtAmministrativa(this.getDataAmministrativa());
		
		cs.setDescrSospensione(this.getDescrSospensione());
		
		cs.setFlagAttSospC(this.getFlagAttivazione());
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
		cs.getCsDDiario().setCsOOperatoreSettore(opSettore);
		cs.getCsDDiario().setDtAttivazioneDa(null);
		cs.getCsDDiario().setDtAttivazioneA(null);
		cs.getCsDDiario().setDtSospensioneDa(null);
		cs.getCsDDiario().setDtSospensioneA(null);
		cs.getCsDDiario().setDtChiusuraDa(null);
		cs.getCsDDiario().setDtChiusuraA(null);
		if(DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equals(this.flagAttivazione)){
			cs.getCsDDiario().setDtAttivazioneDa(this.getDtTipoAttDa());
			cs.getCsDDiario().setDtAttivazioneA(this.getDtTipoAttA());
		}
		if(DataModelCostanti.FoglioAmministrativo.STATO_SOSPENSIONE.equals(this.flagAttivazione)){
			cs.getCsDDiario().setDtSospensioneDa(this.getDtTipoAttDa());
			cs.getCsDDiario().setDtSospensioneA(this.getDtTipoAttA());
		}
		if(DataModelCostanti.FoglioAmministrativo.STATO_CHIUSURA.equals(this.flagAttivazione)){
			cs.getCsDDiario().setDtChiusuraDa(this.getDtTipoAttDa());
			cs.getCsDDiario().setDtChiusuraA(this.getDtTipoAttA());
		}
			
		cs.setFlagRespinto((this.getFlagRespinto()!=null && this.getFlagRespinto()) ? new BigDecimal(1) : new BigDecimal(0)); 
		
		//Recupero motivo chiusura
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) 
				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		
		b.setObj(this.getMotivoChiusura());
		CsTbMotivoChiusuraInt tb = confService.getMotivoChiusuraIntervento(b);
		cs.setCsTbMotivoChiusuraInt(tb);
		
		cs.setMotivoRespinto(this.getMotivoRespinto());
		cs.setTipoAttivazione(this.getTipoAttivazione());
		
	}

	public Long getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(Long idRelazione) {
		this.idRelazione = idRelazione;
	}
}
