package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DOCFA_FOGLI_MICROZONA database table.
 * 
 */
@Entity
@Table(name="DOCFA_FOGLI_MICROZONA")
public class DocfaFogliMicrozona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaFogliMicrozonaPK id;

	@Column(name="NEW_MICROZONA")
	private String newMicrozona;

	@Column(name="OLD_MICROZONA")
	private String oldMicrozona;



    public DocfaFogliMicrozona() {
    }

	public String getNewMicrozona() {
		return this.newMicrozona;
	}

	public void setNewMicrozona(String newMicrozona) {
		this.newMicrozona = newMicrozona;
	}

	public String getOldMicrozona() {
		return this.oldMicrozona;
	}

	public void setOldMicrozona(String oldMicrozona) {
		this.oldMicrozona = oldMicrozona;
	}

	public DocfaFogliMicrozonaPK getId() {
		return id;
	}

	public void setId(DocfaFogliMicrozonaPK id) {
		this.id = id;
	}

}