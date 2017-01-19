package it.webred.cs.csa.web.manbean.fascicolo.pai;

import it.webred.cs.csa.ejb.dto.*;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.*;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IPai;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

public class PaiBean extends FascicoloCompBaseBean implements IPai, Serializable {

	private static final long serialVersionUID = 6070604949953337370L;

	private static final String MESSAGES_ID = "messagesPai";

	private List<CsDPai> pais;
	private List<CsDPai> filteredPais;
	private List<SelectItem> lstTipoPai;
	private int idxSelected;
	private CsDPai selectedPai;
	private String modalHeader;
	private String widgetVar;
	private DualListModel<RelazioneDTO> picklistRelazioni;
	private DualListModel<DatiInterventoBean> picklistInterventi;
	private boolean onUpdateRelazioni=false;
	private boolean onUpdateInterventi=false;
	private boolean onClosing=false;
	//inizio evoluzione-pai
	private TipoInterventoManBean tipoInterventoManBean;
	private List<SelectItem> listaTipiIntervento;
	private InterventiBean interventiBean;
	private TabView tabview=null;
	//fine evoluzione-pai
	
	@Override
	public void initializeData() {
		try {
			onUpdateRelazioni=false;
			onUpdateInterventi=false;
			picklistRelazioni=null;
			picklistInterventi=null;
			onClosing=false;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			pais = diarioService.findPaiByCaso(dto);
			
			filteredPais = pais;
			selectedPai = null;

			//inizio evoluzione-pai  
			loadListaTipiIntervento();
			tipoInterventoManBean = new TipoInterventoManBean(
								  listTipoInterventos, getFilterCategorie()); 
			//fine evoluzione-pai
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}


	@Override
	public void nuovo() {
		picklistRelazioni=null;
		picklistInterventi=null;
		onUpdateRelazioni=false;
		onUpdateInterventi=false;
		onClosing=false;
		modalHeader = "Nuovo progetto";
		selectedPai = new CsDPai(); 
		//inizio evoluzione-pai   
		getPicklistRelazioni();
		getPicklistInterventi(); 
		//fine evoluzione-pai
	}

	@Override
	public void carica() {
		//per la modifica
		try {			
			picklistRelazioni=null;
			picklistInterventi=null;
			onUpdateRelazioni=false;
			onUpdateInterventi=false;
			onClosing=false;
			selectedPai = pais.get(idxSelected);

			//inizio evoluzione-pai   
			getPicklistRelazioni();
			getPicklistInterventi(); 
			//fine evoluzione-pai
			
			modalHeader = selectedPai.isClosed() ? "Progetto chiuso:" : "Modifica progetto:";
			modalHeader +="	["+selectedPai.getDiarioId()+"]";
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void caricaChiudi() {
		//chiudi();
		carica();
		modalHeader = "Chiusura progetto: ["+selectedPai.getDiarioId()+"]";
		onClosing=true;
	}

	@Override
	public void chiudi() {
		if (!validaChiusuraPai()) {
			return;
		}
		salva();
	}

	@Override
	public void salva() {

		if (!validaPai()) {
			return;
		}

		try {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			if (selectedPai.getDiarioId() != null) {

				dto.setObj(selectedPai);
				selectedPai.getCsDDiario().setUsrMod(dto.getUserId());
				selectedPai.getCsDDiario().setDtMod(new Date());

				diarioService.updatePai(dto);
				
			} else {

				dto.setObj(selectedPai);
				//trovo il caso 
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setIdCaso(csASoggetto.getCsACaso().getId());
				CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");

				CsACaso csa = new CsACaso();
				csa.setId(idCaso);

				CsTbTipoDiario cstd = new CsTbTipoDiario();
				cstd.setId(new Long(DataModelCostanti.TipoDiario.PAI_ID));

				selectedPai.getCsDDiario().setCsACaso(csa);
				selectedPai.getCsDDiario().setResponsabileCaso(this.getOperResponsabileCaso().getId());
				selectedPai.getCsDDiario().setCsTbTipoDiario(cstd);
				selectedPai.getCsDDiario().setDtIns(new Date());
				selectedPai.getCsDDiario().setUserIns(dto.getUserId());
				selectedPai.getCsDDiario().setCsOOperatoreSettore(opSettore);

				selectedPai = diarioService.savePai(dto);
			}

			this.updateInterventi();
			this.updateRelazioni();
			
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);

			initializeData();

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	private boolean validaPai() {
		boolean ok = true;
		List<String> errors = this.valida();
		for (String err : errors) {
			ok = false;
			addError(err, "");
		}
		return ok;
	}

	private boolean validaChiusuraPai() {
		boolean ok = true;
		List<String> errors = this.validaChiusura();
		for (String err : errors) {
			ok = false;
			addError(err, "");
		}
		return ok;
	}

	@Override
	public Long getIdCaso() {
		return idCaso;
	}

	@Override
	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	@Override
	public String getModalHeader() {
		return modalHeader;
	}

	@Override
	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	@Override
	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	@Override
	public CsDPai getSelectedPai() {
		return selectedPai;
	}

	@Override
	public void setSelectedPai(CsDPai pai) {
		this.selectedPai = pai;
	}

	@Override
	public List<CsDPai> getPais() {
		return this.pais;
	}

	public void setPais(List<CsDPai> pais) {
		this.pais = pais;
	}

	@Override
	public List<CsDPai> getFilteredPais() {
		return this.filteredPais;
	}

	@Override
	public void setFilteredPais(List<CsDPai> filteredPais) {
		this.filteredPais = filteredPais;
	}

	private List<SelectItem> generateListTipoPai() {
		lstTipoPai = new ArrayList<SelectItem>();
		lstTipoPai.add(new SelectItem(null, ""));
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<CsTbTipoPai> lst = configService.getTipoPai(bo);
		if (lst != null) {
			for (CsTbTipoPai obj : lst) {
				lstTipoPai.add(new SelectItem(obj.getId(),  obj.getDescrizione() , obj.getDescrizione()));
			}
		}

		return lstTipoPai;
	}

	@Override
	public SelectItem[] getLstTipoPai() {
		if (lstTipoPai == null || lstTipoPai.isEmpty()) {
			lstTipoPai = generateListTipoPai();
		}
		return lstTipoPai.toArray(new SelectItem[lstTipoPai.size()] );
	}

	
	@Override
	public List<SelectItem> getStatusOptions() {
		List<SelectItem> options = new ArrayList<SelectItem>();

		options.add(new SelectItem("", ""));
		options.add(new SelectItem("true", "sì"));
		options.add(new SelectItem("false", "no"));

		return options;
	}

	@Override
	public int getIdxSelected() {
		return this.idxSelected;
	}

	@Override
	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;

	}

	private List<String> validaChiusura() {
		ArrayList<String> errors = new ArrayList<String>();

		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviBreve()) && this.selectedPai.getRaggiuntiBreve() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a breve termine");
		}
		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviMedio()) && this.selectedPai.getRaggiuntiMedio() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a medio termine");
		}
		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviLungo()) && this.selectedPai.getRaggiuntiLungo() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a lungo termine");
		}

		if (StringUtils.isEmpty(this.selectedPai.getMotivoChiusura())) {
			errors.add("Indicare il motivo della chiusura");
		}
		else if(StringUtils.equalsIgnoreCase("Altro", this.selectedPai.getMotivoChiusura()) && StringUtils.isEmpty(this.selectedPai.getMotivoChiusuraSpec()) )
		{
			errors.add("Specificare la descrizione del motivo della chiusura (avendo indicato 'Altro')");
		}

		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() == null || 
				this.selectedPai.getCsDDiario().getDtChiusuraDa().equals(DataModelCostanti.END_DATE)	) {
			errors.add("Indicare la data di chiusura");
		} 			
		
		//verificare se ci sono interventi associati ancora aperti e che la relativa data di chiuera sia compresa nella data del progetto
		if(this.picklistInterventi!=null && this.picklistInterventi.getTarget()!=null)
		{
			boolean aperti = false;
			Date maxDataChiusuraFA = new Date(Long.MIN_VALUE);

			for (DatiInterventoBean i: this.picklistInterventi.getTarget())
			{
				for (CsFlgIntervento f : i.getListaFogli())
				{
					if (f.getCsDDiario().getDtChiusuraDa() == null) {
						aperti = true;
					}	
					if (!aperti) 
					{
						if (f.getCsDDiario().getDtChiusuraDa().after(maxDataChiusuraFA)) {
							maxDataChiusuraFA = f.getCsDDiario().getDtChiusuraDa();
						}
					}
				}
			}
			if (aperti) {
				errors.add("Ci sono interventi associati ancora aperti: è necessario chiuderli ovvero scollegarli dal progetto per poter chiudere il PAI");
			}
			else if (maxDataChiusuraFA.after(this.selectedPai.getCsDDiario().getDtChiusuraDa())) {
				errors.add("Ci sono interventi associati con fogli amministrativi la cui data di chiusura è successiva a quella di chiusura del progetto");
			}					
		}
				

		return errors;
	}

	public List<String> valida() {
		ArrayList<String> errors = new ArrayList<String>();

		if (this.selectedPai.getCsTbTipoPai() == null || this.selectedPai.getCsTbTipoPai().getId() == 0) {
			errors.add("Indicare il tipo di progetto");
		}

		if (this.selectedPai.getVerificaOgni() == null || StringUtils.isEmpty(this.selectedPai.getVerificaUnitaMisura())) {
			errors.add("Indicare la frequenza di verifica");
		}
		if (StringUtils.isEmpty(this.selectedPai.getObiettiviBreve()) && StringUtils.isEmpty(this.selectedPai.getObiettiviMedio())
				&& StringUtils.isEmpty(this.selectedPai.getObiettiviLungo())) {
			errors.add("Inserire almeno un obiettivo");
		}

		if (this.selectedPai.getCsDDiario().getDtAttivazioneDa() == null) {
			errors.add("Indicare la data di attivazione");
		}
		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() == null) {
			errors.add("Indicare la data di chiusura anche se presunta");
		}
		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() !=null &&  
				this.selectedPai.getCsDDiario().getDtChiusuraDa().before(this.selectedPai.getCsDDiario().getDtAttivazioneDa()))
		{
			errors.add("La data di chiusura deve essere successiva alla data di attivazione.");
		}
		if(this.pais.size()>1)
		{
			// verificare esistenza di altri progetti dello stesso tipo nello stesso periodo			
			CsTbTipoPai tipo = this.selectedPai.getCsTbTipoPai();
			Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
			Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();
			for (CsDPai p: this.pais)
			{
				if(p.getDiarioId().equals(this.selectedPai.getDiarioId()))
				{
					continue;
				}
				else if(tipo.getId() != p.getCsTbTipoPai().getId())
				{
					continue;
				}
				else
				{
					try {
						if(VerificaSovrapposizioneRangeDate(da, a, p.getCsDDiario().getDtAttivazioneDa(), p.getCsDDiario().getDtChiusuraDa()))
						{
							errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+p.getDiarioId()+").\n Nota: le data del progetto corrente si sovrappongono al periodo di apertura dell'altro progetto esistente");
						}
					} catch (Exception e) {						
						//errors.add(e.getLocalizedMessage());
						logger.error(e.getMessage(), e);
					}
//					if(between(a, p.getCsDDiario().getDtAttivazioneDa(), p.getCsDDiario().getDtChiusuraDa()) )
//					{
//						//ERROR: il progetto corrente finisce tra l'inizio e la fine di un altro progetto esistente
//						errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+p.getDiarioId()+").\n Nota: la data di chiusura del progetto corrente si sovrappone al periodo di apertura dell'altro progetto esistente");
//					}
//					if(between(da, p.getCsDDiario().getDtAttivazioneDa(), p.getCsDDiario().getDtChiusuraDa()) )
//					{
//						//ERROR: il progetto corrente inizia tra l'inizio e la fine di un altro progetto esistente
//						errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+p.getDiarioId()+").\n Nota: la data di attivazione del progetto corrente si sovrappone al periodo di apertura dell'altro progetto esistente");
//					}
				}
			}
		}
		
		//verificare coerenza delle date di tutte le entità (interventi, relazioni) collegate al progetto
		{
			Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
			Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();
			if(this.picklistInterventi!=null && this.picklistInterventi.getTarget()!=null)
			{
				for (DatiInterventoBean i: this.picklistInterventi.getTarget())
				{
					if(!between(i.getFineDa(), da, a) || 
						!between(i.getFineA(), da, a) ||
						!between(i.getInizioA(), da, a) ||
						!between(i.getInizioDa(), da, a) )
					{
						//ERROR: la durata del progetto corrente non comprende le date di inizio e fine di tutti gli interventi associati
						errors.add("Esiste un intervento associato al progetto (ID: "+i.getIdIntervento()+") le cui date di inizio e fine non sono comprese nella durata del progetto corrente.");
					}					
				}
			}
			if(this.picklistRelazioni!=null && this.picklistRelazioni.getTarget()!=null)
			{
				for (RelazioneDTO r: this.picklistRelazioni.getTarget())
				{
					Date dAmm = r.getRelazione().getCsDDiario().getDtAmministrativa();
					
					if (!between(dAmm, da, a) )
					{
						//ERROR: la durata del progetto corrente non comprende le date di tutte le relazioni associate
						errors.add("Esiste una relazione associata al progetto (ID: "+r.getRelazione().getDiarioId()+") la cui data non è compresa nella durata del progetto corrente.");
					}
				}
			}
		}		
		
		if (this.selectedPai.getDiarioId() == null) { // nuovo progetto
		}
		
		return errors;
	}

	//////////////////////////////////////////////////////////////////////////
	public List<RelazioneDTO> getLstRelazioni()
	{
		return getLstRelazioni(false);
	}
	public List<RelazioneDTO> getLstRelazioni(boolean onlyAssociatedToPai) {
		if(selectedPai==null) return null;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idCaso);
		List<RelazioneDTO> lst = diarioService.findRelazioniByCaso(dto);
		
		if (onlyAssociatedToPai)
		{						
			for (Iterator<RelazioneDTO> it=lst.iterator(); it.hasNext(); )
			{
				RelazioneDTO r=it.next();
				if(! r.isRelatedToPai(selectedPai))
				{
					it.remove();
				}
			}
		}
		
