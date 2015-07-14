package py.com.cleito.dbsqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    private EditText edtDNI, edtNombre, edtPartido, edtMesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDNI = (EditText) findViewById(R.id.edtDNI);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtPartido = (EditText) findViewById(R.id.edtPartido);
        edtMesa = (EditText) findViewById(R.id.edtMesa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void alta(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = edtDNI.getText().toString();
        String nombre = edtNombre.getText().toString();
        String partido = edtPartido.getText().toString();
        String mesa = edtMesa.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("dni", dni);
        registro.put("nombre", nombre);
        registro.put("partido", partido);
        registro.put("nro_mesa", mesa);

        db.insert("votantes", null, registro);
        db.close();

        edtDNI.setText("");
        edtNombre.setText("");
        edtPartido.setText("");
        edtMesa.setText("");

        Toast.makeText(this, "Se cargaron los datos de la persona", Toast.LENGTH_SHORT).show();
    }

    public void baja(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = edtDNI.getText().toString();
        String nombre = edtNombre.getText().toString();
        String partido = edtPartido.getText().toString();
        String mesa = edtMesa.getText().toString();

        int cant = db.delete("votantes", "dni="+dni, null);
        db.close();

        if(cant==1){
            Toast.makeText(this, "Se eliminó correctamente el DNI: " + dni + " :)", Toast.LENGTH_SHORT).show();
            edtDNI.setText("");
            edtNombre.setText("");
            edtPartido.setText("");
            edtMesa.setText("");
        }else{
            Toast.makeText(this, "El DNI no se encuentra :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void modif(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = edtDNI.getText().toString();
        String nombre = edtNombre.getText().toString();
        String partido = edtPartido.getText().toString();
        String mesa = edtMesa.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("partido", partido);
        registro.put("nro_mesa", mesa);

        int cant = db.update("votantes", registro, "dni="+dni, null);
        db.close();

        if(cant==1){
            Toast.makeText(this, "Se modificó correctamente el DNI: " + dni + " :)", Toast.LENGTH_SHORT).show();
            edtDNI.setText("");
            edtNombre.setText("");
            edtPartido.setText("");
            edtMesa.setText("");
        }else{
            Toast.makeText(this, "El DNI no se encuentra :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void consulta(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        SQLiteDatabase db = admin.getWritableDatabase();
        String dniPK = edtDNI.getText().toString();

        Cursor fila = db.rawQuery("select nombre, partido, nro_mesa from votantes where dni = " + dniPK, null);

        if(fila.moveToFirst()){
            edtNombre.setText(fila.getString(0));
            edtPartido.setText(fila.getString(1));
            edtMesa.setText(fila.getString(2));
        }else{
            Toast.makeText(this, "El DNI no se encuentra :(", Toast.LENGTH_SHORT).show();
        }

    }
}
