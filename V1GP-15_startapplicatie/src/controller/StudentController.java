package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import model.Les;
import model.PrIS;
import model.Student;
import server.Conversation;
import server.Handler;

public class StudentController implements Handler {
	private PrIS informatieSysteem;
	
	/**
	 * De StudentController klasse moet alle student-gerelateerde aanvragen
	 * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
	 * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
	 * een nieuwe methode schrijven.
	 * 
	 * @param infoSys - het toegangspunt tot het domeinmodel
	 */
	public StudentController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
		if (conversation.getRequestedURI().startsWith("/student/mijnmedestudenten")) {
			mijnMedestudenten(conversation);
		}
		if (conversation.getRequestedURI().startsWith("/student/rooster")) {
			mijnRooster(conversation);
		}
		if (conversation.getRequestedURI().startsWith("/student/mijnafmelding")) {
			mijnLessen(conversation);
		}
		if (conversation.getRequestedURI().startsWith("/student/mijnafmeldingversturen")) {
			mijnAfmeldingVersturen(conversation);
		}
	}

	/**
	 * Deze methode haalt eerst de opgestuurde JSON-data op. Daarna worden
	 * de benodigde gegevens uit het domeinmodel gehaald. Deze gegevens worden
	 * dan weer omgezet naar JSON en teruggestuurd naar de Polymer-GUI!
	 * 
	 * @param conversation - alle informatie over het request
	 */
	private void mijnMedestudenten(Conversation conversation) {					// Zoekt de klasgenoten van de user
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");
		
		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Student> studentenVanKlas = informatieSysteem.getStudentenVanKlas(klasCode);	// medestudenten opzoeken
		
		JsonArrayBuilder jab = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...
		
		for (Student s : studentenVanKlas) {									// met daarin voor elke medestudent een JSON-object... 
			if (s.getGebruikersNaam().equals(gebruikersnaam)) 					// behalve de student zelf...
				continue;
			else {
				jab.add(Json.createObjectBuilder()
						.add("naam", s.getNaam()));
			}
		}
		
		conversation.sendJSONMessage(jab.build().toString());					// terug naar de Polymer-GUI!
	}

	private void mijnRooster(Conversation conversation) {						// laad het rooster van de klas van de user
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");

		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);	// lessen opzoeken
		
		JsonArrayBuilder jab = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...
		
		for (Les l : lessen) {													// met daarin voor elke les een JSON-object... 
			if (l.ifLesDag("maandag",10) && !l.isAfgemeld(student)) {			// De Methode zoekt nu alleen de lessen van week 10. In de echte code zouden we de huidige
				jab.add(Json.createObjectBuilder()								// week programmeren maar omdat het rooster dat gekregen is maar 3 weken duurt
					.add("maandag", l.toString()));								// is er gekozen om een week te kiezen waar zich nog lessen in bevinden
			}
			if (l.ifLesDag("dinsdag",10) && !l.isAfgemeld(student)) {
				jab.add(Json.createObjectBuilder()
					.add("dinsdag", l.toString()));
			}
			if (l.ifLesDag("woensdag",10) && !l.isAfgemeld(student)) {
				jab.add(Json.createObjectBuilder()
					.add("woensdag", l.toString()));
			}
			if (l.ifLesDag("donderdag",10) && !l.isAfgemeld(student)) {
				jab.add(Json.createObjectBuilder()
					.add("donderdag", l.toString()));
			}
			if (l.ifLesDag("vrijdag",10) && !l.isAfgemeld(student)) {
				jab.add(Json.createObjectBuilder()
					.add("vrijdag", l.toString()));
			}
		}
		
		conversation.sendJSONMessage(jab.build().toString());					// terug naar de Polymer-GUI!
	}
	
	private void mijnLessen(Conversation conversation) {						// Alle lessen van de klas van de user sturen
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");

		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);
		
		JsonArrayBuilder jab = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...
		
		for (Les l : lessen) {													// met daarin voor elke les een JSON-object... 
			if (l.ifHuidigeWeek(10)) {
				jab.add(Json.createObjectBuilder()
					.add("lessen", lessen.indexOf(l)+" - "+l.toString()));
			}
		}
		
		conversation.sendJSONMessage(jab.build().toString());					// terug naar de Polymer-GUI
	}
	
	public void mijnAfmeldingVersturen(Conversation conversation) {				// het ontvangen van een afmelding
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");
		System.out.println(gebruikersnaam+" <-- probeerde af te melden");   	// in de java console wordt getoond wie er wilt afmelden
		
		String lesNummerString = jsonObjectIn.getString("lesIndex");
		int lesNummer = Integer.parseInt(lesNummerString);

		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);
		
		for (Les l : lessen) {
			if (lesNummer == lessen.indexOf(l) && !l.ifNogNietGeweest() && !l.isAfgemeld(student)) {
				l.setAfmelding(student);
			}
		}	
	}
}
