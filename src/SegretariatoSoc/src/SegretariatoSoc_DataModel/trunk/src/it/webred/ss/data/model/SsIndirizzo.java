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
	private String comune;
	
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
	
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
}
