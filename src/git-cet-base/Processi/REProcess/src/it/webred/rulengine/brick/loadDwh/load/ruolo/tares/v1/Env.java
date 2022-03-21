package it.webred.rulengine.brick.loadDwh.load.ruolo.tares.v1;

import it.webred.rulengine.brick.loadDwh.load.ruolo.tares.RTaresConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.ruolo.tares.RTaresEnv;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class Env<T extends RTaresEnv> extends  RTaresConcreteImportEnv<T> { 
	
	private EnvSitRuoloTares     envSitRuoloTares = new EnvSitRuoloTares(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTaresImm  envSitRuoloTaresImm = new EnvSitRuoloTaresImm(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTaresRata envSitRuoloTaresRata = new EnvSitRuoloTaresRata(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTaresSt   envSitRuoloTaresSt = new EnvSitRuoloTaresSt(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTaresStSg envSitRuoloTaresStSg = new EnvSitRuoloTaresStSg(envImport.tableRE,null, new String[] {"RID"});

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableRE , null);
		
		return hm;
	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableRE , null);
		return tr;
	}

	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_T_Tares_RUOLO");
		
		return tabs;
	}
	

	public EnvSitRuoloTaresImm getEnvSitRuoloTaresImm() {
		return envSitRuoloTaresImm;
	}

	public void setEnvSitRuoloTaresImm(EnvSitRuoloTaresImm envSitRuoloTaresImm) {
		this.envSitRuoloTaresImm = envSitRuoloTaresImm;
	}

	public EnvSitRuoloTaresRata getEnvSitRuoloTaresRata() {
		return envSitRuoloTaresRata;
	}

	public void setEnvSitRuoloTaresRata(EnvSitRuoloTaresRata envSitRuoloTaresRata) {
		this.envSitRuoloTaresRata = envSitRuoloTaresRata;
	}

	public EnvSitRuoloTaresSt getEnvSitRuoloTaresSt() {
		return envSitRuoloTaresSt;
	}

	public void setEnvSitRuoloTaresSt(EnvSitRuoloTaresSt envSitRuoloTaresSt) {
		this.envSitRuoloTaresSt = envSitRuoloTaresSt;
	}

	public EnvSitRuoloTaresStSg getEnvSitRuoloTaresStSg() {
		return envSitRuoloTaresStSg;
	}

	public void setEnvSitRuoloTaresStSg(EnvSitRuoloTaresStSg envSitRuoloTaresStSg) {
		this.envSitRuoloTaresStSg = envSitRuoloTaresStSg;
	}

	public String getSQL_RE_IMMOBILE() {
		
		String sql = "";
		for(int i=1; i<=20; i++){
			
			sql+="select RID, TIPO, " +
					"CU, " +
					"'"+i+"' PROG, " +
					"ANNO , " +
					"UBIC"+i+" INDIRIZZO, " +
					"TIPO"+i+" TIPO_IMM, " +
					"CODTIP"+i+" CODTIP, " +
					"CAT"+i+" AS CAT, " +
					"CODCAT"+i+" AS CODCAT, " +
					"RIFCAT"+i+" AS RIFCAT, " +
					"MQ"+i+"  AS MQ, " +
				    "TARIFFA_QF"+i+" AS TARIFFA_QF," +
				    "TARIFFA_QV"+i+" AS TARIFFA_QV," +
				    "TARIFFA_QS"+i+" AS TARIFFA_QS," +
				    "RIDUZIONE_QF"+i+" AS RIDUZIONE_QF," +
				    "RIDUZIONE_QV"+i+" AS RIDUZIONE_QV," +
				    "RIDUZIONE_QS"+i+" AS RIDUZIONE_QS," +
				    "CAUSALE"+i+" AS CAUSALE," +
				    "IMPORTO"+i+" AS IMPORTO, "+
				    "IMP_QF"+i+" AS IMP_QF, "+
				    "IMP_QV"+i+" AS IMP_QV, "+
				    "IMP_QS"+i+" AS IMP_QS, "+
				    "TRIBUTO"+i+" AS TRIBUTO, " +
				    "substr(periodo"+i+",instr(periodo1,'dal ')+4,10) PERIODO_DA,  " +
				    "substr(periodo"+i+",instr(periodo1,' al ')+4,10) PERIODO_A, " +
				    "DT_EXP_DATO from " + envImport.tableRE +" where re_flag_elaborato='0' ";
			
			if(i!=20)
				sql+=" union all ";
			
		}
		
		return "select * from (" +sql+") where CODTIP IS not NULL or rifcat is not null or importo is not null order by rid, prog";

	}
	
	public String getSQL_RE_RATA() {
		
		//rata unica
		String sql = "select RID, anno, tipo, cu, '0' as PROG, VCAMPO1 AS VCAMPO, TOTALE AS IMPO, DATA1 AS DATA, " +
				"TOTLETT AS TOTLETT, CODCLI1 AS CODCLI,  IMPBOLL1 AS IMPBOLL , DT_EXP_DATO " +
				"from " + envImport.tableRE +"  where re_flag_elaborato='0' ";
		
		for(int i=1; i<=4; i++){
			
			int ind = i+1;
			
			sql+=" union all ";
			sql+="select RID, anno, tipo, cu, '"+i+"' as PROG, " +
					"VCAMPO"+ind+" AS VCAMPO, " +
					"IMPO"+i+" AS IMPO, " +
					"DATA"+i+" AS DATA, " +
					"TOTLETT"+i+" AS TOTLETT, " +
					"CODCLI"+ind+" AS CODCLI,  " +
					"IMPBOLL"+ind+" AS IMPBOLL, " +
					"DT_EXP_DATO " +
					"from " + envImport.tableRE +"  where re_flag_elaborato='0'";
		}
		
		return "select * from (" +sql+") where IMPO is not null";

	}
	
	public String getSQL_RE_ST() {
		
		String sql = "";
		for(int i=1; i<=5; i++){
			
			sql+="select RID, TIPO, " +
					"CU, " +
					"'"+i+"' PROG, " +
					"ANNO , " +
					"SUBSTR(ST_UBIC"+i+",8) INDIRIZZO, " +
					"ST_TIPO"+i+" TIPO_IMM, "+
					"ST_RIDUZIONE_QF"+i+" AS RIDUZIONE_QF," +
					"ST_RIDUZIONE_QV"+i+" AS RIDUZIONE_QV," +
				    "ST_RIDUZIONE_QS"+i+" AS RIDUZIONE_QS," +
				    "ST_IMPORTO"+i+" AS IMPORTO, "+
				    "ST_IMP_QF"+i+" AS IMP_QF, "+
				    "ST_IMP_QV"+i+" AS IMP_QV, "+
				    "ST_IMP_QS"+i+" AS IMP_QS, "+
					"ST_NUM_FATTURA"+i+" AS NUM_FATTURA, " +
					"ST_DATA_FATTURA"+i+" AS DATA_FATTURA, " +
				    "ST_TRIBUTO"+i+" AS TRIBUTO, DT_EXP_DATO from " + envImport.tableRE +" where re_flag_elaborato='0' ";
			
			if(i!=5)
				sql+=" union all ";
			
		}
		
		return "select * from (" +sql+") where INDIRIZZO is not null";
	}

	public String getSQL_RE_ST_SG() {
		String sql = "";
		for(int i=1; i<=5; i++){
			
			sql+="select RID, TIPO, " +
					"CU, " +
					"'"+i+"' PROG, " +
					"ANNO , " +
					"SUBSTR(ST_SG_UBIC"+i+",8) INDIRIZZO, " +
					"ST_SG_TIPO"+i+" TIPO_IMM, "+
					"ST_SG_RIDUZIONE_QF"+i+" AS RIDUZIONE_QF," +
					"ST_SG_RIDUZIONE_QV"+i+" AS RIDUZIONE_QV," +
				    "ST_SG_RIDUZIONE_QS"+i+" AS RIDUZIONE_QS," +
				    "ST_SG_IMPORTO"+i+" AS IMPORTO, "+
				    "ST_SG_IMP_QF"+i+" AS IMP_QF, "+
				    "ST_SG_IMP_QV"+i+" AS IMP_QV, "+
				    "ST_SG_IMP_QS"+i+" AS IMP_QS, "+
				    "ST_SG_TRIBUTO"+i+" AS TRIBUTO, " +
					"ST_SG_NUM_FATT"+i+" AS NUM_FATTURA, " +
					"ST_SG_DATA_FATT"+i+" AS DATA_FATTURA, " +
				    "ST_SG_NUM_NOTA"+i+" AS NUM_NOTA, " +
					"ST_SG_DATA_NOTA"+i+" AS DATA_NOTA, " +
				    "DT_EXP_DATO from " + envImport.tableRE +" where re_flag_elaborato='0' ";
			
			if(i!=5)
				sql+=" union all ";
			
		}
		
		return "select * from (" +sql+") where INDIRIZZO is not null";
	}

	public String getSQL_RUOLO() {
		return envImport.SQL_RUOLO;
	}

	
	public EnvSitRuoloTares getEnvSitRuoloTares() {
		return envSitRuoloTares;
	}

	public void setEnvSitRuoloTares(EnvSitRuoloTares envSitRuoloTares) {
		this.envSitRuoloTares = envSitRuoloTares;
	}

	
}