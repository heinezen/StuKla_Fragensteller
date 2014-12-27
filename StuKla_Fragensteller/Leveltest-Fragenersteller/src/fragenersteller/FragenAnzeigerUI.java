package fragenersteller;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import materialien.Frage;
import materialien.OptionalFrage;
import materialien.choice.AbstractChoiceFrage;

public class FragenAnzeigerUI 
{
	private final JLabel _fragensatzUeberblick = new JLabel("Überblick");
	private final JLabel _fragensatzVersionLabel = new JLabel("Version: ");
	private final JLabel _fragensatzModulLabel = new JLabel("Modul: ");
	private final JLabel _fragensatzDatumLabel = new JLabel("Datum: ");
	private final JLabel _fragensatzZeitLabel = new JLabel("Bearbeitungszeit: ");
	
	private final JLabel _fragensatzVersion = new JLabel(Startup.VERSION);
	private JLabel _fragensatzModul;
	private JLabel _fragensatzDatum;
	private JLabel _fragensatzZeit;
	
	private JComboBox<String> _fragenBox;
	
	private JButton _eigenschaftenButton;
	private JButton _neueFrageButton;
	private JButton _frageAendernButton;
	private JButton _frageEntfernenButton;
	private JButton _fragensatzErstellenButton;
	
	private JTextArea _fragenanzeige;
	
	private JPanel _anzeige;
	private JPanel _bedienBereich;
	private JPanel _informationsBereich;
	private JPanel _aenderungsBereich;
	private JPanel _buttonBereich;
	
	private JFrame _frame;
	
	public FragenAnzeigerUI()
	{
		initGUI();
	}

	private void initGUI()
    {
	    _anzeige = new JPanel();
		_anzeige.setLayout(new FlowLayout());
		
	    erzeugeFragenAnzeige();
	    erzeugeButtonBereich();
	    
	    erzeugeHauptfenster();
    }

	private void erzeugeHauptfenster()
    {
		_frame = new JFrame("Fragenersteller");
        _frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame.setTitle("Fragenersteller");
        _frame.setVisible(true);
        _frame.add(_anzeige);
        _frame.pack();
        _frame.setLocationRelativeTo(null);
    }

	private void erzeugeButtonBereich()
    {
	    _buttonBereich = new JPanel();
	    _buttonBereich.setLayout(new GridLayout(5, 1, 5, 5));
	    
	    _aenderungsBereich = new JPanel();
	    _aenderungsBereich.setLayout(new GridLayout(1, 2, 5, 0));
	    
	    _fragenBox = new JComboBox<String>();
	    _fragenBox.addItem("Keine Fragen");
	    
	    _eigenschaftenButton = new JButton("Eigenschaften ändern");
	    _neueFrageButton = new JButton("Neue Frage erstellen");
	    _fragensatzErstellenButton = new JButton("Fragensatz erstellen");
	    
	    _frageAendernButton = new JButton("Frage ändern");
	    _frageEntfernenButton = new JButton("Frage entfernen");
	    
	    _aenderungsBereich.add(_frageAendernButton);
	    _aenderungsBereich.add(_frageEntfernenButton);
	    
	    _buttonBereich.add(_eigenschaftenButton);
	    _buttonBereich.add(_fragenBox);
	    _buttonBereich.add(_aenderungsBereich);
	    _buttonBereich.add(_neueFrageButton);
	    _buttonBereich.add(_fragensatzErstellenButton);
	    
	    _bedienBereich.add(_buttonBereich);
    }

