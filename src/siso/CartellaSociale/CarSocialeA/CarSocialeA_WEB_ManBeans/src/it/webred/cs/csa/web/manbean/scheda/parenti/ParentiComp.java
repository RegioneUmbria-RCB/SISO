package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.cartella.RisorsaCalcDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsTbContatto;
import it.webred.cs.data.model.CsTbDisponibilita;
import it.webred.cs.data.model.CsTbPotesta;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IParenti;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;

public class ParentiComp extends ValiditaCompBaseBean implements IParenti {

	private List<CsAComponente> lstParenti = new ArrayList<CsAComponente>();
	private List<CsAComponente> lstConoscenti = new ArrayList<CsAComponente>();

	private List<SelectItem> lstComponentiCalc;
	
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");
	
	private int idxSelected;
	private boolean nuovo;
	private String viveSolo;
	private String haParenti;
	private String parentiSconosciuti;
	
	private Integer disponibilitaDatiAnagraficiRisorse;

	private boolean showNewParente = false;
	private boolean showNewConoscente = false;
	private NuovoParenteBean nuovoParenteBean = new NuovoParenteBean();
	private NuovoConoscenteBean nuovoConoscenteBean = new NuovoConoscenteBean();
	
	private Long anagraficaCompDaAltraScheda_id = null;

	
	// SISO-724 mappa CF - stato cartella
	private HashMap<String, String> cf2StatoCartella = new HashMap<String, String>();
	
	public ParentiComp(Date dataInizio, Date dataFine){
		super();
		setDataInizio(dataInizio);
		setDataFine(dataFine);
		loadLstComponentiCalc(dataInizio, dataFine);
	}
	
	@Override
	public void salvaNuovoParente() {

		CsAComponente comp = null;
		CsAAnagrafica compAna = null;
		CsTbTipoRapportoCon compTipoRapporto = null;
		boolean esisteCF = false;
		if (nuovo) {
			comp = new CsAComponente();
			compAna = new CsAAnagrafica();
			compTipoRapporto = new CsTbTipoRapportoCon();
			
			//Verifico che il CF non sia già presente
			int i = 0;
			if(nuovoParenteBean.getCodFiscale()!=null && !nuovoParenteBean.getCodFiscale().isEmpty()){
				while(i<lstParenti.size() && !esisteCF){
					String cf = ((CsAComponente)lstParenti.get(i)).getCsAAnagrafica().getCf();
					if(cf!=null && !cf.isEmpty() && nuovoParenteBean.getCodFiscale().toUpperCase().equals(cf.toUpperCase())){
						esisteCF = true;
						this.addError("Il soggetto con cod.fiscale '"+nuovoParenteBean.getCodFiscale()+"' è già presente", "");
						return;
					}
					i++; //BUG FIX MOD-RL 2016-10-21
				}
			}
			
		} else {
			comp = lstParenti.get(idxSelected);
			compAna = comp.getCsAAnagrafica();
			compTipoRapporto = comp.getCsTbTipoRapportoCon();
		}

		compAna.setIdOrigWs(nuovoParenteBean.getIdOrigWs());
		compAna.setCf(nuovoParenteBean.getCodFiscale());
		compAna.setCognome(nuovoParenteBean.getCognome());
		comp.setDtIns(new Date());
		comp.setIndirizzoRes(nuovoParenteBean.getIndirizzo());
		compAna.setNome(nuovoParenteBean.getNome());
		comp.setNumCivRes(nuovoParenteBean.getCivico());
		compAna.setSesso(nuovoParenteBean.getDatiSesso().getSesso());
		comp.setUserIns(getCurrentUsername());
		compAna.setCell(nuovoParenteBean.getCellulare());
		compAna.setCittadinanza(nuovoParenteBean.getCittadinanza());
		comp.setConvivente(nuovoParenteBean.getConvivente());
		comp.setAffidatario(nuovoParenteBean.getAffidatario());//SISO-906
		CsTbContatto contatto = new CsTbContatto();
		contatto.setId(nuovoParenteBean.getIdContatto());
		comp.setCsTbContatto(contatto);
		comp.setCsTbDisponibilita(null);
		Long idDisponibilita = nuovoParenteBean.getIdDisponibilita();
		if (idDisponibilita != null && idDisponibilita.intValue() != 0) {
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(idDisponibilita);
			CsTbDisponibilita disponibilita = confService.getDisponibilitaById(b);
			comp.setCsTbDisponibilita(disponibilita);
		}
		compTipoRapporto = nuovoParenteBean.getRapportoModel();
		comp.setCsTbTipoRapportoCon(compTipoRapporto);
		CsTbPotesta potesta = new CsTbPotesta();
		potesta.setId(nuovoParenteBean.getIdPotesta());
		comp.setCsTbPotesta(potesta);
		
		comp.setProfessioneId(nuovoParenteBean.getFormLavoroMan().getIdProfessione());
		comp.setCondLavorativaId(nuovoParenteBean.getFormLavoroMan().getIdCondLavorativa());
		
		compAna.setDataDecesso(nuovoParenteBean.getDataDecesso());
		nuovoParenteBean.setDecesso(compAna.getDataDecesso() != null);
		compAna.setDataNascita(nuovoParenteBean.getDataNascita());
		compAna.setEmail(nuovoParenteBean.getEmail());
		comp.setIndirizzoRes(nuovoParenteBean.getIndirizzo());
		compAna.setNote(nuovoParenteBean.getNote());
		comp.setNumCivRes(nuovoParenteBean.getCivico());
		compAna.setTel(nuovoParenteBean.getTelefono());
		
		valorizzaLuogoNascitaJPA(compAna, nuovoParenteBean.getComuneNazioneNascitaBean());
	
		valorizzaLuogoResidenzaJPS(comp, nuovoParenteBean.getComuneNazioneResidenzaBean());
		
		comp.setDisabile(nuovoParenteBean.getDisabile());
		
		comp.setCsAAnagrafica(compAna);

		if (nuovo && !esisteCF)
			lstParenti.add(comp);

		setShowNewParente(false);

	}

