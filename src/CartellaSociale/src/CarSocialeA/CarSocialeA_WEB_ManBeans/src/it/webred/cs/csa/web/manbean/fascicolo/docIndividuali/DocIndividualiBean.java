package it.webred.cs.csa.web.manbean.fascicolo.docIndividuali;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.interfaces.IDocIndividuali;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.DocIndividualeBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class DocIndividualiBean extends FascicoloCompBaseBean implements IDocIndividuali {

	private List<CsTbSottocartellaDoc> listaSottocartelle;
	private List<DocIndividualeBean> listaDocIndividualiPubblica;
	private List<DocIndividualeBean> listaDocIndividualiPrivata;
	private int idxSelected;
	private String modalHeader;
	private boolean disableUpload;
	private CsDDocIndividuale docIndividuale;
	private DiarioDocsMan diarioDocsMan;
	
	AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
	AccessTableIterStepSessionBeanRemote iterStepService = (AccessTableIterStepSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
	
	@PostConstruct
	public void init() {
		
		if(csASoggetto == null)
			csASoggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		
		if(csASoggetto != null)
			this.initialize(csASoggetto);
	}
	
	public void initializeDocIndividuali(CsASoggettoLAZY soggetto) {

		if(soggetto != null) {
			getSession().setAttribute("soggetto", soggetto);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("docIndividuali.faces");
			} catch (IOException e) {
				addError("Errore", "Errore durante il reindirizzamento ai Documenti Individuali");
			}
		}
		else
			addWarningFromProperties("seleziona.warning");
	}
	
	@Override
	public void initializeData() {
		
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			listaSottocartelle = new ArrayList<CsTbSottocartellaDoc>();
			listaSottocartelle = configService.getTipoCartelle(dto);
			
			
			dto.setObj(idCaso);
			listaDocIndividualiPubblica = new ArrayList<DocIndividualeBean>();
			listaDocIndividualiPrivata = new ArrayList<DocIndividualeBean>();
			
			List<CsDDocIndividuale> lista = diarioService.findDocIndividualiByCaso(dto);
		
			for(CsDDocIndividuale docInd: lista) {
				if(docInd.getPrivato() == null || !docInd.getPrivato()) 
				{
					DocIndividualeBean d = new DocIndividualeBean( docInd );
					if( d.getCsLoadDocumento() != null )
						listaDocIndividualiPubblica.add(d);
				}
				else if(dto.getUserId().equals(docInd.getCsDDiario().getUserIns())) 
				{
					DocIndividualeBean d = new DocIndividualeBean( docInd );
					if( d.getCsLoadDocumento() != null )
						listaDocIndividualiPrivata.add(d);
				}
			}
			
			
			
			
			diarioDocsMan = new DiarioDocsMan();
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void nuovo() {
		
		modalHeader = "Nuovo Documento Individuale";
		docIndividuale = new CsDDocIndividuale();
		docIndividuale.setPrivato(false);
		
		diarioDocsMan = new DiarioDocsMan();
		diarioDocsMan.getuFileMan().setExternalSave(true);
		disableUpload = false;
	}
	
	@Override
	public void caricaPubblico() {
		
		modalHeader = "Modifica Documento Individuale";
		docIndividuale = listaDocIndividualiPubblica.get(idxSelected).getCsDDocIndividuale();
		disableUpload = true;
		
	}
	
	@Override
	public void caricaPrivato() {
		
		modalHeader = "Modifica Documento Individuale";
		docIndividuale = listaDocIndividualiPrivata.get(idxSelected).getCsDDocIndividuale();
		disableUpload = true;
		
	}
	
	@Override
	public void indicaLettura() {
		
		try{
			
			docIndividuale = listaDocIndividualiPubblica.get(idxSelected).getCsDDocIndividuale();
			docIndividuale.setLetto(true);
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(docIndividuale);
			diarioService.updateDocIndividuale(dto);
			
			initializeData();
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void salva() {
		
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(docIndividuale);
			
			if(docIndividuale.getDiarioId() != null) {
				
				//modifica
				docIndividuale.getCsDDiario().setUsrMod(dto.getUserId());
				docIndividuale.getCsDDiario().setDtMod(new Date());
				diarioService.updateDocIndividuale(dto);
				
			} else {
				
				//nuovo
				if(!validaDocIndividuale())
					return;
				
				CsOOperatoreBASIC opeResponsabileCaso = super.getOperResponsabileCaso();
				CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
				
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setIdCaso(idCaso);
				CsACaso csACaso = casoService.findCasoById(itDto);
				
		        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
		        cstd.setId(new Long(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID)); 
		        
		        docIndividuale.getCsDDiario().setCsACaso(csACaso);
		        docIndividuale.getCsDDiario().setCsTbTipoDiario(cstd);
		        docIndividuale.getCsDDiario().setDtIns(new Date());
		        docIndividuale.getCsDDiario().setUserIns(dto.getUserId());
		        docIndividuale.getCsDDiario().setResponsabileCaso(opeResponsabileCaso.getId());
		        docIndividuale.getCsDDiario().setCsOOperatoreSettore(opSettore);
		        
		        //Valorizzo le date Applicative
		        
		        docIndividuale.setLetto(false);
		      
		        CsDDiario csd = diarioService.saveDocIndividuale(dto);
				
				//salvo il documento
				diarioDocsMan.getuFileMan().setIdDiario(csd.getId());
				for(CsLoadDocumento loadDoc: diarioDocsMan.getuFileMan().getDocumentiUploaded())
					diarioDocsMan.getuFileMan().salvaDocumento(loadDoc);

				
				//notifica al responsabile caso
				if(!docIndividuale.getPrivato()) {
					
					//from
					itDto.setIdOrganizzazione(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
					itDto.setIdSettore(opSettore.getCsOSettore().getId());
					itDto.setNomeOperatore(itDto.getUserId());
					//to
					
					if(opeResponsabileCaso != null && opeResponsabileCaso.getId()!=null) {
						//Responsabile
						itDto.setIdOpTo(opeResponsabileCaso.getId());
					}else{
						//creatore
						CsOOperatore operCreatore = super.getOperCreatoreCaso();
						itDto.setIdOpTo(operCreatore.getId());
					}
					
					itDto.setTipo(DataModelCostanti.TipiAlert.DOC_IND);
					itDto.setLabelTipo(DataModelCostanti.TipiAlert.DOC_IND_DESC);
					String denominazione = csACaso.getCsASoggetto().getCsAAnagrafica().getCognome() 
							+ " " + csACaso.getCsASoggetto().getCsAAnagrafica().getNome()
							+ " (" + csACaso.getCsASoggetto().getCsAAnagrafica().getCf() + ")";
					String descrizione = "Il caso " + denominazione.toUpperCase() + " ha un nuovo Documento Individuale";
					itDto.setDescrizione(descrizione);
					itDto.setTitoloDescrizione(descrizione);
					alertService.addAlert(itDto);
	
					//notifica al responsabile settore
					//to
					CsIterStepByCasoDTO itStep = iterStepService.getLastIterStepByCasoDTO(itDto);
					itDto.setIdOpTo(null);
					if(itStep.getCsItStep().getCsOSettore2() != null) {
						itDto.setIdOrgTo(itStep.getCsItStep().getCsOOrganizzazione2().getId());
						itDto.setIdSettTo(itStep.getCsItStep().getCsOSettore2().getId());
						alertService.addAlert(itDto);
					} else if(itStep.getCsItStep().getCsOSettore1() != null) {
						itDto.setIdOrgTo(itStep.getCsItStep().getCsOOrganizzazione1().getId());
						itDto.setIdSettTo(itStep.getCsItStep().getCsOSettore1().getId());
						alertService.addAlert(itDto);
					}
					
				}

			}
			
			initializeData();
			
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	@Override
	public void elimina() {
		
		try {
		
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(docIndividuale);
			diarioService.deleteDocIndividuale(dto);
		
			initializeData();
			
			addInfoFromProperties("elimina.ok");
		
		} catch (Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private boolean validaDocIndividuale() {
		
		boolean ok = true;
		
		List<CsLoadDocumento> lstDocs = diarioDocsMan.getuFileMan().getDocumentiUploaded();
		if(lstDocs.isEmpty()) {
			ok = false;
			addError("Aggiungere un documento", "");
		}else{
			//Verifico che siano stati correttamente caricati
			for(CsLoadDocumento cs : lstDocs){
				if(cs.getDocumento()==null){
					ok=false;
				    addError("Documento non caricato o formato non valido", "");
				 }
			}
		}
		
		if(docIndividuale.getDescrizione() == null || "".equals(docIndividuale.getDescrizione())) {
			ok = false;
			addError("Descrizione è un campo obbligatorio", "");
		}
		
		if(docIndividuale.getCsDDiario().getDtAmministrativa() == null) {
			ok = false;
			addError("Data è un campo obbligatorio", "");
		}
		
		if(docIndividuale.getCsTbSottocartellaDoc()==null){
			ok= false;
			addError("Tipo documento è un campo obbligatorio","");
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
	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	@Override
	public CsDDocIndividuale getDocIndividuale() {
		return docIndividuale;
	}

	public void setDocIndividuale(CsDDocIndividuale docIndividuale) {
		this.docIndividuale = docIndividuale;
	}

	@Override
	public DiarioDocsMan getDiarioDocsMan() {
		return diarioDocsMan;
	}

	public void setDiarioDocsMan(DiarioDocsMan diarioDocsMan) {
		this.diarioDocsMan = diarioDocsMan;
	}

	@Override
	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public List<DocIndividualeBean> getListaDocIndividualiPubblica() {
		return listaDocIndividualiPubblica;
	}

	public void setListaDocIndividualiPubblica(List<DocIndividualeBean> listaDocIndividualiPubblica) {
		this.listaDocIndividualiPubblica = listaDocIndividualiPubblica;
	}

	@Override
	public List<DocIndividualeBean> getListaDocIndividualiPrivata() {
		return listaDocIndividualiPrivata;
	}

	public void setListaDocIndividualiPrivata(List<DocIndividualeBean> listaDocIndividualiPrivata) {
		this.listaDocIndividualiPrivata = listaDocIndividualiPrivata;
	}

	@Override
	public boolean isDisableUpload() {
		return disableUpload;
	}

	public void setDisableUpload(boolean disableUpload) {
		this.disableUpload = disableUpload;
	}

	
	public List<CsTbSottocartellaDoc> getListaSottocartelle() {
		return listaSottocartelle;
	}
	
	@Override
	public List<SelectItem> getListaSottocartelleItem() {
		List<SelectItem> sil = new ArrayList<SelectItem>();
		for(CsTbSottocartellaDoc c : listaSottocartelle){
			SelectItem si = new SelectItem();
			si.setLabel(c.getDescrizione());
			si.setValue(c.getId());
			si.setDisabled(!c.getAbilitato());
			sil.add(si);
		}
		return sil;
	}

	public Long getIdSottocartella() {
		return this.docIndividuale.getCsTbSottocartellaDoc()!=null ? docIndividuale.getCsTbSottocartellaDoc().getId() : null;
	}

	public void setIdSottocartella(Long idSottocartella) {
		if(this.listaSottocartelle!=null){
			int i = 0; boolean trovato=false;
			while(i<listaSottocartelle.size() && !trovato){
				CsTbSottocartellaDoc c = listaSottocartelle.get(i);
				if(c.getId().equals(idSottocartella)){
					this.docIndividuale.setCsTbSottocartellaDoc(c);
					trovato=true;
				}
				i++;
			}
		}
	}
	
}
