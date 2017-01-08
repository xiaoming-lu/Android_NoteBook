package com.example.xiaoming.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by xiaomi on 10/16/2016.
 */
public class NotebookDbAdapter {

    private static final String DATABASE_NAME="notebook.db";
    private static final int DATABASE_VERSION=1;
    public static final String NOTE_TABLE="note";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_TITLE ="title";
    public static final String COLUMN_MESSAGE="message";
    public static final String COLUMN_CATEGORY="category";
    public static final String COLUMN_DATE="date";

    private String[] allColumns = {COLUMN_ID,COLUMN_TITLE,COLUMN_MESSAGE,COLUMN_CATEGORY,COLUMN_DATE};

    public static final String DATABASE_CREATE="create table "+NOTE_TABLE+" ( "+COLUMN_ID + " integer primary key autoincrement, "+
                                                    COLUMN_TITLE+" text not null, "+ COLUMN_MESSAGE +" text not null, "+ COLUMN_CATEGORY + " text not null, "
                                                    +COLUMN_DATE + " );";
    private SQLiteDatabase sqlDB;
    private Context context;

    private NotebookDbHelper notebookDbHelper;

    private static class NotebookDbHelper extends SQLiteOpenHelper{

        public NotebookDbHelper(Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+NOTE_TABLE);
            onCreate(db);
        }
    }

    public NotebookDbAdapter( Context ctx)
    {
        context=ctx;
    }

    public NotebookDbAdapter open() throws android.database.SQLException{
        notebookDbHelper= new NotebookDbHelper(context);
        sqlDB =notebookDbHelper.getWritableDatabase();
        return this;


    }

    public void close()
    {
        notebookDbHelper.close();
    }

    public ArrayList<Note> getAllNotes()
    {
        ArrayList<Note> notes = new ArrayList<Note>();

        Cursor cursor = sqlDB.query(NOTE_TABLE,allColumns,null,null,null,null,null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst();cursor.moveToPrevious())
        {
            Note note =cursorTonote(cursor);
            notes.add(note);
        }
        cursor.close();
        return notes;

    }

    private Note cursorTonote(Cursor  cursor)
    {
        Note note= new Note(cursor.getString(1),cursor.getString(2),Note.Category.valueOf(cursor.getString(3)),cursor.getLong(0),cursor.getLong(4));
        return note;
    }

    public Note createNote(String title, String message, Note.Category category)
    {
        ContentValues value = new ContentValues();
        value.put(COLUMN_TITLE,title);
        value.put(COLUMN_MESSAGE,message);
        value.put(COLUMN_CATEGORY,category.name());
        value.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+"");
        long insertID = sqlDB.insert(NOTE_TABLE,null,value);

        Cursor cursor = sqlDB.query(NOTE_TABLE,allColumns,COLUMN_ID+" = "+insertID,null,null,null,null);
        cursor.moveToFirst();

        Note newNote = cursorTonote(cursor);
        Log.w("create  query",newNote.getNoteId()+"");
        Log.w("create query",newNote.getTitle()+"");
        Log.w("create query",newNote.getMessage()+"");
        cursor.close();


         cursor = sqlDB.query(NOTE_TABLE,allColumns,COLUMN_ID+" = "+insertID,null,null,null,null);
        cursor.moveToFirst();
        newNote = cursorTonote(cursor);

        Log.w("acess againcreate ",newNote.getNoteId()+"");
        Log.w("acess againcreatery",newNote.getTitle()+"");
        Log.w("acess againcreatery",newNote.getMessage()+"");
        cursor.close();
        return newNote;
    }
    public long updateNote(long idToUpdate, String title,String message, Note.Category category)
    {
        ContentValues value = new ContentValues();
        value.put(COLUMN_TITLE,title);
        value.put(COLUMN_MESSAGE,message);
        value.put(COLUMN_CATEGORY,category.name());
        value.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis()+"");


        Log.w("ID_toupdate",idToUpdate+"");
        Cursor cursor = sqlDB.query(NOTE_TABLE,allColumns,COLUMN_ID+" = "+idToUpdate,null,null,null,null);
        cursor.moveToFirst();

        Note newNote = cursorTonote(cursor);
        cursor.close();
        Log.w("before  query",newNote.getNoteId()+"");
        Log.w("before query",newNote.getTitle()+"");
        Log.w("before query",newNote.getMessage()+"");

       long t= sqlDB.update(NOTE_TABLE,value,COLUMN_ID + " = "+idToUpdate,null);


        Log.w("idtoupdate",idToUpdate+"");
        Log.w("return edited",t+"");
        cursor = sqlDB.query(NOTE_TABLE,allColumns,COLUMN_ID+" = "+idToUpdate,null,null,null,null);
        cursor.moveToFirst();

        newNote = cursorTonote(cursor);
        cursor.close();
        Log.w("after  query",newNote.getNoteId()+"");
        Log.w("after query",newNote.getTitle()+"");
        Log.w("after query",newNote.getMessage()+"");
        return t;
    }

    public long deleteNote(long idToDelete)
    {
        return sqlDB.delete(NOTE_TABLE,COLUMN_ID+" = "+idToDelete,null);

    }
}
