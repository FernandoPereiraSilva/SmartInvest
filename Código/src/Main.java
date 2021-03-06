import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import Collection.User;
import Collection.Log;
import Collection.Stock;
import Collection.StockHistory;
import Robot.Robot;

/** Esta classe serve para executar a mineracao, analise, compra e venda */
public class Main {

    /** Metodos principais */
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando servi�o");
        try {
            // Define que o banco de dados so ira exibir logs de erro
            Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
            // Executa a mineracao de dados
            WebMiner.execute();
            // Consulta todas as acoes no banco de dados e executa a analise fundamentalista
            ArrayList<Stock> arrayListStock = FundamentalistAnalysis.execute(Stock.select());
            // Varre todas as acoes
            for(Stock stock : arrayListStock) {
                // Verifica se existe um historico para ser analisado
                if (stock.getStockHistory() != null){
                    // Verifica se existe pelo menos um dado neste historico
                    if (stock.getStockHistory().size() > 0){
                        // Pega o ultimo historico
                        StockHistory stockHistory = stock.getStockHistory().get(0);
                        // Guarda neste historico a pontuacao atual
                        stockHistory.setPoints(stock.getPoints());
                        // Altera no banco de dados esse historico
                        stockHistory.update();
                    }
                }
            }
            // Varre todas as acoes
            for(Stock stock : arrayListStock) {
                // Pega todo o historico
                ArrayList<StockHistory> arrayListStockHistory = stock.getStockHistory();
                // Verifica se o historico e maior que 1
                if(arrayListStockHistory.size() > 1) {
                    // Se teve significa que talvez seja ideal comprar ou vender
                    Robot robot = new Robot();
                    // Executa o algoritmo genetico e salva a direcao
                    int direction = robot.doGenetic(stock);
                    // Gera o calculo da porcentagem para saber se e bom ou nao investir nesta acao
                    double lastPercent = ((stock.getPoints() * 0.6) + (direction * 40));
                    // Verifica se a porcentagem e boa (acima de 60%)
                    if(lastPercent >= 60) {
                        // Se for boa ele compra a acao
                        new User().buyStock(stock);
                    }
                    else {
                        // Se for ruim ele vende caso alguem tenha
                        new User().sellStock(stock);
                    }
                }
            }
        }
        catch(Exception e) {
            // Cria o log de erro
            Log log = new Log();
            log.setIdAccount("");
            log.setSourceType(Log.SOURCE_TYPE_ROBOT);
            log.setDtLog(new Date());
            log.setDsLog(e.getMessage());
            log.setTypeLog(Log.TYPE_LOG_ERROR);
            // Insere o log
            log.insert();
        }
        System.out.println("Encerrando servi�o");
    }
}