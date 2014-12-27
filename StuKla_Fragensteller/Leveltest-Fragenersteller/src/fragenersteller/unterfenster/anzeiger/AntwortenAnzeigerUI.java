package fragenersteller.unterfenster.anzeiger;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import materialien.choice.ChoiceAntwort;

/**
 * Anzeige für alle bisher eingegebenen Antworten des AntwortenWerkzeugs.
 * 
 * @author Christophad
 */
public class AntwortenAnzeigerUI
{
	// Das Anzeigefeld für Antworten
	private JTextArea _antwortenUeberblick;

	private JComboBox<String> _antwortenBox;

	private JButton _neueAntwortButton;
	private JButton _antwortAendernButton;
	private JButton _antwortEntfernenButton;

	private JPanel _antwortenAnzeiger;
	private JPanel _antwortenAenderungPanel;

	/**
	 * Erstellt einen Panel zum Anzeigen von eingegebenen Antworten.
	 */
	public AntwortenAnzeigerUI()
	{
		initGUI();
	}

	/**
	 * Initialisiert die Anzeige
	 */
	private void initGUI()
	{
		_antwortenAnzeiger = new JPanel();
		_antwortenAnzeiger.setLayout(new BoxLayout(_antwortenAnzeiger, BoxLayout.PAGE_AXIS));

		erzeugeAntwortenAnzeige();
		erzeugeButtons();
	}

	/**
	 * Erzeugt den Bereich in dem die Antworten angezeigt werden.
	 */
	private void erzeugeAntwortenAnzeige()
	{
		_antwortenUeberblick = new JTextArea(15, 10);
		_antwortenUeberblick.setText("Keine Antworten");
		_antwortenUeberblick.setEditable(false);
		_antwortenUeberblick.setLineWrap(true);
		_antwortenUeberblick.setWrapStyleWord(true);

		JScrollPane scroll = new JScrollPane(_antwortenUeberblick);

		_antwortenAnzeiger.add(scroll);
	}

	private void erzeugeButtons()
	{
		_neueAntwortButton = new JButton("Neue Antwort");
		_antwortAendernButton = new JButton("Antwort ändern");
		_antwortAendernButton.setEnabled(false);
		_antwortEntfernenButton = new JButton("Antwort löschen");
		_antwortEntfernenButton.setEnabled(false);

		_antwortenBox = new JComboBox<String>();
		_antwortenBox.addItem("Keine Antworten");

		_antwortenAenderungPanel = new JPanel();
		_antwortenAenderungPanel.setLayout(new GridLayout(1, 3, 5, 0));

		_antwortenAenderungPanel.add(_antwortenBox);
		_antwortenAenderungPanel.add(_antwortAendernButton);
		_antwortenAenderungPanel.add(_antwortEntfernenButton);

		_antwortenAnzeiger.add(_neueAntwortButton);
		_antwortenAnzeiger.add(_antwortenAenderungPanel);
	}

	/**
	 * Gibt den Panel mit den Bedienelementen zurück
	 * 
	 * @return Der Panel zur Anzeige der Antworten
	 */
	public JPanel getPanel()
	{
		return _antwortenAnzeiger;
	}

	/**
	 * Gibt den Button für eine neue Antwort zurück.
	 * 
	 * @return Der Button zum Erstellen einer Antwort
	 */
	public JButton getNeueAntwortButton()
	{
		return _neueAntwortButton;
	}
	
	/**
	 * Gibt den Button für die Änderung einer Antwort zurück.
	 * 
	 * @return Der Button zum Ändern einer Antwort
	 */
	public JButton getAntwortAendernButton()
	{
		return _antwortAendernButton;
	}
	
	/**
	 * Gibt den Button zum Entfernen einer Antwort zurück.
	 * 
	 * @return Der Button zum Entfernen einer Antwort
	 */
	public JButton getAntwortEntfernenButton()
	{
		return _antwortEntfernenButton;
	}
	
	/**
	 * Gibt die Box für die Auswahl einer Antwort zurück.
	 * 
	 * @return Die Combobox zur Auswahl einer Antwort
	 */
	public JComboBox<String> getAntwortBox()
	{
		return _antwortenBox;
	}

	/**
	 * Ändert den Text der Antwortenanzeige. Die übergebenen Antworten werden im
	 * Textfeld und in der Box angezeigt.
	 * 
	 * @param antworten
	 *            Die Antworten die anzeigt werden sollen.
	 */
	public void setAntwortenAnzeige(List<Object> antworten)
	{
		String anzeige = "";

		if(antworten.size() < 1)
		{
			anzeige = "Keine Antworten";
		}
		
		for(int i = 0; i < antworten.size(); ++i)
		{
			if(antworten.get(i) instanceof ChoiceAntwort)
			{
				ChoiceAntwort antw = (ChoiceAntwort) antworten.get(i);

				anzeige += (i + 1) + ":";
				anzeige += antw.getAntwortText() + ":";
				anzeige += antw.istRichtig() + "\n";
			}
			else if(antworten.get(i) instanceof String)
			{
				anzeige += (i + 1) + ":";
				anzeige += antworten.get(i) + "\n";
			}
		}

		_antwortenBox.removeAllItems();
		
		for(int i = 1; i <= antworten.size(); ++i)
		{
			_antwortenBox.addItem("Antwort " + i);
		}

		_antwortenUeberblick.setText(anzeige);
	}

	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();

		comp.add(_antwortenUeberblick);

		return comp;
	}
}
