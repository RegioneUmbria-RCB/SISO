package it.webred.rulengine.brick.elab.soggetti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.cataloghi.ValoreMedioRenditaVano;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class VerificaInfoSoggettiSovvenzioni extends Command implements Rule {

	private static final Logger log = Logger.getLogger(VerificaInfoSoggettiSovvenzioni.class.getName());
	private static final String STR_CONN_URL_DEFAULT = "jdbc:oracle:thin:@roma:1521:dbcat"; //DIOGENE MILANO?
	private static final String STR_CONN_USER_DEFAULT = "DIOGENE_F704"; //DIOGENE
	private static final String STR_CONN_PWD_DEFAULT = "DIOGENE_F704"; //DIOGENE
	
	private static final String DIR_FILES_DEFAULT = "C:/elab/soggettiSovvenzioni/";
	
	private static final String SQL_DROP_APPO_TITOLARI_CATASTO = "DROP TABLE APPO_TITOLARI_CATASTO";
	
	private static final String SQL_CREATE_APPO_TITOLARI_CATASTO = "CREATE TABLE APPO_TITOLARI_CATASTO AS  " +
								"SELECT DISTINCT S.* FROM SIT_SOGGETTO_UNICO S, " +
								"(" +
								"SELECT DISTINCT TOT_CAT.FK_SOGGETTO " +
								"FROM CONS_SOGG_TAB SOGGCAT, SIT_SOGGETTO_TOTALE TOT_CAT " +
								"WHERE TOT_CAT.FK_ENTE_SORGENTE = '4' AND TOT_CAT.PROG_ES = '3' " +
								"AND TOT_CAT.ID_DWH = SOGGCAT.PKID " +
								"AND FLAG_PERS_FISICA = 'P' AND PK_CUAA IN " +
								"(SELECT DISTINCT PK_CUAA " +
								"FROM SITICONDUZ_IMM_ALL COND " +
								"WHERE COND.DATA_INIZIO < SYSDATE " +
								"AND COND.DATA_FINE >= SYSDATE " +
								"AND TIPO_TITOLO = 1 " +
								"GROUP BY PK_CUAA) " +
								") S_UNICO " +
								"WHERE S_UNICO.FK_SOGGETTO = S.ID_SOGGETTO " +
								"ORDER BY COGNOME, NOME, DT_NASCITA";
	
	private static final String SQL_DROP_APPO_COMPONENTI_FAMIGLIA = "DROP TABLE APPO_COMPONENTI_FAMIGLIA";
	
	private static final String SQL_CREATE_APPO_COMPONENTI_FAMIGLIA = "CREATE TABLE APPO_COMPONENTI_FAMIGLIA AS " +
								"SELECT FAMI.ID_ORIG FAMIGLIA, PERS.CODFISC CF, PF.RELAZ_PAR " +
								"FROM SIT_D_PERS_FAM PF " +
								"INNER JOIN SIT_D_PERSONA PERS " +
								"ON PF.ID_EXT_D_PERSONA = PERS.ID_EXT " +
								"INNER JOIN SIT_D_FAMIGLIA FAMI " +
								"ON FAMI.ID_EXT = PF.ID_EXT_D_FAMIGLIA " +
								"WHERE FAMI.DT_FINE_VAL IS NULL " +
								"AND FAMI.DT_FINE_DATO IS NULL " +
								"AND PF.DT_FINE_DATO IS NULL " +
								"AND PF.DT_FINE_VAL IS NULL " +
								"AND PERS.DT_FINE_DATO IS NULL " +
								"AND PERS.DT_FINE_VAL IS NULL " +
								"AND SYSDATE BETWEEN NVL(PERS.DATA_NASCITA,TO_DATE('01/01/1000','DD/MM/YYYY'))  AND NVL(PERS.DATA_MOR,SYSDATE) " +
								"AND SYSDATE BETWEEN NVL(PERS.DT_INIZIO_DATO,TO_DATE('01/01/1000','DD/MM/YYYY')) AND NVL(PERS.DT_FINE_DATO,SYSDATE) " +
								"AND SYSDATE BETWEEN NVL(PERS.DATA_IMM,TO_DATE('01/01/1000','DD/MM/YYYY')) AND NVL(PERS.DATA_EMI,SYSDATE) " +
								"ORDER BY FAMIGLIA, DECODE(RELAZ_PAR, 'INTESTATARIO', 1, 'INTESTATARIA', 1, 'CF', 1, 2)";
	
	private static final String SQL_DROP_APPO_IMMOBILI_CATASTO = "DROP TABLE APPO_IMMOBILI_CATASTO";
	
	private static final String SQL_CREATE_APPO_IMMOBILI_CATASTO = "CREATE TABLE APPO_IMMOBILI_CATASTO AS " +
								"SELECT DISTINCT UI.FOGLIO, UI.PARTICELLA, UI.UNIMM AS SUB, " +
								"UI.CATEGORIA, UI.CONSISTENZA, UI.SUP_CAT, UI.CLASSE, UI.RENDITA, UI.PIANO, SC.CUAA, " +
								"TO_CHAR (UI.DATA_INIZIO_VAL, 'DD/MM/YYYY') AS DT_INIZIO_VAL, " +
								"TO_CHAR (UI.DATA_FINE_VAL, 'DD/MM/YYYY') AS DT_FINE_VAL " +
								"FROM SITIUIU UI, SITICONDUZ_IMM_ALL SC " +
								"WHERE UI.DATA_FINE_VAL=TO_DATE('99991231','YYYYMMDD') " +
								"AND UI.COD_NAZIONALE=SC.COD_NAZIONALE (+) " +
								"AND UI.DATA_FINE_VAL=SC.DATA_FINE (+) " +
								"AND UI.FOGLIO=SC.FOGLIO(+) " +
								"AND UI.PARTICELLA=SC.PARTICELLA(+) " +
								"AND UI.SUB=SC.SUB(+) " +
								"AND UI.UNIMM=SC.UNIMM(+) " +
								"AND SC.CUAA IS NOT NULL";
	
	private static final String SQL_SELECT_CODFISC_FAMIGLIA = "SELECT A.CF " +
								"FROM APPO_COMPONENTI_FAMIGLIA A " +
								"WHERE A.FAMIGLIA = " +
								"(SELECT B.FAMIGLIA " +
								"FROM APPO_COMPONENTI_FAMIGLIA B " +
								"WHERE B.CF = ?" +
								")";
	
	private static final String SQL_COUNT_IMM_FAMIGLIA = "SELECT COUNT(*) AS CONTA " +
								"FROM APPO_IMMOBILI_CATASTO " +
								"WHERE @@@";

	private static final String SQL_SELECT_DETTAGLIO_IMM_FAMIGLIA = "SELECT UI.CATEGORIA, COUNT(*) AS CONTA " +
								"FROM SITIUIU UI, SITICONDUZ_IMM_ALL SC " +
								"WHERE UI.DATA_FINE_VAL = TO_DATE('99991231','yyyymmdd') " +
								"AND SC.COD_NAZIONALE = (SELECT CODENT FROM SIT_ENTE) " + 
								"AND UI.COD_NAZIONALE = SC.COD_NAZIONALE (+) " +
								"AND UI.DATA_FINE_VAL = SC.DATA_FINE (+) " +
								"AND UI.FOGLIO = SC.FOGLIO(+) " +
								"AND UI.PARTICELLA = SC.PARTICELLA(+) " +
								"AND UI.SUB = SC.SUB(+) " +
								"AND UI.UNIMM = SC.UNIMM(+) " +
								"AND REGEXP_INSTR(SC.CUAA, '^[A-Za-z]{6}[0-9]{2}[A-Za-z]{1}[0-9]{2}[A-Za-z]{1}[0-9]{3}[A-Za-z]{1}$') = 1 " +
								"AND @@@ " +
								"GROUP BY UI.CATEGORIA " +
								"ORDER BY UI.CATEGORIA ASC";

	private static final String SQL_SELECT_REDDITI_FAMIGLIA = "SELECT RED_REDDITI_DICHIARATI.ANNO_IMPOSTA, NVL(SUM(TO_NUMBER (SUBSTR(RED_REDDITI_DICHIARATI.VALORE_CONTABILE, 2) / RED_TRASCODIFICA.CENT_DIVISORE)), 0) TOTALE " +
								"FROM RED_REDDITI_DICHIARATI, RED_TRASCODIFICA " +
								"WHERE @@@ " +
								"AND ((RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2012' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL075001'," +
								"'PL075002','RA011010','RA011011','RA052009','RA052010'," +
								"'RB010008','RB010013','RB035009','RN004001','RN006002'," +
								"'RN016001','RV010002','TN004002')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2011' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL075001'," +
								"'PL075002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB010011','RB035008','RB035009','RN004001','RN006002'," +
								"'RN016001','RV010002','TN004002')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2010' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL075001'," +
								"'PL075002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN004001','RN006002'," +
								"'RN016001','RV010002','TN004002')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2009' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL075001'," +
								"'PL075002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN004001','RN006002'," +
								"'RN016001','RV010002','TN004002')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2008' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL039001'," +
								"'PL039002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN004001','RN006002'," +
								"'RN016001','RV010002','TN004002')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2007' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL039001'," +
								"'PL039002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN004001','RN006002'," +
								"'RN016001','RV010002','TN004002')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2006' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL033001'," +
								"'PL033002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN005002','RN006001'," +
								"'RN016001','RV010002','TN004001')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2005' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL033001'," +
								"'PL033002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN005002','RN006001'," +
								"'RN016001','RV010002','TN004001')) " +
								"OR (RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = '2004' AND RED_REDDITI_DICHIARATI.CODICE_QUADRO IN " +
								"('DB001011','PL001001','PL001002','PL002001','PL002002'," +
								"'PL003001','PL003002','PL071001','PL071002','PL033001'," +
								"'PL033002','RA011009','RA011010','RA052009','RA052010'," +
								"'RB011008','RB035008','RB035009','RN005002','RN005004'," +
								"'RN016002','RV010002','TN004002'))) " +
								"AND RED_REDDITI_DICHIARATI.ANNO_IMPOSTA = RED_TRASCODIFICA.ANNO_IMPOSTA " +
								"AND RED_REDDITI_DICHIARATI.CODICE_QUADRO = RED_TRASCODIFICA.CODICE_RIGA " +
								"GROUP BY RED_REDDITI_DICHIARATI.ANNO_IMPOSTA " +
								"ORDER BY RED_REDDITI_DICHIARATI.ANNO_IMPOSTA DESC";


	
	public VerificaInfoSoggettiSovvenzioni(BeanCommand bc) {
		super(bc);
	}

	public VerificaInfoSoggettiSovvenzioni(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		String dirFiles = null;
		Connection conn = null;
		CommandAck retAck = null;
		try {
			VerificaInfoSoggettiSovvenzioniEnv env = new VerificaInfoSoggettiSovvenzioniEnv((String)ctx.get("connessione"), ctx);
			dirFiles = env.dirFiles;
			conn = env.getConn();
			retAck = eseguiVerifica(conn, dirFiles);
		} catch(Exception e){
			log.error(e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch(Exception e){
				log.error(e);
			}
		}
		return retAck;
	}
	
	private static CommandAck eseguiVerifica(Connection conn, String dirFiles) {
		CommandAck retAck = null;
		creaTabelleAppoggio(conn);
		elabora(conn, dirFiles);
		retAck = new ApplicationAck("ESECUZIONE OK");
		return retAck;
	}
	
	private static void creaTabelleAppoggio(Connection conn) {
		Statement st = null;
		
		try {
			st = conn.createStatement();
			st.execute(SQL_DROP_APPO_TITOLARI_CATASTO);
		} catch (Exception e) {
			System.out.println("La tabella APPO_TITOLARI_CATASTO non esiste: si procede con la creazione");
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				log.error(e);
			}			
		}
		
		try {
			st = conn.createStatement();
			st.execute(SQL_CREATE_APPO_TITOLARI_CATASTO);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}		
		
		try {
			st = conn.createStatement();
			st.execute(SQL_DROP_APPO_COMPONENTI_FAMIGLIA);
		} catch (Exception e) {
			System.out.println("La tabella APPO_COMPONENTI_FAMIGLIA non esiste: si procede con la creazione");
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		try {
			st = conn.createStatement();
			st.execute(SQL_CREATE_APPO_COMPONENTI_FAMIGLIA);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		try {
			st = conn.createStatement();
			st.execute(SQL_DROP_APPO_IMMOBILI_CATASTO);
		} catch (Exception e) {
			System.out.println("La tabella APPO_IMMOBILI_CATASTO non esiste: si procede con la creazione");
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		try {
			st = conn.createStatement();
			st.execute(SQL_CREATE_APPO_IMMOBILI_CATASTO);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		System.out.println("Terminata creazione tabelle d'appoggio");
	}
	
	private static void elabora(Connection conn, String dirFiles) {
		String fromPath = dirFiles + "totaleNomi.txt";
		if (!new File(fromPath).exists()) {
			return;
		}
		String toPath = dirFiles + "elab.csv";
		
		BufferedReader fileIn = null;
		PrintWriter fileOut = null;
		
		try {
			fileIn = new BufferedReader(new FileReader(fromPath));
			fileOut = new PrintWriter(new File(toPath));
			
			String line = "SOGGETTO|CODICE FISCALE|NUMERO FAMILIARI|CODICI FISCALI FAMILIARI|" +
						"NUMERO IMMOBILI FAMILIARI|DETTAGLIO IMMOBILI FAMILIARI|TOTALE REDDITI FAMILIARI PER ANNO"; 
			fileOut.println(line);
			
			String currentLine = null;
			int conta = 0;
			
			while ((currentLine = fileIn.readLine()) != null) {
				List<String> campi = Arrays.asList(currentLine.split("\\|", -1));
				String denom = campi.get(0).toUpperCase();
				String codFisc = campi.get(1).toUpperCase();
				
				PreparedStatement pst = null;
				ResultSet rs = null;
				int numFam = 0;
				String codFiscsFam = "";
				ArrayList<String> arrCodFiscsFam = new ArrayList<String>();
				
				try {
					pst = conn.prepareStatement(SQL_SELECT_CODFISC_FAMIGLIA);
					pst.setString(1, codFisc);
					rs = pst.executeQuery();					
					while(rs.next()) {
						numFam++;
						String myCodFisc = rs.getString("CF");
						if (!codFiscsFam.equals("")) {
							codFiscsFam += " - ";
						}
						codFiscsFam += myCodFisc;
						arrCodFiscsFam.add(myCodFisc);
					}
				} catch (Exception e) {
					log.error(e);
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (pst != null) {
							pst.close();
						}
					} catch (Exception e) {
						log.error(e);
					}			
				}
				
				int numImmFam = 0;
				try {
					String orClause = "1 = 0";
					if (arrCodFiscsFam.size() > 0) {
						orClause = "(";						
						for (int idx = 0; idx < arrCodFiscsFam.size(); idx++) {
							if (idx > 0) {
								orClause += " OR ";
							}
							orClause += "CUAA = ?";
						}
						orClause += ")";
					}					
					String sql = SQL_COUNT_IMM_FAMIGLIA.replace("@@@", orClause);
					pst = conn.prepareStatement(sql);
					int idx = 0;
					if (arrCodFiscsFam.size() > 0) {
						for (String codFiscFam : arrCodFiscsFam) {
							pst.setString(++idx, codFiscFam);
						}
					}
					rs = pst.executeQuery();
					while(rs.next()) {
						numImmFam = rs.getInt("CONTA");
					}
				} catch (Exception e) {
					log.error(e);
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (pst != null) {
							pst.close();
						}
					} catch (Exception e) {
						log.error(e);
					}			
				}
				
				String dettImmFam = "";
				try {
					String orClause = "1 = 0";					
					if (arrCodFiscsFam.size() > 0) {
						orClause = "(";						
						for (int idx = 0; idx < arrCodFiscsFam.size(); idx++) {
							if (idx > 0) {
								orClause += " OR ";
							}
							orClause += "SC.CUAA = ?";
						}
						orClause += ")";
					}					
					String sql = SQL_SELECT_DETTAGLIO_IMM_FAMIGLIA.replace("@@@", orClause);
					pst = conn.prepareStatement(sql);
					int idx = 0;
					if (arrCodFiscsFam.size() > 0) {
						for (String codFiscFam : arrCodFiscsFam) {
							pst.setString(++idx, codFiscFam);
						}
					}
					rs = pst.executeQuery();
					while(rs.next()) {
						if (!dettImmFam.equals("")) {
							dettImmFam += " ";
						}
						dettImmFam += rs.getString("CATEGORIA");
						dettImmFam += ": ";
						dettImmFam += rs.getInt("CONTA");
						dettImmFam += ";";
					}
				} catch (Exception e) {
					log.error(e);
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (pst != null) {
							pst.close();
						}
					} catch (Exception e) {
						log.error(e);
					}			
				}
				
				String redFam = "";
				try {
					String orClause = "1 = 0";					
					if (arrCodFiscsFam.size() > 0) {
						orClause = "(";						
						for (int idx = 0; idx < arrCodFiscsFam.size(); idx++) {
							if (idx > 0) {
								orClause += " OR ";
							}
							orClause += "RED_REDDITI_DICHIARATI.CODICE_FISCALE_DIC = ?";
						}
						orClause += ")";
					}					
					String sql = SQL_SELECT_REDDITI_FAMIGLIA.replace("@@@", orClause);
					pst = conn.prepareStatement(sql);
					int idx = 0;
					if (arrCodFiscsFam.size() > 0) {
						for (String codFiscFam : arrCodFiscsFam) {
							pst.setString(++idx, codFiscFam);
						}
					}
					rs = pst.executeQuery();
					while(rs.next()) {
						if (!redFam.equals("")) {
							redFam += " ";
						}
						redFam += rs.getString("ANNO_IMPOSTA");
						redFam += ": ";
						redFam += rs.getInt("TOTALE");
						redFam += ";";
					}
				} catch (Exception e) {
					log.error(e);
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (pst != null) {
							pst.close();
						}
					} catch (Exception e) {
						log.error(e);
					}			
				}
				
				//TODO altre elaborazioni
				
				line = denom + "|" + codFisc + "|" + numFam + "|" + codFiscsFam  + 
				"|" + numImmFam + "|" + dettImmFam + "|" + redFam;
				fileOut.println(line);
				
				conta++;
				if (conta % 50 == 0) {
					System.out.println("Elaborati " + conta + " codici fiscali");
				}
			}
			
			System.out.println("Terminata elaborazione dati");
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (fileOut != null) {
				try {
					fileOut.flush();
					fileOut.close();
				} catch (Exception e) {
					log.error(e);
				}				
			}
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			String connUrl = args.length > 3 ? args[0] : STR_CONN_URL_DEFAULT;
			String user = args.length > 3 ? args[1] : STR_CONN_USER_DEFAULT;
			String pwd = args.length > 3 ? args[2] : STR_CONN_PWD_DEFAULT;
			String dirFiles = args.length > 3 ? args[3] : DIR_FILES_DEFAULT;
			conn = DriverManager.getConnection(connUrl, user, pwd);
			CommandAck ack = eseguiVerifica(conn, dirFiles);
			System.out.println(ack.getMessage());
		} catch(Exception e){
			log.error(e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch(Exception e){
				log.error(e);
			}
		}		
	}

}
