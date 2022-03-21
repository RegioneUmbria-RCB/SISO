package it.webred.ct.data.access.basic.indice.ricerca.dao;

import it.webred.ct.data.access.basic.indice.IndiceServiceException;
import it.webred.ct.data.access.basic.indice.oggetto.dto.SitOggettoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.KeyFabbricatoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.KeyUIDTO;
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.concedilizie.SitCConcIndirizzi;
import it.webred.ct.data.model.concedilizie.SitCConcessioniCatasto;
import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;

import java.math.BigDecimal;
import java.util.List;

public interface IndiceCorrelazioneIndirettaDAO {
	//public List<BigDecimal> getCiviciFromFabbricato(String keyFabbr) throws IndiceServiceException;
	//public List<BigDecimal> getCiviciFromUI(String keyUI) throws IndiceServiceException ;
	
	public List<BigDecimal> getCiviciFromFabbricato(KeyFabbricatoDTO keyFabbr) throws IndiceServiceException;
	public List<BigDecimal> getCiviciFromUI(KeyUIDTO keyUI) throws IndiceServiceException ;
	public List<String> getOggettiIciFromCivicoIci(VTIciCiviciAll civIci) throws IndiceServiceException ;
	public List<String> getOggettiTarsuFromCivicoTarsu(VTTarCiviciAll civIci )	throws IndiceServiceException ;
	public List<String> getConcessioniFromIndirizzoConcessioni(SitCConcIndirizzi indConc )	throws IndiceServiceException ;
	public List<String> getConcessioniFromDatiCatastoConcessioni(SitCConcessioniCatasto conCat )	throws IndiceServiceException ;
}
