package it.webred.ct.data.access.basic.concedilizie;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.ConcessioneDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.IndirizzoConcessioneDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.SoggettoConcessioneDTO;
import it.webred.ct.data.model.concedilizie.SitCConcessioni;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ConcessioniEdilizieService {
	public List<SitCConcessioni> getConcessioniSuCiviciCatasto(RicercaCivicoDTO rc);
	public List<SitCConcessioni> getConcessioniByUiu(RicercaConcEdilizieDTO rce);
	public List<SitCConcessioni> getConcessioniByFabbricato(RicercaOggettoCatDTO ro) ;
	public SitCConcessioni getConcessioneById(RicercaConcEdilizieDTO ro) ; 
	public List<SoggettoConcessioneDTO> getSoggettiByConcessione(RicercaConcEdilizieDTO ro) ;
	public String getStringaImmobiliByConcessione(RicercaConcEdilizieDTO ro) ;
	public List<ConcessioneDTO> getDatiConcessioniByFabbricato(RicercaOggettoCatDTO ro) ;
	public List<ConcessioneDTO> getDatiConcessioniByImmobile(RicercaConcEdilizieDTO ro) ;
	public List<SitCConcessioni> getConcessioniByIndirizziByFoglioParticella(RicercaOggettoCatDTO dto);
	public List<IndirizzoConcessioneDTO> getIndirizziByConcessione(RicercaConcEdilizieDTO ro);
	
	
	//Suggerimento per i filtri di ricerca
	public List<String> getListaProgressivoAnno(RicercaConcEdilizieDTO ro);
	public List<String> getListaProtocolloAnno(RicercaConcEdilizieDTO ro);
	public List<String> getListaTitoliSoggetto(RicercaConcEdilizieDTO ro);
	public List<String> getSuggestionVie(RicercaCivicoDTO rc);
	public List<String> getSuggestionCiviciByVia(RicercaCivicoDTO rc);
	public List<String> getSuggestionSoggetti(RicercaSoggettoDTO rs);
	
	public List<ConcessioneDTO> getDatiConcCiviciDelFabbricato(RicercaOggettoCatDTO ro);
	
	//Arichivio Storico Visure (MILANO)
	public ConcVisuraDTO getVisuraById(RicercaConcEdilizieDTO rc);
	public List<ConcVisuraDTO> getVisureCiviciDelFabbricato(RicercaOggettoCatDTO ro);
	public List<String> getVisureTipiAtto(RicercaConcEdilizieDTO rc);
	

}
