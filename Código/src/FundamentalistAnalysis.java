import java.util.ArrayList;

import Collection.Stock;
import Collection.StockHistory;

/** Esta classe serve para fazer a analise fundamentalista e classificar as acoes */
public class FundamentalistAnalysis {

    /** Metodos principais */
    // Este metodo tem como funcao quantificar as acoes, ele recebe uma populacao e retorna ela com a sua pontuacao
    public static ArrayList<Stock> execute(ArrayList<Stock> arrayList) throws Exception {
        // Varre todas as acoes
        for(Stock stock : arrayList) {
            // Pega o ultimo historico
            StockHistory stockHistory = stock.getLastStockHistory();
            // Verifica se existe um historico
            if (stockHistory != null){
                // Se for significa que tem historico, entao ele executa os metodos de analise abaixo
                stock.setPoints(aloneStockAnalyse(stockHistory));
            }
        }
        // Retorna a lista de acoes
        return arrayList;
    }

    /** Metodos de analise unica, analisa uma acao por vez */
    // Comparar valor de mercado com valor da firma, valor de mercado deve ser menor, 15 pontos
    private static int aloneStockAnalyse(StockHistory stockHistory) {
        return ((stockHistory.getValorDeMercado() < stockHistory.getValorDaFirma()) ? 15 : 0) + aloneStockAnalyse2(stockHistory);
    }

    // Análise do índice de PL, deve ser em média menor que 10%, 15 pontos
    private static int aloneStockAnalyse2(StockHistory stockHistory) {
        return ((stockHistory.getpL() < 10) ? 15 : 0) + aloneStockAnalyse3(stockHistory);
    }

    // Análise do ROE, média deve ser menor que 30%, 30 pontos
    private static int aloneStockAnalyse3(StockHistory stockHistory) {
        return ((stockHistory.getRoe() < 0.3) ? 30 : 0) + aloneStockAnalyse4(stockHistory);
    }

    // Liquidez Corrente maior que 1.5, 10 pontos
    private static int aloneStockAnalyse4(StockHistory stockHistory) {
        return ((stockHistory.getLiquidezCorr() >= 1) ? 10 : 0) + aloneStockAnalyse5(stockHistory);
    }

    // Analise da receita liquida anual, a receita anual dividida por 4 tem que ser menor que a receita trimestral, 20 pontos
    private static int aloneStockAnalyse5(StockHistory stockHistory) {
        return (((stockHistory.getReceitaLiquida12() / 4.0) < stockHistory.getReceitaLiquida3()) ? 20 : 0) + aloneStockAnalyse6(stockHistory);
    }

    // Analise de vpa, subrair o valor da acao do vpa, tem que ser menor do que 10, 10 pontos
    private static int aloneStockAnalyse6(StockHistory stockHistory) {
        return((stockHistory.getCotacao() - stockHistory.getvPA() < 10) ? 10 : 0);
    }
}