package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CS_C_INTERV_UM database table.
 * 
 */
@Entity
@Table(name="CS_C_INTERV_UM")
@NamedQuery(name="CsCIntervUm.findAll", query="SELECT c FROM CsCIntervUm c")
public class CsCIntervUm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="ID_UNITA_MISURA")
	private Long idUnitaMisura;
	
	@Column(name="ID_INTERVENTO_CUSTOM")
	private Long idInterventoCustom;

	@Column(name="ID_INTERVENTO_ISTAT")
	private Long idInterventoIstat;

	public CsCIntervUm() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getIdUnitaMisura() {
		return idUnitaMisura;
	}

	public void setIdUnitaMisura(Long idUnitaMisura) {
		this.idUnitaMisura = idUnitaMisura;
	}

	public Long getIdInterventoCustom() {
		return idInterventoCustom;
	}

	public void setIdInterventoCustom(Long idInterventoCustom) {
		this.idInterventoCustom = idInterventoCustom;
	}

	public Long getIdInterventoIstat() {
		return idInterventoIstat;
	}

	public void setIdInterventoIstat(Long idInterventoIstat) {
		this.idInterventoIstat = idInterventoIstat;
	}


}