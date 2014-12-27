package fragenersteller.unterfenster;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import fragenersteller.unterfenster.ersteller.AntwortWerkzeug;
import materialien.Antwort;
import materialien.Frage;
import materialien.OptionalFrage;
import materialien.choice.ChoiceAntwort;
import materialien.choice.multiChoice.KlickFrage;
import materialien.choice.multiChoice.OptionalKlickFrage;
import materialien.choice.singleChoice.AuswahlFrage;
import materialien.choice.singleChoice.OptionalAuswahlFrage;
import materialien.lueckentext.OptionalTextFrage;
import materialien.lueckentext.TextFrage;

/**
 * Stellt ein Werkzeug zum Eingeben von Fragen zur Verfügung. Erzeugt beim
 * Erstellen ein dazugehöriges User Interface in einem Fenster. Sobald eine
 * Frage abgeschlossen wurde, werden die Observer des Werkzeug benachrichtigt.
 * 
 * @author Christophad
 */
public class FragenErstellerWerkzeug extends Observable implements Observer
{
	private FragenErstellerWerkzeugUI _uiFragenErstellerWerkzeug;

	private int _aktuellerBereich;
	private int _aktuelleAntwortIndex;
	private boolean _antwortGeaendert;

	private AntwortWerkzeug _antwortWerkzeug;

	private String _fragetyp;
	private boolean _hatBild;
	private boolean _hatQuelltext;
	private String _fragetext;
	private int _fragePunkte;
	private int _abzugPunkte;
	private File _bild;
	private String _quelltext;
	private List<Object> _antworten;
	private Frage _frage;

	/**
	 * Erstellt ein FragenErstellerWerkzeug.
	 */
	public FragenErstellerWerkzeug()
	{
		_uiFragenErstellerWerkzeug = new FragenErstellerWerkzeugUI();
		
		_aktuellerBereich = 0;
		_aktuelleAntwortIndex = -1;
		_antwortGeaendert = false;

		_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(false);
		_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(false);

		_antworten = new ArrayList<Object>();

		registriereListener();
	}

	/**
	 * Erstellt ein FragenErstellerWerkzeug mit vorhandener Frage.
	 * 
	 * @param frage Die zugehörige Frage.
	 */
	public FragenErstellerWerkzeug(Frage frage)
	{
		_uiFragenErstellerWerkzeug = new FragenErstellerWerkzeugUI(frage);
		
		_fragetyp = frage.getFragetyp();
		if(frage instanceof OptionalFrage)
		{
			_hatBild = ((OptionalFrage) frage).hatBild();
			_hatQuelltext = ((OptionalFrage) frage).hatQuelltext();
		}
		else
		{
			_hatBild = false;
			_hatQuelltext= false;
		}
		
		_aktuellerBereich = 0;
		_aktuelleAntwortIndex = -1;
		_antwortGeaendert = false;

		_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(false);
		_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(true);
		
		_antworten = new ArrayList<Object>();
		
		if(!frage.getFragetyp().equals("Text"))
		{
			String[] antworttexte = frage.getAntworttexte();
			boolean[] antwortwerte = (boolean[]) frage.getAntwortenWerte();
			
			for(int i = 0; i < antworttexte.length; ++i)
			{
				_antworten.add(new ChoiceAntwort(antworttexte[i], antwortwerte[i]));
			}
		}
		else
		{
			String[] antworttexte = frage.getAntworttexte();
			
			for(int i = 0; i < antworttexte.length; ++i)
			{
				_antworten.add(antworttexte[i]);
			}
		}
		
		_uiFragenErstellerWerkzeug.setAntwortenAnzeige(_antworten);

		registriereListener();
	}

