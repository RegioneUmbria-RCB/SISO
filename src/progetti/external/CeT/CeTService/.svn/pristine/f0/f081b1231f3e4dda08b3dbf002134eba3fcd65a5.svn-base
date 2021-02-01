package it.webred.ct.data.access.basic.docfa.dao;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ClassamentoDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ClasseMaxCategoriaDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ControlloClassamentoConsistenzaDTO;
import it.webred.ct.data.access.basic.docfa.dto.BeniNonCensDTO;
import it.webred.ct.data.access.basic.docfa.dto.DatiDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DatiGeneraliDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DocfaInParteUnoDTO;
import it.webred.ct.data.access.basic.docfa.dto.FoglioMicrozonaDTO;
import it.webred.ct.data.access.basic.docfa.dto.OperatoreDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.ZonaOmiDTO;
import it.webred.ct.data.model.docfa.DocfaAnnotazioni;
import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.data.model.docfa.DocfaDatiGenerali;
import it.webred.ct.data.model.docfa.DocfaDatiMetrici;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaInParteDueH;
import it.webred.ct.data.model.docfa.DocfaIntestati;
import it.webred.ct.data.model.docfa.DocfaPlanimetrie;
import it.webred.ct.data.model.docfa.DocfaUiu;
import it.webred.ct.data.model.docfa.DocfaValori;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface DocfaDAO {
	
	public List<DocfaUiu> getListaDocfaUiuByUI(RicercaOggettoDocfaDTO ro);
	public List<DocfaDatiCensuari> getListaDocfaImmobile(RicercaOggettoDocfaDTO ro);
	public List<DatiDocfaDTO> getListaDatiDocfaUiu(RicercaOggettoDocfaDTO ro) ;
	public List<DatiDocfaDTO> getListaDatiDocfaFPSubNonVal(RicercaOggettoDocfaDTO ro) ;
	public DocfaDatiCensuari getDatiCensuariByUiuDocfa(RicercaOggettoDocfaDTO ro);
	
	public List<DatiGeneraliDocfaDTO> getListaDatiGeneraliFabbricato(RicercaOggettoDocfaDTO ro) ;
	public List<DatiGeneraliDocfaDTO> getListaDatiGeneraliByImmobile(RicercaOggettoDocfaDTO ro);
	
	public List<DocfaDatiMetrici> getDatiMetrici(RicercaOggettoDocfaDTO rs);
	public DocfaInParteDueH getDatiMetriciABC(RicercaOggettoDocfaDTO rs);
	public List<DocfaInParteDueH> getListaDocfaInParteDue(String protocollo, Date fornitura);
	public List<DocfaPlanimetrie> getPlanimetrieDocfa(RicercaOggettoDocfaDTO rs);
	public List<DocfaPlanimetrie> getPlanimetriePerDocfa(RicercaOggettoDocfaDTO rs);
	public List<DocfaPlanimetrie> getPlanimetriePerSezFglNum(RicercaOggettoDocfaDTO rs);
	public List<DocfaDatiCensuari> getDocfaDatiCensuariPerNomePlan(RicercaOggettoDocfaDTO rs);
	
	public List<DocfaUiu> getListaDocfaUiu(RicercaOggettoDocfaDTO ro);
	public List<DocfaUiu> getListaDocfaUiuFabbricatoDocfa(RicercaOggettoDocfaDTO ro);
	
	public List<String> getSuggestionVieDocfaUiu(String via);
	public List<String> getSuggestionCiviciByViaDocfaUiu(String via);
	public List<String> getSuggestionDichiarante(String denominazione);
	public List<Date> getListaForniture();

	
	//Ricerca per DOCFA (protocollo e fornitura)
	public List<DocfaDatiGenerali> getDocfaDatiGenerali(Date fornitura, String protocollo);
	public List<DocfaUiu> getListaDocfaUiu(Date fornitura, String protocollo);
	public List<DocfaDatiCensuari> getListaDatiCensuariDocfa(Date fornitura,String protocollo);
	public List<DocfaDichiaranti> getDichiaranti(Date fornitura, String protocollo);
	public List<DocfaIntestati> getIntestati(Date fornitura, String protocollo);
	public List<DocfaAnnotazioni> getAnnotazioni(Date fornitura, String protocollo);
	public List<DocfaInParteUnoDTO> getListaDocfaInParteUno(Date fornitura, String protocollo);
	public List<BeniNonCensDTO> getListaBeniNonCensibili(Date fornitura, String protocollo);
	public List<OperatoreDTO> getListaOperatori(Date fornitura, String protocollo);
	
	//Metodi Calcolo Classamento
	public Double getDocfaRapporto();
	public List<String> getListaClassiMin(String zona, String foglio, String categoria);
	
	public List<ControlloClassamentoConsistenzaDTO> getListaClassiCompatibili(double valComm, double consistenza, double rapporto, String zona, String categoria, String classe);
	public List<DocfaValori> getDocfaValori(String microzona, String tipolEdilizia, String stato);
	public List<DocfaValori> getDocfaValoriByZonaOmi(String zonaOmi, String tipolEdilizia, String stato);

	public List<FoglioMicrozonaDTO> getFoglioMicrozona(String foglio);
	public List<FoglioMicrozonaDTO> getFoglioMicrozona(String foglio, String zonaCens);
	
	public List<ZonaOmiDTO> getZoneOmiByFP(String foglio, String particella);
	public BigDecimal getAreaIntersectZonaOmiParticella(String zonaOmi, String foglio, String particella);
	
	
	public List<ClassamentoDTO> getClassamenti(String zc, String categoriaEdilizia, Double tariffa);
	public List<ClassamentoDTO> getClassamentiComp(String zc, String categoriaEdilizia, String tipologiaIntervento, List<ClasseMaxCategoriaDTO> clm,boolean flgAscensore, Double consistenza, Double tariffa);
	public List<ClasseMaxCategoriaDTO> getClassiMaxCategoria(String foglio,String categoriaEdilizia,String mappale);
	public List<String> getDocfaFogliMicrozona_ListaFogli();
	
	public Date[] getLastSitPlanimetrie(RicercaOggettoDocfaDTO rod);
	
	//Metodi GITOU WS 4.0
	public List<DocfaDatiCensuari> getDocfaDatiCensuari(RicercaOggettoDocfaDTO ro);


}
