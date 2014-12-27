package fragenersteller.unterfenster.anzeiger;

import java.awt.Component;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Eine UI zur Eingabe der Frage- und Abzugspunkte einer Frage.
 * 
 * @author Christophad
 */
public class FragePunkteUI
{
	private JLabel _fragePunkteLabel;
	private JLabel _abzugPunkteLabel;

	private JFormattedTextField _fragePunkteFeld;
	private JFormattedTextField _abzugPunkteFeld;

	private JPanel _fragePunkteBereich;

	/**
	 * Erstellt einen Panel zum Eingeben von Fragepunkten.
	 */
	public FragePunkteUI()
	{
		erzeugeFragePunkteBereich(0, 0);
	}

	/**
	 * Erstellt einen Panel zum Eingeben von Fragepunkten mit vorbestimmten
	 * Frage- und Abzugspunkten.
	 * 
	 * @param fragepunkte
	 *            Die Punkte für eine richtige Antwort.
	 * @param abzugspunkte
	 *            Der Abzug bei einer falschen Antwort.
	 */
	public FragePunkteUI(int fragepunkte, int abzugspunkte)
	{
		erzeugeFragePunkteBereich(fragepunkte, abzugspunkte);
	}

	/**
	 * Erzeugt den Bereich in dem die Punkte pro richtiger Antwort eingegeben
	 * werden.
	 * 
	 * @param fragepunkte
	 *            Die Punkte für eine richtige Antwort.
	 * @param abzugspunkte
	 *            Der Abzug bei einer falschen Antwort.
	 */
	private void erzeugeFragePunkteBereich(int fragepunkte, int abzugspunkte)
	{
		_fragePunkteBereich = new JPanel();
		_fragePunkteBereich.setLayout(new GridLayout(2, 2));

		_fragePunkteLabel = new JLabel("Punkte für richtige Antwort: ");
		_abzugPunkteLabel = new JLabel("Abzugspunkte für falsche Antwort: ");

		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setParseIntegerOnly(true);

		_fragePunkteFeld = new JFormattedTextField(format);
		_abzugPunkteFeld = new JFormattedTextField(format);
		if(fragepunkte > 0)
		{
			_abzugPunkteFeld.setText("" + abzugspunkte);
			_fragePunkteFeld.setText("" + fragepunkte);
		}

		_fragePunkteBereich.add(_fragePunkteLabel);
		_fragePunkteBereich.add(_fragePunkteFeld);
		_fragePunkteBereich.add(_abzugPunkteLabel);
		_fragePunkteBereich.add(_abzugPunkteFeld);
	}

	/**
	 * Gibt den Panel mit den Bedienelementen zurück
	 * 
	 * @return Der Panel zur Eingabe der Fragepunkte
	 */
	public JPanel getPanel()
	{
		return _fragePunkteBereich;
	}

	/**
	 * Gibt die Fragepunkte aus dem Eingabefeld zurück.
	 * 
	 * @return Die Fragepunkte.
	 */
	public int getFragePunkte()
	{
		if(!_fragePunkteFeld.getText().isEmpty())
		{
			try
			{
				return Integer.parseInt(_fragePunkteFeld.getText());
			}
			catch(NumberFormatException nfe)
			{
				return 0;
			}
		}
		return 0;
	}

	/**
	 * Gibt die Abzugspunkte aus dem Eingabefeld zurück.
	 * 
	 * @return Die Abzugspunkte.
	 */
	public int getAbzugPunkte()
	{
		if(!_abzugPunkteFeld.getText().isEmpty())
		{
			try
			{
				return Integer.parseInt(_abzugPunkteFeld.getText());
			}
			catch(NumberFormatException nfe)
			{
				return 0;
			}
		}
		return 0;
	}

	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();

		comp.add(_fragePunkteFeld);
		comp.add(_abzugPunkteFeld);

		return comp;
	}
}
