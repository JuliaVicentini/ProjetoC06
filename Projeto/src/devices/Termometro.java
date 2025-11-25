package devices;

//criando a classe termometro
public class Termometro implements DispositivoMedico {
    private boolean calibrado;
    private int idPaciente;

    public Termometro(int idPaciente) {
        this.idPaciente = idPaciente;
        this.calibrado = false;
    }

    @Override
    public double coletarDados() {
        if (!calibrado) {
            throw new IllegalStateException("Termômetro não calibrado para paciente " + idPaciente);
        }
        // Simula temperatura entre 35.0°C e 40.0°C (menor e maior)
        return 35.0 + Math.random() * 5.0;
    }

    @Override
    public void calibrar() {
        System.out.println("Calibrando termômetro para paciente " + idPaciente);
        this.calibrado = true;
    }

    @Override
    public boolean isCalibrado() {
        return calibrado;
    }

    @Override
    public String getTipoDispositivo() {
        return "Termômetro";
    }
}