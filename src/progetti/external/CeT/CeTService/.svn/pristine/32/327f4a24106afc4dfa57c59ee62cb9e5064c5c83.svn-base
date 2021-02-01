package it.webred.ct.aggregator.ejb.imu;

import it.webred.ct.aggregator.ejb.imu.dto.ImuBaseDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuConduzCatastoDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuConduzCatastoIntervalloDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiAnagrafeDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiCatastaliDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiElaborazioneDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiF24DTO;
import it.webred.ct.support.validation.CeTToken;

import javax.ejb.Remote;

@Remote
public interface IMUService {
	
	public ImuConduzCatastoIntervalloDTO[] getConduzioneCatasto(CeTToken token, String tipo, String foglio, String particella, String subalterno);
	
	public ImuDatiF24DTO[] getDatiF24(CeTToken token, String codfisc);
	
	public ImuDatiAnagrafeDTO[] getDatiAnagrafe(CeTToken token, String codfisc, String cognome, String nome, String datanascita);
	
	public ImuDatiCatastaliDTO[] getDatiCatasto(CeTToken token, String codfisc, String cognome, String nome, String datanascita);
	
	public ImuDatiElaborazioneDTO getPrecedentiElaborazioni(CeTToken token, String codfisc);
	
	public ImuBaseDTO salvaElaborazione(CeTToken token, String codfisc, String json);
	
	public ImuBaseDTO storicizza(CeTToken token, String codfisc, String xml);
}
