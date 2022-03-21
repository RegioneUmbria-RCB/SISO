package it.webred.ct.data.model.pgt;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the PGT_SQL_DECO_LAYER database table.
 * 
 */
@Entity
@Table(name="PGT_SQL_INFO_LAYER")
public class PgtSqlInfoLayer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_pgt_sql_info_layer" )
	@SequenceGenerator(name="seq_pgt_sql_info_layer", sequenceName="SEQ_PGT_SQL_INFO_LAYER")
	private Long id;

	@Column(name="ID_LAYER")
	private Long idLayer;
	
	private String standard;

	@Column(name="NAME_COL")
	private String nameCol;

	@Column(name="NAME_ALIAS")
	private String nameAlias;
	
	public PgtSqlInfoLayer() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLayer() {
		return idLayer;
	}

	public void setIdLayer(Long idLayer) {
		this.idLayer = idLayer;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getNameCol() {
		return nameCol;
	}

	public void setNameCol(String nameCol) {
		this.nameCol = nameCol;
	}

	public String getNameAlias() {
		return nameAlias;
	}

	public void setNameAlias(String nameAlias) {
		this.nameAlias = nameAlias;
	}
}