package Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;

/** Esta classe serve para criar criterios genericos */
@SuppressWarnings("unchecked")
public class Criteria {

    /** Atributos */
    private ArrayList<Map<String, Object>> arrayListObject;

    /** Metodos contrutores */
    public Criteria() {
        setArrayListObject(new ArrayList<Map<String, Object>>());
    }

    /** Metodos modificadores */
    public void setArrayListObject(ArrayList<Map<String, Object>> arrayListObject) {
        this.arrayListObject = arrayListObject;
    }

    /** Metodos de retorno */
    public ArrayList<Map<String, Object>> getArrayListObject() {
        return arrayListObject;
    }

    /** Metodos principais */
    // Este metodo generico tem como funcao adicionar um criterio a lista
    public void addCriteria(String nameFieldDb, Object classTarget, String nameGet) throws Exception {
        // Cria um mapa que ira conter um mapa com todos os dados do criterio
        Map<String, Object> mapFather = new HashMap<String, Object>();
        // Cria um mapa com os dados do criterio
        Map<String, Object> mapChild = new HashMap<String, Object>();

        // Insere no mapa filho o metodo de get de uma determinada classe passada como parametro
        mapChild.put("get", ((Class<?>) classTarget.getClass()).getMethod(nameGet));
        // Insere no mapa filho a classe que este metodo deve ser executado
        mapChild.put("class", classTarget);

        // Insere no mapa pai o mapa que contem todos os dados necessarios para gerar um criterio generico
        mapFather.put(nameFieldDb, mapChild);
        // Adiciona na lista existente de criterios
        getArrayListObject().add(mapFather);
    }

    // Este metodo generico tem como funcao transformar os criterios cadastrados em uma condicao where
    public BasicDBObject getWhereCondition() throws Exception {
        // Cria um objeto que armazenara o criterio final
        BasicDBObject where = new BasicDBObject();
        // Varre a lista de mapas pais cadastrados
        for(Map<String, Object> map : getArrayListObject()) {
            // Varre a lista de mapas filhos para aquele mapa pai
            for(Object key : map.keySet()) {
                // Cria um objeto para armazenar o metodo get que tinha sido guardado antes
                Method methodGet = (Method) ((Map<String, Object>) map.get(key)).get("get");
                // Obetem o conteudo do campo
                Object value = methodGet.invoke(((Map<String, Object>) map.get(key)).get("class"));
                // Verifica se o objeto em questão e um mapa
                if (value.getClass() == HashMap.class){
                    // Se for significa que o metodo de construcao do where e diferente
                    HashMap<Object, Object> mapValue = (HashMap<Object, Object>) value;
                    // Itera todos os itens do mapa
                    for(Object keyValue : mapValue.keySet()) {
                        // Informa que a chave sera composta
                        key = key + "." + keyValue;
                        // Insere o valor da chave
                        value = mapValue.get(keyValue);
                        // Adiciona ao objeto where o valor
                        where.append(key.toString(), value);
                    }
                }else{
                    // Adiciona ao objeto where o nome do atributo presente no mapa pai e o valor do metodo get
                    where.append(key.toString(), value);
                }
            }
        }
        // Retorna o where
        return where;
    }
}
