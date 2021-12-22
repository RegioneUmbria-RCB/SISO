package it.webred.cs.csa.utils.bean.report.dto;


public class TriagePdfDTO extends BasePdfDTO {

	private String morbilitaLivello = EMPTY_VALUE;
	private String morbilitaPunteggio = EMPTY_VALUE;
	private String alimentazioneLivello = EMPTY_VALUE;
	private String alimentazionePunteggio = EMPTY_VALUE;
	private String alvoDiuresiLivello = EMPTY_VALUE;
	private String alvoDiuresiPunteggio = EMPTY_VALUE;
	private String mobilitaLivello = EMPTY_VALUE;
	private String mobilitaPunteggio = EMPTY_VALUE;
	private String igienePersonaleLivello = EMPTY_VALUE;
	private String igienePersonalePunteggio = EMPTY_VALUE;
	private String statoMentaleLivello = EMPTY_VALUE;
	private String statoMentalePunteggio = EMPTY_VALUE;
	private String conChiViveLivello = EMPTY_VALUE;
	private String conChiVivePunteggio = EMPTY_VALUE;
	private String assistenzaDirettaLivello = EMPTY_VALUE;
	private String assistenzaDirettaPunteggio = EMPTY_VALUE;

	// Anagrafica
	private String nome = EMPTY_VALUE;
	private String cognome = EMPTY_VALUE;
	private String codiceFiscale = EMPTY_VALUE;
	private String medicoCurante = EMPTY_VALUE;
	private String accompagnamento = EMPTY_VALUE;
	private String isee = EMPTY_VALUE;
	private String dataInserimento = EMPTY_VALUE;
	private String totale = EMPTY_VALUE;

	private String nFigli = EMPTY_VALUE;
	private String nFiglie = EMPTY_VALUE;
	private String nSorelle = EMPTY_VALUE;
	private String nFratelli = EMPTY_VALUE;
	protected String valFamRating = EMPTY_VALUE; // Descrizione[Tooltip]

	// Rete Sociale
	protected String relAltriSogg = EMPTY_VALUE;
	protected String relAltriSoggRetr = EMPTY_VALUE;
	protected String valReteSocRating = EMPTY_VALUE; // Descrizione[Tooltip]

	// Abitazione
	protected String tipAbit = EMPTY_VALUE;
	protected String ascensore = EMPTY_VALUE;
	protected String portineria = EMPTY_VALUE;
	protected String titoloGodimento = EMPTY_VALUE;
	protected String composizione = EMPTY_VALUE;
	protected String riscaldamento = EMPTY_VALUE;
	protected String gabinetto = EMPTY_VALUE;
	protected String fornitos; // Descrizioni attive
	protected String elettrodomestici; // Descrizioni attive
	protected String altriProblemi; // Descrizioni attive
	protected String valAdAbitazione; // Descrizione[Tooltip]
	protected String valUbAbitazione; // Descrizione[Tooltip]
	
	protected String dataStampa = EMPTY_VALUE;
	protected String dataValutazioneMultidimensionale;
	protected boolean existsMultidim = false;
	protected String user = EMPTY_VALUE;

		
	public String getMorbilitaLivello() {
		return morbilitaLivello;
	}

	public void setMorbilitaLivello(String morbilitaLivello) {
		this.morbilitaLivello = morbilitaLivello;
	}

	public String getMorbilitaPunteggio() {
		return morbilitaPunteggio;
	}

	public void setMorbilitaPunteggio(String morbilitaPunteggio) {
		this.morbilitaPunteggio = morbilitaPunteggio;
	}

	public String getAlimentazioneLivello() {
		return alimentazioneLivello;
	}

	public void setAlimentazioneLivello(String alimentazioneLivello) {
		this.alimentazioneLivello = alimentazioneLivello;
	}

	public String getAlimentazionePunteggio() {
		return alimentazionePunteggio;
	}

	public void setAlimentazionePunteggio(String alimentazionePunteggio) {
		this.alimentazionePunteggio = alimentazionePunteggio;
	}

	public String getAlvoDiuresiLivello() {
		return alvoDiuresiLivello;
	}

	public void setAlvoDiuresiLivello(String alvoDiuresiLivello) {
		this.alvoDiuresiLivello = alvoDiuresiLivello;
	}

	public String getAlvoDiuresiPunteggio() {
		return alvoDiuresiPunteggio;
	}

	public void setAlvoDiuresiPunteggio(String alvoDiuresiPunteggio) {
		this.alvoDiuresiPunteggio = alvoDiuresiPunteggio;
	}

	public String getMobilitaLivello() {
		return mobilitaLivello;
	}

	public void setMobilitaLivello(String mobilitaLivello) {
		this.mobilitaLivello = mobilitaLivello;
	}

	public String getMobilitaPunteggio() {
		return mobilitaPunteggio;
	}

	public void setMobilitaPunteggio(String mobilitaPunteggio) {
		this.mobilitaPunteggio = mobilitaPunteggio;
	}

	public String getIgienePersonaleLivello() {
		return igienePersonaleLivello;
	}

	public void setIgienePersonaleLivello(String igienePersonaleLivello) {
		this.igienePersonaleLivello = igienePersonaleLivello;
	}

	public String getIgienePersonalePunteggio() {
		return igienePersonalePunteggio;
	}

	public void setIgienePersonalePunteggio(String igienePersonalePunteggio) {
		this.igienePersonalePunteggio = igienePersonalePunteggio;
	}

	public String getStatoMentaleLivello() {
		return statoMentaleLivello;
	}

	public void setStatoMentaleLivello(String statoMentaleLivello) {
		this.statoMentaleLivello = statoMentaleLivello;
	}

	public String getStatoMentalePunteggio() {
		return statoMentalePunteggio;
	}

