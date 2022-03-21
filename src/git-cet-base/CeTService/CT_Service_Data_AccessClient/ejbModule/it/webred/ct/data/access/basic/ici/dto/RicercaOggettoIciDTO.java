package it.webred.ct.data.access.basic.ici.dto;

import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RicercaOggettoIciDTO extends CeTBaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String idExt;
	private Date dtRif;
	
	private List<VTIciCiviciAll> listaCivIci;
	private String provenienza;
	
	public RicercaOggettoIciDTO (){
		super();
	}
	public RicercaOggettoIciDTO (String id,	String idExt){
		super();
		this.id=id;
		this.idExt=idExt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdExt() {
		return idExt;
	}
	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}
	public List<VTIciCiviciAll> getListaCivIci() {
		return listaCivIci;
	}
	public void setListaCivIci(List<VTIciCiviciAll> listaCivIci) {
		this.listaCivIci = listaCivIci;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public Date getDtRif() {
		return dtRif;
	}
	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}
	
}
