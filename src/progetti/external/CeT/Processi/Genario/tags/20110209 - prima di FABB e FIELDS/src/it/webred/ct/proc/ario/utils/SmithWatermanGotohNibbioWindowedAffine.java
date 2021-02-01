package it.webred.ct.proc.ario.utils;

import it.webred.utils.GenericTuples;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWaterman;
import uk.ac.shef.wit.simmetrics.similaritymetrics.TagLink;

public class SmithWatermanGotohNibbioWindowedAffine {

	protected static Properties props = null;
	protected static final Logger log = Logger.getLogger(SmithWatermanGotohNibbioWindowedAffine.class.getName());
	
	public static GenericTuples.T2<Boolean,Float> getSimilarity(String sedime1, String sedime2, String s1, String s2) throws Exception{
			
		//Variabili
		boolean sedimeUguale = false;		
		float percTot = 0;
		
		float limitePercSmith = 0;
		float limitePercTag = 0;
		float limitePercJako = 0;
		float limitePercTrig =  0;
						
		boolean smith = false;
		boolean tagLink = false;
		boolean jako = false;		
		boolean tri = false;
						
		List<Float> listPerc = new ArrayList<Float>();
			
		
		try{	
						
			//Imposto i limiti per i confronti delle percentuali valide
			limitePercSmith = Float.parseFloat(getProperty("smithwaterman.perc.limite.fix"));
			limitePercTag = Float.parseFloat(getProperty("taglink.perc.limite.fix"));
			limitePercJako = Float.parseFloat(getProperty("jako.perc.limite.fix"));
			limitePercTrig =  Float.parseFloat(getProperty("trig.perc.limite.fix"));
			
			
			//**
			//* Effettuo il controllo su ugualianza Sedime
			//**
			if(sedime1!=null && sedime1.equals(sedime2)){
				sedimeUguale = true;
			}else{
				sedimeUguale = false;			
			}
			
			
			//**
			//* Algoritmo di Smith
			//*

			float resSmith = new SmithWaterman().getSimilarity(s1,s2);						
			
			if(resSmith>limitePercSmith){	
				smith = true;			
				listPerc.add(resSmith);
			}	
			else{			
				smith = false;
			}	
			
			
			//**
			//* Algoritmo di TagLink
			//*			
			
			float resTag = new TagLink().getSimilarity(s1,s2);

			if(resTag>limitePercTag){	
				tagLink = true;				
				listPerc.add(resTag);
			}	
			else{			
				tagLink = false;
			}	
						
			
			//**
			//* Algoritmo di Jaro-Winkler
			//*

			
			//Numero trasposizioni
			int t = 0;
			 
			//Numero caratteri abbinati
			int m = 0;
			 
			//Calcolo caratteri abbinati
			int matchRange = Math.max(s1.length(), s2.length()) / 2 - 1;
			int carAbb = 0;
			int numAssDiversi = 0;
			
			ArrayList<String> carrAbbinati = new ArrayList<String>();
			String[] carrAss1 = null;
			String[] carrAss2 = null;
			String nuovaString1 = "";
			String nuovaString2 = "";									
					
			for(int i = 0; i<s1.length(); i++){
				
				boolean trovato = false;			
				String c1 = s1.substring(i, i+1);
				
				for(int j=0; ((j<s2.length()) && !trovato);j++){
					
					String c2 = s2.substring(j,j+1);
					
					if (i-j<=matchRange){
								
						if(c2.equals(c1)){
																																
							carrAbbinati.add(c2);
							carAbb = carAbb + 1;
							trovato = true;					
						}
											
					}	
					
				}		
			}
			
			String[] v1 = getArrayStringa(s1);
			String[] v2 = getArrayStringa(s2);		
			
			nuovaString1 = s1;
			for(int i=0; i<v1.length; i++){
							
				String c = v1[i];		
				boolean res = carrAbbinati.contains(c);
				
				if(!res){
					
					String sub1 = nuovaString1.substring(0, nuovaString1.indexOf(c));
					String sub2 = nuovaString1.substring(nuovaString1.indexOf(c)+1);						
					
					nuovaString1 = sub1+sub2;												
				}
			}
			
			carrAss1 = new String[carAbb];		
			carrAss1 = getArrayStringa(nuovaString1);
							
			nuovaString2 = s2;
			for(int i=0; i<v2.length; i++){
							
				String c = v2[i];
				
				boolean res = carrAbbinati.contains(c);
				
				if(!res){
					
					String sub1 = nuovaString2.substring(0, nuovaString2.indexOf(c));
					String sub2 = nuovaString2.substring(nuovaString2.indexOf(c)+1);						
					
					nuovaString2 = sub1+sub2;				
					
				}
			}
			
			carrAss2 = new String[carAbb];
			carrAss2 = getArrayStringa(nuovaString2);
			
			//Calcolo numero trasposizioni					
			for(int k = 0; k < carrAss1.length;k++){
				for(int n = 0; n < carrAss2.length;n++){	
					if(k==n){
						if(!carrAss1[k].equals(carrAss2[n])){
							numAssDiversi = numAssDiversi + 1;
							break;
						}	
					}
				}	
			}	
							
			int numTrasp = numAssDiversi / 2;
			
			//Calcolo somiglianza Stringhe		
			float op1 = (float)carAbb / (float)s1.length();
			float op2 = (float)carAbb / (float)s2.length();
			float op3 = (float)(carAbb-numTrasp) / (float)carAbb;
			
			float somiglianzaJako = (op1 + op2 + op3)/3;
			
			if(somiglianzaJako>limitePercJako){
				jako = true;
				listPerc.add(somiglianzaJako);
			}	
			else{
				jako = false;
			}	
			
			
			
			
			//**
			//* Algoritmo dei trigrammi
			//*
			
			//Riempo le 2 stringhe a sx e dx con uno spazio		
			String newStr1 = " " + s1 + " ";
			String newStr2 = " " + s2 + " ";
			
			
			//Divido le stringhe in triplette di caratteri
			int numTrip1 = 0;
			ArrayList<String> strTrip1 = new ArrayList<String>();		
			for(int i=0; (i<newStr1.length() && (i+3)<=newStr1.length()); i++){
				
				strTrip1.add(newStr1.substring(i,i+3));
				numTrip1++;			
			}
			
			
			int numTrip2 = 0;
			ArrayList<String> strTrip2 = new ArrayList<String>();		
			for(int j=0; (j<newStr2.length() && (j+3)<=newStr2.length()); j++){
				
				strTrip2.add(newStr2.substring(j,j+3));
				numTrip2++;			
			}
			
			int trigComuni = 0;
			for(int k = 0; k < strTrip1.size();k++){
				for(int n = 0; n < strTrip2.size();n++){					
					if(strTrip1.get(k).equals(strTrip2.get(n))){
						trigComuni++;
						break;
					}				
				}	
			}	
			
			//Calcolo Similarità per errori ortografici, testo modificato	s = 2*m/(a + b).		
			float similaritaTrig = (2*(float)trigComuni)/((float)numTrip1+(float)numTrip2);
			
			if(similaritaTrig>limitePercTrig){			
				tri = true;
				listPerc.add(similaritaTrig);
			}	
			else{
				tri = false;
			}	
					
			
			if(listPerc.size() == 2){
				
				//Effettua controllo con algoritmo parole presenti
				boolean res = controllaParolePresenti(s1, s2);
				if(res){
	
					//Trovo la percentuale di associazione			
					for(int i=0; i<listPerc.size();i++){
						percTot = percTot + listPerc.get(i);			
					}
					
					percTot = percTot/listPerc.size();
				}
				
			}else if(listPerc.size() >= 3){	
				
				//Trovo la percentuale di associazione			
				for(int i=0; i<listPerc.size();i++){
					percTot = percTot + listPerc.get(i);			
				}
				
				percTot = percTot/listPerc.size();
			}
						
		}catch (Exception e) {
			throw new Exception("Errore nel calocolo similarità stringhe "+e.getMessage());
		}		
		
		//Inizializzo l'oggetto restituito dal confronto
		GenericTuples.T2<Boolean,Float>  oggettoDaAssociare;		
		oggettoDaAssociare = new GenericTuples.T2<Boolean, Float>(sedimeUguale, percTot);
	
		return oggettoDaAssociare;		
		
	}
	
	
	
/*	public static GenericTuples.T2<Boolean,Float> getSimilarity(String sedime1, String sedime2, String s1, String s2) throws Exception{
		
		//Variabili
		boolean sedimeUguale = false;		
		float percTot = 0;
		
		float limitePercSmith = 0;
		float limitePercTag = 0;
		float limitePercJako = 0;
		float limitePercTrig =  0;
						
		boolean smith = false;
		boolean tagLink = false;
		boolean jako = false;		
		boolean tri = false;
						
		List<Float> listPerc = new ArrayList<Float>();
			
		
		try{	
						
			//Imposto i limiti per i confronti delle percentuali valide
			limitePercSmith = Float.parseFloat(getProperty("smithwaterman.perc.limite.fix"));
			limitePercTag = Float.parseFloat(getProperty("taglink.perc.limite.fix"));
			limitePercJako = Float.parseFloat(getProperty("jako.perc.limite.fix"));
			limitePercTrig =  Float.parseFloat(getProperty("trig.perc.limite.fix"));
			
			
			//**
			//* Effettuo il controllo su ugualianza Sedime
			//**
			if(sedime1.equals(sedime2)){
				sedimeUguale = true;
			}else{
				sedimeUguale = false;			
			}
			
			
			//**
			//* Algoritmo di Smith
			//*

			float resSmith = new SmithWaterman().getSimilarity(s1,s2);						
			System.out.println("Smith: "+resSmith);
		
			
			//**
			//* Algoritmo di TagLink
			//*			
			
			float resTag = new TagLink().getSimilarity(s1,s2);
			System.out.println("TagLink: "+resTag);
						
			
			//**
			//* Algoritmo di Jaro-Winkler
			//*

			float resJaro = new JaroWinkler().getSimilarity(s1,s2);
			System.out.println("TagLink: "+resTag);			
			
			//**
			//* 
			//*

			float resBlock = new BlockDistance().getSimilarity(s1,s2);
			System.out.println("BlockDistance: "+resBlock);			

			float resChap = new ChapmanLengthDeviation().getSimilarity(s1,s2);
			System.out.println("ChapmanLengthDeviation: "+resBlock);			

			float ChapmanMatchingSoundex = new ChapmanMatchingSoundex().getSimilarity(s1,s2);
			System.out.println("ChapmanMatchingSoundex: "+ChapmanMatchingSoundex);			

			float ChapmanMeanLength = new ChapmanMeanLength().getSimilarity(s1,s2);
			System.out.println("ChapmanMeanLength: "+ChapmanMeanLength);			

			float ChapmanOrderedNameCompoundSimilarity = new ChapmanOrderedNameCompoundSimilarity().getSimilarity(s1,s2);
			System.out.println("ChapmanOrderedNameCompoundSimilarity: "+ChapmanOrderedNameCompoundSimilarity);	
			
			float CosineSimilarity = new CosineSimilarity().getSimilarity(s1,s2);
			System.out.println("CosineSimilarity: "+CosineSimilarity);	

			float DiceSimilarity = new DiceSimilarity().getSimilarity(s1,s2);
			System.out.println("DiceSimilarity: "+DiceSimilarity);	

			float JaccardSimilarity = new JaccardSimilarity().getSimilarity(s1,s2);
			System.out.println("JaccardSimilarity: "+JaccardSimilarity);	

			float Jaro = new Jaro().getSimilarity(s1,s2);
			System.out.println("Jaro: "+Jaro);	
			
			float Levenshtein = new Levenshtein().getSimilarity(s1,s2);
			System.out.println("Levenshtein: "+Levenshtein);	
			
			float MatchingCoefficient = new MatchingCoefficient().getSimilarity(s1,s2);
			System.out.println("MatchingCoefficient: "+MatchingCoefficient);
			
			float MongeElkan = new MongeElkan().getSimilarity(s1,s2);
			System.out.println("MongeElkan: "+MongeElkan);

			float NeedlemanWunch = new NeedlemanWunch().getSimilarity(s1,s2);
			System.out.println("NeedlemanWunch: "+NeedlemanWunch);
			
			float OverlapCoefficient = new OverlapCoefficient().getSimilarity(s1,s2);
			System.out.println("OverlapCoefficient: "+OverlapCoefficient);
			
			float QGramsDistance = new QGramsDistance().getSimilarity(s1,s2);
			System.out.println("QGramsDistance: "+QGramsDistance);

			float SmithWaterman = new SmithWaterman().getSimilarity(s1,s2);
			System.out.println("SmithWaterman: "+SmithWaterman);

			float SmithWatermanGotoh = new SmithWatermanGotoh().getSimilarity(s1,s2);
			System.out.println("SmithWatermanGotoh: "+SmithWatermanGotoh);

			float SmithWatermanGotohWindowedAffine = new SmithWatermanGotohWindowedAffine().getSimilarity(s1,s2);
			System.out.println("SmithWatermanGotohWindowedAffine: "+SmithWatermanGotohWindowedAffine);

			float Soundex = new Soundex().getSimilarity(s1,s2);
			System.out.println("Soundex: "+Soundex);

			
			TagLinkToken t = new TagLinkToken();
			//t.setTreshold((float)0.8);
			
			float TagLinkToken = t.getSimilarity(s1,s2);
			System.out.println("TagLinkToken: "+TagLinkToken);
			
			System.out.println("gap :"+ t.getTr());

			
			float p = (TagLinkToken+Soundex+SmithWatermanGotohWindowedAffine+SmithWatermanGotoh+SmithWaterman+QGramsDistance+OverlapCoefficient+
			NeedlemanWunch+MongeElkan+MatchingCoefficient+Levenshtein+Jaro+JaccardSimilarity+DiceSimilarity+CosineSimilarity+ChapmanOrderedNameCompoundSimilarity+
			ChapmanMeanLength+ChapmanMatchingSoundex+resChap+resBlock+resJaro+resTag+resSmith) / 23;
			
			System.out.println("PERCENTUALE: "+p);
			
		}catch (Exception e) {
			throw new Exception("Errore nel calocolo similarità stringhe "+e.getMessage());
		}		
		
		//Inizializzo l'oggetto restituito dal confronto
		GenericTuples.T2<Boolean,Float>  oggettoDaAssociare;		
		oggettoDaAssociare = new GenericTuples.T2<Boolean, Float>(sedimeUguale, percTot);
	
		return oggettoDaAssociare;		
		
	}
	*/
	
	
	
