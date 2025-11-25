package model;

import java.time.LocalDateTime;

public abstract class Leitura {
    protected int idPaciente; // protected - acesso direto para subclasses, mantem encapsulamento
    protected String tipoLeitura;
    protected double valor;
    protected LocalDateTime dataHora;

    public Leitura(int idPaciente, String tipoLeitura, double valor, LocalDateTime dataHora) {
        this.idPaciente = idPaciente;
        this.tipoLeitura = tipoLeitura;
        this.valor = valor;
        this.dataHora = dataHora;
    }

    // metodo abstrato que deve ser implementado pelas subclasses
    public abstract boolean validarLeitura();

    public int getIdPaciente() {
        return idPaciente;
    }

    public String getTipoLeitura() {
        return tipoLeitura;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f (%s)", tipoLeitura, valor, dataHora);
    }
}