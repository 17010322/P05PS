package sg.edu.rp.webservices.p05ps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Task.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//------------------------------------CREATE TABLE-----------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"+COLUMN_DESC + " TEXT ) ";
        db.execSQL(createNoteTableSql);
    }
//------------------------------------CREATE TABLE END-------------------------------------------------

    //--------------------------------UPGRADE TABLE-----------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN  address TEXT");
        //onCreate(db);
    }
    //--------------------------------UPGRADE TABLE END----------------------------------------------------

//------------------------------------INSERT--------------------------------------------------------------
    public long insertId(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        return result;
    }

    public long insertName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        return result;
    }

    public long insertDesc(String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESC, desc);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        return result;
    }
    //-------------------------------INSERT END--------------------------------------------------------------

    //--------------------------------RETRIEVE START------------------------------------------------------
    public ArrayList<Task> getAllTask(String taskID) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME,COLUMN_DESC};
        String condition = COLUMN_ID + " = ";
        String[] args = { taskID };
        Cursor cursor = db.query(TABLE_TASK, columns, condition, args,
                null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                Task task = new Task(id, name, desc);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
//---------------------------------------RETRIEVE END-------------------------------------------------------

    //-----------------------------------UPDATE-------------------------------------------------------------
    public int updatePhone(Task data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESC, data.getDesc());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int updateName(Task data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }
    //------------------------------------UPDATE END---------------------------------------------------------

    //------------------------------------DELETE-------------------------------------------------------------

    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }
}
