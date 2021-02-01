package it.webred.rulengine.diagnostics;

import it.webred.rulengine.Context;
import it.webred.rulengine.diagnostics.bean.DatiTitolariUI;
import it.webred.rulengine.diagnostics.bean.SoggettoLocazioni;
import it.webred.rulengine.diagnostics.bean.TitolareBean;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.utils.ChkDatiUI;

import it.webred.rulengine.type.def.DeclarativeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;


public class UiTitNonResidenti extends ElaboraDiagnosticsNonStandard {
	Connection conn;
	final String SQL_SOGGETTO_LOCAZIONI="SELECT * FROM SIT_SOGGETTO_TOTALE WHERE FK_ENTE_SORGENTE = 5 AND PROG_ES=2 AND FK_SOGGETTO IN " +
			"(SELECT FK_SOGGETTO FROM SIT_SOGGETTO_TOTALE WHERE FK_ENTE_SORGENTE=4 AND PROG_ES=3 AND ID_DWH=?)";
	final String SQL_SOGG_LOCAZ_PROPRIETARIO=" SELECT * FROM LOCAZIONI_B " +
			" WHERE UFFICIO = ? AND ANNO=? AND SERIE=? AND NUMERO=?  AND NVL(SOTTONUMERO,0) = ? AND NVL(PROG_NEGOZIO,0) =?  AND NVL(PROG_SOGGETTO, 0)=? AND TIPO_SOGGETTO='D'";
	final String SQL_CIV_LOCAZ_CORR= " SELECT * FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE = 5 AND PROG_ES=1 AND ID_DWH = ? " +
		" AND FK_CIVICO = (SELECT FK_CIVICO FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE=4 AND PROG_ES=2 AND ID_DWH IN (#))";

	public UiTitNonResidenti() {
		super();
		
	}

