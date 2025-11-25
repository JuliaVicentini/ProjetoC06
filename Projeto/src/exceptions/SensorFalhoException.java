package exceptions;

/**
 * Exceção UNCHECKED (RuntimeException) para simular falhas críticas de hardware.
 * O compilador não obriga o tratamento, mas nós tratamos para o sistema não cair.
 */
public class SensorFalhoException extends RuntimeException {
    public SensorFalhoException(String message) {
        super(message);
    }
}
