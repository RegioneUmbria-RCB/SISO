package it.webred.ct.data.access.basic.imu;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuBaseDTO;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuConsulenzaDTO;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuElaborazioneDTO;
import it.webred.ct.data.model.imu.SaldoImuDatiElab;
import it.webred.ct.data.model.imu.SaldoImuStorico;

import javax.ejb.Remote;
 
@Remote
public interface SaldoImuService {
	
	public void storicizza(SaldoImuBaseDTO storico);
	
	public void salvaElaborazione(SaldoImuBaseDTO dati);
	
	public String getJsonUltimaElaborazione(SaldoImuBaseDTO search);

	public SaldoImuElaborazioneDTO getJsonDTOUltimaElaborazione(SaldoImuBaseDTO search);
	
	public SaldoImuConsulenzaDTO getConsulenza(SaldoImuBaseDTO search);
	
}
