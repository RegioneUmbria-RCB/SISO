package it.webred.SisterClient;
import it.webred.SisterClient.dto.ProvinciaDTO;
import it.webred.SisterClient.dto.VisuraDTO;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import junit.framework.Assert;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

class TestClass extends JFrame  
{  

	
	 public TestClass() {
		 initUI();
	    }
	 
	 
	 public final void initUI() {

		 try {
		        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    } catch (Exception evt) {}
		  
		   
		    getContentPane().setLayout(new FlowLayout());
		    final JTextField cf = new JTextField("codice fiscale", 15);
		    getContentPane().add(cf);

		    JButton vai = new JButton("Ottieni Visura");
		    getContentPane().add(vai);
		    
		    
		    final JTextArea result = new JTextArea(40,40);
		    result.setAutoscrolls(true);
		    getContentPane().add(result);
		    
		    JButton quitButton = new JButton("Esci");
		    quitButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent actionEvent) {
		            System.exit(0);
		        }
		    });


		    
		    vai.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent actionEvent) {
		        	try {
		        		result.setText("Sto cercando i dati sul portale SISTER ....");
		        		VisuraDTO vis = new VisuraCatastale().visuraCatastale(cf.getText(),"PTTFBA65E01F205P","PTTFBA65/*20");
		        		//testo = testo.substring(testo.lastIndexOf("Soggetto selezionato"));
		        		
		        		String s = vis.getCognome()+"\n"+vis.getNome()+"\n"+vis.getDataNasc()+"\n";
		        		s+= vis.getCf()+"\n"+vis.getComuneNasc()+"\n"+vis.getTipoRichiesta()+"\n";
		        		for(ProvinciaDTO prov : vis.getListaProv())
		        			s += prov.getProvincia()+","+prov.getNumFabbricati()+","+prov.getNumTerreni()+"\n";
		        		
		        		result.setText(s);
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        		String errText = "Impossibile ottenere una visura!";
		        		if (e.getMessage().toLowerCase().indexOf("Utente gia' connesso da altra postazione".toLowerCase()) > -1) {
		        			errText += (" Utente gia' connesso da altra postazione.");
		        		}
		        		//
						result.setText(errText);

					}
		        }
		    });

		    
		    
		    getContentPane().add(quitButton);
		    
		    pack();
		    setVisible(true);
	    }
	 

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	new TestClass().setVisible(true);

	            }
	        });
	    }
	}
