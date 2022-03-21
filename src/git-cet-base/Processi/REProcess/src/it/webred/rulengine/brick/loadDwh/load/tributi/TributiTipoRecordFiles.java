package it.webred.rulengine.brick.loadDwh.load.tributi;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.tributi.utils.TributiFileConverter;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.FileUtils;

public class TributiTipoRecordFiles<T extends TributiTipoRecordEnv<TestataStandardFileBean>> extends ImportStandardFiles<T> {
	
	private static final HashMap<String, String> tipiRecordGestiti = new HashMap<String, String>();
	static {
		tipiRecordGestiti.put("TRTRIBTARSUSOGG", "TRTRIBTARSUSOGG");
		tipiRecordGestiti.put("TRTRIBTARSUDICH", "TRTRIBTARSUDICH");
		tipiRecordGestiti.put("TRTRIBTARSUSOGGULT", "TRTRIBTARSUSOGGULT");
		tipiRecordGestiti.put("TRTRIBTARSUVIA", "TRTRIBTARSUVIA");
		tipiRecordGestiti.put("TRTRIBTARRIDUZ", "TRTRIBTARRIDUZ");
		tipiRecordGestiti.put("TRTRIBICISOGG", "TRTRIBICISOGG");
		tipiRecordGestiti.put("TRTRIBICIDICH", "TRTRIBICIDICH");
		tipiRecordGestiti.put("TRTRIBICISOGGULT", "TRTRIBICISOGGULT");
		tipiRecordGestiti.put("TRTRIBICIVIA", "TRTRIBICIVIA");
		tipiRecordGestiti.put("TRTRIBICIRIDUZ", "TRTRIBICIRIDUZ");
		tipiRecordGestiti.put("TRTRIBVICI", "TRTRIBVICI");		
	}
	
	private static String[] suffFileVecchioTracciato = new String[] {"dichiarazioniici.txt", 
																	"pagamentiici.txt", 
																	"soggetti.txt", 
																	"tarsu.txt",
																	"thsddetici.txt",
																	"thsdpici.txt",
																	"thsdpicii.txt",
																	"thsdsogg.txt",
																	"thsdorsu.txt"};
	
