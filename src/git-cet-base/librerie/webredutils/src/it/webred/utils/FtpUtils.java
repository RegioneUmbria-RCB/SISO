package it.webred.utils;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

/**
 * Questa classe contiene una serie di funzioni FTP
 * @author Petracca Marco
 * @version $Revision: 1.2 $ $Date: 2009/08/27 13:25:14 $
 */
public class FtpUtils
{
    //public static DataInputStream is;       // Stream di Input
    private static BufferedReader is; 

    private static PrintStream os;           // Stream di Output
    private static Socket ftpSocket;         // Socket di comunicazione
    private static int NPort;                // Porta. Numero progressivo
    private static String TrMode;            // Transfer Mode. Ascii o Binary
  

     /**
     * Questa procedura si "logga" con il server, fornendo UserName e Password.
     * <br> Visualizza messaggi di errore nel caso UserName o Password siano errati
     * @param host Il nome o ip del server FTP
     * @param port La porta del server (se non impostata di default è la 21)  
     * @param user Nome utente
     * @param passwd Password utente
     * @return Ritorna TRUE se si e' loggato, FALSE altrimenti
     * @throws IOException
     */
    private static boolean OpenComm(String host,Integer port,String user, String passwd ) throws IOException
    {
    	if(port== null)
    		port=21;
    	// Apertura Socket e inizializzazione degli Stream di Input e Output
        ftpSocket=new Socket(host,port);
        os=new PrintStream(ftpSocket.getOutputStream());
        is=new BufferedReader(new InputStreamReader(ftpSocket.getInputStream()));
        NPort=50000;
    	
        String s="";
        boolean Ret=true,ok;

        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if(s.substring(0,4).compareTo("220 ")==0)
                        ok=false;
            }
        
