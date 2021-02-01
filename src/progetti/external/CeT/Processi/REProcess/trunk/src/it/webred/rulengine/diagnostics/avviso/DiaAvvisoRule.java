package it.webred.rulengine.diagnostics.avviso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.SubscriberNotFoundAck;
import it.webred.rulengine.diagnostics.avviso.exception.NoSubscribedUserException;
import it.webred.rulengine.diagnostics.dto.DiaCatalogoDTO;
import it.webred.rulengine.diagnostics.dto.DiaSubscribedUserDTO;
import it.webred.rulengine.diagnostics.dto.RCommandDTO;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.Variable;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

public class DiaAvvisoRule extends Command implements Rule {
	
	
	private static Logger log = Logger.getLogger(DiaAvvisoRule.class.getName());
	
	public DiaAvvisoRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		
		CommandAck retAck = null;
		String oggetto = null;
		String body = null;
		String mittente = null;
		String dd = null;
		String server = null;
		String port = null;
		
		try {
			String belfiore = ctx.getBelfiore();
			Long idFonte = ctx.getIdFonte();
			Connection conn = ctx.getConnection((String)ctx.get("connessione"));
			
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			
			log.debug("Recupero variabili evento");
			Variable v = (Variable)ctx.getDeclarativeType("event.after.command.object");
			RCommandDTO rCommand = (RCommandDTO)v.getObjectValue();
			log.info("Comando che ha scatenato l'evento: "+rCommand.getCodCommand());
			
			//recupero elenco diagnostiche associate al comando (gruppo o singola)
			List<DiaCatalogoDTO> diags = getDiagnostiche(conn, rCommand.getCodCommand());
			
			/*
			 * Recupero degli utenti sottoscritti dalla tabella R_SOTTOSCRITTORI 
			 * tramite il sinonimo su db diogene DWH
			 */
			List<DiaSubscribedUserDTO> destinatari = getUsersEmailAddress(conn,belfiore,rCommand.getCodCommand());
			
			//1-OGGETTO
			log.debug("Recupero oggetto avviso");
			
			ComplexParam param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			
			if(param == null) throw new Exception("Oggetto email non specificato");
			
			HashMap<String,ComplexParamP> pars = param.getParams();
			Set set = pars.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				oggetto = o.toString();
			}
			log.debug("Oggetto avviso: "+oggetto);
			
			
			//2-MESSAGGIO
			log.debug("Recupero body avviso");
			param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			if(param == null) throw new Exception("Messaggio email non specificato");
			
