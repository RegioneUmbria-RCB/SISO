package it.webred.ct.data.access.basic.cosap;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.cosap.dao.CosapDAO;
import it.webred.ct.data.access.basic.cosap.dto.DatiCosapDTO;
import it.webred.ct.data.access.basic.cosap.dto.RicercaOggettoCosapDTO;
import it.webred.ct.data.access.basic.cosap.dto.RicercaSoggettoCosapDTO;
import it.webred.ct.data.model.cosap.SitTCosapContrib;
import it.webred.ct.data.model.cosap.SitTCosapTassa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless
public class CosapServiceBean extends CTServiceBaseBean implements CosapService {
	@Autowired 
	private CosapDAO cosapDAO;
	
	@Override
	public List<SitTCosapContrib> searchSoggetto(RicercaSoggettoCosapDTO rs) throws CosapServiceException {
		return cosapDAO.searchSoggetto(rs);
	}

	@Override
	public List<SitTCosapTassa> getDatiOggettiBySogg(RicercaSoggettoCosapDTO rs) {
		if (rs.getIdSoggCosap()!=null && !rs.getIdSoggCosap().equalsIgnoreCase(""))
			return cosapDAO.getDatiOggettiByIdSogg(rs) ;
		else
			return null;
	}

	@Override
	public SitTCosapContrib getDatiSoggettoById(RicercaSoggettoCosapDTO rs) {
		if (rs.getIdSoggCosap()!=null && !rs.getIdSoggCosap().equalsIgnoreCase(""))
			return cosapDAO.getDatiSoggettoById(rs);
		else
			return null;
	}

	@Override
	public SitTCosapTassa getDatiOggettoById(RicercaOggettoCosapDTO rs) {
		return cosapDAO.getDatiOggettoById(rs);
	}

	@Override
	public List<SitTCosapTassa> getDatiSintesiOggettiByListaID(RicercaOggettoCosapDTO rs) {
		if (rs.getDataRif() == null)
			return cosapDAO.getDatiSintesiOggettiByListaID(rs);
		else {
			List<SitTCosapTassa> lista = new ArrayList<SitTCosapTassa>();
			List<SitTCosapTassa> listaTemp = cosapDAO.getDatiSintesiOggettiByListaID(rs);
			if (listaTemp!=null && listaTemp.size() > 0) {
				for (SitTCosapTassa ogg:listaTemp) {
					if (ogg.getDtIniValidita() != null && !ogg.getDtIniValidita().after(rs.getDataRif()) ) {
						lista.add(ogg);
					}
				}
			}
			return lista;
		}
	}
	
	public List<DatiCosapDTO> getDettaglioCosap(RicercaOggettoCosapDTO ro) throws CosapServiceException {
		List<DatiCosapDTO> listaOggettiCosap= new ArrayList<DatiCosapDTO>();
		
		List<SitTCosapTassa> listaOgg= cosapDAO.getDettaglioCosap(ro);
		
		if (listaOgg!= null){
			for (SitTCosapTassa ogg:listaOgg){
				DatiCosapDTO dati= new DatiCosapDTO();
				dati.setOggetto(ogg);
				RicercaSoggettoCosapDTO rs = new RicercaSoggettoCosapDTO();
				rs.setEnteId(ro.getEnteId());
				rs.setUserId(ro.getUserId());
				if (ogg.getIdExtContrib() != null)  {
					rs.setIdExtSoggCosap(ogg.getIdExtContrib());
					List<SitTCosapContrib> listaSogg = cosapDAO.getListaSoggettiByIdExt(rs) ;	
					if (listaSogg != null && listaSogg.size() > 0)
						dati.setSoggetto(listaSogg.get(0));
				}
				listaOggettiCosap.add(dati);
			}
				
				
		}
		
		
		return listaOggettiCosap;
		
	}

	

	

}
