package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the AM_KEY_VALUE database table.
 * 
 */
@Entity
@Table(name="AM_KEY_VALUE")
public class AmKeyValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

    @Temporal( TemporalType.DATE)
	private Date deleted;

	@Column(name="DESCRIZIONE_KEY")
	private String descrizioneKey;

	@Column(name="KEY_CONF")
	private String key;

	@Column(name="MUST_BE_SET")
	private String mustBeSet;

	@Column(name="OVERW_TYPE")
	private String overwType;

	@Column(name="VALUE_CONF")
	private String value;

    @Column(name="SECTION_NAME", insertable = false, updatable = false)
    private String sectionName;
	
    @Column(name="FK_AM_APPLICATION", insertable = false, updatable = false)
    private String fkAmApplication;
	
	//bi-directional many-to-one association to AmSection
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="FK_AM_APPLICATION", referencedColumnName="FK_AM_APPLICATION"),
		@JoinColumn(name="SECTION_NAME", referencedColumnName="NAME")
		})
	private AmSection amSection;

    public AmKeyValue() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}

	public String getDescrizioneKey() {
		return this.descrizioneKey;
	}

	public void setDescrizioneKey(String descrizioneKey) {
		this.descrizioneKey = descrizioneKey;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMustBeSet() {
		return this.mustBeSet;
	}

	public void setMustBeSet(String mustBeSet) {
		this.mustBeSet = mustBeSet;
	}

	public String getOverwType() {
		return this.overwType;
	}

	public void setOverwType(String overwType) {
		this.overwType = overwType;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AmSection getAmSection() {
		return this.amSection;
	}

	public void setAmSection(AmSection amSection) {
		this.amSection = amSection;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getFkAmApplication() {
		return fkAmApplication;
	}

	public void setFkAmApplication(String fkAmApplication) {
		this.fkAmApplication = fkAmApplication;
	}
	
}