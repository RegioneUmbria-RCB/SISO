package it.webred.ct.data.access.basic.c336;
import it.webred.ct.data.access.basic.c336.dto.C336AllegatoDTO;
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336GridAttribCatA2DTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336SkGenFabbricatoDTO;
import it.webred.ct.data.access.basic.c336.dto.C336SkUiuDTO;
import it.webred.ct.data.access.basic.c336.dto.C336TabValIncrClsA4A3DTO;
import it.webred.ct.data.access.basic.c336.dto.C336TabValIncrClsA5A6DTO;
import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.data.model.c336.C336SkCarGenFabbricato;
import it.webred.ct.data.model.c336.C336SkCarGenUiu;
import it.webred.ct.data.model.c336.C336TabValIncrClsA4A3;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;

import javax.ejb.Remote;

@Remote
public interface C336GesPraticaService {
	public C336Pratica nuovaPratica(C336PraticaDTO praticaDto) throws C336CommonServiceException;
	public C336Pratica modificaPratica(C336PraticaDTO pratica) throws C336CommonServiceException;
	public C336PraticaDTO chiudiIstrPratica(C336PraticaDTO praticaDto) throws C336CommonServiceException;
	public void cambiaGestionePratica(C336PraticaDTO praticaDto) throws C336CommonServiceException;
	public void eliminaAllegato(C336CommonDTO obj) throws C336CommonServiceException;
	public C336Allegato inserisciAllegato(C336AllegatoDTO allegatoDto) throws C336CommonServiceException;
	public void nuovaSkFabbricato(C336SkGenFabbricatoDTO skFabbrDto) throws C336CommonServiceException;
	public C336SkCarGenFabbricato modificaSkFabbricato(C336SkGenFabbricatoDTO skFabbrDto) throws C336CommonServiceException;
	public void nuovaSkUiu(C336SkUiuDTO skUiuDto) throws C336CommonServiceException;
	public C336SkCarGenUiu  modificaSkUiu(C336SkUiuDTO skUiuDto) throws C336CommonServiceException;
	public void nuovaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3DTO dto);
	public C336TabValIncrClsA4A3 modificaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3DTO dto);
	public void nuovaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6DTO dto);
	public C336TabValIncrClsA5A6 modificaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6DTO dto);
	public void nuovaGridAttribCat2(C336GridAttribCatA2DTO dto);
	public C336GridAttribCatA2 modificaGridAttribCat2(C336GridAttribCatA2DTO dto);

}
