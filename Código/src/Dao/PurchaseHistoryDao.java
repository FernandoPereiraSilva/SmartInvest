package Dao;

import Collection.PurchaseHistory;

/** Esta classe serve para tratar as informacoes no banco de dados */
public class PurchaseHistoryDao extends GenericDao {

    /** Metodos principais */
    // Este metodo tem como funcao inserir um historico
    public static void insert(PurchaseHistory purchaseHistory) throws Exception {
        // Insere na conta o _id retornado do banco
        purchaseHistory.setId(insert(PurchaseHistory.class, purchaseHistory));
    }
}
