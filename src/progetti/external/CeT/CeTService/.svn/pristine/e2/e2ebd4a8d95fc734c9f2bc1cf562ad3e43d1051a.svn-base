package it.webred.ct.config.parameters.application;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.parameters.CommonServiceBean;
import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;

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
			logger.debug("LISTA INSTANCE");
			Query q = manager
					.createNativeQuery(new ApplicationQueryBuilder().createQueryListaInstanceByAppUser(application, username));
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
			logger.debug("LISTA FONTE");
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
			logger.debug("INSTANCE BY APPLICATION E COMUNE");
			Query q = manager
					.createNamedQuery("AmInstance.getInstanceByApplicationComune");
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
			logger.debug("INSTANCE BY NAME");
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
			sql = "SELECT url FROM ("+sql+") WHERE FK_AM_COMUNE= ? AND NAME= ? ";
			sql=sql.replaceAll("@user", username);
			Query q = manager.createNativeQuery(sql);
			q.setParameter(1, ente);
			q.setParameter(2, appName);
			
			List<String> result = q.getResultList();
			if(result!=null && result.size()>0)
				url = result.get(0);
		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}
		
		return url;
	}
	
}
