package it.webred.ct.proc.ario.test;

import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.utils.SmithWatermanGotohNibbioWindowedAffine;
import it.webred.utils.GenericTuples;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWaterman;
import uk.ac.shef.wit.simmetrics.similaritymetrics.TagLink;

public class TestStemming {

	
	public static void main(String[] args) throws Exception{
		
		
		//String s1 = "BERTI";							//no associate	
		//String s2 = "LEON B.ALBERTI";			
			
		//String s1 = "CENTRALE";						//associate		0.8919271						
		//String s2 = "CENTROLE";
		
		//String s1 = "ABBIATE GRASSO";					//no associate 					
		//String s2 = "ABBIATI FILIPPO";			
		
		//String s1 = "G GARIBALDI";					//no associate	  
		//String s2 = "GIUSEPPE BALDI";
		
		//String s1 = "G GARIBALDI";					//associate		0.89548296			
		//String s2 = "GIUSEPPE GARIBALDI";
		
		//String s1 = "GARIBALDI";						//associate		1.0				
		//String s2 = "GARIBALDI";
		
		//String s1 = "GARIBALDI";						//associate		0.8932098			
		//String s2 = "GAEIBALDI";
		
		//String s1 = "GARIBALDI";						//associate		0.9037037				
		//String s2 = "GARIBALDO";
		
		//String s1 = "MANCINI";						//no associate	 						
		//String s2 = "MANCINELLI";
	
		//String s1 = "BRISA";							//no associate							
		//String s2 = "BRIVIO";
		
		//String s1 = "ALBERTO D' ALESSANDRIA";			//associate	 0.9772727
		//String s2 = "D' ALESSANDRIA ALBERTO";
		
		
		//String s1 = "PIO PADRE PIONE";			// no associate		
		//String s2 = "SAN PIO";		
		
		//String s1 = "PIO PADRE PIONE";			// no associate		
		//String s2 = "SAN PREPIO";
		
		//String s1 = "PIO PADRE PIONE";			// no associate	
		//String s2 = "SAN PIONE";

		
		//String s1 = "P GARIBALDI";					//NO associate  				
		//String s2 = "GIUSEPPE GARIBALDI";
			
		
		//String s1 = "MARTIRI LIBERTA";			//ASSOCIATE 	0.9659107
		//String s2 = "MARTIRI LIBERTA'";


		//String s1 = "MASACCIO";				//aSSOCIATE		0.9011874
		//String s2 = "MASACCIO SN";	
		
		//String s1 = "MATTEO DA CAMP";			//aSSOCIATE 0.9342572
		//String s2 = "MATTEO DA CAMP.SN";
		
		
		//String s1 = "ROMA";			//No ass
		//String s2 = "ROMAGNA";
		
		
		//String s1 = "ROMA";			//No ass
		//String s2 = "ROMA";
		
		
		String s1 = "LIBERT";			//No ass
		String s2 = "LIBERTA'";
	
		/*float limitePercSmith =  GestioneStringheVie.SMITHWATERMAN_PERC_LIMITE_FIX;
		float limitePercTag = GestioneStringheVie.TAGLINK_PERC_LIMITE_FIX;
		float limitePercJako = (float)0.80;
		float limitePercTrig = (float)0.80;*/
		
		
		/*//Oggetto precedente 
		//Inizializzo l'oggetto restituito dal confronto
		GenericTuples.T2<Boolean,Float>  oggettoVecchio;		
		oggettoVecchio = new GenericTuples.T2<Boolean, Float>(false, (float)0.99999);
		*/
		
		String sedimeVecchio = "PIAZZA";
		String sedimeNuovo = "VIALE";
		
		
		
		//Inizializzo l'oggetto restituito dal confronto		
		GenericTuples.T2<Boolean,Float>  oggettoDaAssociare = SmithWatermanGotohNibbioWindowedAffine.getSimilarity(sedimeVecchio, sedimeNuovo, s1, s2);
		
		if(oggettoDaAssociare.secondObj > 0){
			System.out.println("###### RISULTATO FINALE : SEDIME UGUALE = "+ oggettoDaAssociare.firstObj);
			System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE VENGONO ASSOCIATE con percentuale = "+oggettoDaAssociare.secondObj);
		}else{
			System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE NON VENGONO ASSOCIATE ###########");
		}
		
		
	/*	if(oggettoDaAssociare.firstObj.equals(oggettoVecchio.firstObj)){
			if(oggettoDaAssociare.secondObj>oggettoVecchio.secondObj){
				System.out.println("###### RISULTATO FINALE : SEDIME UGUALE = "+ oggettoDaAssociare.firstObj);
				System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE VENGONO ASSOCIATE con percentuale = "+oggettoDaAssociare.secondObj);		
			}else{
				System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE CON SEDIME UGUALE NON VENGONO ASSOCIATE ###########");	
			}
		}else{
			float diffPerc = oggettoDaAssociare.secondObj-oggettoVecchio.secondObj;
			
			if(oggettoDaAssociare.firstObj.equals(true)){			
				System.out.println("###### RISULTATO FINALE CASISTICA SEDIME UGUALE MIGLIORE ");
				if(diffPerc>-0.10){
					System.out.println("###### RISULTATO FINALE : SEDIME UGUALE = "+ oggettoDaAssociare.firstObj);
					System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE VENGONO ASSOCIATE con percentuale = "+oggettoDaAssociare.secondObj);		
				}else{
					System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE CON SEDIME UGUALE NON VENGONO ASSOCIATE ###########");
				}	
			}else{				
				System.out.println("###### RISULTATO FINALE CASISTICA SEDIME DIVERSO MIGLIORE ");
				if(diffPerc>0.10){
					System.out.println("###### RISULTATO FINALE : SEDIME UGUALE = "+ oggettoDaAssociare.firstObj);
					System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE VENGONO ASSOCIATE con percentuale = "+oggettoDaAssociare.secondObj);		
				}else{
					System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE CON SEDIME UGUALE NON VENGONO ASSOCIATE ###########");
				}
			}
			
		}*/
		
		
	}
		
		
		//String s1 = "BERTI";							//no associate
		//String s2 = "LEON B.ALBERTI";			
			
