package Collection;

import java.util.Date;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.LogDao;

/** Esta classe serve para tratar os dados das minhas acoes */
@MongoClass(className = "log")
public class Log {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private String             id;

    @MongoField(fieldName = "idAccount")
    private String             idAccount;

    @MongoField(fieldName = "typeLog")
    private String             typeLog;

    @MongoField(fieldName = "dsLog")
    private String             dsLog;

    @MongoField(fieldName = "dtLog")
    private Date               dtLog;

    @MongoField(fieldName = "sourceType")
    private String             sourceType;

    public static final String TYPE_LOG_BUY      = "Compra de ação";
    public static final String TYPE_LOG_SELL     = "Venda de ação";
    public static final String TYPE_LOG_ERROR    = "Erro ao executar o robô";

    public static final String SOURCE_TYPE_ROBOT = "Robô";

    /** Metodos contrutores */
    public Log(String id, String idAccount, String typeLog, String dsLog, Date dtLog, String sourceType) {
        setId(id);
        setDsLog(dsLog);
        setDtLog(dtLog);
        setIdAccount(idAccount);
        setTypeLog(typeLog);
        setSourceType(sourceType);
    }

    public Log() {
    }

    /** Metodos modificadores */
    @MongoMethodSet(fieldName = "_id")
    public void setId(String id) {
        this.id = id;
    }

    @MongoMethodSet(fieldName = "dsLog")
    public void setDsLog(String dsLog) {
        this.dsLog = dsLog;
    }

    @MongoMethodSet(fieldName = "dtLog")
    public void setDtLog(Date dtLog) {
        this.dtLog = dtLog;
    }

    @MongoMethodSet(fieldName = "idAccount")
    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    @MongoMethodSet(fieldName = "typeLog")
    public void setTypeLog(String typeLog) {
        this.typeLog = typeLog;
    }

    @MongoMethodSet(fieldName = "sourceType")
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /** Metodos de retorno */
    @MongoMethodGet(fieldName = "_id")
    public String getId() {
        return id;
    }

    @MongoMethodGet(fieldName = "dsLog")
    public String getDsLog() {
        return dsLog;
    }

    @MongoMethodGet(fieldName = "idAccount")
    public String getIdAccount() {
        return idAccount;
    }

    @MongoMethodGet(fieldName = "dtLog")
    public Date getDtLog() {
        return dtLog;
    }

    @MongoMethodGet(fieldName = "typeLog")
    public String getTypeLog() {
        return typeLog;
    }

    @MongoMethodGet(fieldName = "sourceType")
    public String getSourceType() {
        return sourceType;
    }

    /** Metodos DAO */
    // Este metodo tem como funcao inseir esse historico no banco de dados
    public void insert() throws Exception {
        // Insere no banco de dados as informacoes
        LogDao.insert(this);
    }
}
