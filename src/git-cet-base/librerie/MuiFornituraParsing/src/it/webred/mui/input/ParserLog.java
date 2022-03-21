package it.webred.mui.input;

public interface ParserLog {
	void logDebug(String msg);
	void logTrace(String msg);
	void logInfo(String msg);
	void logWarning(String msg);
	void logError(String msg);
}
