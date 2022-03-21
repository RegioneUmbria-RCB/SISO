package it.webred.rulengine.brick.loadDwh.load.tributi;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvStandardFiles;
import it.webred.rulengine.exception.RulEngineException;

public class TributiTipoRecordEnv<T extends TestataStandardFileBean> extends EnvStandardFiles<T> {
	
	public TributiTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("2", connectionName, ctx);
		// TODO Auto-generated constructor stub
	}
	
	public String RE_TRIBUTI_TRTRIBTARSUSOGG = getProperty("tableTRTRIBTARSUSOGG.name");
	public String RE_TRIBUTI_TRTRIBTARSUSOGG_IDX = getProperty("tableTRTRIBTARSUSOGG.idx");
	public String RE_TRIBUTI_TRTRIBTARSUDICH = getProperty("tableTRTRIBTARSUDICH.name");
	public String RE_TRIBUTI_TRTRIBTARSUDICH_IDX = getProperty("tableTRTRIBTARSUDICH.idx");
	public String RE_TRIBUTI_TRTRIBTARSUSOGGULT = getProperty("tableTRTRIBTARSUSOGGULT.name");
	public String RE_TRIBUTI_TRTRIBTARSUSOGGULT_IDX = getProperty("tableTRTRIBTARSUSOGGULT.idx");
	public String RE_TRIBUTI_TRTRIBTARSUVIA = getProperty("tableTRTRIBTARSUVIA.name");
	public String RE_TRIBUTI_TRTRIBTARSUVIA_IDX = getProperty("tableTRTRIBTARSUVIA.idx");
	public String RE_TRIBUTI_TRTRIBTARRIDUZ = getProperty("tableTRTRIBTARRIDUZ.name");
	public String RE_TRIBUTI_TRTRIBTARRIDUZ_IDX = getProperty("tableTRTRIBTARRIDUZ.idx");
	public String RE_TRIBUTI_TRTRIBICISOGG = getProperty("tableTRTRIBICISOGG.name");
	public String RE_TRIBUTI_TRTRIBICISOGG_IDX = getProperty("tableTRTRIBICISOGG.idx");
	public String RE_TRIBUTI_TRTRIBICIDICH = getProperty("tableTRTRIBICIDICH.name");
	public String RE_TRIBUTI_TRTRIBICIDICH_IDX = getProperty("tableTRTRIBICIDICH.idx");
	public String RE_TRIBUTI_TRTRIBICISOGGULT = getProperty("tableTRTRIBICISOGGULT.name");
	public String RE_TRIBUTI_TRTRIBICISOGGULT_IDX = getProperty("tableTRTRIBICISOGGULT.idx");
	public String RE_TRIBUTI_TRTRIBICIVIA = getProperty("tableTRTRIBICIVIA.name");
	public String RE_TRIBUTI_TRTRIBICIVIA_IDX = getProperty("tableTRTRIBICIVIA.idx");
	public String RE_TRIBUTI_TRTRIBICIRIDUZ = getProperty("tableTRTRIBICIRIDUZ.name");
	public String RE_TRIBUTI_TRTRIBICIRIDUZ_IDX = getProperty("tableTRTRIBICIRIDUZ.idx");
	public String RE_TRIBUTI_TRTRIBVICI = getProperty("tableTRTRIBVICI.name");
	public String RE_TRIBUTI_TRTRIBVICI_IDX = getProperty("tableTRTRIBVICI.idx");
		
	protected String createTableTRTRIBTARSUSOGG = getProperty("tableTRTRIBTARSUSOGG.create_table");
	protected String createTableTRTRIBTARSUDICH = getProperty("tableTRTRIBTARSUDICH.create_table");
	protected String createTableTRTRIBTARSUSOGGULT = getProperty("tableTRTRIBTARSUSOGGULT.create_table");
	protected String createTableTRTRIBTARSUVIA = getProperty("tableTRTRIBTARSUVIA.create_table");
	protected String createTableTRTRIBTARRIDUZ = getProperty("tableTRTRIBTARRIDUZ.create_table");
	protected String createTableTRTRIBICISOGG = getProperty("tableTRTRIBICISOGG.create_table");
	protected String createTableTRTRIBICIDICH = getProperty("tableTRTRIBICIDICH.create_table");
	protected String createTableTRTRIBICISOGGULT = getProperty("tableTRTRIBICISOGGULT.create_table");
	protected String createTableTRTRIBICIVIA = getProperty("tableTRTRIBICIVIA.create_table");
	protected String createTableTRTRIBICIRIDUZ = getProperty("tableTRTRIBICIRIDUZ.create_table");
	protected String createTableTRTRIBVICI = getProperty("tableTRTRIBVICI.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	protected String connTablesDriverClass = getProperty("conn.tables.driverClass");
	protected String connTablesConnString = getProperty("conn.tables.connString");
	protected String connTablesUserName = getProperty("conn.tables.userName");
	protected String connTablesPassword = getProperty("conn.tables.password");

	@Override
	public String getPercorsoFilesSpec() {
		// TODO Auto-generated method stub
		return dirFiles;
	}
	
}
