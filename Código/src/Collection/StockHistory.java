package Collection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.StockHistoryDao;
import Util.Criteria;

/** Esta classe serve para tratar os dados das acoes */
@MongoClass(className = "StockHistory")
public class StockHistory {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private ObjectId id;

    @MongoField(fieldName = "idStock")
    private ObjectId idStock;

    @MongoField(fieldName = "valorDeMercado")
    private Long     valorDeMercado;

    @MongoField(fieldName = "valorDaFirma")
    private Long     valorDaFirma;

    @MongoField(fieldName = "volumeMedio")
    private Long     volumeMedio;

    @MongoField(fieldName = "numeroDeAcoes")
    private Long     numeroDeAcoes;

    @MongoField(fieldName = "ativo")
    private Long     ativo;

    @MongoField(fieldName = "disponibilidades")
    private Long     disponibilidades;

    @MongoField(fieldName = "ativoCirculante")
    private Long     ativoCirculante;

    @MongoField(fieldName = "divBruta")
    private Long     divBruta;

    @MongoField(fieldName = "divLiquida")
    private Long     divLiquida;

    @MongoField(fieldName = "patrimLiq")
    private Long     patrimLiq;

    @MongoField(fieldName = "receitaLiquida12")
    private Long     receitaLiquida12;

    @MongoField(fieldName = "receitaLiquida3")
    private Long     receitaLiquida3;

    @MongoField(fieldName = "ebit12")
    private Long     ebit12;

    @MongoField(fieldName = "ebit3")
    private Long     ebit3;

    @MongoField(fieldName = "lucroLiquido12")
    private Long     lucroLiquido12;

    @MongoField(fieldName = "lucroLiquido3")
    private Long     lucroLiquido3;

    @MongoField(fieldName = "cotacao")
    private Double   cotacao;

    @MongoField(fieldName = "minimo52Sem")
    private Double   minimo52Sem;

    @MongoField(fieldName = "maximo52Sem")
    private Double   maximo52Sem;

    @MongoField(fieldName = "pL")
    private Double   pL;

    @MongoField(fieldName = "pVP")
    private Double   pVP;

    @MongoField(fieldName = "pEBIT")
    private Double   pEBIT;

    @MongoField(fieldName = "pSR")
    private Double   pSR;

    @MongoField(fieldName = "pAtivos")
    private Double   pAtivos;

    @MongoField(fieldName = "pCapGiro")
    private Double   pCapGiro;

    @MongoField(fieldName = "evEbit")
    private Double   evEbit;

    @MongoField(fieldName = "giroAtivos")
    private Double   giroAtivos;

    @MongoField(fieldName = "lPA")
    private Double   lPA;

    @MongoField(fieldName = "vPA")
    private Double   vPA;

    @MongoField(fieldName = "liquidezCorr")
    private Double   liquidezCorr;

    @MongoField(fieldName = "divBrPatrim")
    private Double   divBrPatrim;

    @MongoField(fieldName = "dataDaUltimaCotacao")
    private Date     dataDaUltimaCotacao;

    @MongoField(fieldName = "ultimoBalancoProcessado")
    private Date     ultimoBalancoProcessado;

    @MongoField(fieldName = "pAtivCircLiq")
    private Double   pAtivCircLiq;

    @MongoField(fieldName = "divYield")
    private Double   divYield;

    @MongoField(fieldName = "cresRec")
    private Double   cresRec;

    @MongoField(fieldName = "margBruta")
    private Double   margBruta;

    @MongoField(fieldName = "margEbit")
    private Double   margEbit;

    @MongoField(fieldName = "margLiquida")
    private Double   margLiquida;

    @MongoField(fieldName = "ebitAtivo")
    private Double   ebitAtivo;

    @MongoField(fieldName = "roic")
    private Double   roic;

    @MongoField(fieldName = "roe")
    private Double   roe;

    @MongoField(fieldName = "points")
    private Double   points;

    private Double   velocity;

