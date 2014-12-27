package fragenersteller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;

import materialien.Frage;
import materialien.format.FragenFormattierer;
import materialien.format.UebersichtFormattierer;

public class DateiSchreiber
{
	private final File ORDNER;

	public DateiSchreiber(File ordner)
	{
		try
		{
			Files.createDirectory(ordner.toPath());
		}
		catch(IOException e)
		{

		}

		ORDNER = ordner;
	}

	public void schreibeInDatei(List<Frage> fragensatz, String[] uebersicht)
	{
		try
		{
			String dir = uebersicht[1] + "-" + uebersicht[2].replace(".", "");
			String dateiname = dir + ".txt";

			File frageDir = new File(ORDNER + "/" + dir);

			try
			{
				Files.createDirectory(frageDir.toPath());
			}
			catch(IOException e)
			{

			}

			FileWriter fw = new FileWriter(frageDir + "/" + dateiname);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			String ueberblick = UebersichtFormattierer.formattiereUeberblick(uebersicht);
			ueberblick = ueberblick.replace("\n", "\r\n");

			out.print(ueberblick);
			out.println(zerteileFragensatz(fragensatz, frageDir));

			out.close();
		}
		catch(Exception e)
		{

		}
	}

	private String zerteileFragensatz(List<Frage> fragensatz, File dir)
	{
		String fragensatzZerteilt = "";

		for(Frage frage : fragensatz)
		{
			fragensatzZerteilt += FragenFormattierer.formattiereFrage(frage, dir);
		}

		fragensatzZerteilt = fragensatzZerteilt.replace("\n", "\r\n");

		return fragensatzZerteilt;
	}
}
