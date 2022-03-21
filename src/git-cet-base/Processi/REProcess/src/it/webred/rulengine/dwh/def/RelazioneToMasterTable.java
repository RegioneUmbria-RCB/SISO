package it.webred.rulengine.dwh.def;

/**
 * @author MARCO
 * Questa classe rappresenta una relazione verso una tabella master.
 * Si differenzia da una relazione di decodifica per la caratteristica di indicare
 * una chiave verso la tabella Master.
 * In una tabella possono esistere una sola di queste relazioni a meno che 
 * la tabella non sia una tabella di relazione n a m , allora ne esisteranno 2
 * Nel momento in cui su una determinata tabella si va ad inserire un record con id =null, viene fatto il check se 
 * la tabella ha una relazione di questo tipo
 * per operare opportuni meccanismi di storicizzazione @TabellaDwhDao
 * 
 */
public class RelazioneToMasterTable extends Relazione {

	public RelazioneToMasterTable(Class tabella, Campo campo) {
		super(tabella, campo);
		// TODO Auto-generated constructor stub
	}
	
}
