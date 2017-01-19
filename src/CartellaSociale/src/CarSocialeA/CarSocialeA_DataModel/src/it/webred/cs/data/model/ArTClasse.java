package it.webred.cs.data.model;



import java.io.Serializable;

import javax.persistence.*; 

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


//MOD-RL
/**
 * The persistent class for the AR_T_CLASSE database table.
 * 
 */
@Entity
@Table(name="AR_T_CLASSE")
@NamedQuery(name="ArTClasse.findAll", query="SELECT a FROM ArTClasse a")
public class ArTClasse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String abilitato;

	@Column(name="CODICE_MEMO")
	private String codiceMemo;

	private String descrizione;

	private String descrizione2;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="FLAG_INTERVENTO")
	private BigDecimal flagIntervento;

	@Column(name="SUB_CODICE_MEMO")
	private String subCodiceMemo;

	private String tooltip;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional many-to-one association to ArRelClassememoPresInps
	@OneToMany(mappedBy="arTClasse")
	private List<ArRelClassememoPresInps> arRelClassememoPresInps;

	public ArTClasse() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getCodiceMemo() {
		return this.codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione2() {
		return this.descrizione2;
	}

	public void setDescrizione2(String descrizione2) {
		this.descrizione2 = descrizione2;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public BigDecimal getFlagIntervento() {
		return this.flagIntervento;
	}

	public void setFlagIntervento(BigDecimal flagIntervento) {
		this.flagIntervento = flagIntervento;
	}

	public String getSubCodiceMemo() {
		return this.subCodiceMemo;
	}

	public void setSubCodiceMemo(String subCodiceMemo) {
		this.subCodiceMemo = subCodiceMemo;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public List<ArRelClassememoPresInps> getArRelClassememoPresInps() {
		return this.arRelClassememoPresInps;
	}

	public void setArRelClassememoPresInps(List<ArRelClassememoPresInps> arRelClassememoPresInps) {
		this.arRelClassememoPresInps = arRelClassememoPresInps;
	}

	public ArRelClassememoPresInps addArRelClassememoPresInp(ArRelClassememoPresInps arRelClassememoPresInp) {
		getArRelClassememoPresInps().add(arRelClassememoPresInp);
		arRelClassememoPresInp.setArTClasse(this);

		return arRelClassememoPresInp;
	}

	public ArRelClassememoPresInps removeArRelClassememoPresInp(ArRelClassememoPresInps arRelClassememoPresInp) {
		getArRelClassememoPresInps().remove(arRelClassememoPresInp);
		arRelClassememoPresInp.setArTClasse(null);

		return arRelClassememoPresInp;
	}


	

	//bi-directional many-to-one association to CsCTipoIntervento
	@OneToMany(mappedBy="ArTClasse")
	private List<CsCTipoIntervento> csCTipoInterventos;
	
	public List<CsCTipoIntervento> getCsCTipoInterventos() {
		return this.csCTipoInterventos;
	}

	public void setCsCTipoInterventos(List<CsCTipoIntervento> csCTipoInterventos) {
		this.csCTipoInterventos = csCTipoInterventos;
	}

}