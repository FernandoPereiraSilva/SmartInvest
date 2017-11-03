package Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Util.Util;

/** Esta classe serve para armazenar a estrategia que sera utilizada no algoritmo */
public class Strategy {

    /** Atributos */
    private double              buyVolume;
    private double              sellVolume;
    private double              buyVelocity;
    private double              sellVelocity;
    private double              score;
    private double              finalAssets;
    private double              numTrades;
    private Map<Object, Object> parameters;
    private ArrayList<String>   tradeHistory;

    /** Metodos contrutores */
    public Strategy() {
        setBuyVolume(300.0);
        setSellVolume(300.0);
        setBuyVelocity(2.0);
        setSellVelocity(-1.0);
        setScore(0);
        Map<Object, Object> parameters = new HashMap<Object, Object>();
        parameters.put("buyVolume", getBuyVolume());
        parameters.put("sellVolume", getSellVolume());
        parameters.put("buyVelocity", getBuyVelocity());
        parameters.put("sellVelocity", getSellVelocity());
        setParameters(parameters);
    }

    /** Metodos modificadores */
    public void setBuyVolume(double buyVolume) {
        this.buyVolume = buyVolume;
    }

    public void setSellVolume(double sellVolume) {
        this.sellVolume = sellVolume;
    }

    public void setBuyVelocity(double buyVelocity) {
        this.buyVelocity = buyVelocity;
    }

    public void setSellVelocity(double sellVelocity) {
        this.sellVelocity = sellVelocity;
    }

    public void setParameters(Map<Object, Object> parameters) {
        this.parameters = parameters;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setFinalAssets(double finalAssets) {
        this.finalAssets = finalAssets;
    }

    public void setNumTrades(double numTrades) {
        this.numTrades = numTrades;
    }

    public void setTradeHistory(ArrayList<String> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    /** Metodos de retorno */
    public double getBuyVolume() {
        return buyVolume;
    }

    public double getSellVolume() {
        return sellVolume;
    }

    public double getBuyVelocity() {
        return buyVelocity;
    }

    public double getSellVelocity() {
        return sellVelocity;
    }

    public Map<Object, Object> getParameters() {
        return parameters;
    }

    public double getScore() {
        return score;
    }

    public double getFinalAssets() {
        return finalAssets;
    }

    public double getNumTrades() {
        return numTrades;
    }

    public ArrayList<String> getTradeHistory() {
        return tradeHistory;
    }

    /** Metodos principais */
    // Este metodo tem como funcao gerar uma estrategia partindo de dois pais
    public void initialize(Strategy parent1, Strategy parent2) {
        // Verre todos os parametros da estrategia filha
        for(Object key : getParameters().keySet()) {
            // Pega o valor do parent1
            double parent1Value = (Double) parent1.getParameters().get(key);
            // Pega o valor do parent2
            double parent2Value = (Double) parent2.getParameters().get(key);
            // Cria a media dos dois valores
            double fullValue = ((parent1Value + parent2Value) / 2);
            // Adiciona este parametro a estrategia atual
            getParameters().put(key, Util.formatDecimalScale(fullValue, 2));
            // Realiza a mutacao
            mutate();
        }
    }

    // Este metodo tem como funcao fazer a mutacao dos dados
    public void mutate() {
        // Varre todos os parametros desta estrategia
        for(Object key : getParameters().keySet()) {
            // Pega o valor anterior
            double previousValue = (Double) getParameters().get(key);
            // Gera um numero randomico que determinara a direcao
            double direction = ((Math.random() > 0.5) ? 1 : -1);
            // Multiplica a taxa de mutacao com um numero randomico
            double mutation_rate = Config.getMutationMaxRate() * Math.random();
            // O valor completo sera a soma do anterior mais a multiplicacao da direcao, taxa de mutacao e valor anterior
            double fullValue = (previousValue + (direction * mutation_rate * previousValue));
            // Altera o valor anterior do parametro
            getParameters().put(key, Util.formatDecimalScale(fullValue, 2));
        }
    }
}