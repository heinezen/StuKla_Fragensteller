package fragenersteller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import fragenersteller.unterfenster.FragenErstellerWerkzeug;
import fragenersteller.unterfenster.eigenschaften.EigenschaftenWerkzeug;
import materialien.Frage;
public class FragenAnzeigerWerkzeug implements Observer
{
	private FragenAnzeigerUI _uiFragenAnzeiger;
	private DateiSchreiber _dateiSchreiber;
	
	private FragenErstellerWerkzeug _fragenErsteller;
	private EigenschaftenWerkzeug _eigenschaftenWerkzeug;
	
	private int _aktuelleFrageIndex;
	private boolean _frageGeaendert;
	
	private List<Frage> _fragensatz;
	private String[] _uebersicht;
	
	public FragenAnzeigerWerkzeug()
	{
		_uiFragenAnzeiger = new FragenAnzeigerUI();
		
		_dateiSchreiber = new DateiSchreiber(new File("./tests/"));
		
		_uiFragenAnzeiger.getFrageAendernButton().setEnabled(false);
		_uiFragenAnzeiger.getFrageEntfernenButton().setEnabled(false);
		_uiFragenAnzeiger.getFragensatzErstellenButton().setEnabled(false);
		
		_fragensatz = new ArrayList<Frage>();
		
		_uebersicht = new String[4];
		_uebersicht[0] = Startup.VERSION;
		_uebersicht[1] = "";
		_uebersicht[2] = "";
		_uebersicht[3] = "";
		
		_aktuelleFrageIndex = -1;
		_frageGeaendert = false;
		
		registriereListener();
	}
	
	public FragenAnzeigerWerkzeug(List<Frage> fragensatz)
	{
		_uiFragenAnzeiger = new FragenAnzeigerUI();
		
		_dateiSchreiber = new DateiSchreiber(new File("./Tests/"));
		
		_uiFragenAnzeiger.getFragensatzErstellenButton().setEnabled(false);
		
		_fragensatz = fragensatz;
		
		registriereListener();
	}

	private void registriereListener()
    {
		final FragenAnzeigerWerkzeug werkzeug = this;
		
		JButton button = _uiFragenAnzeiger.getNeueFrageButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_frageGeaendert = false;
				
				_fragenErsteller = new FragenErstellerWerkzeug();
				_fragenErsteller.addObserver(werkzeug);
			}
		});
		
		button = _uiFragenAnzeiger.getEigenschaftenButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_eigenschaftenWerkzeug = new EigenschaftenWerkzeug(_uebersicht);
				_eigenschaftenWerkzeug.addObserver(werkzeug);
			}
		});
		
		button = _uiFragenAnzeiger.getFragensatzErstellenButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				schreibeFragenInDatei();
				
				_uiFragenAnzeiger.schliesseFenster();
			}
		});
		
		button = _uiFragenAnzeiger.getFrageAendernButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_frageGeaendert = true;
				
				_fragenErsteller = new FragenErstellerWerkzeug(_fragensatz.get(_aktuelleFrageIndex));
				_fragenErsteller.addObserver(werkzeug);
			}
		});
		
		button = _uiFragenAnzeiger.getFrageEntfernenButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_fragensatz.remove(_aktuelleFrageIndex);
				_uiFragenAnzeiger.aktualisiereAnzeige(_fragensatz);
				
				_aktuelleFrageIndex = _uiFragenAnzeiger.getFragenBox().getSelectedIndex();
				
				boolean aenderungMoeglich = _aktuelleFrageIndex >= 0;
				
				_uiFragenAnzeiger.getFrageAendernButton().setEnabled(aenderungMoeglich);
				_uiFragenAnzeiger.getFrageEntfernenButton().setEnabled(aenderungMoeglich);
			}
		});
		
		final JComboBox<String> box = _uiFragenAnzeiger.getFragenBox();
		box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_aktuelleFrageIndex = box.getSelectedIndex();
				
				boolean aenderungMoeglich = _aktuelleFrageIndex >= 0;
				
				_uiFragenAnzeiger.getFrageAendernButton().setEnabled(aenderungMoeglich);
				_uiFragenAnzeiger.getFrageEntfernenButton().setEnabled(aenderungMoeglich);
			}
		});
		
		registriereFeldListener();
    }
	
	private void registriereFeldListener()
	{
		for(Component comp : _uiFragenAnzeiger.getListenerComponents())
		{
			if(comp instanceof JTextComponent)
			{
				((JTextComponent) comp).getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						
					}
					
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						JButton button = _uiFragenAnzeiger.getFragensatzErstellenButton();
						
						button.setEnabled(pruefeObErstellungMoeglich());
					}
					
					@Override
					public void changedUpdate(DocumentEvent e)
					{
						
					}
				});
			}
			else
			{
				comp.addPropertyChangeListener("text", new PropertyChangeListener() {
				
					@Override
					public void propertyChange(PropertyChangeEvent evt)
					{
						JButton button = _uiFragenAnzeiger.getFragensatzErstellenButton();
					
						button.setEnabled(pruefeObErstellungMoeglich());
					}
				});
			}
		}
	}

	protected boolean pruefeObErstellungMoeglich()
    {
	    for(String s : _uebersicht)
	    {
	    	if(s.isEmpty())
	    	{
	    		return false;
	    	}
	    }
	    
	    if(_fragensatz.isEmpty())
	    {
	    	return false;
	    }
	    
		return true;
    }

	private void schreibeFragenInDatei()
    {
	    _dateiSchreiber.schreibeInDatei(_fragensatz, _uebersicht);
    }

	@Override
    public void update(Observable o, Object arg)
    {
	    if(o instanceof FragenErstellerWerkzeug)
	    {
	    	if(_frageGeaendert)
	    	{
	    		_fragensatz.set(_aktuelleFrageIndex, (Frage) arg);
	    	}
	    	else
	    	{
	    		_fragensatz.add((Frage) arg);
	    	}
	    	_uiFragenAnzeiger.aktualisiereAnzeige(_fragensatz);
	    }
	    if(o instanceof EigenschaftenWerkzeug)
	    {
	    	_uebersicht = (String[]) arg;
	    	
	    	_uiFragenAnzeiger.aktualisiereUebersicht(_uebersicht);
	    }
    }
}
