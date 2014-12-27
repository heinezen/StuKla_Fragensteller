package fragenersteller.unterfenster;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import materialien.Frage;
import materialien.OptionalFrage;
import fragenersteller.unterfenster.anzeiger.AntwortenAnzeigerUI;
import fragenersteller.unterfenster.anzeiger.BildauswahlUI;
import fragenersteller.unterfenster.anzeiger.FragePunkteUI;
import fragenersteller.unterfenster.anzeiger.FragetextUI;
import fragenersteller.unterfenster.anzeiger.FragetypUI;
import fragenersteller.unterfenster.anzeiger.QuelltextUI;

/**
 * Die zum FragenErstellerWerkzeug zugehörige UI.
 * 
 * @author Christophad
 */
public class FragenErstellerWerkzeugUI
{
	private static final String NAME = "Testersteller";

	// Beschreibungstext für jeweilige Eingabe
	private JLabel _beschreibungsLabel;

	// Buttons zur Navigation
	private JButton _zurueckButton;
	private JButton _abschliessenButton;
	private JButton _weiterButton;

	// Beschreibung der jeweiligigen Eingabe
	private JPanel _beschreibungsBereich;

	// Bereiche mit Eingabefeldern
	private FragetypUI _fragetypBereich;
	private FragetextUI _fragetextBereich;
	private FragePunkteUI _fragePunkteBereich;
	private BildauswahlUI _bildBereich;
	private QuelltextUI _quelltextBereich;
	private AntwortenAnzeigerUI _antwortenBereich;

	// Bereiche mit Anzeige und Buttons
	private JPanel _buttonBereich;
	private JPanel _anzeige;

	private JFrame _frame;

	/**
	 * Erstellt ein UI des Fragenerstellers und initialisiert dieses.
	 */
	public FragenErstellerWerkzeugUI()
	{
		initGUI();
	}

	public FragenErstellerWerkzeugUI(Frage frage)
    {
	    initGUI(frage);
    }

	/**
	 * Initialisiert die GUI-Elemente des Testerstellers mi vorbestimmter Frage
	 * 
	 * @param frage Die zugehörige Frage.
	 */
	private void initGUI(Frage frage)
    {
		_anzeige = new JPanel();
		_anzeige.setLayout(new BoxLayout(_anzeige, BoxLayout.Y_AXIS));

		erzeugeEingabeBereiche(frage);
		erzeugeBeschreibungsBereich();
		erzeugeButtonBereich();

		erzeugeHauptfenster();

		zumFragetypBereich();
    }

	/**
	 * Initialisiert die GUI-Elemente des Testerstellers.
	 */
	private void initGUI()
	{
		_anzeige = new JPanel();
		_anzeige.setLayout(new BoxLayout(_anzeige, BoxLayout.Y_AXIS));

		erzeugeEingabeBereiche();
		erzeugeBeschreibungsBereich();
		erzeugeButtonBereich();

		erzeugeHauptfenster();

		zumFragetypBereich();
	}

	/**
	 * Erzeugt die Eingabebereiche der GUI.
	 */
	private void erzeugeEingabeBereiche()
	{
		_fragetypBereich = new FragetypUI();
		_fragetextBereich = new FragetextUI();
		_fragePunkteBereich = new FragePunkteUI();
		_bildBereich = new BildauswahlUI();
		_quelltextBereich = new QuelltextUI();
		_antwortenBereich = new AntwortenAnzeigerUI();
	}
	
	/**
	 * Erzeugt die Eingabebereiche der GUI mit vorbestimmter Frage.
	 */
	private void erzeugeEingabeBereiche(Frage frage)
	{	
		boolean bildVorhanden;
		boolean quelltextVorhanden;
		
		if(frage instanceof OptionalFrage)
		{
			bildVorhanden = ((OptionalFrage) frage).hatBild();
			quelltextVorhanden = ((OptionalFrage) frage).hatQuelltext();
		}
		else
		{
			bildVorhanden = false;
			quelltextVorhanden= false;
		}
		
		_fragetypBereich = new FragetypUI(frage.getFragetyp(), bildVorhanden, quelltextVorhanden);
		_fragetextBereich = new FragetextUI(frage.getFragetext());
		_fragePunkteBereich = new FragePunkteUI(frage.getPunkteFuerAntwort(), frage.getAbzugsPunkte());
		if(bildVorhanden)
		{
			_bildBereich = new BildauswahlUI(((OptionalFrage) frage).getBild());
		}
		else
		{
			_bildBereich = new BildauswahlUI();
		}
		if(quelltextVorhanden)
		{
			_quelltextBereich = new QuelltextUI(((OptionalFrage) frage).getQuelltext());
		}
		else
		{
			_quelltextBereich = new QuelltextUI();
		}
		
		_antwortenBereich = new AntwortenAnzeigerUI();
	}

