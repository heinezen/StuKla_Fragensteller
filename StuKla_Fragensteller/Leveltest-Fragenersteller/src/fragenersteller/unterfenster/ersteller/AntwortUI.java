package fragenersteller.unterfenster.ersteller;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Die zum AntwortWerkzeug zugehörige UI.
 * 
 * @author Christophad
 */
class AntwortUI
{
	// Der Name des Fensters
	private static final String NAME = "Antwortenwerkzeug";
	
	// Button zum Hinzufügen einer Antwort
	private JButton _antwortHinzufuegenButton;
	
	// Eingabefelder
	private JTextField _antwortFeld;
	private JRadioButton[] _antwortWerte;
	
	private JPanel _antwortenBereich;
	
	private JFrame _frame;
	
	/**
	 * Erstellt eine AntwortUI für einen bestimmten Fragetyp.
	 * 
	 * @param fragetyp Der Fragetyp der zugehörigen Frage.
	 */
	public AntwortUI(String fragetyp)
	{
		erzeugeAntwortenBereich(fragetyp, "", true);
		
		registriereListener();
		
		erzeugeHauptfenster();
	}
	
	/**
	 * Erstellt eine AntwortUI für einen bestimmten Fragetyp mit
	 * Antworttext.
	 * 
	 * @param _fragetyp
	 * @param antwort
	 */
	public AntwortUI(String fragetyp, String antwort)
    {
		erzeugeAntwortenBereich(fragetyp, antwort, true);
		
		registriereListener();
		
		erzeugeHauptfenster();
    }
	
	/**
	 * Erstellt eine AntwortUI für einen bestimmten Fragetyp mit
	 * Antworttext und Wahrheitswert.
	 * 
	 * @param _fragetyp
	 * @param antwort
	 * @param wahr
	 */
	public AntwortUI(String fragetyp, String antwort, boolean wahr)
    {
		erzeugeAntwortenBereich(fragetyp, antwort, wahr);
		
		registriereListener();
		
		erzeugeHauptfenster();
    }

	/**
	 * Erstellt die Listener für die Eingabefelder
	 */
	private void registriereListener()
    {
		_antwortFeld.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				aendereButton();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				aendereButton();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				aendereButton();
			}
			
			private void aendereButton()
			{
				if(getAntwortText().isEmpty())
				{
					_antwortHinzufuegenButton.setEnabled(false);
				}
				else
				{
					_antwortHinzufuegenButton.setEnabled(true);
				}
			}
		});
    }

	/**
	 * Erzeugt den Bereich in dem die Antworten eingegeben
	 * und hinzugefügt werden.
	 * 
	 * @param fragetyp Der Fragetyp der zugehörigen Frage
	 * @param wahr 
	 * @param antwort 
	 */
	private void erzeugeAntwortenBereich(String fragetyp, String antwort, boolean wahr)
    {
		_antwortenBereich = new JPanel();
		_antwortenBereich.setLayout(new BoxLayout(_antwortenBereich, BoxLayout.PAGE_AXIS));
		
		if(antwort.isEmpty())
		{
			_antwortHinzufuegenButton = new JButton("Antwort hinzufügen");
			_antwortHinzufuegenButton.setEnabled(false);
		}
		else
		{
			_antwortHinzufuegenButton = new JButton("Antwort ändern");
			_antwortHinzufuegenButton.setEnabled(true);
		}
		
		_antwortWerte = new JRadioButton[2];
		
		ButtonGroup buttonGroup = new ButtonGroup();
		_antwortWerte[0] = new JRadioButton("Richtig");
		_antwortWerte[0].setSelected(wahr);
		buttonGroup.add(_antwortWerte[0]);
		
		_antwortWerte[1] = new JRadioButton("Falsch");
		_antwortWerte[1].setSelected(!wahr);
		buttonGroup.add(_antwortWerte[1]);
		
		_antwortFeld = new JTextField(antwort, 30);
		
		_antwortenBereich.add(_antwortFeld);
		
		if(!fragetyp.equals("Text"))
		{
			_antwortenBereich.add(_antwortWerte[0]);
			_antwortenBereich.add(_antwortWerte[1]);
		}
		
		_antwortenBereich.add(_antwortHinzufuegenButton);
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
        _frame.add(_antwortenBereich);
        _frame.pack();
        _frame.setLocationRelativeTo(null);
    }
	
	/**
	 * Gibt den Antworttext aus dem Eingabefeld zurück.
	 * @return Der Antworttext.
	 */
	public String getAntwortText()
	{
		return _antwortFeld.getText();
	}
	
	/**
	 * Gibt den Antwortwert der Antwort zurück.
	 * @return Der Antwortwert.
	 */
	public boolean getAntwortWert()
	{
		return _antwortWerte[0].isSelected();
	}

	/**
	 * Gibt den Button zurück, mit dem Antworten hinzugefügt
	 * werden.
	 * @return Der Antwort-Hinzufügen-Button.
	 */
	public JButton getAntwortHinzufügenButton()
    {
	    return _antwortHinzufuegenButton;
    }

	/**
	 * Schließt das Fenster der UI.
	 */
	public void schliesseFenster()
    {
	    _frame.dispose();
    }
}
