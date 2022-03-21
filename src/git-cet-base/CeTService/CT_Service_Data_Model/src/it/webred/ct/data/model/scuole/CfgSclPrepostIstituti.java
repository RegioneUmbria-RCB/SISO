package it.webred.ct.data.model.scuole;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CFG_SCL_PREPOST_ISTITUTI database table.
 * 
 */
@Entity
@Table(name="CFG_SCL_PREPOST_ISTITUTI")
public class CfgSclPrepostIstituti implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CfgSclPrepostIstitutiPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="FLAG_SN_POST")
	private String flagSnPost;

	@Column(name="FLAG_SN_PRE")
	private String flagSnPre;

	@Column(name="FLAG_SN_PREPOST")
	private String flagSnPrepost;

	@Column(name="NOTE_ORARIO_POST")
	private String noteOrarioPost;

	@Column(name="NOTE_ORARIO_PRE")
	private String noteOrarioPre;

	@Column(name="NOTE_ORARIO_PREPOST")
	private String noteOrarioPrepost;

	private String processid;

    public CfgSclPrepostIstituti() {
    }

	public CfgSclPrepostIstitutiPK getId() {
		return this.id;
	}

	public void setId(CfgSclPrepostIstitutiPK id) {
		this.id = id;
	}
	
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getFlagSnPost() {
		return this.flagSnPost;
	}

	public void setFlagSnPost(String flagSnPost) {
		this.flagSnPost = flagSnPost;
	}

	public String getFlagSnPre() {
		return this.flagSnPre;
	}

	public void setFlagSnPre(String flagSnPre) {
		this.flagSnPre = flagSnPre;
	}

	public String getFlagSnPrepost() {
		return this.flagSnPrepost;
	}

	public void setFlagSnPrepost(String flagSnPrepost) {
		this.flagSnPrepost = flagSnPrepost;
	}

	public String getNoteOrarioPost() {
		return this.noteOrarioPost;
	}

	public void setNoteOrarioPost(String noteOrarioPost) {
		this.noteOrarioPost = noteOrarioPost;
	}

	public String getNoteOrarioPre() {
		return this.noteOrarioPre;
	}

	public void setNoteOrarioPre(String noteOrarioPre) {
		this.noteOrarioPre = noteOrarioPre;
	}

	public String getNoteOrarioPrepost() {
		return this.noteOrarioPrepost;
	}

	public void setNoteOrarioPrepost(String noteOrarioPrepost) {
		this.noteOrarioPrepost = noteOrarioPrepost;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

}