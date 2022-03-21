package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import oracle.sql.STRUCT;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * Regola che in base ad una stringa esegue un SQL.
 * Se il sql è stato esguito la regola ritorna un CommandAck con un messaggio di conferma
 * Invece se non è stato possibile eseguire il sql la regola ritorna un ErrorAck
 * con il messaggio di errore.
 * 
 * Possono essere passati dei parametri all'sql che verranno sostituiti ai ? del PreparedStatement
 *
 * @author sisani
 * @version $Revision: 1.7 $ $Date: 2010/09/28 12:12:27 $
 */
public class StatCategorieCatastali extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(StatCategorieCatastali.class.getName());
	
	private String sql = null;
	private String STANDARD="N";
	public static Hashtable<String, String> htColoreCategoria = new Hashtable<String, String>();
	
	static{
		htColoreCategoria.put("A01", "darkred"); 
		htColoreCategoria.put("A02", "saddlebrown"); 
		htColoreCategoria.put("A03", "crimson");  
		htColoreCategoria.put("A04", "red");  
		htColoreCategoria.put("A05", "indianred");   
		htColoreCategoria.put("A06", "tomato");  
		htColoreCategoria.put("A07", "rosybrown");  
		htColoreCategoria.put("A08", "lightcoral");  
		htColoreCategoria.put("A09", "darkorange"); 
		htColoreCategoria.put("A10", "sandybrown"); 

		htColoreCategoria.put("B01","darkgreen");   
		htColoreCategoria.put("B02","green");  
		htColoreCategoria.put("B03","olivedrab");  
		htColoreCategoria.put("B04","darkseagreen"); 
		htColoreCategoria.put("B05","mediumaquamarine");  
		htColoreCategoria.put("B06","mediumspringgreen"); 
		htColoreCategoria.put("B07","lightgreen");  
		htColoreCategoria.put("B08","greenyellow");  
		
		htColoreCategoria.put("C01","mediumblue"); 
		htColoreCategoria.put("C02","royalblue");   
		htColoreCategoria.put("C03","steelblue"); 
		htColoreCategoria.put("C04","aqua"); 
		htColoreCategoria.put("C05","cornflowerblue"); 
		htColoreCategoria.put("C06","deepskyblue"); 
		htColoreCategoria.put("C07","lightblue"); 
		
		htColoreCategoria.put("D01","indigo");  
		htColoreCategoria.put("D02","purple"); 
		htColoreCategoria.put("D03","blueviolet");  
		htColoreCategoria.put("D04","mediumvioletred"); 
		htColoreCategoria.put("D05","deeppink");  
		htColoreCategoria.put("D06","fuchsia"); 
		htColoreCategoria.put("D07","palevioletred"); 
		htColoreCategoria.put("D08","orchid"); 
		htColoreCategoria.put("D09","plum"); 
		htColoreCategoria.put("D10","pink");  
		
		htColoreCategoria.put("E01","darkgoldenrod");  
		htColoreCategoria.put("E02","chocolate"); 
		htColoreCategoria.put("E03","goldenrod"); 
		htColoreCategoria.put("E04","tan");  
		htColoreCategoria.put("E05","gold"); 
		htColoreCategoria.put("E06","khaki");  
		htColoreCategoria.put("E07","yellow");  
		htColoreCategoria.put("E08","navajowhite"); 
		htColoreCategoria.put("E09","lightgoldenrodyellow");  
		
		htColoreCategoria.put("F01","black"); 
		htColoreCategoria.put("F02","lightslategray"); 
		htColoreCategoria.put("F03","gray");  
		htColoreCategoria.put("F04","darkgray");  
		htColoreCategoria.put("F05","lightgray"); 

	}
		
	/**
	 * @param bc
	 */
	public StatCategorieCatastali(BeanCommand bc){
		super(bc);
	}
	
	public StatCategorieCatastali(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{
		final String NOME_TABELLA = "CAT_CATEGORIE_CAT";
		final String NOME_SINONIMO = "CAT_CATEGORIE_CAT";
		final String NOME_INDICE = "CAT_CATEGORIE_CAT_SDX";
		
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		
		
		String belfiore = ctx.getBelfiore();
		Connection conn = ctx.getConnection("DWH_" + belfiore);
		Connection connSiti = ctx.getConnection("SITI_" + belfiore);
		
		String nomeSchemaSiti = "";
		String nomeSchemaDiogene = "";
		
		
		
		try{
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			
			DatabaseMetaData dmdSiti = connSiti.getMetaData();
			nomeSchemaSiti = dmdSiti.getUserName();
			
			System.out.println("SCHEMA DIOGENE: " + nomeSchemaDiogene);
			System.out.println("SCHEMA SITI: " + nomeSchemaSiti);
		
		}catch (Exception e)
		{
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
		finally{
			
		}
		
		String message = "";
		try
		{
			//Rimuovo la tabella, se presente
			try{
				sql = "drop table "+ NOME_TABELLA;
				
				log.debug(sql);
				pstmt = conn.prepareCall(sql);
				pstmt.execute();
				
			}catch(Exception e){}

			//Creo la tabella
			
			
			sql = "create table "+NOME_TABELLA+" as "+
			"select rownum as se_row_id, tab.* from ( " +
			"select  su.*, sp.shape from( "+
			"select distinct foglio,particella, categoria, count(*) occorrenze from "+nomeSchemaSiti+".sitiuiu "+
			"where data_fine_val=to_date('99991231','yyyymmdd') " +
			"and categoria <> '-' " +
			"group by foglio, particella, categoria) su " +
			"inner join "+nomeSchemaSiti+".sitipart sp on su.foglio=sp.foglio and su.particella=sp.particella "+
			"order by su.foglio, su.particella, su.categoria "+
			") tab";
			
			log.debug(sql);
			pstmt = conn.prepareCall(sql);
			pstmt.execute();
		
			//Carico le categorie disponibili per poter popolare la PGT_SQL_DECO_LAYER
			
			ArrayList<String> lstCategorie = new ArrayList<String>();
			
			sql ="select distinct categoria from "+nomeSchemaDiogene+"."+NOME_TABELLA+" order by categoria";
				
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
				
			while(rs.next()){
				String categoria = rs.getString("categoria");
				lstCategorie.add(categoria);	
			}
			DbUtils.close(rs);
			DbUtils.close(pstmt);
				
			/*
			 * Recupero l'id del catalogo da  PGT_SQL_LAYER attraverso il nome della tabella 
			 */
			Integer idLayer = this.getIdLayer(conn, nomeSchemaDiogene, NOME_TABELLA);
			
			/*
			 * Elimino i vecchi record in PGT_SQL_DECO_LAYER con il corrente idCat 
			 * per poi rigenerare la codifica 
			 */
			if (idLayer != null){
				sql = "delete from " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER where id_layer = " + idLayer;
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDelete = pstmt.execute();
				DbUtils.close(pstmt);
					
				/*
				 * Inizio Decodifica e insert dentro la PGT_SQL_DECO_LAYER dopo aver
				 * recuperato l'idLayer da PGT_SQL_LAYER attraverso il nome della tabella
				 * specifica per questo catalogo (vedi costante nomeTabella)
				 */
				String colore =null;
				Integer maxId = this.getIdDecoLayer(conn, nomeSchemaDiogene)+1;
				for (int i=0; i<lstCategorie.size(); i++){
						
					String categoria = lstCategorie.get(i);
					
					if(categoria!=null){
					   colore = htColoreCategoria.get(categoria);
					   
					   if(colore!=null){
						   sql = "Insert into " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER " +
						   		 "Values ("+maxId+", '"+categoria+"', 'Categoria:"+categoria+"', '"+idLayer+"', '"+colore+"','1', '1', '"+colore+"', null, 'N')";
	
							pstmt = conn.prepareCall(sql);
							message = "Esito di " + sql + ": ";
							boolean okInsert = pstmt.execute();
							DbUtils.close(pstmt);
							message += (!okInsert)?"OK":"KO";
							log.debug(message);
						   
							maxId++;
					   }
						
					}
					
				}
			}
			
			/*
			 * Creazione degli indici spaziali su SITI {call SITI.crea_idx_spatial()}
			 */
			
			log.debug("Inizio creazione indici spaziali: " + NOME_INDICE);
			sql = "{call " + nomeSchemaDiogene + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean creaIdx = pstmt.execute();
			DbUtils.close(pstmt);
							
			return (new ApplicationAck("SQL ESEGUITO CON SUCCESSO"));
				
		}catch (Exception e)
		{
			log.error("Errore esecuzione sql:" + sql, e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try
			{
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
			}

		}
	}
	
	private Integer getIdDecoLayer(Connection conn, String nomeSchema) throws Exception{
		
		CallableStatement pstmt = null;
		
		Integer id = 0;
		ResultSet rs = null;
		
		try{
			
			sql= "select max(id) max_id from "+nomeSchema+".pgt_sql_deco_layer ";
			log.debug("SQL: " + sql);
			
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				id = rs.getInt("max_id");
			
		}catch(Exception e){
			log.error("Errore esecuzione sql:" + sql, e);
			throw e;
			
		}finally{
			
			try {
				DbUtils.close(pstmt);
				DbUtils.close(rs);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		return id;
	}
	
	
	private Integer getIdLayer(Connection conn, String nomeSchema, String nomeTabella) throws Exception{
		
		CallableStatement pstmt = null;
	
		Integer idCatalogo = null;
		ResultSet rs = null;
		
		try{
			
		
			//Recupero l'id del Catalogo, se presente
			sql= "select * from "+nomeSchema+".pgt_sql_layer where name_table= '"+nomeTabella+"'";
			log.debug("SQL: " + sql);
			
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
				idCatalogo = rs.getInt("id");
			
			//Il catalogo non c'è: va inserito
			if(idCatalogo==null){
				
				sql = "select max(id) max_id from "+nomeSchema+".pgt_sql_layer";
				log.debug("SQL: " + sql);
				
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next())
					idCatalogo = rs.getInt("max_id");
				else
					idCatalogo=0;
				
				idCatalogo++;
				
				/*Inserisco il catalogo in PGT_SQL_LAYER */
				sql = "Insert into PGT_SQL_LAYER"+
						"(ID, DES_LAYER ,TIPO_LAYER, STANDARD ,SQL_LAYER," +
						"NAME_COL_SHAPE,NAME_TABLE,NAME_COL_TEMA,NAME_COL_TEMA_DESCR,NAME_COL_ID," +
						"SQL_DECO," +
						"PLDECODE ,PLDECODE_DESCR,PLENCODE,SHAPE_TYPE,FLG_DOWNLOAD,FLG_PUBLISH ,OPACITY ,DESCR_COL_INFO, NAME_COL_INFO) " +
						"Values ("+idCatalogo+", 'CATEGORIE CATASTALI', 'STAT', '"+STANDARD+"', " +
						"'SELECT * FROM CAT_CATEGORIE_CAT', " +
						"'SHAPE', 'CAT_CATEGORIE_CAT', 'CATEGORIA','CATEGORIA','SE_ROW_ID', " +
						"'SELECT "+idCatalogo+"  AS ID_LAYER,ID,codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = "+idCatalogo+"', " +
						"'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC','LAYER_TOOLS.GETCODEPRGC_DESCR','POLYGON', 'N', 'Y', '60',null,null)";
				log.debug("SQL: " + sql);
				
				pstmt = conn.prepareCall(sql);
				pstmt.execute();
				
			}
			
			//Inserire la sql per recuperare i dati da servizi esterni in PGT_SQL_VIS_LAYER
			this.inserisciSqlVisLayer(conn, nomeSchema, nomeTabella, idCatalogo);
		
		}catch(Exception e){
			idCatalogo = null;
			log.error("Errore esecuzione sql:" + sql, e);
			throw e;
			
		}finally{
			
			try {
				DbUtils.close(pstmt);
				DbUtils.close(rs);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		
		return idCatalogo;
		
	}
	
	
	private void inserisciSqlVisLayer(Connection conn, String nomeSchema, String nomeTabella, Integer idCatalogo) throws Exception{
		
		CallableStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			sql = "select t.id,l.id_tmpl from pgt_sql_vis_template t left join pgt_sql_vis_layer l on l.id_tmpl=t.id and id_layer=? and stnd_layer=?";
			pstmt = conn.prepareCall(sql);
			pstmt.setInt(1, idCatalogo);
			pstmt.setString(2, STANDARD);
			log.debug("SQL: " + sql);
			rs = pstmt.executeQuery();
			ArrayList<Integer> lstIdTmpl = new ArrayList<Integer>();
			while(rs.next()){
				Integer id = rs.getInt("ID");
				Integer idL = rs.getInt("ID_TMPL");
				if(idL==null || id!=idL) 
					lstIdTmpl.add(id);
			}
			
			for(Integer idTmpl : lstIdTmpl){
			
				/*Inserisco query di catalogo in PGT_SQL_VIS_LAYER */
				sql = "Insert into PGT_SQL_VIS_LAYER (ID_TMPL, ID_LAYER ,SQL_LAYER, MOD_INTERROGAZIONE ,VISUALIZZA,STND_LAYER)" +
											 "Values (?,?,null,'C','Y',?)";
				log.debug("SQL: " + sql);
				
				pstmt = conn.prepareCall(sql);
				pstmt.setInt(1, idTmpl);
				pstmt.setInt(2, idCatalogo);
				pstmt.setString(3, STANDARD);
				pstmt.execute();
			}
			
		}catch(Exception e){
			log.error("Errore esecuzione sql:" + sql, e);
			throw e;
			
		}finally{
			
			try {
				DbUtils.close(pstmt);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		
	}
	
	
	private void controllaInserisciColonna(Connection conn, String nomeSchema, String nomeTabella, String nomeColonna, String tipo) throws SQLException{
		
		
		ResultSet rs;
		CallableStatement pstmt;
		
	
		sql = "select * from ALL_TAB_COLUMNS where column_name = '"+nomeColonna+"' and table_name = '" + nomeTabella + "' and owner = '" + nomeSchema + "' ";
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		boolean esisteCol = false;
		if (rs != null){
			while(rs.next()){
				esisteCol = true;
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);
		if (!esisteCol){
			sql = "alter table " + nomeSchema + "." + nomeTabella + " add ("+nomeColonna+"  "+tipo+") ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			pstmt.execute();
			DbUtils.close(pstmt);
		}
		
	}
	
	private boolean isFoglioNumeric(String foglio) {
		if (foglio != null) {
			for (char car : foglio.toCharArray()) {
				if ("1234567890".indexOf("" + car) == -1) {
					return false;
				}
			}
		}
		return true;
	}

}
