package com.example.motionnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "motionapp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNotesTableStatement= "CREATE TABLE notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT, " +
                "position INT)";
        String createEventsTableStatement= "CREATE TABLE events (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "date TEXT, " +
                "hour TEXT, " +
                "content TEXT," +
                "position INT)";
        String createListsTableStatement= "CREATE TABLE lists (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "position INT)";
        String createItemsTableStatement= "CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT, " +
                "is_done BOOL, " +
                "list_id INTEGER," +
                "FOREIGN KEY (list_id)" +
                "   REFERENCES lists (id)" +
                "       ON DELETE CASCADE" +
                "       ON UPDATE NO ACTION)";

        db.execSQL(createNotesTableStatement);
        db.execSQL(createEventsTableStatement);
        db.execSQL(createListsTableStatement);
        db.execSQL(createItemsTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
    //NOTES
    public boolean addNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("content", note.getContent());
        cv.put("position",note.getPosition());

        long insert = db.insert("notes", null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public List<Note> getAllNotes(){
        List<Note> returnList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
        String queryString="SELECT * FROM notes";

        Cursor result = db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            do {
                int id=result.getInt(0);
                String content=result.getString(1);
                int position=result.getInt(2);

                returnList.add(new Note(id,content,position));
            } while (result.moveToNext());
        }
        else{

        }

        result.close();
        db.close();
        return returnList;
    }

    public Note getNote(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String queryString="SELECT * FROM notes WHERE id = "+id;

        Cursor result=db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            String content=result.getString(1);
            int position=result.getInt(2);

            result.close();
            db.close();

            return new Note(id, content, position);
        }
        else {
            result.close();
            db.close();
            return null;
        }
    }

    public boolean updateNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("content", note.getContent());
        cv.put("position",note.getPosition());

        int update=db.update("notes",cv,"id = ?",new String[]{Integer.toString(note.getId())});
        if(update>0){
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteNote(Note note){
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM notes WHERE id = "+note.getId();

        Cursor result=db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            return false;
        }
        else {
            return true;
        }
    }

    //EVENTS
    public boolean addEvent(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("name",event.getName());
        cv.put("date", event.getDate());
        cv.put("hour", event.getHour());
        cv.put("content", event.getContent());
        cv.put("position", event.getPosition());

        long insert = db.insert("events", null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public List<Event> getAllEvents(){
        List<Event> returnList=new ArrayList<>();

        String queryString="SELECT * FROM events";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor result = db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            do {
                int id=result.getInt(0);
                String name=result.getString(1);
                String date=result.getString(2);
                String hour=result.getString(3);
                String content=result.getString(4);
                int position=result.getInt(5);

                returnList.add(new Event(id, name,date, hour, content, position));
            } while (result.moveToNext());
        }
        else{

        }

        result.close();
        db.close();
        return returnList;
    }

    public Event getEvent(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String queryString="SELECT * FROM events WHERE id = "+id;

        Cursor result=db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            do {
                String name=result.getString(1);
                String date=result.getString(2);
                String hour=result.getString(3);
                String content=result.getString(4);
                int position=result.getInt(5);

                return new Event(id, name,date, hour, content, position);
            } while (result.moveToNext());
        }
        else{
            return null;
        }
    }

    public boolean updateEvent(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("name",event.getName());
        cv.put("date", event.getDate());
        cv.put("hour", event.getHour());
        cv.put("content", event.getContent());
        cv.put("position", event.getPosition());

        int update=db.update("events",cv,"id = ?",new String[]{Integer.toString(event.getId())});
        if(update>0){
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEvent(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM events WHERE id = "+event.getId();

        Cursor result=db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            return false;
        }
        else {
            return true;
        }
    }

    //CHECKLISTS
    public long addCheckList(CheckList checkList){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("name", checkList.getName());
        cv.put("position", checkList.getPosition());

        long insert = db.insert("lists", null, cv);
        return insert;
    }

    public List<CheckList> getAllCheckLists(){
        List<CheckList> returnList=new ArrayList<>();

        String queryString="SELECT * FROM lists";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor result = db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            do {
                int id=result.getInt(0);
                String name=result.getString(1);
                int position=result.getInt(2);

                returnList.add(new CheckList(id,name,position));
            } while (result.moveToNext());
        }
        else{

        }

        result.close();
        db.close();
        return returnList;
    }

    public CheckList getCheckList(int id){

        SQLiteDatabase db=this.getReadableDatabase();
        String queryString="SELECT * FROM lists WHERE id = "+id;

        Cursor result = db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            String r_name=result.getString(1);
            int position=result.getInt(2);

            result.close();
            db.close();

            return new CheckList(id,r_name,position);
        }
        else {
            result.close();
            db.close();
            return null;
        }
    }

    public CheckList getCheckList(String name){

        SQLiteDatabase db=this.getReadableDatabase();
        String queryString="SELECT * FROM lists WHERE name LIKE "+name;

        Cursor result = db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            int id=result.getInt(0);
            String r_name=result.getString(1);
            int position=result.getInt(2);

            result.close();
            db.close();
            return new CheckList(id,r_name,position);
        }
        else {
            result.close();
            db.close();
            return null;
        }
    }

    public boolean updateCheckList(CheckList checkList){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("name", checkList.getName());
        cv.put("position", checkList.getPosition());

        int update=db.update("lists",cv,"id = ?",new String[]{Integer.toString(checkList.getId())});
        if(update>0){
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCheckList(CheckList checkList){
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM lists WHERE id = "+checkList.getId();

        Cursor result=db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            return false;
        }
        else {
            return true;
        }
    }

    //ITEMS
    public boolean addItem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("content", item.getContent());
        cv.put("is_done", item.isDone());
//        cv.put("position", item.getPosition());
        cv.put("list_id", item.getList_id());

        long insert = db.insert("items", null, cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public List<Item> getAllItems(){
        List<Item> returnList=new ArrayList<>();

        String queryString="SELECT * FROM items";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor result = db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            do {
                int id=result.getInt(0);
                String content=result.getString(1);
                Boolean isDone=result.getInt(2) == 1;
//                int position=result.getInt(3);
                int list_id=result.getInt(3);

                returnList.add(new Item(id,content,isDone, 0,list_id));
            } while (result.moveToNext());
        }
        else{

        }

        result.close();
        db.close();
        return returnList;
    }

    public boolean updateItem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put("content", item.getContent());
        cv.put("is_done", item.isDone());
//        cv.put("position", item.getPosition());
        cv.put("list_id", item.getList_id());

        int update=db.update("items",cv,"id = ?",new String[]{Integer.toString(item.getId())});
        if(update>0){
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteItem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString="DELETE FROM items WHERE id = "+item.getId();

        Cursor result=db.rawQuery(queryString,null);

        if(result.moveToFirst()){
            return false;
        }
        else {
            return true;
        }
    }

    public void deleteAllItems() {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("items", "", new String[]{});
    }
}
