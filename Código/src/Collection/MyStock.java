package Collection;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.MyStockDao;
import Util.Criteria;

/** Esta classe serve para tratar os dados das minhas acoes */
@MongoClass(className = "MyStock")
public class MyStock {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private ObjectId id;

    @MongoField(fieldName = "idStock")
    private ObjectId idStock;

    @MongoField(fieldName = "idAccount")
    private ObjectId idAccount;

    @MongoField(fieldName = "quantity")
    private Long     quantity;

    @MongoField(fieldName = "dateOfAlteration")
    private Date     dateOfAlteration;

    /** Metodos contrutores */
    public MyStock(ObjectId id, ObjectId idStock, ObjectId idAccount, Long quantity, Date dateOfAlteration) {
        setId(id);
        setIdStock(idStock);
        setIdAccount(idAccount);
        setQuantity(quantity);
        setDateOfAlteration(dateOfAlteration);
    }

    public MyStock() {
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

    @MongoMethodSet(fieldName = "idAccount")
    public void setIdAccount(ObjectId idAccount) {
        this.idAccount = idAccount;
    }

    @MongoMethodSet(fieldName = "quantity")
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @MongoMethodSet(fieldName = "dateOfAlteration")
    public void setDateOfAlteration(Date dateOfAlteration) {
        this.dateOfAlteration = dateOfAlteration;
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

    @MongoMethodGet(fieldName = "idAccount")
    public ObjectId getIdAccount() {
        return idAccount;
    }

    @MongoMethodGet(fieldName = "quantity")
    public Long getQuantity() {
        return quantity;
    }

    @MongoMethodGet(fieldName = "dateOfAlteration")
    public Date getDateOfAlteration() {
        return dateOfAlteration;
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

    // Este metodo tem como funcao deletar esta relacao no banco de dados
    public void delete() throws Exception {
        // Deleta este registro de compra no banco de dados
        MyStockDao.delete(this);
    }
}
