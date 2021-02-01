package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class UnitaImmoDiProprieta extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(UnitaImmoDiProprieta.class.getName());

	public UnitaImmoDiProprieta(BeanCommand bc){
		super(bc);
	}//-------------------------------------------------------------------------
	
	public UnitaImmoDiProprieta(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}//-------------------------------------------------------------------------
	
	@Override
	public CommandAck run(Context ctx) throws CommandException{
		final String NOME_TABELLA = "CAT_UIU_COM";
		final String NOME_TABELLA_ANAG = "CAT_UIU_COM_ANAG";
		final String NOME_SINONIMO = "CAT_UIU_COM";
		final String NOME_INDICE = "CAT_UIU_COM_SDX";
		final String NOME_SEQUENCE = "CAT_UIU_COM_SEQ";
		//final String CODIFICA = "ASC"; //ASCENSORI
		final String PGT_SQL_DECO_LAYER = "PGT_SQL_DECO_LAYER"; //decodifica dei vari layer interni al catalogo
		final String PGT_SQL_LAYER = "PGT_SQL_LAYER"; //censimento del layer
		
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String belfiore = ctx.getBelfiore();
		Connection conn = ctx.getConnection("DWH_" + belfiore);
		Connection connSiti = ctx.getConnection("SITI_" + belfiore);
		Connection connDbTotale = ctx.getConnection("DBTOTALE_" + belfiore);

		String nomeSchemaSiti = "";
		String nomeSchemaDiogene = "";
		String nomeSchemaDbtotale = "";
		try{
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			
			DatabaseMetaData dmdSiti = connSiti.getMetaData();
			nomeSchemaSiti = dmdSiti.getUserName();
			
			DatabaseMetaData dmdDbTotale = connDbTotale.getMetaData();
			nomeSchemaDbtotale = dmdDbTotale.getUserName();
		
			System.out.println("SCHEMA DIOGENE: " + nomeSchemaDiogene);
			System.out.println("SCHEMA SITI: " + nomeSchemaSiti);
			System.out.println("SCHEMA DBTOTALE: " + nomeSchemaDbtotale);
		
		String message = "";

		/*
		 * controllo esistenza layer in PGT_SQL_LAYER
		 * 
		 * Insert into &DIOGENEGIT..PGT_SQL_LAYER
   (ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, 
    NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, 
    NAME_COL_TEMA_DESCR, PLENCODE, PLDECODE, PLDECODE_DESCR, SHAPE_TYPE, 
    FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, FLG_HIDE_INFO, DATA_ACQUISIZIONE)
 Values
   ( (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_LAYER), 'UIU COMUNALI', 'STAT', 'N', 'SELECT * FROM CAT_UIU_COM', 
    'SHAPE', 'CAT_UIU_COM', 'CODUT', 'SE_ROW_ID', 'SELECT ' || (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_LAYER) || ' AS ID_LAYER, ID, codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = ' || (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_LAYER) || ' ', 
    'CATEGORIA', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 'POLYGON', 
    'N', 'Y', '70', '', 'N', TO_DATE('01/26/2017 15:03:00', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;
		 */
		
		Long layerId = 0l;
		sql = "select * from " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + " where NAME_TABLE = '" + NOME_TABELLA + "' ";
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		boolean esisteLayer = false;
		if (rs != null){
			while(rs.next()){
				esisteLayer = true;
				layerId = rs.getLong("ID");
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);
		
		if (esisteLayer){
			/*
			 * esiste record catalogo censito su PGT_SQL_LAYER
			 */
		}else{

			sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + " "
					+ "(ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, "
					+ "NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, "
					+ "NAME_COL_TEMA_DESCR, PLENCODE, PLDECODE, PLDECODE_DESCR, SHAPE_TYPE, "
					+ "FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, FLG_HIDE_INFO, DATA_ACQUISIZIONE)"
					+ "Values"
					+ "( (select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + "), 'UIU COMUNALI', 'STAT', 'N', 'SELECT * FROM CAT_UIU_COM', "
					+ "'SHAPE', 'CAT_UIU_COM', 'CODUT', 'SE_ROW_ID', 'SELECT ' || (select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + ") || ' AS ID_LAYER, ID, codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = ' || (select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + ") || ' ', "
					+ "'CATEGORIA', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 'POLYGON', "
					+ "'N', 'Y', '70', '', 'N', TO_DATE('01/26/2017 15:03:00', 'MM/DD/YYYY HH24:MI:SS')) ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okIns00 = pstmt.execute();
			conn.commit();
			DbUtils.close(pstmt);
			/*
			 * recuperiamo ID_LAYER del catalogo da PGT_SQL_LAYER se non esiste già
			 */
			if (layerId != null && layerId>0){
				sql = "select * from " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + " where NAME_TABLE = '" + NOME_TABELLA + "' ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
					while(rs.next()){
						layerId = rs.getLong("ID");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
			}
			
		}
		
		/*
		 * controllo esistenza riga su PGT_SQL_DECO_LAYER
		 * 
		 delete from pgt_sql_deco_layer where id_layer=8;

		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A00', 'A00', 8, 'maroon','1', '1', 'maroon', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A01', 'A01', 8, 'darkred','1', '1', 'darkred', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A02', 'A02', 8, 'brown', '1', '1', 'brown', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A03', 'A03', 8, 'firebrick','1', '1', 'firebrick', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A04', 'A04', 8, 'red', '1', '1', 'red', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A05', 'A05', 8, 'indianred','1', '1', 'indianred', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A06', 'A06', 8, 'rosybrown','1', '1', 'rosybrown', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A07', 'A07', 8, 'lightcoral','1', '1', 'lightcoral', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A10', 'A10', 8, 'sandybrown','1', '1', 'sandybrown', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'A11', 'A11', 8, 'lightbrown','1', '1', 'lightbrown', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B01', 'B01', 8, 'olivedrab','1', '1', 'olivedrab', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B02', 'B02', 8, 'darkseagreen','1', '1', 'darkseagreen', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B03', 'B03', 8, 'lawngreen','1', '1', 'lawngreen', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B04', 'B04', 8, 'greenyellow','1', '1', 'greenyellow', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B05', 'B05', 8, 'darkgreen','1', '1', 'darkgreen', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B06', 'B06', 8, 'green','1', '1', 'green', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B07', 'B07', 8, 'mediumspringgreen','1', '1', 'mediumspringgreen', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'B08', 'B08', 8, 'lightgreen','1', '1', 'lightgreen', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C01', 'C01', 8, 'royalblue', '1', '1', 'royalblue', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C02', 'C02', 8, 'steelblue','1', '1', 'steelblue', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C03', 'C03', 8, 'aqua','1', '1', 'aqua', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C04', 'C04', 8, 'cadetblue','1', '1', 'cadetblue', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C05', 'C05', 8, 'lightblue','1', '1', 'lightblue', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C06', 'C06', 8, 'lightskyblue','1', '1', 'lightskyblue', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'C07', 'C07', 8, 'lightblue','1', '1', 'lightblue', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D01', 'D01', 8, 'fuchsia','1', '1', 'fuchsia', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D02', 'D02', 8, 'orchid','1', '1', 'orchid', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D03', 'D03', 8, 'mediumvioletred','1', '1', 'mediumvioletred', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D04', 'D04', 8, 'pink','1', '1', 'pink', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D05', 'D05', 8, 'blueviolet','1', '1', 'blueviolet', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D06', 'D06', 8, 'mediumvioletred','1', '1', 'mediumvioletred', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D07', 'D07', 8, 'plum','1', '1', 'plum', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D08', 'D08', 8, 'darkviolet','1', '1', 'darkviolet', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D09', 'D09', 8, 'lightviolet','1', '1', 'lightviolet', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'D10', 'D10', 8, 'lightpink','1', '1', 'lightpink', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E01', 'E01', 8, 'tan','1', '1', 'tan', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E02', 'E02', 8, 'lightyellow','1', '1', 'lightyellow', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E03', 'E03', 8, 'yellow','1', '1', 'yellow', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E04', 'E04', 8, 'orange','1', '1', 'orange', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E05', 'E05', 8, 'goldenrod','1', '1', 'goldenrod', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E06', 'E06', 8, 'darkyellow','1', '1', 'darkyellow', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E07', 'E07', 8, 'navajowhite','1', '1', 'navajowhite', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E08', 'E08', 8, 'navajowhite','1', '1', 'navajowhite', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'E09', 'E09', 8, 'moccasin','1', '1', 'moccasin', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'F01', 'F01', 8, 'black','1', '1', 'black', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'F02', 'F02', 8, 'magenta','1', '1', 'magenta', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'F03', 'F03', 8, 'fuchsia','1', '1', 'fuchsia', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'F04', 'F04', 8, 'deeppink','1', '1', 'deeppink', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'F05', 'F05', 8, 'lightorange','1', '1', 'lightorange', null, 'N');
		Insert into PGT_SQL_DECO_LAYER Values (cat_uiu_seq.nextval, 'F06', 'F06', 8, 'lightslategray','1', '1', 'lightslategray', null, 'N');
		
		COMMIT;


		 */
		
		sql = "select * from " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " where ID_LAYER = " + layerId;
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		boolean esisteDecoLayer = false;
		if (rs != null){
			while(rs.next()){
				esisteDecoLayer = true;
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);
		
		if (esisteDecoLayer){
			/*
			 * Catalogo già tematizzato
			 */
		}else{
			/*
			 * Catalogo da tematizzare
			 */
			
			//sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " "
			//		+ "(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD) "
			//		+ "Values"
			//		+ "( (select max(id)+1 FROM " + PGT_SQL_DECO_LAYER + "), 'ASC00', '00-05', (select max(id) FROM " + PGT_SQL_DECO_LAYER + "), 'violet', '1', '1', 'black', 'Y') ";
			
			sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " "
					+ "(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD) "
					+ "Values ";
			
			String[] arySqlIns = new String[]{
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A00', 'A00', "+layerId+", 'maroon','1', '1', 'maroon', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A01', 'A01', "+layerId+", 'darkred','1', '1', 'darkred', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A02', 'A02', "+layerId+", 'brown', '1', '1', 'brown', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A03', 'A03', "+layerId+", 'firebrick','1', '1', 'firebrick', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A04', 'A04', "+layerId+", 'red', '1', '1', 'red', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A05', 'A05', "+layerId+", 'indianred','1', '1', 'indianred', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A06', 'A06', "+layerId+", 'rosybrown','1', '1', 'rosybrown', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A07', 'A07', "+layerId+", 'lightcoral','1', '1', 'lightcoral', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A08', 'A08', "+layerId+", 'darkcoral','1', '1', 'darkcoral', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A09', 'A09', "+layerId+", 'coral','1', '1', 'coral', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A10', 'A10', "+layerId+", 'sandybrown','1', '1', 'sandybrown', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'A11', 'A11', "+layerId+", 'lightbrown','1', '1', 'lightbrown', null, 'N') ",
					
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B01', 'B01', "+layerId+", 'olivedrab','1', '1', 'olivedrab', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B02', 'B02', "+layerId+", 'darkseagreen','1', '1', 'darkseagreen', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B03', 'B03', "+layerId+", 'lawngreen','1', '1', 'lawngreen', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B04', 'B04', "+layerId+", 'greenyellow','1', '1', 'greenyellow', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B05', 'B05', "+layerId+", 'darkgreen','1', '1', 'darkgreen', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B06', 'B06', "+layerId+", 'green','1', '1', 'green', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B07', 'B07', "+layerId+", 'mediumspringgreen','1', '1', 'mediumspringgreen', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'B08', 'B08', "+layerId+", 'lightgreen','1', '1', 'lightgreen', null, 'N') ",
					
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C01', 'C01', "+layerId+", 'royalblue', '1', '1', 'royalblue', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C02', 'C02', "+layerId+", 'steelblue','1', '1', 'steelblue', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C03', 'C03', "+layerId+", 'aqua','1', '1', 'aqua', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C04', 'C04', "+layerId+", 'cadetblue','1', '1', 'cadetblue', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C05', 'C05', "+layerId+", 'lightblue','1', '1', 'lightblue', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C06', 'C06', "+layerId+", 'lightskyblue','1', '1', 'lightskyblue', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'C07', 'C07', "+layerId+", 'lightblue','1', '1', 'lightblue', null, 'N') ",

					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D01', 'D01', "+layerId+", 'fuchsia','1', '1', 'fuchsia', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D02', 'D02', "+layerId+", 'orchid','1', '1', 'orchid', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D03', 'D03', "+layerId+", 'mediumvioletred','1', '1', 'mediumvioletred', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D04', 'D04', "+layerId+", 'pink','1', '1', 'pink', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D05', 'D05', "+layerId+", 'blueviolet','1', '1', 'blueviolet', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D06', 'D06', "+layerId+", 'mediumvioletred','1', '1', 'mediumvioletred', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D07', 'D07', "+layerId+", 'plum','1', '1', 'plum', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D08', 'D08', "+layerId+", 'darkviolet','1', '1', 'darkviolet', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D09', 'D09', "+layerId+", 'lightviolet','1', '1', 'lightviolet', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'D10', 'D10', "+layerId+", 'lightpink','1', '1', 'lightpink', null, 'N') ",
					
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E01', 'E01', "+layerId+", 'tan','1', '1', 'tan', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E02', 'E02', "+layerId+", 'lightyellow','1', '1', 'lightyellow', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E03', 'E03', "+layerId+", 'yellow','1', '1', 'yellow', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E04', 'E04', "+layerId+", 'orange','1', '1', 'orange', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E05', 'E05', "+layerId+", 'goldenrod','1', '1', 'goldenrod', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E06', 'E06', "+layerId+", 'darkyellow','1', '1', 'darkyellow', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E07', 'E07', "+layerId+", 'navajowhite','1', '1', 'navajowhite', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E08', 'E08', "+layerId+", 'navajowhite','1', '1', 'navajowhite', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'E09', 'E09', "+layerId+", 'moccasin','1', '1', 'moccasin', null, 'N') ",
					
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'F01', 'F01', "+layerId+", 'black','1', '1', 'black', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'F02', 'F02', "+layerId+", 'magenta','1', '1', 'magenta', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'F03', 'F03', "+layerId+", 'fuchsia','1', '1', 'fuchsia', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'F04', 'F04', "+layerId+", 'deeppink','1', '1', 'deeppink', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'F05', 'F05', "+layerId+", 'lightorange','1', '1', 'lightorange', null, 'N') ",
					"((select max(id)+1 FROM " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + "), 'F06', 'F06', "+layerId+", 'lightslategray','1', '1', 'lightslategray', null, 'N') "					
			};
			
			for (int indice=0; indice < arySqlIns.length; indice++){
				String qry = sql + arySqlIns[indice];
				log.debug("QRY: " + qry);			
			
				pstmt = conn.prepareCall(sql);
				boolean okIns00 = pstmt.execute();
			}
			
			conn.commit();
			DbUtils.close(pstmt);
			
		}
		/*
		 * Controllo l'esistenza della tabella contenente le anagrafiche facenti capo al comune 
		 */
		sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_ANAG + "'";
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		boolean esisteTab = false;
		if (rs != null){
			while(rs.next()){
				esisteTab = true;
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);
		/*
		 * Recupero info ente corrente
		 */
		String denominazioneEnte = "";
		String belfioreEnte = "";
		sql = "select * from " + nomeSchemaDiogene + ".SIT_ENTE ";
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		if (rs != null){
			while(rs.next()){
				denominazioneEnte = rs.getString("DESCRIZIONE");
				belfioreEnte = rs.getString("CODENT"); 
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);

		if (esisteTab){
			/*
			 * Se esiste lasciamo il mondo come sta perchè potrebbero aver aggiunto 
			 * delle righe (persone giuridiche facenti capo all'ente) che in automatico 
			 * non è al momento possibile recuperare
			 * DI SEGUITO LA QUERY USATA PER POPOLARE LA ANAG_SOGGETTI IN MODO MANUALE 
			 * PRIMA DELLE CREAZIONE DELLA REGOLA  
			 */
			/*
			 	create table cav_000 as
				insert into cav_000
				select * from (
				select * from anag_soggetti where upper(denominazione) like '%COMUNE%'
				union
				select * from anag_soggetti where part_iva='01199250158'
				)
				minus
				select * from cav_000
			 */
		}else{
			/*
			 * Se NON esiste la tabella delle anagrafiche la generiamo basando la 
			 * raccolta informazioni esclusivamente sul nome dell'ente cosi strutturato:
			 * "COMUNE DI _______"
			 */
			
			
			if (denominazioneEnte != null && !denominazioneEnte.trim().equalsIgnoreCase("")){
				sql = "create table " + nomeSchemaDiogene + "." + NOME_TABELLA_ANAG + " as "
						+ "select * from " + nomeSchemaDiogene + ".anag_soggetti where upper(denominazione) like '%COMUNE DI "+ denominazioneEnte +"%' ";
				
				log.debug("SQL: " + sql);
	
				pstmt = conn.prepareCall(sql);
				boolean okCreate = pstmt.execute();
				DbUtils.close(pstmt);
			}else{
				log.error("IMPOSSIBILE GENERARE TABELLA " + NOME_TABELLA_ANAG + ": campo DESCRIZIONE in SIT_ENTE non valorizzato");
			}
		}
			/*
			 * Controllo l'esistenza della tabella catalogo
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);

			if (esisteTab){
				/*
				 * Se esiste la droppiamo per poi generarla nuovamente 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA;
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Svuotamento " + NOME_TABELLA + " eseguito con successo";
				log.debug(message);
				
			}
			/*
			 * La query attinge anche da tabelle dello schema SITI ma dovrebbero esistere 
			 * i sinonimi su DIOGENE
			 */
				sql = "create table "+nomeSchemaDiogene+"."+NOME_TABELLA+" as "
						+ "select CAST(NULL AS NUMBER) AS SE_ROW_ID, su.*, sp.shape from( " +
					"select foglio, particella, categoria, count(*) n_uiu from ( " + 
					"select distinct u.foglio, u.particella, u.sub, u.unimm, u.categoria " +
					"from "+nomeSchemaDiogene+".siticomu scom " + 
					"left join "+nomeSchemaDiogene+".sitiuiu u on scom.cod_nazionale=u.cod_nazionale " + 
					"left join "+nomeSchemaDiogene+".siticonduz_imm_all sc on u.cod_nazionale=sc.cod_nazionale " +
							"and u.data_fine_val=sc.data_fine " +
							"and u.foglio=sc.foglio " +
							"and u.particella=sc.particella " +
							"and u.sub=sc.sub " +
							"and u.unimm=sc.unimm " +
					"left join "+nomeSchemaDiogene+"."+NOME_TABELLA_ANAG+" ans on sc.data_fine= ans.data_fine_val and sc.pk_cuaa=ans.cod_soggetto " +
					"where scom.codi_fisc_luna = '"+belfioreEnte+"' " +
					"and u.data_fine_val = to_date('99991231','yyyymmdd')" +
					"and u.categoria <> '-' " +
					"and ans.cod_soggetto in (select cod_soggetto from "+nomeSchemaDiogene+"."+NOME_TABELLA_ANAG+")) " +
					"group by foglio, particella, categoria) su " +
						"inner join "+nomeSchemaDiogene+".sitipart sp on su.foglio=sp.foglio and su.particella=sp.particella ";
				
				log.debug("SQL: " + sql);

				pstmt = conn.prepareCall(sql);
				boolean okCreate = pstmt.execute();
				DbUtils.close(pstmt);
				/*
				 * Valorizziamo colonna SE_ROW_ID
				 * SEQ_TEMPO.nextval
				 */
				sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + " SET SE_ROW_ID = ROWNUM ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpd = pstmt.execute();

				/*
				 * Creo i sinonimi - se non esistono - della NOME_TABELLA in DBTOTALE e SITI
				 */
				/*
				 * Controllo esistenza sinonimo in DBTOTALE
				 */
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaDbtotale + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connDbTotale.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esiteSynSuDbtotale = false;
				if (rs != null){
					while(rs.next()){
						esiteSynSuDbtotale = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esiteSynSuDbtotale){
					sql = "CREATE SYNONYM " + nomeSchemaDbtotale + "." + NOME_SINONIMO + " FOR " + nomeSchemaDiogene +  "." + NOME_TABELLA + " ";
					log.debug("SQL: " + sql);
					pstmt = connDbTotale.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
					message = "Sinonimo " + NOME_SINONIMO + " creato con successo";
					log.debug(message);
				}else{
					message = "Sinonimo " + NOME_SINONIMO + " esistente";
					log.debug(message);
				}
				/*
				 * Controllo esistenza sinonimo in SITI
				 */
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaSiti + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteSynSuSiti = false;
				if (rs != null){
					while(rs.next()){
						esisteSynSuSiti = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteSynSuSiti){
					sql = "CREATE SYNONYM " + nomeSchemaSiti + "." + NOME_SINONIMO + " FOR " + nomeSchemaDiogene +  "." + NOME_TABELLA + " ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
					message = "Sinonimo " + NOME_SINONIMO + " creato con successo";
					log.debug(message);
				}else{
					message = "Sinonimo " + NOME_SINONIMO + " esistente";
					log.debug(message);
				}
					
				//STRUCT st = (oracle.sql.STRUCT) rs.getObject("shape");
				
				/*
				 * Creazione degli indici spaziali su DIOGENE {call DIOGENE.crea_idx_spatial()}
				 * EXECUTE crea_idx_spatial('CAT_ASCENSORI', 'SHAPE', null, 'CAT_ASCENSORI_SDX');
				 */

				sql = "{call " + nomeSchemaDiogene + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean creaIdx = pstmt.execute();
				DbUtils.close(pstmt);

				return (new ApplicationAck("LAYER CARTOGRAFICO UNITA IMMOBILIARI DI PROPRIETA GENERATO CON SUCCESSO"));
			
		}catch (Exception e)
		{
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
		finally{
			try {
				conn.close();
				connSiti.close();
				connDbTotale.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}

	}
}
