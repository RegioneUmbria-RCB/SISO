package it.webred.WsSitClient.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ViaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String toponimo;
	private String codVia;
	private String topoVia;
	private String topo1;
	private String topo2;
	private String topo3;
	private String topo4;
	private String topo5;
	public String getToponimo() {
		return toponimo;
	}
	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
	public String getCodVia() {
		return codVia;
	}
	public void setCodVia(String codVia) {
		this.codVia = codVia;
	}
	public String getTopoVia() {
		return topoVia;
	}
	public void setTopoVia(String topoVia) {
		this.topoVia = topoVia;
	}
	public String getTopo1() {
		return topo1;
	}
	public void setTopo1(String topo1) {
		this.topo1 = topo1;
	}
	public String getTopo2() {
		return topo2;
	}
	public void setTopo2(String topo2) {
		this.topo2 = topo2;
	}
	public String getTopo3() {
		return topo3;
	}
	public void setTopo3(String topo3) {
		this.topo3 = topo3;
	}
	public String getTopo4() {
		return topo4;
	}
	public void setTopo4(String topo4) {
		this.topo4 = topo4;
	}
	public String getTopo5() {
		return topo5;
	}
	public void setTopo5(String topo5) {
		this.topo5 = topo5;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
