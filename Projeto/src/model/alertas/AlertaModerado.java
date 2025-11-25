package model.alertas;

// Herda de Alerta
public class AlertaModerado extends Alerta {

    public AlertaModerado(int idPaciente, String mensagem) {
        super(idPaciente, mensagem, "MODERADO");
    }

    @Override
    public void gerarAlerta() {
        System.out.println("ALERTA MODERADO!");
        System.out.println("Paciente: " + idPaciente);
        System.out.println("Mensagem: " + mensagem);
        System.out.println("Data/Hora: " + dataHora);
        System.out.println("Monitorar com atenção");
        System.out.println("----------------------------------------");
    }
}