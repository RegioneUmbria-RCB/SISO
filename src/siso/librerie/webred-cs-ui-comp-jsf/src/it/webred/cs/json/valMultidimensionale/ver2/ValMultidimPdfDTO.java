package it.webred.cs.json.valMultidimensionale.ver2;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;


public class ValMultidimPdfDTO extends ReportPdfDTO {
	
	private String denominazione="EMPTY_VALUE";
	
	//Situazione Attuale
	private String momValTipo= EMPTY_VALUE;
	private String momValLuogo= EMPTY_VALUE;
	private String momValRef= EMPTY_VALUE;
	private String momValTel= EMPTY_VALUE;
	
	//Rete Familiare
	private String nFigli= EMPTY_VALUE;
	private String nFiglie = EMPTY_VALUE;
	private String nSorelle= EMPTY_VALUE;
	private String nFratelli= EMPTY_VALUE;
	//protected ArrayList<AnagraficaMultidimAnzBean> famAnaConvs= new ArrayList<AnagraficaMultidimAnzBean>();

	//protected ArrayList<AnagraficaMultidimAnzBean> famAnaNonConvs= new ArrayList<AnagraficaMultidimAnzBean>();
	
	//Rete Sociale
	protected String relAltriSogg= EMPTY_VALUE;
	protected String relAltriSoggRetr= EMPTY_VALUE;
	//protected ArrayList<AnagraficaMultidimAnzBean> famAnaAltris= new ArrayList<AnagraficaMultidimAnzBean>();
	
	//private String[] patologie; //Valore: descrizione estesa (solo per quelli checkati)
	protected String noteSanitarie = EMPTY_VALUE;;
	
	protected String valSalFisica = EMPTY_VALUE; //Descrizione[Tooltip]
	protected String valSalPsichica = EMPTY_VALUE; //Descrizione[Tooltip]
	protected String valAutonomiaDomestica = EMPTY_VALUE; //Descrizione[Tooltip]
	protected String valAutonomiaExtraDom = EMPTY_VALUE; //Descrizione[Tooltip]
	
	//Abitazione
	protected String tipAbit= EMPTY_VALUE;
	protected String ascensore= EMPTY_VALUE;
	protected String portineria= EMPTY_VALUE;
	protected String titoloGodimento= EMPTY_VALUE;
	protected String composizione= EMPTY_VALUE;
	protected String numVani = EMPTY_VALUE;
	protected String gestoreProprietario = EMPTY_VALUE;
	protected String riscaldamento= EMPTY_VALUE;
	protected String gabinetto= EMPTY_VALUE;
	protected String fornitos;   		//Descrizioni attive
	protected String elettrodomestici; 	//Descrizioni attive 
	protected String altriProblemi;		//Descrizioni attive
	protected String valAdAbitazione;	//Descrizione[Tooltip]
	protected String valUbAbitazione;	//Descrizione[Tooltip]
	
	//Situazione Economica
	protected String isee;
	protected String iseeRipa;
	
	protected String mappaRisorse = EMPTY_VALUE;
	protected boolean renderBarthel=false;
	
	
	public String getMomValTipo() {
		return momValTipo;
	}
	public String getMomValLuogo() {
		return momValLuogo;
	}
	public String getMomValRef() {
		return momValRef;
	}
	public String getMomValTel() {
		return momValTel;
	}
	public String getnFigli() {
		return nFigli;
	}
	public String getnFiglie() {
		return nFiglie;
	}
	public String getnSorelle() {
		return nSorelle;
	}
	public String getnFratelli() {
		return nFratelli;
	}
	
	public String getRelAltriSogg() {
		return relAltriSogg;
	}
	public String getRelAltriSoggRetr() {
		return relAltriSoggRetr;
	}

	public String getNoteSanitarie() {
		return noteSanitarie;
	}
	public String getValSalFisica() {
		return valSalFisica;
	}
	public String getValSalPsichica() {
		return valSalPsichica;
	}
	
