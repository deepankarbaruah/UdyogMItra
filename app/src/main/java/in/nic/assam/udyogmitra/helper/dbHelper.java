package in.nic.assam.udyogmitra.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import in.nic.assam.udyogmitra.model.Visitor;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "visitor_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "visitor_details_table";
    private static final String COL_0 = "visitor_name";
    private static final String COL_1 = "org_name";
    private static final String COL_2 = "visitor_number";
    private static final String COL_3 = "visitor_telephone";
    private static final String COL_4 = "district_name";
    private static final String COL_5 = "purpose";
    private static final String COL_6 = "gm_remarks";
    private static final String COL_7 = "date_of_sub";
    private static final String COL_8 = "remarks_status";


    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table_query = "CREATE TABLE " + TABLE_NAME
                + "("
                + COL_0 + " TEXT,"
                + COL_1 + " TEXT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " TEXT,"
                + COL_5 + " TEXT,"
                + COL_6 + " TEXT,"
                + COL_7 + " TEXT,"
                + COL_8 + " INTEGER DEFAULT 0"
                + ")";

        db.execSQL(create_table_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addVisitor(Visitor visitor){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_0, visitor.getVisitorName());
        values.put(COL_1, visitor.getOrganisationName());
        values.put(COL_2, visitor.getVisitorNumber());
        values.put(COL_3, visitor.getTelephoneNumber());
        values.put(COL_4, visitor.getDistrict_name());
        values.put(COL_5, visitor.getPurpose());
        values.put(COL_6, visitor.getRemarks());
        values.put(COL_7, new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        values.put(COL_8, visitor.getRemarks_status());


        db.insert(TABLE_NAME, null, values);
        Log.d("mydb", "Successfully inserted");
        db.close();


    }

    public void deleteVisitorByName(String visitor_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_0 +"=?", new String[]{String.valueOf(visitor_name)});
        db.close();
    }

    public void saveRemarks(String district,String visitorName, String gmRemarks){
        Log.d("mydb","dist : " + district);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,gmRemarks);
        contentValues.put(COL_8, 1);
        db.update(TABLE_NAME,contentValues, "visitor_name=? AND district_name=?", new String[]{visitorName,district});
        db.close();

    }

    public ArrayList<Visitor> getVisitorList(String districtName){

        ArrayList<Visitor> visitorArrayList = new ArrayList<Visitor>();

        // Select All Query
        String selectQuery = "SELECT * FROM visitor_details_table WHERE district_name=?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{districtName});

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Visitor visitor = new Visitor();
                visitor.setVisitorName(cursor.getString(0));
                visitor.setOrganisationName(cursor.getString(1));
                visitor.setVisitorNumber(cursor.getString(2));
                visitor.setDistrict_name(cursor.getString(4));
                visitor.setPurpose(cursor.getString(5));
                visitor.setRemarks(cursor.getString(6));
                visitor.setDate_of_sub(cursor.getString(7));
                visitorArrayList.add(visitor);
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning visitorArrayList
        return visitorArrayList;
    }

    public ArrayList<Visitor> getSubVisitorList(String districtName){

        ArrayList<Visitor> visitorArrayList = new ArrayList<Visitor>();

        // Select All Query
        String selectQuery = "SELECT * FROM visitor_details_table WHERE district_name=? AND remarks_status= 1 ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{districtName});

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Visitor visitor = new Visitor();
                visitor.setVisitorName(cursor.getString(0));
                visitor.setOrganisationName(cursor.getString(1));
                visitor.setVisitorNumber(cursor.getString(2));
                visitor.setDistrict_name(cursor.getString(4));
                visitor.setPurpose(cursor.getString(5));
                visitor.setRemarks(cursor.getString(6));
                visitor.setDate_of_sub(cursor.getString(7));
                visitorArrayList.add(visitor);
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning visitorArrayList
        return visitorArrayList;
    }

    public ArrayList<Visitor> getPenVisitorList(String districtName){

        ArrayList<Visitor> visitorArrayList = new ArrayList<Visitor>();

        // Select All Query
        String selectQuery = "SELECT * FROM visitor_details_table WHERE district_name=? AND remarks_status= 0 ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{districtName});

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Visitor visitor = new Visitor();
                visitor.setVisitorName(cursor.getString(0));
                visitor.setOrganisationName(cursor.getString(1));
                visitor.setVisitorNumber(cursor.getString(2));
                visitor.setDistrict_name(cursor.getString(4));
                visitor.setPurpose(cursor.getString(5));
                visitor.setRemarks(cursor.getString(6));
                visitor.setDate_of_sub(cursor.getString(7));
                visitorArrayList.add(visitor);
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning visitorArrayList
        return visitorArrayList;
    }
}
