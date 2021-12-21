package it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "treeController")
@ViewScoped
public class TreeController extends CsUiCompBaseBean {
	
	public static final String ICON_SELECTABLE = "ui-icon-unlocked";
	public static final String ICON_NOSELECTABLE = "ui-icon-locked";
	public static final String ICON_FOLDER = "ui-icon-folder-collapsed";
	private List<VGerrarchiaServizi> rootNodes = new LinkedList<VGerrarchiaServizi>();
	private List<VGerrarchiaServizi> childrenNodes = new LinkedList<VGerrarchiaServizi>();
	private HashMap<BigDecimal, String> enabledLeafs = null;
	private List<VGerrarchiaServizi> allServices = null;
	private List<VGerrarchiaServizi> grandsonNodes;
	private List<SelectItem> lstTipoInterventi = null;
	private TreeNode root;
	private String visibleNodes = "";

	public TreeController(List<SelectItem> lstTipoInterventi, String visibleNodes) {
		this.lstTipoInterventi = lstTipoInterventi;
		this.visibleNodes = visibleNodes;
		createTreeNodes();
	}

	@PostConstruct
	public void createTreeNodes() {
		LookAndUnlookLeafs();
		loadTree(false);
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<VGerrarchiaServizi> getRootNodes() {
		return rootNodes;
	}

	public String getVisibleNodes() {
		return visibleNodes;
	}

	// /**PROTECTED Methods*///
	protected void resetTreeSelection() {
		loadTree(true);
	}

	protected boolean setSelectedNode(String catSociale, Long id) {
		boolean found = false;
		collapseRecursive(this.getRoot());
		TreeNode currentNode = null;
		if(id!=null)
			currentNode = searchNode(catSociale, id);
		
		if (currentNode != null) {
			currentNode.setSelected(true);
			expandedRecursiveParents(currentNode);
			found=true;
		}else{
			this.addWarning("Selezionare un altro tipo intervento", "Il tipo intervento selezionato non è più abilitato");
		}
		return found;
	}

	protected void loadTree(boolean reload) {
		if (allServices == null || !reload) {
			root = null;

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			allServices = new LinkedList<VGerrarchiaServizi>();
			allServices = interventoService.findAllNodesTipoIntervento(dto);

			root = new DefaultTreeNode(new TreeNodeBean(0L, "Root"), null);
			createPrincipalNodes(allServices, root);
			createNodes(rootNodes);
		}
	}

	// /**PRIVATE Methods*///

	private TreeNode searchNode(Long id) {
		TreeNode node = recursiveSearch(id, getRoot());
		node.setSelected(true);
		if (node != null)
			expandedRecursiveParents(node);
		return null;
	}
	
	private TreeNode searchNode(String catSociale, Long id) {
		BigDecimal idCatSociale = null;
		int i = 0;
		List<VGerrarchiaServizi> lst = this.getRootNodes();
		while(idCatSociale==null && i<lst.size()){
			VGerrarchiaServizi v = lst.get(i);
			if(catSociale.equalsIgnoreCase(v.getDescLivello()))
				idCatSociale = v.gettClasseId();
			i++;
		}
		TreeNode nodoCat = recursiveSearch(idCatSociale.longValue(), getRoot());
		
		TreeNode node = recursiveSearch(id, nodoCat);
		return node;
	}

	private TreeNode recursiveSearch(Long id, TreeNode node) {
		TreeNodeBean currentNode = (TreeNodeBean) (node.getData());
		if (id.equals(currentNode.getTClasseId()))
			return node;

		List<TreeNode> children = node.getChildren();
		TreeNode leaf = null;

		for (int i = 0; leaf == null && i < children.size(); i++) {
			leaf = recursiveSearch(id, children.get(i));
		}
		return leaf;
	}

	private void expandedRecursiveParents(TreeNode node) {
		TreeNode parent = node.getParent();
		if (parent != getRoot()) {
			parent.setExpanded(true);
			expandedRecursiveParents(parent);
		}

	}
	
	private void collapseRecursive(TreeNode node) {
		node.setExpanded(false);
		node.setSelected(false);
		List<TreeNode> children = node.getChildren();
		for (int i = 0; i < children.size(); i++) {
			collapseRecursive(children.get(i));
		}
	}
	

	private TreeNode createTreeCustomNode(VGerrarchiaServizi gServ, TreeNode parent) {
		TreeNode node = null;
		boolean isSelectable = true;
		String icon = ICON_FOLDER;
		if (!parent.equals(root)) {
			if (enabledLeafs.containsKey(gServ.gettClasseId()))
				icon = ICON_SELECTABLE;
			else {
				isSelectable = false;
				icon = ICON_NOSELECTABLE;
			}

			TreeNodeBean curNode = (TreeNodeBean) parent.getData();
			if (ICON_FOLDER != curNode.getIcon())
				curNode.setIcon(ICON_FOLDER);

		}
        
		TreeNodeBean nodo = new TreeNodeBean(gServ, isSelectable, icon);
		node = new DefaultTreeNode(nodo, parent);

		return node;
	}

	private void createNodes(List<VGerrarchiaServizi> gServizi) {
		String visibleNodesUpper = this.visibleNodes.toUpperCase();
		boolean isvisible = visibleNodes.isEmpty();
		for (VGerrarchiaServizi gServ : rootNodes) {
			if (!visibleNodes.isEmpty())
				isvisible = visibleNodesUpper.contains(gServ.getRootDescrizione().toUpperCase());

			if (isvisible) {
				TreeNode curNode = createTreeCustomNode(gServ, root);

				childrenNodes = new LinkedList<VGerrarchiaServizi>();
				childrenNodes = getChildren(gServ.getId());

				for (VGerrarchiaServizi children : childrenNodes) {
					TreeNode childNode = createTreeCustomNode(children, curNode);
					BigDecimal idNode = children.getId();
					recursiveNodes(childrenNodes, idNode, childNode);
				}
			}

		}
	}

	private void createPrincipalNodes(List<VGerrarchiaServizi> gServizi, TreeNode root) {

		for (VGerrarchiaServizi gServ : gServizi) {
			if (gServ.getqClasseId() == null)
				rootNodes.add(gServ);
		}

	}

	private void recursiveNodes(List<VGerrarchiaServizi> children, BigDecimal id, TreeNode curNode) {
		grandsonNodes = new LinkedList<VGerrarchiaServizi>();
		grandsonNodes = getChildren(id);
		for (VGerrarchiaServizi gServ : grandsonNodes) {
			TreeNode childNode = createTreeCustomNode(gServ, curNode);
			BigDecimal idNode = gServ.getId();
			recursiveNodes(grandsonNodes, idNode, childNode);
		}
	}

	private List<VGerrarchiaServizi> getChildren(BigDecimal id) {
		List<VGerrarchiaServizi> childList = new LinkedList<VGerrarchiaServizi>();
		for (VGerrarchiaServizi item : allServices) {
			if (item.getqClasseId() != null && item.getqClasseId().equals(id)) {
				childList.add(item);
			}
		}
		return childList;
	}

	private void LookAndUnlookLeafs() {
		if (lstTipoInterventi != null && enabledLeafs == null) {
			enabledLeafs = new HashMap<BigDecimal, String>();
			List<String> lstStati = getListaTipoStatoErogazioneByPermessoErogazione();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(lstStati);

			this.enabledLeafs = new HashMap<BigDecimal, String>();
			for (SelectItem i : lstTipoInterventi) {
				if(!i.isDisabled()){
					String description = i.getLabel();
					this.enabledLeafs.put(new BigDecimal((Long) i.getValue()), description);
				}
			}
		}
	}
}
