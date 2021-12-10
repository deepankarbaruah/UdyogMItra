package in.nic.assam.udyogmitra;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static final String DB_NAME = "master_database.db";
    public static final String PASS_PHRASE="abc123@#";
    private net.sqlcipher.database.SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase(PASS_PHRASE);
            try {
                copyDataBase();
                encryptDataBase(PASS_PHRASE);
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {

        String myPath = DB_PATH + DB_NAME;
        //checkDB = SQLiteDatabase.openDatabase(myPath,PASS_PHRASE, null, SQLiteDatabase.OPEN_READONLY);
        File dbFile = this.myContext.getDatabasePath(myPath);
        ///checkDB=dbFile.exists();
        if (!dbFile.exists()) {
            // Database does not exist so copy it from assets here
            return false;
        } else {
            return true;
        }

        //   return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void encryptDataBase(String passphrase) throws IOException {

        File originalFile = this.myContext.getDatabasePath(DB_NAME);

        File newFile = File.createTempFile("sqlcipherutils", "tmp", this.myContext.getCacheDir());

        net.sqlcipher.database.SQLiteDatabase existing_db = net.sqlcipher.database.SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, "", null, SQLiteDatabase.OPEN_READWRITE);

        existing_db.rawExecSQL("ATTACH DATABASE '" + newFile.getPath() + "' AS encrypted KEY '" + passphrase + "';");
        existing_db.rawExecSQL("SELECT sqlcipher_export('encrypted');");
        existing_db.rawExecSQL("DETACH DATABASE encrypted;");

        existing_db.close();

        originalFile.delete();

        newFile.renameTo(originalFile);

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath,PASS_PHRASE, null, SQLiteDatabase.OPEN_READONLY);
        close();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                //Toast.makeText(getApplicationContext(), "Some exception occures ", Toast.LENGTH_SHORT).show();
                //  System.out.println("Some exception occures " + e);
            }
    }

    public String getDistrictName(String userName, String password){

        // Select All Query
        String selectQuery = "SELECT * FROM gm_login WHERE username=? AND password=?";

        SQLiteDatabase db = this.getReadableDatabase(PASS_PHRASE);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userName,password});

        String district_name = "";

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                district_name = cursor.getString(3);
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        return district_name;

    }

    public GeneralManager getGMDetails(String userName, String password){

        // Select All Query
        String selectQuery = "SELECT * FROM gm_login WHERE username=? AND password=?";

        SQLiteDatabase db = this.getReadableDatabase(PASS_PHRASE);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userName,password});

        GeneralManager gm = new GeneralManager();

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                gm.setName(cursor.getString(1));
                gm.setId(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();
        return gm;

    }

    public Boolean getAccess(String userName, String password){

//        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM gm_login WHERE username=? AND password=?",new String[]{userName,password});
        // Select All Query
        String selectQuery = "SELECT * FROM gm_login WHERE username=? AND password=?";

        SQLiteDatabase db = this.getReadableDatabase(PASS_PHRASE);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{userName,password});

        //Loop through now
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.close();
                return true;
            }
        }
        // closing connection
        cursor.close();
        db.close();
        return false;
    }

    public List<District> allDistrict(){
        List<District> labels = new ArrayList<District>();
        District selectDist = new District();

        selectDist.setDistrictName("Select District");
        selectDist.setDistrictCode("0");
        labels.add(0, selectDist);

        // Select All Query
        String selectQuery = "select * from assam_district_master";

        SQLiteDatabase db = this.getReadableDatabase(PASS_PHRASE);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                District district = new District();
                district.setDistrictCode(cursor.getString(1));
                district.setDistrictName(cursor.getString(2));
                labels.add(district);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public ArrayList<GeneralManager> getGeneralManager(String districtName){

        ArrayList<GeneralManager> generalManagerArrayList = new ArrayList<GeneralManager>();

        // Select All Query
        String selectQuery = "SELECT * FROM gm_details WHERE district_name=?";

        SQLiteDatabase db = this.getReadableDatabase(PASS_PHRASE);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{districtName});

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                GeneralManager generalManager = new GeneralManager();
                generalManager.setName(cursor.getString(2));
                generalManager.setDesignation(cursor.getString(3));
                generalManager.setPhoneNumber(cursor.getString(4));
                generalManagerArrayList.add(generalManager);
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning generalManagerArrayList
        return generalManagerArrayList;
    }

}