	public String getValAutonomiaDomestica() {
		return valAutonomiaDomestica;
	}
	public String getValAutonomiaExtraDom() {
		return valAutonomiaExtraDom;
	}
	public String getTipAbit() {
		return tipAbit;
	}
	public String getAscensore() {
		return ascensore;
	}
	public String getPortineria() {
		return portineria;
	}
	public String getTitoloGodimento() {
		return titoloGodimento;
	}
	public String getComposizione() {
		return composizione;
	}
	public String getNumVani() {
		return numVani;
	}
	public String getGestoreProprietario() {
		return gestoreProprietario;
	}
	public String getRiscaldamento() {
		return riscaldamento;
	}
	public String getGabinetto() {
		return gabinetto;
	}
	public String getValAdAbitazione() {
		return valAdAbitazione;
	}
	public String getValUbAbitazione() {
		return valUbAbitazione;
	}
	public String getIsee() {
		return isee;
	}
	public String getIseeRipa() {
		return iseeRipa;
	}
	
	public void setMomValTipo(String momValTipo) {
		this.momValTipo = momValTipo;
	}
	public void setMomValLuogo(String momValLuogo) {
		this.momValLuogo = momValLuogo;
	}
	public void setMomValRef(String momValRef) {
		this.momValRef = momValRef;
	}
	public void setMomValTel(String momValTel) {
		this.momValTel = momValTel;
	}
	public void setnFigli(String nFigli) {
		this.nFigli = nFigli;
	}
	public void setnFiglie(String nFiglie) {
		this.nFiglie = nFiglie;
	}
	public void setnSorelle(String nSorelle) {
		this.nSorelle = nSorelle;
	}
	public void setnFratelli(String nFratelli) {
		this.nFratelli = nFratelli;
	}
	
	public void setRelAltriSogg(String relAltriSogg) {
		this.relAltriSogg = relAltriSogg;
	}
	public void setRelAltriSoggRetr(String relAltriSoggRetr) {
		this.relAltriSoggRetr = relAltriSoggRetr;
	}
	
	public void setNoteSanitarie(String noteSanitarie) {
		this.noteSanitarie = noteSanitarie;
	}
	public void setValSalFisica(String valSalFisica) {
		this.valSalFisica = valSalFisica;
	}
	public void setValSalPsichica(String valSalPsichica) {
		this.valSalPsichica = valSalPsichica;
	}
	
	public void setValAutonomiaDomestica(String valAutonomiaDomestica) {
		this.valAutonomiaDomestica = valAutonomiaDomestica;
	}
	public void setValAutonomiaExtraDom(String valAutonomiaExtraDom) {
		this.valAutonomiaExtraDom = valAutonomiaExtraDom;
	}
	public void setTipAbit(String tipAbit) {
		this.tipAbit = tipAbit;
	}
	public void setAscensore(String ascensore) {
		this.ascensore = ascensore;
	}
	public void setPortineria(String portineria) {
		this.portineria = portineria;
	}
	public void setTitoloGodimento(String titoloGodimento) {
		this.titoloGodimento = titoloGodimento;
	}
	public void setComposizione(String composizione) {
		this.composizione = composizione;
	}
	public void setNumVani(String numVani) {
		this.numVani = numVani;
	}
	public void setGestoreProprietario(String gestoreProprietario) {
		this.gestoreProprietario = gestoreProprietario;
	}
	public void setRiscaldamento(String riscaldamento) {
		this.riscaldamento = riscaldamento;
	}
	public void setGabinetto(String gabinetto) {
		this.gabinetto = gabinetto;
	}
	public void setValAdAbitazione(String valAdAbitazione) {
		this.valAdAbitazione = valAdAbitazione;
	}
	public void setValUbAbitazione(String valUbAbitazione) {
		this.valUbAbitazione = valUbAbitazione;
	}
	public void setIsee(String isee) {
		this.isee = isee;
	}
	public void setIseeRipa(String iseeRipa) {
		this.iseeRipa = iseeRipa;
	}
	
	public String getFornitos() {
		return fornitos;
	}
	public String getElettrodomestici() {
		return elettrodomestici;
	}
	public String getAltriProblemi() {
		return altriProblemi;
	}
	public void setFornitos(String fornitos) {
		this.fornitos = fornitos;
	}
	public void setElettrodomestici(String elettrodomestici) {
		this.elettrodomestici = elettrodomestici;
	}
	public void setAltriProblemi(String altriProblemi) {
		this.altriProblemi = altriProblemi;
	}
	public String getMappaRisorse() {
		return mappaRisorse;
	}
	public void setMappaRisorse(String mappaRisorse) {
		this.mappaRisorse = mappaRisorse;
	}
	public boolean isRenderBarthel() {
		return renderBarthel;
	}
	public void setRenderBarthel(boolean renderBarthel) {
		this.renderBarthel = renderBarthel;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
}