	public UiTitNonResidenti(Connection connPar, Context ctxPar,
			List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
		
	}

	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig,
			long idTestata) throws Exception {
		log.info("[ElaborazioneNonStandard] - Invoke class UiTitNonResidenti ");
		String enteID= this.getCodBelfioreEnte();
		conn= this.getConn();
		PreparedStatement pst =null; ResultSet rs =null; Statement st =null;
		String sqlUiu= "SELECT * FROM SITIUIU WHERE DATA_FINE_VAL =  TO_DATE('31129999','DDMMYYYY') ";
		Object parm = getParamValueByName("FOGLIO");
		Long foglioParm=null;
		String foglioStr="";
		if (parm != null && parm instanceof String) {
			try {
				foglioStr = (String)parm;
				foglioParm = new Long(foglioStr);
			}catch(NumberFormatException e) {}
		}
			
		log.info("FOGLIO PARM---> " + foglioParm);
		if (foglioParm!=null)
			sqlUiu += "AND FOGLIO = ?";
		try {
			pst = conn.prepareStatement(sqlUiu);
			if (foglioParm!=null)
				pst.setLong(1, foglioParm);
			rs = pst.executeQuery();
			while(rs.next()){
				Long foglio= rs.getLong("FOGLIO"); String particella = rs.getString("PARTICELLA"); Long unimm = rs.getLong("UNIMM"); String sub = rs.getString("SUB");  
				log.info("--> U.I.: " + foglio + "-" + particella + "-" + sub + "-" + unimm);
				if (unimm==null || unimm.longValue()==0) {
					log.info("Non elaboratato");
					continue;
				}
				ChkDatiUI checkUi = new ChkDatiUI(conn, foglio, particella, unimm, sub);
				DatiTitolariUI datiTitUi =checkUi.getDatiTitolariUI(new String(""));
				
				boolean unTitolareRisiede= datiTitUi.isUnTitolareRisiede();	
				boolean esisteTitolarePersonaFisica=datiTitUi.isEsisteTitolarePersonaFisica();
				if (!esisteTitolarePersonaFisica)
					continue;
				if (!unTitolareRisiede)  {
					log.info("Nessun titolare risulta residente in nessuno dei civici alla u.i."); 
					boolean titolariLocatari = false;
					for (TitolareBean tit: datiTitUi.getListaTitolari()) {
						log.info("Ricerca dati locazioni-Titolare: " + tit.toString());
						titolariLocatari = isTitolareLocatore(conn,tit.getPkidSogg()+"", datiTitUi.getListaIdCiv());
						if (titolariLocatari)
							break;
					}
					String titLocat="N";
					if (titolariLocatari)
						titLocat="S";
					if (!titolariLocatari)
						log.info("Nessun soggetto è locatario di un ui al civico");
					insertDatiUiNonRes(rs, datiTitUi.getListaTitolari(), idTestata, titLocat);
					
				}
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e, e);
		}finally {
			DbUtils.close(rs);
			DbUtils.close(st);
			DbUtils.close(pst);
		}
		
		super.ElaborazioneNonStandard(diaConfig, idTestata);
	}
	
	private boolean isTitolareLocatore(Connection conn, String idSoggCat, List<String> listaCiviciCat) throws SQLException{
		boolean retVal = false;
		List<SoggettoLocazioni> listaSogg = getListaSoggettiLocazioni(conn, idSoggCat);
		if (listaSogg!=null && listaSogg.size() >0 ) {
			for (SoggettoLocazioni sogg:listaSogg) {
				boolean isSoggLocatore = isSoggettoLocatore(conn,sogg);
				if (isSoggLocatore ) {
					retVal = isLocazioneCivicoCorrelata(conn, sogg, listaCiviciCat);
					if (retVal)
						break;
				}
			}
			
		}
		return retVal;
	}
	
	private List<SoggettoLocazioni> getListaSoggettiLocazioni(Connection conn, String idSoggCat)throws SQLException{
		log.info("REPERIMENTO CORRELAZIONI SOGGETTO-LOCAZIONI - SQL: " + SQL_SOGGETTO_LOCAZIONI);
		log.info("REPERIMENTO DATI SOGGETTO IN DEMOGRAFIA - PARMS: idSoggCat [" + idSoggCat + "]");
		List<SoggettoLocazioni> listaSogg = new ArrayList<SoggettoLocazioni>();
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_SOGGETTO_LOCAZIONI);
			pst.setString(1, idSoggCat);
			rs = pst.executeQuery();
			while (rs.next()){;
				String[] keys = rs.getString("ID_DWH").split("\\|");
				SoggettoLocazioni sogg = new SoggettoLocazioni();
				sogg.setUfficio(keys[0]);
				sogg.setAnno(keys[1]);
				sogg.setSerie(keys[2]);
				sogg.setNumero(keys[3]);
				if (keys.length>4 )
					sogg.setSottoNumero(keys[4]);
				else
					sogg.setSottoNumero("0");
				if (keys.length>5 )
					sogg.setProgNegozio(keys[5]);
				else
					sogg.setProgNegozio("0");
				if (keys.length>6 )
					sogg.setProgSoggetto(keys[6]);
				else
					sogg.setProgSoggetto("0");
				listaSogg.add(sogg);
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CORRELAZIONI SOGGETTO-LOCAZIONI - SQL: " + SQL_SOGGETTO_LOCAZIONI, e);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO DATI SOGGETTO IN DEMOGRAFIA - PARMS: idSoggCat [" + idSoggCat + "]", e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return listaSogg;
	}
	private boolean isSoggettoLocatore(Connection conn, SoggettoLocazioni sogg) throws SQLException{
		log.info("VERIFICA SOGGETTO LOCATORE - SQL: " + SQL_SOGG_LOCAZ_PROPRIETARIO);
		log.info("VERIFICA SOGGETTO LOCATORE - PARMS: UFFICIO [" + sogg.getUfficio() + "];ANNO[" + sogg.getAnno() + 
				"];SERIE[" + sogg.getSerie() + "]; numero[" + sogg.getNumero() + "]; SOTTONUMERO[" + sogg.getSottoNumero() +
				"]; PROG_NEGOZIO[" + sogg.getProgNegozio()+ "]; PROG_SOGGETTO[" + sogg.getProgSoggetto()+ "]");
		boolean retVal = false;
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_SOGG_LOCAZ_PROPRIETARIO);
			pst.setString(1, sogg.getUfficio());
			pst.setString(2, sogg.getAnno());
			pst.setString(3, sogg.getSerie());
			pst.setString(4, sogg.getNumero());
			pst.setString(5, sogg.getSottoNumero());
			pst.setString(6, sogg.getProgNegozio());
			pst.setString(7, sogg.getProgSoggetto());
			rs = pst.executeQuery();
			if (rs.next()){
				retVal=true;
			}

		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CORRELAZIONI SOGGETTO-LOCAZIONI - SQL: " + SQL_SOGG_LOCAZ_PROPRIETARIO);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO DATI SOGGETTO IN DEMOGRAFIA - PARMS: UFFICIO [" + sogg.getUfficio() + "];ANNO[" + sogg.getAnno() + 
					"];SERIE[" + sogg.getSerie() + "]; numero[" + sogg.getNumero() + "]; SOTTONUMERO[" + sogg.getSottoNumero() +
					"]; PROG_NEGOZIO[" + sogg.getProgNegozio()+ "]; PROG_SOGGETTO[" + sogg.getProgSoggetto()+ "]", e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
	}
	
	private boolean isLocazioneCivicoCorrelata(Connection conn, SoggettoLocazioni sogg, List<String> listaCiviciCat) throws SQLException{
		boolean retVal = false;
		PreparedStatement pst = null; ResultSet rs=null;
		String idDwhLocaz="";
		idDwhLocaz = sogg.getUfficio()+"|" + sogg.getAnno()+"|" +sogg.getSerie()+"|" +sogg.getNumero()+"|" ;
		idDwhLocaz += (sogg.getSottoNumero() != null)? sogg.getSottoNumero() : "";
		idDwhLocaz +="|";
		idDwhLocaz += (sogg.getProgNegozio() != null)? sogg.getProgNegozio() : ""; 
		String sqlIn = buildSqlIn(listaCiviciCat);
		String sqlLocCivCorr =SQL_CIV_LOCAZ_CORR; 
		sqlLocCivCorr = sqlLocCivCorr.replace("#", sqlIn); 
		log.info("VERIFICA SE CORRELAZIONE CIVICO -LOCAZIONE ESISTE - SQL: " + sqlLocCivCorr);
		log.info("VERIFICA SE CORRELAZIONE CIVICO -LOCAZIONE ESISTE - PARMS: idDwhLocaz [" +idDwhLocaz+ "]");
		try {
			pst = conn.prepareStatement(sqlLocCivCorr );
			pst.setString(1, idDwhLocaz);
			rs = pst.executeQuery();
			if (rs.next()){
				retVal=true;
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CORRELAZIONI CIVICI LOCAZIONI - SQL: " + SQL_CIV_LOCAZ_CORR);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CORRELAZIONI CIVICI LOCAZIONI - PARMS: idDwhLocaz [" +idDwhLocaz+ "]", e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
	}
	
	private void insertDatiUiNonRes(ResultSet rs, List<TitolareBean> listaTit, long idTestata, String titolariLocatori) throws SQLException{
		Statement st=null; Statement stNextVal=null; ResultSet rsNextVal = null;
		String titolari ="";
		int i =0;
		for (TitolareBean tit: listaTit){
			if(i != 0 && listaTit.size() > 1)
				titolari +=  ";\n" + tit.toString();
			else
				titolari = tit.toString();
			i++;
		}
		titolari= titolari.replace("'", "''");
		String classe="null";
		if (rs.getString("CLASSE") !=null)
			classe="'" + rs.getString("CLASSE")  + "'";
		String consistenza="null";
		if (rs.getObject("CONSISTENZA") != null)
			consistenza ="" + rs.getDouble("CONSISTENZA");
		String supCat="null";
		if (rs.getObject("SUP_CAT") != null)
			supCat ="" + rs.getDouble("SUP_CAT");
		String zona="null";
		if (rs.getString("ZONA") !=null)
			zona="'" + rs.getString("ZONA")  + "'";
		String microz="null";
		if (rs.getString("MICROZONA") !=null)
			microz="'" + rs.getString("MICROZONA")  + "'";
		String rendita="null";
		if (rs.getObject("RENDITA") != null)
			rendita ="" + rs.getDouble("RENDITA");
		String sqlIns ="";
		String sqlNextVal="select SEQ_DIA_DETTAGLIO_D_CTR_CAT.NEXTVAL AS ID FROM DUAL";
		try {
			stNextVal= conn.createStatement();
			rsNextVal = stNextVal.executeQuery(sqlNextVal);
			rsNextVal.next();
			Long id = rsNextVal.getLong("ID");
			sqlIns = "INSERT INTO DIA_DETTAGLIO_D_CTR_CAT02 VALUES ("
				+ id +"," + idTestata + ",'" + rs.getString("COD_NAZIONALE") + "'," + rs.getLong("FOGLIO") + ",'"  + rs.getString("PARTICELLA") + "',"
				+ rs.getLong("UNIMM") +",'" + rs.getString("CATEGORIA") + "',"+classe +","
				+ consistenza + "," + supCat+ "," + zona+ ","+ microz + ","	+ rendita + ",'" + titolari + "','" + titolariLocatori + "')" ;
			log.info("INSERIMENTO DATI -->SQL:" + sqlIns);	
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
	//Verificare se è il caso di metterlo nella superclasse
	private Object getParamValueByName(String namePar) {
		DeclarativeType param =  this.getCtx().getDeclarativeType(namePar); 
		if (param!=null && param.getValue() !=null) {
			log.info("Parametro recuperato. type [" + param.getType()+ "]; value [" + param.getValue().toString() + "]");
			return param.getValue();
		}
		else
			return null;
		
	}
	
	private String buildSqlIn(List<String> listaCivici) {
		String sqlIn="";
		int i=0;
		for (String idCivCat: listaCivici) {
			if (i==0)
				sqlIn="'" +idCivCat +"'";
			else
				sqlIn+= "," + "'" +idCivCat +"'" ;
			i++;
		}
		return sqlIn;
	}
	
	
}


