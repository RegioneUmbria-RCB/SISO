package it.webred.ct.config.parameters.application;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.parameters.CommonServiceBean;
import it.webred.ct.config.parameters.dto.IstanzaApplicazioneDTO;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless(mappedName = "applicationServiceBean")
public class ApplicationServiceBean extends CommonServiceBean implements ApplicationService {

	public List<KeyValueDTO> getListaApplication() {

		List<KeyValueDTO> listSelect = new ArrayList<KeyValueDTO>();

		try {
			logger.debug("LISTA APPLICATION");
			Query q = manager
					.createNativeQuery(new ApplicationQueryBuilder().createQueryListaApplication());
			List<Object[]> result = q.getResultList();
			for (Object[] rs : result) {
				
				/*MAPPATURA CAMPI
				* 0 value
				* 1 label
				* 2 è stato trovato un parametro non valorizzato*/
				String label = rs[2].equals('S')? (String) rs[1] + "*" : (String) rs[1];
				KeyValueDTO item = new KeyValueDTO((String) rs[0], label);
				listSelect.add(item);
				
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
		return listSelect;
		
	}
	
	
	public List<KeyValueDTO> getListaInstanceByApplicationUsername(String application, String username) {
		
		List<KeyValueDTO> listSelect = new ArrayList<KeyValueDTO>();

		try {
			logger.debug("LISTA INSTANCE BY APPLICATION["+application+"] USERNAME["+username+"]");
			Query q = manager
					.createNativeQuery(new ApplicationQueryBuilder().createQueryListaInstanceByAppUser());
			q.setParameter("application", application);
			q.setParameter("username", username);
			List<Object[]> result = q.getResultList();
			for (Object[] rs : result) {
				
				String label = rs[2].equals('S')? (String) rs[1] + "*" : (String) rs[1];
				KeyValueDTO item = new KeyValueDTO((String) rs[0], label);
				listSelect.add(item);
				
			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
		return listSelect;
	}
	
	
	public List<AmFonte> getListaFonte(String application) {

		try {
			logger.debug("LISTA FONTE ["+application+"]");
			Query q = manager
					.createNamedQuery("AmFonte.getFonteByApplication");
			q.setParameter("application", application);
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
	}
	
	public AmInstance getInstanceByApplicationComune(String application, String comune) {

		try {
			logger.debug("INSTANCE BY APPLICATION["+application+"] E COMUNE["+comune+"]");
			Query q = manager.createNamedQuery("AmInstance.getInstanceByApplicationComune");
			q.setParameter("application", application);
			q.setParameter("comune", comune);
			List<AmInstance> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			
			return null;

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
	}

	public AmInstance getInstanceByName(String name) {

		try {
			logger.debug("INSTANCE BY NAME["+name+"]");
			Query q = manager
					.createNamedQuery("AmInstance.getInstanceById");
			q.setParameter("id", name);
			List lista = q.getResultList();
			if(lista.size() > 0)
				return (AmInstance) lista.get(0);
			return new AmInstance();

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
	}
	
	public void saveInstance(AmInstance instance) {
		
		try {
			
			logger.debug("SAVE INSTANCE");
			manager.persist(instance);

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
	}
	
	public void updateInstance(AmInstance instance) {
		
		try {
			
			logger.debug("UPDATE INSTANCE");
			manager.merge(instance);

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
	}

	public String getUrlApplication(String username, String ente, String appName){
		String url = null;
		
		try {
			logger.debug("URL APPLICAZIONE UTENTE");
			String sql = new ApplicationQueryBuilder().getSQL_LISTA_APPLICAZIONI_UTENTE();
			sql = "SELECT url FROM ("+sql+") WHERE FK_AM_COMUNE= :ente AND NAME= :appName ";
			Query q = manager.createNativeQuery(sql);
			q.setParameter("username", username);
			q.setParameter("ente", ente);
			q.setParameter("appName", appName);
			
			List<String> result = q.getResultList();
			if(result!=null && result.size()>0)
				url = result.get(0);
		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
		return url;
	}
	
	@Override
	public List<IstanzaApplicazioneDTO> getListaIstanzeMenu(String username) {
		List<IstanzaApplicazioneDTO> listaIstanze = new ArrayList<IstanzaApplicazioneDTO>();
		try {

			String sql = new ApplicationQueryBuilder().createQueryIstanzeApplicazioneMenu();
			Query q = manager.createNativeQuery(sql);
			q.setParameter("username", username);
			
			List<Object[]> result = (List<Object[]>) q.getResultList();
			
			for(Object[] res : result){
				IstanzaApplicazioneDTO istanza = new IstanzaApplicazioneDTO();
				int i = 0;
				String appName = (String)res[i++];
				String appTipo = (String)res[i++];
				String appCategory = (String)res[i++];
				String dataRouter = (String)res[i++];
				String enteDesc = (String)res[i++];
				String enteCod = (String)res[i++];
				String url = (String)res[i++];
				String uri = (String)res[i++];
				String portRewrite = (String)res[i++];
				BigDecimal concat = (BigDecimal)res[i++];
				Boolean concatUrl = concat!=null && concat.equals(BigDecimal.ZERO) ? Boolean.FALSE : Boolean.TRUE;
				
				istanza.setAppName(appName);
				istanza.setAppTipo(appTipo);
				istanza.setAppCategory(appCategory); 
				istanza.setEnteDesc(enteDesc);
				istanza.setEnteCod(enteCod);
				istanza.setUrl(url);
				istanza.setUri(uri);
				istanza.setPortRewrite(portRewrite);
				istanza.setConcatUrl(concatUrl);
				
				listaIstanze.add(istanza);
			}

		} catch (Throwable t) {
			throw new ApplicationServiceException(t);
		}

		return listaIstanze;
	}


	@Override
	public List<String> getListaTipoAppMenu() {
		Query q = manager.createNamedQuery("AmApplication.getApplicationType");
		q.setParameter("escludi", "AmProfiler");
		return q.getResultList();
	}
	
}
