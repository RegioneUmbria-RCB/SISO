package it.webred.cs.csa.web.manbean.fascicolo.docIndividuali;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiFascicolo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.interfaces.IDocIndividuali;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.DocIndividualeBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

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
	private List<DocIndividualeBean> listaDocIndividualiRichiestaServizio;  //SISO-438
	
	private int idxSelected;
	private String modalHeader;
	private boolean disableUpload;
	private CsDDocIndividuale docIndividuale;
	private DiarioDocsMan diarioDocsMan;
	
	AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) getCarSocialeEjb("AccessTableAlertSessionBean");
	AccessTableIterStepSessionBeanRemote iterStepService = (AccessTableIterStepSessionBeanRemote) getCarSocialeEjb("AccessTableIterStepSessionBean");
	
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
			
			boolean permessoDown = 
					checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_DOC_INDIVIDUALI_DOWN);
			
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
					DocIndividualeBean d = new DocIndividualeBean( docInd, permessoDown);
					if( d.getCsLoadDocumento() != null )
						listaDocIndividualiPubblica.add(d);
				}
				else if(dto.getUserId().equals(docInd.getCsDDiario().getUserIns())) 
				{
					DocIndividualeBean d = new DocIndividualeBean( docInd, permessoDown);
					if( d.getCsLoadDocumento() != null )
						listaDocIndividualiPrivata.add(d);
				}
			}
			

			//inizio SISO-438
			listaDocIndividualiRichiestaServizio = new ArrayList<DocIndividualeBean>();
			if (csASoggetto!=null && csASoggetto.getCsAAnagrafica()!=null) {  
				dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(csASoggetto.getCsAAnagrafica().getCf()); 
		    	CsOOperatoreSettore opSettore = CsUiCompBaseBean.getCurrentOpSettore();  
				dto.setObj2(opSettore.getCsOSettore().getCsOOrganizzazione().getId() );
				
				List<CsDDocIndividuale> listaCsDDoc = diarioService.findDocIndividualeByCfSchedaSegnalato(dto);
				 

				
				
				for(CsDDocIndividuale docInd: listaCsDDoc) {
					DocIndividualeBean d = new DocIndividualeBean( docInd , permessoDown);
					if( d.getCsLoadDocumento() != null) {
						listaDocIndividualiRichiestaServizio.add(d); 
					}	
				}
				
			}  
			//fine SISO-438
			
			
			diarioDocsMan = new DiarioDocsMan();
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	protected void initializeData(Object data) {
		this.initializeData();
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
	public String getDenominazioneOperatore(CsDDiario d) {
		
		try{
			String username = d.getUsrMod()!=null ? d.getUsrMod() : d.getUserIns();
			AmAnagrafica am  =  CsUiCompBaseBean.getAnagraficaByUsername(username);
			return am!=null ? am.getCognome()+" "+am.getNome() : "";
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		return "";
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
				BaseDTO casodto = new BaseDTO();
				fillEnte(casodto);
				casodto.setObj(idCaso);
				CsACasoOpeTipoOpe responsabile = casoService.findCasoOpeResponsabile(casodto);
				CsOOperatoreSettore opSettoreFrom = getCurrentOpSettore();
				
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
		        docIndividuale.getCsDDiario().setResponsabileCaso(responsabile!=null ? responsabile.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getId() : null);
		        docIndividuale.getCsDDiario().setCsOOperatoreSettore(opSettoreFrom);
		        
		        //Valorizzo le date Applicative
		        
		        docIndividuale.setLetto(false);
		        CsDDiario csd = diarioService.saveDocIndividuale(dto);
				
				//salvo il documento
				diarioDocsMan.getuFileMan().setIdDiario(csd.getId());
				for(CsLoadDocumento loadDoc: diarioDocsMan.getuFileMan().getDocumentiUploaded())
					diarioDocsMan.getuFileMan().salvaDocumento(loadDoc);

				
				//notifica al responsabile caso
				if(!docIndividuale.getPrivato()) {
					
					AlertDTO adto = new AlertDTO();
					fillEnte(adto);
					adto.setCaso(csACaso);
					
					//FROM
					adto.setOrganizzazioneFrom(opSettoreFrom.getCsOSettore().getCsOOrganizzazione());
					adto.setSettoreFrom(opSettoreFrom.getCsOSettore());
					adto.setOperatoreFrom(opSettoreFrom.getCsOOperatore());
					
					//TO
					CsOOperatoreSettore opSettResponsabile = null;
					if(responsabile != null && responsabile.getId()!=null) //Responsabile
						opSettResponsabile = responsabile.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore();
					else //Creatore
						opSettResponsabile = super.getOpSettoreCreatoreCaso();
					
					adto.setOpSettoreTo(opSettResponsabile);
					
					adto.setTipo(DataModelCostanti.TipiAlertCod.DOC_IND);
					String denominazione = csACaso.getCsASoggetto().getCsAAnagrafica().getCognome() 
							+ " " + csACaso.getCsASoggetto().getCsAAnagrafica().getNome()
							+ " (" + csACaso.getCsASoggetto().getCsAAnagrafica().getCf() + ")";
					String descrizione = "L'operatore "+
							         opSettoreFrom.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome()+" "+
							         opSettoreFrom.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()+ 
							         " ha inserito un nuovo documento individuale nel fascicolo del caso " + denominazione.toUpperCase();
					          
					String titDescrizione = "Notifica caso "+ denominazione + ": nuovo documento individuale";
					adto.setDescrizione(descrizione);
					adto.setTitolo(titDescrizione);
				
					alertService.addAlert(adto);
	
					//notifica al responsabile settore (a cui è segnalato il caso (se esiste) o a quello di provenienza.
					//to
					CsItStep itStep = iterStepService.getLastIterStepByCaso(itDto);
					adto.setOpSettoreTo(null);
					if(itStep.getCsOSettore2() != null) {
						adto.setOrganizzazioneTo(itStep.getCsOOrganizzazione2());
						adto.setSettoreTo(itStep.getCsOSettore2());
						alertService.addAlert(adto);
					} else if(itStep.getCsOSettore1() != null) {
						adto.setOrganizzazioneTo(itStep.getCsOOrganizzazione1());
						adto.setSettoreTo(itStep.getCsOSettore1());
						alertService.addAlert(adto);
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
		List<String> msg = new ArrayList<String>();
		List<CsLoadDocumento> lstDocs = diarioDocsMan.getuFileMan().getDocumentiUploaded();
		if(lstDocs.isEmpty()) {
			ok = false;
			msg.add("Aggiungere un documento");
		}else{
			//Verifico che siano stati correttamente caricati
			for(CsLoadDocumento cs : lstDocs){
				if(cs.getDocumento()==null){
					ok=false;
				    msg.add("Documento non caricato o formato non valido");
				 }
			}
		}
		
		if(docIndividuale.getDescrizione() == null || "".equals(docIndividuale.getDescrizione())) {
			ok = false;
			msg.add("Descrizione è un campo obbligatorio");
		}
		
		if(docIndividuale.getCsDDiario().getDtAmministrativa() == null) {
			ok = false;
			msg.add("Data è un campo obbligatorio");
		}
		
		if(docIndividuale.getCsTbSottocartellaDoc()==null){
			ok= false;
			msg.add("Tipo documento è un campo obbligatorio");
		}
		
		this.addWarning("Documenti Individuali", msg);
		
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
	
	public List<DocIndividualeBean> getListaDocIndividualiRichiestaServizio() {
		return listaDocIndividualiRichiestaServizio;
	}

	public void setListaDocIndividualiRichiestaServizio(
			List<DocIndividualeBean> listaDocIndividualiRichiestaServizio) {
		this.listaDocIndividualiRichiestaServizio = listaDocIndividualiRichiestaServizio;
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
