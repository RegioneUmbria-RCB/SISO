package it.webred.cs.csa.web.manbean.scheda.sociali;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbMotivoSegnal;
import it.webred.cs.data.model.CsTbProblematica;
import it.webred.cs.data.model.CsTbStesuraRelazioniPer;
import it.webred.cs.data.model.CsTbTipoContratto;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiSociali;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.data.model.SsSchedaSegnalato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

public class DatiSocialiComp extends SchedaValiditaCompUtils implements IDatiSociali{
	
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private String VER_MAX = "";
	private boolean abilitaInfoStranieri;
	private boolean renderProblematicaSogg;
	
	//private BigDecimal idTipologiaFam;
	//private Integer nMinori;
	private BigDecimal idInviante;
	private BigDecimal idInviatoA;
	private BigDecimal idProblematica;
	private BigDecimal idProblematicaNucleo;
	
	private BigDecimal idCaricoA;

	private boolean interventiNucleo;
	private String interventiTipo;
	private String autosufficienza;
	private String patologia;
	private String patologiaAltro;
	
	private BigDecimal idTipoContratto;

	private List<SelectItem> lstInviante;
	private List<SelectItem> lstInviatoA;
	private List<SelectItem> lstCaricoA;
	
	private List<SelectItem> lstProblematiche;
	private List<SelectItem> lstProblematicheNucleo;
	private List<SelectItem> lstStesuraRelPer;

	private List<SelectItem> lstAutosufficienza;
	private List<SelectItem> lstTipoContratto;
	
	private FormazioneLavoroMan formLavoroMan;
	
	private boolean stranieriRequired=false;
	
	private IStranieri  stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	/*Dati Tutela*/
	private ComponenteAltroMan sostegno;
	private ComponenteAltroMan curatela;
	private ComponenteAltroMan tutela;
	private boolean affServSociali=false;  
	
