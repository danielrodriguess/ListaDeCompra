package br.com.compra;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListarItens extends Activity {
	TodasAsListas todas = new TodasAsListas();
	CadastroLista cad = new CadastroLista();
SQLiteDatabase db;
private ListView lvdados;
private SimpleCursorAdapter ad;
public static int nome,valor,ccc = 0,passartela=0,check;
public static String tela;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_itens);
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		 if(todas.contador1 == 1){
			 tela = "todas";
			 todas.contador1 = 0;
			 Intent intent = getIntent();

		        Bundle dados = intent.getExtras();

		        nome = dados.getInt("id");
		 }else if(cad.contador == 1){
			 tela = "cadastro";
			 cad.contador = 0;
			 Intent intent = getIntent();

		        Bundle dados = intent.getExtras();

		        nome = dados.getInt("idi");
		 }
			 
		  
	       
		}
	
		protected void onResume() {
			super.onResume();
			String[] from = {"nomee","quantidade","medida","status"};
			int[] to = {R.id.texto20,R.id.texto30,R.id.texto40,R.id.texto50};
			Cursor c = db.rawQuery("SELECT * FROM itens INNER JOIN lista on lista._id = itens.idlista WHERE idlista = '"+nome+"';",null);
			int con = c.getCount();
			if(con > 0){
			ad = new SimpleCursorAdapter(ListarItens.this,R.layout.itens,c,from,to,0);
			lvdados = (ListView) findViewById(R.id.list);
			lvdados.setAdapter(ad);
			db.close();
			
			lvdados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
				@Override
				public void onItemClick(AdapterView adapter, View view, int position, long id){
					final SQLiteCursor c = (SQLiteCursor) adapter.getAdapter().getItem(position);
					AlertDialog.Builder alerta = new AlertDialog.Builder(ListarItens.this);
					alerta.setTitle("Seu item");
					alerta.setMessage("O que deseja fazer com este item?");
					alerta.setNeutralButton("Cancelar",null);
					alerta.setPositiveButton("Editar",new DialogInterface.OnClickListener(){
						@Override
				        public void onClick(DialogInterface dialog, int whichButton)
				        {
							Intent intent = new Intent(ListarItens.this,EditarItem.class);
							intent.putExtra("ida", c.getInt(c.getColumnIndex("id")));
							
							startActivity(intent);
				        }	
					});
					alerta.setNegativeButton("Deletar item",new DialogInterface.OnClickListener(){
						@Override
				        public void onClick(DialogInterface dialog, int whichButton)
				        {
							ccc += 1;
							Intent intent = new Intent(ListarItens.this,EditarItem.class);
							intent.putExtra("ida", c.getInt(c.getColumnIndex("id")));
							
							startActivity(intent);
				        }	
					});
					alerta.create().show();
					
				}
			
			});
			}else{
				AlertDialog.Builder alerta = new AlertDialog.Builder(ListarItens.this);
				alerta.setTitle("Lista vazia");
				alerta.setMessage("O que deseja fazer com a lista?");
				alerta.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(tela == "todas"){
							Intent intent = new Intent(ListarItens.this,TodasAsListas.class);
							startActivity(intent);
						}else if(tela == "cadastro"){
							Intent intent = new Intent(ListarItens.this,CadastroLista.class);
							startActivity(intent);
						}
						
					}
				});
				alerta.setPositiveButton("Editar",new DialogInterface.OnClickListener(){
					@Override
			        public void onClick(DialogInterface dialog, int whichButton)
			        {
						Intent intent = new Intent(ListarItens.this,EditarLista.class);
						intent.putExtra("idi", nome);
						startActivity(intent);
			        }	
				});
				alerta.setNegativeButton("Deletar lista",new DialogInterface.OnClickListener(){
					@Override
			        public void onClick(DialogInterface dialog, int whichButton)
			        {
						db.execSQL("DELETE FROM lista WHERE _id = '"+nome+"'");
						db.close();
						Intent intent = new Intent(ListarItens.this,TodasAsListas.class);
						intent.putExtra("idi", nome);
						startActivity(intent);
			        }	
				});
				alerta.create().show();
			}
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listar_itens, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.teste) {
			if(tela == "todas"){
				Intent intent = new Intent(ListarItens.this,TodasAsListas.class);
				startActivity(intent);
			}else if(tela == "cadastro"){
				Intent intent = new Intent(ListarItens.this,CadastroLista.class);
				startActivity(intent);
			}
		}else if(id == R.id.teste1){
			passartela += 1;
			Intent intent = new Intent(ListarItens.this,CadastrarItem.class);
			intent.putExtra("fa", nome);
			startActivity(intent);
		}else if(id == R.id.teste2){
			check += 1;
			Intent intent = new Intent(ListarItens.this,FazerCompra.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
