package it.webred.ct.data.access.basic.praticheportale.dao;

import it.webred.ct.data.access.basic.praticheportale.PratichePortaleServiceException;
import it.webred.ct.data.model.praticheportale.PsAnagraficaView;
import it.webred.ct.data.model.praticheportale.PsPratica;

import java.math.BigDecimal;
import java.util.List;

public interface PratichePortaleDAO {

	public List<PsPratica> getListaPraticheByCodFis(String codFiscale);

	public PsAnagraficaView getAnagraficaByCodFis(String codFiscale)
			throws PratichePortaleServiceException;

	public PsAnagraficaView getAnagraficaByID(BigDecimal id)
			throws PratichePortaleServiceException;

}
