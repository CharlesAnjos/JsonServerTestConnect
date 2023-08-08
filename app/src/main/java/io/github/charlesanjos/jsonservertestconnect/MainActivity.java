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
import entidades.Agenda;
import entidades.AgendaDados;
import entidades.DadosAdicionais;

public class MainActivity extends AppCompatActivity {

  private TextView textViewID;
  private final String URL = "https://my-json-server.typicode.com/CharlesAnjos/JsonServerTest/agenda";
  private StringBuilder builder = null;
  private List<Agenda> agenda = null;
  private List<DadosAdicionais> dadosAdicionais = null;
  private List<AgendaDados> agendaDados = null;




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
        Type type = new TypeToken<List<Agenda>>(){}.getType();
        agenda = gson.fromJson(textoJSON,type);
        for(int i = 0; i< agenda.size(); i++){
          builder.append(agenda.get(i).getNome()).append("\n\n")
          .append(agenda.get(i).getTelefone()).append("\n\n")
          .append("-------------------------").append("\n\n");
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