package it.webred.ct.proc.ario.rule;


import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.proc.ario.CaricatoreArioFactory;
import it.webred.ct.proc.ario.GestioneStringheVie;
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
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;

import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class GeneraArio extends Command implements Rule {

	private static Properties props = null;
	
	private static final org.apache.log4j.Logger log = Logger.getLogger(GeneraArio.class.getName());

	
	//Hash Map per gestione parametri di configurazione	
	HashParametriConfBean paramConfBean = new HashParametriConfBean();
	
	
	public GeneraArio(BeanCommand bc, Properties jrulecfg) {
		
		super(bc, jrulecfg);		
		
	}



	@Override
	public CommandAck run(Context ctx) throws CommandException {
	
		
	
		Connection conn = null;
		Connection connForLongResultset = null;
								
		//recupero eventuali parametri di ingresso
		String codEnte =null;
		
		ArrayList<String> listaFontiNew = new ArrayList();
		List<AmFonteComune> listaFonti = null;
		
		try {
			
			
			//Recupera Parametri di Configurazione	
			log.debug("#################### Recupero e creo Hash parametri di configurazione ###################################################");
			this.recuperaParametriDiConfigurazione(ctx);
			
			
			log.debug("#################### Recupero parametro Connessione da contesto #########################################################");
			conn = ctx.getConnection((String)ctx.get("connessione"));
						
			log.debug("#################### Recupero parametro Codice Belfiore da contesto #####################################################");
			codEnte = ctx.getBelfiore();
					
			
			//Carico da AM profile la lista delle fonti
			log.debug("#################### Recupero parametro ListaFontiEnte da contesto ######################################################");
			listaFonti = ctx.getFonteDatiEnte();				
			
			for(int i=0; i<listaFonti.size(); i++){
											
				AmFonteComune fonte = (AmFonteComune)listaFonti.get(i);
				listaFontiNew.add(fonte.getId().getFkAmFonte().toString());
				
			}
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
			Date resultdate = null;

			//Decorrelazione dati basata sulla tabella SIT_CORRELAZIONE_VARIAZIONI
			log.debug("#########################################################################################################################");
			log.debug("################## INIZIO PROCESSO DI DECORRELAZIONE DATI VARIATI (SIT_CORRELAZIONE_VARIAZIONI) #########################");
			log.debug("#########################################################################################################################");

			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione civici modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniCivici decCivici = new GestoreVariazioniCivici();
			decCivici.setConnection(conn);
			decCivici.setConnectionForLongResultset(connForLongResultset);
			decCivici.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione civici modificati "+ sdf.format(resultdate));
			
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione vie modificate "+ sdf.format(resultdate));
			
			GestoreVariazioniVie decVie = new GestoreVariazioniVie();
			decVie.setConnection(conn);
			decVie.setConnectionForLongResultset(connForLongResultset);
			decVie.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione vie modificate "+ sdf.format(resultdate));
			
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione oggetti modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniOggetti decOgg = new GestoreVariazioniOggetti();
			decOgg.setConnection(conn);
			decOgg.setConnectionForLongResultset(connForLongResultset);
			decOgg.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione oggetti modificati "+ sdf.format(resultdate));
			
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione fabbricati modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniFabbricati decFab = new GestoreVariazioniFabbricati();
			decFab.setConnection(conn);
			decFab.setConnectionForLongResultset(connForLongResultset);
			decFab.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione fabbricati modificati "+ sdf.format(resultdate));
			
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione decorrelazione soggetti modificati "+ sdf.format(resultdate));
			
			GestoreVariazioniSoggetti decSogg = new GestoreVariazioniSoggetti();
			decSogg.setConnection(conn);
			decSogg.setConnectionForLongResultset(connForLongResultset);
			decSogg.elabora(codEnte);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione decorrelazione soggetti modificati "+ sdf.format(resultdate));
			
			log.debug("#########################################################################################################################");
			log.debug("################## FINE PROCESSO DI DECORRELAZIONE DATI VARIATI (SIT_CORRELAZIONE_VARIAZIONI)  #########################");
			log.debug("#########################################################################################################################");

		
			//Caricamento Fonti
			log.debug("#########################################################################################################################");
			log.debug("#################### INIZIO PROCESSO DI CARICAMENTO DATI GENARIO IN TABELLE _TOTALE #####################################");
			log.debug("#########################################################################################################################");

			   	 
			resultdate = new Date( System.currentTimeMillis());        	
        	log.info("INIZIO Caricamento : " + sdf.format(resultdate));
			
			CaricatoreArioFactory caf = new CaricatoreArioFactory(listaFontiNew);			
			caf.Execute(codEnte, conn, listaFontiNew, paramConfBean);
						
			resultdate = new Date( System.currentTimeMillis());
			log.info("FINE esecuzione CARICATORI "+ sdf.format(resultdate));        	

			log.debug("#########################################################################################################################");
			log.debug("#################### FINE PROCESSO DI CARICAMENTO DATI GENARIO IN TABELLE _TOTALE #######################################");
			log.debug("#########################################################################################################################");
		
			
			
			//Aggregazione Fonti						
			resultdate = new Date( System.currentTimeMillis());
			log.info("INIZIO ESECUZIONE AGGREGAZIONE "+ sdf.format(resultdate)); 

			log.debug("#########################################################################################################################");
			log.debug("#################### INIZIO AGGREGAZIONE VIE ############################################################################");
			log.debug("#########################################################################################################################");						       	
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			
			resultdate = new Date( System.currentTimeMillis());
			
			log.info("Inizio esecuzione aggregazione VIE "+ sdf.format(resultdate));
			
			AggregatoreVIe vie = new AggregatoreVIe();
			vie.setConnection(conn);
			vie.setConnectionForLongResultset(connForLongResultset);
			vie.aggrega(codEnte, paramConfBean);

			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione aggregazione VIE "+ sdf.format(resultdate));
			
			log.debug("#########################################################################################################################");
			log.debug("#################### FINE AGGREGAZIONE VIE ##############################################################################");
			log.debug("#########################################################################################################################");
		
			log.debug("#########################################################################################################################");
			log.debug("#################### INIZIO AGGREGAZIONE CIVICI #########################################################################");
			log.debug("#########################################################################################################################");
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione aggregazione CIVICI "+ sdf.format(resultdate));
			
			AggregatoreCivici c = new AggregatoreCivici();
			c.setConnection(conn);
			c.setConnectionForLongResultset(connForLongResultset);
			c.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione aggregazione CIVICI "+ sdf.format(resultdate));
			
			log.debug("#########################################################################################################################");
			log.debug("#################### FINE AGGREGAZIONE CIVICI ###########################################################################");
			log.debug("#########################################################################################################################");

			log.debug("#########################################################################################################################");
			log.debug("#################### INIZIO AGGREGAZIONE SOGGETTI #######################################################################");
			log.debug("#########################################################################################################################");
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
					
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione aggregazione SOGGETTI "+ sdf.format(resultdate));
			
			AggregatoreSoggetti sog = new AggregatoreSoggetti();
			sog.setConnection(conn);
			sog.setConnectionForLongResultset(connForLongResultset);
			sog.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione aggregazione SOGGETTI "+ sdf.format(resultdate));
			
			log.debug("#########################################################################################################################");
			log.debug("#################### FINE AGGREGAZIONE SOGGETTI #########################################################################");
			log.debug("#########################################################################################################################");
	
			log.debug("#########################################################################################################################");
			log.debug("#################### INIZIO AGGREGAZIONE OGGETTI ########################################################################");
			log.debug("#########################################################################################################################");
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione aggregazione OGGETTI "+ sdf.format(resultdate));
			
			AggregatoreOggetti og = new AggregatoreOggetti();
			og.setConnection(conn);
			og.setConnectionForLongResultset(connForLongResultset);
			og.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione aggregazione OGGETTI "+ sdf.format(resultdate));
			
			log.debug("#########################################################################################################################");
			log.debug("#################### FINE AGGREGAZIONE OGGETTI ########################################################################");
			log.debug("#########################################################################################################################");
			
			
			log.debug("#########################################################################################################################");
			log.debug("#################### INIZIO AGGREGAZIONE FABBRICATI ########################################################################");
			log.debug("#########################################################################################################################");
			connForLongResultset = RulesConnection.getConnection("DWH_" + ctx.getBelfiore());
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Inizio esecuzione aggregazione FABBRICATI "+ sdf.format(resultdate));
			
			AggregatoreFabbricati fa = new AggregatoreFabbricati();
			fa.setConnection(conn);
			fa.setConnectionForLongResultset(connForLongResultset);
			fa.aggrega(codEnte,paramConfBean);
			conn.commit();
			DbUtils.close(connForLongResultset);
			
			resultdate = new Date( System.currentTimeMillis());
			log.info("Fine esecuzione aggregazione FABBRICATI "+ sdf.format(resultdate));
			
			log.debug("#########################################################################################################################");
			log.debug("#################### FINE AGGREGAZIONE FABBRICATI ########################################################################");
			log.debug("#########################################################################################################################");

			resultdate = new Date( System.currentTimeMillis());
			log.info("FINE ESECUZIONE AGGREGAZIONE "+ sdf.format(resultdate));        	
		
			return new ApplicationAck("GeneraArio eseguito");
		}catch (Exception e)
		{
			log.error("Errore nell'esecuzione di GeneraArio",e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		} finally {
			try {
				if (connForLongResultset!=null && !connForLongResultset.isClosed())
					DbUtils.close(connForLongResultset);
			} catch (SQLException e) {
				log.warn("Problma con chiusura connessione lunga!", e);
			}
		}
	}
   	
	
	
	/**
	 * Metodo che recupera i parametri di configurazione dal Contesto
	 */
	private void recuperaParametriDiConfigurazione(Context ctx) throws Exception{
		
		
		try{
			
			List listaParametriConf = ctx.getParametriConfigurazioneEnte();
			
			for(int i=0; i<listaParametriConf.size();i++){
				
				AmKeyValueExt param = (AmKeyValueExt)listaParametriConf.get(i);
				
				//Recupero tabelle droppate
				if(param.getKeyConf().equals("fornitura.in.replace")){
					
					String key = param.getKeyConf();
					if(param.getFkAmFonte()!=null && !param.getFkAmFonte().equals("")){
						key = key +"."+param.getFkAmFonte(); 
					}		
					
					if(param.getValueConf()!= null){
						paramConfBean.getTabelleDroppate().put(key, param.getValueConf());
					}
					
					continue;
				}
				
				//Recupero criterio lasco soggetti
				if(param.getKeyConf().equals("criterio.lasco.soggetti")){
										
					String key = param.getKeyConf();
					if(param.getValueConf()!= null){
						paramConfBean.getCriterioLascoSogg().put(key, param.getValueConf());
					}
										
					continue;
					
				}
				
				//Recupero codice orig soggetti
				if(param.getKeyConf().equals("codice.orig.soggetto")){
					
					String key = param.getKeyConf();
					if(param.getFkAmFonte()!=null && !param.getFkAmFonte().equals("")){
						key = key +"."+param.getFkAmFonte(); 
					}	
					
					if(param.getValueConf()!= null){
						paramConfBean.getCodiceOrigSogg().put(key, param.getValueConf());
					}	
					
					continue;
					
				}
				
				//Recupero codice orig vie
				if(param.getKeyConf().equals("codice.orig.via")){
					
					String key = param.getKeyConf();
					if(param.getFkAmFonte()!=null && !param.getFkAmFonte().equals("")){
						key = key +"."+param.getFkAmFonte(); 
					}		
					
					if(param.getValueConf()!= null){
						paramConfBean.getCodiceOrigVie().put(key, param.getValueConf());
					}	
					
					continue;
					
				}
				
				//Recupero codice orig civici
				if(param.getKeyConf().equals("codice.orig.civico")){
					
					String key = param.getKeyConf();
					if(param.getFkAmFonte()!=null && !param.getFkAmFonte().equals("")){
						key = key +"."+param.getFkAmFonte(); 
					}	
					
					if(param.getValueConf()!= null){
						paramConfBean.getCodiceOrigCiv().put(key, param.getValueConf());
					}	
					
					continue;
					
				}
				
				//Recupero sezione in aggregazione oggetti
				if(param.getKeyConf().equals("sezione.in.aggregazione")){
					
					String key = param.getKeyConf();
					if(param.getFkAmFonte()!=null && !param.getFkAmFonte().equals("")){
						key = key +"."+param.getFkAmFonte(); 
					}				
					
					if(param.getValueConf()!= null){
						paramConfBean.getSezioneInAggrOgg().put(key, param.getValueConf());
					}	
					
					continue;
					
				}
				
			}
			
		}catch (Exception e) {
			log.error("Errore nel recupero dei parametri di configuazione per GeneraArio",e);
			Exception ea = new Exception("Errore nel recupero dei parametri di configuazione per GeneraArio :",e);
			throw ea;
		}
	}
}
