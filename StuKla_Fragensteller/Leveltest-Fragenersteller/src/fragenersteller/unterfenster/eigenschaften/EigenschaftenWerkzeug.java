package fragenersteller.unterfenster.eigenschaften;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;

import fragenersteller.Startup;

/**
 * Stellt ein Werkzeug zum Eingeben der Eigenschaften des Fragensatzes zur Verfügung. 
 * Erzeugt beim Erstellen ein dazugehöriges User Interface in einem Fenster. Sobald die 
 * Eigenschaften eingegeben wurden, werden die Observer des Werkzeug benachrichtigt.
 * 
 * @author Christophad
 */
public class EigenschaftenWerkzeug extends Observable
{
	private EigenschaftenUI _uiEigenschaften;
	
	/**
	 * Erstellt ein EigenschaftenWerkzeug aus einer gegebenen
	 * Übersicht von Eigenschaften.
	 * 
	 * @param uebersicht Die schon eingetragenen Eigenschaften
	 */
	public EigenschaftenWerkzeug(String[] uebersicht)
	{
		_uiEigenschaften = new EigenschaftenUI(uebersicht);
		
		registriereListener();
	}

	/**
	 * Erstellt die Listener des EigenschaftenWerkzeugs.
	 */
	private void registriereListener()
    {
	    JButton button = _uiEigenschaften.getEigenschaftenButton();
	    button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setChanged();
				notifyObservers(liesEigenschaften());
				
				_uiEigenschaften.schliesseFenster();
			}
		});
    }
	
	/**
	 * Liest die Eigenschaften aus den Textfeldern aus.
	 * 
	 * @return Eigenschaften als Stringarray.
	 */
	private String[] liesEigenschaften()
	{
		String[] eigenschaften = new String[4];
		
		eigenschaften[0] = Startup.VERSION;
		eigenschaften[1] = _uiEigenschaften.getModul();
		eigenschaften[2] = _uiEigenschaften.getDatum();
		eigenschaften[3] = _uiEigenschaften.getZeit();
		
		return eigenschaften;
	}
}
