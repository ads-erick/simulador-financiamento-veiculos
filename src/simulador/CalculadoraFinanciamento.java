package simulador;


public class CalculadoraFinanciamento {

    /** Taxa aplicada sobre o valor financiado (32%, conforme o exemplo do enunciado). */
    public static final double TAXA = 0.32;

    public record Resultado(double valorFinanciado, double valorParcela, double totalPagar) {
    }

    public static Resultado calcular(double valorVeiculo, double entrada, int numeroParcelas) {
        double valorFinanciado = valorVeiculo - entrada;
        double valorTotal = valorFinanciado * (1 + TAXA);
        double valorParcela = valorTotal / numeroParcelas;
        double totalPagar = valorParcela * numeroParcelas;
        return new Resultado(valorFinanciado, valorParcela, totalPagar);
    }
}
