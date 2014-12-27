package materialien.lueckentext;

import materialien.Frage;

/**
 * Eine Textfrage, die mehrere Antwortm�glichkeiten in
 * einem Array h�lt. TextFragen werden verglichen, indem die Antworten
 * des Klienten mit den M�glichkeiten verglichen werden.
 * 
 * @author Christophad
 *
 */
public class TextFrage extends AbstractTextFrage implements Frage
{

	public TextFrage(String fragetext, int fragepunkte, int abzugpunkte, String[] antwortMoeglichkeiten)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antwortMoeglichkeiten);
    }

	@Override
    public int vergleicheAntworten()
    {
		int wert = 0;
		
	    for(String moeglichkeit : _antwortMoeglichkeiten)
	    {
	    	if(moeglichkeit.equals(_spielerAntwort))
	    	{
	    		wert += getPunkteFuerAntwort();
	    	}
	    }
	    
	    return wert;
    }

	@Override
    public void aktualisiereSpielerAntworten(Object neueAntwort)
    {
	    _spielerAntwort = (String) neueAntwort;
    }

	@Override
    public String getFragetyp()
    {
	    return "Text";
    }
}
