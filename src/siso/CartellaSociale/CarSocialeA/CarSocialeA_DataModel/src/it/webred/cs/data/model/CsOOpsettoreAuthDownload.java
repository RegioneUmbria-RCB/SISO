package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CS_O_OPSETTORE_AUTH_DOWNLOAD")
public class CsOOpsettoreAuthDownload implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsOOpsettoreAuthDownloadPK id;

	public CsOOpsettoreAuthDownloadPK getId() {
		return id;
	}

	public void setId(CsOOpsettoreAuthDownloadPK id) {
		this.id = id;
	}
}