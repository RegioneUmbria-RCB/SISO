package it.umbriadigitale.argo.ejb.client.cs.dto.util.codec;

/**
 * 
 * @author andrea.niccolini
 *
 * @param <S>
 * @param <T>
 */
public interface ICodec<S,T> {
	
	T encode(S source);
	S decode(T source);
	
}
