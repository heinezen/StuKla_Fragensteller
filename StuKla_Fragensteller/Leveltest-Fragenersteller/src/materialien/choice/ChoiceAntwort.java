package materialien.choice;

import materialien.AbstractAntwort;
import materialien.Antwort;

/**
 * Eine Antwort, die von Multiple- oder Single-Choice-Fragen
 * benutzt werden kann. ChoiceAntworten haben einen Wahrheitswert,
 * der angibt ob diese wahr oder falsch sind.
 * 
 * @author Christophad
 *
 */
public class ChoiceAntwort extends AbstractAntwort implements Antwort
{
	/**
	 * Wahrheitswert der Antwort.
	 */
	private final boolean _richtig;
	
	/**
	 * Erzeugt eine Antwort mit gegebenem Text und Wahrheitswert. 
	 * Außerdem werden zu der Antwort ihre Punkte gespeichert.
	 * 
	 * @param antwortText Der Text der Antwort.
	 * @param antwortWert Der Wahrheitswert der Antwort.
	 */
	public ChoiceAntwort(String antwortText, boolean antwortWert) 
	{
		super(antwortText);
		
		_richtig = antwortWert;
	}
	
	/**
	 * Gibt zurück ob die Antwort wahr oder falsch ist.
	 * 
	 * @return Der Wahrheitswert der Antwort.
	 */
	public boolean istRichtig()
	{
		return _richtig;
	}
}