//		if (onlyAssociabiliToPai)
//		{
//			for (Iterator<RelazioneDTO> it=lst.iterator(); it.hasNext(); )
//			{
//				RelazioneDTO r=it.next();
//				if(r.getListaPaiDTO()!=null && !r.getListaPaiDTO().isEmpty()
//						&& !r.isRelatedToPai(selectedPai))
//				{
//					it.remove();
//				}
//			}
//		}
					
		return lst;
	}
	
	public List<DatiInterventoBean> getLstInterventi()
	{
		return getLstInterventi(false, true);
	}

	public List<DatiInterventoBean> getLstInterventi(boolean onlyAssociatedToPai, boolean onlyAssociabiliToPai) {

		List<DatiInterventoBean> lst = new ArrayList<DatiInterventoBean>();
		try {
			logger.info("PaiBean - Recupero lista Interventi");
			if (idCaso != null && idCaso > 0) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idCaso);
				List<CsIIntervento> lsti = interventoService.getListaInterventiByCaso(dto);
				
				if (onlyAssociatedToPai)
				{									
					for (Iterator<CsIIntervento> it=lsti.iterator(); it.hasNext(); )
					{						
						CsIIntervento interv=it.next();
						CsDPai pai = interv.getCsDPai();			
						if(!(pai!=null && selectedPai!=null && pai.getDiarioId().equals( selectedPai.getDiarioId())))
						{
							it.remove();
						}						
					}
				}
				
				if (onlyAssociabiliToPai)
				{
					for (Iterator<CsIIntervento> it=lsti.iterator(); it.hasNext(); )
					{						
						CsIIntervento interv=it.next();
						CsDPai pai = interv.getCsDPai();		
						if(pai!=null && selectedPai!=null && !pai.getDiarioId().equals( selectedPai.getDiarioId()))
						{
							it.remove();
						}
					}
				}
				
				for (CsIIntervento i : lsti) {
					DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto);
					lst.add(dib);
				}
			}

			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

		return lst;
	}

	public DualListModel<RelazioneDTO> getPicklistRelazioni() {
		if(picklistRelazioni==null)
		{			 
			List<RelazioneDTO> target = getLstRelazioni(true); 
			List<RelazioneDTO> source = getLstRelazioni();
			if(source!=null) source.removeAll(target);
			picklistRelazioni = new DualListModel<RelazioneDTO>(source, target); 
		}
			
		
		return picklistRelazioni;
    }

	public void setPicklistRelazioni(DualListModel<RelazioneDTO> picklistRelazioni) {
		this.picklistRelazioni = picklistRelazioni;
	}


	//INIZIO residuo-evoluzione-pai 
	public List<RelazioneDTO> getTargetRelazioneDTO() {
		return picklistRelazioni.getTarget();
	}

	public List<DatiInterventoBean> getTargetDatiInterventoBean() {
		return picklistInterventi.getTarget();
	}
	//FINEresiduo-evoluzione-pai 
	
	
	public DualListModel<DatiInterventoBean> getPicklistInterventi() {
		if(picklistInterventi==null)
		{			
			List<DatiInterventoBean> target = getLstInterventi(true,false);
			List<DatiInterventoBean> source = getLstInterventi();
			source.removeAll(target);
			picklistInterventi = new DualListModel<DatiInterventoBean>(source, target);
		}
			
		
		return picklistInterventi;
    }



	public void setPicklistInterventi(DualListModel<DatiInterventoBean> picklistInterventi) {
		this.picklistInterventi = picklistInterventi;
	}
	
	public Converter getPicklistRelazioniConverter()
	{
		return new Converter()
		{			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {			
				RelazioneDTO ret = null;
				
				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(Long.valueOf(submittedValue));
						ret = diarioService.findRelazioneFullById(dto);	
					}
					catch (Exception e) {
						e.printStackTrace();
					}				
				}
				
				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value!=null && value instanceof RelazioneDTO)
					return ((RelazioneDTO)value).getRelazione().getDiarioId().toString();
				else
					return "";
			}			
		};
	}
	
	public Converter getPicklistInterventiConverter()
	{
		return new Converter()
		{			

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {			
				DatiInterventoBean ret = null;
				
				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(Long.valueOf(submittedValue));
						CsIIntervento csiinterv  = interventoService.getInterventoById(dto);
						ret = new DatiInterventoBean(csiinterv, idSoggetto);							
					}
					catch (Exception e) {
						e.printStackTrace();
					}				
				}
				
				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value!=null && value instanceof DatiInterventoBean)
					return ((DatiInterventoBean)value).getIdIntervento().toString();
				else
					return "";
			}
			
		};
	}
		
	public boolean isOnUpdateRelazioni() {
		return onUpdateRelazioni;
	}

	public void setOnUpdateRelazioni(boolean onUpdateRelazioni) {
		this.onUpdateRelazioni = onUpdateRelazioni;
		this.tabview.setActiveIndex(1);
	}
	
	public boolean isOnUpdateInterventi() {
		return onUpdateInterventi;
	}

	public void setOnUpdateInterventi(boolean onUpdateInterventi) {
		this.onUpdateInterventi = onUpdateInterventi;
		this.tabview.setActiveIndex(0);
	}
	
	private void updateInterventi() throws Exception 
	{
		if(picklistInterventi!=null && selectedPai!=null)
		{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(picklistInterventi.getTarget()!=null)
			{
				for (DatiInterventoBean di : picklistInterventi.getTarget())
				{					
					dto.setObj(di.getIdIntervento());
					dto.setObj2(selectedPai);
					interventoService.salvaRifInterventoToPai(dto);
				}		
			}
			if(picklistInterventi.getSource()!=null)
			{
				for (DatiInterventoBean di : picklistInterventi.getSource())
				{									
					dto.setObj(di.getIdIntervento());
					dto.setObj2(null);
					dto.setObj3(selectedPai.getDiarioId());
					interventoService.salvaRifInterventoToPai(dto);
				}		
			}
		}				
	}
	
	private void updateRelazioni() throws Exception 
	{	
		if(picklistRelazioni!=null && selectedPai!=null)
		{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			if(picklistRelazioni.getTarget()!=null)
			{
				for (RelazioneDTO r : picklistRelazioni.getTarget())
				{								
					dto.setObj(r.getRelazione().getDiarioId());
					dto.setObj2(selectedPai.getDiarioId());
					diarioService.salvaRifRelazioneToPai(dto);
				}		
			}
			if(picklistRelazioni.getSource()!=null)
			{
				for (RelazioneDTO r : picklistRelazioni.getSource())
				{									
					dto.setObj(r.getRelazione().getDiarioId());
					dto.setObj2(null);
					dto.setObj3(selectedPai.getDiarioId());
					diarioService.salvaRifRelazioneToPai(dto);
				}		
			}
		}			
	}

	/**
	 * @return the onClosing
	 */
	public final boolean isOnClosing() {
		return onClosing;
	}

	/**
	 * @param onClosing the onClosing to set
	 */
	public final void setOnClosing(boolean onClosing) {
		this.onClosing = onClosing;
	}
	
	@Override
	protected final void addError(String summary, String descrizione) 
	{		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(MESSAGES_ID, message);	
	}
	
	private static boolean between(Date d, Date min, Date max)	
	{
		if(d==null) 
			return true;
		
		if(max!=null && min==null)
			return !d.after(max);
		if(min!=null && max==null)
			return !d.before(min);
		if(min!=null && max!=null)
			return (!d.before(min) && !d.after(max));
		
		return false;		
	}
	
	/// <summary>
	/// Verifica se due range di date si sovrappongono
	/// </summary>
	/// <param name="ev1Begin">inizio primo range</param>
	/// <param name="ev1End">fine primo range</param>
	/// <param name="ev2Begin">inizio secondo range</param>
	/// <param name="ev2End">fine secondo range</param>
	/// <returns>true se sovrapposti</returns>
	///
	// casi possibili:
	//1) ev2 contenuto in ev1
	//    ev1: +---------------+ 
	//    ev2:       +---+
	//2) ev1 contenuto in ev2 
	//    ev1:       +--------+
	//    ev2: +-----------------+
	//3) parzialmente contenuto, ev1 inizia prima di ev2
	//    ev1:   +--------+
	//    ev2:        +---------------+
	//4) parzialmente contenuto, ev1 inizia dopo ev2
	//    ev1:       +--------+
	//    ev2: +--------+
	//5) eventi esattamente sovrapposti
	//    ev1: +--------+
	//    ev2: +--------+
	//6) ev1 contenuto in ev2 con data inizio uguale
	//    ev1: +--------+
	//    ev2: +---------------+
	//7) ev1 contenuto in ev2 con data fine uguale
	//    ev1:      +---+
	//    ev2: +--------+
	public static boolean VerificaSovrapposizioneRangeDate(Date ev1Begin, Date ev1End, Date ev2Begin, Date ev2End) throws Exception
	{
	  if (ev1Begin==null || ev1End==null || ev2Begin==null || ev2End==null )
	  {
		  throw new Exception("Date null");
	  }
		  
	  if (ev1Begin.after(ev1End) || ev2Begin.after(ev2End))
	  {
	    //return true;  // se le date sono invertite indico come periodo sovrapposto
	    // in laternativa
	    throw new Exception("Date invertite");
	  }  
	 
	  if ((!ev1Begin.after(ev2Begin) && !ev1End.before(ev2End))  // 1 e 5
	    || (!ev1Begin.before(ev2Begin) && !ev1End.after(ev2End)) // 2, 6 e 7
	    || (ev1Begin.before(ev2Begin) && ev1End.after(ev2Begin)) // 3
	    || (ev1Begin.before(ev2End) && ev1End.after(ev2End))    // 4
	    )
	    return true;
	  else
	    return false;
	}
	

	 //inizio evoluzione-pai
	@Override
	public boolean isPaiDetailRendered() { 
		return selectedPai!=null;
	}


	public TipoInterventoManBean getTipoInterventoManBean() {
		return tipoInterventoManBean;
	}


	public void setTipoInterventoManBean(TipoInterventoManBean tipoInterventoManBean) {
		this.tipoInterventoManBean = tipoInterventoManBean;
	}

