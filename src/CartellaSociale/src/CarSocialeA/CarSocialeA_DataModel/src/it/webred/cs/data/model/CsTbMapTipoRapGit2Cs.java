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
	private CsTbMapTipoRapGit2CsPK id;
	
	@ManyToOne
	@JoinColumn(name="COD_DEST")
	private CsTbTipoRapportoCon csTbTipoRapportoCon;

	public CsTbMapTipoRapGit2CsPK getId() {
		return id;
	}

	public void setId(CsTbMapTipoRapGit2CsPK id) {
		this.id = id;
	}

	public CsTbTipoRapportoCon getCsTbTipoRapportoCon() {
		return csTbTipoRapportoCon;
	}

	public void setCsTbTipoRapportoCon(CsTbTipoRapportoCon csTbTipoRapportoCon) {
		this.csTbTipoRapportoCon = csTbTipoRapportoCon;
	}


}
