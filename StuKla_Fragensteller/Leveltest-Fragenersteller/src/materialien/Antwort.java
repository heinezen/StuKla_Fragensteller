package materialien;

/**
 * Eine Antwort hält ihren Antworttext und ihre Punktzahl zurückgeben.
 * Antworten werden zu ihren Fragen gespeichert, d.h. jede Frage greift auf
 * mehrere Antworten zu.
 * 
 * @author Christophad
 *
 */
public interface Antwort 
{
	/**
	 * Gibt den Text der Antwort als String zurück.
	 * 
	 * @return String mit dem Antworttext.
	 */
	public String getAntwortText();
}
