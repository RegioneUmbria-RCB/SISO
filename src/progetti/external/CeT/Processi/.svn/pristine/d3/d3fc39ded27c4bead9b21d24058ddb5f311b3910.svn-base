package it.webred.rulengine.brick.loadDwh.load.docfa;

import it.webred.rulengine.brick.loadDwh.load.docfa.dataEstrazione.DataEstrazioneManager;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.utils.StringUtils;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Classe astratta che si occupa del caricamento dei dati
 * 
 * @author Petracca Marco
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:54 $
 */

public abstract class Caricatore<T extends  Env<?>>  extends  ConcreteImport<T> {

	private static final Logger	log	= Logger.getLogger(Caricatore.class.getName());
	public static final int RETURN_CONTINUE = 0;
	public static final int RETURN_FINE_NO_FILE = 100;
	
	
 	/**
 	 * Il costruttore può essere utilizato per creare una istanza di Caricatore attraverso la classe it.webred.utils.GestioneRisorse
 	*	@param configuration
 	*/
 	public Caricatore(String configuration)	{}



	/**
	 * Restituisce il nome della classe di default
	 * <p>
	 * che gestisce il reperimento della data di estrazione della fonte dati
	 */
	public abstract String getDefaultDataEstrazione();

	
	/**
	 * Metodo che restituisce il riferimento alla fonte dati
	 * @return Un Object con il riferimento alla fonte dati
	 * @throws Exception 
	 */
	public abstract Object getRiferimentoDS(TracciatoXml tracciatoxml) throws Exception;
 	


	
	/**
	 * Restituisce la data di estrazione coerentemente con il tipo di caricatore.
	 * <p>
	 * Sovrascrive il gestore di default del caricatore (se presente) con quello eventualmente specificato in tracciatoxml
	 * @param tracciatoxml
	 * @return Il Long contenente la data di estrazione dei dati
	 */
	public Long getDataEstrazone(TracciatoXml tracciatoxml)
	throws Exception
	{ 
		String classOK;
		String classData = this.getDefaultDataEstrazione();
		String classDataTracciato = tracciatoxml.getGestoreDataEstrazione();
		if (classDataTracciato !=null) {
			classOK = classDataTracciato;
		} else {
			classOK = classData;
		}
		Long dataCaricamento=new Long(0);
		dataCaricamento = DataEstrazioneManager.getDataEstrazione(this,classOK, tracciatoxml);
		return dataCaricamento;
		
	}
 	/**
	 * Esegue il caricamento dei dati richiamando il metodo executeCaricamentoSpec
	 * 
	 * @param tracciatoxml 
	 *            Il tracciato di configurazione
	 * @param nomeFileConf 
	 *            Il nome del file di configurazione
	 * @param processID 
	 *            ID del processo
	 * @return Restituisce un numero intero che indica l'esito delle operazioni eseguite
	 * @throws Exception
	 */	
	public int executeCaricamento(TracciatoXml tracciatoxml, String nomeFileConf, String processID,DocfaEnv env)
	throws Exception {
		Long dataCaricamento = this.getDataEstrazone(tracciatoxml);
		return this.executeCaricamentoSpec(tracciatoxml,nomeFileConf,processID, dataCaricamento,env);
	}
	
	
 	/**
	 * Esegue il caricamento dei dati
	 * 
	 * @param tracciatoxml 
	 *            Il tracciato di configurazione
	 * @param nomeFileConf 
	 *            Il nome del file di configurazione
	 * @param processID 
	 *            ID del processo
	 * @return Restituisce un numero intero che indica l'esito delle operazioni eseguite
	 * @throws Exception
	 */

	public abstract int executeCaricamentoSpec(TracciatoXml tracciatoxml, String nomeFileConf, String processID, Long datacaricamento, DocfaEnv env)
		throws Exception;

	/**
	 * Esegue il test del data source
	 * @param tracciatoxml 
	 *            Il tracciato di configurazione
	 * @throws Exception
	 */
	public abstract void testDataSource(TracciatoXml tracciatoxml)
		throws Exception;

	/**
	 * Compone la query per rinominare una tabella da oldName a newName
	 * 
	 * @param oldName 
	 *            Il vecchio nome della tabella
	 * @param newName 
	 *            Il nuoco nome della tabella
	 * @return La stringa contenente la query per rinominare una tabella
	 */
	protected static String rinominaTabella(String oldName, String newName)
	{
		String query = "RENAME TABLE " + oldName + " TO " + newName;
		return query;
	}

	/**
	 * Compone la query per eliminare una tabella
	 * 
	 * @param nomeTabella 
	 *            Il nome della tabella
	 * @return La stringa contenente la query per eliminare una tabella
	 */
	protected static String eliminaTabella(String nomeTabella)
	{
		String query = "drop table " + nomeTabella;
		return query;
	}

	/**
	 * Compone la query per trovare una tabella
	 * 
	 * @param nomeTabella 
	 *            Il nome della tabella
	 * @return La stringa contenente la query per la ricerca della tabella
	 */
	protected static String showTables(String nomeTabella)
	{
		StringBuffer query = new StringBuffer();
		
		//mysql version
		//query.append("show tables like '");
		//query.append(nomeTabella);
		//query.append("%'");
		
		//oracle version
		query.append("select table_name from user_tables where table_name like '%");
		query.append(nomeTabella);
		query.append("%'");
		
		return query.toString();
	}

