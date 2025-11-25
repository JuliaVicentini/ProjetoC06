package model;

import java.time.LocalDateTime;

// Herda da classe Leitura
public class PressaoArterial extends Leitura {
    private double diastolica; // Valor da pressão diastólica

    public PressaoArterial(int idPaciente, double sistolica, double diastolica, LocalDateTime dataHora) {
        super(idPaciente, "Pressão Arterial", sistolica, dataHora);
        this.diastolica = diastolica;
    }

    // Valida se está dentro dos parâmetros normais (sistólica <= 120 e diastólica <= 80)
    @Override
    public boolean validarLeitura() {
        return valor <= 120 && diastolica <= 80; // true se estiver normal, false caso contrário
    }

    public double getDiastolica() {
        return diastolica;
    }

    //Classifica a pressão arterial com base nos valores.
    public String getClassificacao() {
        if (valor < 90 || diastolica < 60) return "Hipotensão";
        if (valor <= 120 && diastolica <= 80) return "Normal";
        if (valor <= 139 || diastolica <= 89) return "Pré-Hipertensão";
        return "Hipertensão";
    }

    // String formatada com os valores sistólica e diastólica
    @Override
    public String toString() {
        return String.format("Pressão Arterial: %.0f/%.0f (%s)", valor, diastolica, dataHora);
    }
}