	private static String[] getArrayStringa(String s){
			
		
		String[] arr = new String[s.length()];
		
		StringBuilder sb = new StringBuilder(s);
		
		for(int i=0; i<s.length(); i++){
						
			CharSequence cs = sb.subSequence(i,i+1);
						
			arr[i] = cs.toString(); 
			
		}
		
		return arr;
		
	}
	
	
	private static boolean controllaParolePresenti(String s1, String s2) throws Exception{
		
		boolean confronto = false;
		boolean parolaContenuta = false;
		boolean parolaSingola = false;
		String[] split1;		
		
		//Effettuo il cambio di stringa per prendere come riferimento quella + corta
		String appoggio = "";
		if(s2.length() < s1.length()){
			appoggio = s1;
			s1 = s2;
			s2 = appoggio;
		}
		
		
		try{
			
			split1 = s1.split(" ");						
			s2 = " " + s2 + " ";
			
			for(int i=0; i<split1.length; i++){
								
				CharSequence search = split1[i];
				if (s2.contains(search)){
					parolaContenuta = true;
				}
				
				search = " " + split1[i] + " ";
				if (s2.contains(search)){
					parolaSingola = true;
				}								
			}
			
			if(!parolaSingola && parolaContenuta ){
				confronto = false;
			}else{
				confronto = true;
			}
			/*if(parolaSingola && parolaContenuta ){
				confronto = true;
			}else{
				confronto = false;
			}*/
			
			
		}catch (Exception e) {
			throw new Exception("Errore in Confronto Parole Presenti "+e.getMessage());
		}
		
		return confronto;
		
	}
	
	
	
	private static String getProperty(String propName) {
			
			if (props==null) {
				
				//Caricamento del file di properties dei caricatori		
		        props = new Properties();
		        try {
		            InputStream is = SmithWatermanGotohNibbioWindowedAffine.class.getResourceAsStream("/it/webred/ct/proc/ario/utils/percentualiAlgoConfrontiStringhe.properties");
		            props.load(is);                     
		        }catch(Exception e) {
		            log.error("Eccezione: "+e.getMessage());
		            return null;
		        }
			}
			
			String p = props.getProperty(propName);
			
			if (p==null)
				p = props.getProperty(propName);
				
			return p;
		}
	
	
}
