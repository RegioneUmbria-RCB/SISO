package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;

import javax.inject.Named;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsPaiMastSogg;

@Named
public class PaiDAO extends CarSocialeBaseDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CsPaiMastSogg salvaSoggettoPai(CsPaiMastSogg soggetto) {
		try{
			em.persist(soggetto);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return soggetto;
	}
}
