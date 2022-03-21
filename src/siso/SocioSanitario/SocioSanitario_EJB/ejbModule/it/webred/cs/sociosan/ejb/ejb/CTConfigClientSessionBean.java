package it.webred.cs.sociosan.ejb.ejb;


import it.webred.cs.csa.ejb.client.domini.AccessTableDominiAmKeySessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ejb.utility.ClientUtility;
import it.webred.mailing.MailUtils.MailParamBean;

import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

@Stateless
public class CTConfigClientSessionBean extends BaseSessionBean implements CTConfigClientSessionBeanRemote {

	protected ParameterService parameterService;
	protected static AccessTableDominiAmKeySessionBeanRemote amKeyDomini;
	
	public CTConfigClientSessionBean() throws NamingException {
		super();
		parameterService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		amKeyDomini = (AccessTableDominiAmKeySessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDominiAmKeySessionBean");

	}
	
	public MailParamBean getSISOMailParametres() throws SocioSanitarioException {
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setComune(null);
		criteria.setSection(null);
		criteria.setKey("siso.mail.server");
		AmKeyValueExt extServer = parameterService.getAmKeyValueExt(criteria);
		criteria.setKey("siso.mail.server.port");
		AmKeyValueExt extPort = parameterService.getAmKeyValueExt(criteria);
		criteria.setKey("siso.mail.server.username");
		AmKeyValueExt extMailUserName = parameterService.getAmKeyValueExt(criteria);
		criteria.setKey("siso.mail.server.password");
		AmKeyValueExt extMailPassword = parameterService.getAmKeyValueExt(criteria);
		criteria.setKey("siso.mail.address");
		AmKeyValueExt extMailSISO = parameterService.getAmKeyValueExt(criteria);
		criteria.setKey("siso.mail.server.param");
		AmKeyValueExt extMailServerParams = parameterService.getAmKeyValueExt(criteria);

		if (extServer==null || extPort==null || extMailSISO==null )
			throw new SocioSanitarioException("Configurazione per invio mail non completa");
		

		
		MailParamBean mailParams = new MailParamBean(extServer.getValueConf(), extPort.getValueConf() , 
				extMailUserName==null ? null: extMailUserName.getValueConf(), 
				extMailPassword==null?null: extMailPassword.getValueConf(), 
				extMailSISO.getValueConf(), 
				extMailServerParams==null?null:extMailServerParams.getValueConf(), 
				extMailUserName==null?false:true );
		
		return mailParams;
	}
	
	@Override
	public String getGlobalParameter(String paramName) {
		String valore = amKeyDomini.findByKey(paramName);
		if(!StringUtils.isBlank(valore)) return valore;
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(paramName);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = parameterService.getAmKeyValueExt(criteria);
		if (amKey != null && amKey.getValueConf() != null && !amKey.getValueConf().trim().isEmpty()) {
			return amKey.getValueConf();
		} else
			logger.warn("Parametro '" + paramName + "' non definito");

		return null;
	}

	
}
