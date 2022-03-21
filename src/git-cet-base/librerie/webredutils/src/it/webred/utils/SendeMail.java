package it.webred.utils;

import javax.mail.*;
import java.util.*;

import javax.mail.internet.*;
import javax.activation.*;
import java.io.*;


public class SendeMail {

  private static int  SMTP_PORT = 25;

  public static void sendeMail(String host,Integer port, String from, String toAddress, String user, String password,String subject, String message) throws Exception {
    try {
    boolean autenticazione= user!=null && !user.equals("")&& password!=null && !password.equals("");
    	
      StringBuffer sb = new StringBuffer( );
      //Recuperi (ed imposti) le proprietà di sistema
      Properties props = System.getProperties( );
      props.put("mail.smtp.host", host);
      props.put("mail.debug", "true");
      if(autenticazione)
      props.put("mail.smtp.auth","true");
      //Inizializzi una sessione con le proprietà prima impostate
      Session session = Session.getDefaultInstance(props, null);
      session.setDebug(true);
      if(port!=null )
    	  SMTP_PORT=port.intValue();
     
      if(autenticazione)
    	  session.setPasswordAuthentication(new URLName("smtp",host,SMTP_PORT,"INBOX",user,password), new PasswordAuthentication(user,password));
      
      MimeMessage msg = new MimeMessage(session);
      //Imposti l'indirizzo del mittente
      msg.setFrom(new InternetAddress(from));

      StringTokenizer tokenizer = new StringTokenizer(toAddress, ","); 
	  while (tokenizer.hasMoreElements()) { 
		  //Imposti l'indirizzo del destinatario
          msg.addRecipient(Message.RecipientType.TO, new InternetAddress(tokenizer.nextToken()));
	  } 
     
      //Imposti il soggetto
      msg.setSubject(subject);
      //Appendi il messaggio
      sb.append(message);
	  //Appendi la firma
	  sb.append(" - " + from + " - ");
	  //Aggiungi il messaggio al MimeMessage
      msg.setText(sb.toString( ));  
      //Invii il messaggio via smtp
      Transport tr = session.getTransport("smtp");
      if(autenticazione)
      tr.connect(host, user, password);
      else
    	  tr.connect();
      msg.saveChanges();
      tr.sendMessage(msg, msg.getAllRecipients());
      tr.close();
    }
    catch (MessagingException e) {
      System.out.println(e);
    }
  } 
  
  
}
