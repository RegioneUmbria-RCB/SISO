package it.webred.mui.input;

public interface MuiFornituraRecordParser extends MuiFornituraRecordChecker {
	void parse(String record) throws MuiInvalidInputDataException;
	void store();
	void reset();
	Object getRecord();
	void setChecker(MuiFornituraRecordChecker checker);
}
