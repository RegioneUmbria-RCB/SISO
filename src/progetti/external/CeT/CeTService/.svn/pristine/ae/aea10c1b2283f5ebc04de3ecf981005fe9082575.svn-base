package it.webred.ct.data.access.basic.concedilizie.dao;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.concedilizie.ConcessioniEdilizieException;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.SoggettoConcessioneDTO;
import it.webred.ct.data.model.concedilizie.ConcEdilizieVisure;
import it.webred.ct.data.model.concedilizie.ConcEdilizieVisureDoc;
import it.webred.ct.data.model.concedilizie.SitCConcIndirizzi;
import it.webred.ct.data.model.concedilizie.SitCConcessioni;
import it.webred.ct.data.model.concedilizie.SitCConcessioniCatasto;

import java.math.BigDecimal;
import java.util.List;

public interface ConcessioniEdilizieDAO {
	public List<SitCConcessioni> getConcessioniByUiu(RicercaConcEdilizieDTO rce) throws ConcessioniEdilizieException;
	public List<SitCConcessioni> getConcessioniByFabbricato(RicercaOggettoCatDTO ro) throws ConcessioniEdilizieException; 
	public SitCConcessioni getConcessioneById(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException; 
	public List<SoggettoConcessioneDTO> getSoggettiByConcessione(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException;
	public String getStringaImmobiliByConcessione(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException;
	public List<SitCConcessioni> getConcessioniSuCiviciCatasto(RicercaCivicoDTO rc) throws ConcessioniEdilizieException;
	public List<SitCConcessioni> getConcessioniByIndirizziByFoglioParticella(String foglio, String particella);
	public List<SitCConcIndirizzi> getIndirizziByConcessione(RicercaConcEdilizieDTO ro);
	public List<SitCConcessioniCatasto> getListaImmobiliByConcessione(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException;
	
	//Suggerimenti Filtro di Ricerca
	public List<String> getListaProgressivoAnno();
	public List<String> getListaProtocolloAnno();
	public List<String> getListaTitoliSoggetto();
	public List<String> getSuggestionVie(RicercaCivicoDTO rc);
	public List<String> getSuggestionCiviciByVia(RicercaCivicoDTO rc);
	public List<String> getSuggestionSoggetti(String denominazione);
	
	
	//Archivio Storico Visure (MILANO)
	public List<ConcEdilizieVisure> getVisureByListaId(RicercaCivicoDTO rc);
	
	public ConcEdilizieVisure getVisuraById(String id);
	public ConcEdilizieVisureDoc getDocVisuraById(BigDecimal id);
	public List<String> getVisureTipiAtto();
	public SitCConcessioni getConcessioneByIdExt(RicercaConcEdilizieDTO ro) throws ConcessioniEdilizieException;
	
	
	
	
	
}