    /** Metodos contrutores */
    public StockHistory(ObjectId id, ObjectId idStock, String papel, String tipo, String empresa, String setor, String subsetor, Long valorDeMercado, Long valorDaFirma, Long volumeMedio, Long numeroDeAcoes, Long ativo, Long disponibilidades, Long ativoCirculante, Long divBruta, Long divLiquida, Long patrimLiq, Long receitaLiquida12, Long receitaLiquida3, Long ebit12, Long ebit3, Long lucroLiquido12, Long lucroLiquido3, Double cotacao, Double minimo52Sem, Double maximo52Sem, Double pL, Double pVP, Double pEBIT, Double pSR, Double pAtivos, Double pCapGiro, Double evEbit, Double giroAtivos, Double lPA, Double vPA, Double liquidezCorr, Double divBrPatrim, Date dataDaUltimaCotacao, Date ultimoBalancoProcessado, Double pAtivCircLiq, Double divYield, Double cresRec, Double margBruta, Double margEbit, Double margLiquida, Double ebitAtivo, Double roic, Double roe) {
        setId(id);
        setIdStock(idStock);
        setValorDeMercado(valorDeMercado);
        setValorDaFirma(valorDaFirma);
        setVolumeMedio(volumeMedio);
        setNumeroDeAcoes(numeroDeAcoes);
        setAtivo(ativoCirculante);
        setDisponibilidades(disponibilidades);
        setAtivoCirculante(ativoCirculante);
        setDivBruta(divBruta);
        setDivLiquida(divLiquida);
        setPatrimLiq(patrimLiq);
        setReceitaLiquida12(receitaLiquida12);
        setReceitaLiquida3(receitaLiquida3);
        setEbit12(ebit12);
        setEbit3(ebit3);
        setLucroLiquido12(lucroLiquido12);
        setLucroLiquido3(lucroLiquido3);
        setCotacao(cotacao);
        setMinimo52Sem(minimo52Sem);
        setMaximo52Sem(maximo52Sem);
        setpL(pL);
        setpVP(pVP);
        setpEBIT(pEBIT);
        setpSR(pSR);
        setpAtivos(pAtivos);
        setpCapGiro(pCapGiro);
        setEvEbit(evEbit);
        setGiroAtivos(giroAtivos);
        setlPA(lPA);
        setvPA(vPA);
        setLiquidezCorr(liquidezCorr);
        setDivBrPatrim(divBrPatrim);
        setDataDaUltimaCotacao(dataDaUltimaCotacao);
        setUltimoBalancoProcessado(ultimoBalancoProcessado);
        setpAtivCircLiq(pAtivCircLiq);
        setDivYield(divYield);
        setCresRec(cresRec);
        setMargBruta(margBruta);
        setMargEbit(margEbit);
        setMargLiquida(margLiquida);
        setEbitAtivo(ebitAtivo);
        setRoic(roic);
        setRoe(roe);
    }

    public StockHistory() {
    }

    /** Metodos modificadores */
    @MongoMethodSet(fieldName = "_id")
    public void setId(ObjectId id) {
        this.id = id;
    }

    @MongoMethodSet(fieldName = "idStock")
    public void setIdStock(ObjectId idStock) {
        this.idStock = idStock;
    }

    @MongoMethodSet(fieldName = "valorDeMercado")
    public void setValorDeMercado(Long valorDeMercado) {
        this.valorDeMercado = valorDeMercado;
    }

    @MongoMethodSet(fieldName = "valorDaFirma")
    public void setValorDaFirma(Long valorDaFirma) {
        this.valorDaFirma = valorDaFirma;
    }

    @MongoMethodSet(fieldName = "volumeMedio")
    public void setVolumeMedio(Long volumeMedio) {
        this.volumeMedio = volumeMedio;
    }

    @MongoMethodSet(fieldName = "numeroDeAcoes")
    public void setNumeroDeAcoes(Long numeroDeAcoes) {
        this.numeroDeAcoes = numeroDeAcoes;
    }

    @MongoMethodSet(fieldName = "ativo")
    public void setAtivo(Long ativo) {
        this.ativo = ativo;
    }

