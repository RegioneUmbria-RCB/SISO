package it.webred.AMProfiler.util;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

import java.util.*;

public class SendEmail {

	protected static Logger logger = Logger.getLogger("am.log");
	
	public boolean sendMail(String mailServer, String mailPort, String recipients[ ], String subject, String message , String from) throws MessagingException
	 {
	     boolean debug = false;
	     boolean esito = true;
	     
	     try{
	     
		     //Set the host smtp address
		     Properties props = new Properties();
		     props.put("mail.smtp.host", mailServer);
		     props.put("mail.smtp.port", mailPort);
	
		     // create some properties and get the default Session
		     Session session = Session.getDefaultInstance(props, null);
		     session.setDebug(debug);
	
		     // create a message
		     Message msg = new MimeMessage(session);
	
		     // set the from and to address
		     InternetAddress addressFrom = new InternetAddress(from);
		     msg.setFrom(addressFrom);
	
		     InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
		     for (int i = 0; i < recipients.length; i++)
		     {
		         addressTo[i] = new InternetAddress(recipients[i]);
		     }
		     msg.setRecipients(Message.RecipientType.TO, addressTo);
	
		    // Setting the Subject and Content Type
		     msg.setSubject(subject);
		     msg.setContent(message, "text/plain");
		     Transport.send(msg);
	     
	     }catch(Exception e){
	    	 logger.error(e.getMessage(),e);
	    	 esito = false;
	     }
	     return esito;
	 }
	
}
