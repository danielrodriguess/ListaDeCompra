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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FazerCompra extends Activity {
	ListarItens itens = new ListarItens();
	ItemCompra item = new ItemCompra();
	SQLiteDatabase db;
	private SimpleCursorAdapter ad;
	private ListView lvdados;
	public static int h = 0,anomi=0,caaa,compra,d,a,foda,ag;
	View v;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fazer_compra);
		a = 0;
		ag = 0;
		 try{
				db = openOrCreateDatabase("vamo", Context.MODE_PRIVATE, null);
				db.execSQL("CREATE TABLE IF NOT EXISTS lista(_id INTEGER PRIMARY KEY AUTOINCREMENT,nome VARCHAR(50));");
				db.execSQL("CREATE TABLE IF NOT EXISTS itens(id INTEGER PRIMARY KEY AUTOINCREMENT,idlista INTEGER,nomee VARCHAR(50),quantidade int,medida varchar(10),status int,FOREIGN KEY(idlista) REFERENCES lista(_id));");
				}catch(SQLException ex){
					Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
				}
		 if(itens.check == 1 && item.veri == 0){
			 itens.check =0;
		 db.execSQL("UPDATE itens SET status = 0 WHERE idlista = '"+itens.nome+"'");
		 }else if(item.vol == 1){
			 item.vol = 0;			 
		 }else if(item.compra == 1){
			 item.compra = 0;
		Intent intent = getIntent();
		Bundle dados = intent.getExtras();
		  foda = dados.getInt("pee");
			 db.execSQL("UPDATE itens SET status = 0 WHERE id = '"+foda+"'");

		 }

	}
	protected void onResume() {
		super.onResume();
		String[] from = {"nomee","quantidade","medida","status"};
		int[] to = {R.id.texto20,R.id.texto30,R.id.texto40,R.id.texto50};
		Cursor c = db.rawQuery("SELECT * FROM itens INNER JOIN lista on lista._id = itens.idlista WHERE idlista = '"+itens.nome+"' and status = 0;",null);
		int con = c.getCount();
		if(con > 0){
		ad = new SimpleCursorAdapter(FazerCompra.this,R.layout.itens,c,from,to,0);
		lvdados = (ListView) findViewById(R.id.compras);
		lvdados.setAdapter(ad);
		db.close();
		
		lvdados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView adapter, View view, int position, long id){
				SQLiteCursor c = (SQLiteCursor) adapter.getAdapter().getItem(position);
				Intent intent = new Intent(FazerCompra.this,ItemCompra.class);
				compra += 1;
				intent.putExtra("vvalor", c.getInt(0));
				intent.putExtra("confere", a);
				startActivity(intent);
			}
		});
		}
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fazer_compra, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.voltando) {
			Intent intent = new Intent(FazerCompra.this,ListarItens.class);
			startActivity(intent);
		}
		else if (id == R.id.itens) {
			ag += 1;
			Intent intent = new Intent(FazerCompra.this,ItemCompra.class);
			intent.putExtra("itenss", ag);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