		//String s1 = "CENTRALE";						//associate								
		//String s2 = "CENTROLE";
		
		//String s1 = "ABBIATE GRASSO";					//no associate					
		//String s2 = "ABBIATI FILIPPO";			
		
		//String s1 = "G GARIBALDI";					//no associate
		//String s2 = "GIUSEPPE BALDI";
		
		//String s1 = "G GARIBALDI";					//associate					
		//String s2 = "GIUSEPPE GARIBALDI";
		
		//String s1 = "GARIBALDI";						//associate						
		//String s2 = "GARIBALDI";
		
		//String s1 = "GARIBALDI";						//associate						
		//String s2 = "GAEIBALDI";
		
		//String s1 = "GARIBALDI";						//associate						
		//String s2 = "GARIBOLDI";
		
		//String s1 = "MANCINI";						//no associate							
		//String s2 = "MANCINELLI";
		
		//String s1 = "ALBERTO D' ALESSANDRIA";			//associate
		//String s2 = "D' ALESSANDRIA ALBERTO";
		
		
		
		//String s1 = "PIO";			//associate
		//String s2 = "PIA";		
		
		//String s1 = "PIO";			//associate
		//String s2 = "PIOVANI";
			
		//String s1 = "P GARIBALDI";					//associate					
		//String s2 = "GIUSEPPE GARIBALDI";
		
		
		//
		 // Confronti per Sedime diverso
		 //
		
		//String s1 = "CORSO BERTI";							//no associate
		//String s2 = "VIA LEON B.ALBERTI";			

