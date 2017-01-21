package br.com.compra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class CadastrarItem extends Activity {
	CadastroLista cadastro = new CadastroLista();
	ListarItens ada = new ListarItens();
	Button btn1;
	EditText txt2,txt3,txt4;
	View v;
	SQLiteDatabase db;
	public static int b;
	public static int a;
	public static int aa,nomi;
	public static String listar,aba;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_item);		
		btn1 = (Button)findViewById(R.id.button60);
		  try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		  if(ada.passartela == 1){
			  ada.passartela = 0;
			  Intent intent = getIntent();

		       Bundle dados = intent.getExtras();

		       nomi = dados.getInt("fa");
		       AlertDialog.Builder a = new AlertDialog.Builder(CadastrarItem.this);
		       a.setTitle("a");
		       a.setMessage("a"+nomi);
		       a.setNeutralButton("ok", null);
		       a.show();
			  listar = "Listar";
		  }
		  else{
			  listar = "aaa";
		  }
		  Cursor c = db.rawQuery("select * from lista where nome = '"+cadastro.resultado+"' or _id = '"+nomi+"'", null);
		  if(c.getCount() > 0){
			   if (c.moveToFirst()){
			        a = c.getInt(0);
			    }
		  }
		  
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cadastrar(v);
			}
		});
	}
	public void Cadastrar(View v){
		txt2 = (EditText)findViewById(R.id.txt10);
		txt3 = (EditText)findViewById(R.id.txt3);
		txt4 = (EditText)findViewById(R.id.txt4);
		if(txt2.getText().toString().equals("") || txt3.getText().toString().equals("") || txt4.getText().toString().equals("")){
			AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarItem.this);
			alerta.setTitle("Erro");
			alerta.setMessage("Preencha todos os campos");
			alerta.setNeutralButton("Ok", null);
			alerta.show();
		}else{
			Cursor c = db.rawQuery("select * from itens where nomee = '"+txt2.getText()+"' and idlista = '"+a+"'", null);
			int id[] = new int[c.getCount()];
			int i = 0;
			 if(c.moveToNext()){
		   		 b = c.getInt(c.getColumnIndex("id"));
		   	 }
			if(c.getCount() > 0){
					AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarItem.this);
					alerta.setTitle("Erro");
					final EditText input = new EditText(this);
					final EditText inputdois = new EditText(this);
					final EditText inputtres = new EditText(this);
					alerta.setView(input);
					alerta.setView(inputdois);
					alerta.setView(inputtres);
					alerta.setMessage("Item já incluso. Por aqui você pode alterar o nome.");
					alerta.setPositiveButton("Sim",new DialogInterface.OnClickListener(){
						@Override
				        public void onClick(DialogInterface dialog, int whichButton)
				        {
				           //I get a compile error here, it wants result to be final.
				           String resultado = input.getText().toString();
				           String resultadodois = inputdois.getText().toString();
				           String resultadotres = inputtres.getText().toString();
				          Cursor c = db.rawQuery("SELECT * FROM itens WHERE nomee = '"+resultado.toString()+"'", null);
				   		int id[] = new int[c.getCount()];
				   		int i = 0;
				   		if(c.getCount() > 0){
				   			AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarItem.this);
							alerta.setTitle("Erro");
							alerta.setMessage("Este nome também já consta. Escolha outro!");
							alerta.setNeutralButton("Ok", null);
							alerta.show();
				   		}else{
				           db.execSQL("UPDATE itens SET nomee = '"+resultado.toString()+"' where id = '"+b+"'");
				   		Toast.makeText(getApplicationContext(), "Nome alterado com sucesso", Toast.LENGTH_SHORT).show();
				   		}
				        }	
					});
					alerta.setNegativeButton("Não", null);
					alerta.create().show();
				}else{
					if(txt2.getText().toString().equals("") || txt3.getText().toString().equals("") ||txt4.getText().toString().equals("")){
						AlertDialog.Builder alerta = new AlertDialog.Builder(CadastrarItem.this);
						alerta.setTitle("Erro");
						alerta.setMessage("Preencha os campos");
						alerta.setNeutralButton("Ok", null);
						alerta.show();
					}else{
						int quantidade = Integer.parseInt(txt3.getText().toString());
						int status = 1;
				db.execSQL("INSERT INTO itens VALUES (?,'"+a+"','"+txt2.getText()+"','"+quantidade+"','"+txt4.getText()+"','"+status+"')");
				Toast.makeText(getApplicationContext(), "Item inserido com sucesso", Toast.LENGTH_SHORT).show();
				txt2.setText("");
				txt3.setText("");
				txt4.setText("");
					}
				}
				
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.um) {
			if(listar == "Listar"){
				ada.passartela = 0;
				Intent intent = new Intent(CadastrarItem.this,ListarItens.class);
				startActivity(intent);
			}else if(listar == "aaa"){
			Intent intent = new Intent(CadastrarItem.this,CadastroLista.class);
			startActivity(intent);
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