	@Override
	public String calcoloEta(Date dateN, Date dataDecesso) {
		String eta = "-";
		if (dateN != null) {
			long today = System.currentTimeMillis();
			Date dataToday = dataDecesso != null ? dataDecesso
					: new Date(today);
			long age = dataToday.getTime() - dateN.getTime();
			int days = (int) Math.round(age / 86400000.0);
			int ageFinal = (days / 365);
			eta = dataDecesso != null ? "Deceduto a " + Integer.toString(ageFinal) + " anni" : Integer.toString(ageFinal);
		}
		return eta;
	}

	private void valorizzaLuogoResidenzaJPS(CsAComponente comp, ComuneNazioneResidenzaBean resMan){
		comp.setComResCod(null);
		comp.setComResDes(null);
		comp.setProvResCod(null);
		comp.setComAltroCod(null);
		comp.setComAltroDes(null);
		
		if (resMan.isComune() &&resMan.getComuneMan().getComune()!=null) {
			ComuneBean comRes = resMan.getComuneMan().getComune();
			comp.setComResCod(comRes.getCodIstatComune());
			comp.setComResDes(comRes.getDenominazione());
			comp.setProvResCod(comRes.getSiglaProv());
		} 
		if (resMan.isNazione() && resMan.getNazioneMan().getNazione() != null) {
			AmTabNazioni nazRes = resMan.getNazioneMan().getNazione();
			comp.setComAltroCod(nazRes.getCodIstatNazione());
			comp.setComAltroDes(nazRes.getNazione());
		} 
		
	}
	
	private void valorizzaLuogoNascitaJPA(CsAAnagrafica compAna, ComuneNazioneNascitaBean nascitaMan){
		
		/*resetto tutti i campi prima di valorizzare correttamente*/
		compAna.setComuneNascitaCod(null);
		compAna.setComuneNascitaDes(null);
		compAna.setProvNascitaCod(null);
		compAna.setStatoNascitaCod(null);
		compAna.setStatoNascitaDes(null);
		
		if (nascitaMan.isComune() && nascitaMan.getComuneMan().getComune()!=null) {
			ComuneBean comNasc = nascitaMan.getComuneMan().getComune();
			compAna.setComuneNascitaCod(comNasc.getCodIstatComune());
			compAna.setComuneNascitaDes(comNasc.getDenominazione());
			compAna.setProvNascitaCod(comNasc.getSiglaProv());
		}
		
		if (nascitaMan.isNazione() && nascitaMan.getNazioneMan().getNazione()!=null) {
			AmTabNazioni nazNasc = nascitaMan.getNazioneMan().getNazione();
			compAna.setStatoNascitaCod(nazNasc.getCodIstatNazione());
			compAna.setStatoNascitaDes(nazNasc.getNazione());
		}
	}
	
