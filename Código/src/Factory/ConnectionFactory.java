package Factory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/** Esta classe serve para criar os dados da conexao, para centralizar tudo, permitindo uma facil manutencao */
public class ConnectionFactory {

    /** Metodos principais */
    // Este metodo tem como funcao retornar o client
    public static MongoClient getClient() {
        // Retorna o client
        return new MongoClient("127.0.0.1", 27017);
    }

    // Este metodo tem como funcao retornar o db
    public static MongoDatabase getDb(MongoClient mongoClient) {
        // Pega o banco de dados "meteor"
        return mongoClient.getDatabase("meteor");
    }
}
