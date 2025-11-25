package model;

import java.time.LocalDateTime; // registra momento exato da leitura

// Herda da classe Leitura
public class FrequenciaCardiaca extends Leitura {

    public FrequenciaCardiaca(int idPaciente, double valor, LocalDateTime dataHora) {
        super(idPaciente, "Frequência Cardíaca", valor, dataHora);
    }

    // Valida se está dentro dos parâmetros normais (60-100 bpm)
    @Override
    public boolean validarLeitura() {
        return valor >= 60 && valor <= 100; // true se estiver normal, false caso contrário
    }

    // Classificação da frenquência cardíaca com base no valor
    public String getClassificacao() {
        if (valor < 60) return "Bradicardia";
        if (valor <= 100) return "Normal";
        if (valor <= 120) return "Taquicardia Leve";
        return "Taquicardia Severa";
    }
}