package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ErogInterventiDateFilterBean {

	private Date dataRichiestaIntervento;
	private Date dataUltimoFlg;
	private Date dataUltimaErogazione;
	
	public Filter<ErogInterventoRowBean,ErogInterventiDateFilterBean> filter = new Filter<ErogInterventoRowBean,ErogInterventiDateFilterBean>() {
        public boolean isMatched(ErogInterventoRowBean row, ErogInterventiDateFilterBean filterValue) {
        	boolean bMatched = true;
        	
        	if( filterValue.getDataUltimaErogazione() != null )
        	{
        		bMatched &= row.getMaster().getDataUltimaErogazione() != null && row.getMaster().getDataUltimaErogazione().after( filterValue.getDataUltimaErogazione() );
        	}
        	
        	return bMatched;
        }
    };

	protected int compareDateString( String sA, String sB ) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dA = df.parse(sA);
		Date dB = df.parse(sB);
		
		if (dA.before(dB)) return 1;
		if (dB.after(dB)) return -1;

		return 0;
	}
	
	public Date getDataRichiestaIntervento() {
		return dataRichiestaIntervento;
	}
	public void setDataRichiestaIntervento(Date dataRichiestaIntervento) {
		this.dataRichiestaIntervento = dataRichiestaIntervento;
	}
	public Date getDataUltimoFlg() {
		return dataUltimoFlg;
	}
	public void setDataUltimoFlg(Date dataUltimoFlg) {
		this.dataUltimoFlg = dataUltimoFlg;
	}
	public Date getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}
	public void setDataUltimaErogazione(Date dataUltimaErogazione) {
		this.dataUltimaErogazione = dataUltimaErogazione;
	}

	public void reset(){
		dataRichiestaIntervento = null;
		dataUltimaErogazione = null;
		dataUltimoFlg = null;
	}
	
	public List<ErogInterventoRowBean> filtra( List<ErogInterventoRowBean> listaInterventi) {

		List<ErogInterventoRowBean> filterList = null;
		if(dataRichiestaIntervento != null || dataUltimaErogazione != null || dataUltimoFlg != null ){
			filterList = new LinkedList<ErogInterventoRowBean>();
	        for (ErogInterventoRowBean object : listaInterventi) {
	            if (filter.isMatched(object, this)) {
	                filterList.add(object);
	            }
	        }
		}
		else
			filterList = listaInterventi;
		
		return filterList;
	}

	public interface Filter<T,E> {
	    public boolean isMatched(T object, E text);
	}
	
}
