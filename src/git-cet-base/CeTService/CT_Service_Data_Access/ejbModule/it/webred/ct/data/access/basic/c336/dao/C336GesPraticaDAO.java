package it.webred.ct.data.access.basic.c336.dao;

import it.webred.ct.data.access.basic.c336.C336PraticaServiceException;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.data.model.c336.C336GesPratica;
import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.data.model.c336.C336SkCarGenFabbricato;
import it.webred.ct.data.model.c336.C336SkCarGenUiu;
import it.webred.ct.data.model.c336.C336TabValIncrClsA4A3;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;

public interface C336GesPraticaDAO {
	public C336Pratica nuovaPratica(C336PraticaDTO pratica) throws C336PraticaServiceException;
	public C336Pratica modificaPratica(C336PraticaDTO pratica) throws C336PraticaServiceException;
	public C336Pratica chiudiPratica(C336Pratica pratica) throws C336PraticaServiceException;
	public C336Pratica getPraticaByPK(C336PraticaDTO pratica) throws C336PraticaServiceException;
	public void fineGesPratica(C336GesPratica gesPra) throws C336PraticaServiceException;
	public void iniziaGesPratica(C336GesPratica pratica) throws C336PraticaServiceException;
	public C336GesPratica getGesAttualePratica(C336Pratica pratica) throws C336PraticaServiceException;
	public C336Allegato nuovoAllegato(C336Allegato allegato) throws C336PraticaServiceException;
	public void eliminaAllegato(Long idAllegato) throws C336PraticaServiceException;
	public void nuovaSkFabbricato(C336SkCarGenFabbricato skFabbr) throws C336PraticaServiceException;
	public C336SkCarGenFabbricato modificaSkFabbricato(C336SkCarGenFabbricato skFabbr) throws C336PraticaServiceException;
	public void nuovaSkUiu(C336SkCarGenUiu skUiu)throws C336PraticaServiceException;
	public C336SkCarGenUiu modificaSkUiu(C336SkCarGenUiu skUiu) throws C336PraticaServiceException;
	
	public C336TabValIncrClsA4A3 modificaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3 c336TabValIncrClsA4A3) throws C336PraticaServiceException;
	public C336TabValIncrClsA5A6 modificaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6 c336TabValIncrClsA5A6) throws C336PraticaServiceException;
	
	public void nuovaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3 c336TabValIncrClsA4A3) throws C336PraticaServiceException;
	public void nuovaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6 c336TabValIncrClsA5A6) throws C336PraticaServiceException;
	
	public C336GridAttribCatA2 modificaGridAttribCat2(C336GridAttribCatA2 griglia)throws C336PraticaServiceException;
	public void nuovaGridAttribCat2(C336GridAttribCatA2 griglia)throws C336PraticaServiceException;
	

}
