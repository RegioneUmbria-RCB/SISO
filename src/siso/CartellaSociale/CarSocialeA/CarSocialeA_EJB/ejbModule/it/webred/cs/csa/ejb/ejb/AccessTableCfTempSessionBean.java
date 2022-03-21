package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCfTempSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CfTempDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCfTemp;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.utils.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableCfTempSessionBean extends CarSocialeBaseSessionBean implements AccessTableCfTempSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	public static final String CF_ANONIMO = "ANONIMO";
	
	@Autowired
	private CfTempDAO cfTempDao;

	
	@Override
	public CsCfTemp findCfTempById(BaseDTO dto) throws Exception {
		String id = (String) dto.getObj();
		if(StringUtils.hasText(id))
		{
			return cfTempDao.findById(Long.parseLong(id));
		}
		return null;
	}


	@Override
	public List<CsCfTemp> findCfTempByCf(BaseDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CsCfTemp> findCfTempByCfTemp(BaseDTO dto) throws Exception 
	{
		String cfTemp = (String) dto.getObj();
		if(StringUtils.hasText(cfTemp))
		{
			List<CsCfTemp> cfTempList = cfTempDao.findByCfTemp(cfTemp);
			return cfTempList;			
		}	
		return null;
	}

	@Override
	public List<CsCfTemp> findCfTempByCfTempNotReplaced(BaseDTO dto) throws Exception {
		String cfTemp = (String) dto.getObj();
		String cf = (String) dto.getObj2();
		if(StringUtils.hasText(cfTemp))
		{
			List<CsCfTemp> cfTempList = cfTempDao.findByCfTempAndCf(cfTemp, cf);
			return cfTempList;			
		}	
		return null;
	}


	@Override
	public CsCfTemp generateNewCfTemp(BaseDTO dto) throws Exception {
		HashMap<String,Object> attribs = (HashMap<String, Object>) dto.getObj();
		if(attribs!=null)
		{
			String cfTemp = generateCfTempStringByAttribs(attribs);
			/*List<CsCfTemp> cfTempList = cfTempDao.findByCfTempAndCf(cfTemp,null);
			if(cfTempList!=null && cfTempList.size()>0)
			{
				return cfTempList;
			}
			else
			{*/
				String attributiString = attributiToString(attribs);
				
				CsCfTemp cfTempObject = new CsCfTemp();
				cfTempObject.setCfTemp(cfTemp);
				cfTempObject.setAttributi(attributiString);
				//cfTempObject = cfTempDao.saveCfTemp(cfTempObject);
			/*	
				cfTempList = new ArrayList<CsCfTemp>();
				cfTempList.add(cfTempObject);				
				return cfTempList;
			}
			*/
				return cfTempObject;
		}

		return null;
	}

	

	private String attributiToString(HashMap<String, Object> attribs) {
		
		String nome = (String) attribs.get("nome");
		String cognome =(String) attribs.get("cognome");
		Date dataNascita =(Date) attribs.get("dataNascita");		
		String cittadinanza =(String) attribs.get("cittadinanza");
		String comuneNascita =(String) attribs.get("comuneNascita");			
		
		final String sep="; ";
		
		String ret="";
		if(StringUtils.hasText(nome)) ret+=(nome+sep);
		if(StringUtils.hasText(cognome)) ret+=(cognome+sep);
		if(StringUtils.hasText(cittadinanza)) ret+=(cittadinanza+sep);
		if(dataNascita!=null) 
		{
			String dataNascitaStr = DateFormat.dateToString(dataNascita, "dd/MM/yyyy");
			ret+=(dataNascitaStr+sep);
		}
		if(StringUtils.hasText(comuneNascita)) ret+=(comuneNascita);
		
		return (ret).toUpperCase();
	}


	//ALGORITMO DI GNERAZIONE DEL CODICE
	public String generateCfTempStringByAttribs(HashMap<String, Object> attribs) 
	{
		String digest = DigestUtils.md5Hex(
				attributiToString(attribs) );
		
		return ("["+digest+"]").toUpperCase();
	}
	
//	@Override	
	public String generateCfTempStringByAttribs(BaseDTO dto) 
	{
		HashMap<String, Object> attribs  = (HashMap<String, Object>) dto.getObj();
		return generateCfTempStringByAttribs(attribs);
	}


	@Override
	public CsCfTemp saveCfTemp(BaseDTO dto) throws Exception {
		CsCfTemp cfTempObject = (CsCfTemp) dto.getObj();
		if(cfTempObject!=null)
		{
			if(cfTempObject.getId()==null)
			{
				//new
				cfTempObject = cfTempDao.saveCfTemp(cfTempObject);
			}
			else
			{
				//update
				cfTempObject = cfTempDao.updateCfTemp(cfTempObject);
				String cfNew = cfTempObject.getCf();
				String cfTempOld = cfTempObject.getCfTemp();
				if(StringUtils.hasText(cfTempOld) && StringUtils.hasText(cfNew) && 
						!cfNew.equals(cfTempOld))
				{
					if(!cfTempOld.equals(CF_ANONIMO) && !cfNew.equals(CF_ANONIMO))
					{
						try
						{
							cfTempDao.replaceAllCfInSS(cfTempOld, cfNew);
							cfTempDao.replaceAllCfInCS(cfTempOld, cfNew);
						}
						catch(Throwable t)
						{
							logger.error("errore durante l'aggiornamento del codice fiscale temporaneo nelle altre schede di 'Cartella Sociale' e 'Segretariato Sociale'.\nCodice fiscale temporaneo: "+cfTempOld+". Codice fiscale nuovo: "+cfNew, t);
						}
					}
				}
				
			}
		}
		return cfTempObject;
	}


	@Override
	public List<CsCfTemp> findCfTempByAttribs(BaseDTO dto) 
	{
		String attribsString = (String) dto.getObj();		
		List<CsCfTemp> cfTempList = cfTempDao.findByAttribs(attribsString);
		if(cfTempList!=null && cfTempList.size()>0)
		{
			return cfTempList;
		}
		return null;
	}
}
