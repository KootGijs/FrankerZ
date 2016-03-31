package model;

import java.util.ArrayList;

public class Presentie {
	private ArrayList<Student> afgemeldeStudenten;
	private Les les;
	
	private Presentie(Les les) {
		this.les = les;
		afgemeldeStudenten = new ArrayList<Student>();
	}
	
	public void setAfmelding(Student student) {
		if (!afgemeldeStudenten.contains(student)) {
			afgemeldeStudenten.add(student);
		}
	}
}
