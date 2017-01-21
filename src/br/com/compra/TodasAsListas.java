package br.com.compra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TodasAsListas extends Activity {
	
	private ListView lvdados;
	private SimpleCursorAdapter ad;
	SQLiteDatabase db;
	View v;
	public static int contador1 = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todas_as_listas);
		
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		 Cursor ca = db.rawQuery("SELECT * FROM lista;",null);
			int aaaa = ca.getCount();
			if (aaaa > 0){
				onResume(v);
			}else{
				AlertDialog.Builder alerta = new AlertDialog.Builder(TodasAsListas.this);
				alerta.setTitle("Erro");
				alerta.setMessage("Você não possui listas");
				alerta.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
					@Override
			        public void onClick(DialogInterface dialog, int whichButton)
			        {
						Intent intent = new Intent(TodasAsListas.this,MainActivity.class);
						startActivity(intent);
			        }
				});
				alerta.show();
				
			}
	}
	protected void onResume(View v){
		super.onResume();
		String[] from = {"nome"};
		int[] to = {R.id.texto1};
		Cursor c = db.rawQuery("SELECT * FROM lista;",null);
		ad = new SimpleCursorAdapter(getBaseContext(),R.layout.lista,c,from,to,0);
		lvdados = (ListView) findViewById(R.id.listando);
		lvdados.setAdapter(ad);
		db.close();
		
		lvdados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView adapter, View view, int position, long id){
				SQLiteCursor c = (SQLiteCursor) adapter.getAdapter().getItem(position);
					contador1 += 1;
					Intent it = new Intent(TodasAsListas.this, ListarItens.class);
					it.putExtra("id", c.getInt(0));
					 startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todas_as_listas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.dez) {
			Intent intent = new Intent(TodasAsListas.this,MainActivity.class);
			startActivity(intent);
		}else if(id == R.id.onze){
			Intent intent = new Intent(TodasAsListas.this,CadastroLista.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}


