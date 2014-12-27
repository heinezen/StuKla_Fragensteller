package materialien.choice.singleChoice;

import materialien.Frage;
import materialien.choice.AbstractChoiceFrage;
import materialien.choice.ChoiceAntwort;

/**
 * Eine Single-Choice-Frage, die mehrere Single-Choice-Antworten in
 * einem Array hält. AuswahlFragen werden verglichen, indem die Antworten
 * des Klienten mit den tatsächlichen über Wahrheitswerte verglichen 
 * werden.
 * 
 * @author Christophad
 *
 */
public class AuswahlFrage extends AbstractChoiceFrage implements Frage
{
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Single-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte für eine richtige Antwort.
	 * @param antworten Die möglichen Antworten der Frage.
	 */
	public AuswahlFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
    }

	@Override
    public int vergleicheAntworten()
    {
		int wert = 0;
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
			if(antwort.istRichtig() && _spielerAntworten[i])
			{
				wert += getPunkteFuerAntwort();
			}
		}
		
		return wert;
    }
	
	public int getMaxPunktzahl()
	{
		return _antwortPunkte;
	}

	@Override
    public void aktualisiereSpielerAntworten(Object neueAntworten)
    {
	    _spielerAntworten = (boolean[]) neueAntworten;
    }

	@Override
    public String getFragetyp()
    {
	    return "Auswahl";
    }
}
