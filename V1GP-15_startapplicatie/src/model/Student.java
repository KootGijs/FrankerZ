package model;

public class Student {
	private String gebruikersNaam;
	private String wachtwoord;
	private int leerlingnummer;
	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private Klas mijnKlas;
	
	public Student(String gbNm, String ww, int llnr, String vn, String tv, String an) {
		gebruikersNaam = gbNm;
		wachtwoord = ww;
		leerlingnummer = llnr;
		voornaam = vn;
		tussenvoegsel = tv;
		achternaam = an;
	}
	
	public String getGebruikersNaam() {
		return gebruikersNaam;
	}
	public String getVoornaam(){
		return voornaam;
	}
	public String getAchternaam(){
		return tussenvoegsel+" "+achternaam;
	}
	public String getNaam(){
 		return voornaam + " " + tussenvoegsel + " " + achternaam ;
 	}
	public String getLeerlingnummer() {
		return ""+leerlingnummer;
	}
	
	public boolean controleerWachtwoord(String ww) {
		return ww.equals(wachtwoord);
	}
	
	public void setWachtwoord(String ww) {
		wachtwoord = ww;
	}
	
	public void setMijnKlas(Klas k) {
		mijnKlas = k;
	}
	
	public Klas getMijnKlas() {
		return mijnKlas;
	}
	
	public String toString() {
		return "Leerlingnummer: "+leerlingnummer+ " \nVoornaam: \t" +voornaam+ " \nAchternaam: \t" +tussenvoegsel+" "+achternaam+ "\n"+mijnKlas;
	}
}
