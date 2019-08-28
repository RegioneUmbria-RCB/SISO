package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the SS_INDIRIZZO database table.
 * 
 */
@Entity
@Table(name="SS_INDIRIZZO")
@NamedQuery(name="SsIndirizzo.findAll", query="SELECT c FROM SsIndirizzo c")
public class SsIndirizzo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_INDIRIZZO_ID_GENERATOR", sequenceName="SQ_SS_INDIRIZZO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_INDIRIZZO_ID_GENERATOR")
	private Long id;
	
	private String via;
	private String numero;
	
	@Column(name="COMUNE_COD")
	private String comuneCod;
	
	@Column(name="COMUNE_DES")
	private String comuneDes;
	
	@Column(name="PROV_COD")
	private String provCod;
	
	@Column(name="STATO_COD")
	private String statoCod;
	
	@Column(name="STATO_DES")
	private String statoDes;
	
	@Column(name="STRUTTURA_ACCOGLIENZA")
	private String strutturaAccoglienza;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getComuneCod() {
		return comuneCod;
	}
	public void setComuneCod(String comuneCod) {
		this.comuneCod = comuneCod;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getStatoCod() {
		return statoCod;
	}
	public void setStatoCod(String statoCod) {
		this.statoCod = statoCod;
	}
	public String getComuneDes() {
		return comuneDes;
	}
	public String getProvCod() {
		return provCod;
	}
	public String getStatoDes() {
		return statoDes;
	}
	public void setComuneDes(String comuneDes) {
		this.comuneDes = comuneDes;
	}
	public void setProvCod(String provCod) {
		this.provCod = provCod;
	}
	public void setStatoDes(String statoDes) {
		this.statoDes = statoDes;
	}
	public String getStrutturaAccoglienza() {
		return strutturaAccoglienza;
	}
	public void setStrutturaAccoglienza(String strutturaAccoglienza) {
		this.strutturaAccoglienza = strutturaAccoglienza;
	}
	
}