	/**
	 * Erstellt die Listener der Eingebeelemente
	 */
	private void registriereListener()
	{
		JButton button = _uiFragenErstellerWerkzeug.getZurueckButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				zumVorherigenBereich();
			}
		});

		button = _uiFragenErstellerWerkzeug.getAbschliessenButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				setChanged();
				notifyObservers(erstelleFrage());

				_uiFragenErstellerWerkzeug.schliesseFenster();
			}
		});

		button = _uiFragenErstellerWerkzeug.getWeiterButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				zumNaechstenBereich();
			}
		});

		registriereFeldListener();
		registriereAntwortButtonListener();
	}

	/**
	 * Erstellt die Listener für die Eingabefelder.
	 */
	private void registriereFeldListener()
	{
		for(Component comp : _uiFragenErstellerWerkzeug.getListenerComponents())
		{
			if(comp instanceof JToggleButton)
			{
				((JToggleButton) comp).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(
						        pruefeObWeiterErlaubt());
					}
				});
			}
			else if(comp instanceof JTextComponent)
			{
				((JTextComponent) comp).getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e)
					{
						// _uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(pruefeObWeiterErlaubt());
					}

					@Override
					public void insertUpdate(DocumentEvent e)
					{
						_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(
						        pruefeObWeiterErlaubt());

						if(_aktuellerBereich == 5)
						{
							JButton button = _uiFragenErstellerWerkzeug.getAbschliessenButton();

							button.setEnabled(pruefeObFrageAbgabebereit());
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e)
					{
						_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(
						        pruefeObWeiterErlaubt());
					}
				});
			}
		}
	}

	/**
	 * Erstellt die Listener für den Antwortenbereich.
	 */
	private void registriereAntwortButtonListener()
	{
		JButton button = _uiFragenErstellerWerkzeug.getNeueAntwortButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				erstelleAntwortenWerkzeug();
			}
		});

		button = _uiFragenErstellerWerkzeug.getAntwortAendernButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				erstelleAntwortenWerkzeug(_antworten.get(_aktuelleAntwortIndex));
			}
		});

		button = _uiFragenErstellerWerkzeug.getAntwortEntfernenButton();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				_antworten.remove(_aktuelleAntwortIndex);
				_uiFragenErstellerWerkzeug.setAntwortenAnzeige(_antworten);

				_aktuelleAntwortIndex = _uiFragenErstellerWerkzeug.getAntwortBox().getSelectedIndex();

				boolean aenderungMoeglich = _aktuelleAntwortIndex >= 0;

				_uiFragenErstellerWerkzeug.getAntwortAendernButton().setEnabled(aenderungMoeglich);
				_uiFragenErstellerWerkzeug.getAntwortEntfernenButton()
				        .setEnabled(aenderungMoeglich);
			}
		});

		final JComboBox<String> box = _uiFragenErstellerWerkzeug.getAntwortBox();
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)
			{
				_aktuelleAntwortIndex = box.getSelectedIndex();

				boolean aenderungMoeglich = _aktuelleAntwortIndex >= 0;

				_uiFragenErstellerWerkzeug.getAntwortAendernButton().setEnabled(aenderungMoeglich);
				_uiFragenErstellerWerkzeug.getAntwortEntfernenButton()
				        .setEnabled(aenderungMoeglich);
			}
		});
	}

	/**
	 * Erstellt ein AntwortenWerkzeug.
	 */
	private void erstelleAntwortenWerkzeug()
	{
		_antwortGeaendert = false;

		_antwortWerkzeug = new AntwortWerkzeug(_fragetyp);
		_antwortWerkzeug.addObserver(this);
	}

	/**
	 * Erstellt ein AntwortenWerkzeug mit einer vorhandenen Antwort.
	 * 
	 * @param antwort
	 *            Die anzuzeigende Antwort.
	 */
	private void erstelleAntwortenWerkzeug(Object antwort)
	{
		_antwortGeaendert = true;

		if(!_fragetyp.equals("Text"))
		{
			String antworttext = ((ChoiceAntwort) antwort).getAntwortText();
			boolean antwortwert = ((ChoiceAntwort) antwort).istRichtig();

			_antwortWerkzeug = new AntwortWerkzeug(_fragetyp, antworttext, antwortwert);
			_antwortWerkzeug.addObserver(this);
		}
		else
		{
			String antworttext = (String) antwort;

			_antwortWerkzeug = new AntwortWerkzeug(_fragetyp, antworttext);
			_antwortWerkzeug.addObserver(this);
		}
	}

	/**
	 * Lässt das Werkzeug zum vorherigen Bereich wechseln.
	 */
	protected void zumVorherigenBereich()
	{
		--_aktuellerBereich;
		if(_aktuellerBereich <= 0)
		{
			_aktuellerBereich = 0;
			_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(false);
		}

		switch(_aktuellerBereich)
		{
			case 4:
				if(_hatQuelltext)
				{
					_uiFragenErstellerWerkzeug.zumQuelltextBereich();
					break;
				}
				else
				{
					--_aktuellerBereich;
				}
			case 3:
				if(_hatBild)
				{
					_uiFragenErstellerWerkzeug.zumBildBereich();
					break;
				}
				else
				{
					--_aktuellerBereich;
				}
			case 2:
				_uiFragenErstellerWerkzeug.zumFragePunkteBereich();
			break;
			case 1:
				_uiFragenErstellerWerkzeug.zumFragetextBereich();
			break;
			case 0:
				_uiFragenErstellerWerkzeug.zumFragetypBereich();
			break;
		}
		_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(pruefeObWeiterErlaubt());
	}

	/**
	 * Lässt das Werkzeug zum nächsten Bereich wechseln.
	 */
	protected void zumNaechstenBereich()
	{
		++_aktuellerBereich;
		_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(true);
		if(_aktuellerBereich >= 5)
		{
			_aktuellerBereich = 5;
			_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(false);
		}
		else if(_aktuellerBereich == 1)
		{
			liesEigenschaftenEin();
		}

		switch(_aktuellerBereich)
		{
			case 1:
				_uiFragenErstellerWerkzeug.zumFragetextBereich();
			break;
			case 2:
				_uiFragenErstellerWerkzeug.zumFragePunkteBereich();
			break;
			case 3:
				if(_hatBild)
				{
					_uiFragenErstellerWerkzeug.zumBildBereich();
					break;
				}
				else
				{
					++_aktuellerBereich;
				}
			case 4:
				if(_hatQuelltext)
				{
					_uiFragenErstellerWerkzeug.zumQuelltextBereich();
					break;
				}
				else
				{
					++_aktuellerBereich;
					_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(false);
				}
			case 5:
				_uiFragenErstellerWerkzeug.getAbschliessenButton().setEnabled(
				        pruefeObFrageAbgabebereit());
				liesFragenInformationenEin();
				_uiFragenErstellerWerkzeug.zumAntwortenBereich();
			break;
		}
		_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(pruefeObWeiterErlaubt());
	}

	/**
	 * Prüft, ob der aktuelle Bereich schon bearbeitet wurde.
	 * 
	 * @return True, falls alle Felder im Bereich (gültig) ausgefüllt sind.
	 */
	private boolean pruefeObWeiterErlaubt()
	{
		liesFragenInformationenEin();
		
		if(_aktuellerBereich == 0)
		{
			liesEigenschaftenEin();
		}

		switch(_aktuellerBereich)
		{
			case 0:
				if(_fragetyp.isEmpty() || _fragetyp.equals("NOT"))
				{
					return false;
				}
			break;
			case 1:
				if(_fragetext.isEmpty())
				{
					return false;
				}
			break;
			case 2:
				if(_fragePunkte <= 0 || _abzugPunkte < 0)
				{
					return false;
				}
			break;
			case 3:
				if(_hatBild && (_bild == null || !_bild.exists()))
				{
					return false;
				}
			break;
			case 4:
				if(_hatQuelltext && _quelltext.isEmpty())
				{
					return false;
				}
			break;
			case 5:
				return false;
		}
		return true;
	}

	/**
	 * Prüft, ob die Frage abgabebereit ist.
	 * 
	 * @return True, falls alle Bereiche (gültig) ausgefüllt sind.
	 */
	private boolean pruefeObFrageAbgabebereit()
	{
		// Fragetyp muss angegeben sein.
		if(_fragetyp.isEmpty() || _fragetyp.equals("NOT"))
		{
			return false;
		}

		// Fragetext darf nicht leer sein
		if(_fragetext.isEmpty())
		{
			return false;
		}

		// Punkte für Frage darf nicht kleiner gleich 0 sein.
		if(_fragePunkte <= 0)
		{
			return false;
		}

		// Falls Bild angegeben muss auch die Datei vorhanden sein.
		if(_hatBild && _bild != null && !_bild.exists())
		{
			return false;
		}

		// Falls Quelltext angegeben muss auch Quelltext vorhanden sein
		if(_hatQuelltext && _quelltext.isEmpty())
		{
			return false;
		}

		// Es müssen bei Klick und Auswahlantwort mindestens zwei
		// Antwortmöglichkeiten
		// gegeben sein.
		if(_fragetyp.equals("Klick") || _fragetyp.equals("Auswahl"))
		{
			if(_antworten.size() < 2)
			{
				return false;
			}
			// Bei einer Auswahlantwort darf nur eine Antwort richtig sein.
			else if(_fragetyp.equals("Auswahl"))
			{
				int richtigeAntworten = 0;

				for(Object antwort : _antworten)
				{
					if(((ChoiceAntwort) antwort).istRichtig())
					{
						++richtigeAntworten;
					}
				}

				if(richtigeAntworten != 1)
				{
					return false;
				}
			}
		}
		// Es muss mindestens eine Antwortmöglichkeit bei einer Textfrage geben.
		else if(_fragetyp.equals("Text"))
		{
			if(_antworten.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Liest die Basisinformationen der Frage ein:
	 * 
	 * Fragetyp Bild Vorhanden Quelltext Vorhanden
	 */
	private void liesEigenschaftenEin()
	{
		if(_fragetyp != _uiFragenErstellerWerkzeug.getFragetyp())
		{
			setzeFrageZurueck();
		}

		if(_hatBild != _uiFragenErstellerWerkzeug.getBildVorhanden())
		{
			setzeBildZurueck();
		}

		if(_hatQuelltext != _uiFragenErstellerWerkzeug.getQuelltextVorhanden())
		{
			setzeQuelltextZurueck();
		}

		_fragetyp = _uiFragenErstellerWerkzeug.getFragetyp();
		_hatBild = _uiFragenErstellerWerkzeug.getBildVorhanden();
		_hatQuelltext = _uiFragenErstellerWerkzeug.getQuelltextVorhanden();
	}

	/**
	 * Setzt den Bildbereich des Werkzeugs zurück.
	 */
	private void setzeBildZurueck()
	{
		_uiFragenErstellerWerkzeug.setzeBildZurueck();

		registriereFeldListener();

		liesFragenInformationenEin();
	}

	/**
	 * Setzt den Quelltextbereich des Werkzeugs zurück.
	 */
	private void setzeQuelltextZurueck()
	{
		_uiFragenErstellerWerkzeug.setzeQuelltextZurueck();

		registriereFeldListener();

		liesFragenInformationenEin();
	}

	/**
	 * Setzt alle Bereiche des Fragenwerkzeugs zurück.
	 */
	private void setzeFrageZurueck()
	{
		_uiFragenErstellerWerkzeug.setzeZurueck();

		liesFragenInformationenEin();

		registriereAntwortButtonListener();
		registriereFeldListener();

		_antworten = new ArrayList<Object>();
	}

	/**
	 * Liest alle Informationen der Frage ein:
	 * 
	 * Fragetext Punkte (Richtig/Falsch) Bild (falls vorhanden) Quelltext (falls
	 * vorhanden)
	 */
	private void liesFragenInformationenEin()
	{
		_fragetext = _uiFragenErstellerWerkzeug.getFragetext();
		_fragePunkte = _uiFragenErstellerWerkzeug.getFragePunkte();
		_abzugPunkte = _uiFragenErstellerWerkzeug.getAbzugPunkte();
		_bild = _uiFragenErstellerWerkzeug.getBild();
		_quelltext = _uiFragenErstellerWerkzeug.getQuelltext();
	}

	/**
	 * Erstellt eine Frage aus den gegebenen Informationen.
	 * 
	 * @return Eine Frage mit Informationen aus dem Fragenwerkzeug.
	 */
	private Frage erstelleFrage()
	{
		switch(_fragetyp)
		{
			case "Klick":
				ChoiceAntwort[] klickantworten = new ChoiceAntwort[_antworten.size()];
				klickantworten = _antworten.toArray(klickantworten);
				if(_hatBild && _hatQuelltext)
				{
					_frage = new OptionalKlickFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        klickantworten, _quelltext, _bild);
				}
				else if(_hatBild)
				{
					_frage = new OptionalKlickFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        klickantworten, _bild);
				}
				else if(_hatQuelltext)
				{
					_frage = new OptionalKlickFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        klickantworten, _quelltext);
				}
				else
				{
					_frage = new KlickFrage(_fragetext, _fragePunkte, _abzugPunkte, klickantworten);
				}
			break;
			case "Auswahl":
				ChoiceAntwort[] auswahlantworten = new ChoiceAntwort[_antworten.size()];
				auswahlantworten = _antworten.toArray(auswahlantworten);
				if(_hatBild && _hatQuelltext)
				{
					_frage = new OptionalAuswahlFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        auswahlantworten, _quelltext, _bild);
				}
				else if(_hatBild)
				{
					_frage = new OptionalAuswahlFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        auswahlantworten, _bild);
				}
				else if(_hatQuelltext)
				{
					_frage = new OptionalAuswahlFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        auswahlantworten, _quelltext);
				}
				else
				{
					_frage = new AuswahlFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        auswahlantworten);
				}
			break;
			case "Text":
				String[] textantworten = new String[_antworten.size()];
				textantworten = _antworten.toArray(textantworten);
				if(_hatBild && _hatQuelltext)
				{
					_frage = new OptionalTextFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        textantworten, _quelltext, _bild);
				}
				else if(_hatBild)
				{
					_frage = new OptionalTextFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        textantworten, _bild);
				}
				else if(_hatQuelltext)
				{
					_frage = new OptionalTextFrage(_fragetext, _fragePunkte, _abzugPunkte,
					        textantworten, _quelltext);
				}
				else
				{
					_frage = new TextFrage(_fragetext, _fragePunkte, _abzugPunkte, textantworten);
				}
			break;
		}

		return _frage;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(arg instanceof String || arg instanceof Antwort)
		{
			if(_antwortGeaendert)
			{
				_antworten.set(_aktuelleAntwortIndex, arg);
			}
			else
			{
				_antworten.add(arg);
			}
			_uiFragenErstellerWerkzeug.setAntwortenAnzeige(_antworten);
		}
	}
}
