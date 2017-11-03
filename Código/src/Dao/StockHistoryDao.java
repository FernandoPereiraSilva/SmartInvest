package Dao;

import java.util.ArrayList;

import Collection.StockHistory;
import Util.Criteria;
import Util.OrderBy;

/** Esta classe serve para tratar as informacoes no banco de dados */
public class StockHistoryDao extends GenericDao {

    /** Metodos principais */
    // Este metodo tem como funcao inserir um historico
    public static void insert(StockHistory stockHistory) throws Exception {
        // Insere na acao o _id retornado do banco
        stockHistory.setId(insert(StockHistory.class, stockHistory));
    }

    // Este metodo tem como funcao retornar o historico pela acao
    public static ArrayList<StockHistory> select(Criteria criteria, OrderBy orderBy) throws Exception {
        // Cria uma ArrayList para armazenar os dados
        ArrayList<StockHistory> arrayListStockHistory = new ArrayList<StockHistory>();
        // Executa o metodo generico de select
        ArrayList<Object> arrayListObject = select(StockHistory.class, criteria, orderBy);
        // Varre todo o retorno do metodo generico
        for(Object object : arrayListObject) {
            // Converte o retorno generico em retorno especifico
            arrayListStockHistory.add((StockHistory) object);
        }
        // Retorna os dados
        return arrayListStockHistory;
    }

    // Este metodo tem como funcao alterar as informacoes no banco de dados
    public static void update(StockHistory stockHistory) throws Exception {
        // Pega a colecao "StockHistory" e faz um update
        update(StockHistory.class, stockHistory);
    }
}
