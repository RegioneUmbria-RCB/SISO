package it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView;

import it.webred.cs.data.model.VGerrarchiaServizi;

import java.io.Serializable;
import java.math.BigDecimal;

import org.primefaces.model.DefaultTreeNode;

public class TreeNodeBean implements Serializable, Comparable<TreeNodeBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idTreeNode;
	private Long tClasseId;
	private String icon;
	private String name;
	private boolean bSelectable = true;

	public TreeNodeBean(Long idTreeNode, String name) {
		this.idTreeNode = idTreeNode;
		this.name = name;
    
	}

//node = new DefaultTreeNode(new TreeNodeBean(gServ.gettClasseId(), gServ.getId(), gServ.getDescrizione(), isSelectable, icon), parent);

	public TreeNodeBean(VGerrarchiaServizi gServ,boolean bSelectable ,String icon) {
		this.tClasseId=gServ.gettClasseId().longValue();
		this.idTreeNode = gServ.getId().longValue();
		this.name = gServ.getDescrizione();
		this.bSelectable = bSelectable;
		this.icon=icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	public boolean isbSelectable() {
		return bSelectable;
	}


	public void setbSelectable(boolean bSelectable) {
		this.bSelectable = bSelectable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((idTreeNode == null) ? 0 : idTreeNode.hashCode());

		return result;
	}

	public Long getIdTreeNode() {
		return idTreeNode;
	}


	public void setIdTreeNode(Long idTreeNode) {
		this.idTreeNode = idTreeNode;
	}


	public Long getTClasseId(){
		return tClasseId;
	}


	public void setTClasseId(Long id) {
		this.tClasseId = id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNodeBean other = (TreeNodeBean) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (idTreeNode == null) {
			if (other.idTreeNode != null)
				return false;
		} else if (!idTreeNode.equals(other.idTreeNode))
			return false;

		return true;
	}

	@Override
	public int compareTo(TreeNodeBean node) {
		return this.getName().compareTo(node.getName());
	}
}