    @MongoMethodSet(fieldName = "disponibilidades")
    public void setDisponibilidades(Long disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    @MongoMethodSet(fieldName = "ativoCirculante")
    public void setAtivoCirculante(Long ativoCirculante) {
        this.ativoCirculante = ativoCirculante;
    }

    @MongoMethodSet(fieldName = "divBruta")
    public void setDivBruta(Long divBruta) {
        this.divBruta = divBruta;
    }

    @MongoMethodSet(fieldName = "divLiquida")
    public void setDivLiquida(Long divLiquida) {
        this.divLiquida = divLiquida;
    }

    @MongoMethodSet(fieldName = "patrimLiq")
    public void setPatrimLiq(Long patrimLiq) {
        this.patrimLiq = patrimLiq;
    }

    @MongoMethodSet(fieldName = "receitaLiquida12")
    public void setReceitaLiquida12(Long receitaLiquida12) {
        this.receitaLiquida12 = receitaLiquida12;
    }

    @MongoMethodSet(fieldName = "receitaLiquida3")
    public void setReceitaLiquida3(Long receitaLiquida3) {
        this.receitaLiquida3 = receitaLiquida3;
    }

    @MongoMethodSet(fieldName = "ebit12")
    public void setEbit12(Long ebit12) {
        this.ebit12 = ebit12;
    }

    @MongoMethodSet(fieldName = "ebit3")
    public void setEbit3(Long ebit3) {
        this.ebit3 = ebit3;
    }

    @MongoMethodSet(fieldName = "lucroLiquido12")
    public void setLucroLiquido12(Long lucroLiquido12) {
        this.lucroLiquido12 = lucroLiquido12;
    }

    @MongoMethodSet(fieldName = "lucroLiquido3")
    public void setLucroLiquido3(Long lucroLiquido3) {
        this.lucroLiquido3 = lucroLiquido3;
    }

    @MongoMethodSet(fieldName = "cotacao")
    public void setCotacao(Double cotacao) {
        this.cotacao = cotacao;
    }

    @MongoMethodSet(fieldName = "minimo52Sem")
    public void setMinimo52Sem(Double minimo52Sem) {
        this.minimo52Sem = minimo52Sem;
    }

    @MongoMethodSet(fieldName = "maximo52Sem")
    public void setMaximo52Sem(Double maximo52Sem) {
        this.maximo52Sem = maximo52Sem;
    }

    @MongoMethodSet(fieldName = "pL")
    public void setpL(Double pL) {
        this.pL = pL;
    }

    @MongoMethodSet(fieldName = "pVP")
    public void setpVP(Double pVP) {
        this.pVP = pVP;
    }

    @MongoMethodSet(fieldName = "pEBIT")
    public void setpEBIT(Double pEBIT) {
        this.pEBIT = pEBIT;
    }

    @MongoMethodSet(fieldName = "pSR")
    public void setpSR(Double pSR) {
        this.pSR = pSR;
    }

    @MongoMethodSet(fieldName = "pAtivos")
    public void setpAtivos(Double pAtivos) {
        this.pAtivos = pAtivos;
    }

    @MongoMethodSet(fieldName = "pCapGiro")
    public void setpCapGiro(Double pCapGiro) {
        this.pCapGiro = pCapGiro;
    }

    @MongoMethodSet(fieldName = "evEbit")
    public void setEvEbit(Double evEbit) {
        this.evEbit = evEbit;
    }

    @MongoMethodSet(fieldName = "giroAtivos")
    public void setGiroAtivos(Double giroAtivos) {
        this.giroAtivos = giroAtivos;
    }

    @MongoMethodSet(fieldName = "lPA")
    public void setlPA(Double lPA) {
        this.lPA = lPA;
    }

    @MongoMethodSet(fieldName = "vPA")
    public void setvPA(Double vPA) {
        this.vPA = vPA;
    }

    @MongoMethodSet(fieldName = "liquidezCorr")
    public void setLiquidezCorr(Double liquidezCorr) {
        this.liquidezCorr = liquidezCorr;
    }

    @MongoMethodSet(fieldName = "divBrPatrim")
    public void setDivBrPatrim(Double divBrPatrim) {
        this.divBrPatrim = divBrPatrim;
    }

    @MongoMethodSet(fieldName = "dataDaUltimaCotacao")
    public void setDataDaUltimaCotacao(Date dataDaUltimaCotacao) {
        this.dataDaUltimaCotacao = dataDaUltimaCotacao;
    }

    @MongoMethodSet(fieldName = "ultimoBalancoProcessado")
    public void setUltimoBalancoProcessado(Date ultimoBalancoProcessado) {
        this.ultimoBalancoProcessado = ultimoBalancoProcessado;
    }

    @MongoMethodSet(fieldName = "pAtivCircLiq")
    public void setpAtivCircLiq(Double pAtivCircLiq) {
        this.pAtivCircLiq = pAtivCircLiq;
    }

    @MongoMethodSet(fieldName = "divYield")
    public void setDivYield(Double divYield) {
        this.divYield = divYield;
    }

    @MongoMethodSet(fieldName = "cresRec")
    public void setCresRec(Double cresRec) {
        this.cresRec = cresRec;
    }

    @MongoMethodSet(fieldName = "margBruta")
    public void setMargBruta(Double margBruta) {
        this.margBruta = margBruta;
    }

    @MongoMethodSet(fieldName = "margEbit")
    public void setMargEbit(Double margEbit) {
        this.margEbit = margEbit;
    }

    @MongoMethodSet(fieldName = "margLiquida")
    public void setMargLiquida(Double margLiquida) {
        this.margLiquida = margLiquida;
    }

    @MongoMethodSet(fieldName = "ebitAtivo")
    public void setEbitAtivo(Double ebitAtivo) {
        this.ebitAtivo = ebitAtivo;
    }

    @MongoMethodSet(fieldName = "roic")
    public void setRoic(Double roic) {
        this.roic = roic;
    }

    @MongoMethodSet(fieldName = "roe")
    public void setRoe(Double roe) {
        this.roe = roe;
    }

    @MongoMethodSet(fieldName = "points")
    public void setPoints(Double points) {
        this.points = points;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    /** Metodos de retorno */
    @MongoMethodGet(fieldName = "_id")
    public ObjectId getId() {
        return id;
    }

    @MongoMethodGet(fieldName = "idStock")
    public ObjectId getIdStock() {
        return idStock;
    }

    @MongoMethodGet(fieldName = "valorDeMercado")
    public Long getValorDeMercado() {
        return valorDeMercado;
    }

    @MongoMethodGet(fieldName = "valorDaFirma")
    public Long getValorDaFirma() {
        return valorDaFirma;
    }

    @MongoMethodGet(fieldName = "volumeMedio")
    public Long getVolumeMedio() {
        return volumeMedio;
    }

    @MongoMethodGet(fieldName = "numeroDeAcoes")
    public Long getNumeroDeAcoes() {
        return numeroDeAcoes;
    }

    @MongoMethodGet(fieldName = "ativo")
    public Long getAtivo() {
        return ativo;
    }

    @MongoMethodGet(fieldName = "disponibilidades")
    public Long getDisponibilidades() {
        return disponibilidades;
    }

    @MongoMethodGet(fieldName = "ativoCirculante")
    public Long getAtivoCirculante() {
        return ativoCirculante;
    }

    @MongoMethodGet(fieldName = "divBruta")
    public Long getDivBruta() {
        return divBruta;
    }

    @MongoMethodGet(fieldName = "divLiquida")
    public Long getDivLiquida() {
        return divLiquida;
    }

    @MongoMethodGet(fieldName = "patrimLiq")
    public Long getPatrimLiq() {
        return patrimLiq;
    }

    @MongoMethodGet(fieldName = "receitaLiquida12")
    public Long getReceitaLiquida12() {
        return receitaLiquida12;
    }

    @MongoMethodGet(fieldName = "receitaLiquida3")
    public Long getReceitaLiquida3() {
        return receitaLiquida3;
    }

    @MongoMethodGet(fieldName = "ebit12")
    public Long getEbit12() {
        return ebit12;
    }

    @MongoMethodGet(fieldName = "ebit3")
    public Long getEbit3() {
        return ebit3;
    }

    @MongoMethodGet(fieldName = "lucroLiquido12")
    public Long getLucroLiquido12() {
        return lucroLiquido12;
    }

    @MongoMethodGet(fieldName = "lucroLiquido3")
    public Long getLucroLiquido3() {
        return lucroLiquido3;
    }

    @MongoMethodGet(fieldName = "cotacao")
    public Double getCotacao() {
        return cotacao;
    }

    @MongoMethodGet(fieldName = "minimo52Sem")
    public Double getMinimo52Sem() {
        return minimo52Sem;
    }

    @MongoMethodGet(fieldName = "maximo52Sem")
    public Double getMaximo52Sem() {
        return maximo52Sem;
    }

    @MongoMethodGet(fieldName = "pL")
    public Double getpL() {
        return pL;
    }

    @MongoMethodGet(fieldName = "pVP")
    public Double getpVP() {
        return pVP;
    }

    @MongoMethodGet(fieldName = "pEBIT")
    public Double getpEBIT() {
        return pEBIT;
    }

    @MongoMethodGet(fieldName = "pSR")
    public Double getpSR() {
        return pSR;
    }

    @MongoMethodGet(fieldName = "pAtivos")
    public Double getpAtivos() {
        return pAtivos;
    }

    @MongoMethodGet(fieldName = "pCapGiro")
    public Double getpCapGiro() {
        return pCapGiro;
    }

    @MongoMethodGet(fieldName = "evEbit")
    public Double getEvEbit() {
        return evEbit;
    }

    @MongoMethodGet(fieldName = "giroAtivos")
    public Double getGiroAtivos() {
        return giroAtivos;
    }

    @MongoMethodGet(fieldName = "lPA")
    public Double getlPA() {
        return lPA;
    }

    @MongoMethodGet(fieldName = "vPA")
    public Double getvPA() {
        return vPA;
    }

    @MongoMethodGet(fieldName = "liquidezCorr")
    public Double getLiquidezCorr() {
        return liquidezCorr;
    }

    @MongoMethodGet(fieldName = "divBrPatrim")
    public Double getDivBrPatrim() {
        return divBrPatrim;
    }

    @MongoMethodGet(fieldName = "dataDaUltimaCotacao")
    public Date getDataDaUltimaCotacao() {
        return dataDaUltimaCotacao;
    }

    @MongoMethodGet(fieldName = "ultimoBalancoProcessado")
    public Date getUltimoBalancoProcessado() {
        return ultimoBalancoProcessado;
    }

    @MongoMethodGet(fieldName = "pAtivCircLiq")
    public Double getpAtivCircLiq() {
        return pAtivCircLiq;
    }

    @MongoMethodGet(fieldName = "divYield")
    public Double getDivYield() {
        return divYield;
    }

    @MongoMethodGet(fieldName = "cresRec")
    public Double getCresRec() {
        return cresRec;
    }

    @MongoMethodGet(fieldName = "margBruta")
    public Double getMargBruta() {
        return margBruta;
    }

    @MongoMethodGet(fieldName = "margEbit")
    public Double getMargEbit() {
        return margEbit;
    }

    @MongoMethodGet(fieldName = "margLiquida")
    public Double getMargLiquida() {
        return margLiquida;
    }

    @MongoMethodGet(fieldName = "ebitAtivo")
    public Double getEbitAtivo() {
        return ebitAtivo;
    }

    @MongoMethodGet(fieldName = "roic")
    public Double getRoic() {
        return roic;
    }

    @MongoMethodGet(fieldName = "roe")
    public Double getRoe() {
        return roe;
    }

    @MongoMethodGet(fieldName = "points")
    public Double getPoints() {
        return points;
    }

    public Double getVelocity() {
        return velocity;
    }

    /** Metodos DAO */
    // Este metodo tem como funcao inserir um historico no banco de dados
    public void insert() throws Exception {
        StockHistoryDao.insert(this);
    }

    // Este metodo tem como funcao alterar esta acao no banco de dados
    public void update() throws Exception {
        StockHistoryDao.update(this);
    }

    // Este metodo tem como funcao consultar todo o historico de uma determinada acao
    public ArrayList<StockHistory> select(Criteria criteriaStockHistory) throws Exception {
        return StockHistoryDao.select(criteriaStockHistory);
    }

    /** Metodos auxiliares */
    // Este metodo tem como funcao definir se o valor da receita liquida e anual ou trimestral
    public void setReceitaLiquida(Long receitaLiquida) {
        // Verifica se o valor anual esta preenchido
        if(getReceitaLiquida12() == null) {
            // Se estiver igual a 0 significa que o valor recebido e anual
            setReceitaLiquida12(receitaLiquida);
            // Se não for, verifica se o valor trimestral esta preenchido
        }
        else if(getReceitaLiquida3() == null) {
            // Se estiver igual a 0 significa que o valor recebido e trimestral
            setReceitaLiquida3(receitaLiquida);
        }
    }

    // Este metodo tem como funcao definir se o valor do ebit e anual ou trimestral
    public void setEbit(Long ebit) {
        // Verifica se o valor anual esta preenchido
        if(getEbit12() == null) {
            // Se estiver igual a 0 significa que o valor recebido e anual
            setEbit12(ebit);
            // Se não for, verifica se o valor trimestral esta preenchido
        }
        else if(getEbit3() == null) {
            // Se estiver igual a 0 significa que o valor recebido e trimestral
            setEbit3(ebit);
        }
    }

    // Este metodo tem como funcao definir se o valor do lucro liquido e anual ou trimestral
    public void setLucroLiquido(Long lucroLiquido) {
        // Verifica se o valor anual esta preenchido
        if(getLucroLiquido12() == null) {
            // Se estiver igual a 0 significa que o valor recebido e anual
            setLucroLiquido12(lucroLiquido);
            // Se não for, verifica se o valor trimestral esta preenchido
        }
        else if(getLucroLiquido3() == null) {
            // Se estiver igual a 0 significa que o valor recebido e trimestral
            setLucroLiquido3(lucroLiquido);
        }
    }

    // Este metodo tem como funcao criar um mapa com todos os gets da classe
    public static Map<String, Map<String, Object>> mappingMethodsGet() throws Exception {
        // Cria o mapa de atributos
        Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
        // Insere os dados no mapa, o primeiro parametro e um metodo que retorna os metodos que irao de fato retornar os valores, para este
        // segundo metodo e passado os parametros: nome do metodo de acesso; o tipo do atributo, string, int, Long, etc... tambem e passado
        // o nome da classe responsavel por tratar estas informacoes, StockHistory
        map.put("getValorDeMercado", mappingAttributesGetAux("getValorDeMercado", StockHistory.class));
        map.put("getValorDaFirma", mappingAttributesGetAux("getValorDaFirma", StockHistory.class));
        map.put("getCotacao", mappingAttributesGetAux("getCotacao", StockHistory.class));
        map.put("getMinimo52Sem", mappingAttributesGetAux("getMinimo52Sem", StockHistory.class));
        map.put("getMaximo52Sem", mappingAttributesGetAux("getMaximo52Sem", StockHistory.class));
        map.put("getVolumeMedio", mappingAttributesGetAux("getVolumeMedio", StockHistory.class));
        map.put("getNumeroDeAcoes", mappingAttributesGetAux("getNumeroDeAcoes", StockHistory.class));
        map.put("getpL", mappingAttributesGetAux("getpL", StockHistory.class));
        map.put("getpVP", mappingAttributesGetAux("getpVP", StockHistory.class));
        map.put("getpEBIT", mappingAttributesGetAux("getpEBIT", StockHistory.class));
        map.put("getpSR", mappingAttributesGetAux("getpSR", StockHistory.class));
        map.put("getpAtivos", mappingAttributesGetAux("getpAtivos", StockHistory.class));
        map.put("getpCapGiro", mappingAttributesGetAux("getpCapGiro", StockHistory.class));
        map.put("getpAtivCircLiq", mappingAttributesGetAux("getpAtivCircLiq", StockHistory.class));
        map.put("getDivYield", mappingAttributesGetAux("getDivYield", StockHistory.class));
        map.put("getEvEbit", mappingAttributesGetAux("getEvEbit", StockHistory.class));
        map.put("getGiroAtivos", mappingAttributesGetAux("getGiroAtivos", StockHistory.class));
        map.put("getCresRec", mappingAttributesGetAux("getCresRec", StockHistory.class));
        map.put("getlPA", mappingAttributesGetAux("getlPA", StockHistory.class));
        map.put("getvPA", mappingAttributesGetAux("getvPA", StockHistory.class));
        map.put("getMargBruta", mappingAttributesGetAux("getMargBruta", StockHistory.class));
        map.put("getMargEbit", mappingAttributesGetAux("getMargEbit", StockHistory.class));
        map.put("getMargLiquida", mappingAttributesGetAux("getMargLiquida", StockHistory.class));
        map.put("getEbitAtivo", mappingAttributesGetAux("getEbitAtivo", StockHistory.class));
        map.put("getRoic", mappingAttributesGetAux("getRoic", StockHistory.class));
        map.put("getRoe", mappingAttributesGetAux("getRoe", StockHistory.class));
        map.put("getLiquidezCorr", mappingAttributesGetAux("getLiquidezCorr", StockHistory.class));
        map.put("getDivBrPatrim", mappingAttributesGetAux("getDivBrPatrim", StockHistory.class));
        map.put("getAtivo", mappingAttributesGetAux("getAtivo", StockHistory.class));
        map.put("getDisponibilidades", mappingAttributesGetAux("getDisponibilidades", StockHistory.class));
        map.put("getAtivoCirculante", mappingAttributesGetAux("getAtivoCirculante", StockHistory.class));
        map.put("getDivBruta", mappingAttributesGetAux("getDivBruta", StockHistory.class));
        map.put("getDivLiquida", mappingAttributesGetAux("getDivLiquida", StockHistory.class));
        map.put("getPatrimLiq", mappingAttributesGetAux("getPatrimLiq", StockHistory.class));
        map.put("getReceitaLiquida", mappingAttributesGetAux("getReceitaLiquida", StockHistory.class));
        map.put("getEbit", mappingAttributesGetAux("getEbit", StockHistory.class));
        map.put("getLucroLiquido", mappingAttributesGetAux("getLucroLiquido", StockHistory.class));
        return map;
    }

    // Este metodo tem como funcao criar um mapa com todos os sets da classe
    public static Map<String, Map<String, Object>> mappingMethodsSet() throws Exception {
        // Cria o mapa de atributos
        Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
        // Insere os dados no mapa, o primeiro parametro e um metodo que retorna os metodos que irao de fato armazenar esse valor, para este
        // segundo metodo e passado os parametros: nome do metodo modificador; nome do metodo que vai formatar o valor
        // recebido; o tipo do atributo, string, int, Long, etc... tambem e passado o nome da classe responsavel por
        // tratar estas informacoes, StockHistory
        map.put("setValorDeMercado", mappingAttributesSetAux("setValorDeMercado", "dataToLong", StockHistory.class, Long.class));
        map.put("setValorDaFirma", mappingAttributesSetAux("setValorDaFirma", "dataToLong", StockHistory.class, Long.class));
        map.put("setCotacao", mappingAttributesSetAux("setCotacao", "dataToDouble", StockHistory.class, Double.class));
        map.put("setMinimo52Sem", mappingAttributesSetAux("setMinimo52Sem", "dataToDouble", StockHistory.class, Double.class));
        map.put("setMaximo52Sem", mappingAttributesSetAux("setMaximo52Sem", "dataToDouble", StockHistory.class, Double.class));
        map.put("setVolumeMedio", mappingAttributesSetAux("setVolumeMedio", "dataToLong", StockHistory.class, Long.class));
        map.put("setNumeroDeAcoes", mappingAttributesSetAux("setNumeroDeAcoes", "dataToLong", StockHistory.class, Long.class));
        map.put("setpL", mappingAttributesSetAux("setpL", "dataToDouble", StockHistory.class, Double.class));
        map.put("setpVP", mappingAttributesSetAux("setpVP", "dataToDouble", StockHistory.class, Double.class));
        map.put("setpEBIT", mappingAttributesSetAux("setpEBIT", "dataToDouble", StockHistory.class, Double.class));
        map.put("setpSR", mappingAttributesSetAux("setpSR", "dataToDouble", StockHistory.class, Double.class));
        map.put("setpAtivos", mappingAttributesSetAux("setpAtivos", "dataToDouble", StockHistory.class, Double.class));
        map.put("setpCapGiro", mappingAttributesSetAux("setpCapGiro", "dataToDouble", StockHistory.class, Double.class));
        map.put("setpAtivCircLiq", mappingAttributesSetAux("setpAtivCircLiq", "dataToDouble", StockHistory.class, Double.class));
        map.put("setDivYield", mappingAttributesSetAux("setDivYield", "dataToDouble", StockHistory.class, Double.class));
        map.put("setEvEbit", mappingAttributesSetAux("setEvEbit", "dataToDouble", StockHistory.class, Double.class));
        map.put("setGiroAtivos", mappingAttributesSetAux("setGiroAtivos", "dataToDouble", StockHistory.class, Double.class));
        map.put("setCresRec", mappingAttributesSetAux("setCresRec", "dataToDouble", StockHistory.class, Double.class));
        map.put("setlPA", mappingAttributesSetAux("setlPA", "dataToDouble", StockHistory.class, Double.class));
        map.put("setvPA", mappingAttributesSetAux("setvPA", "dataToDouble", StockHistory.class, Double.class));
        map.put("setMargBruta", mappingAttributesSetAux("setMargBruta", "dataToDouble", StockHistory.class, Double.class));
        map.put("setMargEbit", mappingAttributesSetAux("setMargEbit", "dataToDouble", StockHistory.class, Double.class));
        map.put("setMargLiquida", mappingAttributesSetAux("setMargLiquida", "dataToDouble", StockHistory.class, Double.class));
        map.put("setEbitAtivo", mappingAttributesSetAux("setEbitAtivo", "dataToDouble", StockHistory.class, Double.class));
        map.put("setRoic", mappingAttributesSetAux("setRoic", "dataToDouble", StockHistory.class, Double.class));
        map.put("setRoe", mappingAttributesSetAux("setRoe", "dataToDouble", StockHistory.class, Double.class));
        map.put("setLiquidezCorr", mappingAttributesSetAux("setLiquidezCorr", "dataToDouble", StockHistory.class, Double.class));
        map.put("setDivBrPatrim", mappingAttributesSetAux("setDivBrPatrim", "dataToDouble", StockHistory.class, Double.class));
        map.put("setAtivo", mappingAttributesSetAux("setAtivo", "dataToLong", StockHistory.class, Long.class));
        map.put("setDisponibilidades", mappingAttributesSetAux("setDisponibilidades", "dataToLong", StockHistory.class, Long.class));
        map.put("setAtivoCirculante", mappingAttributesSetAux("setAtivoCirculante", "dataToLong", StockHistory.class, Long.class));
        map.put("setDivBruta", mappingAttributesSetAux("setDivBruta", "dataToLong", StockHistory.class, Long.class));
        map.put("setDivLiquida", mappingAttributesSetAux("setDivLiquida", "dataToLong", StockHistory.class, Long.class));
        map.put("setPatrimLiq", mappingAttributesSetAux("setPatrimLiq", "dataToLong", StockHistory.class, Long.class));
        map.put("setReceitaLiquida", mappingAttributesSetAux("setReceitaLiquida", "dataToLong", StockHistory.class, Long.class));
        map.put("setEbit", mappingAttributesSetAux("setEbit", "dataToLong", StockHistory.class, Long.class));
        map.put("setLucroLiquido", mappingAttributesSetAux("setLucroLiquido", "dataToLong", StockHistory.class, Long.class));
        return map;
    }

    // Este metodo tem como funcao mapear os metodos a serem executados, um metodo modificador e um metodo de formatacao
    private static Map<String, Object> mappingAttributesSetAux(String set, String format, Class<?> classOfSet, Class<?> formatClass) throws Exception {
        // Cria um map auxiliar
        Map<String, Object> mapAux = new LinkedHashMap<String, Object>();
        // Insere no mapa o metodo modificador
        mapAux.put("set", classOfSet.getMethod(set, formatClass));
        // Insere no mapa o metodo de formatacao
        mapAux.put("format", classOfSet.getMethod(format, String.class));
        // Informa qual a classe que ira tratar estas informacoes
        mapAux.put("class", classOfSet);
        // Retorna o mapa
        return mapAux;
    }

    // Este metodo tem como funcao mapear os metodos a serem executados, um metodo de acesso e a classe de acesso
    private static Map<String, Object> mappingAttributesGetAux(String get, Class<?> classOfGet) throws Exception {
        // Cria um map auxiliar
        Map<String, Object> mapAux = new LinkedHashMap<String, Object>();
        // Insere no mapa o metodo de acesso
        mapAux.put("get", classOfGet.getMethod(get));
        // Informa qual a classe que ira tratar estas informacoes
        mapAux.put("class", classOfGet);
        // Retorna o mapa
        return mapAux;
    }

    // Este metodo tem como funcao formatar
    public static String dataToString(String data) {
        // Retorna o valor que recebeu
        return data;
    }

    // Este metodo tem como funcao formatar
    public static Long dataToLong(String data) {
        // Verifica se o unico caractere e o -
        if(("-").equals(data.replaceAll(" ", ""))) {
            // Se for, ele limpa a String data
            data = "0";
        }
        // Retorna o valor recebido, mas formatado para Long
        return Long.parseLong(data.replaceAll("\\.", ""));
    }

    // Este metodo tem como funcao formatar
    public static Double dataToDouble(String data) {
        // Verifica se o unico caractere e o -
        if(("-").equals(data.replaceAll(" ", ""))) {
            // Se for, ele limpa a String data
            data = "0";
        }
        // Se tiver % significa que e uma porcentagem, ele converte o 30% para 0.3
        if(data.contains("%"))
            return Double.parseDouble(data.replaceAll("%", "").replaceAll(",", ".")) / 100.0;
        // Retorna o valor recebido, mas formatado para Double
        return Double.parseDouble(data.replaceAll("\\.", "").replace(",", "."));
    }

    // Este metodo tem como funcao formatar
    public static Date dataToDate(String data) throws ParseException {
        // Ele cria uma formatacao de data, transformando datas no formato "dd/MM/yyyy" em datas no java
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Retorna o valor recebido, mas formatado para date
        return simpleDateFormat.parse(data);
    }

    @Override
    // Este metodo tem como funcao verificar se determinado objeto e o mesmo que este objeto
    public boolean equals(Object obj) {
        // Cria um objeto para armazenar todas as validacoes
        boolean equals = true;
        // Verifica se o objeto e o mesmo ou se esta vazio
        equals = equals && (this == obj);
        equals = equals && (obj != null);
        equals = equals && (getClass() == obj.getClass());
        // Verifica se algum resultado deu falso
        if(!equals) {
            // Se deu significa que nao e o mesmo objeto, entao retorna falso
            return equals;
        }
        // Se for verdadeiro signifa que pode realizar a seguinte conversao sem dar erro
        StockHistory other = (StockHistory) obj;
        equals = equals && (getAtivo() == other.getAtivo());
        equals = equals && (getAtivoCirculante() == other.getAtivoCirculante());
        equals = equals && (getCotacao() == other.getCotacao());
        equals = equals && (getCresRec() == other.getCresRec());
        equals = equals && ((getDataDaUltimaCotacao() == null)) ? (other.getDataDaUltimaCotacao() == null) : (getDataDaUltimaCotacao().equals(other.getDataDaUltimaCotacao()));
        equals = equals && (getDisponibilidades() == other.getDisponibilidades());
        equals = equals && (getDivBrPatrim() == other.getDivBrPatrim());
        equals = equals && (getDivBruta() == other.getDivBruta());
        equals = equals && (getDivLiquida() == other.getDivLiquida());
        equals = equals && (getDivYield() == other.getDivYield());
        equals = equals && (getEbit12() == other.getEbit12());
        equals = equals && (getEbit3() == other.getEbit3());
        equals = equals && (getEbitAtivo() == other.getEbitAtivo());
        equals = equals && (getEvEbit() == other.getEvEbit());
        equals = equals && (getGiroAtivos() == other.getGiroAtivos());
        equals = equals && (getlPA() == other.getlPA());
        equals = equals && (getLiquidezCorr() == other.getLiquidezCorr());
        equals = equals && (getLucroLiquido12() == other.getLucroLiquido12());
        equals = equals && (getLucroLiquido3() == other.getLucroLiquido3());
        equals = equals && (getMargBruta() == other.getMargBruta());
        equals = equals && (getMargEbit() == other.getMargEbit());
        equals = equals && (getMargLiquida() == other.getMargLiquida());
        equals = equals && (getMaximo52Sem() == other.getMaximo52Sem());
        equals = equals && (getMinimo52Sem() == other.getMinimo52Sem());
        equals = equals && (getNumeroDeAcoes() == other.getNumeroDeAcoes());
        equals = equals && (getpAtivCircLiq() == other.getpAtivCircLiq());
        equals = equals && (getpAtivos() == other.getpAtivos());
        equals = equals && (getpCapGiro() == other.getpCapGiro());
        equals = equals && (getpEBIT() == other.getpEBIT());
        equals = equals && (getpL() == other.getpL());
        equals = equals && (getpSR() == other.getpSR());
        equals = equals && (getpVP() == other.getpVP());
        equals = equals && (getPatrimLiq() == other.getPatrimLiq());
        equals = equals && (getReceitaLiquida12() == other.getReceitaLiquida12());
        equals = equals && (getReceitaLiquida3() == other.getReceitaLiquida3());
        equals = equals && (getRoe() == other.getRoe());
        equals = equals && (getRoic() == other.getRoic());
        equals = equals && ((getUltimoBalancoProcessado() == null) ? (other.getUltimoBalancoProcessado() == null) : getUltimoBalancoProcessado().equals(other.getUltimoBalancoProcessado()));
        equals = equals && (getvPA() == other.getvPA());
        equals = equals && (getValorDaFirma() == other.getValorDaFirma());
        equals = equals && (getValorDeMercado() == other.getValorDeMercado());
        equals = equals && (getVolumeMedio() == other.getVolumeMedio());
        // Se retornar verdadeiro significa que todos os atributos sao iguais, se retornar falso significa que ocorreu uma alteracao
        // entao e um novo historico
        return equals;
    }

}
