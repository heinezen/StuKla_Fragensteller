package fragenersteller.unterfenster.anzeiger;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Eine UI zur Auswahl des Bilds einer Frage.
 * 
 * @author Christophad
 */
public class BildauswahlUI implements ActionListener
{
	private JFileChooser _bildwaehler;

	private JTextField _bildpfad;

	private JButton _bildwaehlerButton;

	private JPanel _bildBereich;

	/**
	 * Erstellt einen Panel zum Auswählen eines Bilds.
	 */
	public BildauswahlUI()
	{
		erzeugeBildBereich(null);
	}

	/**
	 * Erstellt einen Panel zum Auswählen eines Bilds mit vorbestimmtem Bild
	 * 
	 * @param bild
	 *            Das ausgewählte Bild.
	 */
	public BildauswahlUI(File bild)
	{
		erzeugeBildBereich(bild);
	}

	/**
	 * Erzeugt den Bereich in dem der Pfad zu dem Bild angegeben wird.
	 * 
	 * @param bild Das ausgewählte Bild.
	 */
	private void erzeugeBildBereich(File bild)
	{
		_bildBereich = new JPanel();

		_bildwaehler = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Bilder .jpg, .png, .gif",
		        "jpg", "png", "gif");
		_bildwaehler.setFileFilter(filter);

		_bildwaehler.setSelectedFile(bild);

		_bildpfad = new JTextField("Bitte Datei auswählen", 30);
		_bildpfad.setEditable(false);

		if(bild != null)
		{
			_bildpfad.setText(getBild().getAbsolutePath());
		}

		_bildwaehlerButton = new JButton("Auswählen");
		_bildwaehlerButton.addActionListener(this);

		_bildBereich.add(_bildpfad);
		_bildBereich.add(_bildwaehlerButton);
	}

	/**
	 * Gibt den Panel mit den Bedienelementen zurück
	 * 
	 * @return Der Panel zur Auswahl des Bilds
	 */
	public JPanel getPanel()
	{
		return _bildBereich;
	}

	/**
	 * Gibt den Pfad des Bildes aus dem Eingabefeld zurück.
	 * 
	 * @return Der Pfad als String.
	 */
	public File getBild()
	{
		return _bildwaehler.getSelectedFile();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == _bildwaehlerButton)
		{
			int returnVal = _bildwaehler.showOpenDialog(_bildBereich);

			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				_bildpfad.setText(_bildwaehler.getSelectedFile().getAbsolutePath());
			}
		}
	}

	public List<Component> getListenerComponents()
	{
		List<Component> comp = new ArrayList<Component>();

		comp.add(_bildpfad);

		return comp;
	}
}
