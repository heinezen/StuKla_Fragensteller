package fragenersteller.unterfenster.anzeiger;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Eine UI zur Eingabe des Textes einer Frage.
 * 
 * @author Christophad
 */
public class QuelltextUI
{
	private JTextArea _quelltextArea;

	private JPanel _quelltextBereich;

	/**
	 * Erstellt einen Panel zur Eingabe von Quelltext.
	 */
	public QuelltextUI()
	{
		erzeugeQuelltextBereich("");
	}

	/**
	 * Erstellt einen Panel zur Eingabe von Quelltext mit vorbestimmtem Text.
	 * 
	 * @param quelltext
	 *            Der Quelltext.
	 */
	public QuelltextUI(String quelltext)
	{
		erzeugeQuelltextBereich(quelltext);
	}

	/**
	 * Erzeugt den Bereich in dem der Quelltext eingegeben wird.
	 * 
	 * @param quelltext
	 *            Der Quelltext.
	 */
	private void erzeugeQuelltextBereich(String quelltext)
	{
		_quelltextBereich = new JPanel();

		_quelltextArea = new JTextArea(10, 40);
		_quelltextArea.setText(quelltext);

		_quelltextBereich.add(_quelltextArea);
	}

	/**
	 * Gibt den Panel mit den Bedienelementen zurück
	 * 
	 * @return Der Panel zur Eingabe des Quelltexts
	 */
	public JPanel getPanel()
	{
		return _quelltextBereich;
	}

	/**
	 * Gibt den Quelltext aus dem Eingabefeld zurück.
	 * 
	 * @return Der Quelltext.
	 */
	public String getQuelltext()
	{
		return _quelltextArea.getText();
	}

	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();

		comp.add(_quelltextArea);

		return comp;
	}
}
