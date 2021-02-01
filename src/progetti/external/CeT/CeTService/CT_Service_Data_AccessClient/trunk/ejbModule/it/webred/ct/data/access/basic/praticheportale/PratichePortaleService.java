package it.webred.ct.data.access.basic.praticheportale;

import it.webred.ct.data.access.basic.praticheportale.dto.PraticaPortaleDTO;
import it.webred.ct.data.model.praticheportale.PsAnagraficaView;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface PratichePortaleService{

	public List<PraticaPortaleDTO> getListaPraticheByCodFis(PratichePortaleDataIn dataIn)
			throws PratichePortaleServiceException;

	public PsAnagraficaView getAnagraficaByCodFis(PratichePortaleDataIn dataIn)
			throws PratichePortaleServiceException;
	
}
