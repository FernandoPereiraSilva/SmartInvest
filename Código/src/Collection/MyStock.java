package Collection;

import java.util.ArrayList;
import java.util.Date;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.MyStockDao;
import Util.Criteria;

/** Esta classe serve para tratar os dados das minhas acoes */
@MongoClass(className = "myStocks")
public class MyStock {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private String id;

    @MongoField(fieldName = "idStock")
    private String idStock;

    @MongoField(fieldName = "idAccount")
    private String idAccount;

    @MongoField(fieldName = "quantity")
    private Long   quantity;

    @MongoField(fieldName = "dateBuy")
    private Date   dateBuy;

    @MongoField(fieldName = "dateSell")
    private Date   dateSell;

    /** Metodos contrutores */
    public MyStock(String id, String idStock, String idAccount, Long quantity, Date dateBuy, Date dateSell) {
        setId(id);
        setIdStock(idStock);
        setIdAccount(idAccount);
        setQuantity(quantity);
        setDateBuy(dateBuy);
        setDateSell(dateSell);
    }

    public MyStock() {
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

    @MongoMethodSet(fieldName = "dateBuy")
    public void setDateBuy(Date dateBuy) {
        this.dateBuy = dateBuy;
    }

    @MongoMethodSet(fieldName = "dateSell")
    public void setDateSell(Date dateSell) {
        this.dateSell = dateSell;
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

    @MongoMethodGet(fieldName = "dateBuy")
    public Date getDateBuy() {
        return dateBuy;
    }

    @MongoMethodGet(fieldName = "dateSell")
    public Date getDateSell() {
        return dateSell;
    }

    /** Metodos DAO */
    // Este metodo tem como funcao inseir essa relacao no banco de dados
    public void insert() throws Exception {
        // Insere no banco de dados as informacoes da compra
        MyStockDao.insert(this);
    }

    // Este metodo tem como funcao consultar todas as relacoes por acoes no banco de dados
    public static ArrayList<MyStock> select(Criteria criteria) throws Exception {
        // Consolta os dados no banco de dados e retorna
        return MyStockDao.select(criteria);
    }

    // Este metodo tem como funcao alterar esta relacao no banco de dados
    public void update() throws Exception {
        // Altera este registro de compra no banco de dados
        MyStockDao.update(this);
    }

    public void delete() throws Exception {
        // Deleta este registro de compra no banco de dados
        MyStockDao.delete(this);
    }
}
