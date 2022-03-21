package it.webred.ct.data.access.basic.ruolo.tarsu;

import it.webred.ct.data.access.basic.ruolo.RuoloDataIn;
import it.webred.ct.data.access.basic.ruolo.RuoloService;
import it.webred.ct.data.access.basic.ruolo.RuoloServiceBean;
import it.webred.ct.data.access.basic.ruolo.RuoloServiceException;
import it.webred.ct.data.access.basic.ruolo.tarsu.dao.RTarsuDAO;
import it.webred.ct.data.access.basic.ruolo.tarsu.dto.RuoloTarsuDTO;
import it.webred.ct.data.access.basic.versamenti.bp.dto.VersamentoTarBpDTO;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsu;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuRata;
import it.webred.ejb.utility.ClientUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class RTarsuServiceBean  implements RTarsuService {

	@Autowired
	private RTarsuDAO rtarsuDAO;

	@Override
	public List<RuoloTarsuDTO> getListaRuoliByCodFis(RuoloDataIn dataIn) throws RTarsuServiceException {
		List<RuoloTarsuDTO> lstRuoli = new ArrayList<RuoloTarsuDTO>();
		List<SitRuoloTarsu> ruoli = rtarsuDAO.getListaRuoliByCodFis(dataIn.getCf());
		for(SitRuoloTarsu r : ruoli){
			RuoloTarsuDTO ruolo = new RuoloTarsuDTO();
			ruolo.setRuolo(r);
			this.popolaDatiRuolo(dataIn, ruolo);
			lstRuoli.add(ruolo);
		}
		dataIn.setCu(null); //riporto allo stato originario
		
		return lstRuoli;
		
	}
	
	public void popolaDatiRuolo(RuoloDataIn dataIn, RuoloTarsuDTO ruolo){
		SitRuoloTarsu r = ruolo.getRuolo();
		
		String codRuolo = r.getIdExt();
		
		ruolo.setImmobili(rtarsuDAO.getListaImmByCodRuolo(codRuolo));
		ruolo.setListaSt(rtarsuDAO.getListaStByCodRuolo(codRuolo));
		ruolo.setListaStSg(rtarsuDAO.getListaStSgByCodRuolo(codRuolo));
		ruolo.setRate(rtarsuDAO.getListaRateByCodRuolo(codRuolo, dataIn.isRicercaVersamenti()));
		
		dataIn.setCu(r.getCu().toString());
		dataIn.setCf(r.getCodfisc());
		try{
			Context ctx = new InitialContext();
			RuoloService rs = (RuoloService) ClientUtility.getEjbInterface("CT_Service","CT_Service_Data_Access" , "RuoloServiceBean");
			ruolo.setListaNomiPdf(rs.getPercorsiPdfByCfCu(dataIn));
		}catch(Exception rse){
			dataIn.setCu(null);
			throw new RTarsuServiceException(rse.getMessage());
		}
	}
	

	@Override
	public RuoloTarsuDTO getRuoloById(RuoloDataIn dataIn){
		RuoloTarsuDTO ruolo = null;
		SitRuoloTarsu r = rtarsuDAO.getRuoloById(dataIn.getId());
		if(r!=null){
			 ruolo = new RuoloTarsuDTO();
			 ruolo.setRuolo(r);
			 this.popolaDatiRuolo(dataIn, ruolo);
		}
		return ruolo;
	}

}
