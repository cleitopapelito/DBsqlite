package py.com.cleito.dbsqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{


    //Al instanciar la clase ya crea la db "name"
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table votantes(dni integer primary key, nombre text, partido text, nro_mesa integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("drop table if exists votantes");
        db.execSQL("create table votantes(dni integer primary key, nombre text, partido text, nro_mesa integer)");
    }
}
