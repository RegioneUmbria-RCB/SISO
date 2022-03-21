package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CS_TB_MAP_TIPO_RAP_GIT2CS")
public class CsTbMapTipoRapGit2Cs implements Serializable {
	
	@EmbeddedId
	private CsTbMapGit2CsPK id;
	
	@ManyToOne
	@JoinColumn(name="COD_DEST")
	private CsTbTipoRapportoCon csTbTipoRapportoCon;

	public CsTbMapGit2CsPK getId() {
		return id;
	}

	public void setId(CsTbMapGit2CsPK id) {
		this.id = id;
	}

	public CsTbTipoRapportoCon getCsTbTipoRapportoCon() {
		return csTbTipoRapportoCon;
	}

	public void setCsTbTipoRapportoCon(CsTbTipoRapportoCon csTbTipoRapportoCon) {
		this.csTbTipoRapportoCon = csTbTipoRapportoCon;
	}


}
