package it.webred.cs.sociosan.ejb.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;

import it.webred.cs.csa.ejb.client.AccessTableInterventoErogazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneResponseDTO;
import it.webred.cs.data.model.view.VMobiCasi;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.client.ProfilerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ejb.utility.ClientUtility;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableInterventoErogazioneSessionBeanClient extends BaseSessionBean implements AccessTableInterventoErogazioneSessionBeanClientRemote {

	protected AccessTableInterventoErogazioneSessionBeanRemote sb;
	
	@EJB
	protected ProfilerSessionBeanRemote profiler;
	
	public AccessTableInterventoErogazioneSessionBeanClient() throws NamingException {
		super();
		sb = (AccessTableInterventoErogazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoErogazioneSessionBean");

	}
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findInterventoErogazioneByIdSettoreEroganteData(CeTToken tok, FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO input) throws SocioSanitarioException {
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		dto.setObj(input);
		logger.debug("AccessTableInterventoErogazioneSessionBeanClient: Sto chiamando il client AccessTableInterventoErogazioneSessionBeanRemote - CarSocialeA_EJB"  );
		FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO out = sb.findErogazioniOrganizzazione(dto);
		return out;
	}

	public FindCasiByUsernameOperatoreResponseDTO findCasiByUsernameOperatore (CeTToken tok, FindCasiByUsernameOperatoreRequestDTO input) throws SocioSanitarioException {
		FindCasiByUsernameOperatoreResponseDTO casiDTO = new FindCasiByUsernameOperatoreResponseDTO();
    	
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		dto.setObj(input);
		
		List<VMobiCasi> lst = sb.findCasiByUsernameOperatore(dto);
		casiDTO.setvMobiCasi (lst);
		return casiDTO;
	}
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findCasiByOperatoreBySoggetto (CeTToken tok, FindCasiByOperatoreBySoggettoRequestDTO input) throws SocioSanitarioException {
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		dto.setObj(input);
		logger.debug("AccessTableInterventoErogazioneSessionBeanClient: Sto chiamando il client AccessTableInterventoErogazioneSessionBeanRemote - CarSocialeA_EJB"  );
		FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO out = sb.findCasiRicercaSoggetto(dto);
		return out;
			}
	
	
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO findInterventoErogazioneByIdSettoreEroganteDataCasoId (CeTToken tok, FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO input) throws SocioSanitarioException {
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		dto.setObj(input);
		dto.setObj2(input.getCasoId());
		logger.debug("AccessTableInterventoErogazioneSessionBeanClient: Sto chiamando il client AccessTableInterventoErogazioneSessionBeanRemote - CarSocialeA_EJB"  );
		FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO out = sb.findErogazioniSoggettoSelezionato(dto);
		return out;
			}
	
	
	
	private void fillData(CeTToken tok, CeTBaseObject dto){
		dto.setEnteId(tok.getEnte());
		dto.setSessionId(tok.getSessionId());
		String username = profiler.getUsernameUtente(dto);
		dto.setUserId(username);
	}

	@Override
	public UploadInterventiErogazioneResponseDTO uploadDatiMobile(CeTToken tok, String json) {
		UploadInterventiErogazioneResponseDTO out = new UploadInterventiErogazioneResponseDTO();
		logger.debug("Sto stampando il json nella tabella "+json);
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		try{
			dto.setObj(json);
			sb.uploadDatiMobile(dto);
			out.setEsito(true);
		}catch(Exception e ){
			out.setEsito(false);
			out.setMessaggio(e.getMessage());
		}
	
		return out;
	}

	@Override
	public void elaboraUploadDatiMobile(CeTToken tok) {
		logger.debug("Sto elaborando i json inseriti nella tabella");
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		try{
			dto.setObj(null);
			sb.verificaLoadingMobileStaging(dto);
		}catch(Exception e ){
			logger.error("Errore nella elaborazione dei dati contenuti nella tabella di staging  mobile, verranno effettuati nuovi tentativi da parte del timer task");
		}
		
	}

	
}
