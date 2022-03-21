package it.roma.comune.servizi.dto;

public class PersonaCompleta implements java.io.Serializable{
	private String Sesso;
	private String Nome;
	private String Cognome;
	private String CodiceFiscale;
	private String CodiceStatoISTAT;
	private String DescrizioneCittadinanza;
	private String StatoCivile;
	
	public String getSesso() {
		return Sesso;
	}
	public void setSesso(String sesso) {
		Sesso = sesso;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public String getCodiceFiscale() {
		return CodiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		CodiceFiscale = codiceFiscale;
	}
	public String getCodiceStatoISTAT() {
		return CodiceStatoISTAT;
	}
	public void setCodiceStatoISTAT(String codiceStatoISTAT) {
		CodiceStatoISTAT = codiceStatoISTAT;
	}
	public String getDescrizioneCittadinanza() {
		return DescrizioneCittadinanza;
	}
	public void setDescrizioneCittadinanza(String descrizioneCittadinanza) {
		DescrizioneCittadinanza = descrizioneCittadinanza;
	}
	public String getStatoCivile() {
		return StatoCivile;
	}
	public void setStatoCivile(String statoCivile) {
		StatoCivile = statoCivile;
	}
}
