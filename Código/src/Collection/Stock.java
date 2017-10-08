package Collection;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.StockDao;
import Util.Criteria;

/** Esta classe serve para tratar os dados das acoes */
@MongoClass(className = "stocks")
public class Stock {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private String                  id;

    @MongoField(fieldName = "paper")
    private String                  paper;

    @MongoField(fieldName = "type")
    private String                  type;

    @MongoField(fieldName = "company")
    private String                  company;

    @MongoField(fieldName = "sector")
    private String                  sector;

    @MongoField(fieldName = "subSector")
    private String                  subSector;

    private double                  points;

    private ArrayList<StockHistory> stockHistory;

    /** Metodos contrutores */
    public Stock(String id, String paper, String type, String company, String sector, String subSector, int points, ArrayList<StockHistory> stockHistory) {
        setId(id);
        setPaper(paper);
        setType(type);
        setCompany(company);
        setSector(sector);
        setSubSector(subSector);
        setPoints(points);
        setStockHistory(stockHistory);
    }

    public Stock() {
    }

    /** Metodos modificadores */
    @MongoMethodSet(fieldName = "_id")
    public void setId(String id) {
        this.id = id;
    }

    @MongoMethodSet(fieldName = "paper")
    public void setPaper(String paper) {
        this.paper = paper;
    }

    @MongoMethodSet(fieldName = "type")
    public void setType(String type) {
        this.type = type;
    }

    @MongoMethodSet(fieldName = "company")
    public void setCompany(String company) {
        this.company = company;
    }

    @MongoMethodSet(fieldName = "sector")
    public void setSector(String sector) {
        this.sector = sector;
    }

    @MongoMethodSet(fieldName = "subSector")
    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void setStockHistory(ArrayList<StockHistory> stockHistory) {
        this.stockHistory = stockHistory;
    }

    /** Metodos de retorno */
    @MongoMethodGet(fieldName = "_id")
    public String getId() {
        return id;
    }

    @MongoMethodGet(fieldName = "paper")
    public String getPaper() {
        return paper;
    }

    @MongoMethodGet(fieldName = "type")
    public String getType() {
        return type;
    }

    @MongoMethodGet(fieldName = "company")
    public String getCompany() {
        return company;
    }

    @MongoMethodGet(fieldName = "sector")
    public String getSector() {
        return sector;
    }

    @MongoMethodGet(fieldName = "subSector")
    public String getSubSector() {
        return subSector;
    }

    public double getPoints() {
        return points;
    }

    public ArrayList<StockHistory> getStockHistory() {
        return stockHistory;
    }

    /** Metodos principais */
    // Este metodo tem como funcao retornar um mapa contendo a acao e seu historico tendo recebido uma url de um site
    public static Map<Object, Object> getFromPage(String url) throws Exception {
        // Retorna a mineracao da pagina
        return Page.getFromPage(url);
    }

    // Este metodo tem como funcao verificar se existe uma diferenca de 20% entre a analise fundamentalista atual e a passada
    public boolean are20PercentDifferent(double previousPoints) {
        // Informa que a porcentagem aceitavel e 20
        double percentAccept = 20;
        // Pega a variacao aceitavel de acordo com a porcentagem
        double variation = (percentAccept * previousPoints) / 100;
        // Verifica se os pontos atuais esta fora desta variacao permitida
        if(getPoints() < (previousPoints - variation) || getPoints() > (previousPoints + variation)) {
            // Se estiver significa que precisa realizar os calculos de compra e venda novamente, entao retorna verdade
            return true;
        }
        // Retorna falso
        return false;
    }

    // Este metodo tem como funcao retornar o ultimo historico desta acao
    public StockHistory getLastStockHistory() {
        // Verifica se o hitorico esta nulo
        if(getStockHistory() == null) {
            // Se estiver significa que nao existe historico, entao retorna nulo
            return null;
        }
        // Se nao estiver significa que tem historico, entao verifica se o tamanho e 0
        else if(getStockHistory().size() == 0) {
            // Se for 0 significa que nao foi encontrado os registros, entao retorna nulo
            return null;
        }
        else {
            // Se nao cair nas outras verificacoes significa que tem historico e e maior que 0, entao retorna a ultima posicao
            return getStockHistory().get(getStockHistory().size() - 1);
        }
    }

    /** Metodos DAO */
    // Este metodo tem como funcao inserir esta acao no banco de dados
    public void insert() throws Exception {
        // Cria um criterio para consulta
        Criteria criteriaStock = new Criteria();
        // Insere este papel para consulta
        criteriaStock.addCriteria("paper", this, "getPaper");
        // Pega todas as acoes que tenham determinados dados
        ArrayList<Stock> arrayListStock = StockDao.select(criteriaStock);
        // Verifica se esta acao ja existe
        if(arrayListStock.size() == 0) {
            // Se nao existir, ele insere no banco de dados
            StockDao.insert(this);
        }
        else {
            // Se existir, ele pega apenas o id do primeiro registro
            setId(arrayListStock.get(0).getId());
        }
    }

