package it.webred.rulengine.diagnostics.superclass;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.ElaboraDiagnosticsNonStandard;
import it.webred.rulengine.diagnostics.ElaboraDiagnosticsSql;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class AbstractDiagnostics extends Command {
	
	protected static org.apache.log4j.Logger log = Logger.getLogger(AbstractDiagnostics.class.getName());
	
	protected boolean isDiaStandard = true;
	
	public AbstractDiagnostics(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);		
	}
	
	public CommandAck run(Context ctx) throws CommandException {
		
		Connection conn = null;		
		String codCommand = "";			
		
		try {
			log.info("[RUN] - Recupero connessione da contesto");
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			List<RRuleParamIn> parametriIn = this.getParametersIn(_jrulecfg);
	
			//Recupero dal contesto il codice del comando da eseguire
			log.info("[RUN] - Recupero codCommand da contesto");
			codCommand = (String)ctx.getDeclarativeType("RULENGINE.COD_COMMAND").getValue();
			log.debug("[RUN] - Recupero codCommand da contesto: " + codCommand);
			
			if (codCommand == null || codCommand.length() == 0) {
				Exception e = new Exception("Non è stato specificato il codice comando delle diagnostiche da eseguire");
				log.error("Errore grave in fase di elaborazione diagnostiche", e);
				return new RejectAck(e.getMessage());
			} 
			
			if (isDiaStandard){
				log.info("[RUN] - RICHIAMO LA CLASSE ElaboraDiagnosticsSql");
				ElaboraDiagnosticsSql elab = new ElaboraDiagnosticsSql(conn,ctx,parametriIn);
				elab.ExecuteDiagnostic(codCommand);
			}else{
				log.info("[RUN] - RICHIAMO LA CLASSE tramite Reflection ");
				ExecuteDiagnosticNonStandard(conn,ctx,parametriIn,codCommand);				
			}
			
		} catch (Exception e) {
			log.error("Errore grave in fase di elaborazione diagnostica ", e);
			return new ErrorAck(e);
		} finally {
						
		}		
		String msg = "[RUN] - Elaborazione conclusa SENZA ERRORI";
		log.info(msg);
		return new ApplicationAck(msg);
	}
	
	private void ExecuteDiagnosticNonStandard(Connection conn, Context ctx, List<RRuleParamIn> parametriIn, String codCommand) throws Exception{
		String typeClassDia = "";
		String sql = "Select CLASS_TYPE_DIA_NON_STD From DIA_CATALOGO where FK_COD_COMMAND ='"+codCommand+"'";
		ResultSet rs = null;
		try {
			log.info("[ExecuteDiagnosticNonStandard] - Recupero da dia_catalogo la classe da instanziare ");
			log.debug("[ExecuteDiagnosticNonStandard] - CodCommand:"+codCommand);
			rs = conn.prepareStatement(sql).executeQuery();
			if (rs.next()) {
				typeClassDia = rs.getString(1);				
			}
			log.debug("[ExecuteDiagnosticNonStandard] - typeClassDia:" + typeClassDia);
			
			Class cls = Class.forName(typeClassDia);
			if (!cls.getSuperclass().isInstance(new ElaboraDiagnosticsNonStandard()))
				throw new Exception(" La classe passata non estende ElaboraDiagnosticsNonStandard ");
			
			// Invoco il costruttore con 4 parametri
			Class partypesCt[] = new Class[3];
			partypesCt[0] = Connection.class;
			partypesCt[1] = Context.class;
			partypesCt[2] = List.class;	
	        Constructor ct = cls.getConstructor(partypesCt);
            Object arglist[] = new Object[3];;
            arglist[0] = conn;
            arglist[1] = ctx;
            arglist[2] = parametriIn;
            ElaboraDiagnosticsNonStandard diaInstance = (ElaboraDiagnosticsNonStandard)ct.newInstance(arglist);
            diaInstance.ExecuteDiagnostic(codCommand);
            
            //Invoco il metodo ExecuteDiagnostic
//            Class partypes[] = new Class[1];
//            partypes[0] = String.class;            
//            Method meth = cls.getMethod("ExecuteDiagnostic", partypes);            
//            Object arglistMethod[] = new Object[1];
//            arglistMethod[0] = codCommand;         
//            meth.invoke(diaInstance, arglistMethod);
                                                           			
		} catch (Exception e) {
			log.error("[ExecuteDiagnosticNonStandard] - Errore inizializzazione classe tramite reflection : " + e.getMessage());
			throw e;
		}finally {
			if (rs != null) rs.close();
						
		}
		
	}
	

}
