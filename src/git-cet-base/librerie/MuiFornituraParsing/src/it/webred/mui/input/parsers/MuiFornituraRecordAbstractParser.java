package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.input.MuiFornituraRecordChecker;
import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.RowField;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.skillbill.logging.Logger;

public abstract class MuiFornituraRecordAbstractParser extends MuiFornituraRecordAbstractChecker implements
		MuiFornituraRecordParser {
	private MuiFornituraRecordChecker _checker = null;
	private static Map<Class,String[]> _fieldNames=new HashMap<Class,String[]>();
	
	protected Object _record = null;

	public void reset() {
		setIdNota(null);
	}

	protected void setLogEntity(Object pojo,MiDupImportLog log){
	}

	public void setIdNota(Long idNota) {
		super.setIdNota(idNota);
		if(_checker != null ){
			_checker.setIdNota(idNota);
		}
	}

	public void setMiDupNotaTras(MiDupNotaTras dupNotaTras) {
		super.setMiDupNotaTras(dupNotaTras);
		if(_checker != null ){
			_checker.setMiDupNotaTras(dupNotaTras);
		}
	}

	
	public String dumpPojo(Object obj) {
		Class pojoClass = obj.getClass();
		StringBuffer sb = new StringBuffer();
		sb.append(pojoClass.getName());
		sb.append(" : ");
		String[] fieldNames =  getFieldNames(pojoClass);
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			sb.append(fieldName);
			sb.append(" = ");
			String fieldGetterName = "get" + fieldName.toUpperCase().charAt(0)
					+ (fieldName.length() > 1 ? fieldName.substring(1) : "");
			try {
				Method method = pojoClass.getDeclaredMethod(
						fieldGetterName, null);
				sb.append(String.valueOf(method.invoke(obj, null)));
			} catch (Exception e) {
			}
			sb.append(", ");
		}
		return sb.toString();
	}

	public void parsePojo(String record, Object pojo) {
		Class pojoClass = pojo.getClass();
		parsePojo(record, pojo, pojoClass);
	}

	public void parsePojo(String record, Object pojo, Class pojoClass) {
		RowField val;
		String[] fieldNames = getFieldNames(pojoClass);
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
//			for (int j = 0; j < fieldNames.length; j++) {
//				Logger.log().info(pojoClass.getName(),
//						"getFieldNames for class "+pojoClass.getName()+"["+j+"]"+fieldNames[j]);				
//			}
			String fieldValue;
			if ("idNota".equals(fieldName)) {
				fieldValue = String.valueOf(getIdNota());
			} else {
				val = MuiFornituraParser.getNextField(record);
				fieldValue = (val != null ? val.field : null);
				record = (val != null ? val.remaining : null);
			}
			MuiFornituraParser.setPojoProperty(pojo, fieldName, fieldValue,pojoClass);
		}
	}

	public String toString() {
		if (_record == null) {
			return super.toString();
		} else {
			return this.getClass().getName() + ":" + dumpPojo(_record);
		}
	}
	public static String[] getFieldNames(Class pojoClass) {
		
		try
		{
			// provo a ricostruire la classe senza il proxy
			if(pojoClass.getName().indexOf("$") > 0)
				pojoClass =  Class.forName(pojoClass.getName().split("\\$")[0]);
		}
		catch (ClassNotFoundException e) {
			//ci ho provato
			Logger.log().error(pojoClass.getName(),	"per la classe "+pojoClass.getName()+" non sono riuscito a levare il proxy");
		}		
		
		Class theClass =null;
		String[] res = _fieldNames.get(pojoClass);
		if (res == null) {
			String baseClassName = pojoClass.getName()+"Base";
			try {
				theClass = Class.forName(baseClassName);
				Logger.log().info(pojoClass.getName(),
						"getFieldNames using superclass "+baseClassName+" instead of "+pojoClass.getName());				
			} catch (ClassNotFoundException e) {
				theClass = pojoClass;
				Logger.log().info(pojoClass.getName(),
						"getFieldNames using "+pojoClass.getName());				
			}
			Field[] fields = theClass.getDeclaredFields();
			List<String> fieldNames= new ArrayList<String>();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getType().equals(String.class) && !fields[i].getName().endsWith("NotParsed")) {
					fieldNames.add(fields[i].getName());
				}
			}
			res = new String[fieldNames.size()];
			fieldNames.toArray(res);
			_fieldNames.put(pojoClass,res);
		} 
		return res;
	}
	public void setChecker(MuiFornituraRecordChecker checker){
		_checker  = checker;
	}
	protected void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note){
		
	}
	protected boolean doCheck(Object pojo,List<MiDupImportLog> logs) {
		return true;
	}

	public boolean check(Object pojo){
		if(_checker != null ){
			return _checker.check(pojo);
		}
		else{
			return true;
		}
		
	}
	public void store() {
		// TODO Auto-generated method stub
		System.out.println(this);
	}

	public Object getRecord() {
		return _record;
	}
}
