package br.com.compra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarLista extends Activity {
View v;
SQLiteDatabase db;
public static int oo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_lista);
		Button btn1 = (Button)findViewById(R.id.button400);
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		 Intent intent = getIntent();

	        Bundle dados = intent.getExtras();

	        oo = dados.getInt("idi");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Alterar(v);
				
			}
		});
	}
public void Alterar(View v){
	EditText a = (EditText)findViewById(R.id.txt200);
	if(a.getText().toString().equals("")){
		AlertDialog.Builder alerta = new AlertDialog.Builder(EditarLista.this);
		alerta.setTitle("Erro");
		alerta.setMessage("Campo vazio");
		alerta.setNeutralButton("Ok", null);
		alerta.show();
	}else{
		db.execSQL("UPDATE lista set nome='"+a.getText()+"' where _id = '"+oo+"'");
		Toast.makeText(getApplicationContext(), "Alterado com sucesso", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(EditarLista.this,TodasAsListas.class);
		startActivity(intent);
	}
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_lista, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(EditarLista.this,TodasAsListas.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
