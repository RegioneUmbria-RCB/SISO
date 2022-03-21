package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.WarningAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class SendEmail extends Command implements Rule    {

	private static final org.apache.log4j.Logger log = Logger.getLogger(SendEmail.class.getName());

		
	public SendEmail(BeanCommand bc) {
		
		super(bc);

	}
	
	public SendEmail(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx) throws CommandException {
		
		CommandAck retAck = null;
		
		try {
			log.debug("Recupero parametro da contesto");

			String mailServer = null;
			ComplexParam param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			
			HashMap<String,ComplexParamP> pars = param.getParams();
			Set set = pars.entrySet();
			Iterator it = set.iterator();
			int i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				if(o == null) 	
					throw new CommandException("Mail server non specificato");
				
				mailServer = o.toString();
			}
			
			
			String mailPort = null;
			param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			pars = param.getParams();
			set = pars.entrySet();
			it = set.iterator();
			i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				if(o == null) 	
					throw new CommandException("Mail server port non specificata");
				
				mailPort = o.toString();
			}
			
			
			String mailMitt = null;
			param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.3.descr"));
			
			pars = param.getParams();
			set = pars.entrySet();
			it = set.iterator();
			i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				if(o == null) 	
					throw new CommandException("Indirizzo mittente non specificato");
				
				mailMitt = o.toString();
			}
			
			
			String mailDest = null;
			param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.4.descr"));
			
			pars = param.getParams();
			set = pars.entrySet();
			it = set.iterator();
			i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				if(o == null) 	
					throw new CommandException("Indirizzo destinatari/o non specificati/o");
				
				mailDest = o.toString();
			}
			
			
			String soggetto = null;
			param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.5.descr"));
			
			pars = param.getParams();
			set = pars.entrySet();
			it = set.iterator();
			i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				if(o == null) 	
					throw new CommandException("Oggetto email non specificato");
				
				soggetto = o.toString();
			}
			
			
			String messaggio = null;
			param = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.6.descr"));
			
			pars = param.getParams();
			set = pars.entrySet();
			it = set.iterator();
			i =1;
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				if(o == null) 	
					throw new CommandException("Messaggio email non specificato");
				
				messaggio = o.toString();
			}
			
			Properties properties = new Properties();
	        properties.put("mail.smtp.host", mailServer);
	        properties.put("mail.smtp.port", mailPort);
	        Session session = Session.getDefaultInstance(properties, null);
			MimeMessage message = new MimeMessage(session);
 			
			message.setFrom(new InternetAddress(mailMitt));
			//message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailDest));
			message.setRecipients(Message.RecipientType.TO, mailDest);
			message.setSubject(soggetto);
			message.setSentDate(new Date());
			
			// Set the email message text
			message.setContent(messaggio, "text/html");
			
			// SEND MAIL
			System.out.println(" START Transport.send");
			Transport.send(message);
			System.out.println(" END Transport.send");
		
			retAck = new ApplicationAck("Spedizione Terminata Correttamente");
			
		}catch (MessagingException e) {
			log.error("Eccezione messaggio: "+e.getMessage(),e);
			retAck = new ErrorAck(e);
		}catch (CommandException e) {
			log.error("Eccezione messaggio: "+e.getMessage());
			retAck = new WarningAck(e.getMessage());
		}catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck = new ErrorAck(e);
		}
		
		return retAck;
	}






	
	

	

	
	
	
}
