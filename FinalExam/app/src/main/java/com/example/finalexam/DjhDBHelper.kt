package com.example.finalexam

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView

class DjhDBHelper(val context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{
        val DB_NAME = "mydb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "mylocation"
        val ID = "id"
        val LAT = "lat"
        val LNG = "lng"
        val TITLE = "title"
        val CONTENT = "content"


    }

    fun showRecord(name: String){
        val strsql = "select * from $TABLE_NAME where $TITLE='$name';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0

        cursor.moveToFirst()
        val attrcount = cursor.columnCount
        val activity = context as MainActivity
//        activity.binding.tableLayout.removeAllViewsInLayout()
        //make title
        val tablerow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tablerow.layoutParams = rowParam
//        val viewParam = TableRow.LayoutParams(0, 100, 1F)
//        for(i in 0 until attrcount){
//            val textView = TextView(activity)
//            textView.layoutParams = viewParam
//            textView.text = cursor.getColumnName(i)
//            textView.setBackgroundColor(Color.WHITE)
//            textView.textSize = 15.0f
//            textView.gravity = Gravity.CENTER
//            tablerow.addView(textView)
//        }
//        activity.binding.tableLayout.addView(tablerow)
//        if(cursor.count == 0) return
        //add record
        do{
            var tempTitle = cursor.getString(cursor.getColumnIndex("title"))
            var tempLng = cursor.getString(cursor.getColumnIndex("lng"))
            var tempLat = cursor.getString(cursor.getColumnIndex("lat"))
            if (tempTitle.equals(name)){
                activity.binding.templat.text = tempLat.toString()
                activity.binding.templng.text = tempLng.toString()
            }
        }while(cursor.moveToNext())

        cursor.close()
        db.close()
    }


    fun findProduct(name: String): Boolean {
        val strsql = "select * from $TABLE_NAME where $TITLE='$name';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        cursor.close()
        db.close()
        return flag

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME("+
                "$ID integer primary key autoincrement, "+
                "$LAT double, "+
                "$LNG double, "+
                "$TITLE text, "+
                "$CONTENT text);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}