        os.println("USER "+user);
        System.out.println("USER "+user);
        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if(s.substring(0,4).compareTo("331 ")==0)
                        ok=false;
            }
        os.println("PASS "+passwd);
        System.out.println("PASS ********");
        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if((s.substring(0,4).compareTo("230 ")==0) || (s.substring(0,4).compareTo("530 ")==0))
                        ok=false;
            }
        if(s.substring(0,4).compareTo("230 ")==0)
            System.out.println("Utente "+user+" Connesso");
        else
            Ret=false;

        return(Ret);
    }
    /**
     * Funzione per cambiare la directory corrente.
     * @param dir Nome della directory.
     * @throws IOException
     */
    private static void ChDir(String dir) throws IOException
    {
        String s="";
        boolean ok;

// Cambia la directory corrente
        os.println("CWD "+dir);
// Ciclo di attesa messaggio dal server
        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if((s.substring(0,4).compareTo("250 ")==0) || (s.substring(0,4).compareTo("550 ")==0))
                        ok=false;
            }
    }
    /**
     * Funzione per creare una nuova directory.
     * @param dir Nome della nuova directory
     * @throws IOException
     */
    private static void MkDir(String dir) throws IOException
    {
        String s="";
        boolean ok;

// Crea una nuova la directory
        
        os.println("MKD "+dir);
// Ciclo di attesa messaggio dal server
        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if((s.substring(0,4).compareTo("257 ")==0) || (s.substring(0,4).compareTo("550 ")==0))
                        ok=false;
            }
    }
   
    /**
     * Visualizza il percorso della directory corrente.
     * @return Restituisce il percorso.
     * @throws IOException
     */
    private static String PwDir() throws IOException
    {
        boolean ok;
        String s="",dir="";

        // Visualizza la directory corrente
        os.println("PWD");
        // Ciclo di attesa messaggio dal server
        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if(s.substring(0,4).compareTo("257 ")==0)
                    {
                    	ok=false;
                    	dir=s.substring(5,s.length());
                    }
            }
        return dir;
    }

    private static String GetPort()
    {
        int l,h;
        String R;

// Scompone il numero di porta (progressivo) in Byte Alto e Byte Basso
        h=NPort/256;
        l=NPort-(h*256);
        R=""+h+","+l;
        NPort++;

        return(R);
    }
    /**
     * Scarica un file dal sever.
     * @param nomeFile Nome del file da scaricare
     * @param percoDestinazione Percorso di destinazione del file (path comprensivo del nome).
     * @throws IOException
     */
    private static void GetFile(String nomeFile, String percoDestinazione) throws IOException
    {
        boolean ok,ok1=true;
        String s="",s1,P="",H="";
        int l;
        ServerSocket Sck;
        Socket sck;
        DataInputStream isd;
        RandomAccessFile f;

// Scarica un file
// Cicla finché il file è stato trasferito senza errori
        while(ok1)
            {
                s1="";
                H=ftpSocket.getInetAddress().getLocalHost().getHostAddress();
                for(l=0;l<H.length();l++)
                    if(H.charAt(l)=='.')
                        s1+=',';
                    else
                        s1+=H.charAt(l);
                P=GetPort();
// Imposta la porta
                System.out.println("PORT "+s1+","+P);
                os.println("PORT "+s1+","+P);
                ok=true;
                while(ok)
                    {
                        s=is.readLine();
                        System.out.println(s);
                        if(s.length()>5)
                            if((s.substring(0,4).compareTo("200 ")==0) || (s.substring(0,4).compareTo("500 ")==0))
                                ok=false;
                    }
                if(s.substring(0,4).compareTo("200 ")==0)
                    {
// Imposta il tipo di file
                        System.out.println("TYPE "+TrMode);
                        os.println("TYPE "+TrMode);
                        ok=true;
                        while(ok)
                            {
                                s=is.readLine();
                                System.out.println(s);
                                if(s.length()>5)
                                    if((s.substring(0,4).compareTo("200 ")==0) || (s.substring(0,4).compareTo("500 ")==0))
                                        ok=false;
                            }
// Apre il nuovo socket
                        Sck=new ServerSocket(NPort-1);
// Manda il comando di download
                        System.out.println("RETR "+nomeFile);
                        os.println("RETR "+nomeFile);
                        ok=true;
                        while(ok)
                            {
                                s=is.readLine();
                                System.out.println(s);
                                if(s.length()>5)
                                    if((s.substring(0,4).compareTo("150 ")==0) || (s.substring(0,4).compareTo("425 ")==0))
                                        ok=false;
                            }
                        if(s.substring(0,4).compareTo("150 ")==0)
                            {
// Se tutto è andato bene, si prepara ad accettare i dati
                                sck=Sck.accept();
                                isd=new DataInputStream(sck.getInputStream());
                                f=new RandomAccessFile(percoDestinazione,"rw");
                                ok1=false;
// Scrive i dati su disco
                                f.write(isd.read());
                                while(isd.available()!=0)
                                    f.write(isd.read());
                                f.close();
                                ok=true;
                                while(ok)
                                    {
                                        s=is.readLine();
                                        System.out.println(s);
                                        if(s.length()>5)
                                            if(s.substring(0,4).compareTo("226 ")==0)
                                                ok=false;
                                    }
                                sck.close();
                            }
                        Sck.close();
                    }
            }
    }
    /**
     * Esegue l' upload di un file sul server
     * @param nome File Il nome che si vuole dare a file.
     * @param file Array di byte con il contenuto del file.
     * @param directoryDest Directory di destinazione del file.
     * @throws IOException
     */
    private static void PutFile(String nomeFile, byte[] file) throws IOException
    {
        boolean ok,ok1=true;
        String s="",s1,P="",H="";//NFile="",NFileS="";
        int l;
        ServerSocket Sck;
        Socket sck;
        PrintStream osd;
        RandomAccessFile f;

// Procedura di upload
 
        while(ok1)
            {
                s1="";
                H=ftpSocket.getInetAddress().getLocalHost().getHostAddress();
                for(l=0;l<H.length();l++)
                    if(H.charAt(l)=='.')
                        s1+=',';
                    else
                        s1+=H.charAt(l);
                P=GetPort();
                System.out.println("PORT "+s1+","+P);
                os.println("PORT "+s1+","+P);
                ok=true;
                while(ok)
                    {
                        s=is.readLine();
                        System.out.println(s);
                        if(s.length()>5)
                            if((s.substring(0,4).compareTo("200 ")==0) || (s.substring(0,4).compareTo("500 ")==0))
                               ok=false;
                    }
                if(s.substring(0,4).compareTo("200 ")==0)
                    {
                		//remmato perche il comando non esiste
                        //System.out.println("TYPE "+TrMode);
                        //os.println("TYPE "+TrMode);
                        ok=true;
                        while(ok)
                            {
                                s=is.readLine();
                                System.out.println(s);
                                if(s.length()>5)
                                    if((s.substring(0,4).compareTo("200 ")==0) || (s.substring(0,4).compareTo("500 ")==0))
                                        ok=false;
                            }
                        System.out.println("STOR "+nomeFile);
                        os.println("STOR "+nomeFile);
                        Sck=new ServerSocket(NPort-1);
                        ok=true;
                        while(ok)
                            {
                                s=is.readLine();
                                System.out.println(s);
                                if(s.length()>5)
                                    if((s.substring(0,4).compareTo("150 ")==0) || (s.substring(0,4).compareTo("425 ")==0))
                                        ok=false;
                            }
                        if(s.substring(0,4).compareTo("150 ")==0)
                            {
                                sck=Sck.accept();
                                osd=new PrintStream(sck.getOutputStream());
                                ok1=false;
                                for (int i = 0; i < file.length; i++)
								{
									osd.write(file[i]);
								}
                                //while(i<f.length())
                                  //  osd.write(file[0]);

                                sck.close();
                                ok=true;
                                while(ok)
                                    {
                                        s=is.readLine();
                                        System.out.println(s);
                                        if(s.length()>5)
                                            if(s.substring(0,4).compareTo("226 ")==0)
                                                ok=false;
                                    }
                            }
                        Sck.close();
                    }
            }
    }
    /**
     * Visualizza il contenuto della directory corrente
     * @throws IOException
     */
    private static void GetDir() throws IOException
    {
        boolean ok,ok1;
        String s="",s1,P="",H="";
        int l;
        ServerSocket Sck;
        Socket sck;
        DataInputStream isd;

// Visualizza il contenuto della directory corrente
        ok1=true;
        while(ok1)
            {
                s1="";
                H=ftpSocket.getInetAddress().getLocalHost().getHostAddress();
                for(l=0;l<H.length();l++)
                    if(H.charAt(l)=='.')
                        s1+=',';
                    else
                        s1+=H.charAt(l);
                P=GetPort();
// Manda la porta da usarsi
                System.out.println("PORT "+s1+","+P);
                os.println("PORT "+s1+","+P);
                ok=true;
                while(ok)
                    {
                        s=is.readLine();
                        System.out.println(s);
                        if(s.length()>5)
                            if((s.substring(0,4).compareTo("200 ")==0) || (s.substring(0,4).compareTo("500 ")==0))
                                ok=false;
                    }
                if(s.substring(0,4).compareTo("200 ")==0)
                    {
// Apre il socket
                        Sck=new ServerSocket(NPort-1);
// Manda il comando
                        System.out.println("LIST");
                        os.println("LIST");
                        ok=true;
                        while(ok)
                            {
                                s=is.readLine();
                                System.out.println(s);
                                if(s.length()>5)
                                    if((s.substring(0,4).compareTo("150 ")==0) || (s.substring(0,4).compareTo("425 ")==0))
                                        ok=false;
                            }
                        if(s.substring(0,4).compareTo("150 ")==0)
                            {
// Se tutto è andato bene, visualizza i dati che arrivano dalla nuova porta
                                sck=Sck.accept();
                                isd=new DataInputStream(sck.getInputStream());
                                ok1=false;
                                s=isd.readLine();
                                System.out.println(s);
                                while(isd.available()!=0)
                                    {
                                        s=isd.readLine();
                                        System.out.println(s);
                                    }
                                ok=true;
                                while(ok)
                                    {
                                        s=is.readLine();
                                        System.out.println(s);
                                        if(s.length()>5)
                                            if(s.substring(0,4).compareTo("226 ")==0)
                                                ok=false;
                                    }
                                sck.close();
                            }
                        Sck.close();
                    }
            }
    }
    /**
     * Imposta la modalità di trasferimento file.
     * @param Mod La modalità (A=ASCII, I=Binary)
     * @throws IOException
     */
    private static void SetTrMode(String Mod) throws IOException
    {
        String s="";
        boolean ok;
        TrMode=Mod;
        os.println("TYPE "+Mod);
        System.out.println("TYPE "+Mod);
        ok=true;
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if(s.substring(0,4).compareTo("200 ")==0)
                        ok=false;
            }
    }

    /**
     * Chiude la comunicazione con FTP e si disconnette
     * @throws IOException
     */
    private static void CloseConnection() throws IOException
    {
    	
    	boolean ok=true;
        String s="";
        os.println("QUIT");
        System.out.println("QUIT");
        while(ok)
            {
                s=is.readLine();
                System.out.println(s);
                if(s.length()>5)
                    if(s.substring(0,4).compareTo("221 ")==0)
                        ok=false;
            }

        is.close();
        os.close();
        ftpSocket.close();
    }

    
  /*  public static void main(String args[])
    {
        try
            {
        	uploadFile("ftp.webred.it",21,"oracle","oracle", "Prova.txt",new String("Testo di prova").getBytes() ,"/dirProva/prova1" );
            }
        catch(Exception e)
            {
                System.out.println("Eccezione "+e);
            }
    }*/
    
    /**
     * Esegue l' upload di un file sul server. 
     * @param host Il nome o ip del server FTP
     * @param port La porta del server (se non impostata di default è la 21)  
     * @param user Nome utente
     * @param passwd Password utente
     * @param nomeFile File Il nome che si vuole dare a file.
     * @param file Array di byte con il contenuto del file.
     * @param directoryDest Directory di destinazione del file.
     * @throws Exception
     */
    public static void uploadFile(String host,Integer port,String user,String passwd,String nomeFile, byte[] file,String directoryDest )
    throws Exception
    {	
    	if (host==null || host.trim().equals(""))
    		throw new Exception("FtpUtils.uploadFile: Il nome dell'host non è stato valorizzato correttamente!") ;
    	if (nomeFile==null || nomeFile.trim().equals(""))
    		throw new Exception("FtpUtils.uploadFile: Il nome del file non è stato valorizzato correttamente!") ;

    	OpenComm(host, port,user,passwd);
        	PwDir();
        	if (directoryDest!=null) {
        		StringTokenizer st =new StringTokenizer(directoryDest,"/");
	        	while (st.hasMoreTokens()) 
	        	{
					String dir =st.nextToken();
					MkDir(dir);
		        	ChDir(dir);
				}
        	}
        	
        	PutFile( nomeFile,  file); 
        
    }

    
    
}
