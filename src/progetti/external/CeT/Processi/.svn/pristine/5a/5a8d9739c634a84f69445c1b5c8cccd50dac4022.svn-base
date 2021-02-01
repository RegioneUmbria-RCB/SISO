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
import it.webred.rulengine.dwh.Dao.RDemanioBeneDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.RDemanioBene;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

public class LoadDemanioBene extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadDemanioBene(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadDemanioBene.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioBene.class;
	}

	private List<Object> caricaParametri(int maxIndex, Context ctx){
		
		List<Object> lst = new ArrayList<Object>();
		List parametriIn = this.getParametersIn(_jrulecfg);
		for(int i=0; i<= maxIndex; i++){
			Object o = ctx.get(((RRuleParamIn) parametriIn.get(i)).getDescr());
			lst.add(o);
		}
		
		return lst;
		
	}

	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn)
		throws CommandException
	{
		
		try {
			List<Object> params = this.caricaParametri(17, ctx);
			
			RDemanioBene tab = (RDemanioBene)getTabellaDwhInstance(ctx);
			
			String chiaveBene = (String)params.get(1);
			
			tab.setPkOrig((BigDecimal)params.get(0));
			tab.setChiavePadre((String)params.get(2));
			tab.setChiave1((BigDecimal)params.get(3));
			tab.setChiave2((String)params.get(4));
			tab.setChiave3((String)params.get(5));
			tab.setChiave4((String)params.get(6));
			tab.setChiave5((String)params.get(7));
			tab.setCodEcografico((String)params.get(8));
			tab.setCodTipoBene((BigDecimal)params.get(9));
			tab.setDesTipoBene((String)params.get(10));
			tab.setDescrizione((CLOB)params.get(11));
			tab.setNote((CLOB)params.get(12));
			tab.setNumParti((BigDecimal)params.get(13));
			String provenienza = (String)params.get(14);
			tab.setProvenienza(provenienza);
			tab.setTipo((String)params.get(15));

			Integer fk_ente_sorgente = (Integer)params.get(16);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)params.get(17)));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato(new BigDecimal(0));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
		
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(chiaveBene);
			tab.setIdOrig(co);
				
			RDemanioBeneDao dao = (RDemanioBeneDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadDemanioBene",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_BENE_IMM inserito";
		
		return(new ApplicationAck(msg));

	}
	

}
