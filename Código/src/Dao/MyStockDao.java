package Dao;

import java.util.ArrayList;

import Collection.MyStock;
import Util.Criteria;

/** Esta classe serve para tratar as informacoes no banco de dados */
public class MyStockDao extends GenericDao {

    /** Metodos principais */
    // Este metodo tem como funcao inserir uma acao para um cliente
    public static void insert(MyStock myStock) throws Exception {
        // Insere na conta o _id retornado do banco
        myStock.setId(insert(MyStock.class, myStock));
    }

    // Este metodo tem como funcao retornar quem tem determinada acao comprada
    public static ArrayList<MyStock> select(Criteria criteria) throws Exception {
        // Cria uma ArrayList para armazenar os dados
        ArrayList<MyStock> arrayListStock = new ArrayList<MyStock>();
        // Executa o metodo generico de select
        ArrayList<Object> arrayListObject = select(MyStock.class, criteria, null);
        // Varre todo o retorno do metodo generico
        for(Object object : arrayListObject) {
            // Converte o retorno generico em retorno especifico
            arrayListStock.add((MyStock) object);
        }
        // Retorna os dados
        return arrayListStock;
    }

    // Este metodo tem como funcao alterar uma acao comprada
    public static void update(MyStock myStock) throws Exception {
        // Pega a colecao "MyStock" e faz um update
        update(MyStock.class, myStock);
    }

    // Este metodo tem como funcao deletar uma acao comprada
    public static void delete(MyStock myStock) throws Exception {
        // Pega a colecao "MyStock" e faz um delete
        delete(MyStock.class, myStock);
    }
}
