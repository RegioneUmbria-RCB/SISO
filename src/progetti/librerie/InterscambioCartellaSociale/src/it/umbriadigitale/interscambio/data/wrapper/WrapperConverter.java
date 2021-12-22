package it.umbriadigitale.interscambio.data.wrapper;

/**
 * Classe di utilit� per la conversione di elementi wrapper da un tipo a un altro
 */
public class WrapperConverter {
	/**
	 * Restituisce un {@link it.umbriadigitale.interscambio.data.wrapper.PerformerWrapper PerformerWrapper} a partire da un
	 * {@link it.umbriadigitale.interscambio.data.wrapper.PrestazioneWrapper PrestazioneWrapper}. La mappatura delle propriet� segue i nomi delle stesse.
	 */
	public static PerformerWrapper generatePerformerFrom(PrestazioneWrapper prestazione) {
		return new PerformerWrapper(
			prestazione.getCodiceFiscaleOperatoreErogante(),
			prestazione.getProfiloProfessionaleOperatoreEroganteCode(),
			prestazione.getNomeOperatoreErogante(),
			prestazione.getCognomeOperatoreErogante());
	}
}
