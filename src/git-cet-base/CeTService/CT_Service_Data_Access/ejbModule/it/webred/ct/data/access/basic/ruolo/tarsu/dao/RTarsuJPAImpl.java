package it.webred.ct.data.access.basic.ruolo.tarsu.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.rette.RetteServiceException;
import it.webred.ct.data.access.basic.ruolo.tares.RTaresServiceException;
import it.webred.ct.data.access.basic.ruolo.tarsu.RTarsuServiceException;
import it.webred.ct.data.access.basic.ruolo.tarsu.dto.RataDTO;
import it.webred.ct.data.access.basic.versamenti.bp.dto.VersamentoTarBpDTO;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsu;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuImm;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuRata;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuSt;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuStSg;
import it.webred.ct.data.model.versamenti.BP.SitTTarBpVers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class RTarsuJPAImpl extends CTServiceBaseDAO implements RTarsuDAO {

	@Override
	public List<SitRuoloTarsu> getListaRuoliByCodFis(String codFiscale)
			throws RetteServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarsu.getListaRuoliByCF");
			q.setParameter("codfisc",codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
	}

	@Override
	public List<SitRuoloTarsuImm> getListaImmByCodRuolo(String idExtRuolo)
			throws RetteServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarsuImm.getListaImmByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
	}

	
	@Override
	public List<RataDTO> getListaRateByCodRuolo(String idExtRuolo, boolean ricercaVersamenti)
			throws RetteServiceException {
		List<RataDTO> liste = new ArrayList<RataDTO>();
		try {
			Query q = manager_diogene.createNamedQuery("SitRuoloTarsuRata.getListaRateByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			List<SitRuoloTarsuRata> res = q.getResultList();
			for(SitRuoloTarsuRata r : res){
				RataDTO dto = new RataDTO(r);
				if(ricercaVersamenti){
					List<VersamentoTarBpDTO> lstVersBp = this.getVersBpByNumBoll(r.getVCampo(),r.getProg());
					dto.setVersamenti(lstVersBp);
				}
				liste.add(dto);
			}
		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
		return liste;
	}

	@Override
	public List<SitRuoloTarsuSt> getListaStByCodRuolo(String idExtRuolo)
			throws RTarsuServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarsuSt.getListaStByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
	}

	@Override
	public List<SitRuoloTarsuStSg> getListaStSgByCodRuolo(String idExtRuolo)
			throws RTarsuServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRuoloTarsuStSg.getListaSgraviByCodRuolo");
			q.setParameter("idExtRuolo",idExtRuolo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
	}
	
	public List<VersamentoTarBpDTO> getVersBpByNumBoll(String vCampo, String numRata) throws RTarsuServiceException {
		List<VersamentoTarBpDTO> lista = new ArrayList<VersamentoTarBpDTO>();
		try {
			Query q = manager_diogene.createNamedQuery(
					"SitTTarBpVers.getVersamentoByNumBollettino");
			q.setParameter("numBollettino",vCampo);
			List<SitTTarBpVers> res = q.getResultList();
			for(SitTTarBpVers v : res){
				VersamentoTarBpDTO dto = new VersamentoTarBpDTO();
				dto.setNumRataRif(numRata);
				dto.setNumBollettino(v.getNumBollettino());
				dto.setTipoDoc(v.getTipoDocumento());
				dto.setTipoAccettazione(v.getTipoAccettazione());
				dto.setCcBeneficiario(v.getCcBeneficiario());
				dto.setDtAccettazione(v.getDtAccettazione());
				dto.setDtAccredito(v.getDtAccredito());
				
				String pus = v.getUfficioSportello();
				if(pus!=null && pus.length()==8 && !"00000000".equals(pus)){
					dto.setProv(pus.substring(0,3));
					dto.setUfficio(pus.substring(3,6));
					dto.setSportello(pus.substring(6));
				}
				
				String sost = v.getFlagSostitutivo();
				dto.setSostitutivo("S".equals(sost) ? true : false);
				
				String importo = StringUtils.cleanLeftPad(v.getImporto(),'0');
				if(!importo.contains(",") && !importo.contains(".")){
					int index = importo.length();
					importo = importo.substring(0,index-2)+","+importo.substring(index-2);
				}
				dto.setImporto(importo);
				
				lista.add(dto);
			}
			
		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
		
		return lista;
	}

	@Override
	public SitRuoloTarsu getRuoloById(String id) throws RTarsuServiceException {
		try{
			return manager_diogene.find(SitRuoloTarsu.class, id);
		} catch (Throwable t) {
			throw new RTarsuServiceException(t);
		}
	}
	
}
