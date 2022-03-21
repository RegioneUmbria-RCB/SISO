package it.webred.rulengine.brick.idoneitaAlloggi.bean;

import java.io.Serializable;

public class Anagrafe implements Serializable{
	
	private static final long serialVersionUID = -2453322742213612356L;
	
	private String id;
	private String idExt;
	private String codAnagrafe;
	private String tipoSoggetto;
	private String famiglia;
	private String tipoParentela;
	private String sezElettorali;
	private String codFiscale;
	private String cognome;
	private String nome;
	private String dataNascita;
	private String sesso;
	private String comuneNascita;
	private String cittadinanza;
	private String codiceNazionale;
	private String prefisso = "";
	private String nomeVia = "";
	private String civico = "";
	private int idCivico = 0;

	public Anagrafe() {
		// TODO Auto-generated constructor stub
	}//-------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getCodAnagrafe() {
		return codAnagrafe;
	}

	public void setCodAnagrafe(String codAnagrafe) {
		this.codAnagrafe = codAnagrafe;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getFamiglia() {
		return famiglia;
	}

	public void setFamiglia(String famiglia) {
		this.famiglia = famiglia;
	}

	public String getTipoParentela() {
		return tipoParentela;
	}

	public void setTipoParentela(String tipoParentela) {
		this.tipoParentela = tipoParentela;
	}

	public String getSezElettorali() {
		return sezElettorali;
	}

	public void setSezElettorali(String sezElettorali) {
		this.sezElettorali = sezElettorali;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getCodiceNazionale() {
		return codiceNazionale;
	}

	public void setCodiceNazionale(String codiceNazionale) {
		this.codiceNazionale = codiceNazionale;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getNomeVia() {
		return nomeVia;
	}

	public void setNomeVia(String nomeVia) {
		this.nomeVia = nomeVia;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public int getIdCivico() {
		return idCivico;
	}

	public void setIdCivico(int idCivico) {
		this.idCivico = idCivico;
	}
	
	

}

