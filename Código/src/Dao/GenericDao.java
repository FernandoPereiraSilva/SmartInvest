package Dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Annotation.MongoClass;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;

import Factory.ConnectionFactory;
import Util.Criteria;
import Util.OrderBy;
import Util.Util;

/** Esta classe serve para tratar de forma generica os acessos ao banco de dados */
public class GenericDao {

    /** Metodos principais */
    // Este metodo generico tem como funcao inserir um registro
    protected static String insert(Class<?> className, Object classForInsert) throws Exception {
        // Utiliza a refletion para pegar o nome da colecao que esta na anotacao
        String nmCollection = className.getAnnotation(MongoClass.class).className();
        // Cria um objeto de id para retornar
        String id = new ObjectId().toString();
        // Pega o client que esta no connectionFactory
        MongoClient mongoClient = ConnectionFactory.getClient();
        // Pega o banco de dados que esta no connectionFactory
        MongoDatabase mongoDatabase = ConnectionFactory.getDb(mongoClient);
        // Cria um objeto para poder pegar o _id de retorno
        Document document = new Document(Util.mapToBasicDBOObject(mappingData(classForInsert)));
        // Insere o id
        document.put("_id", id);
        // Pega a colecao e insere uma acao
        mongoDatabase.getCollection(nmCollection).insertOne(document);
        // Fecha o client
        mongoClient.close();
        // Retorna o identificador do objeto inserido
        return id;
    }

    // Este metodo generico tem como funcao retornar os dados do banco de dados
    protected static ArrayList<Object> select(Class<?> className, Criteria criteria, OrderBy orderBy) throws Exception {
        // Utiliza a refletion para pegar o nome da colecao que esta na anotacao
        String nmCollection = className.getAnnotation(MongoClass.class).className();
        // Pega o client que esta no connectionFactory
        MongoClient mongoClient = ConnectionFactory.getClient();
        // Pega o banco de dados que esta no connectionFactory
        MongoDatabase mongoDatabase = ConnectionFactory.getDb(mongoClient);
        // Cria um objeto para percorrer os resultados do banco
        FindIterable<Document> findIterable = null;
        // Verifica se a criteria esta nula
        if(criteria == null) {
            // Se estiver significa que nao existe um criterio de busca, entao verifica se existe um order by
            if(orderBy == null) {
                // Se estiver significa que nao existe um order by, entao pega a colecao e pega todos os dados dessa colecao
                findIterable = mongoDatabase.getCollection(nmCollection).find();
            }
            else {
                // Se não estiver significa que existe um order by, entao pega a colecao e pega todos os dados dessa colecao
                findIterable = mongoDatabase.getCollection(nmCollection).find().sort(orderBy.getOrderByCondition());
            }
        }
        else {
            // Se não estiver significa que existe um criterio de busca, entao verifica se existe um order by
            if(orderBy == null) {
                // Se estiver significa que nao existe um order by, entao pega a colecao e pega todos os dados dessa colecao
                findIterable = mongoDatabase.getCollection(nmCollection).find(criteria.getWhereCondition());
            }
            else {
                // Se não estiver significa que existe um order by, entao pega a colecao e pega todos os dados dessa colecao
                findIterable = mongoDatabase.getCollection(nmCollection).find(criteria.getWhereCondition()).sort(orderBy.getOrderByCondition());
            }
        }
        // Cria uma ArrayList para armazenar esses dados
        ArrayList<Object> arrayList = new ArrayList<Object>();
        // Verifica se o retorno nao e null
        if(findIterable != null) {
            // Se nao for, significa que pode realizar operacoes com ele, varre o retorno
            for(Document document : findIterable) {
                // Cria uma classe
                Constructor<?> contructor = className.getConstructor();
                // Cria uma instance desta classe
                Object classAux = contructor.newInstance();
                // Pega todos os metodos sets desta classe que estao mapeados com uma anotacao
                ArrayList<Method> methodsSet = Util.getSetMethod(classAux.getClass());
                // Varre todos os atributos do documento mongo
                for(String key : document.keySet()) {
                    // Varre todos os metodos sets
                    for(int i = 0; i < methodsSet.size(); i++) {
                        // Pega a anotacao deixada no metodo set
                        MongoMethodSet mongoMethodSet = methodsSet.get(i).getAnnotation(MongoMethodSet.class);
                        // Esta anotacao contem o nome do campo, verifica se ele e igual ao nome do objeto do mongo
                        if(mongoMethodSet.fieldName().equals(key)) {
                            // Verifica se o tipo do campo origem e long e o do mongo e int
                            if ((document.get(key) != null) && ((Integer.class).equals(document.get(key).getClass()))){
                                // Se for significa que a tratativa e diferente, entao salva ele de forma customizada
                                methodsSet.get(i).invoke(classAux, new Long("0" + document.get(key)));
                            }else{
                                // Se nao for significa que e este campo nao precisa de uma tratativa especial, entao salva ele
                                methodsSet.get(i).invoke(classAux, (methodsSet.get(i).getParameterTypes()[0]).cast(document.get(key)));
                            }
                            // remove este metodo da proxima validacao
                            methodsSet.remove(i);
                            // Para a analise de metodos sets e avanca pra o proximo campo do mongo
                            i = methodsSet.size() + 1;
                        }
                    }
                }
                // Adiciona essa conta na ArrayList
                arrayList.add(classAux);
            }
        }
        // Fecha o client
        mongoClient.close();
        // Retorna a ArrayList
        return arrayList;
    }

