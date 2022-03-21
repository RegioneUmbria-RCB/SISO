package it.webred.rulengine.diagnostics;

import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.catasto.CatastoService;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.indice.civico.CivicoService;
import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.diagnostics.bean.CivicoBean;
import it.webred.rulengine.diagnostics.bean.DatiUiBean;
import it.webred.rulengine.diagnostics.bean.FabbricatiUiuTarsuBean;
import it.webred.rulengine.diagnostics.bean.SoggettoLocazioni;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.utils.ChkDatiUI;

import it.webred.rulengine.type.def.DeclarativeType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

public class FabbricatiUiuTarsu extends ElaboraDiagnosticsNonStandard {
	Connection conn;
	
	//Estraggo i palazzi a catasto, con il numero di uiu e di oggetti tarsu associati (ricercati per parametri catastali)
	private String SQL_FABBRICATI_FP = "select fabbricati.sezione, fabbricati.foglio, fabbricati.particella, num_uiu, NVL(num_tarsu,'0') num_tarsu_fp from ( " +
			"select sezione_censuaria sezione, LPAD(foglio,4,'0') foglio, particella, count(*) num_uiu from sitiuiu u, siticomu c "+
			"where (c.cod_nazionale = ? or c.codi_fisc_luna = ? ) "+
			"AND u.cod_nazionale = c.codi_fisc_luna  "+
			"and u.data_fine_val = TO_DATE('31/12/9999','dd/MM/yyyy')  "+
			"group by sezione_censuaria, foglio, particella ) fabbricati left join   "+
			"(select NVL(sez,' ') sezione, LPAD(foglio,4,'0') foglio, LPAD(numero,5,'0') particella, count(*) num_tarsu   "+
			"from sit_t_tar_oggetto tar where foglio is not null and dat_fin is null  "+
			"group by NVL(sez,' '), LPAD(foglio,4,'0') , LPAD(numero,5,'0') ) tarsu  "+
			"ON (fabbricati.sezione=tarsu.sezione and fabbricati.foglio = tarsu.foglio and fabbricati.particella = tarsu.particella)  "+
			"order by sezione,foglio, particella  ";
	
	private String SQL_IND_CATASTALE="SELECT DISTINCT tot.fk_civico, indirizzo, civico from " +
			"(select id.*, (ID.ID_IMM || '|' || ID.PROGRESSIVO || '|' || ID.SEQ || '|' || ID.CODI_FISC_LUNA || '|' || ID.SEZIONE) id_dwh, " +
			"t.descr || ' ' || ind.ind  indirizzo, remove_lead_zero (ind.civ1) AS civico " +
			"FROM load_cat_uiu_id ID, siticomu c, load_cat_uiu_ind ind, cat_d_toponimi t  " +
			"WHERE (c.cod_nazionale = ? or c.codi_fisc_luna = ?)  " +
			"AND ID.codi_fisc_luna = c.codi_fisc_luna  " +
			"AND ID.sez = c.sezione_censuaria  " +
			"AND ID.foglio = LPAD (?, 4, '0')  " +
			"AND ID.mappale = LPAD (?, 5, '0')  " +
			"AND ind.codi_fisc_luna = c.codi_fisc_luna " +
			"AND ind.sezione = ID.sezione  " +
			"AND ind.id_imm = ID.id_imm  " +
			"AND ind.progressivo = ID.progressivo  " +
			"AND t.pk_id = ind.toponimo " +
			"AND ind.SEQ = (SELECT MAX (SEQ) FROM load_cat_uiu_ind IND2 " +
			"WHERE IND2.ID_IMM = ind.ID_IMM  AND IND2.CODI_FISC_LUNA = ind.CODI_FISC_LUNA AND IND2.PROGRESSIVO = ind.PROGRESSIVO) " +
			"AND id.SEQ = (SELECT MAX (SEQ) FROM load_cat_uiu_id LID2 " +
			"WHERE LID2.ID_IMM = id.ID_IMM  AND LID2.CODI_FISC_LUNA = id.CODI_FISC_LUNA  AND LID2.PROGRESSIVO = id.PROGRESSIVO " +
			"and lid2.foglio = id.foglio and lid2.mappale = id.mappale and lid2.sub = id.sub)  " +
			"AND ind.PROGRESSIVO = (SELECT MAX (PROGRESSIVO) FROM load_cat_uiu_ind IND2 " +
			"WHERE IND2.ID_IMM = ind.ID_IMM AND IND2.CODI_FISC_LUNA = ind.CODI_FISC_LUNA) " +
			"AND ID.PROGRESSIVO = (SELECT MAX(PROGRESSIVO) FROM load_cat_uiu_id LID2 WHERE LID2.ID_IMM = ID.ID_IMM AND LID2.CODI_FISC_LUNA = ID.CODI_FISC_LUNA)) " +
			"indirizzi left join sit_civico_totale tot " +
			"ON ( TOT.FK_ENTE_SORGENTE = '4' and TOT.PROG_ES = '4' and indirizzi.id_dwh = tot.id_dwh ) " ;
	
