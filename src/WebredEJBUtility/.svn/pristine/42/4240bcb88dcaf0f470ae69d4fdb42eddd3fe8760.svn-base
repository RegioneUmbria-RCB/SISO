package it.webred.mailing;

import it.webred.utilities.CommonUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class MailUtils {
	private static final Logger logger = Logger.getLogger(MailUtils.class);

	public static class MailParamBean implements Serializable {

		private static final long serialVersionUID = 1L;

		private boolean requireAuth = false;
		private Properties mailProperties;

		private String host = "";
		private String port = "";
		private String username = "";
		private String password = "";
		private String from = "";
		private String parameters = "";

		public String getHost() {
			return host;
		}

		public String getPort() {
			return port;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getFrom() {
			return from;
		}

		public boolean isRequireAuth() {
			return requireAuth;
		}

		public Properties getMailProperties() {
			return mailProperties;
		}

		protected void setup(String host, String port, String username, String password, String from, String parameters, boolean requireAuth) {

			this.host = StringUtils.trimToEmpty(host);
			this.port = StringUtils.trimToEmpty(port);
			this.username = StringUtils.trimToEmpty(username);
			this.password = StringUtils.trimToEmpty(password);
			this.from = StringUtils.trimToEmpty(from);
			this.parameters = StringUtils.trimToEmpty(parameters);

			this.requireAuth = requireAuth;

			mailProperties = System.getProperties();
			mailProperties.put("mail.smtp.host", host);
			mailProperties.put("mail.smtp.port", port);
			mailProperties.put("mail.smtp.auth", "" + requireAuth);

			String[] aParams = this.parameters.split(";");
			if (Arrays.asList(aParams).contains("ssl")) {
				mailProperties.put("mail.smtp.socketFactory.port", port);
				mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				mailProperties.put("mail.smtp.socketFactory.fallback", "false");
			}
		}

		public MailParamBean(String host, String port, String username, String password, String from, String parameters, boolean requireAuth) {
			setup(host, port, username, password, from, parameters, requireAuth);
		}

		public MailParamBean(String string) {
			setup(string);
		}

		public MailParamBean(){
			setup( "hiweb.properties" );
		}

		protected void setup( String fileProperties)
		{
			try 
			{
				Properties props = new Properties();

				props.load(this.getClass().getResourceAsStream(fileProperties));

			    String host = props.getProperty("host");
			    String port = props.getProperty("port");
			    String username = props.getProperty("username");
			    String password  = props.getProperty("password");
			    String from = props.getProperty("from");
			    String parameters = props.getProperty("parameters");
			    Boolean requireAuth = Boolean.parseBoolean( props.getProperty("requireAuth") );

			    setup(host, port, username, password, from, parameters, requireAuth);
			    
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public String toString() {
			String retVal = "";
			retVal = String.format("host=%s | port=%s | username=%s | from=%s | parameters=%s", host, port, username, from, parameters);
			return retVal;
		}
	}

	public static class MailAddressList extends LinkedList<String> {

		private static final long serialVersionUID = 1L;

		public MailAddressList(){
		}
		
		public MailAddressList( String address ){
			this.add(address);
		}
		
		public MailAddressList( String address, String separator ){
			List<String> list = CommonUtils.splitString(address, separator); 
			if( list != null ) {
				for (String item : list) {
					this.add(item);
				}
			}
		}

		public MailAddressList( List<String> list )
		{
			if( list != null ) {
				for (String item : list) {
					this.add(item);
				}
			}
		}
		
		@Override
		public boolean isEmpty(){
			for (String address : this) {
				if (StringUtils.isNotEmpty(address)) {
					return false;
				}
			}
			
			return true;
		}
		
		@Override
		public boolean add( String item ){
			if( StringUtils.isEmpty(item))
				return false;
			if( this.contains(item) )
				return false;
			
			return super.add(item);
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public boolean addAll(Collection collection) {
			for (Object object : collection) {
				add((String)object);
			}
			return true;
		}

		@Override
		public String toString(){
			String res = "";
			for( int i = 0; i < size(); i++) {
				res += get(i);
				if( i + 1 < size() )
					res += ",";
			}
			return res;
		}
	}
	
	protected static void addAttachments(Multipart multipart, FileDataSource... attachments) throws MessagingException, IOException {

		if (attachments != null && multipart != null) {
			for (FileDataSource fileDataSource : attachments) {
				if (fileDataSource == null)
					continue;

				// Set the email attachment file
				MimeBodyPart attachmentPart = new MimeBodyPart();

				attachmentPart.setDataHandler(new DataHandler(fileDataSource));
				attachmentPart.setFileName(fileDataSource.getName());

				multipart.addBodyPart(attachmentPart);
			}
		}
	}

	public static void sendEmail( MailAddressList addressListTO, MailAddressList addressListCC, MailAddressList addressListBCC, String subject, String messageBody, FileDataSource... attachments )
	{
	    MailParamBean mailParam = new MailParamBean();
	    logger.info("Tentativo invio mail .... " + subject);
	    sendEmail(mailParam, addressListTO, addressListCC, addressListBCC, subject, messageBody, attachments);;
	    logger.info("Invata mail " + subject);
	}
	
	public static void sendEmail(MailParamBean mailParam, MailAddressList addressListTO, MailAddressList addressListCC, MailAddressList addressListBCC, String subject, String messageBody, FileDataSource... attachments) {
		try {
			if( addressListTO == null )
				return;
			
			boolean isMailingListEmpty = addressListTO.isEmpty();
			if (isMailingListEmpty){
				logger.debug("Email non inviata perche address list non contiene indirizzi");
				return;
			}

			// Get a mail session
			javax.mail.Session session;
			if (mailParam.isRequireAuth()) {
				final String user = mailParam.getUsername();
				final String pwd = mailParam.getPassword();
				session = javax.mail.Session.getInstance(mailParam.getMailProperties(), new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, pwd);
					}
				});
			}
			else
				session = javax.mail.Session.getInstance(mailParam.getMailProperties(), null);

			// Define a new mail message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailParam.getFrom()));
			message.setReplyTo(new javax.mail.Address[]
					{
					    new InternetAddress(mailParam.getFrom())
					});
			message.setSubject(subject);

			if( addressListTO != null ) for (String address : addressListTO)
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			if( addressListCC != null ){
				for (String address : addressListCC){
					if(!addressListTO.contains(address))
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(address));
				}
			}
			if( addressListBCC != null ) for (String address : addressListBCC)
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(address));

			// Create a message part to represent the body text
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(messageBody, "text/html");
			// messageBodyPart.setText(messageBody);

			// use a MimeMultipart as we need to handle the file attachments
			Multipart multipart = new MimeMultipart();

			// add the message body to the mime message
			multipart.addBodyPart(messageBodyPart);

			// add any file attachments to the message
			addAttachments(multipart, attachments);

			// Put all message parts in the message
			message.setContent(multipart);

			// Send the message
			Transport.send(message);

			String toString = ""; if( addressListTO != null ) toString = addressListTO.toString();
			String ccString = ""; if( addressListCC != null ) ccString = addressListCC.toString();
			String bccString = ""; if( addressListBCC != null ) bccString = addressListBCC.toString();
			logger.debug("Email inviata a TO: " + toString + " CC: " + ccString + " BCC: " + bccString );
		}
		catch (Exception ex) {
			logger.error("Errore durante invio Email: " + ex.getMessage());
			throw new MailException("Errore durante invio Email", ex);
		}
	}

}
