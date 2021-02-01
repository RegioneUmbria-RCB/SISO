package it.webred.rulengine.brick.superc.dia;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dia.dataExport.DiaCatDataExporter;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public abstract class AbstractDiagnosticsExport extends Command {

	protected int[] ids;
	protected String[] nomi;
	protected HashMap<String, String[]> elenchiValori;
	
	protected static org.apache.log4j.Logger log = Logger.getLogger(AbstractDiagnosticsExport.class.getName());
	

	public AbstractDiagnosticsExport(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	public int[] getIds() {
		return ids;
	}
	
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	
	public String[] getNomi() {
		return nomi;
	}
	
	public void setNomi(String[] nomi) {
		this.nomi = nomi;
	}	
	
	public HashMap<String, String[]> getElenchiValori() {
		return elenchiValori;
	}

	public void setElenchiValori(HashMap<String, String[]> elenchiValori) {
		this.elenchiValori = elenchiValori;
	}

	public CommandAck run(Context ctx) throws CommandException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		DiaCatDataExporter diaCat = null;
		java.util.Date dtRif = null;
		
		try {
			log.debug("Recupero parametro da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			List<RRuleParamIn> parametriIn = this.getParametersIn(_jrulecfg);
			if (parametriIn != null && parametriIn.size() > 0) {
				dtRif = (Date)ctx.get(((RRuleParamIn)parametriIn.get(0)).getDescr());
			}
			if (parametriIn != null && parametriIn.size() > 1) {
				//in questo caso si esegue una sola diagnostica, e il secondo parametro è il suo id
				nomi = null;
				ids = new int[] {((Integer)ctx.get(((RRuleParamIn)parametriIn.get(1)).getDescr())).intValue()};
			}
			
			if (nomi == null && ids == null) {
				Exception e = new Exception("Non sono stati specificati né i nomi né gli identificativi delle query di estrazione dati");
				log.error("Errore grave in fase di esportazione dati", e);
				return new ErrorAck(e);
			} else {
				if (!verificaNomiIds(conn)) {
					Exception e = new Exception("Secondo la configurazione dell'applicativo, le diagnostiche richieste non sono eseguibili");
					log.error("Impossibile effettuare l'esportazione dati: "+ e.getMessage(),e);
					return new RejectAck(e.getMessage());
				}
			}			
			log.info("ELABORO:");
			if (nomi != null) {
				String strNomi = "";
				for (String nome: nomi) {
					if (!strNomi.equals("")) {
						strNomi += ", ";
					}
					strNomi += nome;
				}
				log.info("NOMI QUERY: " + strNomi);
			}
			if (ids != null) {
				String strIds = "";
				for (int id: ids) {
					if (!strIds.equals("")) {
						strIds += ", ";
					}
					strIds += id;
				}
				log.info("IDENTIFICATIVI QUERY: " + strIds);
			}
			
			if (nomi != null) {
				for (String nome: nomi) {
					sql = "SELECT * FROM DIA_CATALOGO WHERE NOME = ? ORDER BY IDCATALOGODIA";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, nome);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						int id = rs.getInt("IDCATALOGODIA");
						String sqlCommandProp = rs.getString("SQLCOMMANDPROP");
						if (verificaPropConfig(conn, id, sqlCommandProp)) {
							diaCat = new DiaCatDataExporter(id, dtRif, true, false); //ultimo true per fare zip in ogni caso
							String[] elencoValori = leggiElencoValori(id);
							if (elencoValori != null) {
								diaCat.setElencoValori(elencoValori);
							}							
							diaCat.export(conn);
							scriviLog(conn, diaCat);
						}						
					}
				}
			}
			if (ids != null) {
				for (int id: ids) {
					sql = "SELECT * FROM DIA_CATALOGO WHERE IDCATALOGODIA = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						String sqlCommandProp = rs.getString("SQLCOMMANDPROP");
						if (verificaPropConfig(conn, id, sqlCommandProp)) {
							diaCat = new DiaCatDataExporter(id, dtRif, true, false); //ultimo true per fare zip in ogni caso
							String[] elencoValori = leggiElencoValori(id);
							if (elencoValori != null) {
								diaCat.setElencoValori(elencoValori);
							}							
							diaCat.export(conn);
							scriviLog(conn, diaCat);
						}						
					}
				}
			}
		} catch (Exception e) {
			log.error("Errore grave in fase di esportazione dati", e);
			return new ErrorAck(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				log.error(e);
			}			
		}
		
		String msg = "Esportazione dati terminata correttamente";
		return new ApplicationAck(msg);
		
	}
	
	private String[] leggiElencoValori(int id) {
		if (elenchiValori == null)
			return null;
		return elenchiValori.get("" + id);
	}
	
	private void scriviLog(Connection conn, DiaCatDataExporter diaCat) throws Exception {
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO DIA_LOG_ESEC VALUES (?, ?, ?, ?, ?)";
		try {
			HashMap<String, Object> datiDiaLogExec = diaCat.getDatiDiaLogExec();
			//verifica se è stato effettuato lo zip
			if (diaCat.getZipFilePath() != null && !diaCat.getZipFilePath().trim().equals("")) {
				datiDiaLogExec.put(DiaCatDataExporter.PATH_FILE, diaCat.getZipFilePath().trim());
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, diaCat.getIdCatalogoDia());
			pstmt.setTimestamp(2, new java.sql.Timestamp(((java.util.Date)datiDiaLogExec.get(DiaCatDataExporter.DATA_ESEC)).getTime()));
			pstmt.setInt(3, ((Integer)datiDiaLogExec.get(DiaCatDataExporter.NUM_REC)).intValue());
			pstmt.setString(4, (String)datiDiaLogExec.get(DiaCatDataExporter.PATH_FILE));
			pstmt.setDate(5, new java.sql.Date(((java.util.Date)datiDiaLogExec.get(DiaCatDataExporter.DATA_RIF)).getTime()));
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
		}
	}

	private boolean verificaNomiIds(Connection conn) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			if (nomi != null) {
				for (String nome: nomi) {
					sql = "SELECT * FROM DIA_CATALOGO WHERE NOME = ? ORDER BY IDCATALOGODIA";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, nome);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						int id = rs.getInt("IDCATALOGODIA");
						String sqlCommandProp = rs.getString("SQLCOMMANDPROP");
						if (verificaPropConfig(conn, id, sqlCommandProp)) {
							return true;
						}
					}
				}
			}
			if (ids != null) {
				for (int id: ids) {
					sql = "SELECT * FROM DIA_CATALOGO WHERE IDCATALOGODIA = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);
					rs = pstmt.executeQuery();
					if (rs.next()) {
						String sqlCommandProp = rs.getString("SQLCOMMANDPROP");
						if (verificaPropConfig(conn, id, sqlCommandProp)) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
	}
	
	private boolean verificaPropConfig(Connection conn, int idCatalogoDia, String sqlCommandProp) throws Exception {
		String codEnte = "";
		String nomeProp = "" + idCatalogoDia;
		if (sqlCommandProp != null && sqlCommandProp.trim().startsWith("PROPERTY@") && sqlCommandProp.trim().length() > "PROPERTY@".length()) {
			nomeProp = sqlCommandProp.trim().substring(sqlCommandProp.trim().indexOf("PROPERTY@") + "PROPERTY@".length());
		}
		ResultSet rsEnte = conn.prepareStatement("select codent from sit_ente").executeQuery();
		while (rsEnte.next()) {
			codEnte = rsEnte.getString("codent");
		}
		boolean flDisabilita = false;
		String disabilita = getDiaProperty(codEnte.toUpperCase() + ".dia.disabilita");
		if (disabilita != null && !disabilita.equals("")) {
			String[] disabilitate = disabilita.split(",");
			for (String disabilitata : disabilitate) {
				if (disabilitata.equals(nomeProp)) {
					flDisabilita = true;
					break;
				}
			}
		}
		boolean flEsclusiva = false;
		boolean flEsclusivaEnte = false;
		String esclusiva = getDiaProperty("dia." + nomeProp + ".esclusiva");		
		if (esclusiva != null && !esclusiva.equals("")) {
			flEsclusiva = true;
			String[] esclusiveEnte = esclusiva.split(",");
			for (String esclusivaEnte : esclusiveEnte) {
				if (esclusivaEnte.equalsIgnoreCase(codEnte)) {
					flEsclusivaEnte = true;
					break;
				}
			}
		}			
		return ((!flDisabilita && (!flEsclusiva || flEsclusivaEnte)) ||
				(flDisabilita && flEsclusiva && flEsclusivaEnte));
	}
	
	private String getDiaProperty(String propName) {
        String fileName =  "dia.properties";
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream(fileName);
        Properties props = new Properties();        
        try {
        	props.load(is);
        } catch (Exception e) {
			return null;
		}        
        String p = props.getProperty(propName);
        return p;
	}
	
}