    // Este metodo tem como funcao consultar as acoes no banco de dados
    public static ArrayList<Stock> select() throws Exception {
        // Pega todas as acoes presentes no banco de dados
        ArrayList<Stock> arrayListStock = StockDao.select(null);
        // Varre todas as acoes retornadas
        for(Stock stock : arrayListStock) {
            // Cria um historico de acoes
            StockHistory stockHistory = new StockHistory();
            // Insere o identificador desta acao
            stockHistory.setIdStock(stock.getId());
            // Cria um criterio de historico
            Criteria criteriaStockHistory = new Criteria();
            // Insere no criterio o identificador desta acao
            criteriaStockHistory.addCriteria("idStock", stockHistory, "getIdStock");
            // Insere nesta acao o historico retornado do banco de dados
            stock.setStockHistory(stockHistory.select(criteriaStockHistory));
        }
        // Retorna a lista de acoes
        return arrayListStock;
    }

    // Este metodo tem como funcao alterar esta acao no banco de dados
    public void update() throws Exception {
        // Altera esta acao no banco de dados
        StockDao.update(this);
    }

    @SuppressWarnings("static-access")
    /** Esta classe interna tem como funcao tratar os dados retornados da url */
    static class Page {

        /** Metodos principais */
        // Este metodo tem como funcao pegar os dados da url e tratar de acordo com os campos
        public static Map<Object, Object> getFromPage(String url) throws Exception {
            // Cria um objeto acao
            Stock stock = new Stock();
            // Cria um historia pra essa acao
            StockHistory stockHistory = new StockHistory();
            // Pega os campos que foram mapeados da classe acao
            Map<String, Map<String, Object>> map = mappingAttributes();
            // Pega o HTML de uma determinada pagina
            Document documento = Jsoup.connect(url).get();
            // Formata este HTML para pegar os dados
            documento = new Cleaner(new Whitelist().relaxed()).clean(documento);
            // Transforma todo o documento em uma String para poder minerar os dados
            String texto = documento.toString();
            // Enquanto tiver <span na pagina...
            while(texto.contains("<span")) {
                // O primeiro campo encontrado dentro da tag span sera a chave do mapa de atributos da classe acao
                String key = texto.substring(texto.indexOf("<span>") + 6, texto.indexOf("</span>"));
                // Retira a parte da key do HTML para poder analisar o resto
                texto = texto.substring(texto.indexOf("</span>") + 7, texto.length());
                // Verifica se restou algum span no HTML
                if(texto.contains("<span")) {
                    // Se tiver sobrado, significa que essa chave vai ter um valor, entao ele e pego na tag span
                    String value = texto.substring(texto.indexOf("<span>") + 6, texto.indexOf("</span>"));
                    // Verifica se este valor esta dentro de uma tag a, sendo um hyperlink
                    if(value.contains("<a")) {
                        // Se estiver, ele pega o texto desse hyperlink
                        value = value.substring(value.indexOf(">") + 1, value.indexOf("</a>"));
                    }
                    // Verifica se o mapa tem determinado atributo
                    if(map.containsKey(key)) {
                        // Pega qual a classe responsavel por determinado set que esta no mapa
                        Class<?> classOfSet = (Class<?>) map.get(key).get("class");
                        // Verifica se essa classe a a acao
                        if(classOfSet == Stock.class) {
                            // Ele pega o map que tinha sido colocado os metodos modificadores da classe acao, busca se existe
                            // algum atributo com o nome da key, se tiver ele executa ele passando o valor como parametro (
                            // este valor pode ser formatado de alguma forma especifica, essa formatacao tambem esta no map
                            // de atributos, ele chama essa formatacao que retorna o valor que sera inserido no atributo da classe
                            // das acoes)
                            Method methodSet = (Method) map.get(key).get("set");
                            Method methodFormat = (Method) map.get(key).get("format");
                            methodSet.invoke(stock, methodFormat.invoke(stock, value));
                        }
                        else if(classOfSet == StockHistory.class) {
                            // Ele pega o map que tinha sido colocado os metodos modificadores da classe acao, busca se existe
                            // algum atributo com o nome da key, se tiver ele executa ele passando o valor como parametro (
                            // este valor pode ser formatado de alguma forma especifica, essa formatacao tambem esta no map
                            // de atributos, ele chama essa formatacao que retorna o valor que sera inserido no atributo da classe
                            // das acoes)
                            Method methodSet = (Method) map.get(key).get("set");
                            Method methodFormat = (Method) map.get(key).get("format");
                            methodSet.invoke(stockHistory, methodFormat.invoke(stockHistory, value));
                        }
                    }
                }
            }
            // Cria um mapa para armazenar as duas classes de retorno
            Map<Object, Object> mapReturn = new HashMap<Object, Object>();
            // Insere a classe stock no mapa
            mapReturn.put("stock", stock);
            // Insere a classe stockHistory no mapa
            mapReturn.put("stockHistory", stockHistory);
            // Retorna o mapa
            return mapReturn;
        }

