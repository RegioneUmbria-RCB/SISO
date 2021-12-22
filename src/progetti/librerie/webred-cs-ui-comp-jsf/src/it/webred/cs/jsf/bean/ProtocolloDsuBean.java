package it.webred.cs.jsf.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProtocolloDsuBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer anno;
	private String prefisso;
	private String numero;
	private String progressivo;
	
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getProgressivo() {
		if(this.getPrefisso()!=null && StringUtils.isEmpty(progressivo))
			this.progressivo="00";
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	public String getPrefisso() {
		return prefisso;
	}
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}
	public boolean isAnnoSelected(){
		return anno!=null && anno.intValue()>0;
	}
	public String getProtocolloCompleto(){
		return this.getPrefisso()+"-"+this.getNumero()+"-"+this.getProgressivo();
	}
}
