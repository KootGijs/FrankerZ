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
	
	public String getLesMaandag(String week) {
		if (beginDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			return datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
		}
		return "";
	}
	
	public String toString() {
		return datum+" "+beginTijd+"-"+eindTijd+" \nKlas: "+klas+" \nLes: "+vak+", van: "+docent+" \nLokaal: "+lokaal+"\n";
	}
}
