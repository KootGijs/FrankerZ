package controller;

import java.util.ArrayList;

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
	private void mijnMedestudenten(Conversation conversation) {
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
						.add("naam", s.getVoornaam()));
			}
		}
		
		conversation.sendJSONMessage(jab.build().toString());					// terug naar de Polymer-GUI!
	}

	private void mijnRooster(Conversation conversation) {
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");

		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);	// lessen opzoeken
		
		JsonArrayBuilder jab = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...
		
		for (Les l : lessen) {													// met daarin voor elke les een JSON-object... 
			if (l.ifLesDag("maandag",10)) {
				jab.add(Json.createObjectBuilder()
					.add("maandag", l.toString()));
			}
			if (l.ifLesDag("dinsdag",10)) {
				jab.add(Json.createObjectBuilder()
					.add("dinsdag", l.toString()));
			}
			if (l.ifLesDag("woensdag",10)) {
				jab.add(Json.createObjectBuilder()
					.add("woensdag", l.toString()));
			}
			if (l.ifLesDag("donderdag",10)) {
				jab.add(Json.createObjectBuilder()
					.add("donderdag", l.toString()));
			}
			if (l.ifLesDag("vrijdag",10)) {
				jab.add(Json.createObjectBuilder()
					.add("vrijdag", l.toString()));
			}
		}
		
		conversation.sendJSONMessage(jab.build().toString());					// terug naar de Polymer-GUI!
	}
	
	private void mijnLessen(Conversation conversation) {
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");

		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);
		
		JsonArrayBuilder jab = Json.createArrayBuilder();						// Uiteindelijk gaat er een array...
		
		for (Les l : lessen) {													// met daarin voor elke les een JSON-object... 
			if (l.ifHuidigeWeek(10)) {
				jab.add(Json.createObjectBuilder()
					.add("lessen", l.toString()));
			}
		}
		
		conversation.sendJSONMessage(jab.build().toString());					// terug naar de Polymer-GUI
	}
	
	public void mijnAfmeldingVersturen(Conversation conversation) {
		JsonObject jsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String gebruikersnaam = jsonObjectIn.getString("username");
//		String lesNaam = jsonObjectIn.getString("afmeldLes");
		int lesNummer = jsonObjectIn.getInt("lesIndex");

		Student student = informatieSysteem.getStudent(gebruikersnaam);			// Student-object opzoeken
		String klasCode = student.getMijnKlas().getKlasCode();					// klascode van de student opzoeken
		ArrayList<Les> lessen = informatieSysteem.getLessenVanKlas(klasCode);

//		Les les = informatieSysteem.getLes(lesNaam);
		
		for (int i=0; lessen.size() < i; i++) {													// met daarin voor elke les een JSON-object... 
			if (lesNummer == i) {
				lessen.get(i).setAfmelding(student);
			}
		}
		
//		les.setAfmelding(student);
	}
	
}
