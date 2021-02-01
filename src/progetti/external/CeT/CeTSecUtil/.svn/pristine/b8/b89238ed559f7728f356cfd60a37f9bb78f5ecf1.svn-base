package it.webred.cet.permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * TODO Classe per che si occupa di controllare i permessi di un utente.
 * @author Petracca Marco
 * @version $Revision: 1.4 $ $Date: 2010/07/07 08:48:37 $
 */
public final class GestionePermessi
{
	public static final String PLACEHOLDER_READ_ENTITY_RIGHT="Lettura {0} ({1})";
	public static final String PLACEHOLDER_WRITE_ENTITY_RIGHT="Scrittura {0} ({1})";
	public static final String PLACEHOLDER_DELETE_ENTITY_RIGHT="Eliminazione {0} ({1})";
	public static final String PLACEHOLDER_ENTITY_NAME="{0} ({1})";
	public static final String PLACEHOLDER_APPLICATION_NAME="Diritti sui Dati ({1})";
	public static final String PLACEHOLDER_APPLICATION_TYPE="Diritti sui Dati";
	public static final String PLACEHOLDER_ENTE_NAME="{1}";
	public static final String PLACEHOLDER_USER_NAME="{2}";
	public static final String READ_ENTITY="READ_ENTITY";
	public static final String WRITE_ENTITY="WRITE_ENTITY";
	public static final String DELETE_ENTITY="DELETE_ENTITY";
	public  static final long CATEGORIA_AMAPPLICATION_INDEFINITA = 99;

	private static final String ENTE_PREDEFINITO = "";
	
	/**
	 * Mappa che contiene tutti i permessi degli utenti<br>
	 * La chiave è il nome utente<br>
	 * Il valore è il bean PermessirBean  
	 */
	private static Map<String,PermessirBean>	mappa	= Collections.synchronizedMap(new HashMap<String,PermessirBean>());
	//private static SSOUser ssoUser;
	private GestionePermessi()
	{
	}


	/**
	 * @param ssoUser
	 * @return
	 */
	private synchronized static PermessirBean riempiMappa(CeTUser user)
	{
		PermessirBean perm = new PermessirBean();
		String userName = user.getName();
		perm.setUser(userName);
		HashMap ListaPermessi = new HashMap();
		//perm.setListaProperties(user.getPermList()); ------
		
			// Formato del permesso
			// appl. item permission
			// permission@-@rulengine@-@Motore Regole@-@Assegna Permessi

			//String p = ssoUser.getProperties()[i].getName();
			Set<String> keySet = user.getPermList().keySet();
			
			Iterator<String> keys = keySet.iterator();
			while (keys.hasNext()) {
				
			String p = keys.next();
			
			if(p.indexOf("permission@-@")==0)
			{
				String[] tok = p.replace("permission@-@", "").split("@-@");
				String application = tok[0];
				String item = tok[1];
				String permesso = tok[2];
				perm.setPermissions(application, item, permesso);
				ListaPermessi.put(permesso, p);
			}

		}
		
		mappa.put(userName, perm);

		return perm;
	}

	/**
	 * Questo metodo si occupa di controllare se un utente è autorizzato <br>
	 * ad eseguire una determinata operazione sulla entità passata.<br>
	 * Per prima cosa controlla se sono già stati caricati i permessi per l'utente;<br>
	 * in caso affermativo controlla se è autorizzato, altrimenti prima richiama il metodo riempiMappa(ssoUser)<br>
	 * che si occupa del recupero dei permessi per poi memorizzarli. 
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param nome Il nome dell'entità della quale sto controllando i diritti
	 * @param application Il nome dell'applicazione 
	 * @param permesso Il permesso che descrive l'operazione che si vuole compiere(READ,WRITE,DELETE)
	 * @return true se è autorizzato, altrimenti false
	 */
	/*
	public synchronized static boolean operazioneSuEntita(Object user, String nome, String application, String permesso)
	{
		// se almeno uno dei paramentri non è valorizzato allora il diritto non c'è
		if(user==null || nome==null || application==null || permesso==null)
			return false;
		List<String> enti = getEnteFromContext(user, application);
		Iterator<String> it = enti.iterator();
		while (it.hasNext()) {
			String ente = it.next();
			Object[] dati={nome,ente};
			String dirittiApplication=MessageFormat.format(PLACEHOLDER_APPLICATION_NAME,dati);
			String item=MessageFormat.format(PLACEHOLDER_ENTITY_NAME,dati);
			String permessoEntity="";
			if(permesso.toUpperCase().equals(READ_ENTITY)){
				permessoEntity=MessageFormat.format(PLACEHOLDER_READ_ENTITY_RIGHT,dati);
			}else if(permesso.toUpperCase().equals(WRITE_ENTITY)){
				permessoEntity=MessageFormat.format(PLACEHOLDER_WRITE_ENTITY_RIGHT,dati);
			}else if(permesso.toUpperCase().equals(DELETE_ENTITY)){
				permessoEntity=MessageFormat.format(PLACEHOLDER_DELETE_ENTITY_RIGHT,dati);
			}
			if (autorizzato(user,dirittiApplication,item,permessoEntity)) {
				return true;
			}
		}
		return false;

	}
	*/
	
