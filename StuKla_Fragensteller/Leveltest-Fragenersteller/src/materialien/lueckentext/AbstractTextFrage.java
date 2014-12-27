package materialien.lueckentext;

import materialien.AbstractFrage;

/**
 * Definiert die grundlegenden Methoden einer Frage. Fragen müssen
 * ihren Fragetext und ihre Antworttexte ausgeben können. Außerdem
 * kann die Punktzahl einer Frage errechnet werden und ihre Antworten
 * können verglichen werden.
 * 
 * @author Christophad
 *
 */
public abstract class AbstractTextFrage extends AbstractFrage
{	
	/**
	 * Das Array mit den Antworten der Frage.
	 */
	protected final String[] _antwortMoeglichkeiten;
	
	/**
	 * Antworten des Spielers.
	 */
	protected String _spielerAntwort;
	
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte 
	 * @param antworten Die möglichen Antworten der Frage.
	 */
	public AbstractTextFrage(String fragetext, int fragepunkte, int abzugpunkte, String[] antwortMoeglichkeiten) 
	{
		super(fragetext, fragepunkte, abzugpunkte);
		
		_antwortMoeglichkeiten = antwortMoeglichkeiten;
		
		_spielerAntwort = "";
	}
	
	/**
	 * Gibt die Antworttexte als Array von Strings aus.
	 * 
	 * @return Die Antworttexte.
	 */
	public String[] getAntworttexte()
	{
		return _antwortMoeglichkeiten;
	}
	
	/**
	 * Gibt die maximal zu erreichende Punktzahl der Frage zurück.
	 * 
	 * @return Die maximal erreichbare Punktzahl.
	 */
	public int getMaxPunktzahl()
	{
		return _antwortPunkte;
	}
	
	/**
	 * Gibt die Antworten des Spielers als Object zurück.
	 * 
	 * @return Die Antworten des Spielers
	 */
	public Object getSpielerAntworten()
	{
		return _spielerAntwort;
	}
	
	/**
	 * Gibt die richtigen Antworten der Frage zurück.
	 * 
	 * @return Die richtigen Antworten.
	 */
	public Object getAntwortenWerte()
	{
		return _antwortMoeglichkeiten;
	}
	
	/**
	 * Vergleicht die Antworten des Spielers mit den tatsächlichen
	 * Antworten. Zurückgegeben wird eine Punktzahl, die die
	 * erreichten Punkte für die Frage darstellt.
	 * 
	 * @return Das Punktzahl für alle Antworten.
	 */
	public abstract int vergleicheAntworten();
	
	/**
	 * Aktualisiert die Antworten des Spielers.
	 * 
	 * @param neueAntworten Die neue(n) Antwort(en) des Spielers.
	 */
	public abstract void aktualisiereSpielerAntworten(Object neueAntwort);
}
