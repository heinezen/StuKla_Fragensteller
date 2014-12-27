package materialien.format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import materialien.Frage;
import materialien.OptionalFrage;
import materialien.choice.ChoiceAntwort;
import materialien.choice.multiChoice.KlickFrage;
import materialien.choice.multiChoice.OptionalKlickFrage;
import materialien.choice.singleChoice.AuswahlFrage;
import materialien.choice.singleChoice.OptionalAuswahlFrage;
import materialien.lueckentext.OptionalTextFrage;
import materialien.lueckentext.TextFrage;

public class FragenFormattierer
{
	private static final int FRAGETYP_INDEX = 0;
	private static final int FRAGETEXT_INDEX = 1;
	private static final int FRAGEPUNKTE_INDEX = 2;
	private static final int ABZUGPUNKTE_INDEX = 3;
	private static final int BILD_INDEX = 4;
	private static final int QUELLTEXT_INDEX = 5;
	private static final int ANTWORTEN_INDEX = 6;

	private static final int ANTWORTTEXT_INDEX = 0;
	private static final int ANTWORTWERT_INDEX = 1;

	public static final String FRAGEN_TRENNER = "###FRA###";
	private static final String FRAGE_TRENNER = "###FRG###";
	private static final String ANTWORT_TRENNER = "###ANT###";
	private static final String BILD_TRENNER = "###BLD###";
	private static final String QUELLTEXT_TRENNER = "###QUE###";

	/**
	 * Erstellt eine Frage aus einem String.
	 * 
	 * @param frage
	 *            Der String der ausgelesen werden soll.
	 * 
	 * @return Eine Frage.
	 */
	public static Frage parseFrage(String frage)
	{
		String rohFrage = frage.replace("\n", "");
		rohFrage = rohFrage.replace(FRAGEN_TRENNER, "");
		rohFrage = rohFrage.replaceFirst(FRAGE_TRENNER, "");

		String[] rohFrageInformationen = rohFrage.split(FRAGE_TRENNER);

		String fragetyp = rohFrageInformationen[FRAGETYP_INDEX];
		String fragetext = rohFrageInformationen[FRAGETEXT_INDEX];
		int fragepunkte = Integer.parseInt(rohFrageInformationen[FRAGEPUNKTE_INDEX]);
		int abzugpunkte = Integer.parseInt(rohFrageInformationen[ABZUGPUNKTE_INDEX]);

		File bild = null;
		String[] rohBildInformationen = rohFrageInformationen[BILD_INDEX].split(BILD_TRENNER);
		if(Boolean.valueOf(rohBildInformationen[0]))
		{
			bild = new File(rohBildInformationen[1]);
		}

		String quelltext = "";
		String[] rohQuelltextInformationen = rohFrageInformationen[QUELLTEXT_INDEX]
		        .split(QUELLTEXT_TRENNER);
		if(Boolean.valueOf(rohQuelltextInformationen[0]))
		{
			for(int i = 1; i < rohQuelltextInformationen.length; ++i)
			{
				quelltext += rohQuelltextInformationen[i] + "\n";
			}
		}

		List<Object> antworten = new ArrayList<Object>();
		for(int i = ANTWORTEN_INDEX; i < rohFrageInformationen.length; ++i)
		{
			if(!fragetyp.equals("Text"))
			{
				antworten.add(parseChoiceAntwort(rohFrageInformationen[i]));
			}
			else
			{
				antworten.add(rohFrageInformationen[i]);
			}
		}

		// TODO
		return erstelleFrage(fragetyp, fragetext, fragepunkte, abzugpunkte, bild, quelltext,
		        antworten);
	}

	/**
	 * Erstellt eine ChoiceAntwort aus einem String.
	 * 
	 * @param antwort
	 *            Der String der ausgelesen werden soll.
	 * 
	 * @return Eine Antwort.
	 */
	private static ChoiceAntwort parseChoiceAntwort(String antwort)
	{
		String[] rohAntwortInformationen = antwort.split(ANTWORT_TRENNER);

		String antworttext = rohAntwortInformationen[ANTWORTTEXT_INDEX];
		boolean antwortwert = Boolean.valueOf(rohAntwortInformationen[ANTWORTWERT_INDEX]);

		return new ChoiceAntwort(antworttext, antwortwert);
	}

