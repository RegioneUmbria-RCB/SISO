package it.webred.ct.data.access.basic.c336.dao;

import it.webred.ct.data.access.basic.c336.C336PraticaServiceException;
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336StatOperatoreDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.data.model.c336.C336DecodIrreg;
import it.webred.ct.data.model.c336.C336DecodTipDoc;
import it.webred.ct.data.model.c336.C336GesPratica;
import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.data.model.c336.C336SkCarGenFabbricato;
import it.webred.ct.data.model.c336.C336SkCarGenUiu;
import it.webred.ct.data.model.c336.C336TabValIncrClsA4A3;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Collection;
import java.util.List;

public interface C336PraticaDAO {
	
	public C336Pratica getPraticaApertaByOggetto(RicercaOggettoDTO ro) throws C336PraticaServiceException ;
	
	public C336Pratica getPraticaChiusaByOggetto(RicercaOggettoDTO ro) throws C336PraticaServiceException ;
	
	public C336GesPratica getGesAttualePratica(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public String getDesIrregolarita(String codIrregolarita) throws C336PraticaServiceException ;
	
	public List<C336DecodIrreg> getListaIrregolarita() throws C336PraticaServiceException ;
	
	public List<C336DecodTipDoc> getListaTipiDoc() throws C336PraticaServiceException ;
	
	public String getDesTipDoc(String codTipDoc) throws C336PraticaServiceException ;
	
	public List<C336GesPratica> getListaOperatoriPratica(C336PraticaDTO praDto)  throws C336PraticaServiceException ;
	
	public C336GridAttribCatA2 getGridAttribCat2(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public List<C336Allegato> getListaAllegatiPratica(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public C336SkCarGenFabbricato getSkGeneraleFabbricato(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public C336SkCarGenUiu getSkGeneraleUiu(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public C336TabValIncrClsA4A3 getTabValutIncrClasseA4A3(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public C336TabValIncrClsA5A6 getTabValutIncrClasseA5A6(C336PraticaDTO praDto) throws C336PraticaServiceException ;
	
	public C336Allegato getAllegato(C336CommonDTO praDto) throws C336PraticaServiceException ;

	public C336StatOperatoreDTO getStatistiche(String userId);

	public List<String> getOperatori();
}
