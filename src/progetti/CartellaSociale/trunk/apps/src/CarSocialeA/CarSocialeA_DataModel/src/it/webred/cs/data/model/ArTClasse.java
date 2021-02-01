package it.webred.cs.data.model;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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

	/*Rimosso SISO-638 
	@Column(name="FLAG_INTERVENTO")
	private BigDecimal flagIntervento;
	*/

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

	//SISO-1110
	@OneToMany(mappedBy="csCInterventoCustom")
	private List<ArRelIntCustomIstat> arRelIntCustomIstat;
		
	//SISO-1110 Inizio
	public List<ArRelIntCustomIstat> getArRelIntCustomIstat() {
		return arRelIntCustomIstat;
	}

	public void setArRelIntCustomPresInps(
		List<ArRelIntCustomIstat> arRelIntCustomIstat) {
		this.arRelIntCustomIstat = arRelIntCustomIstat;
	}
	//SISO-1110 Fine
	
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

	/*Rimosso SISO-638 
	public BigDecimal getFlagIntervento() {
		return this.flagIntervento;
	}

	public void setFlagIntervento(BigDecimal flagIntervento) {
		this.flagIntervento = flagIntervento;
	}*/

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