	/**
	 * Questo metodo si occupa di controllare se un utente è autorizzato <br>
	 * ad eseguire una determinata operazione.<br>
	 * Per prima cosa controlla se sono già stati caricati i permessi per l'utente;<br>
	 * in caso affermativo controlla se è autorizzato, altrimenti prima richiama il metodo riempiMappa(ssoUser)<br>
	 * che si occupa del recupero dei permessi per poi memorizzarli. 
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param application Il nome dell'applicazione 
	 * @param item L'oggetto dell'applicazione
	 * @param permesso Il permesso che descrive l'operazione che si vuole compiere
	 * @return true se è autorizzato, altrimenti false
	 */
	public synchronized static boolean autorizzato(Object user, String application, String item, String permesso)
	{
		return autorizzato(user,application,item, permesso,false);

	}
	
	/**
	 * Questo metodo si occupa di controllare se un utente è autorizzato <br>
	 * ad eseguire una determinata operazione.<br>
	 * Per prima cosa controlla se sono già stati caricati i permessi per l'utente;<br>
	 * in caso affermativo controlla se è autorizzato, altrimenti prima richiama il metodo riempiMappa(ssoUser)<br>
	 * che si occupa del recupero dei permessi per poi memorizzarli. 
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param application Il nome dell'applicazione 
	 * @param item L'oggetto dell'applicazione
	 * @param permesso Il permesso che descrive l'operazione che si vuole compiere
	 * @param refresh ricarica i diritti dal DB se impostato a true.
	 * @return true se è autorizzato, altrimenti false
	 */
	public synchronized static boolean autorizzato(Object user, String application, String item, String permesso, boolean refresh)
	{
		
		CeTUser cetUser =(CeTUser)user;
		
		
		String userName = cetUser.getName();
		PermessirBean perBean = (PermessirBean) mappa.get(userName);
		if (perBean == null || refresh)
		{
			perBean = riempiMappa(cetUser);
		}
		return perBean.getPermissions(application, item, permesso);

	}
	
