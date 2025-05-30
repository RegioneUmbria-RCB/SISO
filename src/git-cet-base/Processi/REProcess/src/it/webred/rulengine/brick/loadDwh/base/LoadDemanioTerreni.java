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
import it.webred.rulengine.dwh.Dao.RDemanioTerreniDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.RDemanioTerreni;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

public class LoadDemanioTerreni  extends AbstractLoaderCommand implements Rule{
	
	
	public LoadDemanioTerreni(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}//-------------------------------------------------------------------------


	private static final Logger log = Logger.getLogger(LoadDemanioTerreni.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioTerreni.class;
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
			List<Object> params = this.caricaParametri(40, ctx);
			
			RDemanioTerreni tab = (RDemanioTerreni)getTabellaDwhInstance(ctx);
			
			BigDecimal id = (BigDecimal)params.get(0);
			String idExt = (String)params.get(1);
			//String idOrig = (String)params.get(2);
			Integer fk_ente_sorgente = (Integer)params.get(3);
			String idExtBene = (String)params.get(4);
			String chiaveBene = (String)params.get(5);
			String codComune = (String)params.get(6);		
			String sezione = (String)params.get(7);		
			String foglio = (String)params.get(8);		
			String mappale = (String)params.get(9);
			String sub = (String)params.get(10);
			BigDecimal superficie = (BigDecimal)params.get(11);
			String classe = (String)params.get(12);
			String qualita = (String)params.get(13);
			String tipologia = (String)params.get(14);
			String finalita = (String)params.get(15);
			String fonte = (String)params.get(16);
			String usoAttuale = (String)params.get(17);
			String codUtilizzo = (String)params.get(18);
			String partita = (String)params.get(19);
			String unita = (String)params.get(20);
			String annotazione = (String)params.get(21);
			String reddDomin = (String)params.get(22);
			String reddAgr = (String)params.get(23);
			String valInventario = (String)params.get(24);
			String valCatastale = (String)params.get(25);
			String valAcquisto = (String)params.get(26);
			String renditaPresunta = (String)params.get(27);
			BigDecimal quotaPoss = (BigDecimal)params.get(28);
			String note = (String)params.get(29);
			String dismesso = (String)params.get(30);
			Timestamp dataVendita = (Timestamp)params.get(31);
			CLOB contratti = (CLOB)params.get(32);
			String provenienza = (String)params.get(33);
			String tipo = (String)params.get(34);
			Timestamp dtExpDato = (Timestamp)params.get(35);
			String chiave1 = (String)params.get(36);
			String chiave2 = (String)params.get(37);
			String chiave3 = (String)params.get(38);
			String chiave4 = (String)params.get(39);
			String chiave5 = (String)params.get(40);
			
			//tab.setId(  );
			//tab.setIdExt( );
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(id.toString());
			tab.setIdOrig(co);
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
			ChiaveEsterna ce = new ChiaveEsterna();
			ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
			tab.setIdExtBene(ce);
			tab.setChiaveBene( chiaveBene );
			tab.setCodComune( codComune );
			tab.setSezione( sezione );
			tab.setFoglio( foglio );
			tab.setMappale( mappale );
			tab.setSub(sub);
			tab.setSuperficie( superficie!=null?superficie.setScale(2, BigDecimal.ROUND_HALF_EVEN):null );
			tab.setClasse(classe);
			tab.setQualita(qualita);
			tab.setTipologia(tipologia);
			tab.setFinalita(finalita);
			tab.setFonte(fonte);
			tab.setUsoAttuale(usoAttuale);
			tab.setCodUtilizzo(codUtilizzo);
			tab.setPartita(partita);
			tab.setUnita(unita);
			tab.setAnnotazione(annotazione);
			//log.info("reddDomin: " + reddDomin);
			//NEL CAMPO REDDDOMIN E REDDAGR POTREBBERO ESSERE PRESENTI CARATTERI DIVERSI DA NUMERI VIRGOLA PUNTO (TROVATO SIMBOLO EURO)
			tab.setReddDomin( (reddDomin!=null && isNumeric(reddDomin) )?new BigDecimal( reddDomin.replace(",", ".") ).setScale(2, BigDecimal.ROUND_HALF_EVEN):null );
			//log.info("reddAgr: " + reddAgr);
			tab.setReddAgr( (reddAgr!=null && isNumeric(reddAgr) )?new BigDecimal( reddAgr.replace(",", ".") ).setScale(2, BigDecimal.ROUND_HALF_EVEN):null );
			tab.setValInventario(valInventario);
			tab.setValCatastale(valCatastale);
			tab.setValAcquisto(valAcquisto);
			tab.setRenditaPresunta(renditaPresunta);
			tab.setQuotaPoss( quotaPoss!=null? quotaPoss.setScale(2, BigDecimal.ROUND_HALF_EVEN):null );
			tab.setNote(note);
			tab.setDismesso(dismesso);
			tab.setDataVendita( DwhUtils.getDataDwh(new DataDwh(), dataVendita) );
			tab.setContratti( ClobToString( contratti) );
			tab.setProvenienza(provenienza.trim());
			tab.setTipo( tipo );
			tab.setDtExpDato( (DtExpDato)DwhUtils.getDataDwh(new DtExpDato(), dtExpDato ) );
			tab.setChiave1(chiave1);
			tab.setChiave2(chiave2);
			tab.setChiave3(chiave3);
			tab.setChiave4(chiave4);
			tab.setChiave5(chiave5);
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato(new BigDecimal(0));
				
			RDemanioTerreniDao dao = (RDemanioTerreniDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao Demanio Terreni",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadDemanioTerreni",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_TERRENI inserito";
		
		return(new ApplicationAck(msg));

	}//-------------------------------------------------------------------------


}
