package it.roma.comune.servizi.dto;

public class Nascita implements java.io.Serializable{
  /***
   *  <DatadiNascita>
                  <Giorno>13</Giorno>
                  <Mese>11</Mese>
                  <Anno>1976</Anno>
                </DatadiNascita>
                <LuogodiNascita>
                  <NomeComune>Roma</NomeComune>
                  <SiglaProvincia>Rm</SiglaProvincia>
                  <SiglaStato>I</SiglaStato>
                  <NomeStato>Italia</NomeStato>
                  <CodiceComuneISTAT>091</CodiceComuneISTAT>
                  <CodiceProvinciaISTAT>058</CodiceProvinciaISTAT>
                  <CodiceStatoISTAT>100</CodiceStatoISTAT>
                </LuogodiNascita>
   * ***/
	private Genitori genitori;

	private String Giorno;

	public Genitori getGenitori() {
	return genitori;
}
public void setGenitori(Genitori genitori) {
	this.genitori = genitori;
}
	 public String getGiorno() {
	return Giorno;
}
public void setGiorno(String giorno) {
	Giorno = giorno;
}
public String getMese() {
	return Mese;
}
public void setMese(String mese) {
	Mese = mese;
}
public String getAnno() {
	return Anno;
}
public void setAnno(String anno) {
	Anno = anno;
}
public String getNomeComune() {
	return NomeComune;
}
public void setNomeComune(String nomeComune) {
	NomeComune = nomeComune;
}
public String getSiglaProvincia() {
	return SiglaProvincia;
}
public void setSiglaProvincia(String siglaProvincia) {
	SiglaProvincia = siglaProvincia;
}
public String getNomeStato() {
	return NomeStato;
}
public void setNomeStato(String nomeStato) {
	NomeStato = nomeStato;
}
public String getCodiceComuneISTAT() {
	return CodiceComuneISTAT;
}
public void setCodiceComuneISTAT(String codiceComuneISTAT) {
	CodiceComuneISTAT = codiceComuneISTAT;
}
public String getCodiceProvinciaISTAT() {
	return CodiceProvinciaISTAT;
}
public void setCodiceProvinciaISTAT(String codiceProvinciaISTAT) {
	CodiceProvinciaISTAT = codiceProvinciaISTAT;
}
public String getCodiceStatoISTAT() {
	return CodiceStatoISTAT;
}
public void setCodiceStatoISTAT(String codiceStatoISTAT) {
	CodiceStatoISTAT = codiceStatoISTAT;
}
	private String Mese;
	private String Anno;
	private String NomeComune;
	private String SiglaProvincia;
	private String NomeStato;
	private String CodiceComuneISTAT;
	private String CodiceProvinciaISTAT;
	private String CodiceStatoISTAT;
}
