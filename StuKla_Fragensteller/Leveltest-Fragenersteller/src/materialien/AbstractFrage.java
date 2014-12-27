package materialien;

public abstract class AbstractFrage
{
	/**
	 * Der Text der Frage.
	 */
	private final String _frageText;
	
	/**
	 * Punkte für richtige Antworten.
	 */
	protected final int _antwortPunkte;
	
	/**
	 * Punktabzug für falsche/nicht angekreuzte Antworten.
	 */
	protected final int _antwortAbzug;
	
	/**
	 * Erzeugt eine Frage mit Fragetext.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte pro richtiger Antwort.
	 * 
	 * @require !fragetext.isEmpty()
	 * @require fragepunkte > 0
	 * @require abzugpunkte >= 0
	 */
	public AbstractFrage(String fragetext, int fragepunkte, int abzugpunkte) 
	{
		assert !fragetext.isEmpty() : "Vorbedingung verletzt : !fragetext.isEmpty()";
		assert fragepunkte > 0 : "Vorbedingung verletzt : fragepunkte > 0";
		assert abzugpunkte >= 0 : "Vorbedingung verletzt : abzugpunkte >= 0";
		
		_frageText = fragetext;
		
		_antwortPunkte = fragepunkte;
		
		_antwortAbzug = abzugpunkte;
	}
	
	/**
	 * Gibt den Text der Frage als String zurück.
	 * 
	 * @return Der Fragetext.
	 */
	public final String getFragetext()
	{
		return _frageText;
	}
	
	/**
	 * Gibt die Punkte für eine richtige Antwort aus.
	 * 
	 * @return Punkte pro richtiger Antwort.
	 */
	public int getPunkteFuerAntwort()
	{
		return _antwortPunkte;
	}
	
	/**
	 * Gibt die Punkte für eine falsche Antwort aus.
	 * 
	 * @return Abzug für falsche Antwort
	 */
	public int getAbzugsPunkte()
	{
		return _antwortAbzug;
	}
	
	/**
	 * Gibt die maximal zu erreichende Punktzahl der Frage zurück.
	 * 
	 * @return Die maximal erreichbare Punktzahl.
	 */
	public abstract int getMaxPunktzahl();
	
	/**
	 * Gibt den Fragetyp der Frage zurück.
	 * 
	 * @return Der Fragetyp
	 */
	public abstract String getFragetyp();
}