		//String s1 = "PIAZZA BRAILLE";						//associate								
		//String s2 = "VIA BRAILLE";

		
		
		
/*		System.out.println("Similarità tra le stringhe: ");
		System.out.println("Stringa1  : " + s1 );
		System.out.println("Stringa2  : " + s2 );
		
		
		boolean smith_tag = false;		
		boolean jako = false;		
		boolean tri = false;
		
		//Caso Sedime Uguale
		float LimitePercSmith =  GestioneStringheVie.SMITHWATERMAN_PERC_LIMITE_FIX;
		float LimitePercTag = GestioneStringheVie.TAGLINK_PERC_LIMITE_FIX;
		
		//Caso Sedime Diverso
		//float LimitePercSmith =  GestioneStringheVie.SMITHWATERMAN_PERC_LIMITE_INF;
		//float LimitePercTag = GestioneStringheVie.TAGLINK_PERC_LIMITE_INF;

		
		
		float percTot = 0;		
		List<Float> listPerc = new ArrayList<Float>();
	
		
		//
		//  Algoritmo di Smith/TagLink
		// 
		System.out.println("");
		System.out.println("############ ALGORITMO DI SMITH/TAGLINK ######################");							
				
		float resSmith = new SmithWaterman().getSimilarity(s1,s2);		
		System.out.println("SIMILARITA SMITH' : " + resSmith);
		
		float resTag = new TagLink().getSimilarity(s1,s2);
		System.out.println("SIMILARITA TAGLINK' : " + resTag);
		
		if(resSmith>LimitePercSmith || resTag>LimitePercTag){
			System.out.println("Associazione OK");
			smith_tag = true;
			if(resSmith>resTag)
				listPerc.add(resSmith);
				//percSmithTag = resSmith;
			else
				listPerc.add(resTag);
				//percSmithTag = resTag;
		}	
		else{
			System.out.println("NO Associazione");
			smith_tag = false;
		}	
					
	
		//
		//  Algoritmo di Jaro-Winkler
		// 
		System.out.println("");
		System.out.println("############ ALGORITMO DI JARO-WINKLER (somiglianza nel caso di	errori ortografici e dattilografici - associo sopra 80%)");
		
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
		
		//Prova a girare le stringhe in modo inverso partendo con i loop da a e b e non da carrAbbinati
				
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
				
		System.out.println("Somiglianza Stringhe: " + somiglianzaJako);
		if(somiglianzaJako>0.80){
			System.out.println("Associazione OK");
			jako = true;
			//percJako = somiglianzaJako;
			listPerc.add(somiglianzaJako);
		}	
		else{
			System.out.println("NO Associazione");
			jako = false;
		}	
		
		
	
		
		//
		// Algoritmo dei trigrammi
		//
		System.out.println("");
		System.out.println("############ ALGORITMO PER TRIGRAMMI (somiglianza nel caso di errori ortografici, testo modificato)");
		
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
		
		System.out.println("SIMILARITA' PER TRIGRAMMI "+ similaritaTrig);		
		if(similaritaTrig>0.60){
			System.out.println("Associazione OK");
			tri = true;
			//percTrigram = similaritaTrig;
			listPerc.add(similaritaTrig);
		}	
		else{
			System.out.println("NO Associazione");
			tri = false;
		}	
									
		
		System.out.println("");
		System.out.println("");
		System.out.println("###############################################################################");			
		if(listPerc.size()>=2){
			
			//Trovo la percentuale di associazione
			
			for(int i=0; i<listPerc.size();i++){
				percTot = percTot + listPerc.get(i);
			}
			
			percTot = percTot/listPerc.size();
			
			System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE VENGONO ASSOCIATE con percentuale = "+percTot);
			
		}else{
			System.out.println("###### RISULTATO FINALE : LE DUE STRINGHE NON VENGONO ASSOCIATE ###########");
		}
		System.out.println("###############################################################################");
		
	}	   
	
	
	
	private static String[] getArrayStringa(String s){
			
		
		String[] arr = new String[s.length()];
		
		StringBuilder sb = new StringBuilder(s);
		
		for(int i=0; i<s.length(); i++){
						
			CharSequence cs = sb.subSequence(i,i+1);
						
			arr[i] = cs.toString(); 
			
		}
		
		return arr;
		
	}
	
	*/
	
	   
}
