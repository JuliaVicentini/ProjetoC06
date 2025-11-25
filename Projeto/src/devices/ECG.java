package devices;

//criando a classe ECG
public class ECG implements DispositivoMedico {
    private boolean calibrado;
    private int idPaciente;

    //Fazendo a verificação da identificação do paciente
    public ECG(int idPaciente) {
        this.idPaciente = idPaciente;
        this.calibrado = false; //ainda não foi calibrado
    }

    @Override //sobrescrevendo o método que já existe na superclasse
    public double coletarDados(){ //colentando dados
        if (!calibrado) {
            throw new IllegalStateException("ECG não calibrado para paciente " + idPaciente); //mensagem de erro
        }
        // Simula frequência cardíaca entre 40 e 180 bpm
        return 40 + Math.random() * 140; // menor batimento possível 40, maior 180
    }

    @Override
    public void calibrar() { //calibrando
        System.out.println("Calibrando ECG para paciente " + idPaciente);
        this.calibrado = true;
    }

    @Override
    public boolean isCalibrado(){ //tornando o método calibrado verdadeiro na superclasse
        return calibrado;
    }

    @Override
    public String getTipoDispositivo() { //para saber qual dispositivo foi calibrado
        return "ECG";
    }
}