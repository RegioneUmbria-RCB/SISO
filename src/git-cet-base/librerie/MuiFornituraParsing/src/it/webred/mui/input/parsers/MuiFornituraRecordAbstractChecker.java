package it.webred.mui.input.parsers;

import it.webred.mui.hibernate.HibernateUtil;
import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.input.MuiFornituraRecordChecker;
import it.webred.mui.model.CodiceErroreImport;
import it.webred.mui.model.MiDupImportLog;
import it.webred.mui.model.MiDupNotaTras;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

public abstract class MuiFornituraRecordAbstractChecker implements
		MuiFornituraRecordChecker {
	MiDupNotaTras _miDupNotaTras = null;
	Long _idNota = null;

	public Long getIdNota() {
		return _idNota;
	}


	public void setIdNota(Long idNota) {
		this._idNota = idNota;
	}

	public boolean isInteger(String val,boolean skipEmpty) {
		if(skipEmpty && (isEmpty(val))){
			return true;
		}
		else{
			try {
				new Integer(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}


	public boolean isEmpty(String val) {
		return val == null || val.trim().equals("");
	}

	public boolean isDate(String val) {
		return isDate(val,false);
	}

	public boolean isDate(String val,boolean skipEmpty) {
		if(skipEmpty && (isEmpty(val))){
			return true;
		}
		else{
			try {
				MuiFornituraParser.dateParser.parse(val);
				return true;
			} catch (ParseException e) {
				return false;
			} catch (Throwable e) {
				return false;
			}
		}
	}

	public boolean isYear(String val) {
		return isYear(val,false);
	}

	public boolean isYear(String val,boolean skipEmpty) {
		if(skipEmpty && (isEmpty(val))){
			return true;
		}
		else{
			try {
				int k = new Integer(val);
				return  (k >= 1900 && k <= 2030);
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}

	public boolean isIn(String val,int vals[]) {
		int ival;
		try {
			ival = new Integer(val);
		} catch (NumberFormatException e) {
			return false;
		}
		for (int i = 0; i < vals.length; i++) {
			if(ival == vals[i]){
				return true;
			}
		}
		return false;
	}

	public boolean isIn(String val,String vals[]) {
		for (int i = 0; i < vals.length; i++) {
			if(vals[i] == null ) {
				if( isEmpty(val)) return true;
			}
			else{
				if(vals[i].equals(val)){
					return true;
				}
			}
		}
		return false;
	}
	protected abstract void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String code, String note);

	protected void addImportLog(
			List<MiDupImportLog> logs, String column, String code, String note){
		addImportLog(null,logs,column,code,note);
	}

	protected final void addImportLog(MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs,int recordType,String tableName, String column, String code, String note) {
		logs.add(new MiDupImportLog(miDupNotaTras.getMiDupFornitura(), String
				.valueOf(getIdNota()), miDupNotaTras,
				recordType, tableName,column, (CodiceErroreImport)HibernateUtil.currentSession().get(CodiceErroreImport.class,code) ,
				null,null,null,null,null,null,null));
	}

	protected final void checkG0000(String val,MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String note) {
		if(isEmpty(val)){
			addImportLog(miDupNotaTras,logs,column, "G0000", note);
		}
	}

	protected final void checkG0000(String val,MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column) {
		if(isEmpty(val)){
			addImportLog(miDupNotaTras,logs,column, "G0000", null);
		}
	}

	protected final void checkG0001(String val,MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String note) {
		if(!isDate(val)){
			addImportLog(miDupNotaTras,logs,column, "G0001", note);
		}
	}

	protected final void checkG0001(String val,MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column) {
		if(!isDate(val)){
			addImportLog(miDupNotaTras,logs,column, "G0001", null);
		}
	}

	protected final void checkG0002(String val,MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column, String note) {
		if(!isYear(val)){
			addImportLog(miDupNotaTras,logs,column, "G0002", note);
		}
	}

	protected final void checkG0002(String val,MiDupNotaTras miDupNotaTras,
			List<MiDupImportLog> logs, String column) {
		if(!isYear(val)){
			addImportLog(miDupNotaTras,logs,column, "G0002", null);
		}
	}

	protected abstract boolean doCheck(Object pojo,List<MiDupImportLog> logs);
	protected abstract void setLogEntity(Object pojo,MiDupImportLog log);
	
	public boolean check(Object pojo) {
		List<MiDupImportLog> logs = new ArrayList<MiDupImportLog>();
		boolean res = doCheck(pojo,logs);
		Session session = HibernateUtil.currentSession();
		for (Iterator<MiDupImportLog> iter = logs.iterator(); iter.hasNext();) {
			MiDupImportLog log = iter.next();
			setLogEntity(pojo,log);
			session.save( log);
		}
		
		return res;
	}


	public MiDupNotaTras getMiDupNotaTras() {
		return _miDupNotaTras;
	}


	public void setMiDupNotaTras(MiDupNotaTras dupNotaTras) {
		_miDupNotaTras = dupNotaTras;
	}
	
}
