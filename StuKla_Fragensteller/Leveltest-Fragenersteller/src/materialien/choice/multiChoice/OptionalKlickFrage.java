package materialien.choice.multiChoice;

import java.io.File;

import materialien.OptionalFrage;
import materialien.choice.ChoiceAntwort;

/**
 * Eine Multiple-Choice Frage die zus�tzlich noch Quelltext und/oder
 * ein Bild enth�lt.
 * Diese Klasse erweitert die Multiple-Choice Fragen.
 * 
 * @author Christophad
 */
public class OptionalKlickFrage extends KlickFrage implements OptionalFrage
{	
	/**
	 * Der Quelltext der Frage.
	 */
	private final String _quelltext;
	
	/**
	 * Das Bild der Frage.
	 */
	private final File _bild;
	
	/**
	 * Erstellt eine Multiple-Choice Frage, die mehrere Multiple-Choice Antworten
	 * und au�erdem Quelltext und ein Bild enth�lt.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die Antworten der Frage.
	 * @param quelltext Der Quelltext der Frage.
	 * @param bild Das Bild der Frage.
	 * 
	 * @require !quelltext.isEmpty()
	 * @require bild != null
	 * @require bild.exists()
	 * @require bild.canRead()
	 */
	public OptionalKlickFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten, String quelltext, File bild)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
	    
	    assert !quelltext.isEmpty() : "Vorbedingung verletzt : !quelltext.isEmpty()";
	    assert bild != null : "Vorbedingung verletzt : bild != null";
	    assert bild.exists() : "Vorbedingung verletzt : bild.exists()";
	    assert bild.canRead() : "Vorbedingung verletzt : bild.canRead()";
	    
	    _quelltext = quelltext;
	    _bild = bild;
    }
	
	/**
	 * Erstellt eine Multiple-Choice Frage, die mehrere Multiple-Choice Antworten
	 * und au�erdem Quelltext enth�lt.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die Antworten der Frage.
	 * @param quelltext Der Quelltext der Frage.
	 * 
	 * @require !quelltext.isEmpty()
	 */
	public OptionalKlickFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten, String quelltext)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
	    
	    assert !quelltext.isEmpty() : "Vorbedingung verletzt : !quelltext.isEmpty()";
	    
	    _quelltext = quelltext;
	    _bild = null;
    }
	
	/**
	 * Erstellt eine Multiple-Choice Frage, die mehrere Multiple-Choice Antworten
	 * und au�erdem Quelltext und ein Bild enth�lt.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die Antworten der Frage.
	 * @param bild Das Bild der Frage.
	 * 
	 * @require bild != null
	 * @require bild.exists()
	 * @require bild.canRead()
	 */
	public OptionalKlickFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten, File bild)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
	    
	    assert bild != null : "Vorbedingung verletzt : bild != null";
	    assert bild.exists() : "Vorbedingung verletzt : bild.exists()";
	    assert bild.canRead() : "Vorbedingung verletzt : bild.canRead()";
	    
	    _quelltext = "";
	    _bild = bild;
    }

	/**
	 * Gibt den Quelltext der Frage aus.
	 * 
	 * @return Der Quelltext.
	 */
	public String getQuelltext()
	{
		return _quelltext;
	}
	
	/**
	 * Gibt das Bild der Frage aus.
	 * 
	 * @require hatBild()
	 * 
	 * @return Das Bild.
	 */
	public File getBild()
	{
		assert hatBild() : "Vorbedingung verletzt : hatBild()";
		
		return _bild;
	}

	/**
	 * Gibt zur�ck, ob die Frage Quelltext enth�lt.
	 * 
	 * @return true, falls Quelltext vorhanden ist.
	 */
	public boolean hatQuelltext()
    {
	    return !_quelltext.isEmpty();
    }
	
	/**
	 * Gibt zur�ck, ob die Frage ein Bild enth�lt.
	 * 
	 * @return true, falls ein Bild vorhanden ist.
	 */
	public boolean hatBild()
	{
		return _bild != null;
	}
}
