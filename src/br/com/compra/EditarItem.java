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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class EditarItem extends Activity {
	ListarItens a = new ListarItens();
	FazerCompra compra = new FazerCompra();
SQLiteDatabase db;
public static int nomi;
View v;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_item);
		Button btn1 = (Button)findViewById(R.id.button60);
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		 Intent intent = getIntent();

	       Bundle dados = intent.getExtras();

	       nomi = dados.getInt("ida");
	       if(a.ccc == 1){
	    	   a.ccc=0;
	    	   db.execSQL("DELETE FROM itens where id = '"+nomi+"'");
	    	   Intent intenta = new Intent(EditarItem.this,ListarItens.class);
	   		startActivity(intenta);
	       }
	       btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Alterar(v);
			}
		});
	}
public void Alterar(View v){
	EditText txt1 = (EditText)findViewById(R.id.nome);
	EditText txt2 = (EditText)findViewById(R.id.quantidade);
	EditText txt3 = (EditText)findViewById(R.id.medida);
	if(txt1.getText().toString().equals("") || txt2.getText().toString().equals("") || txt3.getText().toString().equals("")){
		 AlertDialog.Builder alerta = new AlertDialog.Builder(EditarItem.this);
			alerta.setTitle("Erro");
			alerta.setMessage("Preencha todos os campos");
			alerta.setNeutralButton("Ok", null);
			alerta.show();
	}else{
		int y = Integer.parseInt(txt2.getText().toString());
		db.execSQL("UPDATE itens SET nomee = '"+txt1.getText()+"',quantidade = '"+y+"',medida = '"+txt3.getText()+"' WHERE id = '"+nomi+"'");
		Intent intent = new Intent(EditarItem.this,ListarItens.class);
		startActivity(intent);
	}
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(EditarItem.this,ListarItens.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
