package it.webred.ct.data.access.basic.ruolo.tares;

import it.webred.ct.data.access.basic.ruolo.RuoloDataIn;
import it.webred.ct.data.access.basic.ruolo.RuoloService;
import it.webred.ct.data.access.basic.ruolo.tares.dao.RTaresDAO;
import it.webred.ct.data.access.basic.ruolo.tares.dto.RuoloTaresDTO;
import it.webred.ct.data.access.basic.ruolo.tarsu.RTarsuServiceException;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTares;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class RTaresServiceBean implements RTaresService {

	@Autowired
	private RTaresDAO rtaresDAO;

	@Override
	public List<RuoloTaresDTO> getListaRuoliByCodFis(RuoloDataIn dataIn)
			throws RTaresServiceException {
		List<RuoloTaresDTO> lstRuoli = new ArrayList<RuoloTaresDTO>();
		List<SitRuoloTares> ruoli = rtaresDAO.getListaRuoliByCodFis(dataIn.getCf());
		for(SitRuoloTares r : ruoli){
			RuoloTaresDTO ruolo = new RuoloTaresDTO();
			ruolo.setRuolo(r);
			this.popolaDatiRuolo(dataIn, ruolo);
			lstRuoli.add(ruolo);
		}
		dataIn.setCu(null); //riporto allo stato originario
		return lstRuoli;
		
	}


	
	public void popolaDatiRuolo(RuoloDataIn dataIn, RuoloTaresDTO ruolo){
		SitRuoloTares r = ruolo.getRuolo();
		
		String codRuolo = r.getIdExt();
		
		ruolo.setImmobili(rtaresDAO.getListaImmByCodRuolo(codRuolo));
		ruolo.setListaSt(rtaresDAO.getListaStByCodRuolo(codRuolo));
		ruolo.setListaStSg(rtaresDAO.getListaStSgByCodRuolo(codRuolo));
		ruolo.setRate(rtaresDAO.getListaRateByCodRuolo(codRuolo));
		
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
	public RuoloTaresDTO getRuoloById(RuoloDataIn dataIn){
		RuoloTaresDTO ruolo = null;
		SitRuoloTares r = rtaresDAO.getRuoloById(dataIn.getId());
		if(r!=null){
			 ruolo = new RuoloTaresDTO();
			 ruolo.setRuolo(r);
			 this.popolaDatiRuolo(dataIn, ruolo);
		}
		return ruolo;
	}

	


}
