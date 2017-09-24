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
            // Verifica se ele e vazio
            if(stockHistory == null) {
                // Se for significa que nao tem nenhum historico, entao insere pontuacao 0
                stock.setPoints(0);
            }
            else {
                // Se nao for significa que tem historico, entao ele executa os metodos de analise abaixo
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

    // Total de ativos Maior que o patrimônio líquido, 10 pontos
    // private static int aloneStockAnalyse5(StockHistory stockHistory){
    // return ((stockHistory.getAtivo() > stockHistory.getPatrimLiq()) ? 10 : 0);
    // }

    /** Metodos de analise total, analisa todas as acoes se baseando em um valor */
    // Dividend Yeld menor possível, 15 pontos
    // private static ArrayList<Stock> allStocksAnalyse(ArrayList<Stock> arrayList) throws Exception{
    // Cria uma ArrayList contendo todos os pontos para esta analise
    // ArrayList<Double> availablePoints = preparePointsArrayList(new double[]{15, 12, 9, 6, 3});
    // Torna a ArrayList ordenada por DivYield
    // arrayList = organizeArray(arrayList, StockHistory.class.getMethod("getDivYield"));
    // Cria um indice para auxiliar na pontuacao
    // int index = 0;
    // Enquanto existir pontos para distribuir e ainda nao tiver chegado no fim da ArrayList de acoes
    // while (availablePoints.size() > 0 && index < arrayList.size()){
    // Adiciona os pontos aos que ja existem
    // arrayList.get(index).setPoints(arrayList.get(index).getPoints() + availablePoints.get(0));
    // Remove esse ponto da ArrayList, para nao poder pontuar de novo
    // availablePoints.remove(0);
    // Vai para o proximo indice
    // index++;
    // }
    // Retorna a ArrayList pontuada
    // return arrayList;
    // }

    /** Metodos auxiliares */
    // Este metodo tem como funcao organizar um ArrayList seguindo um determinado valor (metodo get passado como parametro, precisa ser numero)
    // private static ArrayList<Stock> organizeArray(ArrayList<Stock> arrayList, Method getFromStock) throws Exception{
    // Cria uma ArrayList auxiliar
    // ArrayList<Stock> arrayListAux = new ArrayList<Stock>();
    // Enquanto esta ArrayList nao estiver vazia...
    // while (arrayList.size() > 0){
    // Variaveis para armazenar os Max
    // int indexMax = 0;
    // double valueMax = 0;
    // Varre a ArrayList...
    // for (int i = 0; i < arrayList.size(); i++){
    // Verifica se o valor atual do get passado e maior que o valor maximo armazenado anteriormente
    // if (new Double(getFromStock.invoke(arrayList.get(i).getLastStockHistory()) + "") > valueMax){
    // Guarda as informacoes atuais
    // indexMax = i;
    // valueMax = new Double(getFromStock.invoke(arrayList.get(i).getLastStockHistory()) + "");
    // }
    // }
    // Transfere a acao de uma ArrayList para outra auxiliar
    // arrayListAux.add(arrayList.get(indexMax));
    // Remove este item, para nao analisar ele de novo
    // arrayList.remove(indexMax);
    // }
    // Retorna uma ArrayList ordenada
    // return arrayListAux;
    // }
    // Este metodo tem como funcao criar uma ArrayList com todos os pontos
    // private static ArrayList<Double> preparePointsArrayList(double[] ds){
    // Cria uma ArrayList auxiliar
    // ArrayList<Double> arrayListAux = new ArrayList<Double>();
    // Varre o vetor...
    // for (int i = 0; i < ds.length; i++){
    // Transfere os dados para a ArrayList
    // arrayListAux.add(ds[i]);
    // }
    // Retorna uma ArrayList com os pontos
    // return arrayListAux;
    // }
}