package it.webred.ct.data.access.basic.indice.civico;

import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.data.access.basic.cnc.flusso750.dao.Flusso750DAO;
import it.webred.ct.data.access.basic.indice.IndiceBaseService;
import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.IndiceServiceException;
import it.webred.ct.data.access.basic.indice.civico.dao.CivicoDAO;
import it.webred.ct.data.access.basic.indice.civico.dao.CivicoQueryBuilder;
import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.access.basic.indice.civico.dto.SitCivicoDTO;
import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.via.dao.ViaQueryBuilder;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeDebitore;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitEnteSorgente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
 
@Stateless
public class CivicoServiceBean extends IndiceBaseService implements CivicoService{

	
	@Autowired
	private CivicoDAO civicoDAO;
	
	@PostConstruct
	private void init() {
		super.setIndiceBaseDAO((IndiceBaseDAO) civicoDAO);
	}
	
	
	@Override
	//public List<SitCivicoDTO> getListaCiviciByVia(int start, int rowNumber, String id){
	public List<SitCivicoDTO> getListaCiviciByVia(IndiceDataIn indDataIn){
		
		List<SitCivicoDTO> lista = new ArrayList<SitCivicoDTO>(); 
						
		int start = (int) indDataIn.getListaCiviciByVia().getStart();
		int rowNumber = (int) indDataIn.getListaCiviciByVia().getRowNumber();
		String id = (String) indDataIn.getListaCiviciByVia().getId();
		
		try{
			
			List<Object[]> result = civicoDAO.getListaCiviciByVia(id, start, rowNumber);
			
			/*String sql = (new CivicoQueryBuilder()).createQueryCiviciByViaUnico(id, false);
			
			Query q = manager.createQuery(sql);
			if(start != 0 || rowNumber != 0){
				q.setFirstResult(start);
				q.setMaxResults(rowNumber);
			}
			
			List<Object[]> result = q.getResultList();*/
			
			for (Object[] rs : result) {
				
				SitCivicoDTO civico = new SitCivicoDTO();
				civico.setSitEnteSorgente((SitEnteSorgente)rs[0]);
				civico.setHash((String)rs[1]);
				String c = ((String)rs[2]);
				c = c.replaceAll("^0+(?!$)", "");
				civico.setCivico(c);
				civico.setProgressivo(String.valueOf((Long)rs[3]));
				AmFonteTipoinfo fonteTipoinfo = getFonteTipoinfo(new Integer((int) civico.getSitEnteSorgente().getId()), new BigDecimal((Long)rs[3]));
				civico.setInformazione(fonteTipoinfo.getInformazione());
				lista.add(civico);
				
			}
			
		}catch(Throwable t) {
			throw new IndiceServiceException(t);
		}
		
		return lista;
	}

	@Override
	//public Long getListaCiviciByViaRecordCount(String id) {
	public Long getListaCiviciByViaRecordCount(IndiceDataIn indDataIn) {

		String id = (String)indDataIn.getObj();
		
		Long ol = 0L;
		
		
		try {
			
			ol = civicoDAO.getListaCiviciByViaRecordCount(id);
			
			/*String sql = (new CivicoQueryBuilder()).createQueryCiviciByViaUnico(id, true);
			
			logger.debug("COUNT LISTA CIVICI - SQL["+sql+"]");
			
			if (sql != null) {
				
				Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();
				ol = (Long) o;
				
				logger.debug("COUNT LISTA CIVICI ["+ol+"]");
				
			}
				
		} catch (NoResultException nre) {
			
			logger.warn("Result size [0] " + nre.getMessage());
		*/
		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}

		return ol;
		
	}
	
	public List<String> getListaIdCiviciByViaUnicoDesc(RicercaCivicoIndiceDTO rci){
		List<String> ids = new ArrayList<String>();
		try{
			List<SitCivicoTotale> res = civicoDAO.getListaCivTotaleByViaUnicoDesc(rci);
			for(SitCivicoTotale c: res)
				ids.add(c.getId().getIdDwh());
			
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}
		return ids;
	}


	@Override
	public List<SitCivicoTotale> getListaCivTotaleByCivicoFonte(RicercaCivicoIndiceDTO rci) {
		try {
			return civicoDAO.getListaCivTotaleByCivicoFonte(rci);
		}catch (Throwable t) {
			logger.error("", t);
			throw new IndiceServiceException(t);
		}
		
	}
	
	
}