	@Override
	public void salvaNuovoConoscente() {

		CsAComponente comp = null;
		CsAAnagrafica compAna = null;
		CsTbTipoRapportoCon compTipoRapporto = null;
		if (nuovo) {
			comp = new CsAComponente();
			compAna = new CsAAnagrafica();
			compTipoRapporto = new CsTbTipoRapportoCon();
		} else {
			comp = lstConoscenti.get(idxSelected);
			compAna = comp.getCsAAnagrafica();
			compTipoRapporto = comp.getCsTbTipoRapportoCon();
		}

		compAna.setIdOrigWs(nuovoConoscenteBean.getIdOrigWs());
		compAna.setCognome(nuovoConoscenteBean.getCognome());
		compAna.setNome(nuovoConoscenteBean.getNome());
		compAna.setCf(nuovoConoscenteBean.getCodFiscale());
		compAna.setSesso(nuovoConoscenteBean.getDatiSesso().getSesso());
		compAna.setDataNascita(nuovoConoscenteBean.getDataNascita());
		
		valorizzaLuogoNascitaJPA(compAna, nuovoConoscenteBean.getComuneNazioneNascitaBean());
		
		compAna.setTel(nuovoConoscenteBean.getTelefono());
		compAna.setCell(nuovoConoscenteBean.getCellulare());
		compAna.setEmail(nuovoConoscenteBean.getEmail());
		compAna.setNote(nuovoConoscenteBean.getNote());
		
		
		comp.setDtIns(new Date());
		comp.setIndirizzoRes(nuovoConoscenteBean.getIndirizzo());
		comp.setNumCivRes(nuovoConoscenteBean.getCivico());
		comp.setUserIns(getCurrentUsername());
		compTipoRapporto = nuovoConoscenteBean.getRapportoModel();
		comp.setCsTbTipoRapportoCon(compTipoRapporto);
		comp.setConvivente(nuovoConoscenteBean.getConvivente());
		comp.setAffidatario(nuovoConoscenteBean.getAffidatario());//SISO-906
		comp.setIndirizzoRes(nuovoConoscenteBean.getIndirizzo());
		
		comp.setNumCivRes(nuovoConoscenteBean.getCivico());
		
		valorizzaLuogoResidenzaJPS(comp, nuovoConoscenteBean.getComuneNazioneResidenzaBean());
		
		comp.setCsAAnagrafica(compAna);

		
		
		if (nuovo)
			lstConoscenti.add(comp);

		setShowNewConoscente(false);

	}
	
