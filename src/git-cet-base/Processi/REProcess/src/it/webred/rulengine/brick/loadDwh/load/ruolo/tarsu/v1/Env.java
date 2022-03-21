package it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.v1;

import it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.RTarsuConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.RTarsuEnv;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class Env<T extends RTarsuEnv> extends  RTarsuConcreteImportEnv<T> { 
	
	

	private EnvSitRuoloTarsu     envSitRuoloTarsu = new EnvSitRuoloTarsu(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTarsuImm  envSitRuoloTarsuImm = new EnvSitRuoloTarsuImm(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTarsuRata envSitRuoloTarsuRata = new EnvSitRuoloTarsuRata(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTarsuSt   envSitRuoloTarsuSt = new EnvSitRuoloTarsuSt(envImport.tableRE,null, new String[] {"RID"});
	private EnvSitRuoloTarsuStSg envSitRuoloTarsuStSg = new EnvSitRuoloTarsuStSg(envImport.tableRE,null, new String[] {"RID"});

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
		tabs.add("SIT_T_TARSU_RUOLO");
		
		return tabs;
	}
	

	public EnvSitRuoloTarsuImm getEnvSitRuoloTarsuImm() {
		return envSitRuoloTarsuImm;
	}

	public void setEnvSitRuoloTarsuImm(EnvSitRuoloTarsuImm envSitRuoloTarsuImm) {
		this.envSitRuoloTarsuImm = envSitRuoloTarsuImm;
	}

	public EnvSitRuoloTarsuRata getEnvSitRuoloTarsuRata() {
		return envSitRuoloTarsuRata;
	}

	public void setEnvSitRuoloTarsuRata(EnvSitRuoloTarsuRata envSitRuoloTarsuRata) {
		this.envSitRuoloTarsuRata = envSitRuoloTarsuRata;
	}

	public EnvSitRuoloTarsuSt getEnvSitRuoloTarsuSt() {
		return envSitRuoloTarsuSt;
	}

	public void setEnvSitRuoloTarsuSt(EnvSitRuoloTarsuSt envSitRuoloTarsuSt) {
		this.envSitRuoloTarsuSt = envSitRuoloTarsuSt;
	}

	public EnvSitRuoloTarsuStSg getEnvSitRuoloTarsuStSg() {
		return envSitRuoloTarsuStSg;
	}

	public void setEnvSitRuoloTarsuStSg(EnvSitRuoloTarsuStSg envSitRuoloTarsuStSg) {
		this.envSitRuoloTarsuStSg = envSitRuoloTarsuStSg;
	}

	public String getSQL_RE_IMMOBILE() {
		
		String sql = "";
		for(int i=1; i<=35; i++){
			
			sql+="select RID, TIPO, " +
					"CU, " +
					"'"+i+"' PROG, " +
					"ANNO , " +
					"UBIC"+i+" INDIRIZZO, " +
					"CAT"+i+" AS CAT, " +
					"MQ"+i+"  AS MQ, " +
				    "TARIFFA"+i+" AS TARIFFA," +
				    "RIDUZIONE"+i+" AS RIDUZIONE," +
				    "CAUSALE"+i+" AS CAUSALE," +
				    "IMPORTO"+i+" AS IMPORTO, "+
				    "TRIBUTO"+i+" AS TRIBUTO, DT_EXP_DATO from " + envImport.tableRE +" where re_flag_elaborato='0' ";
			
			if(i!=35)
				sql+=" union all ";
			
		}
		
		return "select * from (" +sql+") where INDIRIZZO is not null";

	}
	
	public String getSQL_RE_RATA() {
		
		//rata unica
		String sql = "select RID, anno, tipo, cu, '0' as PROG, VCAMPO1 AS VCAMPO, TOTALE AS IMPO, DATA1 AS DATA, TOTLETT AS TOTLETT, CODCLI1 AS CODCLI,  IMPBOLL1 AS IMPBOLL , DT_EXP_DATO " +
				"from re_ruolo_tarsu_1_0 where re_flag_elaborato='0' ";
		
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
					"from re_ruolo_tarsu_1_0 where re_flag_elaborato='0'";
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
					"ST_NUM_FATTURA"+i+" AS NUM_FATTURA, " +
					"ST_DATA_FATTURA"+i+" AS DATA_FATTURA, " +
				    "ST_IMPORTO"+i+" AS IMPORTO, "+
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
					"ST_SG_NUM_FATT"+i+" AS NUM_FATTURA, " +
					"ST_SG_DATA_FATT"+i+" AS DATA_FATTURA, " +
				    "ST_SG_IMPORTO"+i+" AS IMPORTO, "+
				    "ST_SG_TRIBUTO"+i+" AS TRIBUTO, " +
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

	
	public EnvSitRuoloTarsu getEnvSitRuoloTarsu() {
		return envSitRuoloTarsu;
	}

	public void setEnvSitRuoloTarsu(EnvSitRuoloTarsu envSitRuoloTarsu) {
		this.envSitRuoloTarsu = envSitRuoloTarsu;
	}

	
}