package model;

import java.util.Calendar;

public class Les {
	private Calendar beginDate;
	private Calendar eindDate;
	private String datum;
	private String beginTijd;
	private String eindTijd;
	private String lokaal;
	private String docent;
	private Klas klas;
	private Vak vak;
	
	public Les(Calendar bd, Calendar ed, String datum, String bt, String et, String lo, String docent) {
		beginDate = bd;
		eindDate = ed;
		this.datum = datum;
		beginTijd = bt;
		eindTijd = et;
		lokaal = lo;
		this.docent = docent;
	}
	
	public void setVak(Vak vak) {
		this.vak = vak;
	}
	
	public void setKlas(Klas dezeKlas) {
		this.klas = dezeKlas;
	}
	
	public Klas getKlas() {
		return klas;
	}
	
	public String getLesTijd(){
		return datum+" "+beginTijd+"-"+eindTijd;
	}
	public String getLesLokaal(){
		return "Les: "+vak+", van: "+docent+" \nin lokaal: "+lokaal;
	}
	
	public String getLesDag(String dag) {
		String s = "";
		if (dag == "maandag"){
			if (beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				s = datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
			}
		}
		if (dag == "dinsdag") {
			if (beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
				s = datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
			}
		}
		if (dag == "woensdag") {
			if (beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				s = datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
			}
		}
		if (dag == "donderdag") {
			if (beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
				s = datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
			}
		}
		if (dag == "vrijdag") {
			if (beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				s = datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
			}
		}
		return s;
	}
	
	public Boolean ifLesDag(String dag, int week) {
		Boolean b = false;
		if (beginDate.get(Calendar.WEEK_OF_YEAR) == week) {
			if (dag == "maandag" && beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				b = true;
			}
			if (dag == "dinsdag" && beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ) {
				b = true;
			}
			if (dag == "woensdag" && beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
				b = true;
			}
			if (dag == "donderdag" && beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
				b = true;
			}
			if (dag == "vrijdag" && beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				b = true;
			}
		}
		return b;
	}
	
	public String toString() {
		return datum+" "+beginTijd+"-"+eindTijd+" \nLes: "+vak+", van: "+docent+" \n in lokaal: "+lokaal+"\n";
	}
}
