package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the PGT_SQL_DECO_LAYER database table.
 * 
 */
@Entity
@Table(name="PGT_SQL_DECO_LAYER")
public class PgtSqlDecoLayer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_pgt_sql_deco_layer" )
	@SequenceGenerator(name="seq_pgt_sql_deco_layer", sequenceName="SEQ_PGT_SQL_DECO_LAYER")
	private Long id;
	
	private String codut;

	private String colore;

	private String colorelinea;

	private String descrizione;

	@Column(name="ID_LAYER")
	private Long idLayer;

	private String riempimento;

	private String spessore;
	
	private BigDecimal occorrenze;

	private String standard;
	
	public PgtSqlDecoLayer() {
    }

	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public String getCodut() {
		return this.codut;
	}

	public void setCodut(String codut) {
		this.codut = codut;
	}

	public String getColore() {
		return this.colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getColorelinea() {
		return this.colorelinea;
	}

	public void setColorelinea(String colorelinea) {
		this.colorelinea = colorelinea;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getIdLayer() {
		return idLayer;
	}

	public void setIdLayer(Long idLayer) {
		this.idLayer = idLayer;
	}

	public String getRiempimento() {
		return this.riempimento;
	}

	public void setRiempimento(String riempimento) {
		this.riempimento = riempimento;
	}

	public String getSpessore() {
		return this.spessore;
	}

	public void setSpessore(String spessore) {
		this.spessore = spessore;
	}

	public BigDecimal getOccorrenze() {
		return occorrenze;
	}

	public void setOccorrenze(BigDecimal occorrenze) {
		this.occorrenze = occorrenze;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}