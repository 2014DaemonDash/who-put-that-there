package edu.umd.cs.daemondash;

import edu.umd.cs.daemondash.sqlite.DatabaseHelper;

public class Storage {
	public static Long barcode;
	public static DatabaseHelper db;
	
	public static Long getBarcode() {
		return barcode;
	}
	public static void setBarcode(Long barcode) {
		Storage.barcode = barcode;
	}
	public static DatabaseHelper getDb() {
		return db;
	}
	public static void setDb(DatabaseHelper db) {
		Storage.db = db;
	}
	
	
}
