package Dao;

import Collection.Log;

/** Esta classe serve para tratar as informacoes no banco de dados */
public class LogDao extends GenericDao {

    /** Metodos principais */
    // Este metodo tem como funcao inserir um historico
    public static void insert(Log log) throws Exception {
        // Insere na conta o _id retornado do banco
        log.setId(insert(Log.class, log));
    }
}
