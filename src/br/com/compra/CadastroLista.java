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
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroLista extends Activity {
	SQLiteDatabase db;
	Button btn1,btn2;
	EditText txt1,txt11;
	View v;
	public static int a;
	public static String b;
	public static int list = 0;
	public static int nome1;
	public static String resultado;
	public static int contador = 0,conte=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_lista);
		btn1 = (Button)findViewById(R.id.button4);
		btn2 = (Button)findViewById(R.id.button5);
		txt11 = (EditText)findViewById(R.id.txt1);
		btn2.setVisibility(View.INVISIBLE);
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cadastrar(v);
			}
		});
	}
	public void Alterar(View v){
		txt1 = (EditText)findViewById(R.id.txt1);
		Cursor c = db.rawQuery("select * from lista where nome = '"+txt1.getText()+"'", null);
		if(c.getCount() > 0){
			AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
			alerta.setTitle("Erro");
			alerta.setMessage("Nome já existente");
			alerta.setNeutralButton("Ok", null);
			alerta.show();
	}else{
		db.execSQL("UPDATE lista SET nome = '"+txt1.getText()+"' where _id = '"+nome1+"'");
   		Toast.makeText(getApplicationContext(), "Nome alterado com sucesso", Toast.LENGTH_SHORT).show();

	}
	}

	public void Cadastrar(View v){
		btn1.setText("Salvar/Alterar");
		btn2.setVisibility(View.INVISIBLE);
		txt1 = (EditText)findViewById(R.id.txt1);
		Cursor c = db.rawQuery("select * from lista where nome = '"+txt1.getText()+"'", null);
		int id[] = new int[c.getCount()];
		int i = 0;
		 if(c.moveToNext()){
	   		 a = c.getInt(c.getColumnIndex("_id"));
	   	 }
		if(c.getCount() > 0){
			AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
			alerta.setTitle("Erro");
			final EditText input = new EditText(this);
			alerta.setView(input);
			alerta.setMessage("Lista já inclusa. Deseja alterar o nome da lista?");
			alerta.setPositiveButton("Sim",new DialogInterface.OnClickListener(){
				@Override
		        public void onClick(DialogInterface dialog, int whichButton)
		        {
		           //I get a compile error here, it wants result to be final.
		           resultado = input.getText().toString();
		          Cursor c = db.rawQuery("SELECT * FROM lista WHERE nome = '"+resultado.toString()+"'", null);
		   		int id[] = new int[c.getCount()];
		   		int i = 0;
		   		if(c.getCount() > 0){
		   			AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
					alerta.setTitle("Erro");
					alerta.setMessage("Este nome também já consta. Escolha outro!");
					alerta.setNeutralButton("Ok", null);
					alerta.show();
		   		}else{
		        db.execSQL("UPDATE lista SET nome = '"+resultado.toString()+"' where _id = '"+a+"'");
		   		Toast.makeText(getApplicationContext(), "Nome alterado com sucesso", Toast.LENGTH_SHORT).show();
		   		list += 1;
		   		}
		        }	
			});
			alerta.setNegativeButton("Não", null);
			alerta.create().show();
		}else{
			if(txt1.getText().toString().equals("")){
				AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
				alerta.setTitle("Erro");
				alerta.setMessage("Preencha o campo");
				alerta.setNeutralButton("Ok", null);
				alerta.show();
			}else{
		db.execSQL("INSERT INTO lista VALUES (?,'"+txt1.getText()+"')");
		resultado = txt1.getText().toString();
		Toast.makeText(getApplicationContext(), "Nova lista criada", Toast.LENGTH_SHORT).show();
		list += 1;
		txt1.setText("");
		btn2.setVisibility(View.INVISIBLE);
			}
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro_lista, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.um) {
			Intent intent = new Intent(CadastroLista.this,MainActivity.class);
			startActivity(intent);
		}else if (id == R.id.dois) {
			if(list > 0){
				list = 0;
				Intent intent = new Intent(CadastroLista.this,CadastrarItem.class);
				startActivity(intent);
			}else{
				Cursor c = db.rawQuery("SELECT * FROM lista WHERE nome = '"+txt1.getText()+"'", null);
				int g = c.getCount();
				if(g > 0){
					if(c.moveToFirst()){
						Intent intent = new Intent(CadastroLista.this,CadastrarItem.class);
						conte += 1;
						intent.putExtra("pega", c.getInt(0));
						startActivity(intent);
					}
				}
				AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
				alerta.setTitle("Erro");
				alerta.setMessage("Salve a lista");
				alerta.setNeutralButton("Ok", null);
				alerta.show();
			}
		}else if (id == R.id.tres) {
			txt1 = (EditText)findViewById(R.id.txt1);
			if(txt1.getText().toString().equals("")){
				AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
				alerta.setTitle("Erro");
				alerta.setMessage("Crie a lista");
				alerta.setNeutralButton("Ok", null);
				alerta.show();
			}else{
				Cursor c = db.rawQuery("select * from lista where nome = '"+txt1.getText()+"'", null);
				int aa = c.getCount();
			   		if(aa > 0){
			   			if(c.moveToFirst()){
			   		contador += 1;
			Intent intent = new Intent(CadastroLista.this,ListarItens.class);
			intent.putExtra("idi", c.getInt(0));
			startActivity(intent);
			   			}
			   		}else{
			   			AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroLista.this);
						alerta.setTitle("Erro");
						alerta.setMessage("Lista não encontrada");
						alerta.setNeutralButton("Ok", null);
						alerta.show();
			   		}
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
