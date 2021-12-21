package it.umbriadigitale.argo.data.cs.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AR_FF_PROGETTO")
public class ArFfProgetto implements java.io.Serializable {

	@Id
	@SequenceGenerator(name="AR_PROG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_PROG_ID_GENERATOR")
	private Long id;
	
	@Column(name = "CODICE_MEMO")
	private String codiceMemo;
	
	private String descrizione;
	
	@Column(name = "USER_INS")
	private String userIns;
	
	@Column(name = "DT_INS")
	private Date dtIns;
	
	@Column(name = "USR_MOD")
	private String usrMod;
	
	@Column(name = "DT_MOD")
	private Date dtMod;
	private Boolean abilitato;
	
	@OneToMany(mappedBy = "arFfProgetto")
	private Set<ArFfLineafin> lstLineaFinanziamento = new HashSet<ArFfLineafin>(0);
	
	public ArFfProgetto() {
	}

	public ArFfProgetto(long id, String codiceMemo, String descrizione,
			String userIns, Date dtIns) {
		this();
		this.id = id;
		this.codiceMemo = codiceMemo;
		this.descrizione = descrizione;
		this.userIns = userIns;
		this.dtIns = dtIns;
	}

	public ArFfProgetto(long id, String codiceMemo, String descrizione,
			String userIns, Date dtIns, String usrMod, Date dtMod,
			Boolean abilitato, Set<ArFfLineafin> arFfLineafins) {
		this();
		this.id = id;
		this.codiceMemo = codiceMemo;
		this.descrizione = descrizione;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.usrMod = usrMod;
		this.dtMod = dtMod;
		this.abilitato = abilitato;
//		this.lstLineaFinanziamento = arFfLineafins;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getUserIns() {
		return userIns;
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
		return usrMod;
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

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public Set<ArFfLineafin> getLstLineaFinanziamento() {
		return lstLineaFinanziamento;
	}

	public void setLstLineaFinanziamento(Set<ArFfLineafin> lstLineaFinanziamento) {
		this.lstLineaFinanziamento = lstLineaFinanziamento;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ArFfProgetto))
            return false;
        return
            id != null &&
            id.equals(((ArFfProgetto) o).getId());
    }
	
}
