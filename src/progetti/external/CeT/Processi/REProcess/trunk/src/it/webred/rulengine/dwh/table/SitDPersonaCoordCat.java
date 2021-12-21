package it.webred.rulengine.dwh.table;

public class SitDPersonaCoordCat extends TabellaDwh
{

	private String descrizioneVia;
	private String tipoCivico;
	private String lotto;
	private String isolato;
	private String scala;
	private String numInt;
	private String piano;
	private String cap;
	private String cittadinanza;
	private String titoloStudio;
	private String statoCivile;
	private String flgIntestatarioScheda;
	private String codEventoVariazione;
	private String foglio;
	private String mappale;
	private String subalterno;
	private String nomeFile;
	
	@Override
	public String getValueForCtrHash()
	{
		return descrizioneVia + 
				tipoCivico +
				lotto +
				isolato +
				scala +
				numInt +
				piano +
				cap +
				cittadinanza +
				titoloStudio +
				statoCivile +
				flgIntestatarioScheda +
				codEventoVariazione +
				foglio +
				mappale +
				subalterno +
				nomeFile;
	}

	public String getDescrizioneVia() {
		return descrizioneVia;
	}

	public void setDescrizioneVia(String descrizioneVia) {
		this.descrizioneVia = descrizioneVia;
	}

	public String getTipoCivico() {
		return tipoCivico;
	}

	public void setTipoCivico(String tipoCivico) {
		this.tipoCivico = tipoCivico;
	}

	public String getLotto() {
		return lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public String getIsolato() {
		return isolato;
	}

	public void setIsolato(String isolato) {
		this.isolato = isolato;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public String getFlgIntestatarioScheda() {
		return flgIntestatarioScheda;
	}

	public void setFlgIntestatarioScheda(String flgIntestatarioScheda) {
		this.flgIntestatarioScheda = flgIntestatarioScheda;
	}

	public String getCodEventoVariazione() {
		return codEventoVariazione;
	}

	public void setCodEventoVariazione(String codEventoVariazione) {
		this.codEventoVariazione = codEventoVariazione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	
}