	private void loadModificaAnagrafica(CsAComponente from, AnagraficaNucleoBean to){
		
		to.setCognome(from.getCsAAnagrafica().getCognome());
		to.setNome(from.getCsAAnagrafica().getNome());
		to.setCodFiscale(from.getCsAAnagrafica().getCf());
		to.setDatiSesso(new SessoBean(from.getCsAAnagrafica().getSesso()));
		to.setDataNascita(from.getCsAAnagrafica().getDataNascita());
		
		to.setTelefono(from.getCsAAnagrafica().getTel());
		to.setCellulare(from.getCsAAnagrafica().getCell());
		to.setEmail(from.getCsAAnagrafica().getEmail());
				
		to.setIndirizzo(from.getIndirizzoRes());
		to.setCivico(from.getNumCivRes());
		
		ComuneBean comuneRes = new ComuneBean(from.getComResCod(),from.getComResDes(), from.getProvResCod());
		to.getComuneNazioneResidenzaBean().getComuneMan().setComune(comuneRes);
		to.getComuneNazioneResidenzaBean().setComuneValue();
		if (from.getComAltroCod() != null) {
			AmTabNazioni amTabNazioni = new AmTabNazioni();
			amTabNazioni.setCodIstatNazione(from.getComAltroCod());
			amTabNazioni.setNazione(from.getComAltroDes());
			to.getComuneNazioneResidenzaBean().setNazioneValue();
			to.getComuneNazioneResidenzaBean().getNazioneMan().setNazione(amTabNazioni);
		}
		
		to.setNote(from.getCsAAnagrafica().getNote());
		
		ComuneBean comuneNas = new ComuneBean(from.getCsAAnagrafica()
				.getComuneNascitaCod(), from.getCsAAnagrafica()
				.getComuneNascitaDes(), from.getCsAAnagrafica()
				.getProvNascitaCod());
		to.getComuneNazioneNascitaBean().setComuneValue();
		to.getComuneNazioneNascitaBean().getComuneMan().setComune(comuneNas);
		if (from.getCsAAnagrafica().getStatoNascitaCod() != null) {
			AmTabNazioni amTabNazioni = new AmTabNazioni();
			amTabNazioni.setCodIstatNazione(from.getCsAAnagrafica().getStatoNascitaCod());
			amTabNazioni.setNazione(from.getCsAAnagrafica().getStatoNascitaDes());
			to.getComuneNazioneNascitaBean().setNazioneValue();
			to.getComuneNazioneNascitaBean().getNazioneMan().setNazione(amTabNazioni);
		}
		
		if (from.getCsTbTipoRapportoCon() != null)
			to.setIdParentela(from.getCsTbTipoRapportoCon().getId());
		
	}

	@Override
	public void loadModificaParente() {
		CsAComponente comp = lstParenti.get(idxSelected);
		this.loadModificaAnagrafica(comp, nuovoParenteBean);
		nuovoParenteBean.setCittadinanza(comp.getCsAAnagrafica().getCittadinanza());
		nuovoParenteBean.setConvivente(comp.getConvivente());
		nuovoParenteBean.setAffidatario(comp.getAffidatario());//SISO-906
		nuovoParenteBean.setDataDecesso(comp.getCsAAnagrafica().getDataDecesso());
		nuovoParenteBean.setDecesso(comp.getCsAAnagrafica().getDataDecesso() != null);
		nuovoParenteBean.setDisabile(comp.getDisabile());
		
		if (comp.getCsTbContatto() != null)
			nuovoParenteBean.setIdContatto(comp.getCsTbContatto().getId());
		if (comp.getCsTbDisponibilita() != null)
			nuovoParenteBean.setIdDisponibilita(comp.getCsTbDisponibilita().getId());
		if (comp.getCsTbPotesta() != null)
			nuovoParenteBean.setIdPotesta(comp.getCsTbPotesta().getId());

		nuovoParenteBean.getFormLavoroMan().setIdCondLavorativa(comp.getCondLavorativaId());
		nuovoParenteBean.getFormLavoroMan().setIdProfessione(comp.getProfessioneId());
		
	}

	@Override
	public void loadModificaConoscente() {
		CsAComponente comp = lstConoscenti.get(idxSelected);
		this.loadModificaAnagrafica(comp, nuovoConoscenteBean);
	}

	@Override
	public void eliminaParente() {
		lstParenti.remove(idxSelected);
	}

	@Override
	public void eliminaConoscente() {
		lstConoscenti.remove(idxSelected);
	}

	public void setLstComponentiCalc(List<SelectItem> lstComponentiCalc) {
		this.lstComponentiCalc = lstComponentiCalc;
	}

	public List<SelectItem> getLstComponentiCalc() {
		return this.lstComponentiCalc;
	}
	
	
	public void nuovoDaAnagrafeWrapper(String idSoggetto){
		//TODO recupero anagrafica del soggetto
		BaseDTO dto=new BaseDTO();
		fillEnte(dto);
		dto.setObj(idSoggetto);
		CsASoggettoLAZY soggettoTrovato=soggettoService.getSoggettoById(dto);
		if(soggettoTrovato!=null && soggettoTrovato.getAnagraficaId()>0)
		{
			CsAAnagrafica anagraficaSoggettoTrovato=soggettoTrovato.getCsAAnagrafica();
			nuovoParenteBean.precaricaParenteDaComponente(anagraficaSoggettoTrovato);
			this.nuovo = true;
			this.showNewParente = true;
			
		}
		
	}
	