	/**
	 * Erzeugt den Beschreibungsbereich der GUI.
	 */
	private void erzeugeBeschreibungsBereich()
	{
		_beschreibungsBereich = new JPanel();
		_anzeige.add(_beschreibungsBereich);

		_beschreibungsLabel = new JLabel("Fragetyp auswählen");
		_beschreibungsBereich.add(_beschreibungsLabel);
	}

	/**
	 * Erzeugt den buttonbereich der GUI.
	 */
	private void erzeugeButtonBereich()
	{
		_buttonBereich = new JPanel();
		_anzeige.add(_buttonBereich);

		_zurueckButton = new JButton("Zurück");
		_abschliessenButton = new JButton("Frage abschließen");
		_weiterButton = new JButton("Weiter");

		_buttonBereich.add(_zurueckButton);
		_buttonBereich.add(_weiterButton);
	}

	/**
	 * Erzeugt das Hauptfenster.
	 */
	private void erzeugeHauptfenster()
	{
		_frame = new JFrame(NAME);
		_frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		_frame.setTitle(NAME);
		_frame.setVisible(true);
		_frame.add(_anzeige);
		_frame.pack();
		_frame.setLocationRelativeTo(null);
	}

	/**
	 * Lässt die GUI den Fragetypbereich anzeigen.
	 */
	public void zumFragetypBereich()
	{
		_beschreibungsLabel.setText("Fragetyp angeben");

		_anzeige.remove(1);

		_anzeige.add(_fragetypBereich.getPanel(), 1);

		_anzeige.add(_buttonBereich);

		_frame.pack();
	}

	/**
	 * Lässt die GUI den Fragetextbereich anzeigen.
	 */
	public void zumFragetextBereich()
	{
		_beschreibungsLabel.setText("Fragetext eingeben");

		_anzeige.remove(1);

		_anzeige.add(_fragetextBereich.getPanel(), 1);
		_anzeige.add(_buttonBereich);

		_frame.pack();
	}

	/**
	 * Lässt die GUI den Antwortpunktebereich anzeigen.
	 */
	public void zumFragePunkteBereich()
	{
		_beschreibungsLabel.setText("Punkte pro richtiger Antwort angeben");

		_buttonBereich.remove(_abschliessenButton);

		_anzeige.remove(1);

		_anzeige.add(_fragePunkteBereich.getPanel(), 1);
		_anzeige.add(_buttonBereich);

		_frame.pack();
	}

	/**
	 * Lässt die GUI den Bildbereich anzeigen.
	 */
	public void zumBildBereich()
	{
		_beschreibungsLabel.setText("Bildpfad auswählen");

		_buttonBereich.remove(_abschliessenButton);

		_anzeige.remove(1);

		_anzeige.add(_bildBereich.getPanel(), 1);
		_anzeige.add(_buttonBereich);

		_frame.pack();
	}

	/**
	 * Lässt die GUI den Quelltextbereich anzeigen.
	 */
	public void zumQuelltextBereich()
	{
		_beschreibungsLabel.setText("Quelltext eingeben");

		_buttonBereich.remove(_abschliessenButton);

		_anzeige.remove(1);

		_anzeige.add(_quelltextBereich.getPanel(), 1);
		_anzeige.add(_buttonBereich);

		_frame.pack();
	}

	/**
	 * Lässt die GUI den Antwortenbereich anzeigen.
	 */
	public void zumAntwortenBereich()
	{
		_beschreibungsLabel.setText("Antworten eingeben");

		_buttonBereich.add(_abschliessenButton, 1);

		_anzeige.remove(1);

		_anzeige.add(_antwortenBereich.getPanel(), 1);
		_anzeige.add(_buttonBereich);

		_frame.pack();
	}

	public void setAntwortenAnzeige(List<Object> antworten)
	{
		_antwortenBereich.setAntwortenAnzeige(antworten);
	}

	/**
	 * Gibt den Zurück-Button zurück.
	 * 
	 * @return Der Zurück-Button.
	 */
	public JButton getZurueckButton()
	{
		return _zurueckButton;
	}

	/**
	 * Gibt den Abschliessen-Button zurück.
	 * 
	 * @return Der Abschliessen-Button.
	 */
	public JButton getAbschliessenButton()
	{
		return _abschliessenButton;
	}

	/**
	 * Gibt den Weiter-Button zurück.
	 * 
	 * @return Der Weiter-Button.
	 */
	public JButton getWeiterButton()
	{
		return _weiterButton;
	}

