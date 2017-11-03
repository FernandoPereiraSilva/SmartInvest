import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import Collection.Log;
import Collection.Stock;
import Collection.StockHistory;
import Util.Criteria;

/** Esta classe serve para inserir as acoes */
public class WebMiner {

    /** Metodos principais */
    // Este metodo tem como funcao chamar os metodos de mineracao da internet e inserir as acoes
    public static void execute() throws Exception {
        // Cria um prefixo que armazenara o comeco de todos os sites utilizados no fundamentus
        String prefix = "http://fundamentus.com.br/detalhes.php?papel=";
        // Busca todas as acoes do banco de dados
        ArrayList<Stock> arrrayListStock = Stock.select();
        // Cria um vetor de sufixo que contera todas as acoes que seram inseridas
        String[] sufix = new String[arrrayListStock.size()];
        // Varre as acoes incluindo nos sufixos que serao procurados
        for (int i = 0; i < arrrayListStock.size(); i++){
            sufix[i] = arrrayListStock.get(i).getPaper();
        }
        // Varre o vetor de sufixo
        for(String aux : sufix) {
            // Pode ser que o administrador tenha inserido uma acao que nao exista, por isso o try
            try{
                // Minera os dados da pagina
                Map<Object, Object> map = Stock.getFromPage(prefix + aux);
                // Pega a acao que foi minerada
                Stock stock = (Stock) map.get("stock");
                // Pega o historico que foi minerado
                StockHistory stockHistory = (StockHistory) map.get("stockHistory");
                // Executa a funcao inserir, no fim ele analisa se esta acao ja existe, se existir, ela nao insere
                stock.insert();
                // Insere o id da acao no historico
                stockHistory.setIdStock(stock.getId());
                if(!wasInsertedBefore(stockHistory)) {
                    // Insere o historico no banco de dados
                    stockHistory.insert();
                }
            }
            // Este erro do administrador ocorre com um null pointer, por isso a tratativa em especifico
            catch (NullPointerException e) {
                // Cria o log de erro
                Log log = new Log();
                log.setIdAccount("");
                log.setSourceType(Log.SOURCE_TYPE_ROBOT);
                log.setDtLog(new Date());
                log.setDsLog("Caro administrador, foi inserida uma ação com um papel incorreto, por favor dê a devida tratativa, o papel é o " + aux);
                log.setTypeLog(Log.TYPE_LOG_ERROR);
                // Insere o log
                log.insert();
            }
        }
    }

    /** Metodos auxiliares */
    // Este metodo tem como funcao verificar se esse historico foi inserido antes
    private static boolean wasInsertedBefore(StockHistory stockHistory) throws Exception {
        // Cria um criterio de busca
        Criteria criteriaStockHistory = new Criteria();
        // Adiciona os dados no criterio
        criteriaStockHistory.addCriteria("idStock", stockHistory, "getIdStock");
        // Faz uma consulta no banco de dados
        ArrayList<StockHistory> arrayListStockHistory = stockHistory.select(criteriaStockHistory, null);
        // Varre todos os historicos
        for(StockHistory aux : arrayListStockHistory) {
            // Verifica se este historico e igual ao que esta tentando inserir
            if(stockHistory.equals(aux)) {
                // Se for ele retorna verdadeiro
                return true;
            }
        }
        // Retorna falso
        return false;
    }
}