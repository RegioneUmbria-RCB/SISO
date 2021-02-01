package it.webred.ct.data.access.basic.c336;
import java.util.List;

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
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336StatOperatoreDTO;
import it.webred.ct.data.access.basic.c336.dto.RicercaStatisticheDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface C336PraticaService {
	public C336Pratica getPraticaApertaByOggetto(RicercaOggettoDTO ro);
	public C336Pratica getPraticaChiusaByOggetto(RicercaOggettoDTO ro);
	public C336GesPratica getGesAttualePratica(C336PraticaDTO praDto) ;
	public String getDesIrregolarita(C336CommonDTO praDto);
	public List<C336DecodIrreg> getListaIrregolarita(CeTBaseObject obj);
	public List<C336DecodTipDoc> getListaTipiDoc(CeTBaseObject obj);
	public String getDesTipDoc(C336CommonDTO obj);
	public List<C336GesPratica> getListaOperatoriPratica(C336PraticaDTO praDto);
	public List<C336Allegato> getListaAllegatiPratica(C336PraticaDTO praDto);
	public C336Allegato getAllegato(C336CommonDTO praDto);
	public C336SkCarGenFabbricato getSkGeneraleFabbricato(C336PraticaDTO praDto);
	public C336SkCarGenUiu getSkGeneraleUiu(C336PraticaDTO praDto);
	public C336TabValIncrClsA4A3 getTabValutIncrClasseA4A3(C336PraticaDTO praDto);
	public C336TabValIncrClsA5A6 getTabValutIncrClasseA5A6(C336PraticaDTO praDto);
	public C336GridAttribCatA2 getGridAttribCat2(C336PraticaDTO praDto);
	public List<C336StatOperatoreDTO> getStatisticheOperatore(RicercaStatisticheDTO rs);
	
}
