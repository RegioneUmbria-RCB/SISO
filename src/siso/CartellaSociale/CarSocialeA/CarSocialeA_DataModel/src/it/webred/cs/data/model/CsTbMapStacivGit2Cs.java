package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CS_TB_MAP_STACIV_GIT2CS")
public class CsTbMapStacivGit2Cs implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsTbMapGit2CsPK id;
	
	@ManyToOne
	@JoinColumn(name="COD_DEST")
	private CsTbStatoCivile csTbStatoCivile;

	public CsTbMapGit2CsPK getId() {
		return id;
	}

	public void setId(CsTbMapGit2CsPK id) {
		this.id = id;
	}

	public CsTbStatoCivile getCsTbStatoCivile() {
		return csTbStatoCivile;
	}

	public void setCsTbStatoCivile(CsTbStatoCivile csTbStatoCivile) {
		this.csTbStatoCivile = csTbStatoCivile;
	}
}
