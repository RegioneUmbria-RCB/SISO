package it.webred.AMProfiler.servlet;

import it.webred.AMProfiler.beans.AmApplication;
import it.webred.AMProfiler.beans.AmComune;
import it.webred.AMProfiler.beans.AmGroup;
import it.webred.AMProfiler.beans.AmItem;
import it.webred.AMProfiler.beans.AmItemRole;
import it.webred.AMProfiler.beans.AmPermission;
import it.webred.AMProfiler.beans.AmRole;
import it.webred.AMProfiler.beans.AmUser;
import it.webred.AMProfiler.beans.CheckBox;
import it.webred.AMProfiler.beans.CodificaPermessi;
import it.webred.AMProfiler.exception.AMException;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.permessi.AuthContext;
import it.webred.permessi.GestionePermessi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Classe contenente la i metodi comuni per le altre classi del package
 * 
 * @author Petracca Marco
 * @revision Nicola Campanelli
 * @version $Revision: 1.6.2.1 $ $Date: 2010/09/07 15:12:11 $
 */
public class BaseAction {

	@PersistenceContext(unitName = "AmProfilerDataModel")
	protected EntityManager manager;
	protected static Logger logger = Logger.getLogger("am_log");


	/**
	 * Apre una connessione al db jdbc/caronteClient
	 * 
	 * @return La connessione
	 * @throws Exception
	 */
	public static Connection apriConnessione() throws Exception {
		Context initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup("java:/AMProfiler");
		// Context envContext = (Context) initContext.lookup("java:/comp/env");
		// DataSource ds = (DataSource) envContext.lookup("jdbc/AMProfiler");
		Connection conn = ds.getConnection();
		return conn;
	}

	public static void chiudiConnessione(Connection con, Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void rollback(Connection con) throws SQLException {
		if (con != null) {
			con.rollback();
		}
	}

	public static void toErrorPage(HttpServletRequest request,
			HttpServletResponse response, Throwable e) throws ServletException,
			IOException {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(ba));
		request.setAttribute("ErrorMessage", e.getMessage());
		request.setAttribute("ErrorTrace", ba.toString());
		request.getRequestDispatcher("/jsp/error.jsp").forward(request,
				response);
	}

	public static ArrayList<AmComune> listaComuni(String gruppo)
			throws Exception {
		ArrayList<AmComune> lista = new ArrayList<AmComune>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT * FROM AM_COMUNE";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmComune amComune = new AmComune();
				amComune.setBelfiore(rs.getString("BELFIORE"));
				amComune.setDescrizione(rs.getString("DESCRIZIONE"));
				lista.add(amComune);
			}

			if (gruppo != null) {
				rs = st
						.executeQuery("SELECT DISTINCT FK_AM_COMUNE from AM_GROUP where NAME='"
								+ gruppo + "'");
				while (rs.next()) {
					for (AmComune comune : lista) {
						boolean checked = comune.getBelfiore().equals(
								rs.getString("FK_AM_COMUNE"));
						if (checked) {
							comune.setChecked(checked);
							break;
						}
					}
				}
			}

			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<AmComune> listaComuniByUser(String user)
			throws Exception {
		ArrayList<AmComune> lista = new ArrayList<AmComune>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "select distinct ente, descrizione FROM ( " 
		            +"select g.fk_am_comune ente, c.descrizione, ug.fk_am_user FROM AM_GROUP G, AM_USER U, AM_USER_GROUP UG, AM_COMUNE C " 
		            +"WHERE u.name=UG.FK_AM_USER AND G.NAME=UG.FK_AM_GROUP and g.FK_AM_COMUNE = c.BELFIORE "
		            +"union "
		            +"select ua.FK_AM_COMUNE,c.DESCRIZIONE, ua.FK_AM_USER "
		            +"from am_user_air ua, AM_COMUNE c where ua.FK_AM_COMUNE = c.BELFIORE "
		            +") " 
		            +"WHERE FK_AM_USER='" + user + "' ";
			
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmComune amComune = new AmComune();
				amComune.setBelfiore(rs.getString("ENTE"));
				amComune.setDescrizione(rs.getString("DESCRIZIONE"));
				lista.add(amComune);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}
	
