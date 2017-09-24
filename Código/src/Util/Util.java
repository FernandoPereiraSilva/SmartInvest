package Util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.mongodb.BasicDBObject;

import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;

/** Esta classe serve para armazenar metodos utilitarios para todo o sistema */
public class Util {

    /** Metodos principais */
    // Este metodo tem como funcao formatar um double com x casas decimais
    public static double formatDecimalScale(double value, int scale) {
        // Cria um objeto novo contendo o valor original
        BigDecimal newValue = new BigDecimal(value);
        // Arredonda para cima em x casas decimais
        newValue.setScale(scale, BigDecimal.ROUND_UP);
        // Retorna o valor formatado
        return newValue.doubleValue();
    }

    // Este metodo tem como funcao transformar uma string em Date
    public static Date toDate(String date) {
        // cria uma funcao de try
        try {
            // Verifica se a string esta vazia
            if(date != null) {
                // Se nao estiver ele pode formatar, entao cria um formatter nulo
                SimpleDateFormat formatter = null;
                // Verifica se a string passada tem 19 caracteres
                if(date.length() == 19) {
                    // Se tiver ele faz a formatacao com horario completo
                    formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    // Se nao tiver ele verifica se a string passada tem 16 caracteres
                }
                else if(date.length() == 16) {
                    // Se tiver ele faz formatacao so com hora e minuto
                    formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    // Se nao tiver ele verifica se a string passada tem 10 caracteres
                }
                else if(date.length() == 10) {
                    // Se tiver ele faz a formatacao so com a data
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                }
                else {
                    // Se nao cair em nenhuma dessas validacoes entao nao tem essa data, retorna vazio
                    return null;
                }
                // Cria uma posicao de parse
                ParsePosition pos = new ParsePosition(0);
                // Retorna a data formatada
                return new Date(formatter.parse(date, pos).getTime());
            }
            // Retorna nulo
            return null;
        }
        catch(Exception e) {
            // Retorna nulo
            return null;
        }
    }

    // Este metodo tem como funcao converter um mapa em parametros para o banco de dados
    public static BasicDBObject mapToBasicDBOObject(Map<String, Object> map) {
        // Cria um objeto do mongo
        BasicDBObject basicDBObject = new BasicDBObject();
        // Varre o vetor de chaves
        for(String key : map.keySet()) {
            // Insere todos os valores do map no objeto do mongo
            basicDBObject.append(key, map.get(key));
        }
        // Retorna o objeto do mongo
        return basicDBObject;
    }

    // Este metodo tem como funcao pegar uma lista de metodos sets relacionados ao banco de dados
    public static ArrayList<Method> getSetMethod(Class<?> classSet) throws Exception {
        // Cria uma lista para armazenar os metodos finais
        ArrayList<Method> result = new ArrayList<Method>();
        // Enquanto a classe nao for nula
        while(classSet != null) {
            // Varre os metodos declarados desta classe
            for(Method method : classSet.getDeclaredMethods()) {
                // Verifica se eles tem a anotacao de metodo set relacionado ao mongo
                if(method.isAnnotationPresent(MongoMethodSet.class)) {
                    // Se tiver significa que este campo tem no banco de dados, entao pega os modificadores dele
                    int modifiers = method.getModifiers();
                    // Verifica se este metodo e publico
                    if(Modifier.isPublic(modifiers)) {
                        // Se for publico significa que pode ser utilizado fora da classe, entao adiciona na lista de metodos
                        result.add(method);
                    }
                }
            }
            // Ele pega a classe pai (caso tenha heranca), se tiver heranca ele vai cair no loop de novo e pegar todos os metodos da
            // classe pai, se ele nao tiver uma classe pai vai ser nulo e cai fora do loop
            classSet = classSet.getSuperclass();
        }
        // Retorna o resultado
        return result;
    }

    // Este metodo tem como funcao pegar uma lista de metodos gets relacionados ao banco de dados
    public static ArrayList<Method> getGetMethod(Class<?> classGet) {
        // Cria uma lista para armazenar os metodos finais
        ArrayList<Method> result = new ArrayList<Method>();
        // Enquanto a classe nao for nula
        while(classGet != null) {
            // Varre os metodos declarados desta classe
            for(Method method : classGet.getDeclaredMethods()) {
                // Verifica se eles tem a anotacao de metodo get relacionado ao mongo
                if(method.isAnnotationPresent(MongoMethodGet.class)) {
                    // Se tiver significa que este campo tem no banco de dados, entao pega os modificadores dele
                    int modifiers = method.getModifiers();
                    // Verifica se este metodo e publico
                    if(Modifier.isPublic(modifiers)) {
                        // Se for publico significa que pode ser utilizado fora da classe, entao adiciona na lista de metodos
                        result.add(method);
                    }
                }
            }
            // Ele pega a classe pai (caso tenha heranca), se tiver heranca ele vai cair no loop de novo e pegar todos os metodos da
            // classe pai, se ele nao tiver uma classe pai vai ser nulo e cai fora do loop
            classGet = classGet.getSuperclass();
        }
        // Retorna o resultado
        return result;
    }
}
