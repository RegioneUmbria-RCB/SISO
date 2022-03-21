package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the PGT_SQL_VIS_LAYER database table.
 * 
 */
@Entity
@Table(name="PGT_SQL_VIS_LAYER")
public class PgtSqlVisLayer implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgtSqlVisLayerPK id;

	@Column(name="MOD_INTERROGAZIONE")
	private String modInterrogazione;

	@Column(name="SQL_LAYER")
	private String sqlLayer;

	private String visualizza;

	public PgtSqlVisLayer() {
	}

	public PgtSqlVisLayerPK getId() {
		return this.id;
	}

	public void setId(PgtSqlVisLayerPK id) {
		this.id = id;
	}

	public String getModInterrogazione() {
		return this.modInterrogazione;
	}

	public void setModInterrogazione(String modInterrogazione) {
		this.modInterrogazione = modInterrogazione;
	}

	public String getSqlLayer() {
		return this.sqlLayer;
	}

	public void setSqlLayer(String sqlLayer) {
		this.sqlLayer = sqlLayer;
	}

	public String getVisualizza() {
		return this.visualizza;
	}

	public void setVisualizza(String visualizza) {
		this.visualizza = visualizza;
	}

}