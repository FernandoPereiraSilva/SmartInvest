package Robot;

import Collection.Stock;
import Collection.StockHistory;
import Util.Util;

/** Esta classe serve para realizar todos os metodos de analise do robo */
public class Robot {

    /** Metodos contrutores */
    public Robot() {
        // Inicializa os valores da configuracao geral
        Config.setPopulationSize(10);
        Config.setMutationMaxRate(0.20);
        Config.setNumGenerations(600);
        Config.setBreedingPopulationSize(3);
        Config.setStartingCash(10000000);
        Config.setVelocityDecay(1.3);
        Config.setCostPerTrade(6);
        Config.setDiversityBonus(100);
    }

    /** Metodos principais */
    // Este metodo tem como funcao executar o algoritmo genetico
    public int doGenetic(Stock stock) {
        // Cria um valor de fechamento da pontuacao fundamentalista
        double previousClosePoints = 0;
        // Cria um valor de fechamento da cotacao
        double previousCloseAction = 0;
        // Cria uma velocidade
        double velocity = 0;
        // Varre todo o historico desta acao
        for(StockHistory stockHistory : stock.getStockHistory()) {
            // Informa que a pontuacao de abertura e a pontuacao deste historico
            Double currentClosePoints = stockHistory.getPoints();
            // Informa que a cotacao de abertura e a cotacao deste historico
            Double currentCloseAction = stockHistory.getCotacao();
            // Verifica se teve uma pontuacao de fechamento
            if(previousClosePoints > 0) {
                // Se tiver significa que pode ter ocorrido uma variacao, entao pega o valor que mudou
                Double changePoints = ((currentClosePoints == null) ? 0.0 : Util.formatDecimalScale((currentClosePoints - previousClosePoints), 2));
                // Pega a porcentagem de mudanca
                double changePercentPoints = Util.formatDecimalScale((changePoints / previousClosePoints * 10), 2);
                // Pega a velocidade de decaimento desta acao nesses dois dias
                double decayedVelocityPoints = velocity / Config.getVelocityDecay();
                // Grava a velocidade
                velocity = Util.formatDecimalScale((decayedVelocityPoints + changePercentPoints), 2);
            }
            if(previousCloseAction > 0) {
                // Se tiver significa que pode ter ocorrido uma variacao, entao pega o valor que mudou
                Double changeAction = ((currentCloseAction == null) ? 0.0 : Util.formatDecimalScale((currentCloseAction - previousCloseAction), 2));
                // Pega a porcentagem de mudanca
                double changePercentAction = Util.formatDecimalScale((changeAction / previousCloseAction * 10), 2);
                // Pega a velocidade de decaimento desta acao nesses dois dias
                double decayedVelocityAction = velocity / Config.getVelocityDecay();
                // Grava a velocidade
                velocity += Util.formatDecimalScale((decayedVelocityAction + changePercentAction), 2);
            }
            // Informa que o valor de fechamento sera este valor de abertura para realizar uma nova analise de velocidade
            previousClosePoints = ((currentClosePoints == null) ? 0 : currentClosePoints);
            // Informa que o valor de fechamento sera este valor de abertura para realizar uma nova analise de velocidade
            previousCloseAction = ((currentCloseAction == null) ? 0 : currentCloseAction);
            // Informa que este historico teve velocidade de decaimento ou crescimento x
            stockHistory.setVelocity(velocity);
        }
        // Cria uma populacao vazia
        Population population = null;
        // Varre a quantidade de geracoes
        for(int i = 0; i < Config.getNumGenerations(); i++) {
            // Verifica se a populacao esta vazia
            if(population == null) {
                // Se tiver ele cria uma nova
                population = new Population();
                // Inicializa a populacao
                population.initialize();
            }
            // Varre as estrategias geradas na populacao
            for(Strategy strategy : population.getMembers()) {
                // Verifica se a estrategia nao foi pontuada ainda
                if(strategy.getScore() == 0) {
                    // Se for verdade ele cria uma simulacao pra ela
                    Simulation simulation = new Simulation(stock);
                    // Executa a simulacao passando a estrategia e a acao
                    simulation.runSimulation(strategy, stock);
                    // Grava o score na estrategia
                    strategy.setScore(simulation.score(stock));
                    // Grava os calculos finais
                    strategy.setFinalAssets(simulation.finalAssets(stock));
                    // Grava a quantidade de negociacoes
                    strategy.setNumTrades(simulation.getNumTrades());
                    // Grava o historico de negociacoes
                    strategy.setTradeHistory(simulation.getTradeHistory());
                }
            }
            // Executa a evolucao da populacao
            population.evolve();
        }
        // Retorna a pontuacao final
        return getFinalScore(population.getMembers().get(0));
    }

    // Este metodo tem como funcao gerar uma pontuacao final que sera somada a analise fundamentalista
    public int getFinalScore(Strategy strategy) {
        // Cria uma quantidade de compras 0
        int buy = 0;
        // Cria uma quantidade de vendas 0
        int sell = 0;
        // Varre todas as negociacoes desta estrategia
        for(String aux : strategy.getTradeHistory()) {
            // Verifica se foi uma compra ou uma venda e soma nas quantidades
            if(aux.startsWith("COMPRA")) {
                buy++;
            }
            else if(aux.startsWith("VENDA")) {
                sell++;
            }
        }
        // Verifica se a quantidade de compra for 0 ou os indices forem iguais retorna 0
        if(buy == 0 || buy == sell)
            return 0;
        // Verifica se a quantidade de compra for maior que a de venda, se for retorna 1
        if(buy > sell)
            return 1;
        // Retorna -1
        return -1;
    }
}
