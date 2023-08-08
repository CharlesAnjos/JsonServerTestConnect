package entidades;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgendaDados {

  @SerializedName("agenda")
  @Expose
  private List<AgendaDados> agenda;
  @SerializedName("dadosAdicionais")
  @Expose
  private List<DadosAdicionais> dadosAdicionais;

  public List<AgendaDados> getAgenda() {
    return agenda;
  }

  public void setAgenda(List<AgendaDados> agenda) {
    this.agenda = agenda;
  }

  public List<DadosAdicionais> getAdicionais() {
    return dadosAdicionais;
  }

  public void setAdicionais(List<DadosAdicionais> dadosAdicionais) {
    this.dadosAdicionais = dadosAdicionais;
  }

}