package br.com.compra;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ItemCompra extends Activity {
	ListarItens itens = new ListarItens();
	SQLiteDatabase db;
	private SimpleCursorAdapter ad;
	private ListView lvdados;
	public static int h = 0,anomi=0,caaa,compra,vol,veri;
	public static int verificar;
	View v;
	public static int ai;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fazer_compra);
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		 Intent intentad = getIntent();

	       Bundle adados = intentad.getExtras();
	       int ia = adados.getInt("itenss");
	       if(ia == 1){
	    	   
	       }else{
	       
		 Intent intent = getIntent();

	       Bundle dados = intent.getExtras();
	       int i = dados.getInt("vvalor");

    
	      ai = i;
	      veri += 1;
			 db.execSQL("UPDATE itens SET status = 1 WHERE id = '"+ai+"'");
	       }

	}
	protected void onResume() {
		super.onResume();
		String[] from = {"nomee","quantidade","medida","status"};
		int[] to = {R.id.texto20,R.id.texto30,R.id.texto40,R.id.texto50};
		Cursor c = db.rawQuery("SELECT * FROM itens INNER JOIN lista on lista._id = itens.idlista WHERE idlista = '"+itens.nome+"' and status = 1;",null);
		int con = c.getCount();
		if(con > 0){
		ad = new SimpleCursorAdapter(ItemCompra.this,R.layout.itens,c,from,to,0);
		lvdados = (ListView) findViewById(R.id.compras);
		lvdados.setAdapter(ad);
		db.close();
		
		lvdados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView adapter, View view, int position, long id){
				SQLiteCursor c = (SQLiteCursor) adapter.getAdapter().getItem(position);
				
				Intent intent = new Intent(ItemCompra.this,FazerCompra.class);
				compra += 1;
				intent.putExtra("pee", c.getInt(0));
				startActivity(intent);
			}
		});
		}
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_compra, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.voltando) {
			vol += 1;
			Intent intent = new Intent(ItemCompra.this,FazerCompra.class);
			startActivity(intent);
		}
		else if (id == R.id.apagar) {
			Toast.makeText(getApplicationContext(), "Compra efetuada com sucesso", Toast.LENGTH_SHORT).show();
			verificar += 1;
			itens.check = 0;
			Intent intent = new Intent(ItemCompra.this,MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
