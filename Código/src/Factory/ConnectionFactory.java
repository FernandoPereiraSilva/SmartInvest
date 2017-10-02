package Factory;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

/** Esta classe serve para criar os dados da conexao, para centralizar tudo, permitindo uma facil manutencao */
public class ConnectionFactory {

    /** Metodos principais banco real */
    // Este metodo tem como funcao retornar o client
    public static MongoClient getClient() {
        // Retorna o client
        return new MongoClient(new ServerAddress("ds141434.mlab.com", 41434), Arrays.asList(MongoCredential.createCredential("admin", "tccusjt", "admin123".toCharArray())));
    }

    // Este metodo tem como funcao retornar o db
    public static MongoDatabase getDb(MongoClient mongoClient) {
        // Pega o banco de dados "meteor"
        return mongoClient.getDatabase("tccusjt");
    }
}