    // Este metodo generico tem como funcao alterar as informacoes no banco de dados
    protected static void update(Class<?> className, Object classForUpdate) throws Exception {
        // Cria um objeto para armazenar o conteudo do update
        BasicDBObject setBasicDBOObject = new BasicDBObject("$set", Util.mapToBasicDBOObject(mappingData(classForUpdate)));
        // Cria um objeto para fazer o where do update
        BasicDBObject idBasicDBObject = new BasicDBObject("_id", ((BasicDBObject) setBasicDBOObject.get("$set")).get("_id"));
        // Utiliza a refletion para pegar o nome da colecao que esta na anotacao
        String nmCollection = className.getAnnotation(MongoClass.class).className();
        // Pega o client que esta no connectionFactory
        MongoClient mongoClient = ConnectionFactory.getClient();
        // Pega o banco de dados que esta no connectionFactory
        MongoDatabase mongoDatabase = ConnectionFactory.getDb(mongoClient);
        // Pega a colecao e faz um update, ele passa o _id e os valores
        mongoDatabase.getCollection(nmCollection).updateOne(idBasicDBObject, setBasicDBOObject);
        // Fecha o client
        mongoClient.close();
    }

    // Este metodo generico tem como funcao deletar uma acao comprada
    public static void delete(Class<?> className, Object classForDelete) throws Exception {
        // Cria um objeto para armazenar o conteudo do delete
        BasicDBObject setBasicDBOObject = new BasicDBObject("$set", Util.mapToBasicDBOObject(mappingData(classForDelete)));
        // Cria um objeto para fazer o where do update
        BasicDBObject idBasicDBObject = new BasicDBObject("_id", ((BasicDBObject) setBasicDBOObject.get("$set")).get("_id"));
        // Utiliza a refletion para pegar o nome da colecao que esta na anotacao
        String nmCollection = className.getAnnotation(MongoClass.class).className();
        // Pega o client que esta no connectionFactory
        MongoClient mongoClient = ConnectionFactory.getClient();
        // Pega o banco de dados que esta no connectionFactory
        MongoDatabase mongoDatabase = ConnectionFactory.getDb(mongoClient);
        // Pega a colecao e deleta este registro
        mongoDatabase.getCollection(nmCollection).deleteOne(idBasicDBObject);
        // Fecha o client
        mongoClient.close();
    }

    /** Metodos auxiliares */
    // Este metodo tem como funcao mapear os dados da conta
    protected static Map<String, Object> mappingData(Object classWithData) throws Exception {
        // Cria um mapa para armazenar todos os valores
        Map<String, Object> mapData = new HashMap<String, Object>();
        // Cria uma lista com todos os gets relacionados ao banco de dados desta classe
        ArrayList<Method> methodsGet = Util.getGetMethod(classWithData.getClass());
        // Varre todos os metodos
        for(Method method : methodsGet) {
            // Pega a anotacao que foi cadastrada na classe
            MongoMethodGet mongoMethodGet = method.getAnnotation(MongoMethodGet.class);
            // Pega o valor que esta nesta classe
            Object get = method.invoke(classWithData);
            // Verifica se o objeto que deste metodo e o "_id" e se ele esta vazio (se estiver nesta condicao significa que
            // ta tentando fazer uma insercao, neste caso nao deve mandar o id ja que ele sera gerado automaticamente),
            // ou se o campo e diferente de "_id", entao mesmo se ele estiver vazio deve ser inserido no banco de dados
            if((get != null && mongoMethodGet.fieldName().equals("_id")) || !mongoMethodGet.fieldName().equals("_id")) {
                // Insere os valores no mapa
                mapData.put(mongoMethodGet.fieldName(), get);
            }
        }
        // Retorna o mapa
        return mapData;
    }
}
