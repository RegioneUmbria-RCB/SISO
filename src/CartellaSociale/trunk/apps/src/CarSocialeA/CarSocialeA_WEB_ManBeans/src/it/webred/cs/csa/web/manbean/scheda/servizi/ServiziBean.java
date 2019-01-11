package it.webred.cs.csa.web.manbean.scheda.servizi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbServComunita;
import it.webred.cs.data.model.CsTbServLuogoStr;
import it.webred.cs.data.model.CsTbServResRetta;
import it.webred.cs.data.model.CsTbServSemiresRetta;
import it.webred.cs.jsf.interfaces.IServizi;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class ServiziBean extends CsUiCompBaseBean implements IServizi{

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private boolean ADH;
	private boolean ADI;
	private boolean ADIP;
	private boolean pasti;
	private boolean SAD;
	private boolean assistenzaComunicazione;
	private boolean CSE;
	private boolean eduIndividuale;
	private boolean eduProgetto;
	private boolean sostegnoScolastico;
	private boolean integrScolastica;
	private boolean integrScolasticaProv;
	private boolean poloH;
	private boolean ADO;
	private String altro;
	private boolean centroDiurno;
	private boolean altroSemires;
	private String nomeIndirizzoStrSemires;
	private Long idLuogoStrSemires;
	private Long idRettaSemires;
	private List<SelectItem> lstLuogoStr;
	private List<SelectItem> lstRettaSemires;
	private boolean comunitaMin;
	private Long idComunitaTipo;
	private List<SelectItem> lstComunitaTipo;
	private boolean affidoFam;
	private boolean comunitaTer;
	private boolean RSA;
	private String nomeIndirizzoStrRes;
	private Long idLuogoStrRes;
	private Long idRettaRes;
	private List<SelectItem> lstRettaRes;
	private boolean sostGenitore;
	private boolean trasportoUfficio;
	private boolean trasportoSociale;
	private String effettuaServizio;

	@Override
	public boolean isADH() {
		return ADH;
	}

	public void setADH(boolean aDH) {
		ADH = aDH;
	}

	@Override
	public boolean isADI() {
		return ADI;
	}

	public void setADI(boolean aDI) {
		ADI = aDI;
	}

	@Override
	public boolean isADIP() {
		return ADIP;
	}

	public void setADIP(boolean aDIP) {
		ADIP = aDIP;
	}

	@Override
	public boolean isPasti() {
		return pasti;
	}

	public void setPasti(boolean pasti) {
		this.pasti = pasti;
	}

	@Override
	public boolean isSAD() {
		return SAD;
	}

	public void setSAD(boolean sAD) {
		SAD = sAD;
	}

	@Override
	public boolean isAssistenzaComunicazione() {
		return assistenzaComunicazione;
	}

	public void setAssistenzaComunicazione(boolean assistenzaComunicazione) {
		this.assistenzaComunicazione = assistenzaComunicazione;
	}

	@Override
	public boolean isCSE() {
		return CSE;
	}

	public void setCSE(boolean cSE) {
		CSE = cSE;
	}

	@Override
	public boolean isEduIndividuale() {
		return eduIndividuale;
	}

	public void setEduIndividuale(boolean eduIndividuale) {
		this.eduIndividuale = eduIndividuale;
	}

	@Override
	public boolean isEduProgetto() {
		return eduProgetto;
	}

	public void setEduProgetto(boolean eduProgetto) {
		this.eduProgetto = eduProgetto;
	}

	@Override
	public boolean isSostegnoScolastico() {
		return sostegnoScolastico;
	}

	public void setSostegnoScolastico(boolean sostegnoScolastico) {
		this.sostegnoScolastico = sostegnoScolastico;
	}

	@Override
	public boolean isIntegrScolastica() {
		return integrScolastica;
	}

	public void setIntegrScolastica(boolean integrScolastica) {
		this.integrScolastica = integrScolastica;
	}

	@Override
	public boolean isIntegrScolasticaProv() {
		return integrScolasticaProv;
	}

	public void setIntegrScolasticaProv(boolean integrScolasticaProv) {
		this.integrScolasticaProv = integrScolasticaProv;
	}

	@Override
	public boolean isPoloH() {
		return poloH;
	}

	public void setPoloH(boolean poloH) {
		this.poloH = poloH;
	}

	@Override
	public boolean isADO() {
		return ADO;
	}

	public void setADO(boolean aDO) {
		ADO = aDO;
	}

	@Override
	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

	@Override
	public boolean isCentroDiurno() {
		return centroDiurno;
	}

	public void setCentroDiurno(boolean centroDiurno) {
		this.centroDiurno = centroDiurno;
	}

	@Override
	public boolean isAltroSemires() {
		return altroSemires;
	}

	public void setAltroSemires(boolean altroSemires) {
		this.altroSemires = altroSemires;
	}

	@Override
	public String getNomeIndirizzoStrSemires() {
		return nomeIndirizzoStrSemires;
	}

	public void setNomeIndirizzoStrSemires(String nomeIndirizzoStrSemires) {
		this.nomeIndirizzoStrSemires = nomeIndirizzoStrSemires;
	}

	@Override
	public Long getIdLuogoStrSemires() {
		return idLuogoStrSemires;
	}

	public void setIdLuogoStrSemires(Long idLuogoStrSemires) {
		this.idLuogoStrSemires = idLuogoStrSemires;
	}

	@Override
	public Long getIdRettaSemires() {
		return idRettaSemires;
	}

	public void setIdRettaSemires(Long idRettaSemires) {
		this.idRettaSemires = idRettaSemires;
	}

	@Override
	public List<SelectItem> getLstRettaSemires() {
		
		if(lstRettaSemires == null){
			lstRettaSemires = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbServSemiresRetta> lst = confService.getServSemiresRetta(bo);
			if (lst != null) {
				for (CsTbServSemiresRetta obj : lst) {
					lstRettaSemires.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstRettaSemires;
	}

	public void setLstRettaSemires(List<SelectItem> lstRettaSemires) {
		this.lstRettaSemires = lstRettaSemires;
	}

	@Override
	public boolean isComunitaMin() {
		return comunitaMin;
	}

	public void setComunitaMin(boolean comunitaMin) {
		this.comunitaMin = comunitaMin;
	}

	@Override
	public Long getIdComunitaTipo() {
		return idComunitaTipo;
	}

	public void setIdComunitaTipo(Long idComunitaTipo) {
		this.idComunitaTipo = idComunitaTipo;
	}

	@Override
	public List<SelectItem> getLstComunitaTipo() {
		
		if(lstComunitaTipo == null){
			lstComunitaTipo = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbServComunita> lst = confService.getServComunita(bo);
			if (lst != null) {
				for (CsTbServComunita obj : lst) {
					lstComunitaTipo.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstComunitaTipo;
	}

	public void setLstComunitaTipo(List<SelectItem> lstComunitaTipo) {
		this.lstComunitaTipo = lstComunitaTipo;
	}

	@Override
	public boolean isAffidoFam() {
		return affidoFam;
	}

	public void setAffidoFam(boolean affidoFam) {
		this.affidoFam = affidoFam;
	}

	@Override
	public boolean isComunitaTer() {
		return comunitaTer;
	}

	public void setComunitaTer(boolean comunitaTer) {
		this.comunitaTer = comunitaTer;
	}

	@Override
	public boolean isRSA() {
		return RSA;
	}

	public void setRSA(boolean rSA) {
		RSA = rSA;
	}

	@Override
	public String getNomeIndirizzoStrRes() {
		return nomeIndirizzoStrRes;
	}

	public void setNomeIndirizzoStrRes(String nomeIndirizzoStrRes) {
		this.nomeIndirizzoStrRes = nomeIndirizzoStrRes;
	}

	@Override
	public Long getIdLuogoStrRes() {
		return idLuogoStrRes;
	}

	public void setIdLuogoStrRes(Long idLuogoStrRes) {
		this.idLuogoStrRes = idLuogoStrRes;
	}

	@Override
	public Long getIdRettaRes() {
		return idRettaRes;
	}

	public void setIdRettaRes(Long idRettaRes) {
		this.idRettaRes = idRettaRes;
	}

	@Override
	public List<SelectItem> getLstRettaRes() {
		
		if(lstRettaRes == null){
			lstRettaRes = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbServResRetta> lst = confService.getServResRetta(bo);
			if (lst != null) {
				for (CsTbServResRetta obj : lst) {
					lstRettaRes.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstRettaRes;
	}

	public void setLstRettaRes(List<SelectItem> lstRettaRes) {
		this.lstRettaRes = lstRettaRes;
	}

	@Override
	public boolean isSostGenitore() {
		return sostGenitore;
	}

	public void setSostGenitore(boolean sostGenitore) {
		this.sostGenitore = sostGenitore;
	}

	@Override
	public boolean isTrasportoUfficio() {
		return trasportoUfficio;
	}

	public void setTrasportoUfficio(boolean trasportoUfficio) {
		this.trasportoUfficio = trasportoUfficio;
	}

	@Override
	public boolean isTrasportoSociale() {
		return trasportoSociale;
	}

	public void setTrasportoSociale(boolean trasportoSociale) {
		this.trasportoSociale = trasportoSociale;
	}

	@Override
	public String getEffettuaServizio() {
		return effettuaServizio;
	}

	public void setEffettuaServizio(String effettuaServizio) {
		this.effettuaServizio = effettuaServizio;
	}

	@Override
	public List<SelectItem> getLstLuogoStr() {
		
		if(lstLuogoStr == null){
			lstLuogoStr = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbServLuogoStr> lst = confService.getServLuogoStr(bo);
			if (lst != null) {
				for (CsTbServLuogoStr obj : lst) {
					lstLuogoStr.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstLuogoStr;
	}
	
	public void setLstLuogoStr(List<SelectItem> lstLuogoStr) {
		this.lstLuogoStr = lstLuogoStr;
	}

}
