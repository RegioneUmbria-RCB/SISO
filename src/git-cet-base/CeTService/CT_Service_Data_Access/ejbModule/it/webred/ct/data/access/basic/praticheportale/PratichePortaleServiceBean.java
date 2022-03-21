package it.webred.ct.data.access.basic.praticheportale;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.acqua.AcquaDataIn;
import it.webred.ct.data.access.basic.acqua.AcquaServiceException;
import it.webred.ct.data.access.basic.acqua.dao.AcquaDAO;
import it.webred.ct.data.access.basic.acqua.dto.AcquaUtenzeDTO;
import it.webred.ct.data.access.basic.praticheportale.dao.PratichePortaleDAO;
import it.webred.ct.data.access.basic.praticheportale.dto.PraticaPortaleDTO;
import it.webred.ct.data.model.acqua.SitAcquaUtente;
import it.webred.ct.data.model.acqua.SitAcquaUtenze;
import it.webred.ct.data.model.praticheportale.PsAnagraficaView;
import it.webred.ct.data.model.praticheportale.PsPratica;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class PratichePortaleServiceBean extends CTServiceBaseBean implements PratichePortaleService {

	@Autowired
	private PratichePortaleDAO pratichePortaleDAO;

	@Override
	public List<PraticaPortaleDTO> getListaPraticheByCodFis(PratichePortaleDataIn dataIn)
			throws PratichePortaleServiceException {
		
		List<PraticaPortaleDTO> listaDTO = new ArrayList<PraticaPortaleDTO>();
		List<PsPratica> lista = pratichePortaleDAO.getListaPraticheByCodFis(dataIn.getCodFiscale());
		for(PsPratica p: lista){
			PraticaPortaleDTO dto = new PraticaPortaleDTO();
			dto.setPratica(p);
			dto.setRichiedente(pratichePortaleDAO.getAnagraficaByID(p.getAnagraficaRichiedenteId()));
			if (p.getAnagraficaFruitoreId()!=null)
				dto.setFruitore(pratichePortaleDAO.getAnagraficaByID(p.getAnagraficaFruitoreId()));
			listaDTO.add(dto);
		}
		
		return listaDTO;
	}
	
	@Override
	public PsAnagraficaView getAnagraficaByCodFis(PratichePortaleDataIn dataIn)
			throws PratichePortaleServiceException {
		
		return pratichePortaleDAO.getAnagraficaByCodFis(dataIn.getCodFiscale());
	}
	
}
