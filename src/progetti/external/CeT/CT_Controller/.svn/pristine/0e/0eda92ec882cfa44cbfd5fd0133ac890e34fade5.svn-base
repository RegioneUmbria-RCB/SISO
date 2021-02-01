package it.webred.ct.rulengine.web.bean.audit;

import it.webred.ct.config.audit.dto.AuditDTO;
import it.webred.ct.config.audit.dto.AuditSearchCriteria;
import it.webred.ct.config.model.AmAudit;
import it.webred.ct.config.model.AmAuditDecode;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.rulengine.web.bean.util.AuditTreeDTO;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

public class RicercaAuditBean extends AuditBaseBean {

	private AuditSearchCriteria criteria = new AuditSearchCriteria();
	private String ultimiNgg;
	private List<String> listaEntiAuth = new ArrayList<String>();
	private List<SelectItem> listaEnti = new ArrayList<SelectItem>();
	private List<SelectItem> listaFonti = new ArrayList<SelectItem>();
	private List<String> collectionItem =
            Arrays.asList("Utente", "Sessione", "Fonte");
	private String idAuditScelto;
	private String descrAuditScelto;
	private TreeNode<String> richDetailTree;
	private String dettPage = "/jsp/protected/empty.xhtml";

	//oggetti richfaces tree
	private List<TreeNode<String>> listaAuditTree = new ArrayList<TreeNode<String>>();
    private List<String> selectedNodeChildren = new ArrayList<String>();    
    private String nodeTitle;
    
    //oggetti creazione tree dinamico
    private AuditTreeDTO auditTree;
    private HashMap<Integer, String> hashItemSelected;
    private String last1;
    private String last2;
    private String last3;

	public void doRicerca() {

		String msg = "audit.ricerca";
		listaAuditTree = new ArrayList<TreeNode<String>>();
		hashItemSelected = new HashMap<Integer, String>();
		last1 = "";
		last2 = "";
		last3 = "";
		
		List<String> orderItem = new ArrayList<String>();
		int i = 1;
	    for(String el: collectionItem) {
	    	hashItemSelected.put(new Integer(i), el);
	    	i++;
	    	if(el.equals("Utente"))
	    		orderItem.add("userId");
	    	if(el.equals("Sessione"))
	    		orderItem.add("sessionId");
	    	if(el.equals("Fonte"))
	    		orderItem.add("descrizioneFonte");
	    }

		try {

			criteria.setOrder(orderItem);
			//setto opzione ultmi n giorni
			if(ultimiNgg != null && !"".equals(ultimiNgg) && !"0".equals(ultimiNgg)){
				Calendar ini = Calendar.getInstance();
				Calendar fin = Calendar.getInstance();
				int days = - new Integer(ultimiNgg).intValue();
				ini.add(Calendar.DATE, days);
				fin.add(Calendar.DATE, 1);
				Date dataDa = ini.getTime();
				Date dataA = fin.getTime();
				criteria.setDataInizio(dataDa);
				criteria.setDataFine(dataA);
			}
			//filtro per enti autorizzati
			criteria.setEnti(listaEntiAuth);

			List<AuditDTO> listaLog = auditService.getListaAmAudit(criteria, null, null);
			
			auditTree = generateAuditList(listaLog);

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}
	
	private AuditTreeDTO generateAuditList(List<AuditDTO> listaLog) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
		AuditTreeDTO listaTree = new AuditTreeDTO();
		
		for(AuditDTO aDto: listaLog){
				AmAudit a = aDto.getAmAudit();
				AmAuditDecode d = aDto.getAmAuditDecode();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				aDto.setUtente(a.getUserId());
				if(aDto.getDataMinSessione() != null && aDto.getDataMaxSessione() != null)
					aDto.setSessione("Da " + sdf.format(aDto.getDataMinSessione()) + " a " + sdf.format(aDto.getDataMaxSessione()));
				if(d.getAmFonte() != null)
					aDto.setFonte(d.getAmFonte().getDescrizione() + (d.getFkAmFonteTipoinfo()!=null?(", " + d.getFkAmFonteTipoinfo()):""));
			
		        AuditTreeDTO tree1 = new AuditTreeDTO();
		        AuditTreeDTO tree2 = new AuditTreeDTO();
		        AuditTreeDTO tree3 = new AuditTreeDTO();
		        AuditTreeDTO tree4 = new AuditTreeDTO();
		        
		        Class cl = aDto.getClass();
		        for(int i = 0; i < collectionItem.size(); i++){
		        	if(i == 0){
				        Field field1 = cl.getDeclaredField(collectionItem.get(i).toLowerCase());
				        field1.setAccessible(true);
				        tree1.setTipo(hashItemSelected.get(new Integer(1)));
				        String descr = (String) field1.get(aDto);
		            	tree1.setDescrizione(descr != null? descr: "");
		        	}
		        	if(i == 1){
				        Field field2 = cl.getDeclaredField(collectionItem.get(i).toLowerCase());
				        field2.setAccessible(true);
				        tree2.setTipo(hashItemSelected.get(new Integer(2)));
				        String descr = (String) field2.get(aDto);
		            	tree2.setDescrizione(descr != null? descr: "");
		        	}
		        	if(i == 2){
				        Field field3 = cl.getDeclaredField(collectionItem.get(i).toLowerCase());
				        field3.setAccessible(true);
				        tree3.setTipo(hashItemSelected.get(new Integer(3)));
				        String descr = (String) field3.get(aDto);
		            	tree3.setDescrizione(descr != null? descr: "");
		        	}
		        }
		        tree4.setTipo("Descrizione");
		        tree4.setDescrizione(d.getDescrizione());
		        tree4.setIdAmAudit(new Long(aDto.getAmAudit().getId()).toString());
                
                if(!last1.equals(tree1.getDescrizione())){
                	tree3.getListaNodi().add(tree4);
	                tree2.getListaNodi().add(tree3);
	                tree1.getListaNodi().add(tree2);
	                listaTree.getListaNodi().add(tree1);
                }
                else if(!last2.equals(tree2.getDescrizione())){
                	tree3.getListaNodi().add(tree4);
                	tree2.getListaNodi().add(tree3);
                	int last = listaTree.getListaNodi().size() - 1;
                	listaTree.getListaNodi().get(last).getListaNodi().add(tree2);
                }
                else if(!last3.equals(tree3.getDescrizione())){
                	tree3.getListaNodi().add(tree4);
                	int last = listaTree.getListaNodi().size() - 1;
                	int last2 = listaTree.getListaNodi().get(last).getListaNodi().size() - 1;
                	listaTree.getListaNodi().get(last).getListaNodi().get(last2).getListaNodi().add(tree3);
                }else{
                	int last = listaTree.getListaNodi().size() - 1;
                	int last2 = listaTree.getListaNodi().get(last).getListaNodi().size() - 1;
                	int last3 = listaTree.getListaNodi().get(last).getListaNodi().get(last2).getListaNodi().size() - 1;
                	listaTree.getListaNodi().get(last).getListaNodi().get(last2).getListaNodi().get(last3).getListaNodi().add(tree4);
                }

                last1 = tree1.getDescrizione();
                last2 = tree2.getDescrizione();
                last3 = tree3.getDescrizione();
                 
        }
		return listaTree;
		
	}
	
