package eu.smartpeg.rilevazionepresenze.pai.ejb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiInfoSinteticheDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDocumentoDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPtiConsuntiDTO;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiInfoSintetiche;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPTI;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPtiConsunti;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPtiDocumento;
import eu.smartpeg.rilevazionepresenze.ejb.dao.RilevazionePresenzeBaseDao;

@Named
public class ArCsPaiPTIDAO extends RilevazionePresenzeBaseDao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public List<ArCsPaiPTIDTO> findMinoriByIdStruttura(Long idStruttura) {
		Query query = em.createQuery("SELECT d FROM ArCsPaiPTI d WHERE d.struttura.id = :idStruttura");
		query.setParameter("idStruttura",idStruttura);
		List<ArCsPaiPTI> result = query.getResultList();
		ArCsPaiPTIDTO tmpDTO;
		List<ArCsPaiPTIDTO> resDTO = new ArrayList<ArCsPaiPTIDTO>();
		for(ArCsPaiPTI minoreEntity: result) {
			tmpDTO = new ArCsPaiPTIDTO();
			BeanUtils.copyProperties(minoreEntity, tmpDTO);
			resDTO.add(tmpDTO);
		}
		return resDTO;
	}
	
	public ArCsPaiPTI saveMinore(ArCsPaiPTIDTO minoreDto) {
		try {
			ArCsPaiPTI toReturn = new ArCsPaiPTI();
			BeanUtils.copyProperties(minoreDto, toReturn);
			toReturn = em.merge(toReturn);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveMinore " + e.getMessage(), e);
		}
		return null;
	}

	public List<ArCsPaiPTIDocumentoDTO> findDocumentiRichiestaSelezionata(String codRouting, Long idPaiPTI){
		List<ArCsPaiPTIDocumentoDTO> resDTO = new ArrayList<ArCsPaiPTIDocumentoDTO>();
		Query query = em.createQuery("SELECT d FROM ArCsPaiPtiDocumento d where d.idPaiPTI = :idPaiPTI and d.codRouting = :codRouting order by d.validoDa desc ");
		query.setParameter("idPaiPTI", idPaiPTI);
		query.setParameter("codRouting",  codRouting);
		ArCsPaiPTIDocumentoDTO tmpDTO;
		ArCsPaiInfoSinteticheDTO tmpInfoDTO;
		List<ArCsPaiPtiDocumento> resultList = query.getResultList();
		for(ArCsPaiPtiDocumento docEntity: resultList) {
			tmpDTO = new ArCsPaiPTIDocumentoDTO();
			String[] ignore = { "arCsPaiInfoSintetiche"};
			BeanUtils.copyProperties(docEntity, tmpDTO);
			
			if (docEntity.getArCsPaiInfoSintetiche() != null ) {
				//QUI DEVO TROVARE SOURCE E tARGET
				tmpInfoDTO = new ArCsPaiInfoSinteticheDTO();
				BeanUtils.copyProperties(docEntity.getArCsPaiInfoSintetiche(), tmpInfoDTO);
				tmpDTO.setArCsPaiInfoSinteticheDTO(tmpInfoDTO);
			}
			
			resDTO.add(tmpDTO);
		}
		
		return resDTO;
	}
	
	public boolean hasPaiDoc(String codRouting, Long idPaiPTI){
		Query query = em.createQuery("SELECT d FROM ArCsPaiPtiDocumento d where d.idPaiPTI = :idPaiPTI and d.codRouting = :codRouting and d.validoA is null ");
		query.setParameter("idPaiPTI", idPaiPTI);
		query.setParameter("codRouting",  codRouting);
		return query.getResultList() != null ? !query.getResultList().isEmpty() : false ;
	}
	
	
	public ArCsPaiPtiDocumento salvaDocumento(ArCsPaiPTIDocumentoDTO documento) {
		try {
			ArCsPaiPtiDocumento toReturn = new ArCsPaiPtiDocumento();	
			ArCsPaiInfoSintetiche toReturnInfo = new ArCsPaiInfoSintetiche();
			
			BeanUtils.copyProperties(documento, toReturn);
			
			if (documento.getArCsPaiInfoSinteticheDTO()!= null) {
				String [] ignoreInfo = {"arCsPaiPtiDocumento"};
				BeanUtils.copyProperties(documento.getArCsPaiInfoSinteticheDTO(), toReturnInfo,ignoreInfo);
				toReturn.setArCsPaiInfoSintetiche(toReturnInfo);
			}
			
			toReturn = em.merge(toReturn);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore salvaDocumento " + e.getMessage(), e);
		}
		return null;
	}
	
	
	public ArCsPaiInfoSintetiche salvaInfoSintetichePai (ArCsPaiInfoSinteticheDTO infoSintetichePAI) {
		try {
			ArCsPaiInfoSintetiche toReturn = new ArCsPaiInfoSintetiche();
			BeanUtils.copyProperties(infoSintetichePAI, toReturn);
			toReturn = em.merge(toReturn);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore salvaInfoSintetichePai " + e.getMessage(), e);
		}
		return null;
	}
	
	public List<ArCsPaiPtiConsuntiDTO> findListaConsuntivazione(String codRouting, Long idPaiPTI){
		List<ArCsPaiPtiConsuntiDTO> resDTO = new ArrayList<ArCsPaiPtiConsuntiDTO>();
		Query query = em.createQuery("SELECT d FROM ArCsPaiPtiConsunti d where d.idPaiPTI = :idPaiPTI and d.codRouting = :codRouting ");
		query.setParameter("idPaiPTI", idPaiPTI);
		query.setParameter("codRouting",  codRouting);
		ArCsPaiPtiConsuntiDTO tmpDTO;
		List<ArCsPaiPtiConsunti> resultList = query.getResultList();
		for(ArCsPaiPtiConsunti consuEntity: resultList) {
			tmpDTO = new ArCsPaiPtiConsuntiDTO();
		
			BeanUtils.copyProperties(consuEntity, tmpDTO);
			
			resDTO.add(tmpDTO);
		}
		
		return resDTO;
	}
}
