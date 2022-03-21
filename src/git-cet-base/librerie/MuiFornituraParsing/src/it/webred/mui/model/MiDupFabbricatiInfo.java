package it.webred.mui.model;

import it.webred.mui.consolidation.DapManager;
import it.webred.mui.hibernate.HibernateUtil;

import java.util.Set;

import net.skillbill.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class MiDupFabbricatiInfo extends MiDupFabbricatiInfoBase {
	
	private CodiceErroreImport _codiceErroreImport = null;

	public MiDupFabbricatiInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MiDupFabbricatiInfo(MiDupFornitura miDupFornitura) {
		super(miDupFornitura);
		// TODO Auto-generated constructor stub
	}

	public MiDupFabbricatiInfo(MiDupFornitura miDupFornitura, String idNota,
			MiDupNotaTras miDupNotaTras, String tipologiaImmobile,
			String flagGraffato, String idImmobile, String idCatastaleImmobile,
			String codiceEsito, String naturaImmobile, String zona,
			String categoria, String classe, String vani, String mc, String mq,
			String superficie, String renditaEuro, String TLotto,
			String TEdificio, String TScala, String TInterno1,
			String TInterno2, String TPiano1, String TPiano2, String TPiano3,
			String TPiano4, String TToponimo, String TIndirizzo,
			String TCivico1, String TCivico2, String TCivico3, String CLotto,
			String CEdificio, String CScala, String CInterno1,
			String CInterno2, String CPiano1, String CPiano2, String CPiano3,
			String CPiano4, String CToponimo, String CIndirizzo,
			String CCivico1, String CCivico2, String CCivico3,
			String annotazioni, Set<MiDupTitolarita> miDupTitolaritas,
			Set<MiDupFabbricatiIdentifica> miDupFabbricatiIdentificas) {
		super(miDupFornitura, idNota, miDupNotaTras, tipologiaImmobile,
				flagGraffato, idImmobile, idCatastaleImmobile, codiceEsito,
				naturaImmobile, zona, categoria, classe, vani, mc, mq,
				superficie, renditaEuro, TLotto, TEdificio, TScala, TInterno1,
				TInterno2, TPiano1, TPiano2, TPiano3, TPiano4, TToponimo,
				TIndirizzo, TCivico1, TCivico2, TCivico3, CLotto, CEdificio,
				CScala, CInterno1, CInterno2, CPiano1, CPiano2, CPiano3,
				CPiano4, CToponimo, CIndirizzo, CCivico1, CCivico2, CCivico3,
				annotazioni, miDupTitolaritas, miDupFabbricatiIdentificas);
		// TODO Auto-generated constructor stub
	}

	public CodiceErroreImport getCodiceErroreImport(){
		Logger.log().info(this.getClass().getName(),
				"getting CodiceErroreImport from CodiceEsito data, codiceEsito="+getCodiceEsito());
		if(_codiceErroreImport != null){
			return _codiceErroreImport;
		}
		if(getCodiceEsito() == null || getCodiceEsito().trim().length() == 0){
			return null;
		}
		else{
			try {
				Session session = HibernateUtil.currentSession();
				CodiceErroreImport loaded = (CodiceErroreImport)session.load(CodiceErroreImport.class,getCodiceEsito());
				Logger.log().info(this.getClass().getName(),
						"loaded CodiceErroreImport from CodiceEsito data, codiceEsito="+getCodiceEsito()+" CodiceErroreImport="+_codiceErroreImport);
				_codiceErroreImport = loaded;
				return loaded;
			} catch (HibernateException e) {
				Logger.log().error(this.getClass().getName(),
						"error while getting CodiceErroreImport from CodiceEsito data", e);
				return null;
			}
		}
	}
	public boolean isAbitativo(){
		return DapManager.isTipologiaImmobileAbitativo(getCategoria());
	}

	public MiDupFabbricatiIdentifica getMiDupFabbricatiIdentifica(){
		Set<MiDupFabbricatiIdentifica> l = getMiDupFabbricatiIdentificas();
		if(l==null||l.isEmpty()){
			return null;
		}
		else{
			return l.iterator().next();
		}
	}
}