	private void loadLstComponentiCalc(Date dataDa, Date dataA) {
		
		CsASoggettoLAZY soggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(soggetto.getCsAAnagrafica().getCf());
		dto.setObj2(dataDa);
		dto.setObj3(dataA);
		List<RisorsaCalcDTO> lstComponenti = schedaService.findComponentiGiaFamigliariBySoggettoCf(dto);
		
		lstComponentiCalc = new ArrayList<SelectItem>();
		HashMap<RisorsaCalcDTO,List<SelectItem>> Schede=new HashMap<RisorsaCalcDTO, List<SelectItem>>();
		if(lstComponenti!=null)
			{
			for (RisorsaCalcDTO rCurr : lstComponenti) {
				if(rCurr.getSoggetto()!=null && rCurr.getSoggetto().getCsACaso()!=null)
				{
					Schede.put(rCurr, new ArrayList<SelectItem>());
				}
			}
			for (RisorsaCalcDTO rCurr : lstComponenti) {
				SelectItem sel=new SelectItem();
				String denominazione = rCurr.getCognome()+" "+rCurr.getNome();
				String intervallo = !StringUtils.isBlank(rCurr.getDateValidita()) ? " "+rCurr.getDateValidita() : "";
				sel.setLabel(denominazione+intervallo);
				sel.setValue(rCurr.getAnagraficaId());
				for (RisorsaCalcDTO soggettoCurr : Schede.keySet()) {
					CsAComponente componenteCurr = rCurr.getComponente();
					Long idFamGruppoComponenteCurr = componenteCurr!=null && componenteCurr.getCsAFamigliaGruppo()!=null ? componenteCurr.getCsAFamigliaGruppo().getId() : null;
					if(idFamGruppoComponenteCurr!=null && famigliaGrupposContainsFamigliaById(soggettoCurr.getFamiglieSoggettoIds(), idFamGruppoComponenteCurr))
					{
						Schede.get(soggettoCurr).add(sel);
					}
					else if(rCurr.getSoggetto()!=null && rCurr.getSoggetto()==soggettoCurr.getSoggetto())
					{
						//è il soggetto titolare della scheda
						Schede.get(soggettoCurr).add(sel);
					}
				}
				
			}
				for (RisorsaCalcDTO soggettoGruppo : Schede.keySet()) {
					Long identificativoCartella = soggettoGruppo.getSoggetto().getCsACaso().getIdentificativo();
					SelectItemGroup gruppoNew=new SelectItemGroup("Identificativo cartella: "+identificativoCartella);
					SelectItem[] itemsDelGruppo=new SelectItem[Schede.get(soggettoGruppo).size()];
					int i=0;
					for (SelectItem selectItem : Schede.get(soggettoGruppo)) {
						if(selectItem.getValue().equals(soggettoGruppo.getAnagraficaId()))
						{
							selectItem.setLabel(selectItem.getLabel()+" (titolare)");
						}
						itemsDelGruppo[i]=selectItem;
						i++;
					}
					gruppoNew.setSelectItems(itemsDelGruppo);
					lstComponentiCalc.add(gruppoNew);
				}
			}
	}

	private boolean famigliaGrupposContainsFamigliaById(List<Long> famIds, Long famigliaCurrId)
	{
		boolean result=false;
		if(famIds!=null){
			for (Long id : famIds) {
				if(id.equals(famigliaCurrId))
				{
					result=true;
				}
			}
		}
		
		return result;
	}
	@Override
	public List<CsAComponente> getLstParenti() {
		return lstParenti;

	}

	public void setLstParenti(List<CsAComponente> lstParenti) {
		this.lstParenti = lstParenti;
	}

	@Override
	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	public NuovoParenteBean getNuovoParenteBean() {
		return nuovoParenteBean;
	}

	public void setNuovoParenteBean(NuovoParenteBean nuovoParenteBean) {
		this.nuovoParenteBean = nuovoParenteBean;
	}

	@Override
	public List<CsAComponente> getLstConoscenti() {
		return lstConoscenti;
	}

	public void setLstConoscenti(List<CsAComponente> lstConoscenti) {
		this.lstConoscenti = lstConoscenti;
	}

