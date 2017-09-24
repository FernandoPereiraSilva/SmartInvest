package Dao;

import java.util.ArrayList;

import Collection.User;
import Util.Criteria;

/** Esta classe serve para tratar as informacoes no banco de dados */
public class UserDao extends GenericDao {

    /** Metodos principais */
    // Este metodo tem como funcao inserir uma conta
    public static void insert(User User) throws Exception {
        // Insere na conta o _id retornado do banco
        User.setId(insert(User.class, User));
    }

    // Este metodo tem como funcao retornar todas as contas do banco de dados
    public static ArrayList<User> select(Criteria criteria) throws Exception {
        // Cria uma ArrayList para armazenar os dados
        ArrayList<User> arrayListAccount = new ArrayList<User>();
        // Executa o metodo generico de select
        ArrayList<Object> arrayListObject = select(User.class, criteria);
        // Varre todo o retorno do metodo generico
        for(Object object : arrayListObject) {
            // Converte o retorno generico em retorno especifico
            arrayListAccount.add((User) object);
        }
        // Retorna os dados
        return arrayListAccount;
    }

    // Este metodo tem como funcao alterar uma determinada conta
    public static void update(User User) throws Exception {
        // Pega a colecao "Account" e faz um update
        update(User.class, User);
    }
}
