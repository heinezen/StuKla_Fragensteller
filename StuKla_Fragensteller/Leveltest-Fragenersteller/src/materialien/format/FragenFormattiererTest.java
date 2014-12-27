package materialien.format;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import materialien.Frage;
import materialien.choice.ChoiceAntwort;
import materialien.choice.multiChoice.KlickFrage;
import materialien.choice.multiChoice.OptionalKlickFrage;
import materialien.choice.singleChoice.AuswahlFrage;
import materialien.choice.singleChoice.OptionalAuswahlFrage;
import materialien.lueckentext.OptionalTextFrage;
import materialien.lueckentext.TextFrage;

import org.junit.Test;

public class FragenFormattiererTest
{
	private Frage erstelleFrage(String fragetyp, boolean bild, boolean quelltext)
	{
		ChoiceAntwort[] antworten = new ChoiceAntwort[3];
		antworten[0] = new ChoiceAntwort("Test0", false);
		antworten[1] = new ChoiceAntwort("Test1", true);
		antworten[2] = new ChoiceAntwort("Test2", false);

		String[] textantworten = new String[3];
		textantworten[0] = "Test0";
		textantworten[1] = "Test1";
		textantworten[2] = "Test2";

		switch(fragetyp)
		{
			case "Klick":
				if(bild && quelltext)
				{
					return new OptionalKlickFrage("Test?", 3, 2, antworten, "Quelltext", new File(
					        "./AFD.jpg"));
				}
				else if(bild)
				{
					return new OptionalKlickFrage("Test?", 3, 2, antworten, new File("./AFD.jpg"));
				}
				else if(quelltext)
				{
					return new OptionalKlickFrage("Test?", 3, 2, antworten, "Quelltext");
				}
				else
				{
					return new KlickFrage("Test?", 3, 2, antworten);
				}
			case "Auswahl":
				if(bild && quelltext)
				{
					return new OptionalAuswahlFrage("Test?", 3, 2, antworten, "Quelltext",
					        new File("./AFD.jpg"));
				}
				else if(bild)
				{
					return new OptionalAuswahlFrage("Test?", 3, 2, antworten, new File("./AFD.jpg"));
				}
				else if(quelltext)
				{
					return new OptionalAuswahlFrage("Test?", 3, 2, antworten, "Quelltext");
				}
				else
				{
					return new AuswahlFrage("Test?", 3, 2, antworten);
				}
			case "Text":
				if(bild && quelltext)
				{
					return new OptionalTextFrage("Test?", 3, 2, textantworten, "Quelltext",
					        new File("./AFD.jpg"));
				}
				else if(bild)
				{
					return new OptionalTextFrage("Test?", 3, 2, textantworten,
					        new File("./AFD.jpg"));
				}
				else if(quelltext)
				{
					return new OptionalTextFrage("Test?", 3, 2, textantworten, "Quelltext");
				}
				else
				{
					return new TextFrage("Test?", 3, 2, textantworten);
				}
		}
		return null;
	}

	@Test
	public void testeFormattierung()
	{
		try
		{
			Frage frage = erstelleFrage("Klick", false, false);
			String test = FragenFormattierer.formattiereFrage(frage,
			        Files.createTempDirectory("test").toFile());
			String erwartet = "###FRA###" + "\n"
								+ "###FRG###Klick" + "\n"
								+ "###FRG###Test?" + "\n"
								+ "###FRG###3" + "\n"
								+ "###FRG###2" + "\n"
								+ "###FRG###false" + "\n"
								+ "###FRG###false" + "\n"
								+ "###FRG###Test0###ANT###false" + "\n"
								+ "###FRG###Test1###ANT###true" + "\n"
								+ "###FRG###Test2###ANT###false" + "\n\n";

			assertEquals(erwartet, test);
		}
		catch(IOException e)
		{

		}
	}

	@Test
	public void testeParsen()
	{

	}

	@Test
	public void testeKompatibilitaet()
	{
		try
		{
			Frage frage = erstelleFrage("Klick", false, false);
			String test = FragenFormattierer.formattiereFrage(frage,
			        Files.createTempDirectory("test").toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Klick", true, false);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Klick", false, true);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Klick", true, true);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Auswahl", false, false);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Auswahl", true, false);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Auswahl", false, true);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Auswahl", true, true);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Text", false, false);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Text", true, false);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Text", false, true);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);

			frage = erstelleFrage("Text", true, true);
			test = FragenFormattierer.formattiereFrage(frage, Files.createTempDirectory("test")
			        .toFile());
			FragenFormattierer.parseFrage(test);
		}
		catch(IOException e)
		{

		}
	}
}
