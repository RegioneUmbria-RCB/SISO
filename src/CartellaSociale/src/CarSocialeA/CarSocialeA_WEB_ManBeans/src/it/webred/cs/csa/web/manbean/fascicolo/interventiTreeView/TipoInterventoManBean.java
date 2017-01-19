package it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;

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

	private String recenteString;
	List<VTipiInterventoUsati> lstRecenti;

	private String newTipoIntCustom;
	private Long selTipoInterventoId;
	private String selTipoInterventoName;
	
	//private String selTipoInterventoCustom;
	//private String selTipoInterventoCustomName;
	
	private KeyValueDTO selTipoInterventoCustom;
	
	private Long selCatSocialeId;

	private CsCTipoInterventoCustom curSelCsCTipoInterventoCustom;
	private List<CsCTipoInterventoCustom> tipoInterventosCustom;
	private List<SelectItem> listaTipoInterventoCustom;
	private List<CsCTipoInterventoCustom> newTipoInterventosCustom;

	@ManagedProperty("#{treeController}")
	private TreeController treeController;

	public TipoInterventoManBean(List<SelectItem> lstTipoInterventi, String filteredNodes) {
		treeController = new TreeController(lstTipoInterventi, filteredNodes);
		root = treeController.getRoot();
		selTipoInterventoCustom=new KeyValueDTO();
		// loadTipoInterventoCustom(false);
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
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(getCurrentTipoInterv()); //TODO: Rimuovere? sembra obsoleto
			tipoInterventosCustom = interventoService.findTipiIntCustom(dto);
		}
	}

	// **PUBLIC //
	public void onChangeCmbxIntervCustom(AjaxBehaviorEvent e) {
		String nomeIntervento = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		if(nomeIntervento!=null && !nomeIntervento.isEmpty()){
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId().toString().equalsIgnoreCase(nomeIntervento)) {
					this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
					this.curSelCsCTipoInterventoCustom = intervento;
					// treeController.setSelectedNode(375L);
					
					treeController.setSelectedNode(getSelTipoInterventoId());
					this.recenteString = null;
					return;
				}
			}
		}else this.selTipoInterventoCustom = new KeyValueDTO();
	}

	public void onChangeCmbxIntervCustomRecent(AjaxBehaviorEvent e) throws Exception {
		String valore = (String) ((javax.faces.component.UIInput) (e.getSource())).getValue();
		VTipiInterventoUsati ir = VTipiInterventoUsati.decode(valore);
		if (ir != null && ir.getIdTipoInterventoCustom() != null) {
			this.selTipoInterventoId = ir.getIdTipoIntervento();
			loadTipoInterventoCustom(true);
			for (CsCTipoInterventoCustom intervento : tipoInterventosCustom) {
				if (intervento.getId() == ir.getIdTipoInterventoCustom()) {
					this.selTipoInterventoCustom = new KeyValueDTO(intervento.getId().toString(),intervento.getDescrizione());
					
					this.curSelCsCTipoInterventoCustom = intervento;
					this.selTipoInterventoName = ir.getDescrizioneTipoIntervento();
					this.selCatSocialeId = ir.getIdCategoriaSociale();
					treeController.setSelectedNode(ir.getIdTipoIntervento());
					return;
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
		nuovoIntCustom.setAbilitato('1');
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
		this.selectedNode = null;
		this.treeController.loadTree(true);
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
		this.tipoInterventosCustom = null;
		this.newTipoIntCustom = null;
		this.selTipoInterventoCustom = new KeyValueDTO();
		this.selTipoInterventoName = null;
		this.selTipoInterventoId = null;
		this.curSelCsCTipoInterventoCustom = null;
		this.selCatSocialeId = null;

		if (event.getTreeNode().isLeaf()) {
			TreeNodeBean curNode = (TreeNodeBean) event.getTreeNode().getData();
			TreeNodeBean parentNode = (TreeNodeBean) findRoot(event.getTreeNode()).getData();

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(parentNode.getName());
			CsCCategoriaSociale catSoc = interventoService.findCatSocialeByDescrizione(dto);
			selCatSocialeId = catSoc.getId();

			if (!curNode.isbSelectable()) {
				this.selTipoInterventoName = curNode.getName();
				addWarningDialog("Attenzione", "Servizio non richiamabile dal operatore");
			} else {
				this.selTipoInterventoId = curNode.getTClasseId();
				this.selTipoInterventoName = curNode.getName();
				this.newTipoIntCustom = null;
				this.selTipoInterventoCustom = new KeyValueDTO();

				//this.selTipoInterventoCustomName=null;
				loadTipoInterventoCustom(true);
			}
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
		//this.selTipoInterventoCustomName = null;
		this.selTipoInterventoName = null;
		this.treeController.loadTree(true);
		this.root = treeController.getRoot();
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
				tmp = interventoService.findAllInterventiRecenti(bdto);

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
			//FIXME   residuo-evoluzione-pai	e.printStackTrace();
			//FIXME   residuo-evoluzione-pai	System.out.println(v);
			//FIXME   residuo-evoluzione-pai	System.out.println(s);
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
				si.setDisabled("1".equals(i.getAbilitato()));
				listaTipoInterventoCustom.add(si);
			}
		}
		return this.listaTipoInterventoCustom;
	}

	
}
