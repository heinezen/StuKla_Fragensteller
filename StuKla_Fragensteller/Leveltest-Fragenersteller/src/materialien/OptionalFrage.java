package materialien;

import java.io.File;

public interface OptionalFrage extends Frage
{
	/**
	 * Gibt den Quelltext der Frage aus.
	 * 
	 * @return Der Quelltext.
	 */
	public String getQuelltext();
	
	/**
	 * Gibt das Bild der Frage aus.
	 * 
	 * @require hatBild()
	 * 
	 * @return Das Bild.
	 */
	public File getBild();

	/**
	 * Gibt zurück, ob die Frage Quelltext enthält.
	 * 
	 * @return true, falls Quelltext vorhanden ist.
	 */
	public boolean hatQuelltext();
	
	/**
	 * Gibt zurück, ob die Frage ein Bild enthält.
	 * 
	 * @return true, falls ein Bild vorhanden ist.
	 */
	public boolean hatBild();
}
