package it.webred.ct.data.access.basic.redditianalitici;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.catasto.dao.CatastoDAO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditianalitici.dao.RedditiAnDAO;
import it.webred.ct.data.access.basic.redditianalitici.dto.RigaQuadroRbDTO;
import it.webred.ct.data.model.catasto.Siticomu;
import it.webred.ct.data.model.redditi.analitici.RedAnFabbricati;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class RedditiAnServiceBean extends CTServiceBaseBean implements
		RedditiAnService {
	
	private static final long serialVersionUID = 1L;

	@Autowired 
	private RedditiAnDAO redditiAnDAO;
	
	@Autowired
	private CatastoDAO catastoDAO;

	@Override
	public List<RigaQuadroRbDTO> getQuadroRBFabbricatiByKeyAnno(KeySoggDTO key) {
		List<RedAnFabbricati> lst = redditiAnDAO.getQuadroRBFabbricatiByKeyAnno(key);
		List<RigaQuadroRbDTO> lstOut = new ArrayList<RigaQuadroRbDTO>();
		
		for(RedAnFabbricati fab : lst){
			RigaQuadroRbDTO r = new RigaQuadroRbDTO();
			
			r.setAnnoImposta(fab.getAnnoImposta());
			r.setCanoneLoc(fab.getCanoneLoc());
			r.setCasiPart(fab.getCasiPart());
			r.setCodiceFiscale(fab.getCodiceFiscale());
			r.setComune(fab.getComune());
			r.setContinuazione((fab.getContinuazione()!=null && "1".equals(fab.getContinuazione())) ? true : false);
			r.setGiorni(fab.getGiorni());
			r.setIci(fab.getIci());
			r.setIdeTelematico(fab.getIdeTelematico());
			r.setImponibile(fab.getImponibile());
			r.setNumFabb(fab.getNumFabb());
			r.setPossesso(fab.getPossesso());
			r.setRendita(fab.getRendita());
			r.setTipoModello(fab.getTipoModello());
			r.setUtilizzo(fab.getUtilizzo());
			r.setNumModulo(fab.getModulo());
			r.setCedolareAq(fab.getCedolareAq());
			
			if(r.getComune()!=null){
				RicercaOggettoCatDTO ro = new RicercaOggettoCatDTO();
				ro.setEnteId(key.getEnteId());
				ro.setCodEnte(r.getComune());
				
				Siticomu sc = catastoDAO.getSitiComu(ro);
				r.setDescComune(sc.getNome());
			}
			
			lstOut.add(r);
		}
		
		return lstOut;
	}
	

}
