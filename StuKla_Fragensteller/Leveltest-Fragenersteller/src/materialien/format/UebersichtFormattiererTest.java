package materialien.format;

import static org.junit.Assert.*;

import org.junit.Test;

public class UebersichtFormattiererTest
{
	private String[] erstelleUebersicht()
	{
		String[] ueberblick = new String[3];
		
		ueberblick[0] = "OPT";
		ueberblick[1] = "24.11.2014";
		ueberblick[2] = "60";
		
		return ueberblick;
	}
	
	@Test
	public void testeParsen()
	{
		String test = "###STUKLA###\n" + "###UEB###OPT\n" + "###UEB###24.11.2014\n"
						+ "###UEB###60\n\n";
		
		String[] testUeberblick = UebersichtFormattierer.parseUeberblick(test);
		
		assertEquals("OPT", testUeberblick[0]);
		assertEquals("24.11.2014", testUeberblick[1]);
		assertEquals("60", testUeberblick[2]);
	}
	
	@Test
	public void testeFormattierung()
	{
		String[] testUeberblick = erstelleUebersicht();
		
		String test = "###STUKLA###\n" + "###UEB###OPT\n" + "###UEB###24.11.2014\n"
				+ "###UEB###60\n\n";
		
		assertEquals(test, UebersichtFormattierer.formattiereUeberblick(testUeberblick));
	}
	
	@Test
	public void testeKompatibilitaet()
	{
		String[] ueberblick = erstelleUebersicht();
		String test = UebersichtFormattierer.formattiereUeberblick(ueberblick);
		UebersichtFormattierer.parseUeberblick(test);
	}
}
