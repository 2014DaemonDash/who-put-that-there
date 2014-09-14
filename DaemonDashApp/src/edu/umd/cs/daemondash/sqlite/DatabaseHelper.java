package edu.umd.cs.daemondash.sqlite;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.umd.cs.daemondash.sqlite.model.ContainerEntry;
import edu.umd.cs.daemondash.sqlite.model.ContainerFood;
import edu.umd.cs.daemondash.sqlite.model.Diner;
import edu.umd.cs.daemondash.sqlite.model.Food;
import edu.umd.cs.daemondash.sqlite.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "containersManager";
	
	// Table names
	private static final String TABLE_USER = "users";
	private static final String TABLE_DINER = "diners";
	private static final String TABLE_FOOD = "foods";
	private static final String TABLE_CONTAINER_FOOD = "container_foods";
	private static final String TABLE_CONTAINER_ENTRY = "containers";
	
	private static final String KEY_ID = "id";
	
	private static final String CREATE_TABLE_USERS =
			"CREATE TABLE " + TABLE_USER + "(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"username TEXT)";
	
	private static final String CREATE_TABLE_DINERS = 
			"CREATE TABLE " + TABLE_DINER + "(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name TEXT)";
	
	private static final String CREATE_TABLE_FOODS =
			"CREATE TABLE " + TABLE_FOOD + "(" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"name TEXT)";
	
	private static final String CREATE_TABLE_CONTAINER_FOODS =
			"CREATE TABLE " + TABLE_CONTAINER_FOOD + "(" +
			"container_id INTEGER,  " +
			"tstamp INTEGER, " +
			"food INTEGER," +
			"PRIMARY KEY (container_id, tstamp, food), " +
			"FOREIGN KEY(food) REFERENCES " + TABLE_FOOD + "(container_id))";
	
	private static final String CREATE_TABLE_CONTAINER_ENTRY =
			"CREATE TABLE " + TABLE_CONTAINER_ENTRY + "(" +
			"container_id INTEGER, " +
			"tstamp INTEGER, " +
			"user INTEGER, " +
			"diner INTEGER, " +
			"lat DOUBLE, " +
			"lon DOUBLE, " +
			"PRIMARY KEY (container_id, tstamp), " +
			"FOREIGN KEY(user) REFERENCES " + TABLE_USER + "(id), " +
			"FOREIGN KEY(diner) REFERENCES " + TABLE_DINER + "(id))";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void populateTestData() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.execSQL("INSERT INTO " + TABLE_FOOD + " (name) VALUES " +
				"('Pizza')," +
				"('Chicken Strips')," +
				"('Wrap')," +
				"('Value Meal')," +
				"('Hamburgers')");
		
		db.execSQL("INSERT INTO " + TABLE_DINER + " (name) VALUES " +
				"('North Diner')," +
				"('South Diner')," +
				"('251 North')");
		
		GregorianCalendar gc = new GregorianCalendar();
		db.execSQL("INSERT INTO " + TABLE_CONTAINER_ENTRY +
				" (container_id, tstamp, diner) VALUES " +
				"(128492648392, " + gc.getTimeInMillis()  + ", 1), " +
				"(128492648392, " + (gc.getTimeInMillis() - 43200000) + ", 1), " +
				"(128492648392, " + (gc.getTimeInMillis() - (43200000 * 5)) + ", 1), " +
				"(128593758472, " + (gc.getTimeInMillis() - (43200000 * 10))  + ", 2), " +
				"(128593758472, " + (gc.getTimeInMillis() - (43200000 * 13.4))  + ", 3), " +
				"(128593758472, " + (gc.getTimeInMillis() - (43200000 * 20.68))  + ", 2) " );
		
		db.execSQL("INSERT INTO " + TABLE_CONTAINER_FOOD +
				" (container_id,tstamp,food) VALUES " +
				"(12849264392," + gc.getTimeInMillis() + ", 1), " +
				"(12849264392," + gc.getTimeInMillis() + ", 2), " +
				"(12849264392," + (gc.getTimeInMillis() - 43200000) + ", 4), " +
				"(12849264392," + (gc.getTimeInMillis() - (43200000 * 5)) + ", 5), " +
				"(12859375472, " + (gc.getTimeInMillis() - (43200000 * 10))  + ", 3), " +
				"(12859375472, " + (gc.getTimeInMillis() - (43200000 * 13.4))  + ", 4), " +
				"(12859375472, " + (gc.getTimeInMillis() - (43200000 * 20.68))  + ", 4) " );
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_DINERS);
		db.execSQL(CREATE_TABLE_FOODS);
		db.execSQL(CREATE_TABLE_CONTAINER_ENTRY);
		db.execSQL(CREATE_TABLE_CONTAINER_FOODS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER_FOOD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER_ENTRY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DINER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
		
		onCreate(db);
	}
	
	public void clearDb() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER_FOOD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER_ENTRY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DINER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
		onCreate(db);
	}
	
	// Create a diner entry
	public long createDiner(Diner diner) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id", diner.getId());
		values.put("name", diner.getName());
				
		return db.insert(TABLE_DINER, null, values);
	}
	
	public Diner getDiner(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_DINER +
				" WHERE name = '" + name + "'";
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		Diner diner = new Diner();
				
		if (c != null) {
			c.moveToFirst();
		} else {
			return null;
		}
		
		diner.setId(c.getLong(c.getColumnIndex("id")));
		diner.setName(c.getString(c.getColumnIndex("name")));
		
		return diner;
	}
	
	public Diner getDiner(Long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_DINER +
				" WHERE id = " + id;
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		Diner diner = new Diner();
				
		if (c != null) {
			c.moveToFirst();
		} else {
			return null;
		}
		
		diner.setId(c.getLong(c.getColumnIndex("id")));
		diner.setName(c.getString(c.getColumnIndex("name")));
		
		return diner;
	}
	
	public long createUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id", user.getId());
		values.put("username", user.getUsername());
				
		return db.insert(TABLE_USER, null, values);
	}
	
	public User getUser(String username) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_USER +
				" WHERE username = '" + username + "'";
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		User user = new User();
				
		if (c != null) {
			c.moveToFirst();
		} else {
			return null;
		}
		
		user.setId(c.getLong(c.getColumnIndex("id")));
		user.setUsername(c.getString(c.getColumnIndex("username")));
		
		return user;
	}
	
	public User getUser(Long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_USER +
				" WHERE id = " + id;
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		User user = new User();
				
		if (c != null) {
			c.moveToFirst();
		} else {
			return null;
		}
		
		user.setId(c.getLong(c.getColumnIndex("id")));
		user.setUsername(c.getString(c.getColumnIndex("username")));
		
		return user;
	}
	
	public long createFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id", food.getId());
		values.put("name", food.getName());
				
		return db.insert(TABLE_FOOD, null, values);
	}
	
	public Food getFood(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_FOOD +
				" WHERE name = '" + name + "'";
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		Food food = new Food();
				
		if (c != null) {
			c.moveToFirst();
		} else {
			return null;
		}
		
		food.setId(c.getLong(c.getColumnIndex("id")));
		food.setName(c.getString(c.getColumnIndex("name")));
		
		return food;
	}
	
	public Food getFood(Long id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_FOOD +
				" WHERE id = " + id;
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		Food food = new Food();
				
		if (c != null) {
			c.moveToFirst();
		} else {
			return null;
		}
		
		food.setId(c.getLong(c.getColumnIndex("id")));
		food.setName(c.getString(c.getColumnIndex("name")));
		
		return food;
	}
	
	private Long createContainerFood(ContainerFood containerFood, Long containerId, SQLiteDatabase db) {
		
		long foodId = createFood(containerFood.getFood());
		
		ContentValues values = new ContentValues();
		values.put("container_id", containerId);
		values.put("tstamp", containerFood.getTstamp());
		values.put("food", foodId);
		
		long containerFoodId = db.insert(TABLE_CONTAINER_FOOD, null, values);
		
		return containerFoodId;
	}
	
	public List<ContainerFood> getContainerFoods() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_CONTAINER_FOOD;
		
		Cursor c = db.rawQuery(selectQuery, null);
				
		List<ContainerFood> entries = new ArrayList<ContainerFood>();
				
		if (c.moveToFirst()) {
			do {
				
				ContainerFood entry = new ContainerFood();
				entry.setFood(getFood(c.getLong(c.getColumnIndex("food"))));
				entry.setTstamp(c.getLong(c.getColumnIndex("tstamp")));
				entries.add(entry);
			} while (c.moveToNext());
		}
		return entries;	
		
	}
	
	public Long createContainerEntry(ContainerEntry entry) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		long dinerId = createDiner(entry.getDiner());
		long userId = createUser(entry.getUser());
		
		ContentValues values = new ContentValues();
		values.put("container_id", entry.getId());
		values.put("tstamp", entry.getTstamp());
		values.put("user", userId);
		values.put("diner", dinerId);
		values.put("lat", entry.getLat());
		values.put("lon", entry.getLon());
		
		long entryId = db.insert(TABLE_CONTAINER_ENTRY, null, values);
		
		for (ContainerFood curr : entry.getFoods()) {
			createContainerFood(curr, entryId, db);
		}
		
		return entryId;
	}
	
	public List<ContainerEntry> getContainerEntries() {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_CONTAINER_ENTRY;
		
		Cursor c = db.rawQuery(selectQuery, null);
				
		List<ContainerEntry> entries = new ArrayList<ContainerEntry>();
				
		if (c.moveToFirst()) {
			do {
				
				ContainerEntry entry = new ContainerEntry();
				entry.setId(c.getLong(c.getColumnIndex("container_id")));
				entry.setTstamp(c.getLong(c.getColumnIndex("tstamp")));
//				entry.setLat(c.getDouble(c.getColumnIndex("lat")));
//				entry.setLon(c.getDouble(c.getColumnIndex("lon")));
//				entry.setUser(getUser(c.getLong(c.getColumnIndex("user"))));
				entry.setDiner(getDiner(c.getLong(c.getColumnIndex("diner"))));
				
				String foodSelectQuery = "SELECT * FROM " + TABLE_CONTAINER_FOOD + 
						" WHERE container_id = " + entry.getId() +
						" AND tstamp = " + entry.getTstamp();
				Cursor c2 = db.rawQuery(foodSelectQuery, null);
				
				List<ContainerFood> foods = new ArrayList<ContainerFood>();
				
				Log.i("FOOD", "Query: " + foodSelectQuery);
				
				if (c2.moveToFirst()) {
					Log.i("FOOD", "TEST name: " + entry.getId());
					do {
						Log.i("FOOD", "Food name: " + entry.getId());
						
						ContainerFood food = new ContainerFood();
						food.setFood(getFood(c2.getLong(c2.getColumnIndex("food"))));
						food.setTstamp(c2.getLong(c2.getColumnIndex("tstamp")));
						foods.add(food);
						

					} while (c2.moveToNext());
				}
				
				entry.setFoods(foods);
				entries.add(entry);
				
			} while (c.moveToNext());
		}
		return entries;		
	}
	
	public List<ContainerEntry> getHistory(Long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_CONTAINER_ENTRY +
				" WHERE container_id = " + id;
		
		Cursor c = db.rawQuery(selectQuery, null);
				
		List<ContainerEntry> entries = new ArrayList<ContainerEntry>();
				
		if (c.moveToFirst()) {
			do {
				
				ContainerEntry entry = new ContainerEntry();
				entry.setId(c.getLong(c.getColumnIndex("container_id")));
				entry.setTstamp(c.getLong(c.getColumnIndex("tstamp")));
//				entry.setLat(c.getDouble(c.getColumnIndex("lat")));
//				entry.setLon(c.getDouble(c.getColumnIndex("lon")));
//				entry.setUser(getUser(c.getLong(c.getColumnIndex("user"))));
				entry.setDiner(getDiner(c.getLong(c.getColumnIndex("diner"))));
				
				entries.add(entry);
				
			} while (c.moveToNext());
		}
		return entries;		
	}
	
	public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
