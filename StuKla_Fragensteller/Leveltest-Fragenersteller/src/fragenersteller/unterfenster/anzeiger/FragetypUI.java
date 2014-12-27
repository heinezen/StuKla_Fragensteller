package fragenersteller.unterfenster.anzeiger;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Eine UI zur Auswahl des Typs einer Frage.
 * 
 * @author Christophad
 */
public class FragetypUI
{
	private JRadioButton[] _fragetypenButtons;
	private JCheckBox[] _fragetypenOptional;

	private JPanel _fragetypBereich;

	/**
	 * Erstellt einen Panel zur Auswahl des Fragetyps.
	 */
	public FragetypUI()
	{
		erzeugeFragetypBereich("", false, false);
	}

	/**
	 * Erstellt einen Panel zur Auswahl des Fragetyps mit vorbestimmten Werten.
	 * 
	 * @param fragetyp
	 *            Der ausgewählte Fragetyp
	 * @param bild
	 *            Gibt an ob ein Bild vorhanden ist.
	 * @param quelltext
	 *            Gibt an ob Quelltext vorhanden ist.
	 */
	public FragetypUI(String fragetyp, boolean bild, boolean quelltext)
	{
		erzeugeFragetypBereich(fragetyp, bild, quelltext);
	}

	/**
	 * Initialisiert die Bedienelemente des Panels
	 * 
	 * @param fragetyp
	 *            Der ausgewählte Fragetyp
	 * @param bild
	 *            Gibt an ob ein Bild vorhanden ist.
	 * @param quelltext
	 *            Gibt an ob Quelltext vorhanden ist.
	 */
	private void erzeugeFragetypBereich(String fragetyp, boolean bild, boolean quelltext)
	{
		_fragetypBereich = new JPanel();
		_fragetypBereich.setLayout(new BoxLayout(_fragetypBereich, BoxLayout.PAGE_AXIS));

		ButtonGroup typGruppe = new ButtonGroup();
		_fragetypenButtons = new JRadioButton[3];
		_fragetypenOptional = new JCheckBox[2];

		_fragetypenButtons[0] = new JRadioButton("Multiple-Choice");
		typGruppe.add(_fragetypenButtons[0]);
		_fragetypBereich.add(_fragetypenButtons[0]);

		_fragetypenButtons[1] = new JRadioButton("Single-Choice");
		typGruppe.add(_fragetypenButtons[1]);
		_fragetypBereich.add(_fragetypenButtons[1]);

		_fragetypenButtons[2] = new JRadioButton("Textantwort");
		typGruppe.add(_fragetypenButtons[2]);
		_fragetypBereich.add(_fragetypenButtons[2]);

		switch(fragetyp)
		{
			case "Klick":
				_fragetypenButtons[0].setSelected(true);
			break;
			case "Auswahl":
				_fragetypenButtons[1].setSelected(true);
			break;
			case "Text":
				_fragetypenButtons[2].setSelected(true);
		}

		_fragetypenOptional[0] = new JCheckBox("Bild");
		_fragetypBereich.add(_fragetypenOptional[0]);

		if(bild)
		{
			_fragetypenOptional[0].setSelected(true);
		}

		_fragetypenOptional[1] = new JCheckBox("Quelltext");
		_fragetypBereich.add(_fragetypenOptional[1]);

		if(quelltext)
		{
			_fragetypenOptional[1].setSelected(true);
		}
	}

	/**
	 * Gibt den Panel mit den Bedienelementen zurück
	 * 
	 * @return Der Panel zur Auswahl des Fragetyps
	 */
	public JPanel getPanel()
	{
		return _fragetypBereich;
	}

	/**
	 * Gibt zurück, ob die Frage ein Bild enthält.
	 * 
	 * @return Wahrheitswert, ob Frage Bild enthält.
	 */
	public boolean getBildVorhanden()
	{
		return _fragetypenOptional[0].isSelected();
	}

	/**
	 * Gibt zurück, ob die Frage Quelltext enthält.
	 * 
	 * @return Wahrheitswert, ob Frage Quelltext enthält.
	 */
	public boolean getQuelltextVorhanden()
	{
		return _fragetypenOptional[1].isSelected();
	}

	/**
	 * Gibt den im Panel angegebenen Fragetyp zurück
	 * 
	 * @return Der angegebene Typ
	 */
	public String getFragetyp()
	{
		for(int i = 0; i < _fragetypenButtons.length; ++i)
		{
			if(_fragetypenButtons[i].isSelected())
			{
				switch(i)
				{
					case 0:
						return "Klick";
					case 1:
						return "Auswahl";
					case 2:
						return "Text";
				}
			}
		}
		return "NOT";
	}

	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();

		comp.add(_fragetypenButtons[0]);
		comp.add(_fragetypenButtons[1]);
		comp.add(_fragetypenButtons[2]);

		comp.add(_fragetypenOptional[0]);
		comp.add(_fragetypenOptional[1]);

		return comp;
	}
}
