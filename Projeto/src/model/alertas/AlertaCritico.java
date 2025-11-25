package model.alertas;

// Herda de Alerta
public class AlertaCritico extends Alerta {

    public AlertaCritico(int idPaciente, String mensagem) {
        super(idPaciente, mensagem, "CRÍTICO");
    }

    @Override
    public void gerarAlerta() {
        System.out.println("ALERTA CRÍTICO!");
        System.out.println("Paciente: " + idPaciente);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("Data/Hora: " + dataHora);
        System.out.println("Ação Imediata Requerida!");
        System.out.println("----------------------------------------");
    }
}