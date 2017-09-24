package Robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Collection.Stock;
import Collection.StockHistory;
import Util.Util;

/** Esta classe serve para realizar as simulacoes */
public class Simulation {

    /** Atributos */
    private int                 numTrades;
    private double              diversity;
    private double              cash;
    private Map<Object, Object> shares;
    private ArrayList<String>   tradeHistory;

    /** Metodos contrutores */
    public Simulation(Stock stock) {
        setNumTrades(0);
        setDiversity(0);
        setCash(Config.getStartingCash());
        setTradeHistory(new ArrayList<String>());
        setShares(new HashMap<Object, Object>());
        getShares().put(stock.getPapel(), 0.0);
    }

    /** Metodos modificadores */
    public void setNumTrades(int numTrades) {
        this.numTrades = numTrades;
    }

    public void setTradeHistory(ArrayList<String> tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

    public void setDiversity(double diversity) {
        this.diversity = diversity;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public void setShares(Map<Object, Object> shares) {
        this.shares = shares;
    }

    /** Metodos de retorno */
    public int getNumTrades() {
        return numTrades;
    }

    public ArrayList<String> getTradeHistory() {
        return tradeHistory;
    }

    public double getDiversity() {
        return diversity;
    }

    public double getCash() {
        return cash;
    }

    public Map<Object, Object> getShares() {
        return shares;
    }

    /** Metodos principais */
    // Este metodo tem como funcao realizar os ultimos calculos
    public double finalAssets(Stock stock) {
        // Pega o dinheiro disponivel
        double total = getCash();
        // Varre a quantidade acoes adquiridas
        for(Object key : getShares().keySet()) {
            // Pega o valor dela
            double currentValue = stock.getLastStockHistory().getCotacao();
            // Soma o valor total com a quantidade de vezes que foi comprada
            total += ((Double) getShares().get(key)) * currentValue;
            // Verifica se a quantidade de partes e maior que 0
            if(((Double) getShares().get(key)) > 0) {
                // Se for ele adiciona 1 na diversidade de acoes
                setDiversity(getDiversity() + 1);
            }
        }
        // o total sera o numero de negociacoes multiplicado pelo custo de cada
        total -= getNumTrades() * Config.getCostPerTrade();
        // Retorna o total
        return Util.formatDecimalScale(total, 2);
    }

    // Este metodo tem como funcao gerar o score de uma acao
    public double score(Stock stock) {
        // O valor de score sera os calculos finais menos o valor inicial de dinheiro mais a multiplicacao da diversidade e bonus
        double value = ((finalAssets(stock) - Config.getStartingCash()) + (getDiversity() * Config.getDiversityBonus()));
        // Retorna o valor de score
        return Util.formatDecimalScale(value, 2);
    }

    // Este metodo tem como funcao executar a simulacao
    public void runSimulation(Strategy strategy, Stock stock) {
        // Varre todo o historico da acao
        for(int i = 0; i < stock.getStockHistory().size(); i++) {
            // Cria uma direcao de negociacao
            int tradeDirection = 0;
            // Cria o verbo da negociacao
            String tradeVerb = "";
            // Verifica se este historico tem uma velocidade de alteracao compativel com a velocidade de compra da estrategia
            if(stock.getStockHistory().get(i).getVelocity() > ((Double) strategy.getParameters().get("buyVelocity"))) {
                // Se tiver significa que pode comprar
                tradeDirection = 1;
                tradeVerb = "COMPRA";
            }
            // Verifica se este historico tem uma veliocidade de alteracao compativel com a velocidade de venda da estrategia
            if(stock.getStockHistory().get(i).getVelocity() < ((Double) strategy.getParameters().get("sellVelocity"))) {
                // Se tiver significa que deve vender
                tradeDirection = -1;
                tradeVerb = "VENDA";
            }
            // Verifica se a direcao de negociacao foi alterada
            if(tradeDirection != 0) {
                // Se foi significa que ocorreu uma negociacao, realiza os calculos para saber a acao de negociacao
                double tradeAction = Util.formatDecimalScale((((Double) strategy.getParameters().get("buyVolume")) / ((Double) stock.getStockHistory().get(i).getCotacao())) * tradeDirection, 2);
                // Executa o metodo de negociacao
                trade(tradeAction, stock.getStockHistory().get(i), stock.getPapel());
                // Adiciona ao historico de compras do robo a negociacao
                getTradeHistory().add(tradeVerb + " " + stock.getPapel() + ". Velocidade em: " + stock.getStockHistory().get(i).getVelocity() + ". Acao: " + tradeAction + ". Data: " + stock.getStockHistory().get(i).getDataDaUltimaCotacao());
            }
        }
    }

    // Este metodo tem como funcao realizar a negociacao
    public void trade(double tradeAction, StockHistory stockHistory, String papel) {
        // Faz o calculo para saber o custo desta negociacao
        double tradeCost = (tradeAction * ((Double) stockHistory.getCotacao()));
        // Adiciona 1 na quantidade de negociacoes
        setNumTrades(getNumTrades() + 1);
        // Verifica a quantidade de partes desta acao
        double shares = (Double) getShares().get(papel);
        // Verifica se o dinheiro menos o custo e maior que 0
        if(cash - tradeCost < 0) {
            // Se for significa que pode realizar a negociacao, entao altera o acao de negociacao
            tradeAction = cash / ((Double) stockHistory.getCotacao());
        }
        // Verifica se as partes mais a acao de negociacao e maior que 0
        if(shares + tradeAction < 0) {
            // Se for significa que a acao de negocios e negativa
            tradeAction = shares * -1;
        }
        // O custo desta negociacao sera o acao de negociacao multiplicado pela cotacao do historico
        tradeCost = Util.formatDecimalScale((tradeAction * ((Double) stockHistory.getCotacao())), 2);
        // Adiciona as partes da negociacao essa nova negociacao
        getShares().put(papel, Util.formatDecimalScale((shares + tradeAction), 2));
        // O novo saldo sera o valor anterior menos o custo da negociacao
        cash = Util.formatDecimalScale((cash - tradeCost), 2);
        // Verifica se o saldo e menor que 0
        if(cash < 0) {
            // Se estiver significa que esta sem saldo ou a diferenca e de 1 centavo, entao
            // verifica se o saldo esta menos de 1 centavo negativo
            if(cash > -0.01) {
                // Se estiver ele leva em consideracao
                cash = 0;
            }
        }
    }
}
