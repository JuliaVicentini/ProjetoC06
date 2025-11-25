package devices;

//criando a classe oximetro
public class Oximetro implements DispositivoMedico {
    private boolean calibrado; // Estado de calibração do dispositivo
    private int idPaciente; // ID do paciente associado

    //obtendo a identificação do paciente
    public Oximetro(int idPaciente) {
        this.idPaciente = idPaciente;
        this.calibrado = false; // Inicialmente não calibrado
    }

    @Override
    public double coletarDados() {
        if (!calibrado) {
            throw new IllegalStateException("Oximetro não calibrado para paciente " + idPaciente);//mensagem de erro
        }
        // Simula valores entre 85% e 100%(menor e maior saturação possiveis)
        return 85 + Math.random() * 15;
    }

    @Override
    public void calibrar() {
        System.out.println("Calibrando oximetro para paciente " + idPaciente);
        this.calibrado = true;
    }

    @Override
    public boolean isCalibrado() {
        return calibrado;
    }

    @Override
    public String getTipoDispositivo() {
        return "Oxímetro";
    }
}