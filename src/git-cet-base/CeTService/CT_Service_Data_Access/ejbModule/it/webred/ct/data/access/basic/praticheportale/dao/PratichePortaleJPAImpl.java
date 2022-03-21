package it.webred.ct.data.access.basic.praticheportale.dao;

import it.webred.ct.data.access.basic.praticheportale.PratichePortaleBaseService;
import it.webred.ct.data.access.basic.praticheportale.PratichePortaleServiceException;
import it.webred.ct.data.model.praticheportale.PsAnagraficaView;
import it.webred.ct.data.model.praticheportale.PsPratica;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

public class PratichePortaleJPAImpl extends PratichePortaleBaseService implements PratichePortaleDAO {

	@Override
	public List<PsPratica> getListaPraticheByCodFis(String codFiscale)
			throws PratichePortaleServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"PsPratica.getPraticheByCF").setParameter(
					"codFisc", codFiscale);
			return q.getResultList();

		} catch (Throwable t) {
			throw new PratichePortaleServiceException(t);
		}
	}
	
	@Override
	public PsAnagraficaView getAnagraficaByCodFis(String codFiscale)
			throws PratichePortaleServiceException {
		try {

			Query q = manager_diogene.createNamedQuery(
					"PsAnagraficaView.getAnagraficaByCF").setParameter(
					"codFisc", codFiscale);
			List<PsAnagraficaView> lista = q.getResultList();
			if(lista.size()>0){
				return lista.get(0);
			}
			
			return new PsAnagraficaView();

		} catch (Throwable t) {
			throw new PratichePortaleServiceException(t);
		}
	}
	
	@Override
	public PsAnagraficaView getAnagraficaByID(BigDecimal id)
			throws PratichePortaleServiceException {
		try {
			if (id==null) {
				return new PsAnagraficaView();
			}

			Query q = manager_diogene.createNamedQuery(
					"PsAnagraficaView.getAnagraficaByID").setParameter(
					"id", id.longValue());
			List<PsAnagraficaView> lista = q.getResultList();
			if(lista.size()>0){
				return lista.get(0);
			}
			
			return new PsAnagraficaView();

		} catch (Throwable t) {
			throw new PratichePortaleServiceException(t);
		}
	}
}
