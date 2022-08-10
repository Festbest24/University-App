package com.example.newproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.newproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "FYP.db";
    private static final String TABLE_NAME = "user";
    private static final String COL_PK = "id";
    private static final String COL_NAME = "name";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_PHONE = "phone";
    private static final String COL_QUALIFICATION = "qualification";
    private static final int DB_VERSION = 1;
    private static final String SCHEMA = "Create table " + TABLE_NAME + "" +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, " +
            "email TEXT NOT NULL, password TEXT NOT NULL," +
            "phone TEXT NOT NULL, qualification TEXT NOT NULL )";
    private static DbHelper instance;

    public DbHelper(Context _context) {
        super(_context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DbHelper getInstance(Context context) //use in threads when we execute things in parallel
    // yeh us activity ky thread k sth merge kr ly ga apny apko
    {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCHEMA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DB_VERSION > oldVersion) {
            db.execSQL(" ALTER TABLE " + TABLE_NAME);
        }
    }

    //----------------- CRUD FUNCTION --------------------------
    public void insertrecord(Context context, User user) {
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase(); //Its a Singleton Pattern
        ContentValues values = new ContentValues();
        db.beginTransaction();
        try {
            values.put(COL_NAME, user.getName());
            values.put(COL_EMAIL, user.getEmail());
            values.put(COL_PASSWORD, user.getPassword());
            values.put(COL_PHONE, user.getPhone());
            values.put(COL_QUALIFICATION, user.getQualification());
            result = db.insert(TABLE_NAME, null, values);
            if (result > 0) {
                db.setTransactionSuccessful();
                Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error..!!!" + "\nRecord not Save", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<User> CheckLogin(Context context, String email, String password) {
        String _email, _password;
        String query = " SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND password = ?";
        List<User> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        try {
            if (cursor.moveToFirst()) {
                do {
                    _email = cursor.getString(2);
                    _password = cursor.getString(3);
                    if (_email.equals(email) && _password.equals(password)) {
                        studentList.add(new User(cursor.getString(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3),
                                cursor.getString(4), cursor.getString(5)));
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return studentList;
    }


    public boolean updaterecord(Context context, String id, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long result = -1;
        db.beginTransaction();
        String _id;
        try {
            values.put(COL_NAME, user.getName());
            values.put(COL_EMAIL, user.getEmail());
            values.put(COL_PHONE, user.getPhone());
            values.put(COL_QUALIFICATION, user.getQualification());

            result = db.update(TABLE_NAME, values, "id = ?", new String[]{id});
            if (result > 0) //It add record with respect to Primary key and PK start from 1 so it can be greater than 0
            {
                Toast.makeText(context, "Record Updated..!!!!", Toast.LENGTH_SHORT).show();
                db.setTransactionSuccessful();
            } else {
                Toast.makeText(context, "Record Not Update..!!!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return true;
    }


    public List<User> DisplayRecord() {
        List<User> studentList = new ArrayList<>();
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    studentList.add(new User(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    ));
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return studentList;
    }

/*
    public List<Student> DisplayRecord() {
        List<Student> studentList = new ArrayList<>();
        String query = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    studentList.add(new Student(cursor.getString(0), cursor.getString(1),
                            cursor.getString(2), cursor.getString(3)));
                }
                while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return studentList;
    }
*/

/*
    public List<Student> showsingledata(Context context,String id)
    {
        String _id;
        String query=" SELECT * FROM "+TABLE_NAME + " WHERE id = ? ";
        List<Student> studentList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,new String[]{id});
        try
        {
                if(cursor.moveToFirst())
                {
                    do {
                        _id=cursor.getString(0);
                        if(_id.equals(id)) {
                            studentList.add(new Student(cursor.getString(0),cursor.getString(1),
                                    cursor.getString(2),cursor.getString(3)));
                            Toast.makeText(context, "Record Found", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else
                        {
                            Toast.makeText(context, "Record Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    while (cursor.moveToNext());
                }
            }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            cursor.close();
            db.close();
        }
        return studentList;
    }

    public void deletedata(Context context,String id)
    {
        String _id;
        SQLiteDatabase db=this.getReadableDatabase();
        boolean isDeleted=false;
        String query=" SELECT * FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        try
        {
            if(cursor.moveToFirst())
            {
                do {
                    _id=cursor.getString(0);
                    if(_id.equals(id))
                    {
                        db.delete(TABLE_NAME," id = ? ",new String[]{id});
                        isDeleted=true;
                        break;
                    }
                }
                while (cursor.moveToNext());
            }
            if(isDeleted)
            {
                Toast.makeText(context, "Record Deleted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Record not Found", Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            cursor.close();
            db.close();
        }

    }


*/
}
