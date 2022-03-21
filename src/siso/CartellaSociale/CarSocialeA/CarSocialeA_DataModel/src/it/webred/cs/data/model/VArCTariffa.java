package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_AR_C_TARIFFA database table.
 * 
 */
@Entity
@Table(name="V_AR_C_TARIFFA")
@NamedQueries(value={
	   @NamedQuery(name = "VArCTariffa.findTariffaByUmOrgIntCustom",
			   	   query="SELECT v FROM VArCTariffa v WHERE v.unitaMisuraId = :unitaMisuraId and v.organizzazioneId = :orgTitolareId and v.tipoInterventoCustomId = :intCustomId")
	})
public class VArCTariffa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name="COD_TARIFFA")
	private String codTariffa;
	
	@Column(name="DESC_TARIFFA")
	private String descTariffa;

	@Column(name="ORGANIZZAZIONE_ID")
	private Long organizzazioneId;
	
	@Column(name="COD_ROUTING")
	private String codRouting;

	@Column(name="UNITA_MISURA_ID")
	private Long unitaMisuraId;
	
	@Column(name="UNITA_MISURA_DESC")
	private String unitaMisuraDesc;
	
	@Column(name="TIPO_INTERVENTO_CUSTOM_ID")
	private Long tipoInterventoCustomId;
	
	private BigDecimal tariffa;
	
	@Column(name="DATA_DA")
	private Date dataDa;

	@Column(name="DATA_A")
	private Date dataA;

	private boolean abilitato;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	@Column(name="DT_MOD")
	private Date dtMod;
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodTariffa() {
		return this.codTariffa;
	}

	public void setCodTariffa(String codTariffa) {
		this.codTariffa = codTariffa;
	}


	public String getDescTariffa() {
		return this.descTariffa;
	}

	public void setDescTariffa(String descTariffa) {
		this.descTariffa = descTariffa;
	}

	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}

	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}

	
	public Long getUnitaMisuraId() {
		return unitaMisuraId;
	}

	
	public void setUnitaMisuraId(Long unitaMisuraId) {
		this.unitaMisuraId = unitaMisuraId;
	}

	public String getUnitaMisuraDesc() {
		return unitaMisuraDesc;
	}

	public void setUnitaMisuraDesc(String unitaMisuraDesc) {
		this.unitaMisuraDesc = unitaMisuraDesc;
	}

	public Long getTipoInterventoCustomId() {
		return tipoInterventoCustomId;
	}

	
	public void setTipoInterventoCustomId(Long tipoInterventoCustomId) {
		this.tipoInterventoCustomId = tipoInterventoCustomId;
	}

	public BigDecimal getTariffa() {
		return this.tariffa;
	}

	public void setTariffa(BigDecimal tariffa) {
		this.tariffa = tariffa;
	}
	
	public Date getDataDa() {
		return dataDa;
	}


	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	
	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}
	
	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}
	
	public Date getDtMod() {
		return dtMod;
	}


	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	
	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

}
