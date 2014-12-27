package fragenersteller.unterfenster.eigenschaften;

import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.DateFormatter;

/**
 * Die zum EigenschaftenWerkzeug zugehörige UI.
 * 
 * @author Christophad
 */
class EigenschaftenUI
{
	private static final String NAME = "Eigenschaftenwerkzeug";
	
	private final JLabel _modulAngabeLabel = new JLabel("Modul angeben: ");
	private final JLabel _datumAngabeLabel = new JLabel("Datum angeben: ");
	private final JLabel _zeitAngabeLabel = new JLabel("Bearbeitungszeit angeben: ");
	
	private JTextField _modulAngabeFeld;
	private JFormattedTextField _datumAngabeFeld;
	private JFormattedTextField _zeitAngabeFeld;
	
	private JButton _aendernButton;
	
	private JPanel _anzeige;
	
	private JFrame _frame;
	
	public EigenschaftenUI(String[] uebersicht)
	{
		initGUI(uebersicht);
	}

	private void initGUI(String[] uebersicht)
    {
		erzeugeEigenschaftenBereich(uebersicht);
		
	    erzeugeHauptfenster();
    }
	
	private void erzeugeEigenschaftenBereich(String[] uebersicht)
    {
	    _anzeige = new JPanel();
	    _anzeige.setLayout(new GridLayout(4, 2));
	    
	    _modulAngabeFeld = new JTextField(uebersicht[1]);
	    _datumAngabeFeld = new JFormattedTextField(new DateFormatter());
	    Calendar c = Calendar.getInstance();
	    _datumAngabeFeld.setText(String.format("%td.%tm.%tY", c, c, c));
	    
	    NumberFormat format = NumberFormat.getIntegerInstance();
	    format.setParseIntegerOnly(true);
	    _zeitAngabeFeld = new JFormattedTextField(format);
	    _zeitAngabeFeld.setText(uebersicht[3]);
	    
	    _anzeige.add(_modulAngabeLabel);
	    _anzeige.add(_modulAngabeFeld);
	    _anzeige.add(_datumAngabeLabel);
	    _anzeige.add(_datumAngabeFeld);
	    _anzeige.add(_zeitAngabeLabel);
	    _anzeige.add(_zeitAngabeFeld);
	    
	    _aendernButton = new JButton("Eigenschaften ändern");
	    
	    _anzeige.add(_aendernButton);
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
	
	public JButton getEigenschaftenButton()
	{
		return _aendernButton;
	}
	
	public String getModul()
	{
		return _modulAngabeFeld.getText();
	}
	
	public String getDatum()
	{
		return _datumAngabeFeld.getText();
	}
	
	public String getZeit()
	{
		return _zeitAngabeFeld.getText();
	}
	
	/**
	 * Schließt das Fenster der UI.
	 */
	public void schliesseFenster()
    {
	    _frame.dispose();
    }
}
