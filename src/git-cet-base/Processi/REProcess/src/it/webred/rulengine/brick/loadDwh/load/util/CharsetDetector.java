package it.webred.rulengine.brick.loadDwh.load.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import sun.io.ByteToCharConverter;


public class CharsetDetector {

    public Charset detectCharset(File f, String[] charsets, boolean readAllFile) {

        Charset charset = null;

        for (String charsetName : charsets) {
            charset = detectCharset(f, Charset.forName(charsetName), readAllFile);
            if (charset != null) {
                break;
            }
        }

        return charset;
    }

    private Charset detectCharset(File f, Charset charset, boolean readAllFile) {
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));

            CharsetDecoder decoder = charset.newDecoder();
            decoder.reset();
            
            boolean identified = false;
            byte[] buffer = new byte[512]; 
            
            if (readAllFile) {
            	 while ((input.read(buffer) != -1)) {
            		identified = identify(buffer, decoder);
            		if (!identified)
            			break;
                 }
            } else {            	           
                while ((input.read(buffer) != -1) && (!identified)) {
                	identified = identify(buffer, decoder);
                }
            }
            
            input.close();

            if (identified) {
                return charset;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    private boolean identify(byte[] bytes, CharsetDecoder decoder) {
        try {
            decoder.decode(ByteBuffer.wrap(bytes));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }

    //per test
    public static void main(String[] args) {
        File f = new File("C:\\Load\\F704\\concessioni\\ConcessioniEdilizie_29022012_12.01.06.txt");
        File f1 = new File("C:\\Load\\F704\\concessioni\\ConcessioniEdilizie_29022012_12.01.06_NEW.txt");

        String[] charsetsToBeTested = {"UTF8", "windows-1253", "ISO-8859-7"};

        CharsetDetector cd = new CharsetDetector();
        Charset charset = cd.detectCharset(f, charsetsToBeTested, true);
      
        if (charset != null) {
        	System.out.println(charset.name());
            try {
                BufferedReader fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
                String currentLine = null;
                PrintWriter out = new PrintWriter(f1, "UTF8");
                while ((currentLine = fileIn.readLine()) != null) {
                	out.println(new String(currentLine.getBytes("windows-1253")));
                }
                out.close();
                fileIn.close();
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }

        }else{
            System.out.println("Unrecognized charset.");
        }
    }
}