	private String SQL_TARSU_CIV = "select count(distinct icio.id) num_tarsu_civ " +
			"from SIT_T_TAR_OGGETTO icio INNER JOIN SIT_T_TAR_VIA iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT ), sit_civico_totale tar "+
			"where icio.foglio is null and icio.numero is null and icio.dat_fin is null " +
			"and iciv.ID || '|' || NVL(icio.NUM_CIV, '') || '|' || NVL(icio.ESP_CIV, '') = tar.id_dwh "+ 
			"and TAR.FK_ENTE_SORGENTE = '2' and TAR.PROG_ES = '3' and tar.fk_civico= ? "; 
			
	
	private String enteID;
	
	public FabbricatiUiuTarsu() {
		super();
		
	}

	public FabbricatiUiuTarsu(Connection connPar, Context ctxPar, List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
	}

	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig, long idTestata) throws Exception {
		log.info("[ElaborazioneNonStandard] - Invoke class FabbricatiUiuTarsu ");
		enteID= this.getCodBelfioreEnte();
		conn= this.getConn();
		PreparedStatement pstFP =null; 
		PreparedStatement pstInd = null;
		PreparedStatement pstCiv = null;
		
		ResultSet rs =null; 
	
		List<FabbricatiUiuTarsuBean> lista = new ArrayList<FabbricatiUiuTarsuBean>();
		
		try {
			
			pstFP = conn.prepareStatement(SQL_FABBRICATI_FP);
			pstInd = conn.prepareStatement(SQL_IND_CATASTALE);
			pstCiv = conn.prepareStatement(SQL_TARSU_CIV);
			
			pstFP.setString(1, enteID);
			pstFP.setString(2, enteID);
			rs = pstFP.executeQuery();
			while(rs.next()){
				FabbricatiUiuTarsuBean fabBean = valFabBean(rs);
				lista.add(fabBean);
			}
			
			DbUtils.close(rs);
			DbUtils.close(pstFP);
			
			//Recupero l'indirizzo
			int numElaborati = 0;
			for(FabbricatiUiuTarsuBean f : lista){
				String message = "";
				
				List<Object[]> indirizzi = this.getListaIndirizzi(pstInd, f);
		
				if(indirizzi.size()==1){
					f.setIndirizzo((String)indirizzi.get(0)[1]);
					f.setCivico((String)indirizzi.get(0)[2]);
				}else if (indirizzi.size()> 1){
					//Concateno gli indirizzi per inserirli in un campo unico
					message +="Sono associati più indirizzi al fabbricato.";
				}else{
					message +="Nessun indirizzo associato al fabbricato.";
				}
				
				String indConcat = "";
				for(Object[] i : indirizzi){
					String civico = (String)i[2];
					String ind = (String)i[1] + (civico!=null ? ","+ civico : "");
					
					BigDecimal fkCivico = (BigDecimal) i[0];
					if(fkCivico!=null){
						int count = this.getNumOggTarsuAlCivico(pstCiv, fkCivico, f) + f.getNumTarCiv();
						f.setNumTarCiv(count);
					}else{
						message += "Il civico ["+ind+"] non è correlato";
					}
					indConcat += ind +"|";
					
				}
				
				if(f.getIndirizzo()==null && f.getCivico()==null)
					f.setIndirizziConcat(indConcat);
				
				
				f.setNote(message);
				
				
				//Calcolato il numero di oggetti tarsu, verifico se il fabbricato ha un numero di oggetti tarsu < delle uiu e lo inserisco nella lista finale.
				int sumTarsu = f.getNumTarFP() + f.getNumTarCiv();
				if(sumTarsu < f.getNumUiu()){
					//log.info("SumTarsu: " + sumTarsu + " < NumUiu: " + f.getNumUiu());
					insertDati(idTestata, f);
				}else{
					//log.info("SumTarsu: " + sumTarsu + " > NumUiu: " + f.getNumUiu());
				
					String s = "ESCLUSO (" + (String)f.getFoglio() + "','" + (String)f.getParticella() + "','"  + f.getIndirizzo() + "','"
							+ (String)f.getCivico()  +"'," + (Integer)f.getNumUiu() + ","+ (Integer)f.getNumTarFP()+ ","+ (Integer)f.getNumTarCiv()+",'"+ (String) f.getIndirizziConcat()+"','"+ (String)f.getNote()+"')";
					//log.info(s);	
				}
				
				numElaborati++;
				
				if(numElaborati%100 == 0)
					conn.commit();
				
			}
			
			log.info("Diagnostica - Palazzi con numero di oggetti tarsu < del num. di uiu: elaborati " +numElaborati +" fabbricati.");
			
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e, e);
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pstFP);
			DbUtils.close(pstInd);
			DbUtils.close(pstCiv);
		}
		
		super.ElaborazioneNonStandard(diaConfig, idTestata);
	}
	
	private List<Object[]> getListaIndirizzi(PreparedStatement pstInd,  FabbricatiUiuTarsuBean f) throws SQLException{
		
		List<Object[]> indirizzi = new ArrayList<Object[]>();
		
		ResultSet rs=null;
		int numTarsuCiv = 0;
		try{
			
			pstInd.setString(1, enteID);
			pstInd.setString(2, enteID);
			pstInd.setString(3, f.getFoglio());
			pstInd.setString(4, f.getParticella());
			
			rs = pstInd.executeQuery();
			while(rs.next()){
				Object[] o = new Object[3];
				o[0] = rs.getBigDecimal("FK_CIVICO");
				o[1] = rs.getString("INDIRIZZO");
				o[2] = rs.getString("CIVICO");
			
				indirizzi.add(o);
			}
			
			
		}catch(SQLException e){
			log.error("ERRORE SQL IN FASE DI ESTRAZIONE INDIRIZZI CATASTALI: " + f.getFoglio() +" "+f.getParticella() , e);
			throw e;
		}finally{
			DbUtils.close(rs);
		}
		
		return indirizzi;
	}
	
	private int getNumOggTarsuAlCivico(PreparedStatement pstCiv, BigDecimal fkCivico, FabbricatiUiuTarsuBean f) throws SQLException{
		ResultSet rs=null;
		int numTarsuCiv = 0;
		try{
			pstCiv.setBigDecimal(1, fkCivico);
			//pstCiv.setString(2, f.getFoglio());
			//pstCiv.setString(3, f.getParticella());
			
			rs = pstCiv.executeQuery();
			
			if(rs.next())
				numTarsuCiv = rs.getInt("NUM_TARSU_CIV");
			
			
		}catch(SQLException e){
			log.error("ERRORE SQL IN FASE DI CONTEGGIO OGGETTI TARSU AL CIVICO - FK_CIVICO: " + fkCivico , e);
			throw e;
		}finally{
			DbUtils.close(rs);
		}
		
		return numTarsuCiv;
	}
	
	
	
	private FabbricatiUiuTarsuBean valFabBean(ResultSet rs) throws SQLException{
		FabbricatiUiuTarsuBean bean = new FabbricatiUiuTarsuBean();
		try {
			bean.setSezione(rs.getString("SEZIONE"));
			bean.setFoglio(rs.getString("FOGLIO"));
			bean.setParticella(rs.getString("PARTICELLA"));
			bean.setNumUiu(rs.getInt("NUM_UIU"));
			bean.setNumTarFP(rs.getInt("NUM_TARSU_FP"));
		}
		catch(SQLException sqle) {
			log.error("ERRORE SQL IN FASE DI ACQUISIZIONE DATI FABBRICATO BASE DAL ResultSet ", sqle);
		}
	
		return bean;
	}

	private void insertDati(long idTestata, FabbricatiUiuTarsuBean f) throws SQLException{
		Statement st=null; Statement stNextVal=null; ResultSet rsNextVal = null;
		String sqlIns ="";
		String sqlNextVal="select SEQ_DIA_DETTAGLIO_D_CFR_CTAR.NEXTVAL AS ID FROM DUAL";
		try {
			stNextVal= conn.createStatement();
			rsNextVal = stNextVal.executeQuery(sqlNextVal);
			rsNextVal.next();
			Long id = rsNextVal.getLong("ID");
			
			String sezione = f.getSezione()!=null ? "'"+f.getSezione()+"'" : null;
			String foglio = f.getFoglio()!=null ? "'"+f.getFoglio()+"'" : null;
			String particella = f.getParticella()!=null ? "'"+f.getParticella()+"'" : null;
			String indirizzo = f.getIndirizzo()!=null ? "'"+f.getIndirizzo()+"'" : null;
			String civico = f.getCivico()!=null ? "'"+f.getCivico()+"'" : null;
			String indirizziConcat = f.getIndirizziConcat()!=null ? "'"+f.getIndirizziConcat()+"'" : null;
			String note = f.getNote()!=null ? "'"+f.getNote()+"'" : null;
			
			sqlIns = "INSERT INTO DIA_DETTAGLIO_D_CFR_CTAR VALUES ("
				+ id +"," + idTestata + ","+sezione+ ","+foglio+","+particella+","+indirizzo+","+civico+"," + (Integer)f.getNumUiu() + ","+ (Integer)f.getNumTarFP()+ "," +
						(Integer)f.getNumTarCiv()+","+ indirizziConcat+","+note+")";
			//log.info("INSERIMENTO DATI -->SQL:" + sqlIns);	
			st = conn.createStatement();
			st.execute(sqlIns);
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI INSERIMENTO RIGA - SQL: " + sqlIns, e);
			throw e;
		}finally {
			DbUtils.close(st);
			DbUtils.close(rsNextVal);
			DbUtils.close(stNextVal);
		}
	}
	
	
	
}