	/**
	 * Erstellt eine Frage aus den gegebenen Informationen.
	 * 
	 * @param fragetyp
	 *            Fragetyp der Frage
	 * @param fragetext
	 *            Fragetext der Frage
	 * @param fragepunkte
	 *            Fragepunkte der Frage
	 * @param abzugpunkte
	 *            Abzugspunkte der Frage
	 * @param bild
	 *            Bild der Frage
	 * @param quelltext
	 *            Quelltext der Frage
	 * @param antworten
	 *            Antworten der Frage
	 * 
	 * @return Eine Frage.
	 */
	private static Frage erstelleFrage(String fragetyp, String fragetext, int fragepunkte,
	        int abzugpunkte, File bild, String quelltext, List<Object> antworten)
	{
		switch(fragetyp)
		{
			case "Klick":
				ChoiceAntwort[] klickantworten = new ChoiceAntwort[antworten.size()];
				klickantworten = antworten.toArray(klickantworten);
				if(bild != null && !quelltext.isEmpty())
				{
					return new OptionalKlickFrage(fragetext, fragepunkte, abzugpunkte,
					        klickantworten, quelltext, bild);
				}
				else if(bild != null)
				{
					return new OptionalKlickFrage(fragetext, fragepunkte, abzugpunkte,
					        klickantworten, bild);
				}
				else if(!quelltext.isEmpty())
				{
					return new OptionalKlickFrage(fragetext, fragepunkte, abzugpunkte,
					        klickantworten, quelltext);
				}
				else
				{
					return new KlickFrage(fragetext, fragepunkte, abzugpunkte, klickantworten);
				}
			case "Auswahl":
				ChoiceAntwort[] auswahlantworten = new ChoiceAntwort[antworten.size()];
				auswahlantworten = antworten.toArray(auswahlantworten);
				if(bild != null && !quelltext.isEmpty())
				{
					return new OptionalAuswahlFrage(fragetext, fragepunkte, abzugpunkte,
					        auswahlantworten, quelltext, bild);
				}
				else if(bild != null)
				{
					return new OptionalAuswahlFrage(fragetext, fragepunkte, abzugpunkte,
					        auswahlantworten, quelltext);
				}
				else if(!quelltext.isEmpty())
				{
					return new OptionalAuswahlFrage(fragetext, fragepunkte, abzugpunkte,
					        auswahlantworten, bild);
				}
				else
				{
					return new AuswahlFrage(fragetext, fragepunkte, abzugpunkte, auswahlantworten);
				}
			case "Text":
				String[] textantworten = new String[antworten.size()];
				textantworten = antworten.toArray(textantworten);
				if(bild != null && !quelltext.isEmpty())
				{
					return new OptionalTextFrage(fragetext, fragepunkte, abzugpunkte,
					        textantworten, quelltext, bild);
				}
				else if(bild != null)
				{
					return new OptionalTextFrage(fragetext, fragepunkte, abzugpunkte,
					        textantworten, bild);
				}
				else if(!quelltext.isEmpty())
				{
					return new OptionalTextFrage(fragetext, fragepunkte, abzugpunkte,
					        textantworten, quelltext);
				}
				else
				{
					return new TextFrage(fragetext, fragepunkte, abzugpunkte, textantworten);
				}
		}

		return null;
	}

	/**
	 * Formattiert einen auslesbaren String aus einer Frage.
	 * 
	 * @param frage
	 *            Die einzulesende Frage.
	 * 
	 * @require frage != null
	 * @require dir != null
	 * 
	 * @return Ein formattierter String.
	 */
	public static String formattiereFrage(Frage frage, File dir)
	{
		assert frage != null : "Vorbedingung verletzt : frage != null";
		assert dir != null : "Vorbedingung verletzt : dir != null";

		String schreibFrage = "";

		schreibFrage += FRAGEN_TRENNER + "\n";
		schreibFrage += FRAGE_TRENNER + frage.getFragetyp() + "\n";
		schreibFrage += FRAGE_TRENNER + frage.getFragetext() + "\n";
		schreibFrage += FRAGE_TRENNER + frage.getPunkteFuerAntwort() + "\n";
		schreibFrage += FRAGE_TRENNER + frage.getAbzugsPunkte() + "\n";

		if(frage instanceof OptionalFrage)
		{
			schreibFrage += FRAGE_TRENNER + ((OptionalFrage) frage).hatBild();
			if(((OptionalFrage) frage).hatBild())
			{
				File bild = ((OptionalFrage) frage).getBild();
				File bildKopiert = kopiereBild(bild, dir);

				schreibFrage += BILD_TRENNER + bildKopiert.getName() + "\n";
			}
			else
			{
				schreibFrage += "\n";
			}

			schreibFrage += FRAGE_TRENNER + ((OptionalFrage) frage).hatQuelltext();
			if(((OptionalFrage) frage).hatQuelltext())
			{
				String quelltext = ((OptionalFrage) frage).getQuelltext();
				quelltext = quelltext.replace("\n", "\n" + QUELLTEXT_TRENNER);

				schreibFrage += QUELLTEXT_TRENNER + quelltext + "\n";
			}
			else
			{
				schreibFrage += "\n";
			}
		}
		else
		{
			schreibFrage += FRAGE_TRENNER + "false\n";
			schreibFrage += FRAGE_TRENNER + "false\n";
		}

		if(frage instanceof TextFrage)
		{
			String[] antworttexte = frage.getAntworttexte();
			for(int i = 0; i < antworttexte.length; i++)
			{
				schreibFrage += FRAGE_TRENNER + antworttexte[i] + "\n";
			}
		}
		else
		{
			String[] antworttexte = frage.getAntworttexte();
			boolean[] antwortwerte = (boolean[]) frage.getAntwortenWerte();

			for(int i = 0; i < antworttexte.length; ++i)
			{
				schreibFrage += FRAGE_TRENNER + antworttexte[i] + ANTWORT_TRENNER + antwortwerte[i]
				        + "\n";
			}
		}

		schreibFrage += "\n";

		return schreibFrage;
	}

	/**
	 * Kopiert ein Bild in ein Verzeichnis.
	 * 
	 * @require bild != null
	 * @require dir != null
	 * 
	 * @param bild
	 *            Das zu kopierende Bild.
	 * @param dir
	 *            Der Zielordner.
	 */
	private static File kopiereBild(File bild, File dir)
	{
		assert bild != null : "Vorbedingung verletzt : bild != null";
		assert dir != null : "Vorbedingung verletzt : dir != null";

		Path originalPath = bild.toPath();

		Path kopiertPath = dir.toPath();

		String name = dir.getName() + "-" + originalPath.getFileName();

		try
		{
			Files.copy(originalPath, kopiertPath.resolve(name), StandardCopyOption.COPY_ATTRIBUTES);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return kopiertPath.resolve(name).toFile();
	}
}
