package devices;

public interface DispositivoMedico {
    double coletarDados();

    void calibrar();

    boolean isCalibrado();

    String getTipoDispositivo();
}
