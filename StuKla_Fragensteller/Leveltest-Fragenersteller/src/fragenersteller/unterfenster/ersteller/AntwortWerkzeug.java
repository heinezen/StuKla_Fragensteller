package fragenersteller.unterfenster.ersteller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;

import materialien.choice.ChoiceAntwort;

/**
 * Stellt ein Werkzeug zum Eingeben von Antworten bereit. Erzeugt beim Erstellen
 * ein dazugeh�riges User Interface in einem Fenster. Sobald eine Antwort
 * eingetragen wurde, werden die Observer des Werkzeug benachrichtigt. Falls
 * die eingegebene Antwort zu einer ChoiceFrage geh�rt, wird auch eine
 * ChoiceAntwort zur�ckgegeben. Ansonsten wird ein String �bergeben. 
 * 
 * @author Christophad
 */
public class AntwortWerkzeug extends Observable
{
	// Die UI des Antwortenwerkzeugs
	private AntwortUI _uiAntwort;
	
	// Der Fragetyp der zugeh�rigen Frage
	private String _fragetyp;
	
	/**
	 * Erstellt ein AntwortenWerkzeug mit einem dem Fragetyp angepassten Interface.
	 * 
	 * @param fragetyp Der Fragetyp der zu den Antworten geh�rigen Frage.
	 */
	public AntwortWerkzeug(String fragetyp)
	{
		_fragetyp = fragetyp;
		_uiAntwort = new AntwortUI(_fragetyp);
		
		regristriereListener();
	}
	
	/**
	 * Erstellt ein AntwortenWerkzeug mit einem dem Fragetyp angepassten Interface und
	 * einem Antworttext.
	 * 
	 * @param fragetyp Der Fragetyp der zu den Antworten geh�rigen Frage.
	 * @param antwort Der Antworttext.
	 */
	public AntwortWerkzeug(String fragetyp, String antwort)
	{
		_fragetyp = fragetyp;
		_uiAntwort = new AntwortUI(_fragetyp, antwort);
		
		regristriereListener();
	}
	
	/**
	 * Erstellt ein AntwortenWerkzeug mit einem dem Fragetyp angepassten Interface,
	 * einem Antworttext und einem Wahrheitswert.
	 * 
	 * @param fragetyp Der Fragetyp der zu den Antworten geh�rigen Frage.
	 * @param antwort Der Antworttext.
	 * @param wahr Der Wahrheitswert der Antwort.
	 */
	public AntwortWerkzeug(String fragetyp, String antwort, boolean wahr)
	{
		_fragetyp = fragetyp;
		_uiAntwort = new AntwortUI(_fragetyp, antwort, wahr);
		
		regristriereListener();
	}

	/**
	 * Erstellt die Listener der Eingebeelemente
	 */
	private void regristriereListener()
    {
	    JButton button = _uiAntwort.getAntwortHinzuf�genButton();
	    button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setChanged();
				notifyObservers(erstelleAntwort());
				
				_uiAntwort.schliesseFenster();
			}
		});
    }

	/**
	 * Erstellt eine Antwort aus den Eingaben. Je nach Fragetyp wird entweder
	 * eine ChoiceAntwort oder ein String zur�ckgegeben.
	 * 
	 * @return Eine Antwort auf die Frage.
	 */
	private Object erstelleAntwort()
    {
	    if(_fragetyp != "Text")
	    {
	    	return new ChoiceAntwort(_uiAntwort.getAntwortText(), _uiAntwort.getAntwortWert());
	    }
	    else
	    {
	    	return _uiAntwort.getAntwortText();
	    }
    }
}
