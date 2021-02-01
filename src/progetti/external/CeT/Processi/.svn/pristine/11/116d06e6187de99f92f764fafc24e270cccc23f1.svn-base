package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.RDemanioTitoliDao;
import it.webred.rulengine.dwh.def.Chiave;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.table.RDemanioTitoli;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

public class LoadDemanioTitoli  extends AbstractLoaderCommand implements Rule{
	
	public LoadDemanioTitoli(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}//-------------------------------------------------------------------------

	private static final Logger log = Logger.getLogger(LoadDemanioTitoli.class.getName());

//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioTitoli.class;
	}

	private List<Object> caricaParametri(int maxIndex, Context ctx){
		List<Object> lst = new ArrayList<Object>();
		List parametriIn = this.getParametersIn(_jrulecfg);
		for(int i=0; i<= maxIndex; i++){
			Object o = ctx.get(((RRuleParamIn) parametriIn.get(i)).getDescr());
			lst.add(o);
		}
		return lst;
	}//-------------------------------------------------------------------------
	
	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException{
		
		try {
			//numero di parametri espresso nel properties -1 xchè qui nel codice parte da zero
			List<Object> params = this.caricaParametri(39, ctx);
			
			RDemanioTitoli tab = (RDemanioTitoli)getTabellaDwhInstance(ctx);
			
			String id = (String)params.get(0);
			String idExt = (String)params.get(1);
			String idOrig = (String)params.get(2);
			Integer fk_ente_sorgente = (Integer)params.get(3);
			String idExtBene = (String)params.get(4);
			String chiaveBene = (String)params.get(5);
			String idTitolo = (String)params.get(6);		
			String tipoAcquisizione = (String)params.get(7);		
			String tipoDirittoReale = (String)params.get(8);		
			CLOB oggetto = (CLOB)params.get(9);
			String strumentoCostruttivo = (String)params.get(10);
			String annoAcquisizione = (String)params.get(11);
			String normativaUtilizzata = (String)params.get(12);
			String docpropOrigine = (String)params.get(13);
			String docpropNumero = (String)params.get(14);
			CLOB docpropContraenti = (CLOB)params.get(15);
			CLOB docpropPagamento = (CLOB)params.get(16);
			CLOB docpropPatticond = (CLOB)params.get(17);
			CLOB docpropServatt = (CLOB)params.get(18);
			CLOB docpropServpass = (CLOB)params.get(19);
			Timestamp titData = (Timestamp)params.get(20);
			String titRegAtti = (String)params.get(21);
			String titRegUfficio = (String)params.get(22);
			String titRegNumero = (String)params.get(23);
			String titRegVolume = (String)params.get(24);
			String titRegSerie = (String)params.get(25);
			Timestamp titRegData = (Timestamp)params.get(26);
			Timestamp trascrData = (Timestamp)params.get(27);
			String trascrNum = (String)params.get(28);
			String trascrNumOrdine = (String)params.get(29);
			String trascrLocalita = (String)params.get(30);
			String annotazioni = (String)params.get(31);
			String provenienza = (String)params.get(32);
			String tipo = (String)params.get(33);
			Timestamp dtExpDato = (Timestamp)params.get(34);
			BigDecimal chiave1 = (BigDecimal)params.get(35);
			String chiave2 = (String)params.get(36);
			String chiave3 = (String)params.get(37);
			String chiave4 = (String)params.get(38);
			String chiave5 = (String)params.get(39);
			
			Chiave uid = new Chiave();
			uid.setValore(id.toString());
			tab.setId( uid );
			//tab.setIdExt( );
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(chiaveBene);
			tab.setIdOrig(co);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
			tab.setIdExtBene(ce);
			tab.setChiaveBene( chiaveBene );
			tab.setIdTitolo( idTitolo );
			tab.setTipoAcquisizione( tipoAcquisizione );
			tab.setTipoDirittoReale( tipoDirittoReale );
			tab.setOggetto( oggetto );
			tab.setStrumentoCostruttivo( strumentoCostruttivo );
			tab.setAnnoAcquisizione( annoAcquisizione );
			tab.setNormativaUtilizzata( normativaUtilizzata );
			tab.setDocpropOrigine( docpropOrigine );
			tab.setDocpropNumero( docpropNumero );
			tab.setDocpropContraenti( docpropContraenti );
			tab.setDocpropPagamento( docpropPagamento );
			tab.setDocpropPatticond( docpropPatticond );
			tab.setDocpropServatt( docpropServatt );
			tab.setDocpropServpass( docpropServpass );
			tab.setTitData( DwhUtils.getDataDwh(new DataDwh(), titData) );
			tab.setTitRegAtti( titRegAtti );
			tab.setTitRegUfficio( titRegUfficio );
			tab.setTitRegNumero( titRegNumero );
			tab.setTitRegVolume( titRegVolume );
			tab.setTitRegSerie( titRegSerie );
			tab.setTitRegData( DwhUtils.getDataDwh(new DataDwh(), titRegData) );
			tab.setTrascrData( DwhUtils.getDataDwh(new DataDwh(), trascrData) );
			tab.setTrascrNum(trascrNum);
			tab.setTrascrNumordine( trascrNumOrdine );
			tab.setTrascrLocalita(trascrLocalita);
			tab.setAnnotazioni(annotazioni);
			tab.setProvenienza(provenienza.trim());
			tab.setTipo( tipo );
			tab.setDtExpDato( (DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dtExpDato!=null?dtExpDato:new Timestamp(System.currentTimeMillis()) ) );
			tab.setChiave1( chiave1!=null?chiave1.toString():"" );
			tab.setChiave2(chiave2);
			tab.setChiave3(chiave3);
			tab.setChiave4(chiave4);
			tab.setChiave5(chiave5);
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato( new BigDecimal(0) );
			tab.setDtInizioVal( (DtIniVal)DwhUtils.getDataDwh(new DtIniVal(),null) );
			tab.setDtFineVal( (DtFineVal)DwhUtils.getDataDwh(new DtFineVal(),null) );

			RDemanioTitoliDao dao = (RDemanioTitoliDao)DaoFactory.createDao(conn, tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao Demanio Titoli",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)																																
		{
			log.error("LoadDemanioTitoli",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_TITOLI inserito";
		
		return(new ApplicationAck(msg));

	}//-------------------------------------------------------------------------

}
