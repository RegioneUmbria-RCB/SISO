package it.webred.ct.data.access.basic.c336;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.c336.dao.C336PraticaDAO;
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336StatOperatoreDTO;
import it.webred.ct.data.access.basic.c336.dto.RicercaStatisticheDTO;
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
/**
 * Session Bean implementation class C336PraticaServiceBean
 */  
@Stateless 
public class C336PraticaServiceBean extends CTServiceBaseBean implements
		C336PraticaService {
	@Autowired 
	private C336PraticaDAO c336PraticaDAO; 
	@Override
	public List<C336GesPratica> getListaOperatoriPratica(C336PraticaDTO praDto) {
		return c336PraticaDAO.getListaOperatoriPratica(praDto);
	}
	@Override
	public C336GridAttribCatA2 getGridAttribCat2(C336PraticaDTO praDto) {
		return c336PraticaDAO.getGridAttribCat2(praDto);
	}
	@Override
	public List<C336Allegato> getListaAllegatiPratica(C336PraticaDTO praDto) {
		return c336PraticaDAO.getListaAllegatiPratica(praDto);
	}
	@Override
	public C336SkCarGenFabbricato getSkGeneraleFabbricato(C336PraticaDTO praDto) {
		return c336PraticaDAO.getSkGeneraleFabbricato(praDto);
	}
	@Override
	public C336SkCarGenUiu getSkGeneraleUiu(C336PraticaDTO praDto) {
		return c336PraticaDAO.getSkGeneraleUiu(praDto);
	}
	@Override
	public C336TabValIncrClsA4A3 getTabValutIncrClasseA4A3(C336PraticaDTO praDto) {
		return c336PraticaDAO.getTabValutIncrClasseA4A3(praDto);
	}
	@Override
	public C336TabValIncrClsA5A6 getTabValutIncrClasseA5A6(C336PraticaDTO praDto) {
		return c336PraticaDAO.getTabValutIncrClasseA5A6(praDto);
	}
	@Override
	public C336Pratica getPraticaApertaByOggetto(RicercaOggettoDTO ro) {
		return c336PraticaDAO.getPraticaApertaByOggetto(ro);
	}
	@Override
	public String getDesIrregolarita(C336CommonDTO obj) {
		return c336PraticaDAO.getDesIrregolarita(obj.getCodIrreg());
	}
	@Override
	public C336GesPratica getGesAttualePratica(C336PraticaDTO praDto) {
		return c336PraticaDAO.getGesAttualePratica(praDto);
	}
	@Override
	public List<C336DecodIrreg> getListaIrregolarita(CeTBaseObject obj) {
		return c336PraticaDAO.getListaIrregolarita();
	}
	@Override
	public C336Pratica getPraticaChiusaByOggetto(RicercaOggettoDTO ro) {
		return c336PraticaDAO.getPraticaChiusaByOggetto(ro);
	}
	@Override
	public List<C336DecodTipDoc> getListaTipiDoc(CeTBaseObject obj) {
		return c336PraticaDAO.getListaTipiDoc();
	}
	@Override
	public String getDesTipDoc(C336CommonDTO obj) {
	 	return c336PraticaDAO.getDesTipDoc(obj.getCodTipDoc());	
	 }
	@Override
	public C336Allegato getAllegato(C336CommonDTO praDto) {
		return c336PraticaDAO.getAllegato(praDto);
	}
	
	@Override
	public List<C336StatOperatoreDTO> getStatisticheOperatore(RicercaStatisticheDTO rs){
		List<C336StatOperatoreDTO> statistiche = new ArrayList<C336StatOperatoreDTO>();
		
		List<String> operatori = new ArrayList<String>();
		
		if(!rs.getSupervisore())
			operatori.add(rs.getUserId());
		else
			operatori.addAll(c336PraticaDAO.getOperatori());
		
		for(String oper : operatori) 
			statistiche.add(c336PraticaDAO.getStatistiche(oper));
		
		return statistiche;
		
	}
	

}
