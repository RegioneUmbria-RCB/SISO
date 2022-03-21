package it.webred.ss.web.bean.wizard;

import java.util.Date;

public class InterventoEconomico {
	
	private Long id;
	private Long importo;
	private String tipo;
	private String erogante;
	private Date data;
	
	
	public InterventoEconomico(Long id, Long importo, String tipo, String erogante, Date data) {
		super();
		this.id = id;
		this.importo = importo;
		this.tipo = tipo;
		this.erogante = erogante;
		this.data = data;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getImporto() {
		return importo;
	}
	public void setImporto(Long importo) {
		this.importo = importo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getErogante() {
		return erogante;
	}
	public void setErogante(String erogante) {
		this.erogante = erogante;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
}
