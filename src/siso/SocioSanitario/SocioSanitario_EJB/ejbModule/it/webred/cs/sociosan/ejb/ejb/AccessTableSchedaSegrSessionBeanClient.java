package it.webred.cs.sociosan.ejb.ejb;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SegnalazioneDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsSchedeAltraProvenienza;
import it.webred.cs.sociosan.ejb.client.AccessTableSchedaSegrSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.client.ProfilerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dto.SegnalazioneSerenaDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ejb.utility.ClientUtility;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;


@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableSchedaSegrSessionBeanClient extends BaseSessionBean implements AccessTableSchedaSegrSessionBeanClientRemote {

	protected AccessTableSchedaSegrSessionBeanRemote sb;
	
	@EJB
	protected ProfilerSessionBeanRemote profiler;
	
	
	public AccessTableSchedaSegrSessionBeanClient() throws NamingException {
		super();
		sb = (AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	}
	
	
	private void fillData(CeTToken tok, CeTBaseObject dto){
		dto.setEnteId(tok.getEnte());
		dto.setSessionId(tok.getSessionId());
		String username = profiler.getUsernameUtente(dto);
		dto.setUserId(username);
	}
	
	@Override
	public long saveSegnalazioneSerena(CeTToken tok, SegnalazioneDTO dto) {
		fillData(tok,dto);
		
		CsSchedeAltraProvenienza saveSegnalazioneSerena = sb.saveSegnalazioneSerena(dto);
		
		// restituisco solo l'ID della tabella CS_SCHEDE_ALTRA_PROVENIENZA, perché al chiamante è sufficiente
		return saveSegnalazioneSerena.getIdSchedaSegr();
	}
	
	@Override
	public SegnalazioneSerenaDTO getStatusSegnalazioneSerena(CeTToken tok, Long idSchedaSegr) {
		BaseDTO dto = new BaseDTO();
		fillData(tok,dto);
		
		dto.setObj(idSchedaSegr);
		dto.setObj2(DataModelCostanti.SchedaSegr.PROVENIENZA_SERENA);
		
		CsSsSchedaSegr scheda = sb.findSchedaSegrBySchedaIdProvenienza(dto);
		
		SegnalazioneSerenaDTO s = new SegnalazioneSerenaDTO();
		fillData(tok, s);
		
		s.setId(scheda.getId());
		s.setProvenienza(scheda.getProvenienza());
		s.setSchedaId(scheda.getSchedaId());
		
		s.setCodEnte(scheda.getCodEnte());
		
		s.setFlgStato(scheda.getFlgStato());
		s.setStato(scheda.getStato());
		s.setNotaStato(scheda.getNotaStato());
		
		s.setFlgEsistente(scheda.getFlgEsistente());
		s.setSoggettoId(scheda.getSoggettoId());
		
		s.setUserId(scheda.getUserIns());
		s.setDtIns(scheda.getDtIns());
		s.setUsrMod(scheda.getUsrMod());
		s.setDtMod(scheda.getDtMod());
		
		return s;
	}
}
