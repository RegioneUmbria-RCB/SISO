package it.webred.utils;

import static java.sql.Types.*;
import java.util.*;

/**
 * @author Filippo
 * classe di costanti e metodi statici (sul modello di it.webred.diogene.sqldiagram.EnumsRepository...)<p>
 * 
 */
public class MetadataConstants {
	
	/**
	 * @author Filippo
	 * Costanti che definiscono il tipo DB
	 */
	public static enum DBType
	{
		ORACLE {
			public String stringValue() {
				return "Ora";
			}
			public String stringToDisplay() {
				return "Oracle";
			}
		},
		MYSQL {
			public String stringValue() {
				return "MySql";
			}
			public String stringToDisplay() {
				return "MySql";
			}
		},
		MSSQL {
			public String stringValue() {
				return "MsSql";
			}
			public String stringToDisplay() {
				return "Ms Sql Server";
			}
		};
		//da aggiungere altri tipi DB...
		
		public abstract String stringValue();
		public abstract String stringToDisplay();
	}
	
	public static List<String> getDBDescriptions() {
		List<String> retVal = new ArrayList<String>();
		for (DBType dbt : DBType.values()) {
			retVal.add(dbt.stringToDisplay());
		}
		return orderList(retVal);
	}
	
	/**
	 * @author Filippo
	 * Costanti che rappresentano i nomi delle colonne del ResultSet restituito dal metodo getSchemas() di
	 * DatabaseMetaData
	 */
	public static enum ColumnsFromGetSchemas
	{
		IS_ONE_BASED, //nel resultset le colonne sono in base uno, se uso ordinal() evito di aggiungere 1
		TABLE_SCHEM,
		TABLE_CATALOG
	}
	
	/**
	 * @author Filippo
	 * Costanti che rappresentano i nomi delle colonne del ResultSet restituito dal metodo getTables() di
	 * DatabaseMetaData
	 */
	public static enum ColumnsFromGetTables
	{
		IS_ONE_BASED, //nel resultset le colonne sono in base uno, se uso ordinal() evito di aggiungere 1
		TABLE_CAT,
		TABLE_SCHEM,
		TABLE_NAME,
		TABLE_TYPE,
		REMARKS,
		TYPE_CAT,
		TYPE_SCHEM,
		TYPE_NAME,
		SELF_REFERENCING_COL_NAME,
		REF_GENERATION
	}
	
	/**
	 * @author Filippo
	 * Costanti che rappresentano i nomi delle colonne del ResultSet restituito dal metodo getColumns() di
	 * DatabaseMetaData
	 */
	public static enum ColumnsFromGetColumns
	{
		IS_ONE_BASED, //nel resultset le colonne sono in base uno, se uso ordinal() evito di aggiungere 1
		TABLE_CAT,
		TABLE_SCHEM,
		TABLE_NAME,
		COLUMN_NAME,
		DATA_TYPE,
		TYPE_NAME,
		COLUMN_SIZE,
		BUFFER_LENGTH,
		DECIMAL_DIGITS,
		NUM_PREC_RADIX,
		NULLABLE,
		REMARKS,
		COLUMN_DEF,
		SQL_DATA_TYPE,
		SQL_DATETIME_SUB,
		CHAR_OCTET_LENGTH,
		ORDINAL_POSITION,
		IS_NULLABLE,
		SCOPE_CATALOG,
		SCOPE_SCHEMA,
		SCOPE_TABLE,
		SOURCE_DATA_TYPE
	}
	
