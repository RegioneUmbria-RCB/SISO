package it.webred.ct.rulengine.web.bean.util;

import it.webred.ct.config.model.AmAudit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.richfaces.model.TreeNode;

public class AuditTreeDTO implements Serializable {
	
	private String tipo;
	private String descrizione;
	private TreeNode<String> tree;
	private String idAmAudit;
	private List<AuditTreeDTO> listaNodi = new ArrayList<AuditTreeDTO>();
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public TreeNode<String> getTree() {
		return tree;
	}
	public void setTree(TreeNode<String> tree) {
		this.tree = tree;
	}
	public List<AuditTreeDTO> getListaNodi() {
		return listaNodi;
	}
	public void setListaNodi(List<AuditTreeDTO> listaNodi) {
		this.listaNodi = listaNodi;
	}
	public String getIdAmAudit() {
		return idAmAudit;
	}
	public void setIdAmAudit(String idAmAudit) {
		this.idAmAudit = idAmAudit;
	}
	
}