    public void doDetailTree(){
    	
    	try{
    		
    		AmAudit a = auditService.getAmAuditById(new Long(idAuditScelto));
    		richDetailTree = new TreeNodeImpl<String>();
			TreeNode<String> richNodeChiave = new TreeNodeImpl<String>();
			TreeNode<String> richNodeInput = loadTree(a.getArgs());
			richNodeInput.setData("Argomenti");
			if(a.getChiave() != null){
				String value = a.getChiave();
				value = value.replaceAll(">>>", ":   ");
	            value = value.replaceAll("\\|\\|\\|", "   ");
	            value = value.replaceAll("Input", "Chiave");
				richNodeChiave.setData(value);
			}else richNodeChiave.setData("Chiave:");
			richDetailTree.addChild(new Integer(1), richNodeChiave);
			richDetailTree.addChild(new Integer(2), richNodeInput);
			if(!a.getResult().equals("null")){
				TreeNode<String> richNodeOutput = loadTree(a.getResult());
				richNodeOutput.setData("Risultato");
				richDetailTree.addChild(new Integer(3), richNodeOutput);
			}
    		
    	} catch (Exception e) {
        	super.addErrorMessage("audit.dettaglio.error", e.getMessage());
			logger.error(e.getMessage(),e);
        }
    	
    }
	
	private void addNodes(String path, TreeNode<String> node, HashMap<String, String> hFields) {
        boolean end = false;
        int counter = 1;
       
        while(!end){
        	String key = path != null ? path + '.' + counter : String.valueOf(counter);
        	String value = hFields.get(key);	
            if (value != null && !value.endsWith("null")&& !value.endsWith(">>>")) {
                TreeNodeImpl<String> nodeImpl = new TreeNodeImpl<String>();
                value = value.replaceAll(">>>", ":   ");
                value = value.replaceAll("\\|\\|\\|", "   ");
                nodeImpl.setData(value);
                node.addChild(new Integer(counter), nodeImpl);
                addNodes(key, nodeImpl, hFields);
                counter++;
            } else {
                end = true;
            }
        }
    }
    
