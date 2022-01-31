package it.webred.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Classe dedicata alla spedizione di mail
 * @author Petracca Marco
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public class SendMail
{
	private static final int  SMTP_PORT = 25;
	private static final char SMTP_ERROR_CODE1 = '4';
	private static final char SMTP_ERROR_CODE2 = '5';
	 

	//La funzione principale
	 /**
	 * La funzione  che si occupa dell' invio della mail
	 * 
	 * @param host Indirizzo ip dell'host 
	 * @param domain Nome di dominio dell' host,
	 * @param sender Mittente
	 * @param recipients Destinatari (separati da ",")
	 * @param subject Oggetto della mail
	 * @param maildata Corpo della mail
	 * @param sessionTrace vettore sul quale le funzioni ausiliarie memorizzeranno tutte le transizioni.
	 * @throws IOException
	 */
	public static void sendMail(String host,String domain,String sender,String recipients,String subject,String maildata,Vector sessionTrace) throws IOException {

		
//	 il Socket che collega sender a receiver
	Socket mailSocket;
//	 gli stream di input e output verso il receiver
	  BufferedReader socketIn;
	  DataOutputStream socketOut;
	  StringTokenizer tokenizer;
//	 apre la connessione verso il server SMTP e collega gli stream
	  mailSocket = new Socket(host, SMTP_PORT); 
	  socketIn = new BufferedReader(new InputStreamReader(mailSocket.getInputStream()) ); 
	  socketOut = new DataOutputStream(mailSocket.getOutputStream()); 
//	 riceve la prima risposta dal server 
	  readReply(socketIn, sessionTrace); 
//	 il saluto al server, e relativa risposta
	if(domain!=null && !domain.trim().equals("") )
	  {
		sendCommand(socketOut, "HELO " + domain, sessionTrace); 
		readReply(socketIn, sessionTrace);
	  }
//	 invia l'indirizzo del mittente
	  sendCommand(socketOut, "MAIL FROM: " + sender, sessionTrace); 
	  readReply(socketIn, sessionTrace); 
	  // invia l'elenco dei destinatari, che nell'argomento erano passati
	  // sotto forma di un'unica stringa, separati dalla virgola
	  // per ogniuno dei destinatari invia il comando RCPT TO
	  // e legge la risposta del server
	  tokenizer = new StringTokenizer(recipients, ","); 
	  while (tokenizer.hasMoreElements()) { 
	   sendCommand(socketOut, "RCPT TO: " + tokenizer.nextToken(), sessionTrace); 
	   readReply(socketIn, sessionTrace);
	  } 
	  // invia il comando <DATA>. Da ora il receiver 
	  // interpreta gli input
	  // come corpo del testo e si attende la sequenza <CRLF>.<CRLF>
	  // come conclusione del corpo
	  sendCommand(socketOut, "DATA", sessionTrace); 
	  readReply(socketIn, sessionTrace); 

	  // crea la stringa body e vi scrive i <memo header items>
	  String body = "";
	  body += "From: " + sender + "\n";
	  body += "To: " + recipients + "\n";
	  body += "Subject: " + subject + "\n";

	  // aggiunge il corpo vero e proprio della mail
	  body += maildata;

	  // invia il corpo della mail e la sequenza di chiusura
	  sendCommand(socketOut, body, sessionTrace); 
	  sendCommand(socketOut, ".", sessionTrace); 
	  readReply(socketIn, sessionTrace); 

	  // conclude la sessione
	  sendCommand(socketOut, "QUIT", sessionTrace); 
	  readReply(socketIn, sessionTrace); 
	 }
	 
	 

/*	Le funzioni di servizio
	Le funzioni di servizio hanno lo scopo di inviare i comandi al receiver e di leggere le risposte (e generare una eccezione in caso di codice di errore).
*/
	 /**
	 * Invia un comando attraverso uno stream di output e tiene traccia della stringa su di un vettore
	 * @param out DataOutputStream
	 * @param command Il comando da inviare
	 * @param sessionTrace Vettore sul quale memorizzare tutte le transizioni.
	 * @throws IOException
	 */
	private static void sendCommand(DataOutputStream out, String command, Vector sessionTrace) 
	 throws IOException { 
	  out.writeBytes(command + "\r\n"); 
	  sessionTrace.addElement(command); 
	  System.out.println(command); 
	 } 

	 /**
	 * Legge la risposta del receiver.
	 * <br>Se il server risponde con un codice 4xx o 5xx significa che è occorso un errore
	 * @param reader BufferedReader
	 * @param sessionTrace Vettore sul quale memorizzare tutte le transizioni.
	 * @throws IOException
	 */
	private static void readReply(BufferedReader reader, Vector sessionTrace) 
	 throws IOException { 
	  String reply; 
	  char   statusCode; 
	  reply = reader.readLine(); 
	  statusCode = reply.charAt(0); 
	  sessionTrace.addElement(reply); 
	  System.out.println(reply); 
	  if ( (statusCode == SMTP_ERROR_CODE1) | (statusCode == SMTP_ERROR_CODE2) )
	   { throw (new IOException("SMTP: " + reply));}
	 } 
	
	
	
	
}
