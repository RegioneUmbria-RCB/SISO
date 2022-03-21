package it.webred.rulengine.brick.reportTarsu;

import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class CaricaSitRepTarsu extends Command implements Rule {
	private static final org.apache.log4j.Logger log = Logger.getLogger(CaricaSitRepTarsu.class.getName());
	public CaricaSitRepTarsu(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		Connection conn = null;
		CallableStatement cstmt=null; 
		
		//
		boolean insertSitRepTarsu=true;
		boolean updateTarsuCat=true;
		boolean updateUiuCiv=true;
		boolean updateRes=true;
		boolean updateResCiv=true;
		boolean updatePropPart=true;
		boolean updatePropUiu=true;
		boolean updateTarsu=true;
		boolean updateTarsuFam=true;
		boolean updateTarsuTit=true;
		boolean updateFAcq=true;
		boolean updateFAcqCiv=true;
		boolean updateFEnel=true;
		boolean updateFEnelCiv=true;
		boolean updateLocatore=true;
		boolean updateLocatari=true;
		boolean updateLocatariTarsu=true;
		boolean updateAltriResCiv=true;
		boolean updateAltriResTarsu=true;
		
		String enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		try {
			// recupero parametri catena jelly
			log.debug("INIZIO REGOLA CaricaSitRepTarsu");
			//this.getJellyParam(ctx);//QUESTO PER ORA NON SERVE
			conn = ctx.getConnection((String)ctx.get("connessione"));
			if (conn== null) {
				log.debug("ERRORE CONNESSIONE");
				retAck =  new ErrorAck("ERRORE RECUPERO CONNESSIONE: NULL");
				return retAck;
			}
			
			String sql = "";
			//preparazione tabelle di appoggio
	        if (updateRes || updateResCiv || updateTarsuFam) {
	        	//DROP VIEW
				dropObj(conn, "view" , "j_sogg_cat_ana ");
				//creazione tabelle di appoggio
				//---cancellazione 
				dropObj(conn, "table", "j_sogg_cat_ana" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
				String sqlCreateTabTemp01=
					"create table tab_temp_rep_tarsu_001 as select distinct a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a, " +
		            "    a.codfisc codfisc_a, a.dt_nascita dt_nascita_a, a.rel_descr rel_descr_a, a.rating rating_a, "+
		            "    a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_soggetto " +
					" from sit_soggetto_totale a " +
			        " where fk_ente_sorgente=4 " +
			        "   and prog_es = 3";
				eseguiSql(conn,sqlCreateTabTemp01);
				log.debug("creata tab_temp_rep_tarsu_001 ");
				String sqlCreateTabTemp02=
					"create table tab_temp_rep_tarsu_002 as select distinct b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b," +
		            "    b.codfisc codfisc_b, b.dt_nascita dt_nascita_b, b.rel_descr rel_descr_b, b.rating rating_b, " +
		            "    b.fk_ente_sorgente es_b, b.prog_es prog_es_b, b.fk_soggetto " +
		            " from sit_soggetto_totale b  " +
		            " where fk_ente_sorgente=1 " +
		           "  and prog_es = 1 "; 
				eseguiSql(conn,sqlCreateTabTemp02);
				log.debug("creata tab_temp_rep_tarsu_001 ");
				
				//creazione indici per tabelle di appoggio
				sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel";
				eseguiSql(conn,sql);
				
				log.debug("creato idx_temp_01_fs ");
				sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
				eseguiSql(conn,sql);
				
				log.debug("creato idx_temp_02_fs ");
				//creazione tabella  j_sogg_cat_ana  da usare al posto della vista omonima
				sql= "create table j_sogg_cat_ana as " +
					" select a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a,  a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a, " +
					"	b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b " +
					"	from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b " +
					"	where a.fk_soggetto=b.fk_soggetto";
				eseguiSql(conn,sql);
				
				log.debug("creata j_sogg_cat_ana ");
				sql="create index idx_jsca_id_dwh_a on j_sogg_cat_ana (id_dwh_a) nologging noparallel";
				eseguiSql(conn,sql);
				
				log.debug("creato idx_jsca_id_dwh_a ");
				sql="create index idx_jsca_id_dwh_b on j_sogg_cat_ana (id_dwh_b) nologging noparallel";
				eseguiSql(conn,sql);
				
				log.debug("creato idx_jsca_id_dwh_b ");
				//cancellazione tabelle di appoggio
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
	        }
	        if (updateResCiv || updateAltriResCiv || updateAltriResTarsu ) {
	        	//DROP VIEW
				dropObj(conn, "view" , "j_civ_cat_ana");
				//creazione tabelle di appoggio
				//---cancellazione 
				dropObj(conn, "table", "j_civ_cat_ana" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
				String sqlCreateTabTemp01=
					"create table tab_temp_rep_tarsu_001 as select distinct a.id_dwh id_dwh_a, a.civ_liv1 civ_liv1_a, " +
					"  a.civ_liv2 civ_liv2_a, a.civ_liv3 civ_liv3_a, a.rel_descr rel_descr_a, a.rating rating_a, " +
		            "  a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_civico " +
		            " from sit_civico_totale a " +
		            " where fk_ente_sorgente=4 and prog_es = 2 ";
				eseguiSql(conn,sqlCreateTabTemp01);
				log.debug("creata tab_temp_rep_tarsu_001 ");
				
				String sqlCreateTabTemp02=
					"create table tab_temp_rep_tarsu_002 as select distinct b.id_dwh id_dwh_b, b.civ_liv1 civ_liv1_b, " +
					"  b.civ_liv2 civ_liv2_b, b.civ_liv3 civ_liv3_b, b.rel_descr rel_descr_b, b.rating rating_b," +
					"  b.fk_ente_sorgente es_b, b.prog_es prog_es_b, b.fk_civico" +
					" from sit_civico_totale b" +
					" where fk_ente_sorgente=1 and prog_es = 1"; 
				this.eseguiSql(conn, sqlCreateTabTemp02);
				log.debug("creata tab_temp_rep_tarsu_001 ");
				
				//creazione indici per tabelle di appoggio
				sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_civico) nologging noparallel ";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_temp_01_fs ");
				sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_civico) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_temp_01_fs ");
				//creazione tabella  j_civ_cat_ana da usare al posto della vista omonima
				sql= "create table j_civ_cat_ana as select " + 
					 " a.id_dwh_a, a.civ_liv1_a, a.civ_liv2_a, a.civ_liv3_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a, " +
					 " b.id_dwh_b, b.civ_liv1_b, b.civ_liv2_b, b.civ_liv3_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b, b.fk_civico " +  
					" from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b "+
					" where a.fk_civico=b.fk_civico ";
				this.eseguiSql(conn, sql);
				log.debug("creata j_civ_cat_ana ");
				sql="create index idx_jcca_id_dwh_a on j_civ_cat_ana (id_dwh_a) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jcca_id_dwh_a per j_civ_cat_ana");
				sql="create index idx_jcca_id_dwh_b on j_civ_cat_ana (id_dwh_b) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jcca_id_dwh_b per j_civ_cat_ana");
				sql="create index idx_jcca_id_dwh_ab on j_civ_cat_ana (id_dwh_a, id_dwh_b) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jcca_id_dwh_ab per j_civ_cat_ana");
				//cancellazione tabelle di appoggio
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
	        }
	        if (updateTarsu) {
	           	//DROP VIEW
				dropObj(conn, "view" , "j_sogg_cat_trib");
				//creazione tabelle di appoggio
				//---cancellazione 
				dropObj(conn, "table", "j_sogg_cat_trib" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
				String sqlCreateTabTemp01=
					"create table tab_temp_rep_tarsu_001 as select distinct a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a," +
					"  a.codfisc codfisc_a, a.dt_nascita dt_nascita_a, a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, " +
					"  a.prog_es prog_es_a, a.fk_soggetto " +
					" from sit_soggetto_totale a " +
					" where fk_ente_sorgente=4 and prog_es = 3";
				this.eseguiSql(conn, sqlCreateTabTemp01);
				log.debug("creata tab_temp_rep_tarsu_001 ");
				String sqlCreateTabTemp02=
					"create table tab_temp_rep_tarsu_002 as select distinct b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b, " +
					"  b.codfisc codfisc_b, b.dt_nascita dt_nascita_b, b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b, " +
					"  b.prog_es prog_es_b, b.fk_soggetto" +
					" from sit_soggetto_totale b " +
					" where fk_ente_sorgente=2 and prog_es = 4"; 
				this.eseguiSql(conn, sqlCreateTabTemp02);
				log.debug("creata tab_temp_rep_tarsu_002 ");
				//creazione indici per tabelle di appoggio
				sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel ";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_temp_01_fs ");
				sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_temp_02_fs ");
				//creazione tabella  j_sogg_cat_trib da usare al posto della vista omonima
				sql= "create table j_sogg_cat_trib as select " +
					 "  a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
					 "  b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b" +
					 " from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b " +
					 " where a.fk_soggetto=b.fk_soggetto";
				this.eseguiSql(conn, sql);
				log.debug("creata j_sogg_cat_trib ");
				sql="create index idx_jsct_id_dwh_a on j_sogg_cat_trib (id_dwh_a) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jsct_id_dwh_a per j_sogg_cat_trib");
				sql="create index idx_jsct_id_dwh_b on j_sogg_cat_trib (id_dwh_b) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jsct_id_dwh_b per j_sogg_cat_trib");
				sql="create index idx_jsct_id_dwh_a_b on j_sogg_cat_trib (id_dwh_a,id_dwh_b)";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jsct_id_dwh_a_b per j_sogg_cat_trib");
				//cancellazione tabelle di appoggio
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
	        }
	        if (updateTarsuFam || updateAltriResTarsu) {
	           	//DROP VIEW
				dropObj(conn, "view" , "J_SOGG_ANA_TRIB");
				//creazione tabelle di appoggio
				//---cancellazione 
				dropObj(conn, "table", "J_SOGG_ANA_TRIB" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
				String sqlCreateTabTemp01=
					"create table tab_temp_rep_tarsu_001 as select distinct a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a, " +
					"  a.codfisc codfisc_a, a.dt_nascita dt_nascita_a, a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, " +
					"  a.prog_es prog_es_a, a.fk_soggetto " +
					" from sit_soggetto_totale a where fk_ente_sorgente=1 and prog_es = 1 ";
				this.eseguiSql(conn, sqlCreateTabTemp01);
				log.debug("creata tab_temp_rep_tarsu_001 ");
				String sqlCreateTabTemp02=
					"create table tab_temp_rep_tarsu_002 as select distinct b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b," +
					"  b.codfisc codfisc_b, b.dt_nascita dt_nascita_b, b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b," +
					"  b.prog_es prog_es_b, b.fk_soggetto" +
					" from sit_soggetto_totale b where fk_ente_sorgente=2 and prog_es = 4";
				this.eseguiSql(conn, sqlCreateTabTemp02);
				log.debug("creata tab_temp_rep_tarsu_002 ");
				//creazione indici per tabelle di appoggio
				sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel ";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_temp_01_fs ");
				sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_temp_02_fs ");
				//creazione tabella  j_sogg_cat_trib da usare al posto della vista omonima
				sql= "create table  j_sogg_ana_trib as select " +
				  " a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
				  " b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b " +
				  " from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_soggetto=b.fk_soggetto";
				this.eseguiSql(conn, sql);
				log.debug("creata j_sogg_ana_trib ");
				sql="create index idx_jsat_id_dwh_a on j_sogg_ana_trib (id_dwh_a) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("idx_jsat_id_dwh_a on j_sogg_ana_trib");
				sql="create index idx_jsat_id_dwh_b on j_sogg_ana_trib (id_dwh_b) nologging noparallel";
				this.eseguiSql(conn, sql);
				log.debug("creato idx_jsat_id_dwh_b on j_sogg_ana_trib");
				//cancellazione tabelle di appoggio
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
	        }
	        if (updateFAcq || updateFAcqCiv) {
	        	if (isFonteAbilitata(enteID, "ACQUA NEW")) { 
	        		//DROP VIEW
					dropObj(conn, "view" , "J_SOGG_CAT_UTEACQUA");
					//creazione tabelle di appoggio
					//---cancellazione 
					dropObj(conn, "table", "J_SOGG_CAT_UTEACQUA" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
					String sqlCreateTabTemp01=
						"create table tab_temp_rep_tarsu_001 as select distinct a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a," +
						"  a.codfisc codfisc_a, a.dt_nascita dt_nascita_a, a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, " +
						"  a.prog_es prog_es_a, a.fk_soggetto" +
						" from sit_soggetto_totale a where fk_ente_sorgente=4 and prog_es = 3";
					this.eseguiSql(conn, sqlCreateTabTemp01);
					log.debug("creata tab_temp_rep_tarsu_001 ");
					String sqlCreateTabTemp02=
						"create table tab_temp_rep_tarsu_002 as select distinct b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b, " +
						"  b.codfisc codfisc_b, b.dt_nascita dt_nascita_b, b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b, " +
						"  b.prog_es prog_es_b, b.fk_soggetto " +
						" from sit_soggetto_totale b where fk_ente_sorgente=30 and prog_es = 1";
					this.eseguiSql(conn, sqlCreateTabTemp02);
					
					log.debug("creata tab_temp_rep_tarsu_001 ");
					//creazione indici per tabelle di appoggio
					sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel ";
					this.eseguiSql(conn, sql);
					log.debug("creato idx_temp_01_fs ");
					sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
					this.eseguiSql(conn, sql);
					log.debug("creato idx_temp_02_fs ");
					//creazione tabella  J_SOGG_CAT_UTEACQUA da usare al posto della vista omonima
					sql= "create table  J_SOGG_CAT_UTEACQUA as select " +
					  " a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a, " +
					  " b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b " +
					  "  from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_soggetto=b.fk_soggetto";
					this.eseguiSql(conn, sql);
					log.debug("creata J_SOGG_CAT_UTEACQUA");
					sql="create index idx_jscu_id_dwh_a on j_sogg_cat_uteacqua (id_dwh_a) nologging noparallel";
					this.eseguiSql(conn, sql);
					log.debug("creato indice idx_jscu_id_dwh_a on j_sogg_cat_uteacqua ");
					sql="create index idx_jscu_id_dwh_b on j_sogg_cat_uteacqua (id_dwh_b) nologging noparallel";
					this.eseguiSql(conn, sql);
					log.debug("creato dx_jscu_id_dwh_b on j_sogg_cat_uteacqua ");
					//cancellazione tabelle di appoggio
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
		    		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//DROP VIEW
					dropObj(conn, "view" , "j_civ_cat_catacqua");
					//creazione tabelle di appoggio
					//---cancellazione 
					dropObj(conn, "table", "j_civ_cat_catacqua" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
					sqlCreateTabTemp01=	"create table tab_temp_rep_tarsu_001 as select distinct " +
							"  a.id_dwh id_dwh_a, a.civ_liv1 civ_liv1_a, a.civ_liv2 civ_liv2_a, a.civ_liv3 civ_liv3_a, a.rel_descr rel_descr_a, " +
							"  a.rating rating_a, a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_civico " +
							" from sit_civico_totale a where fk_ente_sorgente=4 and prog_es = 2";
					this.eseguiSql(conn, sqlCreateTabTemp01);
			
					log.debug("creata tab_temp_rep_tarsu_001 ");
					sqlCreateTabTemp02=
						"create table tab_temp_rep_tarsu_002 as select distinct " +
						"  b.id_dwh id_dwh_b, b.civ_liv1 civ_liv1_b, b.civ_liv2 civ_liv2_b, b.civ_liv3 civ_liv3_b, b.rel_descr rel_descr_b, " +
						"  b.rating rating_b, b.fk_ente_sorgente es_b, b.prog_es prog_es_b, b.fk_civico" +
						" from sit_civico_totale b where fk_ente_sorgente=30 and prog_es = 2";
					this.eseguiSql(conn, sqlCreateTabTemp02);
					
					log.debug("creata tab_temp_rep_tarsu_002 ");
					//creazione indici per tabelle di appoggio
					sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_civico) nologging noparallel ";
					this.eseguiSql(conn, sql);
					log.debug("creato idx_temp_01_fs ");
					sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_civico) nologging noparallel";
					this.eseguiSql(conn, sql);
					log.debug("creato idx_temp_02_fs ");
					//creazione tabella  j_civ_cat_catacqua da usare al posto della vista omonima
					sql= "create table  j_civ_cat_catacqua as select " +
					 " a.id_dwh_a, a.civ_liv1_a, a.civ_liv2_a, a.civ_liv3_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
					 " b.id_dwh_b, b.civ_liv1_b, b.civ_liv2_b, b.civ_liv3_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b, b.fk_civico" +
					 "  from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_civico=b.fk_civico";
					this.eseguiSql(conn, sql);
					log.debug("creata j_civ_cat_catacqua");
					sql="create index idx_jccca_id_dwh_a on j_civ_cat_catacqua (id_dwh_a) nologging noparallel";
					this.eseguiSql(conn, sql);
					log.debug("creato indice idx_jccca_id_dwh_a on j_civ_cat_catacqua ");
					sql="create index idx_jccca_id_dwh_b on j_civ_cat_catacqua (id_dwh_b) nologging noparallel";
					this.eseguiSql(conn, sql);
					log.debug("creato idx_jccca_id_dwh_b on j_civ_cat_catacqua ");
					//cancellazione tabelle di appoggio
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
				}
	        }
	        if (updateFEnel || updateFEnelCiv ) {
	        	if (isFonteAbilitata(enteID, "FORNITURE ELETTRICHE")) { 
	          		//DROP VIEW
					dropObj(conn, "view" , "j_sogg_cat_uteenel");
					//creazione tabelle di appoggio
					//---cancellazione 
					dropObj(conn, "table", "j_sogg_cat_uteenel" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
					String sqlCreateTabTemp01=
						"create table tab_temp_rep_tarsu_001 as select distinct " +
						" a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a, a.codfisc codfisc_a, a.dt_nascita dt_nascita_a, " +
						" a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_soggetto" +
						"   from sit_soggetto_totale a  where fk_ente_sorgente=4 and prog_es = 3";
					this.eseguiSql(conn, sqlCreateTabTemp01);
				
					log.debug("creata tab_temp_rep_tarsu_001 ");
					String sqlCreateTabTemp02=
						"create table tab_temp_rep_tarsu_002 as select distinct " +
						" b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b, b.codfisc codfisc_b, b.dt_nascita dt_nascita_b," +
						" b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b, b.prog_es prog_es_b, b.fk_soggetto " +
						"    from sit_soggetto_totale b where fk_ente_sorgente=10 and prog_es = 1 ";
					this.eseguiSql(conn, sqlCreateTabTemp02);
					
					log.debug("creata tab_temp_rep_tarsu_002 ");
					//creazione indici per tabelle di appoggio
					sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel ";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_01_fs ");
					sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_02_fs ");
					//creazione tabella  j_sogg_cat_uteenel da usare al posto della vista omonima
					sql= "create table  j_sogg_cat_uteenel as select " +
					  " a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
					  " b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b " +
					  "  from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_soggetto=b.fk_soggetto";
					this.eseguiSql(conn, sql);
					
					log.debug("creata j_sogg_cat_uteenel");
					sql="create index  idx_jscue_id_dwh_a on j_sogg_cat_uteenel (id_dwh_a) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato indice  idx_jscue_id_dwh_a on j_sogg_cat_uteenel ");
					sql="create index idx_jscue_id_dwh_b on j_sogg_cat_uteenel (id_dwh_b) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato index idx_jscue_id_dwh_b on j_sogg_cat_uteenel");
					//cancellazione tabelle di appoggio
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
		    		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//DROP VIEW
					dropObj(conn, "view" , "J_CIV_CAT_UTEENEL");
					//creazione tabelle di appoggio
					//---cancellazione 
					dropObj(conn, "table", "J_CIV_CAT_UTEENEL" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
					sqlCreateTabTemp01=
						"create table tab_temp_rep_tarsu_001 as select distinct " +
						" a.id_dwh id_dwh_a, a.civ_liv1 civ_liv1_a, a.civ_liv2 civ_liv2_a, a.civ_liv3 civ_liv3_a, " +
						" a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_civico" +
						"  from sit_civico_totale a where fk_ente_sorgente=4 and prog_es = 2 ";
					this.eseguiSql(conn, sqlCreateTabTemp01);
					
					log.debug("creata tab_temp_rep_tarsu_001 ");
					sqlCreateTabTemp02=
						"create table tab_temp_rep_tarsu_002 as select distinct " +
						 " b.id_dwh id_dwh_b, b.civ_liv1 civ_liv1_b, b.civ_liv2 civ_liv2_b, b.civ_liv3 civ_liv3_b," +
						 " b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b, b.prog_es prog_es_b," +
						 " b.fk_civico" +
						 "  from sit_civico_totale b where fk_ente_sorgente=10 and prog_es = 1 ";
					this.eseguiSql(conn, sqlCreateTabTemp02);
					
					log.debug("creata tab_temp_rep_tarsu_002 ");
					//creazione indici per tabelle di appoggio
					sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_civico) nologging noparallel ";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_01_fs ");
					sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_civico) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_02_fs ");
					//creazione tabella  j_civ_cat_uteenel da usare al posto della vista omonima
					sql= "create table   j_civ_cat_uteenel as select " +
					 " a.id_dwh_a, a.civ_liv1_a, a.civ_liv2_a, a.civ_liv3_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
					 " b.id_dwh_b, b.civ_liv1_b, b.civ_liv2_b, b.civ_liv3_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b, b.fk_civico" +
					 " from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_civico=b.fk_civico";
					this.eseguiSql(conn, sql);
					
					log.debug("creata j_civ_cat_uteenel");
					sql="create index idx_jccue_id_dwh_a on j_civ_cat_uteenel (id_dwh_a) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato indice idx_jccue_id_dwh_a on j_civ_cat_uteenel ");
					sql="create index idx_jccue_id_dwh_b on j_civ_cat_uteenel (id_dwh_b) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato index idx_jccue_id_dwh_b on j_civ_cat_uteenel");
					//cancellazione tabelle di appoggio
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
	        	}
	        }
	        if (updateLocatore || updateLocatari  ) {
	        	if (isFonteAbilitata(enteID, "LOCAZIONI")) { 
	           		//DROP VIEW
					dropObj(conn, "view" , "j_sogg_cat_locazioni");
					//creazione tabelle di appoggio
					//---cancellazione 
					dropObj(conn, "table", "j_sogg_cat_locazioni" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
					String sqlCreateTabTemp01=
						"create table tab_temp_rep_tarsu_001 as select distinct " +
						" a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a, a.codfisc codfisc_a, a.dt_nascita dt_nascita_a, " +
						" a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_soggetto " +
						" from sit_soggetto_totale a where fk_ente_sorgente=4 and prog_es = 3";
					this.eseguiSql(conn, sqlCreateTabTemp01);
					
					log.debug("creata tab_temp_rep_tarsu_001 ");
					String sqlCreateTabTemp02=
						"create table tab_temp_rep_tarsu_002 as select distinct " +
						"  b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b, b.codfisc codfisc_b, b.dt_nascita dt_nascita_b," +
						"  b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b, b.prog_es prog_es_b, b.fk_soggetto" +
						"  from sit_soggetto_totale b where fk_ente_sorgente=5 and prog_es = 2";
					this.eseguiSql(conn, sqlCreateTabTemp02);
				
					log.debug("creata tab_temp_rep_tarsu_002 ");
					//creazione indici per tabelle di appoggio
					sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel ";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_01_fs ");
					sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_02_fs ");
					//creazione tabella  j_sogg_cat_locazioni da usare al posto della vista omonima
					sql= "create table  j_sogg_cat_locazioni as select " +
						" a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
						" b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b" +
						" from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_soggetto=b.fk_soggetto ";
					this.eseguiSql(conn, sql);
					
					log.debug("creata j_sogg_cat_locazioni");
					sql="create index  idx_jscl_id_dwh_a on j_sogg_cat_locazioni (id_dwh_a) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato indice  idx_jscl_id_dwh_a on j_sogg_cat_locazioni ");
					sql="create index idx_jscl_id_dwh_b on j_sogg_cat_locazioni (id_dwh_b) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato index idx_jscl_id_dwh_b on j_sogg_cat_locazioni");
					//cancellazione tabelle di appoggio
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
			   	}
	        }
	        if (updateLocatariTarsu ) {
	        	if (isFonteAbilitata(enteID, "LOCAZIONI")) { 
	        		//DROP VIEW
					dropObj(conn, "view" , "j_sogg_locazioni_trib");
					//creazione tabelle di appoggio
					//---cancellazione 
					dropObj(conn, "table", "j_sogg_locazioni_trib" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
					String sqlCreateTabTemp01=
						"create table tab_temp_rep_tarsu_001 as select distinct " +
						" a.id_dwh id_dwh_a, a.cognome cognome_a, a.nome nome_a, a.codfisc codfisc_a, a.dt_nascita dt_nascita_a," +
						" a.rel_descr rel_descr_a, a.rating rating_a, a.fk_ente_sorgente es_a, a.prog_es prog_es_a, a.fk_soggetto" +
						" from sit_soggetto_totale a where fk_ente_sorgente=5 and prog_es = 2";
					this.eseguiSql(conn, sqlCreateTabTemp01);
					
					log.debug("creata tab_temp_rep_tarsu_001 ");
					String sqlCreateTabTemp02=
						"create table tab_temp_rep_tarsu_002 as select distinct " +
						"  b.id_dwh id_dwh_b, b.cognome cognome_b, b.nome nome_b, b.codfisc codfisc_b, b.dt_nascita dt_nascita_b," +
						"  b.rel_descr rel_descr_b, b.rating rating_b, b.fk_ente_sorgente es_b, b.prog_es prog_es_b, b.fk_soggetto" +
						" from sit_soggetto_totale b where fk_ente_sorgente=2 and prog_es = 4";
					this.eseguiSql(conn, sqlCreateTabTemp02);
					
					log.debug("creata tab_temp_rep_tarsu_002 ");
					//creazione indici per tabelle di appoggio
					sql="create index idx_temp_01_fs on tab_temp_rep_tarsu_001 (fk_soggetto) nologging noparallel ";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_01_fs ");
					sql="create index idx_temp_02_fs on tab_temp_rep_tarsu_002 (fk_soggetto) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato idx_temp_02_fs ");
					//creazione tabella  j_sogg_cat_locazioni da usare al posto della vista omonima
					sql= "create table j_sogg_locazioni_trib as select " +
					  " a.id_dwh_a, a.cognome_a, a.nome_a, a.codfisc_a, a.dt_nascita_a, a.rel_descr_a, a.rating_a, a.es_a, a.prog_es_a," +
					  " b.id_dwh_b, b.cognome_b, b.nome_b, b.codfisc_b, b.dt_nascita_b, b.rel_descr_b, b.rating_b, b.es_b, b.prog_es_b " +
					  " from tab_temp_rep_tarsu_001 a, tab_temp_rep_tarsu_002 b where a.fk_soggetto=b.fk_soggetto ";
					this.eseguiSql(conn, sql);
					
					log.debug("creata j_sogg_locazioni_trib");
					sql="create index idx_jslt_id_dwh_a on j_sogg_locazioni_trib (id_dwh_a) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato indice  idx_jscl_id_dwh_a on j_sogg_cat_locazioni ");
					sql="create index idx_jslt_id_dwh_b on j_sogg_locazioni_trib (id_dwh_b) nologging noparallel";
					this.eseguiSql(conn, sql);
					
					log.debug("creato index idx_jslt_id_dwh_b on j_sogg_locazioni_trib");
					//cancellazione tabelle di appoggio
					dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
					dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
			   	}
	        }
	        
	        
	        //INIZIO VALORIZZAZIONE COLONNE PER DIAGNOSTICHE
			if (insertSitRepTarsu){
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO CHIAMATA PROC PREPARA_SIT_REP_TARSU");
				sql = "{call PROGETTO_TARSU.prepara_sit_rep_tarsu(?)}";
				cstmt = conn.prepareCall(sql);	
				cstmt.setString(1, enteID);
	            cstmt.execute();
	            log.debug("Caricamento Sit_Rep_Tarsu - FINE CHIAMATA PROC PREPARA_SIT_REP_TARSU");	
			}
			
			if (updateTarsuCat){
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB SUP_TARSU_CAT");
				sql = "update sit_rep_tarsu set sup_tarsu_cat = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.sup_tarsu_cat()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB SUP_TARSU_CAT");	
			}
			if (updateUiuCiv){
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB UIU_CIV");
				sql = "update sit_rep_tarsu set uiu_civ = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.uiu_civ()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB UIU_CIV");	
			}
			if (updateRes) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB RES");
				
				sql = "update sit_rep_tarsu set res = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.res()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB RES");	
			}
			if (updateResCiv) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB RES_CIV");
				sql = "update sit_rep_tarsu set res_civ = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.res_civ()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB RES_CIV");	
			}
			if (updatePropPart){
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB PROP_PART");
				sql = "update sit_rep_tarsu set prop_part = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.prop_part()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB PROP_PART");	
			}
			if (updatePropUiu){
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB PROP_UIU");
				sql = "update sit_rep_tarsu set prop_uiu = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.prop_uiu()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB PROP_UIU");	
			}
			if (updateTarsu) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB TARSU");
				sql = "update sit_rep_tarsu set tarsu = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.tarsu()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB TARSU");	
			}
			if (updateTarsuFam) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB TARSU_FAM");
				sql = "update sit_rep_tarsu set tarsu_fam = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.tarsu_fam()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB TARSU_FAM");	
			}
			if (updateTarsuTit) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB TARSU_TIT");
				sql = "update sit_rep_tarsu set tarsu_tit = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.tarsu_tit()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB TARSU_TIT");	
			}
			if (updateFAcq) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB F_ACQ");
				if (isFonteAbilitata(enteID, "ACQUA")) {
					sql = "update sit_rep_tarsu set f_acq = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.f_acq()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB F_ACQ");	
			}
			if (updateFAcqCiv) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB F_ACQ_CIV");
				if (isFonteAbilitata(enteID, "ACQUA")) {
					sql = "update sit_rep_tarsu set f_acq_civ = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.f_acq_civ()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB F_ACQ_CIV");	
			}
			if (updateFEnel) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB F_ENEL");
				if (isFonteAbilitata(enteID, "FORNITURE ELETTRICHE")) {
					sql = "update sit_rep_tarsu set f_enel = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.f_enel()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB F_ENEL");	
			}
			if (updateFEnelCiv) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB F_ENEL_CIV");
				if (isFonteAbilitata(enteID, "FORNITURE ELETTRICHE")) {
					sql = "update sit_rep_tarsu set f_enel_civ = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.f_enel_civ()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB F_ENEL_CIV");	
			}
			if (updateLocatore) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB LOCATORE");
				if (isFonteAbilitata(enteID, "LOCAZIONI")) {
					sql = "update sit_rep_tarsu set locatore = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.locatore()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB F_ENEL_CIV");	
			}
			if (updateLocatari) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB LOCATARI");
				if (isFonteAbilitata(enteID, "LOCAZIONI")) {
					sql = "update sit_rep_tarsu set locatari = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.locatari()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB LOCATARI");	
			}
			if (updateLocatariTarsu) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB LOCATARI_TARSU");
				if (isFonteAbilitata(enteID, "LOCAZIONI")) {
					sql = "update sit_rep_tarsu set locatari_tarsu = null";
					this.eseguiSql(conn, sql);
					
					sql = "{call PROGETTO_TARSU.locatari_tarsu()}";
					cstmt = conn.prepareCall(sql);	
					cstmt.execute();	
				}
				
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB LOCATARI_TARSU");	
			}
			
		
			
			if (updateAltriResCiv) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB ALTRI_RES_CIV");
				sql = "update sit_rep_tarsu set altri_res_civ = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.altri_res_civ()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB ALTRI_RES_CIV");	
			}
			if (updateAltriResTarsu) {
				log.debug("Caricamento Sit_Rep_Tarsu - INIZIO ELAB ALTRI_RES_TARSU");
				sql = "update sit_rep_tarsu set altri_res_tarsu = null";
				this.eseguiSql(conn, sql);
				
				sql = "{call PROGETTO_TARSU.altri_res_tarsu()}";
				cstmt = conn.prepareCall(sql);	
				cstmt.execute();
				log.debug("Caricamento Sit_Rep_Tarsu - FINE ELAB ALTRI_RES_TARSU");	
			}
			
				
			
			log.debug("ESECUZIONE REGOLA CARICAMENTO SIT_REP_TARSU: ok");			
			retAck = new ApplicationAck("Caricamento SIT_REP_TARSU completato correttamente.");
		}catch (SQLException sqle) {
			log.error("ESECUZIONE REGOLA CARICAMENTO SIT_REP_TARSU - ERRORE SQL ", sqle);
			retAck =  new ErrorAck("Errore sql caricamento SIT_REP_TARSU:" + sqle);
		}catch (Exception e) {
			log.debug("ESECUZIONE REGOLA CARICAMENTO SIT_REP_TARSU: ko");
			retAck =  new ErrorAck("Errore caricamento SIT_REP_TARSU:"+ e);
		}
		finally {
			log.debug("INIZIO OPERAZIONI FINALI");
			
			String condSoggAnaA = "ES_A = 1 AND PROG_ES_A = 1";
			String condSoggAnaB = "ES_B = 1 AND PROG_ES_B = 1";
			
			String condSoggCatA = "ES_A = 4 AND PROG_ES_A = 3";
			
			String condCivCatA = "ES_A = 4 AND PROG_ES_A = 2";
			
			String condSoggTarB ="ES_B = 2 AND PROG_ES_B = 4";
			
			String condCivAcquaUtentiB = "ES_B = 30 AND PROG_ES_B = 1";
			String condCivAcquaUtenzeB = "ES_B = 30 AND PROG_ES_B = 2";
			
			String condSoggLocA="ES_A = 5 AND PROG_ES_A = 2";
			String condSoggLocB="ES_B = 5 AND PROG_ES_B = 2";
			
			try {
				dropObj(conn, "table", "tab_temp_rep_tarsu_001" );
				dropObj(conn, "table", "tab_temp_rep_tarsu_002" );
				//cancellazione tabelle di supporto
				 if (updateRes || updateResCiv || updateTarsuFam) {
					 dropObj(conn, "table", "j_sogg_cat_ana");
				 //ricreo la vista 	 
					 String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_CAT_ANA  ( " +
						   " ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A, " +
	 					   " ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B  " +
						   ") AS SELECT " +
						   " ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A, " +
						   " ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B " +
						   " FROM SIT_MATRICE_SOGGETTI_T m WHERE "+ condSoggCatA +" AND "+condSoggAnaB;
					 eseguiSql(conn,sql);
					 log.debug("Eseguita create view j_sogg_cat_ana");	 
				 }
				 if (updateResCiv || updateAltriResCiv || updateAltriResTarsu ) {
					 dropObj(conn, "table", "j_civ_cat_ana" );
					 //ricreo la vista 	 
					 String sql = "CREATE OR REPLACE FORCE VIEW J_CIV_CAT_ANA ( " +
							"  ID_DWH_A, CIV_LIV1_A, CIV_LIV2_A, CIV_LIV3_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A, " +
							"  ID_DWH_B, CIV_LIV1_B, CIV_LIV2_B, CIV_LIV3_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B, FK_CIVICO " +
							") AS   SELECT " + 
							"  ID_DWH_A, CIV_LIV1_A, CIV_LIV2_A, CIV_LIV3_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A, " +
							"  ID_DWH_B, CIV_LIV1_B, CIV_LIV2_B, CIV_LIV3_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B, M.FK_CIVICO " +
							"    FROM SIT_MATRICE_CIVICI_T m WHERE " + condCivCatA +" AND "+condSoggAnaB;
					 eseguiSql(conn,sql);
					 log.debug("Eseguita create view J_CIV_CAT_ANA");	 
				 }
				 if (updateTarsu) {
					 //DROP table
					 dropObj(conn, "table" , "j_sogg_cat_trib");
					 //ricreo la vista 	 
					 String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_CAT_TRIB (" +
					 		"  ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
					 		"  ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
					 		" )	 AS   SELECT " +
					 		"  ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A, " +
					 		"  ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
					 		"     FROM SIT_MATRICE_SOGGETTI_T m WHERE "+condSoggCatA+" AND "+condSoggTarB;
					 eseguiSql(conn,sql);
					 log.debug("Eseguita create view j_sogg_cat_trib");	
				 }
				 if (updateTarsuFam || updateAltriResTarsu) {
					//DROP table
					 dropObj(conn, "table" , "j_sogg_ana_trib");
					 //ricreo la vista 	 
					 String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_ANA_TRIB (" +
					 		" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
					 		" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
					 		" )	AS SELECT " +
					 		" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
					 		" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
					 		"    FROM SIT_MATRICE_SOGGETTI_T m WHERE "+condSoggAnaA+" AND " + condSoggTarB;
					 eseguiSql(conn,sql);
					 log.debug("Eseguita create view j_sogg_ana_trib");	
				 }
				 if (updateFAcq || updateFAcqCiv) {
					 if (isFonteAbilitata(enteID, "ACQUA NEW")) { 
						//DROP table
						 dropObj(conn, "table" , "J_SOGG_CAT_UTEACQUA");
						 //ricreo la vista 	 
						 String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_CAT_UTEACQUA (" +
						 		" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
						 		" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
						 		" ) AS SELECT " +
						 		" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
						 		" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
						 		"   FROM SIT_MATRICE_SOGGETTI_T m WHERE "+condSoggCatA+" AND "+condCivAcquaUtentiB ;
						 eseguiSql(conn,sql);
						 log.debug("Eseguita create view J_SOGG_CAT_UTEACQUA");
						 //////////////////////////////////////////////////////////////////
						 //DROP table
						 dropObj(conn, "table" , "j_civ_cat_catacqua");
						 log.debug("Eseguita drop table j_civ_cat_catacqua");
						 //ricreo la vista 	 
						 sql = "CREATE OR REPLACE FORCE VIEW j_civ_cat_catacqua ( " +
						 	" ID_DWH_A, CIV_LIV1_A, CIV_LIV2_A, CIV_LIV3_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
						 	" ID_DWH_B, CIV_LIV1_B, CIV_LIV2_B, CIV_LIV3_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
						 	" )	AS  SELECT " +
						 	" ID_DWH_A, CIV_LIV1_A, CIV_LIV2_A, CIV_LIV3_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
						 	" ID_DWH_B, CIV_LIV1_B, CIV_LIV2_B, CIV_LIV3_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
						 	"  FROM sit_matrice_civici_t m WHERE "+condCivCatA+" AND "+condCivAcquaUtenzeB;
						 eseguiSql(conn,sql);
						 log.debug("Eseguita create view j_civ_cat_catacqua");
					}	 
					 if (updateFEnel || updateFEnelCiv ) {
				       	if (isFonteAbilitata(enteID, "FORNITURE ELETTRICHE")) { 
							//DROP table
							dropObj(conn, "table" , "j_sogg_cat_uteenel");
							//ricreo la vista 	 
							String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_CAT_UTEENEL (" +
							 " ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							 " ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B " +
							 " ) AS SELECT " +
							 " ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							 " ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
							 "  FROM sit_matrice_soggetti_t m WHERE "+condSoggCatA+" AND es_b = 10 AND prog_es_b = 1";
							eseguiSql(conn,sql);
							log.debug("Eseguita create view j_sogg_cat_uteenel");
							////////////////////////////////////////////////////////////////////////
							//DROP TABLE
							dropObj(conn, "table" , "J_CIV_CAT_UTEENEL");
							log.debug("Eseguita drop table J_CIV_CAT_UTEENEL");
							//ricreo la vista 	 
							sql = "CREATE OR REPLACE FORCE VIEW J_CIV_CAT_UTEENEL (" +
							" ID_DWH_A, CIV_LIV1_A, CIV_LIV2_A, CIV_LIV3_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							" ID_DWH_B, CIV_LIV1_B, CIV_LIV2_B, CIV_LIV3_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B " +
							" ) AS SELECT " +
							" ID_DWH_A, CIV_LIV1_A, CIV_LIV2_A, CIV_LIV3_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							" ID_DWH_B, CIV_LIV1_B, CIV_LIV2_B, CIV_LIV3_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B " +
							"  FROM SIT_MATRICE_CIVICI_T m WHERE "+condCivCatA+" AND ES_B = 10 AND PROG_ES_B = 2";
							eseguiSql(conn,sql);
							log.debug("Eseguita create view j_civ_cat_uteenel");
				        }
					 }
					 if (updateLocatore || updateLocatari  ) {
			        	if (isFonteAbilitata(enteID, "LOCAZIONI")) { 
			        		//DROP table
							dropObj(conn, "table" , "j_sogg_cat_locazioni");
							//ricreo la vista 	 
							String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_CAT_LOCAZIONI (" +
							" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B " +
							" ) AS SELECT " +
							" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A, " +
							" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
							"  FROM SIT_MATRICE_SOGGETTI_T m WHERE "+condSoggCatA+" AND "+condSoggLocB ;
							eseguiSql(conn,sql);
							log.debug("Eseguita create view j_sogg_cat_locazioni");
			        	}
					}  	
					if (updateLocatariTarsu ) {
				       	if (isFonteAbilitata(enteID, "LOCAZIONI")) { 
				       		//DROP table
							dropObj(conn, "table" , "j_sogg_locazioni_trib");
							//ricreo la vista 	 
							String sql = "CREATE OR REPLACE FORCE VIEW J_SOGG_LOCAZIONI_TRIB (" +
							" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B " +
							" ) AS SELECT " +
							" ID_DWH_A, COGNOME_A, NOME_A, CODFISC_A, DT_NASCITA_A, REL_DESCR_A, RATING_A, ES_A, PROG_ES_A," +
							" ID_DWH_B, COGNOME_B, NOME_B, CODFISC_B, DT_NASCITA_B, REL_DESCR_B, RATING_B, ES_B, PROG_ES_B" +
							"   FROM SIT_MATRICE_SOGGETTI_T m  WHERE "+ condSoggLocA + " AND "+condSoggTarB;
							eseguiSql(conn,sql);
							log.debug("Eseguita create view j_sogg_locazioni_trib");
				       	}
					}   	
 			    }
				
				DbUtils.close(cstmt);
			}
			catch (SQLException sqle) {
				log.error("ERRORE CHIUSURA OGGETTI SQL",sqle);
			}
			
				
		}
		return retAck;
	}

	/*private void getJellyParam(Context ctx) throws Exception {
		try {
			int index = 1;
			String par1 = getJellyParam(ctx, index++, "AGGIORNA_COL1");
			String par2 = getJellyParam(ctx, index++, "AGGIORNA_COL2");
			log.debug("PAR1: " + par1);
			log.debug("PAR2: " + par2);
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
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
		return variabile;
		
	}
*/	
	
	private void eseguiSql(Connection conn, String sql) throws SQLException{
		PreparedStatement pst = null;
		
		try{
		
			log.debug("SQL["+sql+"]");
			conn.prepareStatement(sql).execute();
		}catch(SQLException e){
	    	throw e;
	    }finally{
	   		DbUtils.close(pst);
	    }
	    
	}
	
	private boolean isFonteAbilitata(String enteID, String fonte) {
		boolean fonteAbilitata =false;
		ComuneService comuneSer = (ComuneService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
		AmFonteComune am= comuneSer.getFonteComuneByComuneCodiceFonte(enteID, fonte);
		if (am==null)
			fonteAbilitata = false;
		else
			fonteAbilitata= true;
		log.debug("ENTE ID / FONTE " + enteID + "/" +fonte + " abilitata: " + fonteAbilitata);
		return fonteAbilitata;	
	}
	
	private void dropObj(Connection conn, String obj, String  nomeObj ) {
		try {
			String sqlDrop ="DROP " + obj + " " + nomeObj;
			conn.prepareStatement(sqlDrop).execute();
			log.debug("eseguita drop " + obj + " " + nomeObj);
		}catch(SQLException sqle) {
			log.info("Eccezione in drop " + obj + " " + nomeObj + "-" + sqle.getMessage());
		}
	}
}