	private void erzeugeFragenAnzeige()
    {	
		_bedienBereich = new JPanel();
		_bedienBereich.setLayout(new GridLayout(2, 1));
		
		_fragenanzeige = new JTextArea(20, 15);
		_fragenanzeige.setText("Keine Fragen");
		_fragenanzeige.setEditable(false);
		_fragenanzeige.setLineWrap(true);
		_fragenanzeige.setWrapStyleWord(true);
		
		JScrollPane scroll = new JScrollPane(_fragenanzeige);
		
		_anzeige.add(scroll);
		
		_informationsBereich = new JPanel();
		_fragensatzModul = new JLabel("Modul");
		_fragensatzDatum = new JLabel("Datum");
		_fragensatzZeit = new JLabel("Zeit");
		
		_informationsBereich.setLayout(new GridLayout(5, 2));
		_informationsBereich.add(_fragensatzUeberblick);
		_informationsBereich.add(new JLabel());
		_informationsBereich.add(_fragensatzVersionLabel);
		_informationsBereich.add(_fragensatzVersion);
		_informationsBereich.add(_fragensatzModulLabel);
		_informationsBereich.add(_fragensatzModul);
		_informationsBereich.add(_fragensatzDatumLabel);
		_informationsBereich.add(_fragensatzDatum);
		_informationsBereich.add(_fragensatzZeitLabel);
		_informationsBereich.add(_fragensatzZeit);
		
		_bedienBereich.add(_informationsBereich);
		
		_anzeige.add(_bedienBereich);
    }
	
	public JButton getEigenschaftenButton()
	{
		return _eigenschaftenButton;
	}
	
	public JComboBox<String> getFragenBox()
	{
		return _fragenBox;
	}
	
	public JButton getFrageAendernButton()
	{
		return _frageAendernButton;
	}
	
	public JButton getFrageEntfernenButton()
	{
		return _frageEntfernenButton;
	}
	
	public JButton getNeueFrageButton()
	{
		return _neueFrageButton;
	}
	
	public JButton getFragensatzErstellenButton()
	{
		return _fragensatzErstellenButton;
	}

	public void aktualisiereAnzeige(List<Frage> fragensatz)
    {
	    aktualisiereFragenanzeige(fragensatz);
    }

	public void aktualisiereUebersicht(String[] uebersicht)
    {
	    _fragensatzModul.setText(uebersicht[1]);
	    _fragensatzDatum.setText(uebersicht[2]);
	    _fragensatzZeit.setText(uebersicht[3] + " min");
    }

	private void aktualisiereFragenanzeige(List<Frage> fragensatz)
    {
		String fragen = "";
	    if(!fragensatz.isEmpty())
	    {
	    	for(Frage frage : fragensatz)
	    	{
	    		fragen += "Frage " + (fragensatz.indexOf(frage) + 1) + "\n";
	    		fragen += frage.getFragetext() + "\n";
	    		fragen += "Fragetyp: " + frage.getFragetyp() + "\n";
	    		fragen += "Fragepunkte: " + frage.getPunkteFuerAntwort() + "\n";
	    		fragen += "Abzugspunkte: " + frage.getAbzugsPunkte() + "\n";
	    		if(frage instanceof OptionalFrage)
	    		{
	    			fragen += "Bild: " + ((OptionalFrage) frage).hatBild() + "\n";
	    			fragen += "Quelltext: " + ((OptionalFrage) frage).hatQuelltext() +"\n";
	    		}
	    		else
	    		{
	    			fragen += "Bild: false\n";
	    			fragen += "Quelltext: false\n";
	    		}
	    		
	    		String[] antworten = frage.getAntworttexte();
	    		if(frage instanceof AbstractChoiceFrage)
	    		{
	    			boolean[] antwortwerte = (boolean[]) frage.getAntwortenWerte();
	    			for(int i = 0; i < antwortwerte.length; ++i)
	    			{
	    				fragen += antworten[i] + ":" + antwortwerte[i] + "\n";
	    			}
	    		}
	    		else
	    		{
	    			for(String antwort : antworten)
	    			{
	    				fragen += antwort + "\n";
	    			}
	    		}
	    		
	    		fragen += "\n";
	    	}
	    }
	    else
	    {
	    	fragen = "Keine Fragen";
	    }
	    
	    _fragenanzeige.setText(fragen);
	    
	    _fragenBox.removeAllItems();
		for(int i = 1; i <= fragensatz.size(); ++i)
		{
			_fragenBox.addItem("Frage " + i);
		}
		
		_frame.pack();
    }
	
	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();
		
		comp.add(_fragenanzeige);
		comp.add(_fragensatzModul);
		comp.add(_fragensatzDatum);
		comp.add(_fragensatzZeit);
		
		return comp;
	}
	
	public void schliesseFenster()
	{
		_frame.dispose();
	}
}