	/**
	 * Gibt den Button zurück, mit dem Antworten hinzugefügt werden.
	 * 
	 * @return Der Antwort-Hinzufügen-Button.
	 */
	public JButton getNeueAntwortButton()
	{
		return _antwortenBereich.getNeueAntwortButton();
	}
	
	/**
	 * Gibt den Button für die Änderung einer Antwort zurück.
	 * 
	 * @return Der Button zum Ändern einer Antwort
	 */
	public JButton getAntwortAendernButton()
	{
		return _antwortenBereich.getAntwortAendernButton();
	}
	
	/**
	 * Gibt den Button zum Entfernen einer Antwort zurück.
	 * 
	 * @return Der Button zum Entfernen einer Antwort
	 */
	public JButton getAntwortEntfernenButton()
	{
		return _antwortenBereich.getAntwortEntfernenButton();
	}
	
	/**
	 * Gibt die Box für die Auswahl einer Antwort zurück.
	 * 
	 * @return Die Combobox zur Auswahl einer Antwort
	 */
	public JComboBox<String> getAntwortBox()
	{
		return _antwortenBereich.getAntwortBox();
	}

	/**
	 * Gibt den Fragetyp der aktuell zu erstellenden Frage zurück.
	 * 
	 * @return 0,1,2 für die Fragetypen Klick, Auswahl, Text -1, falls nichts
	 *         angewählt ist
	 */
	public String getFragetyp()
	{
		return _fragetypBereich.getFragetyp();
	}

	/**
	 * Gibt zurück, ob die Frage ein Bild enthält.
	 * 
	 * @return Wahrheitswert, ob Frage Bild enthält.
	 */
	public boolean getBildVorhanden()
	{
		return _fragetypBereich.getBildVorhanden();
	}

	/**
	 * Gibt zurück, ob die Frage Quelltext enthält.
	 * 
	 * @return Wahrheitswert, ob Frage Quelltext enthält.
	 */
	public boolean getQuelltextVorhanden()
	{
		return _fragetypBereich.getQuelltextVorhanden();
	}

	/**
	 * Gibt den Fragetext aus dem Eingabefeld zurück.
	 * 
	 * @return Der Fragetext.
	 */
	public String getFragetext()
	{
		return _fragetextBereich.getFragetext();
	}

	/**
	 * Gibt die Antwortpunkte aus dem Eingabefeld zurück.
	 * 
	 * @return Die Antwortpunkte.
	 */
	public int getFragePunkte()
	{
		return _fragePunkteBereich.getFragePunkte();
	}

	/**
	 * Gibt die Abzugspunkte aus dem Eingabefeld zurück.
	 * 
	 * @return Die Abzugspunkte.
	 */
	public int getAbzugPunkte()
	{
		return _fragePunkteBereich.getAbzugPunkte();
	}

	/**
	 * Gibt den Pfad des Bildes aus dem Eingabefeld zurück.
	 * 
	 * @return Der Pfad als String.
	 */
	public File getBild()
	{
		return _bildBereich.getBild();
	}

	/**
	 * Gibt den Quelltext aus dem Eingabefeld zurück.
	 * 
	 * @return Der Quelltext.
	 */
	public String getQuelltext()
	{
		return _quelltextBereich.getQuelltext();
	}

	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();

		comp.addAll(_fragetypBereich.getListenerComponents());
		comp.addAll(_fragetextBereich.getListenerComponents());
		comp.addAll(_fragePunkteBereich.getListenerComponents());
		comp.addAll(_bildBereich.getListenerComponents());
		comp.addAll(_quelltextBereich.getListenerComponents());
		comp.addAll(_antwortenBereich.getListenerComponents());

		return comp;
	}

	/**
	 * Setzt die Eingabebereiche für Fragetext, Fragepunkte und Antworten
	 * zurück.
	 */
	public void setzeZurueck()
	{
		_fragetextBereich = new FragetextUI();
		_fragePunkteBereich = new FragePunkteUI();
		_antwortenBereich = new AntwortenAnzeigerUI();
	}

	/**
	 * Setzt den Eingabebereich für das Bild zurück.
	 */
	public void setzeBildZurueck()
	{
		_bildBereich = new BildauswahlUI();
	}

	/**
	 * Setzt den Eingabebereich für den Quelltext zurück.
	 */
	public void setzeQuelltextZurueck()
	{
		_quelltextBereich = new QuelltextUI();
	}

	/**
	 * Schließt das Fenster der UI
	 */
	public void schliesseFenster()
	{
		_frame.dispose();
	}
}
