package it.webred.mui.model;

import it.webred.mui.input.MuiFornituraParser;
import net.skillbill.logging.Logger;

import org.hibernate.Session;

public class MiDupFornitura extends MiDupFornituraBase {

	private int anno = -1 ;
	private int numeroAnno = -1;
	
	public boolean isFullyLoaded(){
		if(getFileTotNotParsed() == null||getFileTotNotParsed().trim().length()==0){
			return true;
		}
		else{
			String[] loaded = getFileLoadedNotParsed().split(",");			
			//return loaded.length == Integer.valueOf(getFileTotNotParsed());
			
			//modificato Filippo Mazzini 27.05.09
			//dal codice "attuale" di CodeBehinduploadXMLPage e MuiFornituraParser, sembra che getFileLoadedNotParsed()
			//restituisca non una stringa composta, ma un semplice intero (uguale al totale dei file)
			boolean retVal = loaded.length == Integer.valueOf(getFileTotNotParsed());
			if (!retVal) {
				try {
					retVal = Integer.valueOf(getFileLoadedNotParsed()).intValue() == Integer.valueOf(getFileTotNotParsed()).intValue();
				} catch (Exception e) {
					return false;
				}				
			}
			
			return retVal;
			//fine modificato Filippo Mazzini 27.05.09		
			
		}
	}
	
	public void deleteFully(Session s){
		Class table[] = new Class[]{MiConsDap.class,MiConsIntegrationLog.class,MiConsOggetto.class,MiConsComunicazione.class,MiDupImportLog.class,MiDupTitolarita.class,MiDupFabbricatiIdentifica.class,MiDupFabbricatiInfo.class,MiDupTerreniInfo.class,MiDupIndirizziSog.class, MiDupSoggetti.class,MiDupNotaTras.class,MiDupFornituraInfo.class};
		for (int i = 0; i < table.length; i++) {
			deleteReferencedTable(s, table[i]);
		}
		s.delete(this);
	}

	protected void deleteReferencedTable(Session s, Class table) {
		String hqlDelete = "delete "+table.getName()+" c where c.miDupFornitura = :miDupFornitura";
		Logger.log().info(this.getClass().getName(),"deleting from "+table.getName()+" where iidFornitura="+this.getIid());
		int deletedEntities = s.createQuery( hqlDelete ).setParameter( "miDupFornitura", this).executeUpdate();
		Logger.log().info(this.getClass().getName(),"deleted "+deletedEntities+" row(s) from "+table.getName()+" where iidFornitura="+this.getIid());
		
	}

	public int getAnno() {
		if(anno == -1){
			anno  = Integer.valueOf( MuiFornituraParser.yearParser.format(getDataInizialeDate()));
		}	
		return anno;
	}

	public int getNumeroAnno() {
		return numeroAnno;
	}

	public void setNumeroAnno(int numeroAnno) {
		this.numeroAnno = numeroAnno;
	}
}
