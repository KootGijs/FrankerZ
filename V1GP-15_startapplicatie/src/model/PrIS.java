package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class PrIS {
	private ArrayList<Docent> deDocenten;
	private ArrayList<Student> deStudenten;
	private ArrayList<Les> deLessen;
	
	/**
	 * De constructor maakt een set met standaard-data aan. Deze data
	 * moet nog vervangen worden door gegevens die uit een bestand worden
	 * ingelezen, maar dat is geen onderdeel van deze demo-applicatie!
	 * 
	 * De klasse PrIS (PresentieInformatieSysteem) heeft nu een meervoudige
	 * associatie met de klassen Docent en Student. Uiteraard kan dit nog veel
	 * verder uitgebreid en aangepast worden! 
	 * 
	 * Methode login geeft de rol van de gebruiker die probeert in te loggen,
	 * dat kan 'student', 'docent' of 'undefined' zijn! Die informatie kan gebruikt 
	 * worden om in de Polymer-GUI te bepalen wat het volgende scherm is dat getoond 
	 * moet worden.
	 * 
	 */
	public PrIS() {
		deDocenten = new ArrayList<Docent>();
		deStudenten = new ArrayList<Student>();
		deLessen = new ArrayList<Les>();
		
		Docent d1 = new Docent("Wim", "geheim");
		Docent d2 = new Docent("Hans", "geheim");
		Docent d3 = new Docent("Jan", "geheim");
		
		d1.voegVakToe(new Vak("TCIF-V1AUI-15", "Analyse en User Interfaces"));
		d1.voegVakToe(new Vak("TICT-V1GP-15", "Group Project"));
		d1.voegVakToe(new Vak("TICT-V1OODC-15", "Object Oriented Design & Construction"));
		
		deDocenten.add(d1);
		deDocenten.add(d2);
		deDocenten.add(d3);
	}
	Docent d1 = new Docent("Wim", "geheim");
	Docent d2 = new Docent("Hans", "geheim");
	Docent d3 = new Docent("Jan", "geheim");
	
	Vak aui = new Vak("TCIF-V1AUI-15", "Analyse en User Interfaces");
	Vak gp = new Vak("TICT-V1GP-15", "Group Project");
	Vak oodc = new Vak("TICT-V1OODC-15", "Object Oriented Design & Construction");
	
	Klas v1a = new Klas("SIE_V1A");
	Klas v1b = new Klas("SIE_V1B");
	Klas v1c = new Klas("SIE_V1C");
	Klas v1d = new Klas("SIE_V1D");
	Klas v1e = new Klas("SIE_V1E");
	Klas v1f = new Klas("SIE_V1F");
	
	public String login(String gebruikersnaam, String wachtwoord) {				// Methode om in te loggen en de rol terug te geven
		for (Docent d : deDocenten) {
			if (d.getGebruikersNaam().equals(gebruikersnaam)) {
				if (d.controleerWachtwoord(wachtwoord)) {
					return "docent";
				}
			}
		}
		
		for (Student s : deStudenten) {
			if (s.getGebruikersNaam().equals(gebruikersnaam)) {
				if (s.controleerWachtwoord(wachtwoord)) {
					return "student";
				}
			}
		}
		
		return "undefined";
	}
	
	public Docent getDocent(String gebruikersnaam) {							// Methode om de Docent object te krijgen bij invoer van een gebruikersnaam
		Docent resultaat = null;
		
		for (Docent d : deDocenten) {
			if (d.getGebruikersNaam().equals(gebruikersnaam)) {
				resultaat = d;
				break;
			}
		}
		
		return resultaat;
	}
	
	public Student getStudent(String gebruikersnaam) {							// Methode om de Student object te krijgen bij invoer van een gebruikersnaam
		Student resultaat = null;
		
		for (Student s : deStudenten) {
			if (s.getGebruikersNaam().equals(gebruikersnaam)) {
				resultaat = s;
				break;
			}
		}
		return resultaat;
	}
	
	public Les getLes(String les) {												// Methode om de Les object te krijgen bij invoer van begin datum en tijd
		Les resultaat = null;
		
		for (Les l : deLessen) {
			if (l.getBeginDatumTijd().equals(les)) {
				resultaat = l;
				break;
			}
		}
		return resultaat;
	}
	
	public ArrayList<Student> getStudentenVanKlas(String klasCode) {			// Methode om een ArrayList te ontvangen met alle studenten van een bepaalde klasCode
		ArrayList<Student> resultaat = new ArrayList<Student>();
		
		for (Student s : deStudenten) {
			if (s.getMijnKlas().getKlasCode().equals(klasCode)) {
				resultaat.add(s);
			}
		}
		return resultaat;
	}
	
	public ArrayList<Les> getLessenVanKlas(String klasCode) {					// Methode om een ArrayList van alle lessen van een klasCode te krijgen
		ArrayList<Les> resultaat = new ArrayList<Les>();
		
		for (Les l : deLessen) {
			if (l.getKlas().getKlasCode().equals(klasCode)) {
				resultaat.add(l);
			}
		}
		return resultaat;
	}	
	
	public void inladenKlas(String klas) throws FileNotFoundException {			// Methode om de klassen te laden via de textbestanden of csv bestanden
		String klasFile = klas + ".txt";
		File f = new File(klasFile);
		if (f.exists() && f.isFile()) {
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				String s = sc.nextLine();
				String[] value = s.split(",");
				int leerlingnummer = Integer.parseInt(value[0]);
				String voornaam = value[3];
				String tussenvoegsel = value[2];
				String achternaam = value[1];
				Student s1 = new Student(voornaam+"."+achternaam, "geheim", leerlingnummer, voornaam, tussenvoegsel, achternaam);
				deStudenten.add(s1);
				if (v1a.getKlasCode().equals(klas)) {
					s1.setMijnKlas(v1a);
				} else if (v1b.getKlasCode().equals(klas)){
					s1.setMijnKlas(v1b);
				} else if (v1c.getKlasCode().equals(klas)){
					s1.setMijnKlas(v1c);
				} else if (v1d.getKlasCode().equals(klas)){
					s1.setMijnKlas(v1d);
				} else if (v1e.getKlasCode().equals(klas)){
					s1.setMijnKlas(v1e);
				} else if (v1f.getKlasCode().equals(klas)){
					s1.setMijnKlas(v1f);
				} else {
					System.out.println("Klas kan niet gevonden worden");
				}
//				System.out.println(s1);
			}
			sc.close();
		}
	}
	
	public void inladenRooster(String roosterFile) throws FileNotFoundException, ParseException {		// Methode om de roosters in te laden via een txt of csv bestand
		File f = new File(roosterFile);
		if (f.exists() && f.isFile()) {
			Scanner sc = new Scanner(f);
			while (sc.hasNext()) {
				String s = sc.nextLine();
				String[] value = s.split(",");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddHH:mm");
				String dateInString = value[0]+value[1];
				String dateInString2 = value[0]+value[2];
				Calendar beginDate = Calendar.getInstance();
				beginDate.setTime(formatter.parse(dateInString));
				Calendar eindDate = Calendar.getInstance();
				eindDate.setTime(formatter.parse(dateInString2));
				String datum = value[0];
				String beginTijd = value[1];
				String eindTijd = value[2];
				String vak = value[3];
				String docent = value[4];
				String lokaal = value[5];
				String klas = value[6];
				Les l = new Les(beginDate, eindDate, datum, beginTijd, eindTijd, lokaal, docent);
				if (v1a.getKlasCode().equals(klas)) {
					l.setKlas(v1a);
				} else if (v1b.getKlasCode().equals(klas)){
					l.setKlas(v1b);
				} else if (v1c.getKlasCode().equals(klas)){
					l.setKlas(v1c);
				} else if (v1d.getKlasCode().equals(klas)){
					l.setKlas(v1d);
				} else if (v1e.getKlasCode().equals(klas)){
					l.setKlas(v1e);
				} else if (v1f.getKlasCode().equals(klas)){
					l.setKlas(v1f);
				} else {
					System.out.println("Klas kan niet gevonden worden");
				}
				if (vak.equals(aui.getVakCode())) {
					l.setVak(aui);
				} else if (vak.equals(gp.getVakCode())) {
					l.setVak(gp);
				} else if (vak.equals(oodc.getVakCode())) {
					l.setVak(oodc);
				}
				deLessen.add(l);
			}
			sc.close();
		}
	}
}
