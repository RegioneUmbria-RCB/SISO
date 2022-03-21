package it.webred.rulengine.brick.loadDwh.load;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;
import it.webred.utils.GenericTuples.T2;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.abaco.catasto.loadanalisi.LoadAnalyzer;
import com.abaco.catasto.loadxml.FileLoaderFromRepository;
import com.abaco.catasto.loadxml.exceptions.*;

public class ImportCatastoRule extends Command implements Rule {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportCatastoRule.class.getName());

	// nome della proprietà di configurazione
	public static final String DIR_FILE_KEY = "dir.files";

	public static final String SITIDATA_EXE_DIR_KEY = "sitidata.exe";
	
	public static final String SITIDATA_USERNAME_KEY = "sitidata.userName";
	
	public static final String SITIDATA_PASSWORD_KEY = "sitidata.password";
	
	public static final String SITIDATA_TEMP_DIR_KEY = "sitidata.tmp.dir";
	
	public static final String SITI_LOCATION_KEY = "siti.location";
	
	private String SQL_S3_TI09_CATALOGOSTATO;

	private String SQL_S3_TI04_SERVIZIDETTAGLIO;
	
	private String[] TAB_TARSU = {"load_cat_uiu_sup4","load_cat_uiu_sup1","load_cat_uiu_sup4_full","load_cat_uiu_sup1_full","sitiedi_vani","sitiedi_uiu_ext"};

	public ImportCatastoRule(BeanCommand bc) {
		super(bc);
	}

	public ImportCatastoRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;

		try {
			log.debug("Recupero parametro da contesto");
			
			String belfiore = ctx.getBelfiore();
			String fonte = ctx.getIdFonte().toString();
			
			// recupero parametri catena jelly
			this.getJellyParam(ctx);

			// recupero del path locale dell ente/fd
			ParameterService cdm = (ParameterService) ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(DIR_FILE_KEY, belfiore, fonte);
			String dirS = param.getValueConf();
			log.debug("Directory reperimento file: " + dirS);
			
			ParameterSearchCriteria psc = new ParameterSearchCriteria();
			psc.setComune(belfiore);
			psc.setSection("param.controller");
			psc.setInstance("Controller");
			
			psc.setKey(this.SITIDATA_EXE_DIR_KEY);
			String sitiDataExe = cdm.getAmKeyValueExt(psc).getValueConf();
			log.debug("Directory SitiData: " + sitiDataExe);  //"C:\\Program Files\\SITIClient\\SITIData.exe"
			
			psc.setKey(this.SITIDATA_USERNAME_KEY);
			String sitiDataUser = cdm.getAmKeyValueExt(psc).getValueConf();
			log.debug("User Name SitiData: " + sitiDataUser); //"admin"
			
			psc.setKey(this.SITIDATA_PASSWORD_KEY);
			String sitiDataPwd = cdm.getAmKeyValueExt(psc).getValueConf();
			log.debug("Password SitiData: " + sitiDataPwd); //"welcome"
			
			psc.setKey(this.SITI_LOCATION_KEY);
			String location = cdm.getAmKeyValueExt(psc).getValueConf();
			log.debug("Location Siti: "+location);
			
			psc.setKey(this.SITIDATA_TEMP_DIR_KEY);
			String tempPath = cdm.getAmKeyValueExt(psc).getValueConf();
			log.debug("Temp Path Caricamento Catasto: " + tempPath); //"C:\\temp"
			File tmp = new File(tempPath);
			if(!tmp.exists())
				tmp.mkdirs();
			
			long id_processo = 0;

			Connection connSiti = ctx.getConnection("SITI_" + belfiore);
			Connection connStaging = ctx.getConnection("STAGING_" + belfiore);

			DatabaseMetaData dbmd = connSiti.getMetaData();
			log.debug("db_url " + dbmd.getURL());
			log.debug("db_user " + dbmd.getUserName());
			log.debug("db_product_name " + dbmd.getDatabaseProductName());
			log.debug("db_product_version " + dbmd.getDatabaseProductVersion());
			log.debug("db_driver_name " + dbmd.getDriverName());
			log.debug("db_driver_version " + dbmd.getDriverVersion());
			log.debug("java_vendor "
					+ System.getProperties().getProperty("java.vendor"));
			log.debug("java_version "
					+ System.getProperties().getProperty("java.version"));
			log.debug("os_version "
					+ System.getProperties().getProperty("os.version"));
			log.debug("os_name "
					+ System.getProperties().getProperty("os.name"));
			log.debug("os_arch "
					+ System.getProperties().getProperty("os.arch"));

			File dir = new File(dirS);
			if (dir.exists()) {
				String message = null;
				try {

					log.debug("Avvio FileLoaderFromRepository...");
					
					//Inserito OVERRIDE in sede di PRIMA installazione per evitare il caricamento di info non ancora presenti o non di interesse (ex.: ICI)
					FileLoaderFromRepository loader = new FileLoaderFromRepository(connSiti, dirS, belfiore) {
						@Override
						public void addFile(int type, File file) {
							if (type != TYPE_ICI)
								super.addFile(type, file);
						}

					};
				
					log.debug("setSitiData()...");
					
					loader.setSitiData(new File(sitiDataExe), sitiDataUser, sitiDataPwd, new File(tempPath), location);
					
					log.debug("Avvio Processo Importazione..."); 
					
					id_processo = loader.process();

					log.debug("Processo Importazione FileLoaderFromRepository concluso correttamente [idProcesso:" + id_processo + "]");

					log.debug("Avvio processo LoadAnalyzer per il trasferimento dalle tabelle LOAD_ a SITI...");

					LoadAnalyzer analyzer = new LoadAnalyzer(connSiti);
					analyzer.process(false, false);

					log.debug("Processo LoadAnalyzer per il trasferimento dalle tabelle LOAD_ a SITI, concluso correttamente.");

					this.aggiornaCatalogoStato(connStaging);
					this.aggiornaServiziDettaglio(connStaging,belfiore);

					retAck = new ApplicationAck("Processo di caricamento completato correttamente [idProcesso:" + id_processo + "]");

				} catch (SAXException e) {
					message = "Errori di parsing del file indice (index.xml).";
					log.error(message,e);
					retAck = new ErrorAck(message);
				} catch (JAXBException e) {
					message = "Errori di parsing del file indice (index.xml).";
					log.error(message,e);
					retAck = new ErrorAck(message);
					
				} catch (NoFileException e) {
					message = "Non sono disponibili file da elaborare.";
					log.error(message,e);
					retAck = new ErrorAck(message);
					
				} catch (InvalidFileTypeException e) {
					message = "Tipo di file non valido";
					log.error(message, e);
					retAck = new ErrorAck(message);
					
				} catch (InvalidTipoImmobileException e) {
					message = "Presenza di un Fabbricato in un file di Terreni o viceversa (errore nel file XML).";
					log.error(message, e);
					retAck = new ErrorAck(message);
					
				} catch (MissingDatiPresentiException e) {
					message = "Nel file XML non è presente il nodo DatiPresenti.";
					log.error(message, e);
					retAck = new ErrorAck(message);
					
				} catch (InvalidSoggettoException e) {
					message = "Nel file XML dei soggetti è presente un soggetto di tipo sconosciuto (né SoggettoFisico né SoggettoGiuridico, errore nel file XML).";
					log.error(message, e);
					retAck = new ErrorAck(message);
					
				} catch (IncrementaleGiaCaricatoException e) {
					message = "Il file risulta essere già caricato (presente in tabella LOAD_CAT_INCREMENTALI).";
					log.error(message, e);
					retAck = new ErrorAck(message);
					

				} catch (Exception e) {
					log.error("Errore Generico da FileLoaderFromRepository", e);
					
					retAck = new ErrorAck(e);
				} finally {

				}

			} else {
				log.error("Directory " + dirS + "non esistente.", null);
				retAck = new ErrorAck("Directory " + dirS + "non esistente.");
			}

		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			
			retAck = new ErrorAck(e);
		}
		
		return retAck;
	}
	
	private void svuotaTabelleTarsu(Connection connSiti) throws SQLException {
			PreparedStatement ps = null;

			try {
				log.debug("Svuotamento Tabelle TARSU in corso...");
			
				for(String tab : this.TAB_TARSU){
					String sql = "DELETE FROM "+tab;
					log.debug("SQL["+sql+"]");
					ps = connSiti.prepareStatement(sql);
					ps.execute();
				}
				connSiti.commit();
			} catch (SQLException e) {
				
				log.error("Errore nello svuotamento delle Tabelle TARSU", e);
				

			} finally {
				ps.close();

			}	
	}
	

	private void aggiornaCatalogoStato(Connection connStaging)
			throws SQLException {
		PreparedStatement ps = null;

		try {
			log.debug("Aggiornamento Tabella s3_TI09_CATALOGOSTATO in corso...");
		
			log.debug("SQL["+ this.SQL_S3_TI09_CATALOGOSTATO +"]");
			ps = connStaging.prepareStatement(this.SQL_S3_TI09_CATALOGOSTATO);
			ps.execute();
			connStaging.commit();
		} catch (SQLException e) {
			
			log.error("Errore nell' aggiornamento di s3_TI09_CATALOGOSTATO", e);
			

		} finally {
			ps.close();

		}

	}

	private void aggiornaServiziDettaglio(Connection connStaging, String codEnte) throws SQLException {

		PreparedStatement ps = null;

		try {

			log.debug("Aggiornamento Tabella S3_TI04_SERVIZIDETTAGLIO in corso...");

			
			log.debug("SQL["+ this.SQL_S3_TI04_SERVIZIDETTAGLIO +"]");
			ps = connStaging.prepareStatement(this.SQL_S3_TI04_SERVIZIDETTAGLIO);
			ps.setString(1, codEnte);
			int ind = ps.executeUpdate();
			connStaging.commit();

			log.debug("Aggiornamento Tabella S3_TI04_SERVIZIDETTAGLIO completato: "
					+ ind + " record inseriti.");

		} catch (SQLException e) {
			
			log.error("Errore nell' aggiornamento di S3_TI04_SERVIZIDETTAGLIO", e);
			

		} finally {
			ps.close();

		}

	}
	
	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		String variabile = null;
		ComplexParam paramSql= (ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in."+index+".descr"));
		
		HashMap<String,ComplexParamP> p = paramSql.getParams();
		Set set = p.entrySet();
		Iterator it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			ComplexParamP cpp = (ComplexParamP)entry.getValue();
			Object o = TypeFactory.getType(cpp.getValore(),cpp.getType());
			variabile = o.toString();
		}
		
		log.debug("Query "+desc+" da eseguire:\n"+ variabile);
		return variabile;
		
	}
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			this.SQL_S3_TI09_CATALOGOSTATO= getJellyParam(ctx, 1 , "SQL_S3_TI09_CATALOGOSTATO");
			this.SQL_S3_TI04_SERVIZIDETTAGLIO = getJellyParam(ctx, 2 , "SQL_S3_TI04_SERVIZIDETTAGLIO");
			
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}

}
