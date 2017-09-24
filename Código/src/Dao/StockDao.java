package Dao;

import java.util.ArrayList;

import Collection.Stock;
import Util.Criteria;

/** Esta classe serve para tratar as informacoes no banco de dados */
public class StockDao extends GenericDao {

    /** Metodos principais */
    // Este metodo tem como funcao inserir uma acao
    public static void insert(Stock stock) throws Exception {
        // Insere na acao o _id retornado do banco
        stock.setId(insert(Stock.class, stock));
    }

    // Este metodo tem como funcao retornar todas as acoes
    public static ArrayList<Stock> select(Criteria criteria) throws Exception {
        // Cria uma ArrayList para armazenar os dados
        ArrayList<Stock> arrayListStock = new ArrayList<Stock>();
        // Executa o metodo generico de select
        ArrayList<Object> arrayListObject = select(Stock.class, criteria);
        // Varre todo o retorno do metodo generico
        for(Object object : arrayListObject) {
            // Converte o retorno generico em retorno especifico
            arrayListStock.add((Stock) object);
        }
        // Retorna os dados
        return arrayListStock;
    }

    // Este metodo tem como funcao alterar as informacoes no banco de dados
    public static void update(Stock stock) throws Exception {
        // Pega a colecao "Stock" e faz um update
        update(Stock.class, stock);
    }
}
