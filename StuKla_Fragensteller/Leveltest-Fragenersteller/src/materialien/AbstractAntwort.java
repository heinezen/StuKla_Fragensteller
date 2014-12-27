package materialien;

/**
 * Definiert die grundlegenden Methoden einer ChoiceAntwort. Antworten müssen
 * sowohl ihren Antworttext als auch ihre Punktzahl zurückgeben können.
 * 
 * @author Christophad
 *
 */
public abstract class AbstractAntwort 
{
	/**
	 * Der Text der Antwort.
	 */
	protected final String _antwortText;
	
	/**
	 * Erzeugt eine Antwort mit gegebenem Text und Wahrheitswert. Außerdem
	 * werden zu der Antwort ihre Punkte gespeichert.
	 * 
	 * @param antwortText Der Text der Antwort.
	 * 
	 * @require !antwortText.isEmpty()
	 */
	public AbstractAntwort(String antwortText) 
	{
		assert !antwortText.isEmpty() : "Vorbedingung verletzt : !antwortText.isEmpty()";
		
		_antwortText = antwortText;
	}
	
	/**
	 * Gibt den Text der Antwort als String zurück.
	 * 
	 * @return String mit dem Antworttext.
	 */
	public final String getAntwortText() 
	{
		return _antwortText;
	}
}