	public void setStatoMentalePunteggio(String statoMentalePunteggio) {
		this.statoMentalePunteggio = statoMentalePunteggio;
	}

	public String getConChiViveLivello() {
		return conChiViveLivello;
	}

	public void setConChiViveLivello(String conChiViveLivello) {
		this.conChiViveLivello = conChiViveLivello;
	}

	public String getConChiVivePunteggio() {
		return conChiVivePunteggio;
	}

	public void setConChiVivePunteggio(String conChiVivePunteggio) {
		this.conChiVivePunteggio = conChiVivePunteggio;
	}

	public String getAssistenzaDirettaLivello() {
		return assistenzaDirettaLivello;
	}

	public void setAssistenzaDirettaLivello(String assistenzaDirettaLivello) {
		this.assistenzaDirettaLivello = assistenzaDirettaLivello;
	}

	public String getAssistenzaDirettaPunteggio() {
		return assistenzaDirettaPunteggio;
	}

	public void setAssistenzaDirettaPunteggio(String assistenzaDirettaPunteggio) {
		this.assistenzaDirettaPunteggio = assistenzaDirettaPunteggio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getMedicoCurante() {
		return medicoCurante;
	}

	public void setMedicoCurante(String medicoCurante) {
		this.medicoCurante = medicoCurante;
	}

	public String getIsee() {
		return isee;
	}

	public void setIsee(String isee) {
		this.isee = isee;
	}

	public String getTotale() {
		return totale;
	}

	public void setTotale(String totale) {
		this.totale = totale;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public String getnFigli() {
		return nFigli;
	}

	public void setnFigli(String nFigli) {
		this.nFigli = nFigli;
	}

	public String getnFiglie() {
		return nFiglie;
	}

	public void setnFiglie(String nFiglie) {
		this.nFiglie = nFiglie;
	}

	public String getnSorelle() {
		return nSorelle;
	}

	public void setnSorelle(String nSorelle) {
		this.nSorelle = nSorelle;
	}

	public String getnFratelli() {
		return nFratelli;
	}

	public void setnFratelli(String nFratelli) {
		this.nFratelli = nFratelli;
	}

	public String getValFamRating() {
		return valFamRating;
	}

	public void setValFamRating(String valFamRating) {
		this.valFamRating = valFamRating;
	}

	public String getRelAltriSogg() {
		return relAltriSogg;
	}

	public void setRelAltriSogg(String relAltriSogg) {
		this.relAltriSogg = relAltriSogg;
	}

	public String getRelAltriSoggRetr() {
		return relAltriSoggRetr;
	}

	public void setRelAltriSoggRetr(String relAltriSoggRetr) {
		this.relAltriSoggRetr = relAltriSoggRetr;
	}

	public String getValReteSocRating() {
		return valReteSocRating;
	}

	public void setValReteSocRating(String valReteSocRating) {
		this.valReteSocRating = valReteSocRating;
	}

	public String getTipAbit() {
		return tipAbit;
	}

	public void setTipAbit(String tipAbit) {
		this.tipAbit = tipAbit;
	}

	public String getAscensore() {
		return ascensore;
	}

	public void setAscensore(String ascensore) {
		this.ascensore = ascensore;
	}

	public String getPortineria() {
		return portineria;
	}

	public void setPortineria(String portineria) {
		this.portineria = portineria;
	}

	public String getTitoloGodimento() {
		return titoloGodimento;
	}

	public void setTitoloGodimento(String titoloGodimento) {
		this.titoloGodimento = titoloGodimento;
	}

	public String getComposizione() {
		return composizione;
	}

	public void setComposizione(String composizione) {
		this.composizione = composizione;
	}

	public String getRiscaldamento() {
		return riscaldamento;
	}

	public void setRiscaldamento(String riscaldamento) {
		this.riscaldamento = riscaldamento;
	}

	public String getGabinetto() {
		return gabinetto;
	}

	public void setGabinetto(String gabinetto) {
		this.gabinetto = gabinetto;
	}

	public String getFornitos() {
		return fornitos;
	}

	public void setFornitos(String fornitos) {
		this.fornitos = fornitos;
	}

	public String getElettrodomestici() {
		return elettrodomestici;
	}

	public void setElettrodomestici(String elettrodomestici) {
		this.elettrodomestici = elettrodomestici;
	}

	public String getAltriProblemi() {
		return altriProblemi;
	}

	public void setAltriProblemi(String altriProblemi) {
		this.altriProblemi = altriProblemi;
	}

	public String getValAdAbitazione() {
		return valAdAbitazione;
	}

	public void setValAdAbitazione(String valAdAbitazione) {
		this.valAdAbitazione = valAdAbitazione;
	}

	public String getValUbAbitazione() {
		return valUbAbitazione;
	}

	public void setValUbAbitazione(String valUbAbitazione) {
		this.valUbAbitazione = valUbAbitazione;
	}

	public String getAccompagnamento() {
		return accompagnamento;
	}

	public void setAccompagnamento(String accompagnamento) {
		this.accompagnamento = accompagnamento;
	}

	public String getDataStampa() {
		return dataStampa;
	}

	public void setDataStampa(String dataStampa) {
		this.dataStampa = dataStampa;
	}

	public boolean isExistsMultidim() {
		return existsMultidim;
	}

	public void setExistsMultidim(boolean existsMultidim) {
		this.existsMultidim = existsMultidim;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDataValutazioneMultidimensionale() {
		return dataValutazioneMultidimensionale;
	}

	public void setDataValutazioneMultidimensionale(
			String dataValutazioneMultidimensionale) {
		this.dataValutazioneMultidimensionale = dataValutazioneMultidimensionale;
	}

}
