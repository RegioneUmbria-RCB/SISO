package it.webred.ct.config.parameters;

import it.webred.ct.config.model.*;
import it.webred.ct.config.parameters.application.ApplicationServiceException;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.AssistedValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class CommonServiceBean{

	@PersistenceContext(unitName = "ConfigDataModel")
	protected EntityManager manager;

	protected static Logger logger = Logger.getLogger("am.log");

	
	public List<AmKeyValueDTO> getListaKeyValue(ParameterSearchCriteria criteria) {

		List<AmKeyValueDTO> resultDTO = new ArrayList<AmKeyValueDTO>();

		try {

			String sql = (new ParameterQueryBuilder(criteria)).createQueryAmKeyValue();
			logger.debug("LISTA KEY VALUE");
			logger.debug("SQL[" + sql + "]");
			Query q = manager.createNativeQuery(sql);

			List<Object[]> result = q.getResultList();
			for (Object[] rs : result) {

				AmKeyValueDTO dto = new AmKeyValueDTO();
				AmKeyValueExt ext = new AmKeyValueExt();

				ext.setKeyConf((String) rs[0]);
				if ("Y".equals((String) rs[1]))
					dto.setMustBeSet(true);
				else
					dto.setMustBeSet(false);
				ext.setValueConf((String) rs[3]);
				ext.setExtType((String) rs[4]);
				ext.setAmComune(new AmComune());
				ext.getAmComune().setBelfiore((String) rs[5]);
				ext.setAmInstance(new AmInstance());
				ext.getAmInstance().setName((String) rs[6]);
				BigDecimal fkFonte = (BigDecimal) rs[7];
				ext.setFkAmFonte(fkFonte != null ? new Integer(fkFonte
						.intValue()) : null);
				BigDecimal id = (BigDecimal) rs[10];
				ext.setId(id != null ? id.intValue() : null);

				ext.setAmSection(getAmSection((String) rs[2], (String) rs[8]));

				dto.setAmKeyValueExt(ext);
				dto.setDescrizione((String) rs[9]);
				dto.setDefaultValue((String) rs[11]);
				if (ext.getValueConf() == null && rs[11] != null) {
					dto.setShowDefault(true);
					ext.setValueConf((String) rs[11]);
				}

				String className = mappaClasseConf((String) rs[0]);

				try {

					Class cls = Class.forName(className);
					dto.setMappedClass(className);
					Method getListaAssistedValue = cls.getMethod(
							"getListaAssistedValue", new Class[] {
									EntityManager.class, String.class });
					Object arglist[] = new Object[2];
					arglist[0] = manager;
					arglist[1] = ext.getValueConf();

					try {
						dto
								.setListaAssistedValue((List<AssistedValueDTO>) getListaAssistedValue
										.invoke(cls.newInstance(), arglist));
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage(),e);
					}

				} catch (ClassNotFoundException t) {
					dto.setMappedClass(null);
				}

				//filtro i parametri fonte/comune in base alla presenza della fonte nella section
				if(criteria.getComune() != null && criteria.getFonte() != null){
					if(ext.getAmSection().getAmFonte() == null || ext.getAmSection().getAmFonte().getId().toString().equals(criteria.getFonte()))
						resultDTO.add(dto);
				}
				//filtro i parametri instance/comune in base alla presenza della application nella section
				else if(criteria.getComune() != null && criteria.getInstance() != null){
					if(ext.getAmSection().getAmApplication() == null)
						resultDTO.add(dto);
					else{
						AmInstance am = (AmInstance) manager.createNamedQuery(
						"AmInstance.getInstanceById").setParameter("id", criteria.getInstance()).getSingleResult();
						if(am.getFkAmApplication().equals(ext.getAmSection().getAmApplication().getName()))
							resultDTO.add(dto);
					}
				}
				else
					resultDTO.add(dto);

			}

		} catch (Throwable t) {
			logger.error("", t);
			throw new ApplicationServiceException(t);
		}

		return resultDTO;

	}

	public List<AmKeyValueExt> getListaKeyValueExt99(Integer idFonte,
			String applicationName, EntityManager manager) {

		List<AmKeyValueExt> lista = new ArrayList<AmKeyValueExt>();

		if (manager == null)
			manager = this.manager;

		List<AmKeyValue> result = manager.createNamedQuery(
				"AmKeyValue.getAmKeyValue99").getResultList();
		for (AmKeyValue kv : result) {

			kv.setAmSection(getAmSection(kv
					.getSectionName(), kv.getFkAmApplication()));
			if (idFonte != null) {
				if (idFonte.equals(kv.getAmSection().getAmFonte().getId())
						&& applicationName == null
						&& kv.getAmSection().getAmApplication() == null) {
					// parametri fonte
					AmKeyValueExt kve = new AmKeyValueExt();
					kve.setKeyConf(kv.getKey());
					kve.setValueConf(kv.getValue());
					kve.setAmSection(kv.getAmSection());
					kve.setFkAmFonte(kv.getAmSection().getAmFonte().getId());
					lista.add(kve);

				} else if (idFonte.equals(kv.getAmSection().getAmFonte()
						.getId())
						&& applicationName != null
						&& kv.getAmSection().getAmApplication() != null) {
					// parametri fonte/applicazione
					AmKeyValueExt kve = new AmKeyValueExt();
					kve.setKeyConf(kv.getKey());
					kve.setValueConf(kv.getValue());
					kve.setAmSection(kv.getAmSection());
					kve.setFkAmFonte(kv.getAmSection().getAmFonte().getId());
					lista.add(kve);
				}
			} else if (applicationName != null
					&& kv.getAmSection().getAmApplication() != null) {
				// parametri applicazione
				AmKeyValueExt kve = new AmKeyValueExt();
				kve.setKeyConf(kv.getKey());
				kve.setValueConf(kv.getValue());
				kve.setAmSection(kv.getAmSection());
				kve.setFkAmFonte(kv.getAmSection().getAmFonte().getId());
				lista.add(kve);
			}
		}

		return lista;

	}

	public List<AmKeyValue> getListaToOverwrite(String application) {

		return manager.createNamedQuery("AmKeyValue.getAmKeyValueToOverwrite")
				.setParameter("application", application).getResultList();

	}

	public List<AmKeyValueDTO> updateSelect(List<AmKeyValueDTO> listaDTO,
			String selected, int index) {

		for (AmKeyValueDTO dto : listaDTO) {
			if (dto.getAmKeyValueExt().getKeyConf().equals(selected)) {

				try {

					Class cls = Class.forName(dto.getMappedClass());
					Method updateSelect = cls.getMethod("updateSelect",
							new Class[] { EntityManager.class,
									AmKeyValueDTO.class, int.class });
					Object arglist[] = new Object[3];
					arglist[0] = manager;
					arglist[1] = dto;
					// index mi fa capire quale selectBox l'utente ha
					// selezionato,
					// in base a questa eseguo un update delle selectBox o no
					arglist[2] = index;

					try {
						updateSelect.invoke(cls.newInstance(), arglist);
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage(),e);
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage(),e);
					}

				} catch (ClassNotFoundException t) {
					dto.setMappedClass(null);
				} catch (Throwable t) {
					logger.error("", t);
					throw new ApplicationServiceException(t);
				}

			}
		}
		return listaDTO;

	}

	public AmKeyValueExt saveParam(AmKeyValueDTO dto) {

		AmKeyValueExt ext = dto.getAmKeyValueExt();
		try {

			if (dto.getMappedClass() != null) {
				Class cls = Class.forName(dto.getMappedClass());
				Method setAssistedValue = cls.getMethod("setAssistedValue",
						new Class[] { List.class });
				Object arglist[] = new Object[1];
				arglist[0] = dto.getListaAssistedValue();

				try {
					ext.setValueConf((String) setAssistedValue.invoke(cls
							.newInstance(), arglist));
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(),e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(),e);
				}
			}

			if (ext.getId() != null) {

				Query q = manager
						.createNamedQuery("AmKeyValueExt.getAmKeyValueExtById");
				q.setParameter("id", ext.getId());
				AmKeyValueExt result = (AmKeyValueExt) q.getSingleResult();
				result.setValueConf(ext.getValueConf());

				if (result.getValueConf() != null
						&& !"".equals(result.getValueConf())) {
					
					if (ext.getAmComune().getBelfiore() != null && result.getAmComune() == null) {
						q = manager
								.createNamedQuery("AmComune.getComuneById");
						q.setParameter("id", ext.getAmComune().getBelfiore());
						result.setAmComune((AmComune) q.getSingleResult());
					}
					if (ext.getAmComune().getBelfiore() == null && result.getAmComune() != null)
						result.setAmComune(null);
			
					
					// recupero AmSection altrimenti è nulla
					result.setAmSection(ext.getAmSection());
					// update
					manager.merge(result);
					ext = result;

				} else {

					// delete
					manager.remove(result);
					ext.setId(null);
					if (ext.getAmInstance() == null)
						ext.setAmInstance(new AmInstance());
					if (ext.getAmComune() == null)
						ext.setAmComune(new AmComune());

				}

			} else if (!"".equals(ext.getValueConf())) {

				// insert
				if (ext.getAmComune().getBelfiore() != null) {
					Query q = manager
							.createNamedQuery("AmComune.getComuneById");
					q.setParameter("id", ext.getAmComune().getBelfiore());
					AmComune result = (AmComune) q.getSingleResult();
					ext.setAmComune(result);
				} else
					ext.setAmComune(null);

				if (ext.getAmInstance().getName() != null) {
					Query q = manager
							.createNamedQuery("AmInstance.getInstanceById");
					q.setParameter("id", ext.getAmInstance().getName());
					AmInstance result = (AmInstance) q.getSingleResult();
					ext.setAmInstance(result);
				} else
					ext.setAmInstance(null);

				if (ext.getAmSection().getAmApplication() != null) {
					Query q = manager
							.createNamedQuery("AmApplication.getApplicationByName");
					q.setParameter("application", ext.getAmSection()
							.getAmApplication().getName());
					AmApplication result = (AmApplication) q.getSingleResult();
					ext.getAmSection().setAmApplication(result);
				} else
					ext.getAmSection().setAmApplication(null);

				ext.setExtType("1");

				manager.persist(ext);
				dto.setAmKeyValueExt(ext);
			}

			return ext;
		} catch (Throwable t) {
			throw new ParameterServiceException(t);
		}
	}

	private String mappaClasseConf(String key) {

		// controllo mappatura classe/conf
		String className = key;
		className = className.replaceAll("\\.", "");
		className = className.replaceFirst(className.substring(0, 1), className
				.substring(0, 1).toUpperCase());

		return "it.webred.ct.config.parameters.configuration." + className;
	}

	public AmFonteTipoinfo getFonteTipoInfo(Integer idFonte, BigDecimal prog) {

		try {
			Query q = manager
					.createNamedQuery("AmFonteTipoinfo.getFonteTipoinfoByFonteProg");
			q.setParameter("fonte", idFonte);
			q.setParameter("prog", prog);
			return (AmFonteTipoinfo) q.getSingleResult();
		} catch (Throwable t) {
			throw new ParameterServiceException(t);
		}

	}
	
	public AmSection getAmSection(String name, String application){
		String sql="";
		try {
			
			sql = new ParameterQueryBuilder().createQueryAmSection(name, application);
			Query q = manager.createQuery(sql);
			return (AmSection) q.getSingleResult();
			
		} catch (Throwable t) {
			throw new ParameterServiceException(sql, t);
		}
		
	}

	public AmKeyValueExt getAmKeyValueExtByKeyFonteComune(String key, String comune,
			String fonte) {

		try {
			Query q = manager
					.createNamedQuery("AmKeyValueExt.getAmKeyValueExtByKeyFonteComune");
			q.setParameter("comune", comune);
			q.setParameter("fonte", new Integer(fonte));
			q.setParameter("key", key);
			List<AmKeyValueExt> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			else return null;
		} catch (Throwable t) {
			throw new ParameterServiceException(t);
		}
		
	}
	
	public List<AmKeyValueExt> getAmKeyValueExtByComune(String comune) {
		List<AmKeyValueExt> result = manager.createNamedQuery(
				"AmKeyValueExt.getAmKeyValueExtByComune").setParameter(
				"comune", comune).getResultList();
		for (AmKeyValueExt kve : result) {
			kve.setAmSection(getAmSection(kve.getSectionName(), kve.getFkAmApplication()));
		}
		List<AmKeyValue> lista = manager.createNamedQuery(
				"AmKeyValue.getAmKeyValue99ByComune").setParameter("comune",
				comune).getResultList();
		for (AmKeyValue kv : lista) {
			AmKeyValueExt kve = new AmKeyValueExt();
			kve.setKeyConf(kv.getKey());
			kve.setValueConf(kv.getValue());
			kve.setAmSection(getAmSection(kv.getSectionName(), kv.getFkAmApplication()));
			kve.setFkAmFonte(kve.getAmSection().getAmFonte().getId());
			result.add(kve);
		}
		return result;
	}
	
	public AmKeyValueExt getAmKeyValueExt(ParameterSearchCriteria criteria) {

		try {
			String sql = new ParameterQueryBuilder(criteria).createQueryAmKeyValueExt();
			Query q = manager.createQuery(sql);
			List<AmKeyValueExt> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			else return null;
		} catch (Throwable t) {
			throw new ParameterServiceException(t);
		}
		
	}
	
	public String getVersioneSmartWelfare(){
		String versione = null;
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("versione.smartwelfare");
		AmKeyValueExt amKey = this.getAmKeyValueExt(criteria);
		versione = amKey.getValueConf()!= null ? amKey.getValueConf() : "";
		return versione;
	}
	
	public String getVersionePiattaforma(){
		String versione = null;
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("versione.piattaforma");
		AmKeyValueExt amKey = this.getAmKeyValueExt(criteria);
		versione = amKey.getValueConf()!= null ? amKey.getValueConf() : "";
		return versione;
	}
	
}
