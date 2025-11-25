package model.alertas;

import java.time.LocalDateTime;

//Classe abstrata
public abstract class Alerta {
    protected String mensagem;
    protected LocalDateTime dataHora;
    protected int idPaciente;
    protected String tipoAlerta;

    public Alerta(int idPaciente, String mensagem, String tipoAlerta) {
        this.idPaciente = idPaciente;
        this.mensagem = mensagem;
        this.tipoAlerta = tipoAlerta;
        this.dataHora = LocalDateTime.now();
    }

    public abstract void gerarAlerta();

    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataHora() { return dataHora; }
    public int getIdPaciente() { return idPaciente; }
    public String getTipoAlerta() { return tipoAlerta; }

    @Override
    public String toString() {
        return String.format("[%s] Paciente %d: %s - %s", tipoAlerta, idPaciente, mensagem, dataHora);
    }
}