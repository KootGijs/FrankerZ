package controller;

import java.io.*;
import java.text.ParseException;

import model.PrIS;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, ParseException {		
		PrIS infoSysteem = new PrIS();
		
		infoSysteem.inladenKlas("SIE_V1A");
		infoSysteem.inladenKlas("SIE_V1B");
		infoSysteem.inladenKlas("SIE_V1C");
		infoSysteem.inladenKlas("SIE_V1D");
		infoSysteem.inladenKlas("SIE_V1E");
		infoSysteem.inladenKlas("SIE_V1F");
		infoSysteem.inladenRooster("rooster_C.txt");
	}
}