	/**
	 * Effetuta i controlli sull'esistenza o meno delle tabelle _t1 e _t2;
	 * se _t1 è presente la cancella
	 * 
	 * @param tracciato  Il tracciato di configurazione
	 * @param con  Una connessione al DB
	 * @return Booleano che indica se la tabella t2 esiste
	 * @throws Exception
	 */
	protected boolean controlloTabelle(TracciatoXml tracciato, Connection con)
		throws Exception
	{
		Statement statment = null;
		statment = con.createStatement();
		try
		{
			String nomeTabella = tracciato.getNomeTabella();

			boolean tab_t1 = false;
			boolean tab_t2 = false;
			
			
			ResultSet rMod = statment.executeQuery(showTables(nomeTabella));
			while (rMod.next())
			{
				if (rMod.getString(1).equalsIgnoreCase(tracciato.getNomeTabella() + "_T1"))
				{
					log.debug("La tabella " + tracciato.getNomeTabella() + "_T1" + " esiste!");
					tab_t1 = true;

				}
				else if (rMod.getString(1).equalsIgnoreCase(tracciato.getNomeTabella() + "_T2"))
				{
					log.debug("La tabella " + tracciato.getNomeTabella() + "_T2" + " esiste!");
					tab_t2 = true;
				}
			}
			if (tab_t1)
			{
				statment.cancel();
				statment = con.createStatement();
				statment.executeUpdate("drop table " + tracciato.getNomeTabella() + "_T1");

				log.debug("La tabella " + tracciato.getNomeTabella() + "_T1" + " è stata eliminata");
			}
			
			statment.close();
			//creaTabelle(tracciato, con, tab_t2);
			return tab_t2; 
		}
		catch (Exception e)
		{
			log.error("Errore nel metodo controlloTabelle: ", e);
			throw e;

		}
		finally
		{
			if (statment != null)
				statment.close();
		}

	}
	
	protected String getCtrHash(String stringa) throws Exception {
		MessageDigest md = null;
		String hash = null;

		try	  {
			md = MessageDigest.getInstance("SHA");
			md.update(stringa.getBytes());
			byte[] b = md.digest();
			hash = new String(StringUtils.toHexString(b));
		} catch (Exception e)	  {
			log.error("Eccezione hash: "+e.getMessage(),e);
			throw e;
		}

		return hash;
	}

	

	/**
	 * Esegue la creazione delle tabelle _t1 e _t2 in base al tracciato di configurazione;
	 * 
	 * @param tracciato  Il tracciato di configurazione
	 * @param con  Una connessione al DB
	 * @param t2  Booleano che indica se la tabella _t2 è presente, in caso negativo la crea
	 * @throws Exception
	 */
	public void creaTabelle(TracciatoXml tracciato, Connection con, boolean t2)
		throws Exception
	{
		Statement statement = null;
		try
		{
			statement = con.createStatement();
			StringBuffer ct = new StringBuffer();
			StringBuffer pk = new StringBuffer("PRIMARY KEY (");
			int pkLength = pk.length();
			ct.append("CREATE TABLE ");
			String nomeTabella = tracciato.getNomeTabella()+"_T1";
			ct.append(nomeTabella + " (");
			for (int i = 0; i < tracciato.count(); i++)
			{
				ct.append(((Colonna) tracciato.getListaColonne().get(i)).getNome() + " VARCHAR2(" + ((Colonna) tracciato.getListaColonne().get(i)).getDimensioneOrig() + " BYTE),");
				if (((Colonna) tracciato.getListaColonne().get(i)).isChiave())
					pk.append(((Colonna) tracciato.getListaColonne().get(i)).getNome() + ",");

			}
			ct.append("HASH  VARCHAR2(100 BYTE)");
			if (pk.length() > pkLength)
			{
				pk = pk.replace(pk.lastIndexOf(","), pk.lastIndexOf(",") + 1, ")");
				ct.append("," + pk);
			}
			
			//ct.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8");
			ct.append(")");
			log.debug("Comando creazione tabella _T1:\n"+ct.toString());

			statement.executeUpdate(ct.toString());
			statement.close();
			
			if (!t2)
			{
				//String duplica = "CREATE TABLE " + nomeTabella + "_t2" + " LIKE " + nomeTabella + "_t1";
				String duplica = ct.toString().replaceAll("_T1", "_T2");
				log.debug("Comando creazione tabella _T2:\n"+duplica);
				
				statement = con.createStatement();
				statement.executeUpdate(duplica);
				statement.close();

			}
		}
		catch (Exception e)
		{
			log.error("Errore nel metodo creaTabelle: ", e);
			throw e;

		}
		finally
		{
			if (statement != null)
				statement.close();
		}

	}

	/**
	 * Apre una connessione al  db jdbc/caronteClient
	 * 
	 * @return La connessione 
	 * @throws Exception
	 */
	public static Connection apriConnessione()
		throws Exception
	{
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/caronteClient");
		Connection conn = ds.getConnection();
		return conn;
	}

	
}