	public NuovoConoscenteBean getNuovoConoscenteBean() {
		return nuovoConoscenteBean;
	}

	public void setNuovoConoscenteBean(NuovoConoscenteBean nuovoConoscenteBean) {
		this.nuovoConoscenteBean = nuovoConoscenteBean;
	}

	@Override
	public boolean isNuovo() {
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}

	public boolean isShowNewParente() {
		return showNewParente;
	}

	public void setShowNewParente(boolean showNewParente) {
		this.showNewParente = showNewParente;
	}

	public boolean isShowNewConoscente() {
		return showNewConoscente;
	}

	public void setShowNewConoscente(boolean showNewConoscente) {
		this.showNewConoscente = showNewConoscente;
	}

	public Long getAnagraficaCompDaAltraScheda_id() {
		return this.anagraficaCompDaAltraScheda_id;
	}

	public void setAnagraficaCompDaAltraScheda_id(Long anagraficaIdSel) {
		this.anagraficaCompDaAltraScheda_id=anagraficaIdSel;
	}
	
	@Override
	public void onSelectComponenteDaAltraScheda(){
		CsAAnagrafica anagraficaSel=null;
		if(anagraficaCompDaAltraScheda_id!=null && anagraficaCompDaAltraScheda_id>0){
			BaseDTO dto=new BaseDTO();
			fillEnte(dto);
			dto.setObj(anagraficaCompDaAltraScheda_id);
			anagraficaSel= soggettoService.getAnagraficaById(dto);
		}
		nuovoParenteBean.reset();
		if (anagraficaSel != null && anagraficaSel.getId() > 0) {
			this.nuovo = true;
			this.showNewParente = true;
			nuovoParenteBean.precaricaParenteDaComponente(anagraficaSel);
		} else {
			this.nuovo = false;
			this.showNewParente = false;
		}
		anagraficaCompDaAltraScheda_id = null;
	}
	
	@Override
	public boolean loadStatoRdC(CsAComponente c) {
		boolean val = false;
		if(!StringUtils.isBlank(c.getCsAAnagrafica().getCf())) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(c.getCsAAnagrafica().getCf());
			val = this.soggettoService.isBeneficiarioRdC(dto);
		}
		return val;
	}
	
	@Override
	public String loadStatoCartella(String cf){
		boolean exists = cf2StatoCartella.containsKey(cf);
		String s = null;
		
		if(!exists){
			s = super.loadStatoCartella(cf);
			cf2StatoCartella.put(cf, s);
		}else{
			s = cf2StatoCartella.get(cf);	
		}
		
		if(s!=null && !s.isEmpty())
			return "<b>Cartella Sociale esistente</b>"+ s;
		else return s;
	}
	
	@Override
	public Boolean isHaParenti() {
		Boolean val = null;
		if(this.haParenti!=null) 
			val = this.haParenti.equalsIgnoreCase("true") ? true : false;
		return val;
	}

	@Override
	public Boolean isParentiSconosciuti() {
		Boolean val = null;
		if(this.parentiSconosciuti!=null) 
			val = this.parentiSconosciuti.equalsIgnoreCase("true") ? true : false;
		return val;
	}

	@Override
	public Boolean isViveSolo() {
		Boolean val = null;
		if(this.viveSolo!=null) 
			val = this.viveSolo.equalsIgnoreCase("true") ? true : false;
		return val;
	}

	public Integer getDisponibilitaDatiAnagraficiRisorse() {
		return disponibilitaDatiAnagraficiRisorse;
	}

	public void setDisponibilitaDatiAnagraficiRisorse(
			Integer disponibilitaDatiAnagraficiRisorse) {
		this.disponibilitaDatiAnagraficiRisorse = disponibilitaDatiAnagraficiRisorse;
	}
	
	public String getViveSolo() {
		return viveSolo;
	}

	public void setViveSolo(String viveSolo) {
		this.viveSolo = viveSolo;
	}

	public String getHaParenti() {
		return haParenti;
	}

	public void setHaParenti(String haParenti) {
		this.haParenti = haParenti;
	}

	public String getParentiSconosciuti() {
		return parentiSconosciuti;
	}

	public void setParentiSconosciuti(String parentiSconosciuti) {
		this.parentiSconosciuti = parentiSconosciuti;
	}

}
