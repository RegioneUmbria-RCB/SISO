package it.webred.rulengine;

import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.ContextException;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;
import it.webred.rulengine.type.def.DeclarativeType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interfaccia del Contesto dell'applicazione Rule Engine
 * 
 * @author sisani
 * @version $Revision: 1.3 $ $Date: 2007/09/26 12:41:14 $
 */
public interface Context extends Map
{
	
	
	// copia alcuni attributi ca ctx al context corrente
	public void copiaAttributi(Context ctx) throws ContextException;

	public void addEnteSorgente(BeanEnteSorgente bes);
	
	public BeanEnteSorgente getEnteSorgenteById(Integer id);
	
	public HashMap<Integer,BeanEnteSorgente> getListaEnteSrgente();
	
	public void setListaEnteSorgente(HashMap<Integer,BeanEnteSorgente> lbes);
	
	/**
	 * Metodo che ritorna il Process Id
	 * 
	 * @return
	 */
	public String getProcessID();

	/**
	 * Metodo per settare il Process Id
	 * 
	 * @param processID
	 */
	public void setProcessID(String processID);
	
	public String getBelfiore();
	
	public void setBelfiore(String belfiore);
	
	public List getParametriConfigurazioneEnte();
	
	public void setParametriConfigurazioneEnte(List parametriConfigurazioneEnte);
	
	public List getFonteDatiEnte();

	public void setFonteDatiEnte(List fonteDatiEnte);
	
	public Long getIdFonte();
	
	public void setIdFonte(Long idFonte);
	
	public HashMap<String, DeclarativeType> getDeclarativeType();
	
	public void addDeclarativeType(String name , DeclarativeType dt);

	public DeclarativeType getDeclarativeType(String name);
	
	public Connection getConnection(String connectionName);
	
	public HashMap<String, Connection> getConnections();

	public void addConnectionNoTransactionIsolation(String name, Connection conn) throws SQLException;
	
	public Long getIdSched();
	
	public void setIdSched(Long idSched);

	public Map getJellyParams();
	
	public void setJellyParams(Map jellyParams);
	
	public Map getReverseObjects();
	
	public void addReverseObjects(Map reverseObjects);
	
	public void riempiReverseObjects(Map reverseObjects);
}
