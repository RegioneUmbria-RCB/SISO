package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the CS_TB_MOTIVO_CHIUSURA_PAI database table.
 * 
 */
@Entity
@Table(name = "CS_TB_MOTIVO_CHIUSURA_PAI")
@NamedQuery(name = "CsTbMotivoChiusuraPai.findAll", query = "SELECT c FROM CsTbMotivoChiusuraPai c order by c.descrizione")
public class CsTbMotivoChiusuraPai implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_TB_MOTIVO_CHIUSURA_PAI_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_TB_MOTIVO_CHIUSURA_PAI_ID_GENERATOR")
	private long id;

	private boolean abilitato;

	private String descrizione;

	private String tooltip;

	private Integer ordine;
	
	private String tipi_pai;
	
	@Column(name="CODICE_ESITO_SINBA")
	private String codiceEsitoSinba; 
			
	public CsTbMotivoChiusuraPai() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public String getTipi_pai() {
		return tipi_pai;
	}

	public void setTipi_pai(String tipi_pai) {
		this.tipi_pai = tipi_pai;
	}

	public String getCodiceEsitoSinba() {
		return codiceEsitoSinba;
	}

	public void setCodiceEsitoSinba(String codiceEsitoSinba) {
		this.codiceEsitoSinba = codiceEsitoSinba;
	}
	
}