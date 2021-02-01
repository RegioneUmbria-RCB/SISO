package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class AffidoPdfDTO extends ReportPdfDTO {

	
	private String statoAffido = EMPTY_VALUE;

	private String tipologiaAffido = EMPTY_VALUE;
	
	private String affidoProf= EMPTY_VALUE;
	
	private String adottabile = EMPTY_VALUE;
	
	private String numDecreto = EMPTY_VALUE;
	
	private String dataDecreto = EMPTY_VALUE;
	
	private String naturaAccoglienza = EMPTY_VALUE;
	
	private String formeAffidamento = EMPTY_VALUE;

	private String collocamento = EMPTY_VALUE;

	private String entitaAffido = EMPTY_VALUE;

	private String affidoParziale = EMPTY_VALUE;

	private String convivGenitoriConAffid = EMPTY_VALUE;

	private String minoreStranieroNonAccom = EMPTY_VALUE;

	private String stessaCulturaMinoreFamig = EMPTY_VALUE;
	
	private String affidamentoLungoTermine = EMPTY_VALUE; //SISO-1172

	private String disabilitaDuranteAffi = EMPTY_VALUE;

	private String frequenzaContattiMinFamOrig = EMPTY_VALUE;
	
	private String modalitaRapportoNucleoOrigine = EMPTY_VALUE;
	
    private String frequenzaContattiAff = EMPTY_VALUE;
	
	private String modalitaRapportoOrigAff = EMPTY_VALUE;
	
	private String modalitaRappEsperienzaOrig  = EMPTY_VALUE;
	
	private String modalitaRappEsperienzaFamAff = EMPTY_VALUE;
	
	private String noteGestioneSanita = EMPTY_VALUE;


//	private String esitoAffido = EMPTY_VALUE;
//
//	private String esitoAffidoAltro = EMPTY_VALUE;
	
	private String impFamigliaOrig = EMPTY_VALUE;

	private String impFamigliaAffid = EMPTY_VALUE;

	private String impMinore = EMPTY_VALUE;

	private String impServizioSoc = EMPTY_VALUE;

	private String impAltriSoggetti = EMPTY_VALUE;

	private String famOrigAllontanam = EMPTY_VALUE;

	private String famOrigGenSconosc = EMPTY_VALUE;

	private String famOrigFuoriReg = EMPTY_VALUE;

	private String famOrigInterv = EMPTY_VALUE;
	
	private String famOrigContributoAlleSpese = EMPTY_VALUE;
	
	private String famOrigNoteContributoAlleSpese = EMPTY_VALUE;
	
	private JRBeanCollectionDataSource tabella_1;
	
	private JRBeanCollectionDataSource tabella_2;
	
	private JRBeanCollectionDataSource tabella_3;

	private String famAffCaratter = EMPTY_VALUE;

	private String famAffMotivaz = EMPTY_VALUE;

	private String famAffInBancaDati = EMPTY_VALUE;

	private String famAffIdoneita = EMPTY_VALUE;

	private String famAffAffidatari = EMPTY_VALUE;

	private String famAffConoscDaMin = EMPTY_VALUE;

	private String famAffFuoriReg = EMPTY_VALUE;
	
	private String prezenzaRetiFam = EMPTY_VALUE;

	private String SUBREPORT_DIR;
	
	public String getStatoAffido() {
		return statoAffido;
	}

	public void setStatoAffido(String statoAffido) {
		this.statoAffido = statoAffido;
	}

	public String getTipologiaAffido() {
		return tipologiaAffido;
	}

	public void setTipologiaAffido(String tipologiaAffido) {
		this.tipologiaAffido = tipologiaAffido;
	}

	public String getAffidoProf() {
		return affidoProf;
	}

	public void setAffidoProf(String affidoProf) {
		this.affidoProf = affidoProf;
	}

	public String getAdottabile() {
		return adottabile;
	}

	public void setAdottabile(String adottabile) {
		this.adottabile = adottabile;
	}

	public String getNumDecreto() {
		return numDecreto;
	}

	public void setNumDecreto(String numDecreto) {
		this.numDecreto = numDecreto;
	}

	public String getDataDecreto() {
		return dataDecreto;
	}

	public void setDataDecreto(String dataDecreto) {
		this.dataDecreto = dataDecreto;
	}

	public String getNaturaAccoglienza() {
		return naturaAccoglienza;
	}

	public void setNaturaAccoglienza(String naturaAccoglienza) {
		this.naturaAccoglienza = naturaAccoglienza;
	}
	//SISO-1172
	public String getFormeAffidamento() {
		return formeAffidamento;
	}

	public void setFormeAffidamento(String formeAffidamento) {
		this.formeAffidamento = formeAffidamento;
	}

	public String getCollocamento() {
		return collocamento;
	}
	
	public void setCollocamento(String collocamento) {
		this.collocamento = collocamento;
	}

	public String getEntitaAffido() {
		return entitaAffido;
	}

	public void setEntitaAffido(String entitaAffido) {
		this.entitaAffido = entitaAffido;
	}

	public String getAffidoParziale() {
		return affidoParziale;
	}

	public void setAffidoParziale(String affidoParziale) {
		this.affidoParziale = affidoParziale;
	}

	public String getConvivGenitoriConAffid() {
		return convivGenitoriConAffid;
	}

	public void setConvivGenitoriConAffid(String convivGenitoriConAffid) {
		this.convivGenitoriConAffid = convivGenitoriConAffid;
	}

	public String getMinoreStranieroNonAccom() {
		return minoreStranieroNonAccom;
	}

	public void setMinoreStranieroNonAccom(String minoreStranieroNonAccom) {
		this.minoreStranieroNonAccom = minoreStranieroNonAccom;
	}

	public String getStessaCulturaMinoreFamig() {
		return stessaCulturaMinoreFamig;
	}

	public void setStessaCulturaMinoreFamig(String stessaCulturaMinoreFamig) {
		this.stessaCulturaMinoreFamig = stessaCulturaMinoreFamig;
	}

	public String getDisabilitaDuranteAffi() {
		return disabilitaDuranteAffi;
	}

	public void setDisabilitaDuranteAffi(String disabilitaDuranteAffi) {
		this.disabilitaDuranteAffi = disabilitaDuranteAffi;
	}

	public String getFrequenzaContattiMinFamOrig() {
		return frequenzaContattiMinFamOrig;
	}

	public void setFrequenzaContattiMinFamOrig(String frequenzaContattiMinFamOrig) {
		this.frequenzaContattiMinFamOrig = frequenzaContattiMinFamOrig;
	}

//	public String getEsitoAffido() {
//		return esitoAffido;
//	}
//
//	public void setEsitoAffido(String esitoAffido) {
//		this.esitoAffido = esitoAffido;
//	}
//
//	public String getEsitoAffidoAltro() {
//		return esitoAffidoAltro;
//	}
//
//	public void setEsitoAffidoAltro(String esitoAffidoAltro) {
//		this.esitoAffidoAltro = esitoAffidoAltro;
//	}

//	public String getSoggettoCognome() {
//		return soggettoCognome;
//	}
//
//	public void setSoggettoCognome(String soggettoCognome) {
//		this.soggettoCognome = soggettoCognome;
//	}
//
//	public String getSoggettoNome() {
//		return soggettoNome;
//	}
//
//	public void setSoggettoNome(String soggettoNome) {
//		this.soggettoNome = soggettoNome;
//	}
//
//	public String getSoggettoRuolo() {
//		return soggettoRuolo;
//	}
//
//	public void setSoggettoRuolo(String soggettoRuolo) {
//		this.soggettoRuolo = soggettoRuolo;
//	}

	public String getImpFamigliaOrig() {
		return impFamigliaOrig;
	}

	public void setImpFamigliaOrig(String impFamigliaOrig) {
		this.impFamigliaOrig = impFamigliaOrig;
	}

	public String getImpFamigliaAffid() {
		return impFamigliaAffid;
	}

	public void setImpFamigliaAffid(String impFamigliaAffid) {
		this.impFamigliaAffid = impFamigliaAffid;
	}

	public String getImpMinore() {
		return impMinore;
	}

	public void setImpMinore(String impMinore) {
		this.impMinore = impMinore;
	}

	public String getImpServizioSoc() {
		return impServizioSoc;
	}

	public void setImpServizioSoc(String impServizioSoc) {
		this.impServizioSoc = impServizioSoc;
	}

	public String getImpAltriSoggetti() {
		return impAltriSoggetti;
	}

	public void setImpAltriSoggetti(String impAltriSoggetti) {
		this.impAltriSoggetti = impAltriSoggetti;
	}

//	public String getFamOrigCognome() {
//		return famOrigCognome;
//	}
//
//	public void setFamOrigCognome(String famOrigCognome) {
//		this.famOrigCognome = famOrigCognome;
//	}
//
//	public String getFamOrigNome() {
//		return famOrigNome;
//	}
//
//	public void setFamOrigNome(String famOrigNome) {
//		this.famOrigNome = famOrigNome;
//	}
//
//	public String getFamOrigCF() {
//		return famOrigCF;
//	}
//
//	public void setFamOrigCF(String famOrigCF) {
//		this.famOrigCF = famOrigCF;
//	}
//
//	public String getFamOrigSesso() {
//		return famOrigSesso;
//	}
//
//	public void setFamOrigSesso(String famOrigSesso) {
//		this.famOrigSesso = famOrigSesso;
//	}
//
//	public String getFamOrigRelazione() {
//		return famOrigRelazione;
//	}
//
//	public void setFamOrigRelazione(String famOrigRelazione) {
//		this.famOrigRelazione = famOrigRelazione;
//	}

	public String getFamOrigAllontanam() {
		return famOrigAllontanam;
	}

	public void setFamOrigAllontanam(String famOrigAllontanam) {
		this.famOrigAllontanam = famOrigAllontanam;
	}

	public String getFamOrigGenSconosc() {
		return famOrigGenSconosc;
	}

	public void setFamOrigGenSconosc(String famOrigGenSconosc) {
		this.famOrigGenSconosc = famOrigGenSconosc;
	}

	public String getFamOrigFuoriReg() {
		return famOrigFuoriReg;
	}

	public void setFamOrigFuoriReg(String famOrigFuoriReg) {
		this.famOrigFuoriReg = famOrigFuoriReg;
	}

	public String getFamOrigInterv() {
		return famOrigInterv;
	}

	public void setFamOrigInterv(String famOrigInterv) {
		this.famOrigInterv = famOrigInterv;
	}

//	public String getFamAffCogn() {
//		return famAffCogn;
//	}
//
//	public void setFamAffCogn(String famAffCogn) {
//		this.famAffCogn = famAffCogn;
//	}
//
//	public String getFamAffNome() {
//		return famAffNome;
//	}
//
//	public void setFamAffNome(String famAffNome) {
//		this.famAffNome = famAffNome;
//	}
//
//	public String getFamAffCF() {
//		return famAffCF;
//	}
//
//	public void setFamAffCF(String famAffCF) {
//		this.famAffCF = famAffCF;
//	}
//
//	public String getFamAffSesso() {
//		return famAffSesso;
//	}
//
//	public void setFamAffSesso(String famAffSesso) {
//		this.famAffSesso = famAffSesso;
//	}
//
//	public String getFamAffRelaz() {
//		return famAffRelaz;
//	}
//
//	public void setFamAffRelaz(String famAffRelaz) {
//		this.famAffRelaz = famAffRelaz;
//	}

	public String getFamAffCaratter() {
		return famAffCaratter;
	}

	public void setFamAffCaratter(String famAffCaratter) {
		this.famAffCaratter = famAffCaratter;
	}

	public String getFamAffMotivaz() {
		return famAffMotivaz;
	}

	public void setFamAffMotivaz(String famAffMotivaz) {
		this.famAffMotivaz = famAffMotivaz;
	}

	public String getFamAffInBancaDati() {
		return famAffInBancaDati;
	}

	public void setFamAffInBancaDati(String famAffInBancaDati) {
		this.famAffInBancaDati = famAffInBancaDati;
	}

	public String getFamAffIdoneita() {
		return famAffIdoneita;
	}

	public void setFamAffIdoneita(String famAffIdoneita) {
		this.famAffIdoneita = famAffIdoneita;
	}

	public String getFamAffAffidatari() {
		return famAffAffidatari;
	}

	public void setFamAffAffidatari(String famAffAffidatari) {
		this.famAffAffidatari = famAffAffidatari;
	}

	public String getFamAffConoscDaMin() {
		return famAffConoscDaMin;
	}

	public void setFamAffConoscDaMin(String famAffConoscDaMin) {
		this.famAffConoscDaMin = famAffConoscDaMin;
	}

	public String getFamAffFuoriReg() {
		return famAffFuoriReg;
	}

	public void setFamAffFuoriReg(String famAffFuoriReg) {
		this.famAffFuoriReg = famAffFuoriReg;
	}

	public String getPrezenzaRetiFam() {
		return prezenzaRetiFam;
	}

	public void setPrezenzaRetiFam(String prezenzaRetiFam) {
		this.prezenzaRetiFam = prezenzaRetiFam;
	}
    
	public String getAffidamentoLungoTermine() {
		return affidamentoLungoTermine;
	}

	public void setAffidamentoLungoTermine(String affidamentoLungoTermine) {
		this.affidamentoLungoTermine = affidamentoLungoTermine;
	}

	
	public String getModalitaRapportoNucleoOrigine() {
		return modalitaRapportoNucleoOrigine;
	}

	public void setModalitaRapportoNucleoOrigine(
			String modalitaRapportoNucleoOrigine) {
		this.modalitaRapportoNucleoOrigine = modalitaRapportoNucleoOrigine;
	}

	
	public String getFrequenzaContattiAff() {
		return frequenzaContattiAff;
	}

	public void setFrequenzaContattiAff(String frequenzaContattiAff) {
		this.frequenzaContattiAff = frequenzaContattiAff;
	}

	public String getModalitaRapportoOrigAff() {
		return modalitaRapportoOrigAff;
	}

	public void setModalitaRapportoOrigAff(String modalitaRapportoOrigAff) {
		this.modalitaRapportoOrigAff = modalitaRapportoOrigAff;
	}

	
	public String getModalitaRappEsperienzaOrig() {
		return modalitaRappEsperienzaOrig;
	}

	public void setModalitaRappEsperienzaOrig(String modalitaRappEsperienzaOrig) {
		this.modalitaRappEsperienzaOrig = modalitaRappEsperienzaOrig;
	}

	public String getModalitaRappEsperienzaFamAff() {
		return modalitaRappEsperienzaFamAff;
	}

	public void setModalitaRappEsperienzaFamAff(String modalitaRappEsperienzaFamAff) {
		this.modalitaRappEsperienzaFamAff = modalitaRappEsperienzaFamAff;
	}

	public String getNoteGestioneSanita() {
		return noteGestioneSanita;
	}

	public void setNoteGestioneSanita(String noteGestioneSanita) {
		this.noteGestioneSanita = noteGestioneSanita;
	}

	public String getFamOrigContributoAlleSpese() {
		return famOrigContributoAlleSpese;
	}

	public void setFamOrigContributoAlleSpese(String famOrigContributoAlleSpese) {
		this.famOrigContributoAlleSpese = famOrigContributoAlleSpese;
	}
 
	public String getFamOrigNoteContributoAlleSpese() {
		return famOrigNoteContributoAlleSpese;
	}

	public void setFamOrigNoteContributoAlleSpese(
			String famOrigNoteContributoAlleSpese) {
		this.famOrigNoteContributoAlleSpese = famOrigNoteContributoAlleSpese;
	}

	public JRBeanCollectionDataSource getTabella_1() {
		return tabella_1;
	}

	public void setTabella_1(JRBeanCollectionDataSource tabella_1) {
		this.tabella_1 = tabella_1;
	}

	public JRBeanCollectionDataSource getTabella_2() {
		return tabella_2;
	}

	public void setTabella_2(JRBeanCollectionDataSource tabella_2) {
		this.tabella_2 = tabella_2;
	}

	public JRBeanCollectionDataSource getTabella_3() {
		return tabella_3;
	}

	public void setTabella_3(JRBeanCollectionDataSource tabella_3) {
		this.tabella_3 = tabella_3;
	}

	public String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}

	public void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}

	
	
}