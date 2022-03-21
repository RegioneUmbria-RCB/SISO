package it.webred.utils;

import java.math.BigInteger;

/**
 * Utitit&agrave; matematiche.
 * @author Giulio Quaresima
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public class MathUtils
{
	/**
	 * Restituisce le combinazioni di n elementi di classe k
	 * @param n
	 * @param k
	 * @return
	 * n! / (k! * (n-k)!)
	 */
	public static int combinations(int n, int k)
	throws IllegalArgumentException
	{
		BigInteger result = veryLargeCombinations(n, k);
		if (result.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1)
			throw new IllegalArgumentException("Il risultato è maggiore del massimo numero intero consentito (java.lang.Integer.MAX_VALUE). Utilizzare veryLargeCombinations(n, k).");
		return result.intValue();
	}
	/**
	 * Restituisce le combinazioni di n elementi di classe k
	 * @param n
	 * @param k
	 * @return
	 * n! / (k! * (n-k)!)
	 */
	public static BigInteger veryLargeCombinations(int n, int k)
	{
		if (k <= 0)
			throw new IllegalArgumentException("La classe deve essere maggiore di 0");
		if (n < k)
			throw new IllegalArgumentException("Il numero di elementi in combinazione non può essere inferiore alla classe");
		
		return (n > 2) ? (bigNotRecursiveFatt(n).divide((bigNotRecursiveFatt(k).multiply(bigNotRecursiveFatt(n-k))))) : BigInteger.ONE;
	}
	
	/** 
	 * Restituisce il fattoriale di n, per 0 <= n < 13
	 * Per valori pi&ugrave; grandi &egrave; necessario
	 * utilizzare bigInt(int n) o bigRecursiveFatt(int n)
	 * @param n
	 * @return
	 */
	public static int fatt(int n)
	{
		if (n < 0)
			throw new IllegalArgumentException("Inserire un valore 0-positivo");
		// PUO' SEMBRARE UNA SOLUZIONE PUERILE, MA, 
		// VISTA LA SCARSA QUANTITA' DI DATI,
		// E' ANCHE SICURAMENTE LA PIU' EFFICIENTE
		switch (n)
		{
		case  0:
		case  1: return         1;
		case  2: return         2;
		case  3: return         6;
		case  4: return        24;
		case  5: return       120;
		case  6: return       720;
		case  7: return      5040;
		case  8: return     40320;
		case  9: return    362880;
		case 10: return   3628800;
		case 11: return  39916800;
		case 12: return 479001600;
		default: throw new IllegalArgumentException("Il numero passato e' troppo grande, il risultato supera il valore Integer.MAX_VALUE. Per valori così grandi, utilizzare bigFatt()");
		}
	}
	/**
	 * Calcola ricorsivamente il fattoriale di n, per n >= 0.
	 * In generale, ma sopratutto per valori grandi, si consiglia
	 * di utilizzare bigNotRecursiveFatt(), in quanto più efficente
	 * e per evitare il rischio di una StackOverflowException
	 * @param n
	 * @return
	 */
	public static BigInteger bigRecursiveFatt(int n)
	{
		if (n < 0)
			throw new IllegalArgumentException("Inserire un valore 0-positivo!");
		if (n < 2)
			return BigInteger.ONE;
		else
			return BigInteger.valueOf(n).multiply(bigRecursiveFatt(n - 1));
	}
	/**
	 * Calcola il fattoriale di n, per n >= 0.
	 * Meno elegante ma decisamente piu' efficente di bigRecursiveFatt()
	 * soprattutto per valori grandi, non corre il rischio di incorrere 
	 * in una StackOverflowException, e consuma meno memoria
	 * di usare 
	 * @param n
	 * @return
	 */
	public static BigInteger bigNotRecursiveFatt(int n)
	{
		if (n < 0)
			throw new IllegalArgumentException("Inserire un valore 0-positivo!");
		BigInteger result = BigInteger.ONE;
		for (int i = 2; i <= n; i++)
			result = BigInteger.valueOf(i).multiply(result);
		return result;
	}
	
	public static void main (String args[])
	{
		System.out.println("Massimo valore consentito dagli int di Java " + Integer.MAX_VALUE);
		for (int i = 0; i < 1000; i++)
		{
			System.out.print("Fattoriale di " + i + ": ");
			try	{System.out.print(MathUtils.fatt(i));}
			catch (IllegalArgumentException e)
			{System.out.print("#VALUE!!!");}
			System.out.print(" <-> ");
			System.out.println(MathUtils.bigNotRecursiveFatt(i));			
		}
	}
}
