package fragenersteller.unterfenster.anzeiger;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Eine UI zur Eingabe des Textes einer Frage.
 * 
 * @author Christophad
 */
public class FragetextUI
{
	private JTextField _fragetextFeld;
	
	private JPanel _fragetextBereich;
	
	/**
	 * Erstellt einen Panel zum Eingeben des Fragetextes.
	 */
	public FragetextUI()
	{
		erzeugeFragetextBereich("");
	}
	
	/**
	 * Erstellt einen Panel zum Eingeben des Fragetextes mit vorbestimmtem Text.
	 * 
	 * @param fragetext Der Fragetext.
	 */
	public FragetextUI(String fragetext)
    {
	    erzeugeFragetextBereich(fragetext);
    }

	/**
	 * Erzeugt den Bereich in dem der Fragetext eingegeben wird.
	 * 
	 * @param fragetext Der Fragetext.
	 */
	private void erzeugeFragetextBereich(String fragetext)
    {
		_fragetextBereich = new JPanel();
		_fragetextBereich.setLayout(new BoxLayout(_fragetextBereich, BoxLayout.PAGE_AXIS));
		
		_fragetextFeld = new JTextField(30);
		_fragetextFeld.setText(fragetext);
		
		_fragetextBereich.add(_fragetextFeld);
    }
	
	/**
	 * Gibt den Panel mit den Bedienelementen zurück
	 * 
	 * @return Der Panel zur Eingabe des Fragetexts
	 */
	public JPanel getPanel()
	{
		return _fragetextBereich;
	}
	
	/**
	 * Gibt den Fragetext aus dem Eingabefeld zurück.
	 * @return Der Fragetext.
	 */
	public String getFragetext()
	{
		return _fragetextFeld.getText();
	}
	
	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();
		
		comp.add(_fragetextFeld);
		
		return comp;
	}
}