        /** Metodos auxiliares */
        // Este metodo tem como funcao criar um mapa com todos os atributos da classe acao, ele fala qual o campo do site e qual
        // o metodo modificador que deve ser executado para poder armazenar esse valor na classe
        private static Map<String, Map<String, Object>> mappingAttributes() throws Exception {
            // Cria o mapa de atributos
            Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
            // Insere os dados no mapa, o primeiro atributo e o nome do campo na pagina, exatamente do jeito que esta la,
            // o segundo parametro e um metodo que retorna os metodos que irao de fato armazenar esse valor, para este
            // segundo metodo e passado os parametros: nome do metodo modificador; nome do metodo que vai formatar o valor
            // recebido; o tipo do atributo, string, int, long, etc... tambem e passado o nome da classe responsavel por
            // tratar estas informacoes, Stock ou StockHistory
            map.put("Papel", mappingAttributesAux("setPaper", "dataToString", Stock.class, String.class));
            map.put("Tipo", mappingAttributesAux("setType", "dataToString", Stock.class, String.class));
            map.put("Empresa", mappingAttributesAux("setCompany", "dataToString", Stock.class, String.class));
            map.put("Setor", mappingAttributesAux("setSector", "dataToString", Stock.class, String.class));
            map.put("Subsetor", mappingAttributesAux("setSubSector", "dataToString", Stock.class, String.class));
            map.put("Valor de mercado", mappingAttributesAux("setValorDeMercado", "dataToLong", StockHistory.class, Long.class));
            map.put("Valor da firma", mappingAttributesAux("setValorDaFirma", "dataToLong", StockHistory.class, Long.class));
            map.put("Cotação", mappingAttributesAux("setCotacao", "dataToDouble", StockHistory.class, Double.class));
            map.put("Data últ cot", mappingAttributesAux("setDataDaUltimaCotacao", "dataToDate", StockHistory.class, Date.class));
            map.put("Min 52 sem", mappingAttributesAux("setMinimo52Sem", "dataToDouble", StockHistory.class, Double.class));
            map.put("Max 52 sem", mappingAttributesAux("setMaximo52Sem", "dataToDouble", StockHistory.class, Double.class));
            map.put("Vol $ méd (2m)", mappingAttributesAux("setVolumeMedio", "dataToLong", StockHistory.class, Long.class));
            map.put("Últ balanço processado", mappingAttributesAux("setUltimoBalancoProcessado", "dataToDate", StockHistory.class, Date.class));
            map.put("Nro. Ações", mappingAttributesAux("setNumeroDeAcoes", "dataToLong", StockHistory.class, Long.class));
            map.put("P/L", mappingAttributesAux("setpL", "dataToDouble", StockHistory.class, Double.class));
            map.put("P/VP", mappingAttributesAux("setpVP", "dataToDouble", StockHistory.class, Double.class));
            map.put("P/EBIT", mappingAttributesAux("setpEBIT", "dataToDouble", StockHistory.class, Double.class));
            map.put("PSR", mappingAttributesAux("setpSR", "dataToDouble", StockHistory.class, Double.class));
            map.put("P/Ativos", mappingAttributesAux("setpAtivos", "dataToDouble", StockHistory.class, Double.class));
            map.put("P/Cap. Giro", mappingAttributesAux("setpCapGiro", "dataToDouble", StockHistory.class, Double.class));
            map.put("P/Ativ Circ Liq", mappingAttributesAux("setpAtivCircLiq", "dataToDouble", StockHistory.class, Double.class));
            map.put("Div. Yield", mappingAttributesAux("setDivYield", "dataToDouble", StockHistory.class, Double.class));
            map.put("EV / EBIT", mappingAttributesAux("setEvEbit", "dataToDouble", StockHistory.class, Double.class));
            map.put("Giro Ativos", mappingAttributesAux("setGiroAtivos", "dataToDouble", StockHistory.class, Double.class));
            map.put("Cres. Rec (5a)", mappingAttributesAux("setCresRec", "dataToDouble", StockHistory.class, Double.class));
            map.put("LPA", mappingAttributesAux("setlPA", "dataToDouble", StockHistory.class, Double.class));
            map.put("VPA", mappingAttributesAux("setvPA", "dataToDouble", StockHistory.class, Double.class));
            map.put("Marg. Bruta", mappingAttributesAux("setMargBruta", "dataToDouble", StockHistory.class, Double.class));
            map.put("Marg. EBIT", mappingAttributesAux("setMargEbit", "dataToDouble", StockHistory.class, Double.class));
            map.put("Marg. Líquida", mappingAttributesAux("setMargLiquida", "dataToDouble", StockHistory.class, Double.class));
            map.put("EBIT / Ativo", mappingAttributesAux("setEbitAtivo", "dataToDouble", StockHistory.class, Double.class));
            map.put("ROIC", mappingAttributesAux("setRoic", "dataToDouble", StockHistory.class, Double.class));
            map.put("ROE", mappingAttributesAux("setRoe", "dataToDouble", StockHistory.class, Double.class));
            map.put("Liquidez Corr", mappingAttributesAux("setLiquidezCorr", "dataToDouble", StockHistory.class, Double.class));
            map.put("Div Br/ Patrim", mappingAttributesAux("setDivBrPatrim", "dataToDouble", StockHistory.class, Double.class));
            map.put("Ativo", mappingAttributesAux("setAtivo", "dataToLong", StockHistory.class, Long.class));
            map.put("Disponibilidades", mappingAttributesAux("setDisponibilidades", "dataToLong", StockHistory.class, Long.class));
            map.put("Ativo Circulante", mappingAttributesAux("setAtivoCirculante", "dataToLong", StockHistory.class, Long.class));
            map.put("Dív. Bruta", mappingAttributesAux("setDivBruta", "dataToLong", StockHistory.class, Long.class));
            map.put("Dív. Líquida", mappingAttributesAux("setDivLiquida", "dataToLong", StockHistory.class, Long.class));
            map.put("Patrim. Líq", mappingAttributesAux("setPatrimLiq", "dataToLong", StockHistory.class, Long.class));
            map.put("Receita Líquida", mappingAttributesAux("setReceitaLiquida", "dataToLong", StockHistory.class, Long.class));
            map.put("EBIT", mappingAttributesAux("setEbit", "dataToLong", StockHistory.class, Long.class));
            map.put("Lucro Líquido", mappingAttributesAux("setLucroLiquido", "dataToLong", StockHistory.class, Long.class));
            return map;
        }

