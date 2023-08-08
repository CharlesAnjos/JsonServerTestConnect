package io.github.charlesanjos.jsonservertestconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import auxiliadores.Auxiliador;
import auxiliadores.Conexao;
import entidades.Post;

public class MainActivity extends AppCompatActivity {

  private TextView textViewID;
  private final String URL = "https://jsonplaceholder.typicode.com/posts";
  private StringBuilder builder = null;
  private List<Post> posts = null;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textViewID = findViewById(R.id.textViewDados);

    new ObterDados().execute();
  } //onCreate

  private class ObterDados extends AsyncTask<Void,Void,Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      Toast.makeText(MainActivity.this,"Iniciando Download...",
        Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
      Conexao conexao = new Conexao();
      InputStream inputStream = conexao.obterRespostaHTTP(URL);
      Auxiliador auxiliador = new Auxiliador();
      String textoJSON = auxiliador.converter(inputStream);
      Log.i("JSON","doInBackground "+textoJSON);

      Gson gson = new Gson();
      builder = new StringBuilder();

      if(textoJSON != null){
        Type type = new TypeToken<List<Post>>(){}.getType();
        posts = gson.fromJson(textoJSON,type);
        for(int i=0;i<posts.size();i++){
          builder.append(posts.get(i).getId()).append("\n\n");
        } //for
      } //if
      else {
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(MainActivity.this, "Deu ruim",
              Toast.LENGTH_SHORT).show();
          }
        });
      } //else
      return null;
    }//doInBackground

    @Override
    protected void onPostExecute(Void unused) {
      super.onPostExecute(unused);
      textViewID.setText(builder.toString());
    }
  }//class ObterDados
}//class