package it.webred.rulengine.brick.loadDwh.utils;

public interface FileConverter {
	
	public void save(String filePathFrom, String filePathTo ,String belfiore) throws Exception;
	
	public String checkFilenames(String filePathFrom, String filePathTo);
	
}