        // Este metodo tem como funcao mapear os metodos a serem executados, um metodo modificador e um metodo de formatacao
        private static Map<String, Object> mappingAttributesAux(String set, String format, Class<?> classOfSet, Class<?> formatClass) throws Exception {
            // Cria um map auxiliar
            Map<String, Object> mapAux = new LinkedHashMap<String, Object>();
            // Insere no mapa o metodo modificador
            mapAux.put("set", classOfSet.getMethod(set, formatClass));
            // Insere no mapa o metodo de formatacao
            mapAux.put("format", Page.class.getMethod(format, String.class));
            // Informa qual a classe que ira tratar estas informacoes
            mapAux.put("class", classOfSet);
            // Retorna o mapa
            return mapAux;
        }

        // Este metodo tem como funcao formatar
        public static String dataToString(String data) {
            // Retorna o valor que recebeu
            return data;
        }

        // Este metodo tem como funcao formatar
        public static long dataToLong(String data) {
            // Verifica se o unico caractere e o -
            if(("-").equals(data.replaceAll(" ", ""))) {
                // Se for, ele limpa a String data
                data = "0";
            }
            // Retorna o valor recebido, mas formatado para long
            return Long.parseLong(data.replaceAll("\\.", ""));
        }

        // Este metodo tem como funcao formatar
        public static double dataToDouble(String data) {
            // Verifica se o unico caractere e o -
            if(("-").equals(data.replaceAll(" ", ""))) {
                // Se for, ele limpa a String data
                data = "0";
            }
            // Se tiver % significa que e uma porcentagem, ele converte o 30% para 0.3
            if(data.contains("%"))
                return Double.parseDouble(data.replaceAll("%", "").replaceAll(",", ".")) / 100.0;
            // Retorna o valor recebido, mas formatado para double
            return Double.parseDouble(data.replaceAll("\\.", "").replace(",", "."));
        }

        // Este metodo tem como funcao formatar
        public static Date dataToDate(String data) throws ParseException {
            // Ele cria uma formatacao de data, transformando datas no formato "dd/MM/yyyy" em datas no java
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            // Retorna o valor recebido, mas formatado para date
            return simpleDateFormat.parse(data);
        }
    }
}
