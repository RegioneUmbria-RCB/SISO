package it.webred.ss.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SS_TEST database table.
 * 
 */
@Entity
@Table(name="SS_TEST")
@NamedQuery(name="SsTest.findAll", query="SELECT c FROM SsTest c")
public class SsTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_TEST_ID_GENERATOR", sequenceName="SQ_SS_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_TEST_ID_GENERATOR")
	private Long id;

	private String descrizione;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA")//opzionale: se non presente assume che abbia lo stesso nome nella tabella
	private Date data;

	private BigDecimal prezzo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(BigDecimal prezzo) {
		this.prezzo = prezzo;
	}
	
}