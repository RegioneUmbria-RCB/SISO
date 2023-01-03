package it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.data.model.ArRelIntCustomIstat;
import it.webred.cs.data.model.ArTClasse;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VServiziCustom;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "tipoInterventoManBean")
@ViewScoped
public class TipoInterventoManBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 8156132885250536156L;

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
			"AccessTableInterventoSessionBean");

	private TreeNode root;
	private TreeNode selectedNode;
    private InformativaDTO tooltip;
    private InformativaDTO tooltipCustom;

	private String recenteString;
	private List<VTipiInterventoUsati> lstRecenti;

	private String newTipoIntCustom;
	private Long selTipoInterventoId;
	private String selTipoInterventoName;
	
	private KeyValueDTO selTipoInterventoCustom;
	
	private Long selCatSocialeId;

	private CsCTipoInterventoCustom curSelCsCTipoInterventoCustom;
	private List<CsCTipoInterventoCustom> tipoInterventosCustom;
	private List<SelectItem> listaTipoInterventoCustom;
	
	//SISO-1110 Inizio
	private List<SelectItem> listaTipoIntervento1Custom;
	private List<SelectItem> listaTipoIntervento2Custom;
	private List<SelectItem> listaTipoIntervento3Custom;
	private List<SelectItem> listaTipoIntervento4Custom;
	private KeyValueDTO selTipoInterventoCustom1;
	private KeyValueDTO selTipoInterventoCustom2;
	private KeyValueDTO selTipoInterventoCustom3;
	private KeyValueDTO selTipoInterventoCustom4;
	//AREA TARGET
	private List<SelectItem> listaAreaTargetCustom;
	private KeyValueDTO selAreaTargetCustom;
	private List<VServiziCustom> areasTarget = null;
    //SERVIZI INPS
	private List<SelectItem> listaServiziINPS;
	private KeyValueDTO selServizioINPS;
	private List<VServiziCustom> servizisINPS = null;
	private String descrizioneServizioINPS;
	private VServiziCustom curSelServizioInps;
	//CATEGORIE SOCIALI ASSOCIATE AL SOGGETTO
	private String categorieSocialiSoggetto;
	private List<String> lstIdAreas = null;
	private List<VServiziCustom> listaTipoInterventoPerAree ;
	private List<VServiziCustom> interventiPerAreaT = null;
	//ISTAT
	private List<SelectItem> listaTipoInterventoISTAT;
	private KeyValueDTO selInterventoISTAT;
	private List<ArRelIntCustomIstat> interventisISTAT = null;
	private List<ArTClasse> interISTAT = null;
	private String selIstatDescrizione2 ;
	private boolean visibleDescrizione = false;
	private String labelLivello1 = null;
	private String labelLivello2 = null;
	private String labelLivello3 = null;
	private String labelLivello4 = null;
	private String categorieSociali = "";
	
	public TipoInterventoManBean(){}
	
	//INIZIO SISO-1110	
	public List<SelectItem> getListaTipoIntervento1Custom() {
		listaTipoIntervento1Custom = new ArrayList<SelectItem>();
			if(this.tipoInterventosCustom!=null){
				for (CsCTipoInterventoCustom i : this.getTipoInterventosCustom()) {
					if (i.getCodice_1() == null && !i.getCodiceMemo().contains("INOLTRO") )// per escludere gli interventi del Segreatariato
					{
						SelectItem si = new SelectItem(i.getId().toString(), i.getDescrizione());
						si.setDisabled(!"1".equals(i.getAbilitato()));						
						listaTipoIntervento1Custom.add(si);
					}
				}
			}
			
			return this.listaTipoIntervento1Custom;
		}	
	
	public List<SelectItem> getListaAreaTargetCustom() {
		listaAreaTargetCustom = new ArrayList<SelectItem>();
		if (this.areasTarget != null)		{
						
			for (VServiziCustom sC : this.areasTarget) {
				SelectItem areaTarget = new SelectItem(Integer.toString(sC.getAreatId()), sC.getAreatDescrizione());
				listaAreaTargetCustom.add(areaTarget);
				}
			}
		return listaAreaTargetCustom;
	}
	
	public List<SelectItem> getListaServiziINPS()	 {
		listaServiziINPS = new ArrayList<SelectItem>();
		if (this.servizisINPS != null)		{
						
			for (VServiziCustom serInps : this.servizisINPS) {
				SelectItem servizioInps = new SelectItem(serInps.getInpsCodice(), serInps.getInpsDenonimazione());
				listaServiziINPS.add(servizioInps);
				}
			}
		return listaServiziINPS;
	}

	public List<SelectItem> getListaTipoInterventoISTAT() {		
		listaTipoInterventoISTAT = new ArrayList<SelectItem>();
		if (this.interISTAT != null)		{
						
			for (ArTClasse intervISTAT : this.interISTAT) {
				SelectItem intISTAT = new SelectItem(intervISTAT.getId(), intervISTAT.getDescrizione());
				listaTipoInterventoISTAT.add(intISTAT);
				}
			}
		
		return listaTipoInterventoISTAT;
	}
	
	
	//FINE SISO-1110
	
	public void setListaTipoIntervento1Custom(List<SelectItem> listaTipoIntervento1Custom) {
		this.listaTipoIntervento1Custom = listaTipoIntervento1Custom;
	}

	public List<SelectItem> getListaTipoIntervento2Custom() {
		return listaTipoIntervento2Custom;
	}

	public void setListaTipoIntervento2Custom(
			List<SelectItem> listaTipoIntervento2Custom) {
		this.listaTipoIntervento2Custom = listaTipoIntervento2Custom;
	}

	public List<SelectItem> getListaTipoIntervento3Custom() {
		return listaTipoIntervento3Custom;
	}

	public void setListaTipoIntervento3Custom(
			List<SelectItem> listaTipoIntervento3Custom) {
		this.listaTipoIntervento3Custom = listaTipoIntervento3Custom;
	}

	public List<SelectItem> getListaTipoIntervento4Custom() {
		return listaTipoIntervento4Custom;
	}

	public void setListaTipoIntervento4Custom(
			List<SelectItem> listaTipoIntervento4Custom) {
		this.listaTipoIntervento4Custom = listaTipoIntervento4Custom;
	}

	public KeyValueDTO getSelTipoInterventoCustom1() {
		return selTipoInterventoCustom1;
	}

	public void setSelTipoInterventoCustom1(KeyValueDTO selTipoInterventoCustom1) {
		this.selTipoInterventoCustom1 = selTipoInterventoCustom1;
	}

	public KeyValueDTO getSelTipoInterventoCustom2() {
		return selTipoInterventoCustom2;
	}

	public void setSelTipoInterventoCustom2(KeyValueDTO selTipoInterventoCustom2) {
		this.selTipoInterventoCustom2 = selTipoInterventoCustom2;
	}

	public KeyValueDTO getSelTipoInterventoCustom3() {
		return selTipoInterventoCustom3;
	}

	public void setSelTipoInterventoCustom3(KeyValueDTO selTipoInterventoCustom3) {
		this.selTipoInterventoCustom3 = selTipoInterventoCustom3;
	}

	public KeyValueDTO getSelTipoInterventoCustom4() {
		return selTipoInterventoCustom4;
	}

	public void setSelTipoInterventoCustom4(KeyValueDTO selTipoInterventoCustom4) {
		this.selTipoInterventoCustom4 = selTipoInterventoCustom4;
	}
		
	public void setListaAreaTargetCustom(List<SelectItem> listaAreaTargetCustom) {
		this.listaAreaTargetCustom = listaAreaTargetCustom;
	}

	public KeyValueDTO getSelAreaTargetCustom() {
		return selAreaTargetCustom;
	}

	public void setSelAreaTargetCustom(KeyValueDTO selAreaTargetCustom) {
		this.selAreaTargetCustom = selAreaTargetCustom;
	}
	
	public void setListaServiziINPS(List<SelectItem> listaServiziINPS) {
		this.listaServiziINPS = listaServiziINPS;
	}

	public KeyValueDTO getSelServizioINPS() {
		return selServizioINPS;
	}

	public void setSelServizioINPS(KeyValueDTO selServizioINPS) {
		this.selServizioINPS = selServizioINPS;
	}

	
	public String getDescrizioneServizioINPS() {
		return descrizioneServizioINPS;
	}

	public void setDescrizioneServizioINPS(String descrizioneServizioINPS) {
		this.descrizioneServizioINPS = descrizioneServizioINPS;
	}

    
	public VServiziCustom getCurSelServizioInps() {
		return curSelServizioInps;
	}

	public void setCurSelServizioInps(VServiziCustom curSelServizioInps) {
		this.curSelServizioInps = curSelServizioInps;
	}

	public String getCategorieSocialiSoggetto() {
		return categorieSocialiSoggetto;
	}

	public void setCategorieSocialiSoggetto(String categorieSocialiSoggetto) {
		this.categorieSocialiSoggetto = categorieSocialiSoggetto;
	}
	

	public List<String> getLstIdAreas() {
		return lstIdAreas;
	}

	public void setLstIdAreas(List<String> lstIdAreas) {
		this.lstIdAreas = lstIdAreas;
	}


	public List<VServiziCustom> getListaTipoInterventoPerAree() {
		return listaTipoInterventoPerAree;
	}

	public void setListaTipoInterventoPerAree(
			List<VServiziCustom> listaTipoInterventoPerAree) {
		this.listaTipoInterventoPerAree = listaTipoInterventoPerAree;
	}

		public void setListaTipoInterventoISTAT(
			List<SelectItem> listaTipoInterventoISTAT) {
		this.listaTipoInterventoISTAT = listaTipoInterventoISTAT;
	}

	public KeyValueDTO getSelInterventoISTAT() {
		return selInterventoISTAT;
	}

	public void setSelInterventoISTAT(KeyValueDTO selInterventoISTAT) {
		this.selInterventoISTAT = selInterventoISTAT;
	}
	public String getSelIstatDescrizione2() {
		return selIstatDescrizione2;
	}

	public void setSelIstatDescrizione2(String selIstatDescrizione2) {
		this.selIstatDescrizione2 = selIstatDescrizione2;
	}
	
	public InformativaDTO getTooltipCustom() {
		return tooltipCustom;
	}

	public void setTooltipCustom(InformativaDTO tooltipCustom) {
		this.tooltipCustom = tooltipCustom;
	}

	public boolean isVisibleDescrizione() {
		return visibleDescrizione;
	}

	public void setVisibleDescrizione(boolean visibleDescrizione) {
		this.visibleDescrizione = visibleDescrizione;
	}
	
	public String getTitoloNomenclatore() {
		return super.getTitoloNomenclatoreTipoIntervento();
	}

	public String getLabelLivello1() {
		return labelLivello1;
	}

	public void setLabelLivello1(String labelLivello1) {
		this.labelLivello1 = labelLivello1;
	}

	public String getLabelLivello2() {
		return labelLivello2;
	}

	public void setLabelLivello2(String labelLivello2) {
		this.labelLivello2 = labelLivello2;
	}

	public String getLabelLivello3() {
		return labelLivello3;
	}

	public void setLabelLivello3(String labelLivello3) {
		this.labelLivello3 = labelLivello3;
	}

	public String getLabelLivello4() {
		return labelLivello4;
	}

	public void setLabelLivello4(String labelLivello4) {
		this.labelLivello4 = labelLivello4;
	}
	public String getCategorieSociali() {
		return categorieSociali;
	}

	public void setCategorieSociali(String categorieSociali) {
		this.categorieSociali = categorieSociali;
	}
	//SISO-1110 Fine
	

	private List<CsCTipoInterventoCustom> newTipoInterventosCustom;

	@ManagedProperty("#{treeController}")
	private TreeController treeController;

	public TipoInterventoManBean(List<SelectItem> lstTipoInterventi, String filteredNodes) {
		if (isTreeViewTipoIntervento()) {
			treeController = new TreeController(lstTipoInterventi,filteredNodes);
			root = treeController.getRoot();
			selTipoInterventoCustom=new KeyValueDTO();
		} else {
			
			this.resetCustomIstat();
            this.setCategorieSociali(filteredNodes);		
         	initPannello(filteredNodes);
			loadTipoInterventoCustom(false);
		}
	}

	// @PostConstruct
	public void init() {

	}

	public void setTreeController(TreeController service) {
		this.treeController = service;
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<CsCTipoInterventoCustom> getTipoInterventosCustom() {
		return tipoInterventosCustom;
	}

	public void setTipoInterventosCustom(List<CsCTipoInterventoCustom> tipoInterventosCustom) {
		this.tipoInterventosCustom = tipoInterventosCustom;
	}

	public String getNewTipoIntCustom() {
		return newTipoIntCustom;
	}

	public void setNewTipoIntCustom(String newTipoIntCustom) {
		this.newTipoIntCustom = newTipoIntCustom;
	}

	public Long getSelTipoInterventoId() {
		return selTipoInterventoId;
	}

	public CsCTipoInterventoCustom getCurSelCsCTipoInterventoCustom() {
		return curSelCsCTipoInterventoCustom;
	}

	public void setCurSelCsCTipoInterventoCustom(CsCTipoInterventoCustom curSelCsCTipoInterventoCustom) {
		this.curSelCsCTipoInterventoCustom = curSelCsCTipoInterventoCustom;
	}

	public Long getSelTipoInterventoCutomId() {
		if (getCurSelCsCTipoInterventoCustom() == null)
			return 0L;
		else
			return getCurSelCsCTipoInterventoCustom().getId();
	}

	public String getSelTipoInterventoName() {
		return selTipoInterventoName;
	}

	public void setSelTipoInterventoName(String selTipoInterventoName) {
		this.selTipoInterventoName = selTipoInterventoName;
	}

	public void setSelTipoInterventoCustom(KeyValueDTO selTipoInterventoCustom) {
		this.selTipoInterventoCustom = selTipoInterventoCustom;
	}

	public Long getSelCatSocialeId() {
		return selCatSocialeId;
	}

	public String getRecenteString() {
		return recenteString;
	}

	public void setRecenteString(String recenteString) {
		this.recenteString = recenteString;
	}

	// **PRIVATE //
	private void loadTipoInterventoCustom(boolean reload) {
		if (tipoInterventosCustom == null || reload) {
			tipoInterventosCustom = new LinkedList<CsCTipoInterventoCustom>();
			CeTBaseObject dto = new CeTBaseObject();
			fillEnte(dto);
			tipoInterventosCustom = confService.findTipiIntCustom(dto);
		}
	}

	public void onChangeCmbxIntervCustom(AjaxBehaviorEvent e) {
		String nomeIntervento = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		if(nomeIntervento!=null && !nomeIntervento.isEmpty()){
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId().toString().equalsIgnoreCase(nomeIntervento)) {
					this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
					this.curSelCsCTipoInterventoCustom = intervento;
					//treeController.setSelectedNode(getSelTipoInterventoId());
					this.recenteString = null;
					return;
				}
			}
		}else this.selTipoInterventoCustom = new KeyValueDTO();
	}
	//SISO-1110 Inizio
	public void onChangeCmbx1IntervCustom(AjaxBehaviorEvent e) {
		this.curSelCsCTipoInterventoCustom = null;
		this.selCatSocialeId = null;
		this.areasTarget =null;
		this.interISTAT= null;
		this.tooltipCustom =null;
		String nomeIntervento = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		
		listaTipoIntervento2Custom = new ArrayList<SelectItem>();
		listaTipoIntervento3Custom = new ArrayList<SelectItem>();
		listaTipoIntervento4Custom = new ArrayList<SelectItem>();	
		listaAreaTargetCustom  = new ArrayList<SelectItem>();
		
		this.selTipoInterventoCustom2=new KeyValueDTO();
		this.selTipoInterventoCustom3=new KeyValueDTO();
		this.selTipoInterventoCustom4=new KeyValueDTO();
		listaTipoInterventoISTAT = new ArrayList<SelectItem>();
		this.selAreaTargetCustom=new KeyValueDTO(); 
		this.selInterventoISTAT=new KeyValueDTO(); 
		this.selTipoInterventoCustom = new KeyValueDTO();
		this.selTipoInterventoName = null;
		
		if(nomeIntervento!=null && !nomeIntervento.isEmpty()){
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId().toString().equalsIgnoreCase(selTipoInterventoCustom1.getCodice())) {
					this.selTipoInterventoCustom1 = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
				    this.curSelCsCTipoInterventoCustom = intervento;
				    this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
				}
				if (intervento.getCodice_1() != null && intervento.getCodice_1().toString().equalsIgnoreCase(nomeIntervento) && intervento.getCodice_2() == null && intervento.getCodice_3() == null && intervento.getCodice_4() == null ) {
					SelectItem si = new SelectItem(intervento.getId().toString(), intervento.getDescrizione());
					si.setDisabled(!"1".equals(intervento.getAbilitato()));
					listaTipoIntervento2Custom.add(si); 					
				}
				
			}
			if (this.selTipoInterventoCustom1.getCodice() != null && listaTipoIntervento2Custom.size() <= 0)
			{
				//Messaggio 
				loadAreaTargetDaTipoInterventoCustom(Long.parseLong(this.selTipoInterventoCustom1.getCodice()));
			}
			
				
		}else this.selTipoInterventoCustom1 = new KeyValueDTO();
	}
	
	public void onChangeCmbx2IntervCustom(AjaxBehaviorEvent e) {
		this.curSelCsCTipoInterventoCustom = null;
		this.selCatSocialeId = null;
		this.areasTarget =null;
		this.interISTAT= null;
		this.tooltipCustom =null;
		String nomeIntervento = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		listaTipoIntervento3Custom = new ArrayList<SelectItem>();
		listaTipoIntervento4Custom = new ArrayList<SelectItem>();	
		listaAreaTargetCustom  = new ArrayList<SelectItem>();
		listaTipoInterventoISTAT = new ArrayList<SelectItem>();
		this.selTipoInterventoCustom3=new KeyValueDTO();
		this.selTipoInterventoCustom4=new KeyValueDTO();
		this.selAreaTargetCustom=new KeyValueDTO(); 
		this.selInterventoISTAT=new KeyValueDTO();
		this.selTipoInterventoName = null;
		this.selTipoInterventoCustom = new KeyValueDTO();
		
		if(nomeIntervento!=null && !nomeIntervento.isEmpty()){
			
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId().toString().equalsIgnoreCase(selTipoInterventoCustom2.getCodice())) {
					this.selTipoInterventoCustom2 = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
					this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
				    this.curSelCsCTipoInterventoCustom = intervento;				
				}
				if (intervento.getCodice_2() != null  && intervento.getCodice_3() == null && intervento.getCodice_4() == null ) {
					if (intervento.getCodice_2().toString().equalsIgnoreCase(nomeIntervento)){					
				
					if (this.listaTipoInterventoPerAree != null && this.listaTipoInterventoPerAree.size()>0){
						for (VServiziCustom interventoA : listaTipoInterventoPerAree) {
							  if (intervento.getId().equals(interventoA.getInterventoCustomId())){						
								  
								  SelectItem si = new SelectItem(intervento.getId().toString(), intervento.getDescrizione());
								  si.setDisabled(!"1".equals(intervento.getAbilitato()));
									
								  listaTipoIntervento3Custom.add(si); 
						     	}
							}
						
						
						}
											
						else
						{
						
						SelectItem si = new SelectItem(intervento.getId().toString(), intervento.getDescrizione());
						si.setDisabled(!"1".equals(intervento.getAbilitato()));
						
						listaTipoIntervento3Custom.add(si); 
						}
				}
			}
			}
			if (this.selTipoInterventoCustom2.getCodice() != null && listaTipoIntervento3Custom.size() <=0)
			{
				
				loadAreaTargetDaTipoInterventoCustom(Long.parseLong(this.selTipoInterventoCustom2.getCodice()));
				
				if (areasTarget.size()<=0){
					
					addWarningDialog("Attenzione", "Non esistono interventi per la categoria sociale di appartenenza del soggetto");
				}
			}
			
		}else this.selTipoInterventoCustom2 = new KeyValueDTO();
	}

	
	public void onChangeCmbx3IntervCustom(AjaxBehaviorEvent e) {
		this.curSelCsCTipoInterventoCustom = null;
		this.selCatSocialeId = null;
		this.areasTarget =null;
		interISTAT= null;
		this.tooltipCustom =null;
		String nomeIntervento = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		
		listaTipoIntervento4Custom = new ArrayList<SelectItem>();
		listaAreaTargetCustom  = new ArrayList<SelectItem>();
		listaTipoInterventoISTAT = new ArrayList<SelectItem>();
		this.selTipoInterventoCustom4=new KeyValueDTO();
		this.selAreaTargetCustom=new KeyValueDTO(); 
		this.selInterventoISTAT=new KeyValueDTO(); 			
		this.selTipoInterventoName = null;
		this.selTipoInterventoCustom = new KeyValueDTO();
		
		if(nomeIntervento!=null && !nomeIntervento.isEmpty()){
			
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId().toString().equalsIgnoreCase(selTipoInterventoCustom3.getCodice())) {
					this.selTipoInterventoCustom3 = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());		
					this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
				    this.curSelCsCTipoInterventoCustom = intervento;
				}
				if (intervento.getCodice_3() != null && intervento.getCodice_3().toString().equalsIgnoreCase(nomeIntervento) && intervento.getCodice_4() == null ) {
					SelectItem si = new SelectItem(intervento.getId().toString(), intervento.getDescrizione());
					si.setDisabled(!"1".equals(intervento.getAbilitato()));
											
					listaTipoIntervento4Custom.add(si); 
				}
			}
			if (this.selTipoInterventoCustom3.getCodice()!= null && listaTipoIntervento4Custom.size() <=0)
			{
				loadAreaTargetDaTipoInterventoCustom(Long.parseLong(this.selTipoInterventoCustom3.getCodice()));
                if (areasTarget.size()<=0){
					
					addWarningDialog("Attenzione", "Non esistono interventi per la categoria sociale di appartenenza del soggetto");
				}
				
			}
			
		}else this.selTipoInterventoCustom3 = new KeyValueDTO();
	}
	
	public void onChangeCmbx4IntervCustom(AjaxBehaviorEvent e) {
		this.curSelCsCTipoInterventoCustom = null;
		this.selCatSocialeId = null;
		this.areasTarget =null;
		this.interISTAT= null;
		this.tooltipCustom =null;
		String nomeIntervento = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		
		listaAreaTargetCustom  = new ArrayList<SelectItem>();
		listaTipoInterventoISTAT = new ArrayList<SelectItem>();
		this.selAreaTargetCustom=new KeyValueDTO(); 
		this.selInterventoISTAT=new KeyValueDTO(); 
		this.selTipoInterventoName = null;
		this.selTipoInterventoCustom = new KeyValueDTO();
		
		if(nomeIntervento!=null && !nomeIntervento.isEmpty()){
			
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId().toString().equalsIgnoreCase(nomeIntervento)) {
					this.selTipoInterventoCustom4 = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
					this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
					this.curSelCsCTipoInterventoCustom = intervento;
			  }

			}
			if (this.selTipoInterventoCustom4!= null )
			{
				loadAreaTargetDaTipoInterventoCustom(Long.parseLong(this.selTipoInterventoCustom4.getCodice()));
				
                if (areasTarget.size()<=0){
					
					addWarningDialog("Attenzione", "Non esistono interventi per la categoria sociale di appartenenza del soggetto");
				}
			}
			
		}else this.selTipoInterventoCustom4 = new KeyValueDTO();
	}

	public void onChangeCmbxAreaTargetCustom(AjaxBehaviorEvent e) {
		this.selCatSocialeId = null;
		this.interISTAT= null;
		this.tooltipCustom =null;
		
		
		String idAreaTarget = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		if(idAreaTarget!=null && !idAreaTarget.isEmpty()){
			listaAreaTargetCustom = new ArrayList<SelectItem>();
			listaTipoInterventoISTAT = new ArrayList<SelectItem>();
			this.selAreaTargetCustom=new KeyValueDTO(); 
			this.selInterventoISTAT=new KeyValueDTO(); 
			this.selTipoInterventoName = null;
						
			for (VServiziCustom areaTarget : areasTarget) {
				if (Integer.toString(areaTarget.getAreatId()).equalsIgnoreCase(idAreaTarget)) {
					this.selAreaTargetCustom = new KeyValueDTO(Integer.toString(areaTarget.getAreatId()),areaTarget.getAreatDescrizione());
				    this.selCatSocialeId = Long.parseLong(areaTarget.getCodiceMemoAreat());
				}
 		}
			if (this.selAreaTargetCustom!= null )
			{
				//loadServiziCustoByInterventoAndAreatId(this.curSelCsCTipoInterventoCustom.getId(), Integer.parseInt(this.selAreaTargetCustom.getCodice()));
				this.loadIntISTATDaTipoInterventoCustom(this.curSelCsCTipoInterventoCustom.getId());
			}
			 if (interventisISTAT.size()<=0){
					
					addWarningDialog("Attenzione", "Non esistono interventi ISTAT per la categoria sociale di appartenenza del soggetto");
				}
			
		}else {
		   this.selAreaTargetCustom = new KeyValueDTO();
		   this.selCatSocialeId = null;
		   }
	}
	
	public void onChangeCmbxIntervISTAT(AjaxBehaviorEvent e) {
		//TODO
		this.visibleDescrizione = false;
		this.selIstatDescrizione2 ="";
		String idIntIstat = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		listaTipoInterventoISTAT= new ArrayList<SelectItem>();
		if(idIntIstat!=null && !idIntIstat.isEmpty()){
			for (ArTClasse istat : interISTAT) {
			if (Long.toString(istat.getId()).equalsIgnoreCase(idIntIstat)) {
				this.selInterventoISTAT = new KeyValueDTO(Long.toString(istat.getId()),istat.getDescrizione());
				this.selIstatDescrizione2 = istat.getDescrizione2();
				this.selTipoInterventoName = this.selInterventoISTAT.getDescrizione();
				this.selTipoInterventoId = istat.getId();
				this.visibleDescrizione = !this.selIstatDescrizione2.equals("");
				this.tooltipCustom = this.findTooltip(istat.getId());
			}
		}
			
			}else 
				this.selInterventoISTAT = new KeyValueDTO();
		}
		
	

	
	private void loadAreaTargetDaTipoInterventoCustom(Long idTipoInterventoCustom) {
		if (areasTarget == null ) {
			areasTarget = new LinkedList<VServiziCustom>();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idTipoInterventoCustom);
			
			if (lstIdAreas.size()<=0)
			{
				areasTarget = confService.findAreaTInterventoById(dto);

			}else{//se provengo dal fascicolo di un Soggetto ho anche le aree target a cui è associato, quindi devo filtrare gli interventi 
				//di cui può ususfruire in funzione alle aree T di appartenenza
				dto.setObj2(lstIdAreas);
				areasTarget = confService.findAreaTInterventoByIdeAreaTSoggetto(dto);
			}			

		}
	}
		
	private void loadServiziCustoByInterventoAndAreatId(Long idTipoInterventoCustom,Integer areatId) {
		if (servizisINPS == null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idTipoInterventoCustom);
			dto.setObj2(areatId);
			
			servizisINPS = confService.findAllServiziCustoByInterventoAndAreatId(dto);
		}
	}
	
	private List<VServiziCustom> loadInterventoByAreasId(List<String> lstIdAree) {
	 if(interventiPerAreaT == null){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(lstIdAree);
	
		
		interventiPerAreaT = confService.findDettaglioInterventobyAreaTId(dto);
		
	}
		return interventiPerAreaT;
	}
	
	private void loadIntISTATDaTipoInterventoCustom(Long idTipoInterventoCustom) {

		if (interISTAT == null) {
			interventisISTAT = new LinkedList<ArRelIntCustomIstat>();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idTipoInterventoCustom);
			
			interventisISTAT = confService.findInterventoIstatByInterventoCustom(dto);
			if (interventisISTAT.size()>0) {
				for (ArRelIntCustomIstat intervento : interventisISTAT) {
					BaseDTO dto2 = new BaseDTO();
					fillEnte(dto2);

					////SelezioNo solo le prime 2 lettere del codice Memo  
					//dto2.setObj(intervento.getPrestazioneIstatCodice().substring(0,2));
					
					//Seleziono le prime lettere del codice Memo fino al "-"
					String[] codiceIstat = intervento.getPrestazioneIstatCodice().split("-");
					dto2.setObj(codiceIstat[0]);
					
					interISTAT = confService.findInterventoIstatByCodice(dto2);
				}
			}
          
		}
	}

	public void resetCustomIstat() {
		selTipoInterventoCustom1 = new KeyValueDTO();
		selTipoInterventoCustom2 = new KeyValueDTO();
		selTipoInterventoCustom3 = new KeyValueDTO();
		selTipoInterventoCustom4 = new KeyValueDTO();
		selAreaTargetCustom = new KeyValueDTO();
		selServizioINPS = new KeyValueDTO();
		selInterventoISTAT = new KeyValueDTO();
		
		listaTipoIntervento1Custom = new ArrayList<SelectItem>();
		listaTipoIntervento2Custom = new ArrayList<SelectItem>();
		listaTipoIntervento3Custom = new ArrayList<SelectItem>();
		listaTipoIntervento4Custom = new ArrayList<SelectItem>();
		listaTipoInterventoISTAT = new ArrayList<SelectItem>();
		listaTipoInterventoPerAree = new ArrayList<VServiziCustom>();
		listaAreaTargetCustom = new ArrayList<SelectItem>();
		
		
		this.newTipoIntCustom = null;		
//		this.curSelCsCTipoInterventoCustom = null;
//		this.selCatSocialeId = null;
		this.curSelServizioInps = null;
		this.interISTAT= null;
		this.areasTarget=null;
		lstIdAreas = new ArrayList<String>();
		
		this.visibleDescrizione = false;
		this.tooltipCustom = null;
		
	
		
	}
	
	private void initPannello(String filteredNodes){
		if (!filteredNodes.isEmpty()) {
			String idAreas[] = filteredNodes.split("\\|");
			for (int i = 0; i < idAreas.length; i++) {
				this.lstIdAreas.add(idAreas[i]);
			}
			if (this.lstIdAreas != null && !this.lstIdAreas.isEmpty()) {

				this.listaTipoInterventoPerAree = loadInterventoByAreasId(this.lstIdAreas);
			}
		}
		
		 //Carico dinamicamente le labelle dei livelli del nomenclatore
     	String livelliNomenclatore = super.getLivelliNomenclatore();
		if (livelliNomenclatore != null && !livelliNomenclatore.isEmpty()) {

			String livellis[] = livelliNomenclatore.split("\\|");
			if (livellis != null && livellis.length > 1) {
				try {
					this.setLabelLivello1(livellis[0]);
					this.labelLivello2 = livellis[1] != null ? livellis[1].toString() : "Non definito";
					this.labelLivello3 = livellis[2] != null ? livellis[2].toString() : "Non definito";
					this.labelLivello4 = livellis[3] != null ? livellis[3].toString() : "Non definito";
				} catch (Exception e) {
					addWarning("",
							"Le etichette dei livelli del nomenclatore non sono stati definiti");
				}

			}
		}
		
	}
	
	//SISO-1110 Fine
	

	public void onChangeCmbxIntervCustomRecent(AjaxBehaviorEvent e) throws Exception {
		String valore = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		VTipiInterventoUsati ir = VTipiInterventoUsati.decode(valore);
		if(ir!=null){
			this.reset();
			boolean found = treeController.setSelectedNode(ir.getDescrizioneCategoriaSociale(), ir.getIdTipoIntervento());
			if(found){ 
				this.selTipoInterventoId = ir.getIdTipoIntervento();
				loadTipoInterventoCustom(true);
				this.selTipoInterventoName = ir.getDescrizioneTipoIntervento();
				this.selCatSocialeId = ir.getIdCategoriaSociale();
				this.tooltip = this.findTooltip(ir.getIdTipoIntervento());
				if(ir.getIdTipoInterventoCustom() != null) {
					for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
						if (intervento.getId() == ir.getIdTipoInterventoCustom()) {
							this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
							this.curSelCsCTipoInterventoCustom = intervento;			
							return;
						}
					}
				}
			}
		}
	}

	public void SalvaNuovoIntCustom() {
		if (this.newTipoInterventosCustom == null)
			this.newTipoInterventosCustom = new LinkedList<CsCTipoInterventoCustom>();
		for (CsCTipoInterventoCustom item : tipoInterventosCustom) {
			if (item.getDescrizione().equalsIgnoreCase(getNewTipoIntCustom())) {
				addErrorDialog("Attenzione", "non è possibile salvare  l'intervento " + getNewTipoIntCustom() + "poichè è già presente.");
				return;
			}
		}

		CsCTipoInterventoCustom nuovoIntCustom = new CsCTipoInterventoCustom();
		nuovoIntCustom.setDescrizione(getNewTipoIntCustom());
		nuovoIntCustom.setAbilitato("1");
		nuovoIntCustom.setId(tipoInterventosCustom.size() * (-1L));
		newTipoInterventosCustom.add(nuovoIntCustom);
		tipoInterventosCustom.add(nuovoIntCustom);
		this.selTipoInterventoCustom = new KeyValueDTO(nuovoIntCustom.getId().toString(),null);
		this.curSelCsCTipoInterventoCustom = nuovoIntCustom;

	}

	public void ConfermaSelezione() {
		if (newTipoInterventosCustom != null) {

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(newTipoInterventosCustom);
			dto.setObj2(this.curSelCsCTipoInterventoCustom);
			CsCTipoInterventoCustom curIntCust = interventoService.saveNewCsCTipoInterventoCustom(dto);
			this.selTipoInterventoCustom = new KeyValueDTO(curIntCust.getId().toString(),null);

			interventoService.refreshTipoInterventoView(dto);
			addWarningDialog("Attenzione", "Nessuna modifica effettuata!");
		}
	}

	public void EsciAction() {
		if (isTreeViewTipoIntervento())// SISO-1110
		{
			this.selectedNode = null;
			this.treeController.loadTree(true);
			this.tooltip = null;
		} else {
			// Inizio SISO-1110
			this.resetCustomIstat();
			this.initPannello(getCategorieSociali());
			this.loadTipoInterventoCustom(true);
			// Fine SISO-1110
		}

	}

	public TreeNodeBean getCurCustomNode() {

		TreeNodeBean node = null;
		if (selectedNode != null) {
			node = (TreeNodeBean) selectedNode.getData();
		}
		return node;
	}

	protected TreeNode findRoot(TreeNode node) {
		if (node.getParent() == root)
			return node;
		else
			return findRoot(node.getParent());
	}

	public void onNodeSelect(NodeSelectEvent event) {
		try {
			this.tipoInterventosCustom = null;
			this.newTipoIntCustom = null;
			this.selTipoInterventoCustom = new KeyValueDTO();
			this.selTipoInterventoName = null;
			this.selTipoInterventoId = null;
			this.curSelCsCTipoInterventoCustom = null;
			this.selCatSocialeId = null;
			this.tooltip = null;
			
			if (event.getTreeNode().isLeaf()) {
				TreeNodeBean curNode = (TreeNodeBean) event.getTreeNode().getData();
				TreeNodeBean parentNode = (TreeNodeBean) findRoot(event.getTreeNode()).getData();
	
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(parentNode.getName());
				CsCCategoriaSociale catSoc = confService.findCatSocialeByDescrizione(dto);
				if(catSoc!=null)
					selCatSocialeId = catSoc.getId();
				else
					this.addError("Errore caricamento", "Area target non trovata: "+parentNode.getName());
					
				if (!curNode.isbSelectable()) {
					this.selTipoInterventoName = curNode.getName();
					addWarningDialog("Attenzione", "Servizio non abilitato");
				} else {
					this.selTipoInterventoId = curNode.getTClasseId();
					this.selTipoInterventoName = curNode.getName();
					this.newTipoIntCustom = null;
					this.selTipoInterventoCustom = new KeyValueDTO();
					this.recenteString = null;
					//this.selTipoInterventoCustomName=null;
					loadTipoInterventoCustom(true);
				}
				
				tooltip = this.findTooltip(curNode.getTClasseId().longValue());
				
			}
		}catch(Exception e){
			logger.error("onNodeSelect "+e.getMessage(), e);
			this.addError("Errore caricamento", e.getMessage());
		}
	}

	public Long getCurrentTipoInterv() {
		TreeNodeBean node = null;
		if (selectedNode != null) {
			node = (TreeNodeBean) selectedNode.getData();
			return node.getTClasseId();
		}
		return null;
	}

	public void reset() {
		this.selectedNode = null;
		this.selCatSocialeId = null;
		this.curSelCsCTipoInterventoCustom = null;
		this.newTipoIntCustom = null;
		this.selTipoInterventoId = null;
		this.selTipoInterventoCustom = new KeyValueDTO();
		this.selTipoInterventoName = null;
		this.treeController.loadTree(true);
		this.root = treeController.getRoot();
		this.visibleDescrizione = false;
		this.tooltip = null;
	}
	
	public List<VTipiInterventoUsati> getLstRecenti() {
		
		VTipiInterventoUsati v = null;
		VGerrarchiaServizi s = null;
		try {
			final int MAX_ELEMENTS = 20;
			int added = 0;

			if (treeController == null || treeController.getRootNodes() == null || treeController.getRootNodes().size() == 0) {
				System.err.println("TreeController empty or not initialized");
				return new ArrayList<VTipiInterventoUsati>();
			}

			List<VTipiInterventoUsati> tmp = new ArrayList<VTipiInterventoUsati>();
			if(lstRecenti == null ) lstRecenti = new ArrayList<VTipiInterventoUsati>();
			if(lstRecenti.isEmpty()) {
				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				tmp = confService.findAllInterventiRecenti(bdto);

				for (int i = 0; i < tmp.size(); i++) {
					v = tmp.get(i);
//				}
//				for (VTipiInterventoUsati v : ) {
					for (int j = 0; j < treeController.getRootNodes().size(); j++) {
						s = treeController.getRootNodes().get(j);
//					}
//					for (VGerrarchiaServizi s : treeController.getRootNodes()) {
							if (treeController.getVisibleNodes().isEmpty() || treeController.getVisibleNodes().indexOf(s.getDescLivello()) != -1) {
								if (v.getDescrizioneCategoriaSociale().equalsIgnoreCase(s.getDescLivello())) {
									lstRecenti.add(v);
									added++;
									break;
								}
							}
						}
					if (added >= MAX_ELEMENTS)
						break;
				}
			}
			Collections.sort(lstRecenti);
		} catch (Exception e) {
			logger.error(e);
		}

		return lstRecenti;
	}

	public KeyValueDTO getSelTipoInterventoCustom() {
		return selTipoInterventoCustom;
	}

	public List<SelectItem> getListaTipoInterventoCustom() {
		listaTipoInterventoCustom = new ArrayList<SelectItem>();
		if(this.tipoInterventosCustom!=null){
			for (CsCTipoInterventoCustom i : this.getTipoInterventosCustom()) {
				String description = i.getDescrizione();
				SelectItem si = new SelectItem(i.getId().toString(), description);
				si.setDisabled(!"1".equals(i.getAbilitato()));
				listaTipoInterventoCustom.add(si);
			}
		}
		return this.listaTipoInterventoCustom;
	}	
	
	public InformativaDTO getTooltip() {
		return tooltip;
	}

	public void setTooltip(InformativaDTO tooltip) {
		this.tooltip = tooltip;
	}
	
	private InformativaDTO findTooltip(Long idNodo){
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idNodo);
		InformativaDTO i = confService.findInformativa(dto);
		return i;
	}
}
