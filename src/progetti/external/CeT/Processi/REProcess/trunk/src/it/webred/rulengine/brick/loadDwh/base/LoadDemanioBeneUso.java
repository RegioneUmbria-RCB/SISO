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
import it.webred.rulengine.dwh.Dao.RDemanioBeneUsoDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.RDemanioBeneUso;
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

public class LoadDemanioBeneUso extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadDemanioBeneUso(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadDemanioBeneUso.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioBeneUso.class;
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
			List<Object> params = this.caricaParametri(6, ctx);
			
			RDemanioBeneUso tab = (RDemanioBeneUso)getTabellaDwhInstance(ctx);
			
			String chiaveBene = (String)params.get(0);
			
			tab.setCodTipoUso((BigDecimal)params.get(1));
			tab.setDesTipoUso((String)params.get(2));
			String provenienza = (String)params.get(3);
			tab.setProvenienza(provenienza);
			tab.setTipo((String)params.get(4));

			Integer fk_ente_sorgente = (Integer)params.get(5);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)params.get(6)));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato(new BigDecimal(0));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
		
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(chiaveBene);
			tab.setIdOrig(co);
				
			RDemanioBeneUsoDao dao = (RDemanioBeneUsoDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadDemanioBeneUso",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_BENE_USO inserito";
		
		return(new ApplicationAck(msg));

	}
	

}