	/**
	 * @author Filippo
	 * Costanti che rappresentano la corrispondenza tipi java.sql.Types / classi java
	 */
	public static enum SqlJavaTypes
	{
		SQL_JAVA_BIGINT {
			public int getSqlType() {
				return BIGINT;
			}
			public Class getJavaClass() {
				return java.lang.Long.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("NUMBER(38, 0)");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BIGINT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BIGINT");
				return retVal;
			}
		},
		SQL_JAVA_BINARY {
			public int getSqlType() {
				return BINARY;
			}
			public Class getJavaClass() {
				return byte[].class; //verificare, sarebbe byte[]
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("RAW");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("IMAGE");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TINYBLOB");
				return retVal;
			}
		},
		SQL_JAVA_BIT {
			public int getSqlType() {
				return BIT;
			}
			public Class getJavaClass() {
				return java.lang.Boolean.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BIT");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BIT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_BLOB {
			public int getSqlType() {
				return BLOB;
			}
			public Class getJavaClass() {
				return byte[].class; //verificare, sarebbe byte[]
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BLOB");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BLOB");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_CHAR {
			public int getSqlType() {
				return CHAR;
			}
			public Class getJavaClass() {
				return java.lang.String.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("CHAR");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("CHAR");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("CHAR");
				retVal.add("ENUM");
				retVal.add("SET");
				return retVal;
			}
		},
		SQL_JAVA_CLOB {
			public int getSqlType() {
				return CLOB;
			}
			public Class getJavaClass() {
				return java.lang.String.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("CLOB");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("CLOB");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_DATE {
			public int getSqlType() {
				return DATE;
			}
			public Class getJavaClass() {
				return java.sql.Date.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATE");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATE");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATE");
				retVal.add("YEAR");
				return retVal;
			}
		},
		SQL_JAVA_DECIMAL {
			public int getSqlType() {
				return DECIMAL;
			}
			public Class getJavaClass() {
				return java.math.BigDecimal.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("NUMBER");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DECIMAL");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DECIMAL");
				retVal.add("NUMERIC");
				return retVal;
			}
		},
		SQL_JAVA_DOUBLE {
			public int getSqlType() {
				return DOUBLE;
			}
			public Class getJavaClass() {
				return java.lang.Double.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DOUBLE PRECISION");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DOUBLE PRECISION");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DOUBLE");
				retVal.add("REAL");
				return retVal;
			}
		},
		SQL_JAVA_FLOAT {
			public int getSqlType() {
				return FLOAT;
			}
			public Class getJavaClass() {
				return java.lang.Double.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("FLOAT");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("FLOAT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_INTEGER {
			public int getSqlType() {
				return INTEGER;
			}
			public Class getJavaClass() {
				return java.lang.Integer.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("INTEGER");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("INTEGER");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("INT");
				retVal.add("INT24");
				retVal.add("MEDIUMINT");
				return retVal;
			}
		},
		SQL_JAVA_JAVA_OBJECT {
			public int getSqlType() {
				return JAVA_OBJECT;
			}
			public Class getJavaClass() {
				return java.lang.Object.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("JAVA_OBJECT");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("JAVA_OBJECT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_LONGVARBINARY {
			public int getSqlType() {
				return LONGVARBINARY;
			}
			public Class getJavaClass() {
				return byte[].class; //verificare, sarebbe byte[]
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("LONG RAW");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("IMAGE");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("BLOB");
				retVal.add("LONGBLOB");
				retVal.add("MEDIUMBLOB");
				return retVal;
			}
		},
		SQL_JAVA_LONGVARCHAR {
			public int getSqlType() {
				return LONGVARCHAR;
			}
			public Class getJavaClass() {
				return java.lang.String.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("LONG");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TEXT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TEXT");
				retVal.add("LONGTEXT");
				retVal.add("MEDIUMTEXT");
				return retVal;
			}
		},
		SQL_JAVA_NUMERIC {
			public int getSqlType() {
				return NUMERIC;
			}
			public Class getJavaClass() {
				return java.math.BigDecimal.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("NUMBER");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("NUMERIC");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_OTHER {
			public int getSqlType() {
				return OTHER;
			}
			public Class getJavaClass() {
				return java.lang.Object.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("OTHER");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("OTHER");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_REAL {
			public int getSqlType() {
				return REAL;
			}
			public Class getJavaClass() {
				return java.lang.Float.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("REAL");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("REAL");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("FLOAT");
				return retVal;
			}
		},
		SQL_JAVA_SMALLINT {
			public int getSqlType() {
				return SMALLINT;
			}
			public Class getJavaClass() {
				return java.lang.Integer.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("SMALLINT");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("SMALLINT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("SMALLINT");
				return retVal;
			}
		},
		SQL_JAVA_TIME {
			public int getSqlType() {
				return TIME;
			}
			public Class getJavaClass() {
				return java.sql.Time.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATE");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TIME");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TIME");
				return retVal;
			}
		},
		SQL_JAVA_TIMESTAMP {
			public int getSqlType() {
				return TIMESTAMP;
			}
			public Class getJavaClass() {
				return java.sql.Timestamp.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATE");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATETIME");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("DATETIME");
				retVal.add("TIMESTAMP");
				return retVal;
			}
		},
		SQL_JAVA_TINYINT {
			public int getSqlType() {
				return TINYINT;
			}
			public Class getJavaClass() {
				return java.lang.Byte.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TINYINT");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TINYINT");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TINYINT");
				return retVal;
			}
		},
		SQL_JAVA_VARBINARY {
			public int getSqlType() {
				return VARBINARY;
			}
			public Class getJavaClass() {
				return byte[].class; //verificare, sarebbe byte[]
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("RAW");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("IMAGE");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				return retVal;
			}
		},
		SQL_JAVA_VARCHAR {
			public int getSqlType() {
				return VARCHAR;
			}
			public Class getJavaClass() {
				return java.lang.String.class;
			}
			public List<String> getOraTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("VARCHAR");
				return retVal;
			}
			public List<String> getMsSqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("VARCHAR");
				return retVal;
			}
			public List<String> getMySqlTypes() {
				List<String> retVal = new ArrayList<String>();
				retVal.add("TINYTEXT");
				retVal.add("VARCHAR");
				return retVal;
			}
		};
		
		public abstract int getSqlType();
		public abstract Class getJavaClass();
		public abstract List<String> getOraTypes();
		public abstract List<String> getMsSqlTypes();
		public abstract List<String> getMySqlTypes();
	}
	
	/** Dato un tipo java.sql.Types, restituisce la classe java corrispondente
	 * @param type: tipo java.sql.Types
	 * @return Class: classe java corrispondente
	 */
	public static Class getJavaClassFromSqlType(int type) {
		for (SqlJavaTypes sjt : SqlJavaTypes.values()) {
			if (type == sjt.getSqlType()) return sjt.getJavaClass();
		}
		return java.lang.Object.class; //default
	}
	
	/** Dato un tipo java.sql.Types, restituisce il nome della classe java corrispondente
	 * @param type: tipo java.sql.Types
	 * @return String: nome della classe java corrispondente
	 */
	public static String getJavaClassNameFromSqlType(int type) {
		return getJavaClassFromSqlType(type).getName();
	}
	
	/** Dato un nome DB, restituisce la lista dei data types corrispondenti
	 * @param dbName: nome del DB (come in stringToDisplay() di DBType)
	 * @return List<String>: lista dei data types per il DB parametro
	 * @throws NotYetImplementedDBException: se il DB non è implementato
	 */
	public static List<String> getDataTypesFromDBName(String dbName) throws NotYetImplementedDBException {
		List<String> retVal = new ArrayList<String>();
		//importante: chiama il metodo corrispondente al DB selezionato
		String methodName = "get" + getStringValueFromDisplay(dbName) + "Types";
		try {
			for (SqlJavaTypes sjt : SqlJavaTypes.values()) {
				List<String> myDT = getGenericArrayList((ArrayList<?>)sjt.getClass().getMethod(methodName).invoke(sjt, (Object[])null));
				for (String myDTStr : myDT) {
					boolean presente = false;
					for (String str : retVal) {
						if (str.equals(myDTStr)) {
							presente = true;
							break;
						}
					}
					if (!presente) retVal.add(myDTStr);
				}
			}
		}catch (Exception e) {
			throw new NotYetImplementedDBException();
		}
		return orderList(retVal);
	}
	
	/** Dato un data type e un nome DB, restituisce la lista dei java types corrispondenti
	 * @param dt: data type
	 * @param dbName: nome del DB (come in stringToDisplay() di DBType)
	 * @return List<String>: lista dei java types per il data type e il DB parametri
	 * @throws NotYetImplementedDBException: se il DB non è implementato
	 */
	public static List<String> getJavaTypeNamesFromDataType(String dt, String dbName) throws NotYetImplementedDBException {
		List<String>retVal = new ArrayList<String>();
		//importante: chiama il metodo corrispondente al DB selezionato
		String methodName = "get" + getStringValueFromDisplay(dbName) + "Types";
		try {
			for (SqlJavaTypes sjt : SqlJavaTypes.values()) {
				List<String> myDT = getGenericArrayList((ArrayList<?>)sjt.getClass().getMethod(methodName).invoke(sjt, (Object[])null));
				for (String myDTStr : myDT) {
					if (myDTStr.equals(dt)) {
						String myJT = sjt.getJavaClass().getName();
						boolean presente = false;
						for (String str : retVal) {
							if (str.equals(myJT)) {
								presente = true;
								break;
							}
						}
						if (!presente) retVal.add(myJT);
					}
				}
			}
		}catch (Exception e) {
			throw new NotYetImplementedDBException();
		}
		return orderList(retVal);
	}
	
	/** Dato un java type (nome classe) e un nome DB, restituisce la lista dei data types corrispondenti
	 * @param jt: java type (nome classe come in Class.getName())
	 * @param dbName: nome del DB (come in stringToDisplay() di DBType)
	 * @return List<String>: lista dei data types per il java type e il DB parametri
	 * @throws NotYetImplementedDBException: se il DB non è implementato
	 */
	public static List<String> getDataTypeNamesFromJavaType(String jt, String dbName) throws NotYetImplementedDBException {
		List<String>retVal = new ArrayList<String>();
		//importante: chiama il metodo corrispondente al DB selezionato
		String methodName = "get" + getStringValueFromDisplay(dbName) + "Types";
		try {
			for (SqlJavaTypes sjt : SqlJavaTypes.values()) {
				String myJT = sjt.getJavaClass().getName();
				if (myJT.equals(jt)) {
					List<String> myDT = getGenericArrayList((ArrayList<?>)sjt.getClass().getMethod(methodName).invoke(sjt, (Object[])null));
					for (String myDTStr : myDT) {
						boolean presente = false;
						for (String str : retVal) {
							if (str.equals(myDTStr)) {
								presente = true;
								break;
							}
						}
						if (!presente) retVal.add(myDTStr);
					}
				}
			}
		}catch (Exception e) {
			throw new NotYetImplementedDBException();
		}
		return orderList(retVal);
	}
	
	/** Data una lista di data types, un DB "di partenza" e uno "di arrivo", restituisce la lista dei 
	 * data types corrispondenti, nel DB di arrivo, ai data types del DB di partenza passati a parametro
	 * @param dt: lista di data types del DB dbNameFrom
	 * @param dbNameFrom: nome del DB di partenza (come in stringToDisplay() di DBType)
	 * @param dbNameTo: nome del DB di arrivo (come in stringToDisplay() di DBType)
	 * @return: List<String>: lista dei data types del DB dbNameTo corrispondenti ai dt di dbNameFrom
	 * @throws NotYetImplementedDBException: se uno dei due DB non è implementato
	 */
	public static List<String> translateDataTypes(List<String> dt, String dbNameFrom, String dbNameTo) throws NotYetImplementedDBException {
		List<String>retVal = new ArrayList<String>();
		//importante: chiama il metodo corrispondente al DB selezionato
		String methodNameFrom = "get" + getStringValueFromDisplay(dbNameFrom) + "Types";
		String methodNameTo = "get" + getStringValueFromDisplay(dbNameTo) + "Types";
		for (String str : dt) {
			for (SqlJavaTypes sjt : SqlJavaTypes.values()) {
				List<String> myDTFrom = null;
				try {
					myDTFrom = getGenericArrayList((ArrayList<?>)sjt.getClass().getMethod(methodNameFrom).invoke(sjt, (Object[])null));
				}catch (Exception e) {
					throw new NotYetImplementedDBException("Il database " + dbNameFrom + " non è implementato");
				}
				for (String myDTFromStr : myDTFrom) {
					if (myDTFromStr.equals(str)) {
						List<String> myDTTo = null;
						try {
							myDTTo = getGenericArrayList((ArrayList<?>)sjt.getClass().getMethod(methodNameTo).invoke(sjt, (Object[])null));
						}catch (Exception e) {
							throw new NotYetImplementedDBException("Il database " + dbNameTo + " non è implementato");
						}
						for (String myDTToStr : myDTTo) {
							boolean presente = false;
							for (String str1 : retVal) {
								if (str1.equals(myDTToStr)) {
									presente = true;
									break;
								}
							}
							if (!presente) retVal.add(myDTToStr);
						}
					}
				}
			}
		}
		return orderList(retVal);
	}	
		
	/** Data una DBType.stringToDisplay(), restituisce il corrispondente stringValue()
	 * @param std: DBType.stringToDisplay()
	 * @return String: stringValue corrispondente
	 */
	public static String getStringValueFromDisplay(String std) {
		for (DBType dbt : DBType.values()) {
			if (std.equals(dbt.stringToDisplay())) return dbt.stringValue();
		}
		return "Ora"; //default
	}
	
	/** Ordinamento alfabetico di una List di String
	 * @param list: la lista da ordinare alfabeticamente
	 * @return List<String>: la lista ordinata alfabeticamente
	 */
	private static List<String> orderList(List<String> list) {
		if (list == null) return null;
		//ordinamento alfabetico
		List<String> retVal = new ArrayList<String>();
		String[] retValStr = (String[])list.toArray(new String[list.toArray().length]);
		Arrays.sort(retValStr);
		retVal = Arrays.asList(retValStr);
		return retVal;
	}
	
	/** Metodo di utilità per evitare errori di conversione da Object (restituito da Method.invoke()) a List<String>
	 * @param list: List contenente solo oggetti String
	 * @return List<String>: la List<String> corrispondente
	 */
	private static List<String> getGenericArrayList(List<?> list) {
		if (list == null) return null;
		List<String> retVal = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			retVal.add((String)list.get(i));
		}
		return retVal;
	}
	
}