	public static String getBelfioreByDescr(String descr)
			throws Exception {
		String belfiore = null;
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT belfiore FROM AM_COMUNE WHERE descrizione ='" + descr + "'";
			
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				belfiore = rs.getString("belfiore");
			}
			rs.close();
			st.cancel();
			return belfiore;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<AmApplication> listaApplication() throws Exception {
		ArrayList<AmApplication> lista = new ArrayList<AmApplication>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT a.*, c.FK_AM_COMUNE, i.URL "
					+ "FROM am_application a, am_instance i, am_instance_comune c "
					+ "WHERE i.fk_am_application (+)LIKE a.NAME AND c.FK_AM_INSTANCE (+)LIKE i.NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmApplication amApp = new AmApplication();
				amApp.setName(rs.getString("NAME"));
				amApp.setUrl(rs.getString("URL"));
				amApp.getComune().setBelfiore(rs.getString("FK_AM_COMUNE"));
				amApp.setTipo_app(rs.getString("APP_TYPE"));
				amApp.setCat_app(rs.getString("APP_CATEGORY"));
				lista.add(amApp);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}

	}

	public static AmGroup gruppoByNome(String nome)
			throws Exception {

		AmGroup amGroup = null;
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT NAME, FK_AM_COMUNE, PERM_RANGE_IP, PERM_ORA_DA, PERM_ORA_A " + "FROM am_group "
					+ "WHERE NAME = '" + nome + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				amGroup = new AmGroup();
				amGroup.setName(rs.getString("NAME"));
				amGroup.setFkAmComune(rs.getString("FK_AM_COMUNE"));
				amGroup.setPermRangeIp(rs.getString("PERM_RANGE_IP"));
				amGroup.setPermOraDa(rs.getString("PERM_ORA_DA"));
				amGroup.setPermOraA(rs.getString("PERM_ORA_A"));
			}

			rs.close();
			st.cancel();
			return amGroup;
		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	public static ArrayList<AmGroup> listaGruppi(String application, String item)
			throws Exception {
		ArrayList<AmGroup> lista = new ArrayList<AmGroup>();

		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT DISTINCT ag.NAME " + "FROM am_group ag, "
					+ "am_instance ai, " + "am_instance_comune aic "
					+ "WHERE aic.FK_AM_COMUNE = ag.FK_AM_COMUNE "
					+ "AND aic.FK_AM_INSTANCE = ai.NAME "
					+ "AND ai.FK_AM_APPLICATION = '" + application + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmGroup amGroup = new AmGroup();
				amGroup.setName(rs.getString("NAME"));
				lista.add(amGroup);
			}

			rs.close();
			st.cancel();
			return lista;
		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}

	public static ArrayList<AmGroup> listaTuttiGruppi(String userName)
			throws Exception {
		ArrayList<AmGroup> lista = new ArrayList<AmGroup>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT * FROM AM_GROUP, AM_COMUNE WHERE FK_AM_COMUNE = BELFIORE ORDER BY NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmGroup amGroup = new AmGroup();
				amGroup.setName(rs.getString("NAME"));
				amGroup.setFkAmComune(rs.getString("FK_AM_COMUNE"));
				amGroup.setDescrComune(rs.getString("DESCRIZIONE"));
				lista.add(amGroup);
			}

			if (userName != null) {
				rs = st
						.executeQuery("SELECT DISTINCT FK_AM_GROUP from AM_USER_GROUP where FK_AM_USER='"
								+ userName + "'");
				while (rs.next()) {
					for (AmGroup gruppo : lista) {
						boolean checked = gruppo.getName().equals(
								rs.getString("FK_AM_GROUP"));
						if (checked) {
							gruppo.setChecked(checked);
							break;
						}
					}
				}
			}

			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}
	
	public static ArrayList<AmGroup> listaGruppiByUser(String userName)
			throws Exception {
		ArrayList<AmGroup> lista = new ArrayList<AmGroup>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT * FROM AM_GROUP, AM_COMUNE WHERE FK_AM_COMUNE = BELFIORE " +
					" AND BELFIORE IN (" +
					" select ua.fk_am_comune from am_user_air ua where ua.fk_am_user = '"+ userName +"' " +
					" union " +
					" select g.FK_AM_COMUNE from am_group g, am_user_group ug where" +
					" ug.FK_AM_GROUP = g.NAME and ug.FK_AM_USER = '"+ userName +"')" +
					" ORDER BY NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmGroup amGroup = new AmGroup();
				amGroup.setName(rs.getString("NAME"));
				amGroup.setFkAmComune(rs.getString("FK_AM_COMUNE"));
				amGroup.setDescrComune(rs.getString("DESCRIZIONE"));
				lista.add(amGroup);
			}

			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public static ArrayList<AmGroup> listaGruppiPerEnte(String ente)
			throws Exception {
		ArrayList<AmGroup> lista = new ArrayList<AmGroup>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT * FROM AM_GROUP WHERE FK_AM_COMUNE = '" + ente
					+ "' ORDER BY NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmGroup amGroup = new AmGroup();
				amGroup.setName(rs.getString("NAME"));
				amGroup.setFkAmComune(rs.getString("FK_AM_COMUNE"));
				lista.add(amGroup);
			}

			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public static ArrayList<AmGroup> listaGruppiPerEnteApplication(String ente,
			String application) throws Exception {
		ArrayList<AmGroup> lista = new ArrayList<AmGroup>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT DISTINCT ag.NAME, ag.FK_AM_COMUNE "
					+ "FROM am_group ag, "
					+ "am_instance ai, "
					+ "am_instance_comune aic "
					+ "WHERE aic.FK_AM_COMUNE = ag.FK_AM_COMUNE "
					+ "AND aic.FK_AM_INSTANCE = ai.NAME "
					+ (ente != null && !ente.equals("null") ? ("AND ag.FK_AM_COMUNE = '"
							+ ente + "'")
							: "") + "AND ai.FK_AM_APPLICATION = '"
					+ application + "' ORDER BY ag.NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmGroup amGroup = new AmGroup();
				amGroup.setName(rs.getString("NAME"));
				amGroup.setFkAmComune(rs.getString("FK_AM_COMUNE"));
				lista.add(amGroup);
			}

			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public static ArrayList<AmUser> listaUsers() throws Exception {
		ArrayList<AmUser> lista = new ArrayList<AmUser>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "select name, NVL(DISABLE_CAUSE,'?') as DISABLE_CAUSE, TO_CHAR(DT_UPD_PWD, 'YYYYMMDD') AS DT_UPD_PWD FROM AM_USER ORDER BY NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmUser amUser = new AmUser();
				amUser.setName(rs.getString("NAME"));
				amUser.setDISABLE_CAUSE(rs.getString("DISABLE_CAUSE"));
				amUser.setDt_upd_pwd(rs.getString("DT_UPD_PWD"));
				// password
				// amUser.setPwd(rs.getString("PWD"));
				lista.add(amUser);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmUser> listaUsersPerGroup(String gruppo)
			throws Exception {
		ArrayList<AmUser> lista = new ArrayList<AmUser>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "select DISTINCT "
					+ "NAME, "
					+ "NVL(DISABLE_CAUSE,'?') as DISABLE_CAUSE, "
					+ "TO_CHAR(DT_UPD_PWD, 'YYYYMMDD') AS DT_UPD_PWD "
					+ "FROM AM_USER u "
					+ "LEFT JOIN AM_USER_GROUP ug on U.NAME=UG.FK_AM_USER "
					+ (StringUtils.isNotEmpty(gruppo) ? "WHERE UG.FK_AM_GROUP='"
							+ gruppo + "'"
							: "") + "ORDER BY NAME";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmUser amUser = new AmUser();
				amUser.setName(rs.getString("NAME"));
				amUser.setDISABLE_CAUSE(rs.getString("DISABLE_CAUSE"));
				amUser.setDt_upd_pwd(rs.getString("DT_UPD_PWD"));
				// password
				// amUser.setPwd(rs.getString("PWD"));
				lista.add(amUser);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmUser> listaUsers(String group) throws Exception {
		ArrayList<AmUser> lista = new ArrayList<AmUser>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "select name, NVL(DISABLE_CAUSE,'?') as DISABLE_CAUSE, TO_CHAR(DT_UPD_PWD, 'YYYYMMDD') AS DT_UPD_PWD FROM AM_USER ORDER BY NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmUser amUser = new AmUser();
				amUser.setName(rs.getString("NAME"));
				amUser.setDISABLE_CAUSE(rs.getString("DISABLE_CAUSE"));
				amUser.setDt_upd_pwd(rs.getString("DT_UPD_PWD"));
				// password
				// amUser.setPwd(rs.getString("PWD"));
				lista.add(amUser);
			}

			if (group != null) {
				for (AmUser amUser : lista) {
					rs = st
							.executeQuery("SELECT FK_AM_GROUP from AM_USER_GROUP where FK_AM_USER='"
									+ amUser.getName()
									+ "' AND FK_AM_GROUP='"
									+ group + "'");
					if (rs.next())
						amUser.setChecked(true);
				}
			}

			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}
	
	public static boolean checkUserVisible(String loggedUser, String userToCheck) throws Exception {
		
		/**Controllo se l'utente loggato puo visualizzare e agire sull'utente specificato
		 * visualizzo l'utente solo se questo non è stato ancora assegnato a nessun ente 
		 * o se ha permessi o gruppi uguali all'utente loggato */
		
		Connection con = null;
		Statement st = null;
		boolean visible = false;
		
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "select case when (select fk_am_user from am_user_air where fk_am_user = '"+userToCheck+"' " +
							"union select fk_am_user from am_user_group where fk_am_user = '"+userToCheck+"') is null then 'TRUE' " +
							"when(select count(belfiore) from " +
							"( " +
							"select ua.FK_AM_COMUNE belfiore from am_user_air ua where ua.fk_am_user = '"+userToCheck+"' " +
							"union " +
							"select g.FK_AM_COMUNE from am_group g, am_user_group ug where ug.FK_AM_GROUP = g.NAME and ug.FK_AM_USER = '"+userToCheck+"' " +
							") " +
							"where belfiore in " +
							"( " +
							"select ua.FK_AM_COMUNE belfiore from am_user_air ua where ua.fk_am_user = '"+loggedUser+"' " +
							"union " +
							"select g.FK_AM_COMUNE from am_group g, am_user_group ug where ug.FK_AM_GROUP = g.NAME and ug.FK_AM_USER = '"+loggedUser+"'  " +
							")) > 0 then 'TRUE' else 'FALSE' end as visible from dual";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				if(rs.getString("VISIBLE").equals("TRUE"))
					visible = true;
			}

			rs.close();
			st.cancel();
			return visible;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmItem> listaItemForApp(String application)
			throws Exception {
		ArrayList<AmItem> lista = new ArrayList<AmItem>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT * FROM AM_APPLICATION_ITEM where FK_AM_APPLICATION = '"
					+ application + "'";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmItem amItem = new AmItem();
				amItem.setName(rs.getString("FK_AM_ITEM"));
				lista.add(amItem);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmItemRole> listaItemRolePerItem(
			String application, String item) throws Exception {
		ArrayList<AmItemRole> lista = new ArrayList<AmItemRole>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT air.* FROM AM_AI_ROLE air , AM_APPLICATION_ITEM ai "
					+ "WHERE air.FK_AM_APPLICATION_ITEM=ai.id "
					+ "AND ai.FK_AM_ITEM ='"
					+ item
					+ "' "
					+ "AND ai.FK_AM_APPLICATION='" + application + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmItemRole amItemRole = new AmItemRole();
				amItemRole.setId(rs.getLong("ID"));
				amItemRole.setAmRoleS(rs.getString("FK_AM_ROLE"));
				amItemRole
						.setAmAppItemS(rs.getString("FK_AM_APPLICATION_ITEM"));
				lista.add(amItemRole);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmItemRole> listaItemRolePerApp(String application)
			throws Exception {
		ArrayList<AmItemRole> lista = new ArrayList<AmItemRole>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT distinct air.* "
					+ "FROM AM_AI_ROLE air "
					+ "LEFT JOIN AM_APPLICATION_ITEM ai on AIR.FK_AM_APPLICATION_ITEM=AI.ID "
					+ "LEFT JOIN AM_GROUP_AIR gair on AIR.ID=GAIR.FK_AM_AI_ROLE "
					+ "LEFT JOIN AM_USER_AIR ui on air.ID=UI.FK_AM_AI_ROLE "
					+ "WHERE air.FK_AM_APPLICATION_ITEM=ai.id "
					+ "AND ai.FK_AM_APPLICATION='"
					+ application
					+ "'"
					+ "AND AIR.ID NOT IN ( select FK_AM_AI_ROLE FROM AM_GROUP_AIR) "
					+ "AND AIR.ID NOT IN ( select FK_AM_AI_ROLE FROM AM_USER_AIR)";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmItemRole amItemRole = new AmItemRole();
				amItemRole.setId(rs.getLong("ID"));
				amItemRole.setAmRoleS(rs.getString("FK_AM_ROLE"));
				amItemRole
						.setAmAppItemS(rs.getString("FK_AM_APPLICATION_ITEM"));
				lista.add(amItemRole);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmItemRole> listaItemRolePerItemUserGroup(
			String application, String item, String ente, String group,
			String user) throws Exception {
		ArrayList<AmItemRole> lista = new ArrayList<AmItemRole>();
		Connection con = null;
		Statement st = null;
		if ("null".equals(ente))
			ente = null;
		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT * FROM (SELECT distinct "
					+ "air.id, AIR.FK_AM_APPLICATION_ITEM, AIR.FK_AM_ROLE, UAIR.FK_AM_USER, NULL AS FK_AM_GROUP, "
					+ "(SELECT FK_AM_COMUNE FROM AM_USER_AIR"
					+ " WHERE FK_AM_AI_ROLE = air.ID" + " AND FK_AM_USER = '"
					+ user
					+ "'"
					+ " AND FK_AM_COMUNE = '"
					+ ente
					+ "') as FK_AM_COMUNE "
					+ "FROM AM_AI_ROLE air "
					+ "LEFT JOIN AM_APPLICATION_item ai "
					+ "ON AIR.FK_AM_APPLICATION_ITEM=AI.ID "
					+ "LEFT JOIN AM_USER_AIR uair "
					+ "ON ( air.ID=uair.FK_AM_AI_ROLE "
					+ "AND uair.FK_AM_USER ='"
					+ user
					+ "') "
					+ "WHERE air.FK_AM_APPLICATION_ITEM=ai.ID "
					+ "and ai.FK_AM_ITEM = '"
					+ item
					+ "' "
					+ "and ai.FK_AM_APPLICATION='"
					+ application
					+ "' "
					+ "UNION "
					+ "SELECT "
					+ "air.id, AIR.FK_AM_APPLICATION_ITEM, AIR.FK_AM_ROLE, ug.FK_AM_USER, UG.FK_AM_GROUP, GR.FK_AM_COMUNE "
					+ "FROM AM_AI_ROLE air "
					+ "LEFT JOIN AM_APPLICATION_item ai "
					+ "ON AIR.FK_AM_APPLICATION_ITEM=AI.ID "
					+ "JOIN AM_GROUP_AIR "
					+ "ON air.id=AM_GROUP_AIR.FK_AM_AI_ROLE "
					+ "LEFT JOIN AM_GROUP gr "
					+ "ON gr.NAME=AM_GROUP_AIR.FK_AM_GROUP "
					+ "LEFT JOIN AM_USER_GROUP ug "
					+ "ON ( ug.FK_AM_GROUP=gr.NAME "
					+ "AND ug.FK_AM_USER ='"
					+ user
					+ "') "
					+ "WHERE air.FK_AM_APPLICATION_ITEM=ai.ID "
					+ "and ai.FK_AM_ITEM = '"
					+ item
					+ "' "
					+ "and ai.FK_AM_APPLICATION='" + application + "' ) ORDER BY FK_AM_ROLE";

			ResultSet rs = st.executeQuery(sql);
			List<String> listaControlloRuoli = new ArrayList<String>();
			while (rs.next()) {

				AmItemRole newItemRole = new AmItemRole();
				newItemRole.setId(rs.getLong("ID"));
				newItemRole.setAmAppItemS(rs
						.getString("FK_AM_APPLICATION_ITEM"));
				newItemRole.setAmRoleS(rs.getString("FK_AM_ROLE"));
				if (rs.getString("FK_AM_USER") != null) {

					if (ente == null)
						newItemRole.setChecked(true);
					else if (ente.equals(rs.getString("FK_AM_COMUNE")))
						newItemRole.setChecked(true);

				}
				if (rs.getString("FK_AM_GROUP") != null
						&& (ente == null || ente.equals(rs.getString("FK_AM_COMUNE")))) {
					// aggiungi un * per elementi gruppati
					newItemRole.setAmRoleS(newItemRole.getAmRoleS() + "(*)");
					newItemRole.setGrouped(true);
				}

				if (!listaControlloRuoli.contains(newItemRole.getAmRoleS())) {
					lista.add(newItemRole);
					listaControlloRuoli.add(newItemRole.getAmRoleS());
				}
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmItemRole> listaItemRolePerItemGroup(
			String application, String item, String group) throws Exception {
		ArrayList<AmItemRole> lista = new ArrayList<AmItemRole>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT "
					+ "air.id, AIR.FK_AM_APPLICATION_ITEM, AIR.FK_AM_ROLE, GAIR.FK_AM_GROUP "
					+ "FROM AM_AI_ROLE air "
					+ "LEFT JOIN AM_APPLICATION_item ai "
					+ "ON AIR.FK_AM_APPLICATION_ITEM=AI.ID "
					+ "LEFT JOIN AM_GROUP_AIR gair "
					+ "ON ( air.id=gair.FK_AM_AI_ROLE "
					+ "and gair.FK_AM_GROUP ='" + group + "') " + "WHERE "
					+ "ai.FK_AM_ITEM = '" + item + "' "
					+ "and ai.FK_AM_APPLICATION='" + application + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// ID|FK_AM_ROLE|FK_AM_ITEM|FK_AM_USER|FK_AM_ITEM_ROLE
				AmItemRole amItemRole = new AmItemRole();
				amItemRole.setId(rs.getLong("ID"));
				amItemRole
						.setAmAppItemS(rs.getString("FK_AM_APPLICATION_ITEM"));
				amItemRole.setAmRoleS(rs.getString("FK_AM_ROLE"));
				if (rs.getString("FK_AM_GROUP") != null) {
					amItemRole.setChecked(true);
					amItemRole.setGrouped(true);
				}
				lista.add(amItemRole);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmRole> getlistaRole(String application, String item)
			throws Exception {
		ArrayList<AmRole> lista = new ArrayList<AmRole>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT role.*, CASE " + "WHEN ( " +

			"SELECT airole.ID FROM am_application_item ai, am_ai_role airole "
					+ "WHERE ai.FK_AM_APPLICATION = '" + application + "' "
					+ "AND ai.FK_AM_ITEM = '" + item + "' "
					+ "AND ai.ID = airole.FK_AM_APPLICATION_ITEM "
					+ "AND airole.FK_AM_ROLE = role.NAME " +

					") IS NOT NULL " + "THEN 'Y' " + "ELSE 'N' "
					+ "END AS PRESENTE " + "FROM am_role role";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmRole amRole = new AmRole();
				amRole.setName(rs.getString("NAME"));
				String presente = rs.getString("PRESENTE");
				if (presente.equals("Y"))
					amRole.setChecked(true);
				else
					amRole.setChecked(false);
				lista.add(amRole);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmPermission> listaPermissionForItem(String item)
			throws Exception {
		ArrayList<AmPermission> lista = new ArrayList<AmPermission>();
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT * FROM AM_PERMISSION where FK_AM_item = '"
					+ item + "' order by NAME";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmPermission amPerm = new AmPermission();
				amPerm.setPermission(rs.getString("NAME"));
				lista.add(amPerm);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static ArrayList<AmUser> listaUsersForRule(String application,
			String item, String ruolo, String ente) throws Exception {
		ArrayList<AmUser> lista = new ArrayList<AmUser>();
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "select distinct uair.FK_AM_USER, air.FK_AM_ROLE "
					+ "from AM_USER_AIR uair LEFT JOIN AM_AI_ROLE air ON UAIR.FK_AM_AI_ROLE=air.ID "
					+ "where air.ID='" + ruolo + "' " 
					+ "AND FK_AM_COMUNE = '"+ente+"' "
					+ "UNION "
					+ "select distinct ug.FK_AM_USER, air.FK_AM_ROLE "
					+ "   from AM_GROUP_AIR gair "
					+ "   LEFT JOIN AM_AI_ROLE air on gair.FK_AM_AI_ROLE=air.ID "
					+ "   JOIN AM_GROUP gr ON gair.FK_AM_GROUP=gr.NAME "
					+ "   JOIN AM_USER_GROUP ug ON ug.FK_AM_GROUP=gr.NAME "
					+ "   where air.ID='" + ruolo + "' "
					+ "   ORDER BY FK_AM_USER";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				AmUser amUser = new AmUser();
				amUser.setName(rs.getString("FK_AM_USER"));

				lista.add(amUser);
			}
			rs.close();
			st.cancel();
			return lista;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static String getPerameter(HttpServletRequest request, String nome) {
		String val = null;
		if (StringUtils.isNotEmpty(request.getParameter(nome)))
			return request.getParameter(nome);
		return val;
	}

	public static LinkedHashMap<String, Boolean> getPermessiRuoliMap(
			String application, String item) throws Exception {

		LinkedHashMap<String, Boolean> mappa = new LinkedHashMap<String, Boolean>();
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT air.FK_AM_ROLE ROLE, air.ID||'@-@'||p.NAME KEY "
					+ "FROM AM_AI_ROLE air, AM_PERMISSION p, AM_APPLICATION_ITEM ai "
					+ "WHERE (p.FK_AM_ITEM = ai.FK_AM_ITEM "
					+ "and ai.ID=air.FK_AM_APPLICATION_ITEM "
					+ "and p.FK_AM_item = '"
					+ item
					+ "' "
					+ "and ai.FK_AM_APPLICATION= '"
					+ application
					+ "' ) "
					+ "order by ROLE, KEY";
			ResultSet rs = st.executeQuery(sql);

			String ruolo = "";
			while (rs.next()) {

				String ruolo2 = rs.getString("ROLE");
				if (ruolo.equals("") || !ruolo.equals(ruolo2)) {
					ruolo = ruolo2;
					mappa.put(ruolo, false);
				}

				String key = rs.getString("KEY").replace(" ", "_");
				mappa.put(key, false);
			}
			rs.close();
			st.cancel();

			sql = "SELECT air.FK_AM_ROLE, air.ID||'@-@'||pair.FK_AM_PERMISSION KEY "
					+ "FROM AM_AI_ROLE air, AM_PERMISSION_AIR pair, AM_APPLICATION_ITEM ai "
					+ "WHERE air.ID=PAIR.FK_AM_AI_ROLE "
					+ "and air.FK_AM_APPLICATION_ITEM=ai.ID "
					+ "and  ai.FK_AM_ITEM='"
					+ item
					+ "' "
					+ "and ai.FK_AM_APPLICATION= '" + application + "'";

			rs = st.executeQuery(sql);
			while (rs.next()) {
				String key = rs.getString("KEY").replace(" ", "_");
				mappa.put(key, true);
			}

			return mappa;
		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static LinkedHashMap<String, CheckBox> getServiziRuoliMap(
			String application) throws Exception {

		LinkedHashMap<String, CheckBox> mappa = new LinkedHashMap<String, CheckBox>();
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			st = con.createStatement();

			String sql = "SELECT distinct AIR.FK_AM_ROLE ROLE, AIR.FK_AM_ROLE||'@-@'||AI.FK_AM_ITEM KEY "
					+ "FROM AM_AI_ROLE air, AM_APPLICATION_ITEM ai "
					+ "WHERE AI.FK_AM_APPLICATION='"
					+ application
					+ "' "
					+ "ORDER BY ROLE";
			ResultSet rs = st.executeQuery(sql);

			String ruolo = "";
			while (rs.next()) {
				String ruolo2 = rs.getString("ROLE");
				if (ruolo.equals("") || !ruolo.equals(ruolo2)) {
					ruolo = ruolo2;
					mappa.put(ruolo, new CheckBox(false, false));
				}

				String key = rs.getString("KEY").replace(" ", "_");
				mappa.put(key, new CheckBox(false, false));
			}
			rs.close();
			st.cancel();

			sql = "SELECT DISTINCT "
					+ "AIR.FK_AM_ROLE ROLE, "
					+ "GAIR.FK_AM_GROUP,  "
					+ "UI.FK_AM_USER, "
					+ "PAIR.FK_AM_PERMISSION, "
					+ "AIR.FK_AM_ROLE||'@-@'||AI.FK_AM_ITEM KEY "
					+ "FROM AM_AI_ROLE air "
					+ "FULL JOIN AM_APPLICATION_ITEM ai ON air.FK_AM_APPLICATION_ITEM = ai.ID "
					+ "FULL JOIN AM_PERMISSION_AIR pair ON air.ID = pair.FK_AM_AI_ROLE "
					+ "FULL JOIN AM_GROUP_AIR gair ON air.ID = gair.FK_AM_AI_ROLE "
					+ "FULL JOIN AM_USER_AIR ui ON air.ID = ui.FK_AM_AI_ROLE "
					+ "WHERE AI.FK_AM_APPLICATION='" + application + "'";

			rs = st.executeQuery(sql);
			while (rs.next()) {
				String key = rs.getString("KEY").replace(" ", "_");
				String gruppo = rs.getString("FK_AM_GROUP");
				String user = rs.getString("FK_AM_USER");
				String permesso = rs.getString("FK_AM_PERMISSION");

				boolean disabled = StringUtils.isNotEmpty(gruppo)
						|| StringUtils.isNotEmpty(user)
						|| StringUtils.isNotEmpty(gruppo);

				mappa.put(key, new CheckBox(true, disabled));
			}

			return mappa;
		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void SalvaPermessi(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();
			ArrayList<AmItemRole> listaIdperEliminazione = listaItemRolePerItem(
					request.getParameter("application"), request
							.getParameter("item"));
			// Prima di tutto elimino tutti i
			if (listaIdperEliminazione.size() > 0) {
				String sqlDelete =

				"delete from AM_PERMISSION_AIR WHERE FK_AM_AI_ROLE IN (  ";
				String id = "";
				for (int i = 0; i < listaIdperEliminazione.size(); i++) {
					id += "'"
							+ ((AmItemRole) listaIdperEliminazione.get(i))
									.getId() + "',";
				}

				id = id.substring(0, id.lastIndexOf(","));
				sqlDelete += id + ")";
				st.executeUpdate(sqlDelete);
			}

			// System.out.println("---------- PARAMETRI --------------"+parameters.keySet().size());

			String queryUp = "INSERT INTO AM_PERMISSION_AIR (FK_AM_AI_ROLE, FK_AM_PERMISSION) VALUES (?,?)";
			PreparedStatement prepStatement = con.prepareStatement(queryUp);

			Map parameters = request.getParameterMap();
			for (Object key : parameters.keySet()) {
				int indexOf = key.toString().indexOf("@-@");
				if (indexOf != -1) {
					prepStatement.clearParameters();
					String value = key.toString().replace("_", " ");

					String ruolo = value.substring(0, indexOf);
					String permesso = value.substring(indexOf + 3);

					int paramIndex = 0;
					prepStatement.setString(++paramIndex, ruolo);
					prepStatement.setString(++paramIndex, permesso);
					prepStatement.executeUpdate();
				}

			}

			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void SalvaServiziRuoli(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);
			st = con.createStatement();

			// Prima di tutto elimino tutte le associazioni relative al singolo
			// servizio
			ArrayList<AmItemRole> listaIdperEliminazione = listaItemRolePerApp(request
					.getParameter("application"));
			if (listaIdperEliminazione.size() > 0) {
				String sqlDelete = "DELETE FROM AM_AI_ROLE WHERE ID IN (  ";
				String id = "";
				for (int i = 0; i < listaIdperEliminazione.size(); i++) {
					id += "'"
							+ ((AmItemRole) listaIdperEliminazione.get(i))
									.getId() + "',";
				}

				id = id.substring(0, id.lastIndexOf(","));
				sqlDelete += id + ")";
				st.executeUpdate(sqlDelete);
			}

			String queryUp = "INSERT INTO AM_AI_ROLE (ID, FK_AM_ROLE, FK_AM_APPLICATION_ITEM ) VALUES (?,?,?)";
			PreparedStatement prepStatement = con.prepareStatement(queryUp);

			int id = nextID("AM_AI_ROLE");

			Map parameters = request.getParameterMap();
			for (Object key : parameters.keySet()) {
				int indexOf = key.toString().indexOf("@-@");
				if (indexOf != -1) {
					prepStatement.clearParameters();
					String value = key.toString().replace("_", " ");

					String ruolo = value.substring(0, indexOf);
					String servizio = value.substring(indexOf + 3);

					int paramaIndex = 0;
					prepStatement.setInt(++paramaIndex, ++id);
					prepStatement.setString(++paramaIndex, ruolo);
					prepStatement.setString(++paramaIndex,
							AppItemName2Id(servizio));
					prepStatement.executeUpdate();
				}
			}

			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void SalvaRuoliUtente(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();
			String user = request.getParameter("utentiXgruppo");
			String application = request.getParameter("application");
			String item = request.getParameter("item");
			String ente = request.getParameter("ente");
			if ("null".equals(ente))
				ente = null;

			// Prima di tutto elimino tutti i ruoli che erano assegnati in
			// precedenza
			// all' utenti per il determinato oggetto.
			String sqlDelete = "DELETE from AM_USER_AIR uair "
					+ "where exists "
					+ "(SELECT * FROM AM_USER_AIR, AM_AI_ROLE air, AM_APPLICATION_ITEM ai "
					+ "where air.FK_AM_APPLICATION_ITEM=ai.ID "
					+ "and ai.FK_AM_ITEM='"
					+ item
					+ "' "
					+ "and ai.FK_AM_APPLICATION='"
					+ application
					+ "' "
					+ "and uair.FK_AM_AI_ROLE = air.ID) "
					+ (ente != null ? ("and uair.FK_AM_COMUNE = '" + ente + "' ")
							: "and uair.FK_AM_COMUNE is null ")
					+ "and uair.FK_AM_USER ='" + user + "'";

			st.executeUpdate(sqlDelete);

			String[] ruoliXutente = request.getParameterValues("ruoliXutente");
			if (ruoliXutente != null) {
				String queryUp = "INSERT INTO AM_USER_AIR (FK_AM_USER, FK_AM_AI_ROLE, FK_AM_COMUNE) VALUES (?,?,?)";
				PreparedStatement prepStatement = con.prepareStatement(queryUp);

				for (String val : ruoliXutente) {
					if (StringUtils.isEmpty(val))
						continue;
					if (val.contains("(*)"))
						continue;
					prepStatement.clearParameters();
					int idx = 0;
					prepStatement.setString(++idx, user);
					prepStatement.setString(++idx, RoleName2Id(item, val
							.replace("(*)", "").trim()));
					prepStatement.setString(++idx, ente);
					prepStatement.executeUpdate();
				}

			}
			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void SalvaRuoliGruppo(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();
			String group = request.getParameter("gruppi");
			String application = request.getParameter("application");
			String item = request.getParameter("item");

			// Prima di tutto elimino tutti i ruoli che erano assegnati in
			// precedenza
			// al gruppo per il determinato oggetto.

			String sqlDelete = "DELETE from AM_GROUP_AIR gair "
					+ "where exists "
					+ "(SELECT * FROM AM_GROUP_AIR, AM_AI_ROLE air, AM_APPLICATION_ITEM ai "
					+ "where air.FK_AM_APPLICATION_ITEM=ai.ID "
					+ "and ai.FK_AM_ITEM='" + item + "' "
					+ "and ai.FK_AM_APPLICATION='" + application + "' "
					+ "and gair.FK_AM_AI_ROLE = air.ID) "
					+ "and gair.FK_AM_GROUP ='" + group + "'";

			st.executeUpdate(sqlDelete);

			// System.out.println("---------- rules --------------"+request.getParameterValues("rules"));

			String[] ruoliXgruppo = request.getParameterValues("ruoliXgruppo");
			if (ruoliXgruppo != null) {
				String queryUp = "INSERT INTO AM_GROUP_AIR (FK_AM_GROUP, FK_AM_AI_ROLE) VALUES (?,?)";
				PreparedStatement prepStatement = con.prepareStatement(queryUp);

				for (String val : ruoliXgruppo) {
					if (StringUtils.isEmpty(val))
						continue;

					prepStatement.clearParameters();
					int idx = 0;

					prepStatement.setString(++idx, group);
					prepStatement.setString(++idx, val);
					prepStatement.executeUpdate();
				}

			}
			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void SalvaRuoliItem(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {

			String[] ruoliXitem = request.getParameterValues("ruoliXitem");
			ArrayList<String> ruoliScelti = new ArrayList(Arrays
					.asList(ruoliXitem));
			String item = request.getParameter("item");
			String itemId = AppItemName2Id(item);
			String application = request.getParameter("application");

			ArrayList<AmRole> ruoli = getlistaRole(application, item);

			con = apriConnessione();

			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);
			st = con.createStatement();

			int id = nextID("AM_AI_ROLE");
			for (AmRole ruolo : ruoli) {

				// caso1: già presente
				if (ruolo.isChecked() && ruoliScelti.contains(ruolo.getName()))
					continue;

				// caso2: nella nuova lista non è presente quindi elimino
				if (ruolo.isChecked() && !ruoliScelti.contains(ruolo.getName())) {
					String roleId = RoleName2Id(item, ruolo.getName());

					// cancello su gruppi
					String sqlDelete = "DELETE FROM AM_GROUP_AIR WHERE FK_AM_AI_ROLE = "
							+ roleId;
					st.executeUpdate(sqlDelete);

					// cancello su permessi
					sqlDelete = "DELETE FROM AM_PERMISSION_AIR WHERE FK_AM_AI_ROLE = "
							+ roleId;
					st.executeUpdate(sqlDelete);

					// cancello su user
					sqlDelete = "DELETE FROM AM_USER_AIR WHERE FK_AM_AI_ROLE = "
							+ roleId;
					st.executeUpdate(sqlDelete);

					// cancello su role
					sqlDelete = "DELETE FROM AM_AI_ROLE WHERE FK_AM_APPLICATION_ITEM = "
							+ itemId
							+ " AND FK_AM_ROLE = '"
							+ ruolo.getName()
							+ "'";
					st.executeUpdate(sqlDelete);
				}

				// caso3: nella vecchia lista non è presente quindi aggiungo
				if (!ruolo.isChecked() && ruoliScelti.contains(ruolo.getName())) {
					String queryUp = "INSERT INTO AM_AI_ROLE (ID, FK_AM_ROLE, FK_AM_APPLICATION_ITEM ) VALUES (?,?,?)";
					PreparedStatement prepStatement = con
							.prepareStatement(queryUp);

					// int id = nextID("AM_AI_ROLE");
					prepStatement.setInt(1, ++id);
					System.out.println("ID_AM_AI_ROLE: " + id + " ruolo: "
							+ ruolo.getName() + " itemId: " + itemId);
					prepStatement.setString(2, ruolo.getName());
					prepStatement.setString(3, itemId);
					prepStatement.executeUpdate();
				}
			}

			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void NuovoRuolo(HttpServletRequest request) throws Exception {
		Connection con = null;
		Statement st = null;
		con = apriConnessione();
		if (!GestionePermessi.autorizzato(new AuthContext(request
				.getUserPrincipal(), con, CodificaPermessi.NOME_APP,
				CodificaPermessi.ITEM_MAPPING),
				CodificaPermessi.NUOVO_PERMESSO, true))
			throw new AMException("Permesso: 'Nuovo Ruolo' non concesso");

		chiudiConnessione(con, st);

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			String queryUp = "INSERT INTO AM_ROLE (NAME) VALUES (?)";
			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String ruolo = request.getParameter("nuovoRuolo");
			prepStatement.setString(1, ruolo);

			prepStatement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void NuovoPermesso(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;
		con = apriConnessione();
		if (!GestionePermessi.autorizzato(new AuthContext(request
				.getUserPrincipal(), con, CodificaPermessi.NOME_APP,
				CodificaPermessi.ITEM_MAPPING),
				CodificaPermessi.NUOVO_PERMESSO, true))
			throw new AMException("Permesso: '"
					+ CodificaPermessi.NUOVO_PERMESSO + "' non concesso");

		chiudiConnessione(con, st);
		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			String queryUp = "INSERT INTO AM_PERMISSION (NAME, FK_AM_ITEM) VALUES (?,?)";
			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String permission = request.getParameter("nuovoPermesso");
			String item = request.getParameter("item");
			prepStatement.setString(1, permission);
			prepStatement.setString(2, item);

			prepStatement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void NuovoGruppo(String gruppo, String ente,
			HttpServletRequest request) throws Exception {
		Connection con = null;
		Statement st = null;
		con = apriConnessione();
		if (!GestionePermessi.autorizzato(new AuthContext(request
				.getUserPrincipal(), con, CodificaPermessi.NOME_APP,
				CodificaPermessi.ITEM_MAPPING), CodificaPermessi.NUOVO_GRUPPO,
				true))
			throw new AMException("Permesso: '" + CodificaPermessi.NUOVO_GRUPPO
					+ "' non concesso");

		chiudiConnessione(con, st);

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			String queryUp = "INSERT INTO AM_GROUP (NAME, FK_AM_COMUNE) VALUES (?,?)";
			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			prepStatement.setString(1, gruppo);
			prepStatement.setString(2, ente);

			prepStatement.executeUpdate();
			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void SalvaUtente(HttpServletRequest request) throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			// Prima di tutto elimino tutti i gruppi associati all'utente

			String queryUp = null;
			if ("new".equals(request.getParameter("mode"))) {
				// inserimento
				queryUp = "INSERT INTO AM_USER (NAME, PWD, DT_INS, USER_INS, DT_UPD_PWD, EMAIL) VALUES (?,?,?,?,?,?)";
			} else {
				// modifica password
				queryUp = "UPDATE AM_USER SET PWD = ?, DT_UPD_PWD = ? WHERE NAME = ?";
			}

			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String nome = request.getParameter("userName");

			final MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(request.getParameter("password").getBytes());
			final byte[] digest = md.digest();

			if ("new".equals(request.getParameter("mode"))) {
				// inserimento
				prepStatement.setString(1, nome);
				prepStatement.setString(2, toHexString(digest));
				prepStatement.setDate(3, new Date(Calendar.getInstance()
						.getTimeInMillis()));
				prepStatement
						.setString(4, request.getUserPrincipal().getName());
				Date oggi = new Date(Calendar.getInstance().getTimeInMillis());
				if (request.getParameter("flPwdValida") != null
						&& request.getParameter("flPwdValida")
								.equalsIgnoreCase("on")) {
					prepStatement.setDate(5, oggi);
				} else {
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(oggi);
					gc.add(Calendar.DAY_OF_YEAR, -1-CaricaUtenti.numGiorniVal);
					prepStatement.setDate(5, new Date(gc.getTime().getTime()));
				}
				String email = request.getParameter("email");
				prepStatement.setString(6, email);
			} else {
				// modifica password
				prepStatement.setString(1, toHexString(digest));
				Date oggi = new Date(Calendar.getInstance().getTimeInMillis());
				if (request.getParameter("flPwdValida") != null
						&& request.getParameter("flPwdValida")
								.equalsIgnoreCase("on")) {
					prepStatement.setDate(2, oggi);
				} else {
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(oggi);
					gc.add(Calendar.DAY_OF_YEAR, -1-CaricaUtenti.numGiorniVal);
					prepStatement.setDate(2, new Date(gc.getTime().getTime()));
				}
				prepStatement.setString(3, nome);
			}

			prepStatement.executeUpdate();
			con.commit();

			// salvataggio OK
			request.setAttribute("salvaOk","Aggiornamento dati avvenuto correttamente");
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void UpdateEmail(HttpServletRequest request) throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			String queryUp = "UPDATE AM_USER SET EMAIL = ? WHERE NAME = ?";

			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String nome = request.getParameter("userName");

			String email = request.getParameter("email");
			prepStatement.setString(1, email);
			prepStatement.setString(2, nome);

			prepStatement.executeUpdate();
			con.commit();

			// salvataggio OK
			request.setAttribute("salvaOk","Aggiornamento dati avvenuto correttamente");
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static void UpdatePermessiAccessoUtente(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			String queryUp = "UPDATE AM_USER SET PERM_RANGE_IP = ?, PERM_ORA_DA = ?, PERM_ORA_A = ? WHERE NAME = ?";

			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String nome = request.getParameter("userName");

			String ipFidati = request.getParameter("ipFidati");
			String daMinuto = request.getParameter("daMinuto");
			String daOra = request.getParameter("daOra");
			String aOra = request.getParameter("aOra");
			String aMinuto = request.getParameter("aMinuto");

			prepStatement.setString(1, ipFidati.equals("") ? null : ipFidati);
			if (daOra.equals("") || daMinuto.equals(""))
				prepStatement.setString(2, null);
			else
				prepStatement.setString(2, daOra + ":" + daMinuto);
			if (aOra.equals("") || aMinuto.equals(""))
				prepStatement.setString(3, null);
			else
				prepStatement.setString(3, aOra + ":" + aMinuto);
			prepStatement.setString(4, nome);

			prepStatement.executeUpdate();
			con.commit();

			// salvataggio OK
			request.setAttribute("salvaOk","Aggiornamento dati avvenuto correttamente");
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public static void UpdatePermessiAccessoGruppo(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			String queryUp = "UPDATE AM_GROUP SET PERM_RANGE_IP = ?, PERM_ORA_DA = ?, PERM_ORA_A = ? WHERE NAME = ?";

			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String nome = request.getParameter("gruppo");

			String ipFidati = request.getParameter("ipFidati");
			String daMinuto = request.getParameter("daMinuto");
			String daOra = request.getParameter("daOra");
			String aOra = request.getParameter("aOra");
			String aMinuto = request.getParameter("aMinuto");

			prepStatement.setString(1, ipFidati.equals("") ? null : ipFidati);
			if (daOra.equals("") || daMinuto.equals(""))
				prepStatement.setString(2, null);
			else
				prepStatement.setString(2, daOra + ":" + daMinuto);
			if (aOra.equals("") || aMinuto.equals(""))
				prepStatement.setString(3, null);
			else
				prepStatement.setString(3, aOra + ":" + aMinuto);
			prepStatement.setString(4, nome);

			prepStatement.executeUpdate();
			con.commit();

			// salvataggio OK
			request.setAttribute("salvaOk","Aggiornamento dati avvenuto correttamente");
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	/**
	 * Cancella logicamente un utente
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void CancellaUtente(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;
		con = apriConnessione();
		if (!GestionePermessi.autorizzato(new AuthContext(request
				.getUserPrincipal(), con, CodificaPermessi.NOME_APP,
				CodificaPermessi.ITEM_MAPPING),
				CodificaPermessi.CANCELLA_UTENTE, true))
			throw new AMException("Permesso: "
					+ CodificaPermessi.CANCELLA_UTENTE + " non concesso");

		chiudiConnessione(con, st);

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			boolean ripristina = request.getParameter("ripristina") != null
					&& request.getParameter("ripristina").equals("yes");

			String queryUp = "";
			if (!ripristina) {
				queryUp = "update AM_USER set disable_cause = 'CANCELLED' WHERE NAME = ?";
			} else {
				queryUp = "update AM_USER set disable_cause = NULL WHERE NAME = ?";
			}

			PreparedStatement prepStatement = con.prepareStatement(queryUp);
			String nome = request.getParameter("userName");
			prepStatement.setString(1, nome);

			prepStatement.executeUpdate();
			con.commit();
			if (!ripristina) {
				request.setAttribute("cancOkMsgColor", "red");
				request.setAttribute("cancOk", "L'utente \"" + nome
						+ "\" è stato cancellato!");
			} else {
				request.setAttribute("cancOkMsgColor", "green");
				request.setAttribute("cancOk", "L'utente \"" + nome
						+ "\" è stato ripristinato!");
			}

		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}
	
	/**
	 * Elimina dal db un utente
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void EliminaUtente(HttpServletRequest request)
			throws Exception {
		Connection con = null;
		Statement st = null;
		con = apriConnessione();
		if (!GestionePermessi.autorizzato(new AuthContext(request
				.getUserPrincipal(), con, CodificaPermessi.NOME_APP,
				CodificaPermessi.ITEM_MAPPING),
				CodificaPermessi.CANCELLA_UTENTE, true))
			throw new AMException("Permesso: "
					+ CodificaPermessi.CANCELLA_UTENTE + " non concesso");

		chiudiConnessione(con, st);

		try {
			String nome = request.getParameter("userName");
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);

			st = con.createStatement();

			// cancello su gruppi
			String sqlDelete = "DELETE FROM AM_USER_GROUP WHERE FK_AM_USER = '"+ nome + "'";
			st.executeUpdate(sqlDelete);
			
			// cancello su am_ai_role
			sqlDelete = "DELETE FROM AM_USER_AIR WHERE FK_AM_USER = '"+ nome + "'";
			st.executeUpdate(sqlDelete);
			
			// cancello su anagrafica
			sqlDelete = "DELETE FROM AM_ANAGRAFICA WHERE FK_AM_USER = '"+ nome + "'";
			st.executeUpdate(sqlDelete);

			
			// cancello su ufficio
			sqlDelete = "DELETE FROM AM_USER_UFFICIO WHERE FK_AM_USER = '"+ nome + "'";
			st.executeUpdate(sqlDelete);

			
			// cancello su user
			sqlDelete = "DELETE FROM AM_USER WHERE NAME = '"+ nome + "'";
			st.executeUpdate(sqlDelete);
			con.commit();

			request.setAttribute("cancOkMsgColor", "green");
			request.setAttribute("cancOk", "Utente \"" + nome
					+ "\" eliminato!");

		} catch (Exception e) {
			logger.error("Errore eliminazione utente",e);
			rollback(con);
			throw new Exception("Impossibile cancellare lutente a causa si informazioni collegate");
		} finally {
			chiudiConnessione(con, st);
		}

	}

	/**
	 * Ritorna la stringa in formato esadecimale
	 * 
	 * @param bytes
	 *            I bytes da convertire
	 * @return La stringa esadecimale che rappresenta i bytes di dati
	 * @throws IllegalArgumentException
	 *             Se il byte array è nullo
	 */
	public static String toHexString(byte[] bytes) {
		if (bytes == null)
			throw new IllegalArgumentException("byte array must not be null");
		StringBuffer hex = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			hex.append(Character.forDigit((bytes[i] & 0XF0) >> 4, 16));
			hex.append(Character.forDigit((bytes[i] & 0X0F), 16));
		}
		return hex.toString();
	}

	public static String RoleId2Name(String idItemRole) throws Exception {

		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT FK_AM_ROLE FROM AM_AI_ROLE air  "
					+ "WHERE air.ID='" + idItemRole + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				return rs.getString("FK_AM_ROLE");
			rs.close();
			st.cancel();
			return "";

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static String RoleName2Id(String item, String name) throws Exception {

		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT air.ID FROM AM_AI_ROLE air "
					+ "LEFT JOIN AM_APPLICATION_ITEM ai on AIR.FK_AM_APPLICATION_ITEM=AI.ID "
					+ "WHERE air.FK_AM_ROLE='" + name + "' AND AI.FK_AM_ITEM='"
					+ item + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				return rs.getString("ID");
			rs.close();
			st.cancel();
			return "";

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	public static String AppItemName2Id(String name) throws Exception {

		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT ID FROM AM_APPLICATION_ITEM ai  "
					+ "WHERE ai.FK_AM_ITEM='" + name + "'";

			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				return rs.getString("ID");
			rs.close();
			st.cancel();
			return "";

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}

	}

	protected static int nextID(String table) throws Exception {
		Connection con = null;
		Statement st = null;
		try {
			con = apriConnessione();
			st = con.createStatement();
			String sql = "SELECT MAX(ID) FROM " + table;

			ResultSet rs = st.executeQuery(sql);
			while (rs.next())
				return Integer.parseInt(rs.getString("MAX(ID)"));
			rs.close();
			st.cancel();
			return 1;

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public static void SalvaGruppi(HttpServletRequest request) throws Exception {

		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);
			st = con.createStatement();

			// Prima di tutto elimino tutti gli item relativi a tale user
			String userName = (String) request.getAttribute("uN");
			String sqlDelete = "DELETE FROM AM_USER_GROUP WHERE FK_AM_USER = '"
					+ userName + "'";
			st.executeUpdate(sqlDelete);

			String[] gruppi = (String[]) request.getParameterValues("gruppi");

			if (gruppi != null) {
				String queryUp = "INSERT INTO AM_USER_GROUP (FK_AM_GROUP, FK_AM_USER) VALUES (?,?)";
				PreparedStatement prepStatement = con.prepareStatement(queryUp);

				for (String gruppo : gruppi) {
					prepStatement.clearParameters();

					int idx = 0;
					prepStatement.setString(++idx, gruppo.trim());
					prepStatement.setString(++idx, userName.trim());
					prepStatement.executeUpdate();

				}
			}

			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public static void SalvaUserGruppi(String gruppo, String[] utenti)
			throws Exception {

		Connection con = null;
		Statement st = null;

		try {
			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con
						.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			con.setAutoCommit(false);
			st = con.createStatement();

			// Prima di tutto elimino tutti gli item relativi a tale user
			String sqlDelete = "DELETE FROM AM_USER_GROUP WHERE FK_AM_GROUP = '"
					+ gruppo + "'";
			st.executeUpdate(sqlDelete);

			if (utenti != null) {
				String queryUp = "INSERT INTO AM_USER_GROUP (FK_AM_GROUP, FK_AM_USER) VALUES (?,?)";
				PreparedStatement prepStatement = con.prepareStatement(queryUp);

				for (String utente : utenti) {
					prepStatement.clearParameters();

					int idx = 0;
					prepStatement.setString(++idx, gruppo.trim());
					prepStatement.setString(++idx, utente.trim());
					prepStatement.executeUpdate();

				}
			}

			con.commit();
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, st);
		}
	}

	public void salvaAnagrafica(AmAnagrafica anagrafica) throws Exception {

		try {

			manager.persist(anagrafica);

		} catch (Exception e) {
			logger.error("Errore :",e);

			throw e;
		}

	}
	
	
	
	public static AmUserUfficio fillDatiUfficio(HttpServletRequest request){
	
		AmUserUfficio ana = new AmUserUfficio();
			
		ana.setDirezione(request.getParameter("direzione"));
		ana.setSettore(request.getParameter("settore"));
		ana.setEmail(request.getParameter("emailUfficio"));
		ana.setTelefono(request.getParameter("telUfficio"));
		ana.setFkAmUser(request.getParameter("userName"));

		return ana;
	}
	

	public static void controlloPassword(HttpServletRequest request)
			throws Exception {

		Connection con = null;
		Statement statment = null;

		try {

			Principal user = request.getUserPrincipal();
			con = apriConnessione();
			statment = con.createStatement();
			ResultSet rsc = statment
					.executeQuery("select * from am_user where name = "
							+ user.getName());
			java.util.Date dateUpdate = new java.util.Date();
			while (rsc.next()) {
				dateUpdate = rsc.getDate("dt_upd_pwd");
			}
			java.util.Date dataOggi = new java.util.Date();
			int numMillisecondsVal = CaricaUtenti.numGiorniVal * 24 * 60 * 60
					* 1000;
			java.util.Date dataScad = new java.util.Date();
			dataScad.setTime(dateUpdate.getTime() + numMillisecondsVal);
			if (dataOggi.getTime() > dataScad.getTime()) {
				request.setAttribute("pwdScaduta", "true");
			}
		} catch (Exception e) {
			logger.error("Errore :",e);

			rollback(con);
			throw e;
		} finally {
			chiudiConnessione(con, statment);
		}
	}
	
	public static  boolean  validateDatiAnagrafica(HttpServletRequest request, String userName){
		String cognome = request.getParameter("cognome");
		String nome = request.getParameter("nome");
		String dtNascita = request.getParameter("dtNascita");
		String comNascita = request.getParameter("cmNascita");
		String esteroNascita = request.getParameter("esteroNascita");
		
		boolean stato = true;
		
		if (cognome == null || cognome.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noCog", "true");
			stato = false;
		}
		
		if (nome == null || nome.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noNome", "true");
			stato = false;
		}
		
		if (dtNascita == null || dtNascita.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noDtNascita", "true");
			stato = false;
		}
		
		if ((comNascita == null || comNascita.trim().equals("")) && (esteroNascita==null || esteroNascita.trim().equals(""))) {
			request.setAttribute("uN", userName);
			request.setAttribute("noLuogoNascita", "true");
			stato = false;
		}
		
	
		return stato;
		
	}
	
	public static  boolean  validateDatiUfficio(HttpServletRequest request, String userName){
	
		String direzione = request.getParameter("direzione");
		String settore = request.getParameter("settore");
		String emailUfficio = request.getParameter("emailUfficio");
		String telUfficio = request.getParameter("telUfficio");
		
		boolean stato = true;
		
		if (direzione == null || direzione.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noDirezione", "true");
			stato = false;
		}
		
		if (settore == null || settore.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noSettore", "true");
			stato = false;
		}
		
		if (emailUfficio == null || emailUfficio.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noEmailUfficio", "true");
			stato = false;
		}
		
		if (telUfficio == null || telUfficio.trim().equals("")) {
			request.setAttribute("uN", userName);
			request.setAttribute("noTelUfficio", "true");
			stato = false;
		}
		
		return stato;
		
	}
	
	
	
}