	/** 
	 * Metodo che verifica la validità della password dell'utente che si connette ad un applicativo.
	 * @param conn La connessione allo schema AM dove effettuare il controllo
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param numGiorni Il numero dei giorni di validità della password dell'utente
	 * @return un array di due oggetti:<br>
	 * 1) String: il nome dell'utente loggato<br>
	 * 2) boolean: true se la password dell'utente è valida (non è scaduta), altrimenti false
	 */
	public synchronized static Object[] pwdValida(Connection conn, Object user, int numGiorni)
	{
		Object[] retVal = new Object[2];

		if( ! (user instanceof CeTUser) ) {
			retVal[0] = null;
			retVal[1] = new Boolean(false);
			return retVal;
		}		
			
		CeTUser cetUser =(CeTUser)user;
		
		try {
			String userName = cetUser.getName();
			retVal[0] = userName;
			PreparedStatement pstmt = conn.prepareStatement("SELECT TRUNC(DT_UPD_PWD) AS DT_UPD_PWD FROM AM_USER WHERE NAME = ?");
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getObject("DT_UPD_PWD") == null) {
					retVal[1] = new Boolean(false);
					return retVal;
				}					
				Date dtUpdPwd = rs.getDate("DT_UPD_PWD");
				GregorianCalendar calUpdPwd = new GregorianCalendar();
				calUpdPwd.setTime(dtUpdPwd);
				calUpdPwd.add(Calendar.DAY_OF_YEAR, numGiorni);
				GregorianCalendar calOggi = new GregorianCalendar();
				calOggi.set(Calendar.HOUR_OF_DAY, 0);
				calOggi.set(Calendar.MINUTE, 0);
				calOggi.set(Calendar.SECOND, 0);
				calOggi.set(Calendar.MILLISECOND, 0);
				retVal[1] = new Boolean(calOggi.getTime().getTime() <= calUpdPwd.getTime().getTime());
			}
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			return retVal;
		} catch (Exception e) {
			retVal[0] = null;
			retVal[1] = new Boolean(false);
			return retVal;
		}

	}
	
	/**
	 * Carica tutti i permessi assegnati ad un certo utente<br>
	 * senza distinzione tra applicazioni
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @return Una lista di stringhe contenente i nomi dei permessi
	 */
	public synchronized static ArrayList getPermissionsList(Object user)
	{
		return getPermissionsList(user,false);
	}	
	
	/**
	 * Carica tutti i permessi assegnati ad un certo utente<br>
	 * senza distinzione tra applicazioni
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param refresh ricarica i diritti dal DB se impostato a true.
	 * @return Una lista di stringhe contenente i nomi dei permessi
	 */
	public synchronized static ArrayList getPermissionsList(Object user, boolean refresh)
	{
		if( !(user instanceof CeTUser ) )
			return new ArrayList();
		
		CeTUser cetUser =(CeTUser)user;
		
			
		String userName = cetUser.getName();
		PermessirBean perBean = (PermessirBean) mappa.get(userName);
		if (perBean == null || refresh)
		{
			perBean = riempiMappa(cetUser);
		}
		return perBean.getPermissionsList();

	}	

	/**
	 * Recupera l'ente nelle proprietà dell'utente loggato nella sua proprietà ="property@-@ente@-@<context>"
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal().
	 * @param context Il context del quale vogliamo recuperare l'ente. 
	 * @return L'ente se trovato altrimenti stringa vuota "".
	 */
	/*
	public synchronized static List<String> getEnteFromContext(Object user, String context)
	{
		ArrayList<String> enti = new ArrayList<String>();
		
		String entePropertyString="property@-@ente@-@"+context;
		ArrayList lista =getListaPropertiesValues(user,entePropertyString);
		
		if(lista.size() > 0) {
			Iterator<String> it = lista.iterator();
			while (it.hasNext()) {
				enti.add(it.next());
			}
		} else {
			enti.add(ENTE_PREDEFINITO);
		}

		return enti;
	}
*/

	/**
	 * Ricava una lista di valori relativi ad una certa proprietà
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param nomeProperties Il nome della proprietà 
	 * @return La lista dei valori trovati, può essere vuota
	 */
	/*
	public synchronized static ArrayList getListaPropertiesValues(Object user, String nomeProperties)
	{
		
		return getListaPropertiesValues(user,nomeProperties,false);
	}
	*/
	/**
	 * Ricava una lista di valori relativi ad una certa proprietà
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param nomeProperties Il nome della proprietà 
	 * @return La lista dei valori trovati, può essere vuota
	 */
	/*
	public synchronized static ArrayList getListaPropertiesValues(Object user, String nomeProperties, boolean refresh)
	{
		
		if( !(user instanceof CeTUser ) )
			return new ArrayList();
		
		CeTUser cetUser =(CeTUser)user;

		String userName = cetUser.getName();
		PermessirBean perBean = (PermessirBean) mappa.get(userName);
		if (perBean == null || refresh)
		{
			perBean = riempiMappa(cetUser);
		}
		
		return perBean.getListaPropertiesValues(nomeProperties);
	}
	*/
	
	public synchronized static void clearCache(String username)
	{
		if(username != null)
			mappa.remove(username);
	}
	
	
	/**
	 * Carica tuttele applicazioni alle quali può accedere un utente
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @return Una lista di stringhe contenente i nomi delle applicazioni
	 */
	public synchronized static ArrayList getApplications(Object user)
	{
		return getApplications(user,false);
	}		
	/**
	 * Carica tuttele applicazioni alle quali può accedere un utente
	 * @param user L'oggetto recuperato dalla request mediante il metodo getUserPrincipal()
	 * @param refresh ricarica i diritti dal DB se impostato a true.
	 * @return Una lista di stringhe contenente i nomi delle applicazioni
	 */
	public synchronized static ArrayList getApplications(Object user, boolean refresh)
	{
		
		if( !(user instanceof CeTUser ) )
			return new ArrayList();
		
		CeTUser cetUser =(CeTUser)user;
		
			
		String userName = cetUser.getName();
		PermessirBean perBean = (PermessirBean) mappa.get(userName);
		if (perBean == null || refresh)
		{
			perBean = riempiMappa(cetUser);
		}
		return perBean.getApplications();

	}	
}