			pars = param.getParams();
			set = pars.entrySet();
			it = set.iterator();
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				body = o.toString();
				body = body.replace("$d", rCommand.getCodCommand()+" ("+rCommand.getDescr()+")");
			}
			
			body = this.appendDiagsToMessage(body,rCommand.getCodCommand(),diags);
			log.debug("[Message]\n"+body);
			
			//3-MITTENTE
			criteria.setComune(belfiore);
			criteria.setKey("email.diagnostics");
			criteria.setInstance("Diagnostics_"+belfiore);
			AmKeyValueExt amkvext = cdm.getAmKeyValueExt(criteria);
			if(amkvext != null) {
				mittente = amkvext.getValueConf();
				log.debug("Mittente: "+mittente);
			}
			else {
				throw new Exception("Nessun mittente email specificato");
			}
			
			//4-DESTINATARIO
			StringBuilder destinatario = new StringBuilder();
			for(DiaSubscribedUserDTO d: destinatari) {
				destinatario.append(d.getEmailAddress());
				destinatario.append(",");
			}
			
			dd = destinatario.toString();
			dd = destinatario.substring(0, destinatario.length()-1);
			log.debug("Destinatari: "+dd);
			
			//5-SERVER
			criteria.setKey("mail.server");
			criteria.setInstance(null);
			amkvext = cdm.getAmKeyValueExt(criteria);
			if(amkvext != null) {
				server = amkvext.getValueConf();
				log.debug("Server: "+server);
			}
			else {
				throw new Exception("Nessun server mail specificato");
			}

			//6-PORT
			criteria.setKey("mail.server.port");
			amkvext = cdm.getAmKeyValueExt(criteria);
			if(amkvext != null) {
				 port = amkvext.getValueConf();
				log.debug("Port: "+port);
			}
			else {
				throw new Exception("Nessuna porta mail specificata");
			}
			
			//preparazione invio
			log.info("Preparazione invio email");
			Properties properties = new Properties();
	        properties.put("mail.smtp.host", server);
	        properties.put("mail.smtp.port", port);
	        Session session = Session.getDefaultInstance(properties, null);
			MimeMessage message = new MimeMessage(session);
 			
			message.setFrom(new InternetAddress(mittente));
			message.setRecipients(Message.RecipientType.TO, dd);
			message.setSubject(oggetto);
			message.setSentDate(new Date());
			
			// Set the email message text
			message.setContent(body, "text/html");
			
			// SEND MAIL
			System.out.println(" START Transport.send");
			Transport.send(message);
			System.out.println(" END Transport.send");
			
			//retAck = new ApplicationAck("Impostazioni invio email esito diagnostica");
			retAck = new ApplicationAck("Invio email esito diagnostica eseguito con successo");
			
		}catch (NoSubscribedUserException e) {
			log.warn("Attenzione !!"+e.getMessage());
			retAck = new SubscriberNotFoundAck(e.getMessage());
		}catch (Exception e) {
			log.error("Eccezione avviso diagnostica: "+e.getMessage());
			retAck = new ErrorAck(e);
		}finally {
			//gestione param out per
			//log.info("Impostazione parametri di output della regola ["+this.getClass().getSimpleName()+"]");
			//ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.1.descr"), server);
			//ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.2.descr"), port);
			//ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.3.descr"), mittente);
			//ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.4.descr"), dd);
			//ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.5.descr"), oggetto);
			//ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.6.descr"), body);
		}
		
		return retAck;
	}
	
	
	
	
	
	private List<DiaSubscribedUserDTO> getUsersEmailAddress(Connection conn,String belfiore,String codCommand) throws Exception {
		
		List<DiaSubscribedUserDTO> subscribed = new ArrayList<DiaSubscribedUserDTO>();
		
		StringBuilder sqlCommand = new StringBuilder("select rs.fk_user_email, rs.fk_name ");
		sqlCommand.append(" from r_sottoscrittori rs ");
		sqlCommand.append(" where rs.belfiore = ? ");
		sqlCommand.append(" and rs.fk_cod_command = ?");
		log.debug("Query: "+sqlCommand.toString());
		log.debug("Query params: "+belfiore+", "+codCommand);
		
		PreparedStatement pstmt = conn.prepareStatement(sqlCommand.toString());
		pstmt.setString(1, belfiore);
		pstmt.setString(2, codCommand);
		ResultSet rs_qry = pstmt.executeQuery();
		
		int count = 0;
		while (rs_qry.next()) {
			String user = rs_qry.getString("fk_name");
			String email = rs_qry.getString("fk_user_email");
			
			subscribed.add(new DiaSubscribedUserDTO(user,email));
			count++;
		}
		
		rs_qry.close();
		pstmt.close();
		
		if (count <= 0) {
			throw new NoSubscribedUserException("Nessun utente sottoscritto alla diagnostica "+
								codCommand+" [ente: "+belfiore+"]");
		}
		
		return subscribed;
	}
	
	
	private List<DiaCatalogoDTO> getDiagnostiche(Connection conn,String codCommand) throws Exception {
		
		List<DiaCatalogoDTO> diag = new ArrayList<DiaCatalogoDTO>();
		
		StringBuilder sqlCommand = new StringBuilder("select dc.idcatalogodia, dc.descrizione ");
		sqlCommand.append(" from dia_catalogo dc ");
		sqlCommand.append(" where (dc.fk_cod_command_group = ? ");
		sqlCommand.append(" or dc.fk_cod_command = ?) ");
		sqlCommand.append(" and abilitata = 1 ");
		sqlCommand.append(" order by  dc.idcatalogodia asc ");
		log.debug("Query: "+sqlCommand.toString());
		log.debug("Query params: "+codCommand);
		
		PreparedStatement pstmt = conn.prepareStatement(sqlCommand.toString());
		pstmt.setString(1, codCommand);
		pstmt.setString(2, codCommand);
		ResultSet rs_qry = pstmt.executeQuery();

		while (rs_qry.next()) {
			Long idCatalog = rs_qry.getLong("idcatalogodia");
			String descr = rs_qry.getString("descrizione");
			
			diag.add(new DiaCatalogoDTO(idCatalog,codCommand,descr));
		}
		
		rs_qry.close();
		pstmt.close();
		
		return diag;
	}
	
	
	private String appendDiagsToMessage(String message,String codCommand,List<DiaCatalogoDTO> dd) {
		log.debug("Inserimento elenco diagnostiche nel messaggio");
		String complete = message + "<br/><br/>Elenco diagnostiche eseguite:<br/>";
		
		for(DiaCatalogoDTO d: dd) {
			String toAdd = d.getIdCatalogo().toString()+" - "+d.getDescrizione()+"<br/>";
			complete += toAdd;
		}
		
		return complete;
	}
}