	public TributiTipoRecordFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles#preProcesing(java.sql.Connection)
	 */
	@Override
	public void preProcesingSpec(Connection con) throws RulEngineException {
		
		//creazione tabelle
		
		Statement st = null;
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBTARSUSOGG);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBTARSUSOGG esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBTARSUDICH);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBTARSUDICH esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBTARSUSOGGULT);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBTARSUSOGGULT esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBTARSUVIA);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBTARSUVIA esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBTARRIDUZ);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBTARRIDUZ esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBICISOGG);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBICISOGG esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBICIDICH);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBICIDICH esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBICISOGGULT);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBICISOGGULT esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBICIVIA);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBICIVIA esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBICIRIDUZ);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBICIRIDUZ esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		try {
			st = con.createStatement();
			st.execute(env.createTableTRTRIBVICI);
		} catch (SQLException e1) {
			log.warn("Tabella TRTRIBVICI esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		//creazione indici
		
		if (env.RE_TRIBUTI_TRTRIBTARSUSOGG_IDX != null && !env.RE_TRIBUTI_TRTRIBTARSUSOGG_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBTARSUSOGG_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBTARSUSOGG esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBTARSUDICH_IDX != null && !env.RE_TRIBUTI_TRTRIBTARSUDICH_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBTARSUDICH_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBTARSUDICH esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}

		if (env.RE_TRIBUTI_TRTRIBTARSUSOGGULT_IDX != null && !env.RE_TRIBUTI_TRTRIBTARSUSOGGULT_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBTARSUSOGGULT_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBTARSUSOGGULT esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBTARSUVIA_IDX != null && !env.RE_TRIBUTI_TRTRIBTARSUVIA_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBTARSUVIA_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBTARSUVIA esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBTARRIDUZ_IDX != null && !env.RE_TRIBUTI_TRTRIBTARRIDUZ_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBTARRIDUZ_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBTARRIDUZ esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBICISOGG_IDX != null && !env.RE_TRIBUTI_TRTRIBICISOGG_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBICISOGG_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBICISOGG esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBICIDICH_IDX != null && !env.RE_TRIBUTI_TRTRIBICIDICH_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBICIDICH_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBICIDICH esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBICISOGGULT_IDX != null && !env.RE_TRIBUTI_TRTRIBICISOGGULT_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBICISOGGULT_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBICISOGGULT esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBICIVIA_IDX != null && !env.RE_TRIBUTI_TRTRIBICIVIA_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBICIVIA_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBICIVIA esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBICIRIDUZ_IDX != null && !env.RE_TRIBUTI_TRTRIBICIRIDUZ_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBICIRIDUZ_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBICIRIDUZ esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		if (env.RE_TRIBUTI_TRTRIBVICI_IDX != null && !env.RE_TRIBUTI_TRTRIBVICI_IDX.equals("")) {
			try {
				st = con.createStatement();
				st.execute(env.RE_TRIBUTI_TRTRIBVICI_IDX);
			} catch (SQLException e1) {
				log.warn("Indice TRTRIBVICI esiste già: OK, BENE");
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}			
			}
		}
		
		//verifica dello svuotamento delle tabelle (nel caso di fornitura in replace e non)
		verificaSvuotamentoTabelle(con);
		
		//se i file sono nel vecchio tracciato, vengono convertiti nel nuovo
		convertiSeVecchioTracciato();
		
		
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "TRIBUTI";
	}
	
	private void verificaSvuotamentoTabelle(Connection con) throws RulEngineException {
		
		String[] tablesToDrop = new String[] {
			"RET_SIT_T_ICI_CONTIT",
			"RET_SIT_T_ICI_CONTRIB",
			"RET_SIT_T_ICI_DICH",
			"RET_SIT_T_ICI_OGG_ULTSOGG",
			"RET_SIT_T_ICI_OGGETTO",
			"RET_SIT_T_ICI_RIDUZIONI",
			"RET_SIT_T_ICI_SOGG",
			"RET_SIT_T_ICI_ULT_SOGG",
			"RET_SIT_T_ICI_VERSAMENTI",
			"RET_SIT_T_ICI_VIA",
			"RET_SIT_T_TAR_CONTRIB",
			"RET_SIT_T_TAR_DICH",
			"RET_SIT_T_TAR_OGG_ULTSOGG",
			"RET_SIT_T_TAR_OGGETTO",
			"RET_SIT_T_TAR_RIDUZIONI",
			"RET_SIT_T_TAR_SOGG",
			"RET_SIT_T_TAR_ULT_SOGG",
			"RET_SIT_T_TAR_VIA"
		};
		
		String[] tablesToTruncate = new String[] {
			"RE_TRIBUTI_TRTRIBICIDICH",
			"RE_TRIBUTI_TRTRIBICIRIDUZ",
			"RE_TRIBUTI_TRTRIBICISOGG",
			"RE_TRIBUTI_TRTRIBICISOGGULT",
			"RE_TRIBUTI_TRTRIBICIVIA",
			"RE_TRIBUTI_TRTRIBTARRIDUZ",
			"RE_TRIBUTI_TRTRIBTARSUDICH",
			"RE_TRIBUTI_TRTRIBTARSUSOGG",
			"RE_TRIBUTI_TRTRIBTARSUSOGGULT",
			"RE_TRIBUTI_TRTRIBTARSUVIA",
			"RE_TRIBUTI_TRTRIBVICI"/*,
			"SIT_T_ICI_CONTIT",
			"SIT_T_ICI_CONTRIB",
			"SIT_T_ICI_DICH",
			"SIT_T_ICI_OGG_ULTSOGG",
			"SIT_T_ICI_OGGETTO",
			"SIT_T_ICI_RIDUZIONI",
			"SIT_T_ICI_SOGG",
			"SIT_T_ICI_ULT_SOGG",
			"SIT_T_ICI_VERSAMENTI",
			"SIT_T_ICI_VIA",
			"SIT_T_TAR_CONTRIB",
			"SIT_T_TAR_DICH",
			"SIT_T_TAR_OGG_ULTSOGG",
			"SIT_T_TAR_OGGETTO",
			"SIT_T_TAR_RIDUZIONI",
			"SIT_T_TAR_SOGG",
			"SIT_T_TAR_ULT_SOGG",
			"SIT_T_TAR_VIA"*/
		};
		
		String[] tablesToDeleteNoElab = new String[] {
			"RE_TRIBUTI_TRTRIBICIDICH",
			"RE_TRIBUTI_TRTRIBICIRIDUZ",
			"RE_TRIBUTI_TRTRIBICISOGG",
			"RE_TRIBUTI_TRTRIBICISOGGULT",
			"RE_TRIBUTI_TRTRIBICIVIA",
			"RE_TRIBUTI_TRTRIBTARRIDUZ",
			"RE_TRIBUTI_TRTRIBTARSUDICH",
			"RE_TRIBUTI_TRTRIBTARSUSOGG",
			"RE_TRIBUTI_TRTRIBTARSUSOGGULT",
			"RE_TRIBUTI_TRTRIBTARSUVIA",
			"RE_TRIBUTI_TRTRIBVICI",
		};
		
		Statement st = null;
		
		try {
			//in ogni caso devono essere droppate le RET_
			for (String table : tablesToDrop) {
				try {
					st = con.createStatement();
					st.execute("DROP TABLE " + table);
				} catch (SQLException e1) {
					log.warn("La tabella temporanea " + table + " è già stata droppata: OK, BENE");
				}
				finally {
					try {
						if (st!=null)
							st.close();
					} catch (SQLException e1) {
					}			
				}
			}
			
			if (env.getEnteSorgente().isInReplace()) {
				for (String table : tablesToTruncate) {
					try {
						st = con.createStatement();
						st.execute("TRUNCATE TABLE " + table);
					} catch (SQLException e1) {
						log.error("Errore durante lo svuotamento preliminare della tabella " + table, e1);
						throw new RulEngineException("Errore durante lo svuotamento preliminare della tabella " + table, e1);
					}
					finally {
						try {
							if (st!=null)
								st.close();
						} catch (SQLException e1) {
						}			
					}
				}
			} else {
				for (String table : tablesToDeleteNoElab) {
					try {
						st = con.createStatement();
						st.execute("DELETE FROM " + table + " WHERE RE_FLAG_ELABORATO='0'");
					} catch (SQLException e1) {
						log.error("Errore durante lo svuotamento preliminare della tabella " + table, e1);
						throw new RulEngineException("Errore durante lo svuotamento preliminare della tabella " + table, e1);
					}
					finally {
						try {
							if (st!=null)
								st.close();
						} catch (SQLException e1) {
						}			
					}
				}
			}
		} catch (Exception e) {
			log.error("Errore durante lo svuotamento preliminare delle tabelle", e);
			throw new RulEngineException("Errore durante lo svuotamento preliminare delle tabelle", e);
		} finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}
		}
			
	}
	
	private void convertiSeVecchioTracciato() throws RulEngineException {
		Connection connTables = null;
		try {
			// unzip dei file
			String[] fs = it.webred.utils.FileUtils.cercaFileDaElaborare(this.percorsoFiles);
			for (int i = 0; i < fs.length; i++) {
				File f = new File(this.percorsoFiles+fs[i]);
				if (FileUtils.isZip(f))  {
					FileUtils.unzip(f);
					boolean del=false;
					int count = 1;
					while (!del) {
						del = f.delete();
						if (count>10000)
							throw new RulEngineException("Impossibile cancellare file zip dopo scompattamento - elaborazione interrotta");
						count+=1;
							
					}
				}
			}
			//controllo tipologia tracciato
			fs = it.webred.utils.FileUtils.cercaFileDaElaborare(this.percorsoFiles);
			boolean trovato = false;
			for (String f : fs) {
				if (trovato)
					break;
				for (String suff : suffFileVecchioTracciato) {
					if (f.toLowerCase().endsWith(suff.toLowerCase())) {
						trovato = true;
						break;
					}
				}
			}
			
			//verifico anche se i dati si possono trovare non in file di testo ma in tabelle (es. Milano schema TRIBUTI)				
			boolean trovatoTables = (env.connTablesDriverClass != null && !env.connTablesDriverClass.equals("")) &
						(env.connTablesConnString != null && !env.connTablesConnString.equals("")) &
						(env.connTablesUserName != null && !env.connTablesUserName.equals("")) &
						(env.connTablesPassword != null && !env.connTablesPassword.equals(""));
			
			if (!trovato) {
				trovato = trovatoTables;
			}
			if (trovato) {
				TributiFileConverter tfc = new TributiFileConverter();
				if (trovatoTables) {
					Class.forName(env.connTablesDriverClass);
					connTables = DriverManager.getConnection(env.connTablesConnString, env.connTablesUserName, env.connTablesPassword);
					tfc.setConnTables(connTables);
				}
				tfc.save(this.percorsoFiles, this.percorsoFiles, ctx.getBelfiore());
			}			
		} catch (Exception e) {
			log.error("Errore durante il controllo della tipologia dei tracciati: " + e.getMessage(), e);
			throw new RulEngineException("Errore durante il controllo della tipologia dei tracciati: " + e.getMessage(), e);
		}		
	}
	
	@Override
	public List<String> getValoriFromLine(String tipoRecord,String currentLine) throws RulEngineException {
		List<String> campi=null;
		//gestione campo n_comp_fam presente solo in v4 
		if("TRTRIBTARSUDICH".equals(tipoRecord) && !"4".equals(getVersioneTracciato()))
			currentLine = currentLine + "|";
		campi = Arrays.asList(currentLine.split("\\|", -1));
		return campi;
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sData = "";
		int i = file.lastIndexOf("_");
		
		if(file.startsWith("ICI") || file.startsWith("TARSU") || file.startsWith("VICI")){
			sData = file.substring(++i, i+8);
		}else{
			//vecchio tracciato
			sData = file.substring(5, 13);
		}
		
		try {
			env.saveFornitura(sdf.parse(sData), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}
	
}
