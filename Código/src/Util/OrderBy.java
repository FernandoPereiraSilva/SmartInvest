package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;

/** Esta classe serve para criar orders by genericos */
public class OrderBy {

    /** Atributos */
    private ArrayList<Map<String, Integer>> arrayListObject;

    public static Integer ORDER_BY_ASC = 1;
    public static Integer ORDER_BY_DESC = -1;
    
    /** Metodos contrutores */
    public OrderBy() {
        setArrayListObject(new ArrayList<Map<String, Integer>>());
    }

    /** Metodos modificadores */
    public void setArrayListObject(ArrayList<Map<String, Integer>> arrayListObject) {
        this.arrayListObject = arrayListObject;
    }

    /** Metodos de retorno */
    public ArrayList<Map<String, Integer>> getArrayListObject() {
        return arrayListObject;
    }

    /** Metodos principais */
    // Este metodo generico tem como funcao adicionar um orders by a lista
    public void addOrderBy(String nmCondition, Integer order) throws Exception {
        // Cria um mapa que ira conter um mapa com todos os dados do order by
        Map<String, Integer> map = new HashMap<String, Integer>();
        // Insere no mapa os dados
        map.put(nmCondition, order);
        // Adiciona na lista existente de criterios
        getArrayListObject().add(map);
    }

    // Este metodo generico tem como funcao transformar os orders by cadastrados em uma condicao orders by
    public BasicDBObject getOrderByCondition() throws Exception {
        // Cria um objeto que armazenara a ordernação final
        BasicDBObject orderBy = new BasicDBObject();
        // Varre a lista de mapas pais cadastrados
        for(Map<String, Integer> map : getArrayListObject()) {
            // Varre a lista de mapas filhos para aquele mapa pai
            for(Object key : map.keySet()) {
                // Adiciona ao objeto order by ao valor
                orderBy.append(key.toString(), map.get(key));
                
            }
        }
        // Retorna o orderBy
        return orderBy;
    }
}
