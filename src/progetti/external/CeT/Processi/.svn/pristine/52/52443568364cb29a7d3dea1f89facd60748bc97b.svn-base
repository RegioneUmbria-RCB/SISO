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
import it.webred.rulengine.dwh.Dao.RDemanioIndirizziDao;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.table.RDemanioIndirizzi;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadDemanioIndirizzi extends AbstractLoaderCommand implements Rule
{
	
	
	public LoadDemanioIndirizzi(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	private static final Logger log = Logger.getLogger(LoadDemanioIndirizzi.class.getName());
	
	//	blocco di inizializzazione della variabile tabellaDwhClass (specifica di quale classe è la TabellaDwh di questo load)
	{
		tabellaDwhClass = RDemanioIndirizzi.class;
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
			List<Object> params = this.caricaParametri(13, ctx);
			
			RDemanioIndirizzi tab = (RDemanioIndirizzi)getTabellaDwhInstance(ctx);
		
			tab.setChiaveBene((String)params.get(0));
			tab.setFlgPrincipale((BigDecimal)params.get(1));
			tab.setCodVia((BigDecimal)params.get(2));
			tab.setTipoVia((String)params.get(3));
			tab.setDescrVia((String)params.get(4));
			tab.setCivico((String)params.get(5));
			tab.setCodComune((String)params.get(6));
			tab.setDesComune((String)params.get(7));
			tab.setProv((String)params.get(8));
			
			String id = (String)params.get(9);
			String provenienza = (String)params.get(10);
			
			tab.setProvenienza(provenienza);
			tab.setTipo((String)params.get(11));

			Integer fk_ente_sorgente = (Integer)params.get(12);
			tab.setDtExpDato((DtExpDato)DwhUtils.getDataDwh(new DtExpDato(),(Timestamp)params.get(13)));
			tab.setDtInizioDato((DtIniDato)DwhUtils.getDataDwh(new DtIniDato(),null));
			tab.setDtFineDato((DtFineDato)DwhUtils.getDataDwh(new DtFineDato(),null));
			tab.setFlagDtValDato(new BigDecimal(0));
			tab.setFkEnteSorgente(DwhUtils.getIdentificativo(fk_ente_sorgente),ctx.getBelfiore());
		
			ChiaveOriginale co = new ChiaveOriginale();
			co.setValore(id);
			tab.setIdOrig(co);
			
			if (tab.getChiaveBene() != null) {
				co = new ChiaveOriginale();
				co.setValore(tab.getChiaveBene());
				
				ChiaveEsterna ce = new ChiaveEsterna();
				ce.setValore(provenienza, co, DwhUtils.getIdentificativo(fk_ente_sorgente));
				tab.setIdExtBene(ce);
			}
				
			RDemanioIndirizziDao dao = (RDemanioIndirizziDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			
			boolean salvato = dao.save(ctx.getBelfiore());
			
		} catch (DaoException e)
		{
			log.error("Errore di inserimento nella classe Dao",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} catch (Exception e)
		{
			log.error("LoadDemanioIndirizzi",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DEMANIO_INDIRIZZI inserito";
		
		return(new ApplicationAck(msg));

	}
	

}
