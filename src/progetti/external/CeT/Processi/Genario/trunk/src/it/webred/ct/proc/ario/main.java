package it.webred.ct.proc.ario;

import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.proc.ario.aggregatori.AggregatoreCivici;
import it.webred.ct.proc.ario.aggregatori.AggregatoreFabbricati;
import it.webred.ct.proc.ario.aggregatori.AggregatoreOggetti;
import it.webred.ct.proc.ario.aggregatori.AggregatoreSoggetti;
import it.webred.ct.proc.ario.aggregatori.AggregatoreVIe;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.gestoreVariazioni.GestoreVariazioniCivici;
import it.webred.ct.proc.ario.gestoreVariazioni.GestoreVariazioniFabbricati;
import it.webred.ct.proc.ario.gestoreVariazioni.GestoreVariazioniOggetti;
import it.webred.ct.proc.ario.gestoreVariazioni.GestoreVariazioniSoggetti;
import it.webred.ct.proc.ario.gestoreVariazioni.GestoreVariazioniVie;
import it.webred.ct.proc.ario.test.MainTest;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.InitialContext;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.impl.BaseCommandFactory;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.factory.JCommandFactory;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class main {

	public static Properties props = null;
	private static final Logger log = Logger.getLogger(main.class.getName());

	// Hash Map per gestione parametri di configurazione
	private static HashParametriConfBean paramConfBean = new HashParametriConfBean();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Context ctx = new ContextBase();
		Connection conn = null;
		Connection connAm = null;
		Connection connRe = null;
		Connection connForLongResultset = null;

		// recupero eventuali parametri di ingresso
		String codEnte = null;

		ArrayList<String> listaFontiNew = new ArrayList();
		List<AmFonteComune> listaFonti = null;

		try {

			caricaProps();

			System.out.println("#################### Recupero parametro Connessione da contesto #########################################################");

			String url = props.getProperty("conn.url");
			String user = props.getProperty("conn.user");
			String pass = props.getProperty("conn.pass");
			String driver = props.getProperty("conn.driver");
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);

			
			System.out
					.println("#################### Recupero parametro Codice Belfiore da contesto #####################################################");
			codEnte = props.getProperty("belfiore");

			// Carico da AM profile la lista delle fonti
			System.out
					.println("#################### Recupero parametro ListaFontiEnte da contesto ######################################################");
			String urlAm = props.getProperty("connAm.url");
			String userAm = props.getProperty("connAm.user");
			String passAm = props.getProperty("connAm.pass");
			connAm = DriverManager.getConnection(urlAm, userAm, passAm);
			Statement st = connAm.createStatement();
			String sql = "select * from am_fonte_comune where fk_am_comune = '"
					+ codEnte + "'";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				listaFontiNew.add(rs.getString("FK_AM_FONTE"));
			}

			// Recupera Parametri di Configurazione
			System.out.println("#################### Recupero e creo Hash parametri di configurazione ###################################################");
			recuperaParametriDiConfigurazione(connAm);

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
			Date resultdate = null;

			//Decorrelazione dati basata sulla tabella SIT_CORRELAZIONE_VARIAZIONI
			System.out.println("#########################################################################################################################");
			System.out.println("################## INIZIO PROCESSO DI DECORRELAZIONE DATI VARIATI (SIT_CORRELAZIONE_VARIAZIONI) #########################");
			System.out.println("#########################################################################################################################");

			connForLongResultset = DriverManager.getConnection(url, user, pass);
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione civici modificati "+ sdf.format(resultdate));
			System.out.println("Inizio esecuzione decorrelazione civici modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniCivici decCivici = new GestoreVariazioniCivici();
			decCivici.setConnection(conn);
			decCivici.setConnectionForLongResultset(connForLongResultset);
			decCivici.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione civici modificati "+ sdf.format(resultdate));
			System.out.println("Fine esecuzione decorrelazione civici modificati "+ sdf.format(resultdate));
			
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione vie modificate "+ sdf.format(resultdate));
			System.out.println("Inizio esecuzione decorrelazione vie modificate "+ sdf.format(resultdate));
			
			GestoreVariazioniVie decVie = new GestoreVariazioniVie();
			decVie.setConnection(conn);
			decVie.setConnectionForLongResultset(connForLongResultset);
			decVie.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione vie modificate "+ sdf.format(resultdate));
			System.out.println("Fine esecuzione decorrelazione vie modificate "+ sdf.format(resultdate));
			
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione oggetti modificati "+ sdf.format(resultdate));
			System.out.println("Inizio esecuzione decorrelazione oggetti modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniOggetti decOgg = new GestoreVariazioniOggetti();
			decOgg.setConnection(conn);
			decOgg.setConnectionForLongResultset(connForLongResultset);
			decOgg.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione oggetti modificati "+ sdf.format(resultdate));
			System.out.println("Fine esecuzione decorrelazione oggetti modificati "+ sdf.format(resultdate));
			
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione fabbricati modificati "+ sdf.format(resultdate));
			System.out.println("Inizio esecuzione decorrelazione fabbricati modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniFabbricati decFab = new GestoreVariazioniFabbricati();
			decFab.setConnection(conn);
			decFab.setConnectionForLongResultset(connForLongResultset);
			decFab.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione fabbricati modificati "+ sdf.format(resultdate));
			System.out.println("Fine esecuzione decorrelazione fabbricati modificati "+ sdf.format(resultdate));
			
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione soggetti modificati "+ sdf.format(resultdate));
			System.out.println("Inizio esecuzione decorrelazione soggetti modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniSoggetti decSogg = new GestoreVariazioniSoggetti();
			decSogg.setConnection(conn);
			decSogg.setConnectionForLongResultset(connForLongResultset);
			decSogg.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione soggetti modificati "+ sdf.format(resultdate));
			
			System.out.println("#########################################################################################################################");
			System.out.println("################## FINE PROCESSO DI DECORRELAZIONE DATI VARIATI (SIT_CORRELAZIONE_VARIAZIONI)  #########################");
			System.out.println("#########################################################################################################################");

			
			// Caricamento Fonti
			System.out.println("#########################################################################################################################");
			System.out.println("#################### INIZIO PROCESSO DI CARICAMENTO DATI GENARIO IN TABELLE _TOTALE #####################################");
			System.out.println("#########################################################################################################################");

			resultdate = new Date(System.currentTimeMillis());
			System.out.println("INIZIO Caricamento : " + sdf.format(resultdate));

			CaricatoreArioFactory caf = new CaricatoreArioFactory(listaFontiNew);
			caf.Execute(codEnte, conn, listaFontiNew, paramConfBean);

			resultdate = new Date(System.currentTimeMillis());
			System.out.println("FINE esecuzione CARICATORI " + sdf.format(resultdate));

			System.out.println("#########################################################################################################################");
			System.out.println("#################### FINE PROCESSO DI CARICAMENTO DATI GENARIO IN TABELLE _TOTALE #######################################");
			System.out.println("#########################################################################################################################");

			// Aggregazione Fonti
			resultdate = new Date(System.currentTimeMillis());
			System.out.println("INIZIO ESECUZIONE AGGREGAZIONE " + sdf.format(resultdate));
			if(conn.isClosed())
				conn = DriverManager.getConnection(url, user, pass);
			
			/*// creazione pool thread per altri tipi
			ExecutorService executor = Executors.newFixedThreadPool(3);
			// soggetti
			AggregatoreSoggetti sog = new AggregatoreSoggetti();
			sog.setConnection(conn);
			sog.setConnectionForLongResultset(DriverManager.getConnection(url, user, pass));
			sog.setCodEnte(codEnte);
			sog.setParamConfBean(paramConfBean);
			// oggetti
			AggregatoreOggetti ogg = new AggregatoreOggetti();
			ogg.setConnection(conn);
			ogg.setConnectionForLongResultset(DriverManager.getConnection(url, user, pass));
			ogg.setCodEnte(codEnte);
			ogg.setParamConfBean(paramConfBean);
			// fabbricati
			AggregatoreFabbricati fab = new AggregatoreFabbricati();
			fab.setConnection(conn);
			fab.setConnectionForLongResultset(DriverManager.getConnection(url, user, pass));
			fab.setCodEnte(codEnte);
			fab.setParamConfBean(paramConfBean);

			executor.submit(sog);
			executor.submit(ogg);
			executor.submit(fab);

			executor.shutdown();*/


			System.out.println("#########################################################################################################################");
			System.out.println("#################### INIZIO AGGREGAZIONE SOGGETTI #######################################################################");
			System.out.println("#########################################################################################################################");
			connForLongResultset = DriverManager.getConnection(url, user, pass);
					
			resultdate = new Date( System.currentTimeMillis());
			System.out.println("Inizio esecuzione aggregazione SOGGETTI "+ sdf.format(resultdate));
			
			AggregatoreSoggetti sog = new AggregatoreSoggetti();
			sog.setConnection(conn);
			sog.setConnectionForLongResultset(connForLongResultset);
			sog.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			System.out.println("Fine esecuzione aggregazione SOGGETTI "+ sdf.format(resultdate));
			
			System.out.println("#########################################################################################################################");
			System.out.println("#################### FINE AGGREGAZIONE SOGGETTI #########################################################################");
			System.out.println("#########################################################################################################################");
	
			System.out.println("#########################################################################################################################");
			System.out.println("#################### INIZIO AGGREGAZIONE OGGETTI ########################################################################");
			System.out.println("#########################################################################################################################");
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			
			resultdate = new Date( System.currentTimeMillis());
			System.out.println("Inizio esecuzione aggregazione OGGETTI "+ sdf.format(resultdate));
			
			AggregatoreOggetti og = new AggregatoreOggetti();
			og.setConnection(conn);
			og.setConnectionForLongResultset(connForLongResultset);
			og.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			System.out.println("Fine esecuzione aggregazione OGGETTI "+ sdf.format(resultdate));
			
			System.out.println("#########################################################################################################################");
			System.out.println("#################### FINE AGGREGAZIONE OGGETTI ########################################################################");
			System.out.println("#########################################################################################################################");
			
			
			System.out.println("#########################################################################################################################");
			System.out.println("#################### INIZIO AGGREGAZIONE FABBRICATI ########################################################################");
			System.out.println("#########################################################################################################################");
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			
			resultdate = new Date( System.currentTimeMillis());
			System.out.println("Inizio esecuzione aggregazione FABBRICATI "+ sdf.format(resultdate));
			
			AggregatoreFabbricati fa = new AggregatoreFabbricati();
			fa.setConnection(conn);
			fa.setConnectionForLongResultset(connForLongResultset);
			fa.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			System.out.println("Fine esecuzione aggregazione FABBRICATI "+ sdf.format(resultdate));
			
			System.out.println("#########################################################################################################################");
			System.out.println("#################### FINE AGGREGAZIONE FABBRICATI ########################################################################");
			System.out.println("#########################################################################################################################");   
			
			System.out.println("#########################################################################################################################");
			System.out.println("#################### INIZIO AGGREGAZIONE VIE, OGGETTI, SOGGETTI, FABBRICATI #############################################");
			System.out.println("#########################################################################################################################");
			connForLongResultset = DriverManager.getConnection(url, user, pass);

			resultdate = new Date(System.currentTimeMillis());

			System.out.println("Inizio esecuzione aggregazione VIE "
					+ sdf.format(resultdate));

			AggregatoreVIe vie = new AggregatoreVIe();
			vie.setConnection(conn);
			vie.setConnectionForLongResultset(connForLongResultset);
			vie.aggrega(codEnte, paramConfBean);

			conn.commit();
			DbUtils.close(connForLongResultset);

			resultdate = new Date(System.currentTimeMillis());
			System.out.println("Fine esecuzione aggregazione VIE "
					+ sdf.format(resultdate));

			System.out.println("#########################################################################################################################");
			System.out.println("#################### FINE VIE, INIZIO AGGREGAZIONE CIVICI ###############################################################");
			System.out.println("#########################################################################################################################");
			connForLongResultset = DriverManager.getConnection(url, user, pass);
			resultdate = new Date(System.currentTimeMillis());
			System.out.println("Inizio esecuzione aggregazione CIVICI "
					+ sdf.format(resultdate));

			AggregatoreCivici c = new AggregatoreCivici();
			c.setConnection(conn);
			c.setConnectionForLongResultset(connForLongResultset);
			c.aggrega(codEnte, paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);

			resultdate = new Date(System.currentTimeMillis());
			System.out.println("Fine esecuzione aggregazione CIVICI "
					+ sdf.format(resultdate));

			System.out.println("#########################################################################################################################");
			System.out.println("#################### FINE AGGREGAZIONE CIVICI ###########################################################################");
			System.out.println("#########################################################################################################################");

			
			/*while (!executor.isTerminated()) {
				Thread.sleep(60000);
			}*/

			resultdate = new Date(System.currentTimeMillis());
			System.out.println("FINE ESECUZIONE AGGREGAZIONE "
					+ sdf.format(resultdate));

		} catch (Exception e) {
			System.out.println("Errore nell'esecuzione di GeneraArio"
					+ e.getMessage());
			ErrorAck ea = new ErrorAck(e);
		} finally {
			try {
				if (connForLongResultset != null
						&& !connForLongResultset.isClosed())
					DbUtils.close(connForLongResultset);
				if (conn != null && !conn.isClosed())
					DbUtils.close(conn);
				if (connAm != null && !connAm.isClosed())
					DbUtils.close(connAm);
			} catch (SQLException e) {
				System.out.println("Problema con chiusura connessioni!");
			}
		}
	}

	/**
	 * Metodo che recupera i parametri di configurazione dal Contesto
	 */
	private static List<AmKeyValueExt> getParametriDiConfigurazione(Connection connAm) throws Exception{
		
		List<AmKeyValueExt> listaParam = new ArrayList<AmKeyValueExt>();
		try{
			
			Statement st = connAm.createStatement();
            String sql = "select * from am_key_value_ext where key_conf = 'fornitura.in.replace' " +
            		"or key_conf = 'criterio.lasco.soggetti' " +
            		"or key_conf = 'codice.orig.soggetto' " +
            		"or key_conf = 'codice.orig.via' " +
            		"or key_conf = 'codice.orig.civico' " +
            		"or key_conf = 'sezione.in.aggregazione' ";
            ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				
				AmKeyValueExt am = new AmKeyValueExt();
				String key = rs.getString("KEY_CONF");
				String fonte = rs.getString("FK_AM_FONTE");
				String value = rs.getString("VALUE_CONF");
				am.setKeyConf(key);
				am.setValueConf(value);
				if(fonte != null)
					am.setFkAmFonte(new Integer(fonte));
				listaParam.add(am);
			}
			
			return listaParam;
		}catch (Exception e) {
				log.error("Errore nel recupero dei parametri di configuazione per GeneraArio",e);
				Exception ea = new Exception("Errore nel recupero dei parametri di configuazione per GeneraArio :",e);
				throw ea;
			}
	}

	/**
	 * Metodo che recupera i parametri di configurazione dal Contesto
	 */
	private static void recuperaParametriDiConfigurazione(Connection connAm)
			throws Exception {

		try {

			Statement st = connAm.createStatement();
			String sql = "select * from am_key_value_ext where key_conf = 'fornitura.in.replace' "
					+ "or key_conf = 'criterio.lasco.soggetti' "
					+ "or key_conf = 'codice.orig.soggetto' "
					+ "or key_conf = 'codice.orig.via' "
					+ "or key_conf = 'codice.orig.civico' "
					+ "or key_conf = 'sezione.in.aggregazione' ";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {

				String key = rs.getString("KEY_CONF");
				String fonte = rs.getString("FK_AM_FONTE");
				String value = rs.getString("VALUE_CONF");

				// Recupero tabelle droppate
				if (key.equals("fornitura.in.replace")) {

					if (fonte != null && !fonte.equals("")) {
						key = key + "." + fonte;
					}

					if (value != null) {
						paramConfBean.getTabelleDroppate().put(key, value);
					}

					continue;
				}

				// Recupero criterio lasco soggetti
				if (key.equals("criterio.lasco.soggetti")) {

					if (value != null) {
						paramConfBean.getCriterioLascoSogg().put(key, value);
					}

					continue;

				}

				// Recupero codice orig soggetti
				if (key.equals("codice.orig.soggetto")) {

					if (fonte != null && !fonte.equals("")) {
						key = key + "." + fonte;
					}

					if (value != null) {
						paramConfBean.getCodiceOrigSogg().put(key, value);
					}

					continue;

				}

				// Recupero codice orig vie
				if (key.equals("codice.orig.via")) {

					if (fonte != null && !fonte.equals("")) {
						key = key + "." + fonte;
					}

					if (value != null) {
						paramConfBean.getCodiceOrigVie().put(key, value);
					}

					continue;

				}

				// Recupero codice orig civici
				if (key.equals("codice.orig.civico")) {

					if (fonte != null && !fonte.equals("")) {
						key = key + "." + fonte;
					}

					if (value != null) {
						paramConfBean.getCodiceOrigCiv().put(key, value);
					}

					continue;

				}

				// Recupero sezione in aggregazione oggetti
				if (key.equals("sezione.in.aggregazione")) {

					if (fonte != null && !fonte.equals("")) {
						key = key + "." + fonte;
					}

					if (value != null) {
						paramConfBean.getSezioneInAggrOgg().put(key, value);
					}

					continue;

				}

			}

		} catch (Exception e) {
			log.error(
					"Errore nel recupero dei parametri di configuazione per GeneraArio",
					e);
			Exception ea = new Exception(
					"Errore nel recupero dei parametri di configuazione per GeneraArio :",
					e);
			throw ea;
		}
	}

	public static void caricaProps() {

		try {

			props = new Properties();
			props.load(main.class.getResourceAsStream("/main.properties"));

		} catch (Exception e1) {
			System.out.println("ERRORE: " + e1.getMessage());
		}

	}
}
