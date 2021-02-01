package it.webred.ct.data.access.basic.imu.dao;

import java.util.List;

import it.webred.ct.data.access.basic.imu.dto.SaldoImuBaseDTO;
import it.webred.ct.data.model.imu.SaldoImuDatiElab;
import it.webred.ct.data.model.imu.SaldoImuStorico;

public interface SaldoImuDAO {

	public void salvaStorico(SaldoImuStorico storico);

	public void salvaElaborazione(SaldoImuDatiElab dati);

	/*Restituisce la lista dei dati elaborazione, associati al codice fiscale ordinati per progressivo decrescente*/
	public List<SaldoImuDatiElab> getDatiElaborazione(String codfisc);
	
	/*Restituisce lo storico delle elaborazioni, associati al codice fiscale ordinati per progressivo decrescente*/
	public List<SaldoImuStorico> getStorico(String codfisc);

}