    private TreeNode<String> loadTree(String object) {
    	
    	if(object != null && !"".equals(object.trim())){
	        try {
	            
	        	TreeNode<String> rootNode = new TreeNodeImpl<String>();
	            // costruzione hm dalla stringa che descrive l'oggetto loggato
	            HashMap<String, String> hFields = new HashMap<String, String>();
	            String[] fields = object.split("\\|\\|\\|");
	            for(int i = 0; i<fields.length; i++) {
	            	String[] field = fields[i].split("===");
	            	hFields.put(field[0], field[1]);
	            }
	            addNodes(null, rootNode, hFields);
	            
	            return rootNode;
	            
	        } catch (Exception e) {
	        	super.addErrorMessage("audit.ricerca.error", e.getMessage());
	        	logger.error(e.getMessage(),e);
	        }
    	} return null;
    }
    
    public void processSelection(NodeSelectedEvent event) {
        HtmlTree tree = (HtmlTree) event.getComponent();
        nodeTitle = (String) tree.getRowData();
        selectedNodeChildren.clear();
        TreeNode<String> currentNode = tree.getModelTreeNode(tree.getRowKey());
        if (currentNode.isLeaf()){
            selectedNodeChildren.add((String)currentNode.getData());
        }else
        {
            Iterator<Map.Entry<Object, TreeNode<String>>> it = currentNode.getChildren();
            while (it!=null &&it.hasNext()) {
                Map.Entry<Object, TreeNode<String>> entry = it.next();
                selectedNodeChildren.add(entry.getValue().getData().toString()); 
            }
        }
    }
    
    public void doReset(){
    	setCriteria(new AuditSearchCriteria());
    	ultimiNgg = "";
    }
    
	public void resetPage() {

		dettPage = "/jsp/protected/empty.xhtml";

	}
    
	public String goAudit() {
		if (listaEnti.size() == 0) {
			List<AmComune> listaComuni = getListaEntiAuth();
			for (AmComune comune : listaComuni) {
				listaEntiAuth.add(comune.getBelfiore());
				listaEnti.add(new SelectItem(comune.getBelfiore(), comune
						.getDescrizione()));
			}
		}
		if (listaFonti.size() == 0) {
			List<AmFonte> listaF = fonteService.getListaFonteAll();
			
			for (AmFonte fonte : listaF) {
				listaFonti.add(new SelectItem(fonte.getId().toString(), fonte.getDescrizione()));
			}
		}
		
		return "controller.audit";
	}
    
    public List<TreeNode<String>> getListaAuditTree() {
		return listaAuditTree;
	}

	public void setListaAuditTree(List<TreeNode<String>> listaAuditTree) {
		this.listaAuditTree = listaAuditTree;
	}

	public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

	public AuditSearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(AuditSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public List<String> getCollectionItem() {
		return collectionItem;
	}

	public void setCollectionItem(List<String> collectionItem) {
		this.collectionItem = collectionItem;
	}

	public AuditTreeDTO getAuditTree() {
		return auditTree;
	}

	public void setAuditTree(AuditTreeDTO auditTree) {
		this.auditTree = auditTree;
	}

	public String getUltimiNgg() {
		return ultimiNgg;
	}

	public void setUltimiNgg(String ultimiNgg) {
		this.ultimiNgg = ultimiNgg;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	public List<SelectItem> getListaFonti() {
		return listaFonti;
	}

	public void setListaFonti(List<SelectItem> listaFonti) {
		this.listaFonti = listaFonti;
	}

	public String getIdAuditScelto() {
		return idAuditScelto;
	}

	public void setIdAuditScelto(String idAuditScelto) {
		this.idAuditScelto = idAuditScelto;
	}

	public TreeNode<String> getRichDetailTree() {
		return richDetailTree;
	}

	public void setRichDetailTree(TreeNode<String> richDetailTree) {
		this.richDetailTree = richDetailTree;
	}

	public String getDettPage() {
		return dettPage;
	}

	public void setDettPage(String dettPage) {
		this.dettPage = dettPage;
	}

	public String getDescrAuditScelto() {
		return descrAuditScelto;
	}

	public void setDescrAuditScelto(String descrAuditScelto) {
		this.descrAuditScelto = descrAuditScelto;
	}

}
