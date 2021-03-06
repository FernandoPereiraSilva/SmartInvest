package Collection;

import java.util.Date;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.PurchaseHistoryDao;

/** Esta classe serve para tratar os dados das minhas acoes */
@MongoClass(className = "purchaseHistory")
public class PurchaseHistory {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private String             id;

    @MongoField(fieldName = "idStock")
    private String             idStock;

    @MongoField(fieldName = "idAccount")
    private String             idAccount;

    @MongoField(fieldName = "quantity")
    private Long               quantity;

    @MongoField(fieldName = "price")
    private Double             price;

    @MongoField(fieldName = "transactionType")
    private String             transactionType;

    @MongoField(fieldName = "sourceType")
    private String             sourceType;

    @MongoField(fieldName = "dtTransaction")
    private Date               dtTransaction;

    public static final String TRANSACTION_TYPE_BUY  = "Compra";
    public static final String TRANSACTION_TYPE_SELL = "Venda";
    public static final String SOURCE_TYPE_ROBOT     = "Rob�";

    /** Metodos contrutores */
    public PurchaseHistory(String id, String idStock, String idAccount, Long quantity, Double price, String transactionType, String sourceType, Date dtTransaction) {
        setId(id);
        setIdStock(idStock);
        setIdAccount(idAccount);
        setQuantity(quantity);
        setPrice(price);
        setTransactionType(transactionType);
        setSourceType(sourceType);
        setDtTransaction(dtTransaction);
    }

    public PurchaseHistory() {
    }

    /** Metodos modificadores */
    @MongoMethodSet(fieldName = "_id")
    public void setId(String id) {
        this.id = id;
    }

    @MongoMethodSet(fieldName = "idStock")
    public void setIdStock(String idStock) {
        this.idStock = idStock;
    }

    @MongoMethodSet(fieldName = "idAccount")
    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    @MongoMethodSet(fieldName = "quantity")
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @MongoMethodSet(fieldName = "price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @MongoMethodSet(fieldName = "transactionType")
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @MongoMethodSet(fieldName = "sourceType")
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @MongoMethodSet(fieldName = "dtTransaction")
    public void setDtTransaction(Date dtTransaction) {
        this.dtTransaction = dtTransaction;
    }

    /** Metodos de retorno */
    @MongoMethodGet(fieldName = "_id")
    public String getId() {
        return id;
    }

    @MongoMethodGet(fieldName = "idStock")
    public String getIdStock() {
        return idStock;
    }

    @MongoMethodGet(fieldName = "idAccount")
    public String getIdAccount() {
        return idAccount;
    }

    @MongoMethodGet(fieldName = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    @MongoMethodGet(fieldName = "price")
    public Double getPrice() {
        return price;
    }

    @MongoMethodGet(fieldName = "transactionType")
    public String getTransactionType() {
        return transactionType;
    }
    
    @MongoMethodGet(fieldName = "sourceType")
    public String getSourceType() {
        return sourceType;
    }
    
    @MongoMethodGet(fieldName = "dtTransaction")
    public Date getDtTransaction() {
        return dtTransaction;
    }

    /** Metodos DAO */
    // Este metodo tem como funcao inseir esse historico no banco de dados
    public void insert() throws Exception {
        // Insere no banco de dados as informacoes
        PurchaseHistoryDao.insert(this);
    }
}
