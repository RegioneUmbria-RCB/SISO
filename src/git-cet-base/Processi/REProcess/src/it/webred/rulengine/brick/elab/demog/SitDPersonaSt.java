package it.webred.rulengine.brick.elab.demog;

import java.io.Serializable;
import java.sql.Date;


public class SitDPersonaSt implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String codfisc;

	private String cognome;

	private Date dataEmi;

	private Date dataImm;

	private Date dataInizioResidenza;

	private Date dataMor;

	private Date dataNascita;

	private String idExtDPersona;
	
	private String idExtComuneEmi;

	private String idExtComuneImm;

	private String idExtComuneMor;

	private String idExtComuneNascita;

	private String idExtProvinciaEmi;

	private String idExtProvinciaImm;

	private String idExtProvinciaMor;

	private String idExtProvinciaNascita;

	private String idExtStato;

	private String nome;

	private String sesso;

	private String statoCivile;
	
	private Integer nOrd;
	
	private String indirizzoEmi;

    public SitDPersonaSt() {
    }
    
    public SitDPersonaSt(SitDPersonaSt p){
    	codfisc=p.getCodfisc();
    	cognome=p.getCognome();
    	idExtDPersona=p.getIdExtDPersona();
    	
    	dataNascita=p.getDataNascita();
    	idExtComuneNascita=p.getIdExtComuneNascita();
    	idExtProvinciaNascita=p.getIdExtProvinciaNascita();
    	idExtStato=p.getIdExtStato();
    	
    	dataMor=p.getDataMor();
    	idExtComuneMor=p.getIdExtComuneMor();
    	idExtProvinciaMor=p.getIdExtProvinciaMor();

    	
    	nome=p.getNome();
    	sesso=p.getSesso();
    	statoCivile=p.getStatoCivile();
    	nOrd=p.getnOrd();
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodfisc() {
		return codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataEmi() {
		return dataEmi;
	}

	public void setDataEmi(Date dataEmi) {
		this.dataEmi = dataEmi;
	}

	public Date getDataImm() {
		return dataImm;
	}

	public void setDataImm(Date dataImm) {
		this.dataImm = dataImm;
	}

	public Date getDataInizioResidenza() {
		return dataInizioResidenza;
	}

	public void setDataInizioResidenza(Date dataInizioResidenza) {
		this.dataInizioResidenza = dataInizioResidenza;
	}

	public Date getDataMor() {
		return dataMor;
	}

	public void setDataMor(Date dataMor) {
		this.dataMor = dataMor;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIdExtComuneEmi() {
		return idExtComuneEmi;
	}

	public void setIdExtComuneEmi(String idExtComuneEmi) {
		this.idExtComuneEmi = idExtComuneEmi;
	}

	public String getIdExtComuneImm() {
		return idExtComuneImm;
	}

	public void setIdExtComuneImm(String idExtComuneImm) {
		this.idExtComuneImm = idExtComuneImm;
	}

	public String getIdExtComuneMor() {
		return idExtComuneMor;
	}

	public void setIdExtComuneMor(String idExtComuneMor) {
		this.idExtComuneMor = idExtComuneMor;
	}

	public String getIdExtComuneNascita() {
		return idExtComuneNascita;
	}

	public void setIdExtComuneNascita(String idExtComuneNascita) {
		this.idExtComuneNascita = idExtComuneNascita;
	}

	public String getIdExtProvinciaEmi() {
		return idExtProvinciaEmi;
	}

	public void setIdExtProvinciaEmi(String idExtProvinciaEmi) {
		this.idExtProvinciaEmi = idExtProvinciaEmi;
	}

	public String getIdExtProvinciaImm() {
		return idExtProvinciaImm;
	}

	public void setIdExtProvinciaImm(String idExtProvinciaImm) {
		this.idExtProvinciaImm = idExtProvinciaImm;
	}

	public String getIdExtProvinciaMor() {
		return idExtProvinciaMor;
	}

	public void setIdExtProvinciaMor(String idExtProvinciaMor) {
		this.idExtProvinciaMor = idExtProvinciaMor;
	}

	public String getIdExtProvinciaNascita() {
		return idExtProvinciaNascita;
	}

	public void setIdExtProvinciaNascita(String idExtProvinciaNascita) {
		this.idExtProvinciaNascita = idExtProvinciaNascita;
	}

	public String getIdExtStato() {
		return idExtStato;
	}

	public void setIdExtStato(String idExtStato) {
		this.idExtStato = idExtStato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdExtDPersona() {
		return idExtDPersona;
	}

	public void setIdExtDPersona(String idExtDPersona) {
		this.idExtDPersona = idExtDPersona;
	}

	public Integer getnOrd() {
		return nOrd;
	}

	public void setnOrd(Integer nOrd) {
		this.nOrd = nOrd;
	}

	public String getIndirizzoEmi() {
		return indirizzoEmi;
	}

	public void setIndirizzoEmi(String indirizzoEmi) {
		this.indirizzoEmi = indirizzoEmi;
	}


}