	public DatiSocialiComp(Long idSoggetto, Long idCaso){
		super();
		stranieriRequired=false;
		formLavoroMan = new FormazioneLavoroMan();
		sostegno = new ComponenteAltroMan(idSoggetto);
		curatela = new ComponenteAltroMan(idSoggetto);
		tutela = new ComponenteAltroMan(idSoggetto);
		
		try {
			stranieriMan  = StranieriManBaseBean.initByVersion(VER_MAX);
			abitazioneMan = AbitazioneManBaseBean.initByVersion(VER_MAX);
			famConviventiMan = FamiliariManBaseBean.initByVersion(VER_MAX);
			if(idCaso!=null){
				stranieriMan.setIdCaso(idCaso);
				abitazioneMan.setIdCaso(idCaso);
				famConviventiMan.setIdCaso(idCaso);
			}
	        
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public boolean isInterventiNucleo() {
		return interventiNucleo;
	}

	public void setInterventiNucleo(boolean interventiNucleo) {
		this.interventiNucleo = interventiNucleo;
	}

	@Override
	public String getInterventiTipo() {
		return interventiTipo;
	}

	public void setInterventiTipo(String interventiTipo) {
		this.interventiTipo = interventiTipo;
	}

	@Override
	public BigDecimal getIdInviante() {
		return idInviante;
	}

	public void setIdInviante(BigDecimal idInviante) {
		this.idInviante = idInviante;
	}

	@Override
	public BigDecimal getIdInviatoA() {
		return idInviatoA;
	}

	public void setIdInviatoA(BigDecimal idInviatoA) {
		this.idInviatoA = idInviatoA;
	}

	@Override
	public BigDecimal getIdProblematica() {
		return idProblematica;
	}

	public void setIdProblematica(BigDecimal idProblematica) {
		this.idProblematica = idProblematica;
	}


	public void setLstInviante(List<SelectItem> lstInviante) {
		this.lstInviante = lstInviante;
	}
	
	
	private List<SelectItem> loadListaSettoriFlag(boolean inCarico, boolean inviato, boolean inviante) {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		lista.add(new SelectItem(null, "- seleziona -"));
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<CsOSettore> lst = confService.getSettoreAll(bo);
		LinkedHashMap<String,List<SelectItem>> mappa = new LinkedHashMap<String,List<SelectItem>>();
		
		if (lst != null) {
			for (CsOSettore obj : lst) {
				String belfioreOrg = obj.getCsOOrganizzazione().getBelfiore();
				//boolean comuneValido = belfioreOrg==null || belfioreOrg.equals(bo.getEnteId());	
				boolean comuneValido = true;
				boolean aggiungi = 	
						(inCarico && obj.getFlgInCaricoA()!=null && obj.getFlgInCaricoA()) ||
						(inviato && obj.getFlgInviatoA()!=null && obj.getFlgInviatoA()) ||
						(inviante && obj.getFlgInviante()!=null && obj.getFlgInviante());
						
				if(comuneValido && aggiungi){
					String nomeOrg = obj.getCsOOrganizzazione().getNome();
					SelectItem si = new SelectItem(obj.getId(), obj.getNome());
					si.setDisabled(!obj.getAbilitato());
					List<SelectItem> gi = mappa.get(nomeOrg);
					if(gi==null)
						gi = new ArrayList<SelectItem>();
					
					gi.add(si);
					mappa.put(nomeOrg, gi);
				}
				
			}
			
			Iterator iter = mappa.keySet().iterator();
			while(iter.hasNext()){
				String s = (String) iter.next();
				mappa.get(s);
				SelectItemGroup g = new SelectItemGroup(s);
				List<SelectItem> lstIt = mappa.get(s);
				g.setSelectItems(lstIt.toArray(new SelectItem[lstIt.size()]));
				
				lista.add(g);
			}
		}		

		return lista;
	}
	
	
	@Override
	public List<SelectItem> getLstCaricoA() {
		if(lstCaricoA == null)
		   lstCaricoA = this.loadListaSettoriFlag(true, false, false);
		return lstCaricoA;
	}
	
	@Override
	public List<SelectItem> getLstInviatoA() {
		if(this.lstInviatoA == null)
			lstInviatoA = this.loadListaSettoriFlag(false, true, false);
		return lstInviatoA;
	}
	
	@Override
	public List<SelectItem> getLstInviante() {
		if(this.lstInviante == null)
			lstInviante = this.loadListaSettoriFlag(false, false, true);
		return lstInviante;
	}
	
	@Override
	public List<SelectItem> getLstProblematiche() {
		
		if(lstProblematiche == null){
			lstProblematiche = new ArrayList<SelectItem>();
			lstProblematiche.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbProblematica> lst = confService.getProblematiche(bo);
			if (lst != null) {
				for (CsTbProblematica obj : lst) {
					lstProblematiche.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstProblematiche;
	}

	public void setLstProblematiche(List<SelectItem> lstProblematiche) {
		this.lstProblematiche = lstProblematiche;
	}

	@Override
	public List<SelectItem> getLstStesuraRelPer() {
		
		if(lstStesuraRelPer == null){
			lstStesuraRelPer = new ArrayList<SelectItem>();
			lstStesuraRelPer.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbStesuraRelazioniPer> lst = confService.getStesuraRelazioniPer(bo);
			if (lst != null) {
				for (CsTbStesuraRelazioniPer obj : lst) {
					lstStesuraRelPer.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}
		}
		
		return lstStesuraRelPer;
	}

	public void setLstStesuraRelPer(List<SelectItem> lstStesuraRelPer) {
		this.lstStesuraRelPer = lstStesuraRelPer;
	}

	@Override
	public String getAutosufficienza() {
		return autosufficienza;
	}

	public void setAutosufficienza(String autosufficienza) {
		this.autosufficienza = autosufficienza;
	}

	@Override
	public BigDecimal getIdTipoContratto() {
		return idTipoContratto;
	}

	public void setIdTipoContratto(BigDecimal idTipoContratto) {
		this.idTipoContratto = idTipoContratto;
	}

	@Override
	public List<SelectItem> getLstAutosufficienza() {
		return lstAutosufficienza;
	}

	public void setLstAutosufficienza(List<SelectItem> lstAutosufficienza) {
		this.lstAutosufficienza = lstAutosufficienza;
	}

	@Override
	public List<SelectItem> getLstTipoContratto() {
		if(lstTipoContratto == null){
			lstTipoContratto = new ArrayList<SelectItem>();
			lstTipoContratto.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbTipoContratto> lst = confService.getTipoContratto(bo);
			if (lst != null) {
				for (CsTbTipoContratto obj : lst) {
					lstTipoContratto.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		return lstTipoContratto;
	}

	public void setLstTipoContratto(List<SelectItem> lstTipoContratto) {
		this.lstTipoContratto = lstTipoContratto;
	}
	
	public String getPatologia() {
		return patologia;
	}

	public void setPatologia(String patologia) {
		this.patologia = patologia;
	}

	public String getPatologiaAltro() {
		return patologiaAltro;
	}

	public void setPatologiaAltro(String patologiaAltro) {
		this.patologiaAltro = patologiaAltro;
	}

	public BigDecimal getIdCaricoA() {
		return idCaricoA;
	}

	public void setIdCaricoA(BigDecimal idCaricoA) {
		this.idCaricoA = idCaricoA;
	}

	public void setLstCaricoA(List<SelectItem> lstCaricoA) {
		this.lstCaricoA = lstCaricoA;
	}


	public void setLstInviatoA(List<SelectItem> lstInviatoA) {
		this.lstInviatoA = lstInviatoA;
	}

	@Override
	public BigDecimal getIdProblematicaNucleo() {
		return idProblematicaNucleo;
	}

	public void setIdProblematicaNucleo(BigDecimal idProblematicaNucleo) {
		this.idProblematicaNucleo = idProblematicaNucleo;
	}

	@Override
	public List<SelectItem> getLstProblematicheNucleo() {	
			if(lstProblematicheNucleo == null){
				lstProblematicheNucleo = new ArrayList<SelectItem>();
				lstProblematicheNucleo.add(new SelectItem(null, "- seleziona -"));
				CeTBaseObject bo = new CeTBaseObject();
				fillEnte(bo);
				List<CsTbMotivoSegnal> lst = confService.getMotivoSegnalazioni(bo);
				if (lst != null) {
					for (CsTbMotivoSegnal obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						si.setDisabled(si.isDisabled());
						lstProblematicheNucleo.add(si);
					}
				}		
			}
			
			return lstProblematicheNucleo;
		}

	public FormazioneLavoroMan getFormLavoroMan() {
		return formLavoroMan;
	}

	public void setFormLavoroMan(FormazioneLavoroMan formLavoroMan) {
		this.formLavoroMan = formLavoroMan;
	}

	public void valorizzaFormazioneLavoroDaCS(CsADatiSociali cs) {
		formLavoroMan.setIdProfessione(cs.getProfessioneId());
		formLavoroMan.setIdTitoloStudio(cs.getTitoloStudioId());
		formLavoroMan.setIdSettoreImpiego(cs.getSettImpiegoId());
		formLavoroMan.setIdCondLavorativa(cs.getCondLavorativaId());
	}
	
	public void valorizzaFormazioneLavoroDaUDC(SsSchedaSegnalato segnalato) {
		formLavoroMan.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
		formLavoroMan.setIdTitoloStudio(segnalato.getTitoloStudioId());
		formLavoroMan.setIdSettoreImpiego(segnalato.getSettImpiegoId());
		formLavoroMan.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
	}
	
	public void valorizzaFormazioneLavoroJpa(CsADatiSociali cs) {
		cs.setProfessioneId(nullZeroValue(formLavoroMan.getIdProfessione()));
		cs.setCondLavorativaId(nullZeroValue(formLavoroMan.getIdCondLavorativa()));
		cs.setSettImpiegoId(nullZeroValue(formLavoroMan.getIdSettoreImpiego()));
		cs.setTitoloStudioId(formLavoroMan.getIdTitoloStudio());
	}
	
	private BigDecimal nullZeroValue(BigDecimal b){
		if(b!=null && b.longValue()>0)
			return b;
		else
			return null;
	}

	public IStranieri getStranieriMan() {
		return stranieriMan;
	}

	public void setStranieriMan(IStranieri stranieriMan) {
		this.stranieriMan = stranieriMan;
	}

	public IAbitazione getAbitazioneMan() {
		return abitazioneMan;
	}

	public void setAbitazioneMan(IAbitazione abitazioneMan) {
		this.abitazioneMan = abitazioneMan;
	}

	public boolean isStranieriRequired() {
		return stranieriRequired;
	}

	public void setStranieriRequired(boolean stranieriRequired) {
		this.stranieriRequired = stranieriRequired;
	}

	public boolean isAbilitaInfoStranieri() {
		return abilitaInfoStranieri;
	}

	public void setAbilitaInfoStranieri(boolean abilitaInfoStranieri) {
		this.abilitaInfoStranieri = abilitaInfoStranieri;
	}
	
	public void onChangeCondLavoro(){}
	public void onChangeTitoloStudio(){}

	public IFamConviventi getFamConviventiMan() {
		return famConviventiMan;
	}

	public void setFamConviventiMan(IFamConviventi famConviventiMan) {
		this.famConviventiMan = famConviventiMan;
	}

	public AccessTableConfigurazioneSessionBeanRemote getConfService() {
		return confService;
	}

	@Override
	public ComponenteAltroMan getSostegno() {
		return sostegno;
	}

	@Override
	public ComponenteAltroMan getCuratela() {
		return curatela;
	}

	@Override
	public ComponenteAltroMan getTutela() {
		return tutela;
	}

	@Override
	public boolean isAffServSociali() {
		return affServSociali;
	}

	public void setConfService(AccessTableConfigurazioneSessionBeanRemote confService) {
		this.confService = confService;
	}

	public void setSostegno(ComponenteAltroMan sostegno) {
		this.sostegno = sostegno;
	}

	public void setCuratela(ComponenteAltroMan curatela) {
		this.curatela = curatela;
	}

	public void setTutela(ComponenteAltroMan tutela) {
		this.tutela = tutela;
	}

	public void setAffServSociali(boolean affServSociali) {
		this.affServSociali = affServSociali;
	}

	public boolean isRenderProblematicaSogg() {
		return renderProblematicaSogg;
	}

	public void setRenderProblematicaSogg(boolean renderProblematicaSogg) {
		this.renderProblematicaSogg = renderProblematicaSogg;
	}
	
}