//	public List<SelectItem> getListaTipiIntervento()  {
//		
//		if(listaTipiIntervento == null) {
//			try{
//				
//				listaTipiIntervento = new ArrayList<SelectItem>();
//				InterventoDTO dto = new InterventoDTO();
//				fillEnte(dto);
//				dto.setLstIdCatSoc(this.getLstIdCatSoc());
//		        CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
//				dto.setIdSettore(opSettore.getCsOSettore().getId());
//				List<CsCTipoIntervento> lista = interventoService.findTipiInterventoSettoreCatSoc(dto);
//				for(CsCTipoIntervento tipoInt: lista) 
//					listaTipiIntervento.add(new SelectItem(tipoInt.getId() , tipoInt.getDescrizione()));
//			} catch (Exception e) {
//				addErrorFromProperties("caricamento.error");
//				logger.error(e.getMessage(),e);
//			}
//		}
//		
//		return listaTipiIntervento;
//	}
	

	@ManagedProperty(value="#{fascicoloBean}")
	private FascicoloBean fascicoloBean;

	
	public FascicoloBean getFascicoloBean() {
		return fascicoloBean;
	}

	public void setFascicoloBean(FascicoloBean fascicoloBean) {
		this.fascicoloBean = fascicoloBean;
	}
	
	public void refreshPicklistInterventi(CsIIntervento inter){
		if (picklistInterventi!=null) {		//è null quando il salvataggio viene richiamato dalla tab "Interventi"; è valorizzato quando viene richiamato nella tab PAI
			picklistInterventi.getTarget().add( new DatiInterventoBean(inter, idSoggetto) );
			//UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
			//UIComponent component = viewRoot.findComponent("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel");
			//System.out.println(component.getClientId());
			RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel"); 
			tabview.setActiveIndex(1);	
		}
	}
	
	
	public void refreshPicklistRelazioni(RelazioneDTO relazioneDto){
		if (picklistRelazioni!=null) {  //è null quando il salvataggio viene richiamato dalla tab "Attività professionali"; è valorizzato quando viene richiamato nella tab PAI
			picklistRelazioni.getTarget().add(relazioneDto);  
//			UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
//			UIComponent component = viewRoot.findComponent("fascicoloTab:paiForm:paiComponent:paitabview:dataTableRelazioni_panel");
//			System.out.println(component.getClientId()); 
			RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableRelazioni_panel");
		}
	}



	
	@Override
	public void aggiungiTipoInterventoButton() {
		

		FacesContext context = FacesContext.getCurrentInstance();
		fascicoloBean = (FascicoloBean) context.getApplication()
				.evaluateExpressionGet(context, "#{fascicoloBean}", FascicoloBean.class);
		
		boolean interventoSelezionato = fascicoloBean.getInterventiBean().inizializzaNuovoIntervento(tipoInterventoManBean);
		 

		if (!interventoSelezionato) {
			RequestContext.getCurrentInstance().addCallbackParam("interventoSelezionato", false);
		} else { 
			RequestContext.getCurrentInstance().addCallbackParam("interventoSelezionato", true);
		}
		 
		 
		
	}

	public TabView getTabview() {
		return tabview;
	}

	public void setTabview(TabView tabview) {
		this.tabview = tabview;
	}  
	

	private List<SelectItem> listTipoInterventos = null;
	private void loadListaTipiIntervento() {
		if (listTipoInterventos == null)
		{
			// filterCategorie="";
			listTipoInterventos = new LinkedList<SelectItem>();
			
			FglInterventoBean fglInterventoBean = (FglInterventoBean) getBean("fglInterventoBean");
			
			List<SelectItem> listTipoIntervento = fglInterventoBean.getLstTipoIntervento();
			for (SelectItem item : listTipoIntervento) {

				SelectItemGroup itemGroup = (SelectItemGroup) item;
				for (SelectItem selectItem : itemGroup.getSelectItems()) {

					String values = (String) selectItem.getValue();
					String vals[] = values.split("@");
					Long id = new Long(vals[1]);
					String label = (String) selectItem.getLabel();
					this.listTipoInterventos.add(new SelectItem(id, label));
				}
			}
		}
	}
	

	private String getFilterCategorie() {
		FglInterventoBean fglInterventoBean = (FglInterventoBean) getBean("fglInterventoBean");
		
		String filterCategorie = "";
		if (fglInterventoBean.getCatsocCorrenti() != null) {
			for (CsCCategoriaSocialeBASIC item : fglInterventoBean.getCatsocCorrenti()) {
				filterCategorie =filterCategorie+ item.getDescrizione() + ",";
			}
		}
		return filterCategorie;
	}
	
	//fine evoluzione-pai
}
