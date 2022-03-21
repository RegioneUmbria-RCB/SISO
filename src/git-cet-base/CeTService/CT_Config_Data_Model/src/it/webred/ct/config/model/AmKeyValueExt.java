package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_KEY_VALUE_EXT database table.
 * 
 */
@Entity
@Table(name="AM_KEY_VALUE_EXT")
public class AmKeyValueExt implements Serializable {
	private static final long serialVersionUID = 1L;

	@SequenceGenerator(name="GEN_KEY_VALUE_EXT", sequenceName="SEQ_KEY_VALUE_EXT", allocationSize=1)
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="GEN_KEY_VALUE_EXT")
	private Integer id;

	@Column(name="EXT_TYPE")
	private String extType;

	@Column(name="FK_AM_FONTE")
	private Integer fkAmFonte;

	@Column(name="KEY_CONF")
	private String keyConf;

	@Column(name="VALUE_CONF")
	private String valueConf;

	//uni-directional many-to-one association to AmComune
	@ManyToOne
	@JoinColumn(name="FK_AM_COMUNE")
	private AmComune amComune;

	//uni-directional many-to-one association to AmInstance
    @ManyToOne
	@JoinColumn(name="FK_AM_INSTANCE")
	private AmInstance amInstance;

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

    public AmKeyValueExt() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExtType() {
		return this.extType;
	}

	public void setExtType(String extType) {
		this.extType = extType;
	}

	public Integer getFkAmFonte() {
		return this.fkAmFonte;
	}

	public void setFkAmFonte(Integer fkAmFonte) {
		this.fkAmFonte = fkAmFonte;
	}

	public String getKeyConf() {
		return this.keyConf;
	}

	public void setKeyConf(String keyConf) {
		this.keyConf = keyConf;
	}

	public String getValueConf() {
		return this.valueConf != null?this.valueConf.trim(): null;
	}

	public void setValueConf(String valueConf) {
		this.valueConf = valueConf;
	}

	public AmComune getAmComune() {
		return this.amComune;
	}

	public void setAmComune(AmComune amComune) {
		this.amComune = amComune;
	}
	
	public AmInstance getAmInstance() {
		return this.amInstance;
	}

	public void setAmInstance(AmInstance amInstance) {
		this.amInstance = amInstance;
	}
	
	public AmSection getAmSection() {
		return this.amSection;
	}

	public void setAmSection(AmSection amSection) {
		this.amSection = amSection;
	}

	public String getFkAmApplication() {
		return fkAmApplication;
	}

	public void setFkAmApplication(String fkAmApplication) {
		this.fkAmApplication = fkAmApplication;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
}