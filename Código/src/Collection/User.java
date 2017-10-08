package Collection;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import Annotation.MongoClass;
import Annotation.MongoField;
import Annotation.MongoMethodGet;
import Annotation.MongoMethodSet;
import Dao.UserDao;
import Util.Criteria;

/** Esta classe serve para tratar os dados das contas */
@MongoClass(className = "user")
public class User {

    /** Atributos */
    @MongoField(fieldName = "_id")
    private String _id;

    @MongoField(fieldName = "profile")
    private Map<Object, Object> profile;

    /** Metodos contrutores */
    public User(String _id, Map<Object, Object> profile) {
        setId(_id);
        setProfile(profile);
    }

    public User() {
    }

    /** Metodos modificadores */
    @MongoMethodSet(fieldName = "_id")
    public void setId(String _id) {
        this._id = _id;
    }

    @MongoMethodSet(fieldName = "profile")
    public void setProfile(Map<Object, Object> profile) {
        this.profile = profile;
    }

    /** Metodos de retorno */
    @MongoMethodGet(fieldName = "_id")
    public String getId() {
        return _id;
    }

    @MongoMethodGet(fieldName = "profile")
    public Map<Object, Object> getProfile() {
        return profile;
    }

    /** Metodos DAO */
    // Este metodo tem como funcao inserir esta acao no banco de dados
    public void insert() throws Exception {
        // Insere a conta no banco de dados
        UserDao.insert(this);
    }

    // Este metodo tem como funcao consultar todas as contas no banco de dados
    public static ArrayList<User> select() throws Exception {
        // Faz um select no banco e retorna
        return UserDao.select(null);
    }

    // Este metodo tem como funcao alterar esta conta no banco de dados
    public void update() throws Exception {
        // Executa a alteracao no banco de dados
        UserDao.update(this);
    }

    /** Metodos principais */
    // Este metodo tem como funcao alterar realizar a compra de uma determinada acao em todas as contas
    public static void buyStock(Stock stock) throws Exception {
        // Pega todas as conas no banco de dados
        ArrayList<User> arrayListAccount = select();
        // Varre a lista de contas
        for(User User : arrayListAccount) {
            // Executa a compra para determinada acao de um determinado usuario
            stock = User.buy(stock);
        }
    }

    // Este metodo tem como funcao alterar realizar a venda de uma determinada acao em todas as contas
    public static void sellStock(Stock stock) throws Exception {
        // Pega todas as conas no banco de dados
        ArrayList<User> arrayListAccount = select();
        // Varre a lista de contas
        for(User User : arrayListAccount) {
            // Executa a venda de uma determinada acao de um determinado usuario
            stock = User.sell(stock);
        }
    }

    /** Metodos auxiliares */
    // Este metodo tem como funcao realizar todo o procedimento da compra
    private Stock buy(Stock stock) throws Exception {
        // Pega a cotacao do ultimo registro do historico
        double cotacao = stock.getLastStockHistory().getCotacao();
        // Pega o valor do balanco
        Double balance = ((getProfile().get("balance") == null) ? 0.0 : new Double(getProfile().get("balance") + ""));
        // O usuario so podera gastar metade do seu saldo nestas acoes (para nao gastar tudo em uma unica bolsa),
        // entao ele faz o calculo para saber a quantidade maxima que pode ser comprada
        long numberOfTrades = (long) ((((Double) balance) / 2) / cotacao);
        // Verifica se a quantidade esta maior que o total de acoes disponivel
        if(numberOfTrades > stock.getLastStockHistory().getNumeroDeAcoes()) {
            // Se estiver significa que o usuario em questao pode comprar tudo, entao altera a quantidade para o total de acoes
            numberOfTrades = stock.getLastStockHistory().getNumeroDeAcoes();
        }
        // O valor de balanco do usuario sera o valor que tinha subtraido do valor gasto nesta compra
        getProfile().put("balance", (((Double) balance) - (numberOfTrades * stock.getLastStockHistory().getCotacao())));
        // Verifica se foi feito alguma compra
        if(numberOfTrades > 0) {
            // Se tiver sido significa que pode debitar o salod e cadastrar as novas acoes, entao cria
            // um objeto contendo os dados da acao comprada, informa que a data de compra foi hoje
            MyStock myStock = new MyStock(null, stock.getId(), getId(), numberOfTrades, new Date(), null);
            // Altera o numero de acoes desta bolsa, informando que agora o saldo e o total menos a quantidade comprada
            stock.getLastStockHistory().setNumeroDeAcoes(stock.getLastStockHistory().getNumeroDeAcoes() - numberOfTrades);
            // Atualiza o historico
            stock.getLastStockHistory().update();
            // Insere o registro das minhas acoes
            myStock.insert();
            // Cria o historico a ser inserido
            PurchaseHistory purchaseHistory = new PurchaseHistory();
            purchaseHistory.setIdAccount(myStock.getIdAccount());
            purchaseHistory.setIdStock(myStock.getIdStock());
            purchaseHistory.setPrice(stock.getLastStockHistory().getCotacao());
            purchaseHistory.setQuantity(myStock.getQuantity());
            purchaseHistory.setTransactionType(PurchaseHistory.TRANSACTION_TYPE_BUY);
            purchaseHistory.setSourceType(PurchaseHistory.SOURCE_TYPE_ROBOT);
            purchaseHistory.setDtTransaction(new Date());
            // Insere o historico
            purchaseHistory.insert();
            // Faz um update deste usuario no banco, agora ira constar seu novo saldo
            update();
        }
        // Retorna a acao
        return stock;
    }

    // Este metodo tem como funcao realizar todo o procedimento da venda
    private Stock sell(Stock stock) throws Exception {
        // Cria um criterio para pesquisar no banco de dados
        Criteria criteriaMyStock = new Criteria();
        // Informa que deve ser pesquisado todas as acoes que estou tentando vender
        criteriaMyStock.addCriteria("idStock", stock, "getId");
        // Informa que deve ser pesquisado apenas as acoes deste usuario
        criteriaMyStock.addCriteria("idAccount", this, "getId");
        // Realiza a pesquisa no banco de dados, retornando se este usuario tem a acao em questao
        ArrayList<MyStock> arrayListMyStock = MyStock.select(criteriaMyStock);
        // Varre a lista de acoes compradas
        for(MyStock myStock : arrayListMyStock) {
            // Verifica se esta acao e a mesma da acao comprada
            if(stock.getId().equals(myStock.getIdStock())) {
                // Se for significa que deve ser vendida, entao pega o valor da ultima cotacao e multiplica pala quantidade,
                // isso ira retornar o valor total de lucro desta acao
                double newValue = myStock.getQuantity() * stock.getLastStockHistory().getCotacao();
                // Soma a quantidade vendida ao total de acoes deste dia
                stock.getLastStockHistory().setNumeroDeAcoes(stock.getLastStockHistory().getNumeroDeAcoes() + myStock.getQuantity());
                // Faz um update guardando os dados da acao
                stock.getLastStockHistory().update();
                // Altera o saldo deste usuario, informa que seu saldo sera o que ja tinha com o lucro obtido
                getProfile().put("balance", (((Double) getProfile().get("balance")) + newValue));
                // Realiza a alteracao deste usuario no banco de dados
                update();
                // Esta acao comprada foi vendida, entao deleta esta acao do banco de dados
                myStock.delete();
                // Cria o historico a ser inserido
                PurchaseHistory purchaseHistory = new PurchaseHistory();
                purchaseHistory.setIdAccount(myStock.getIdAccount());
                purchaseHistory.setIdStock(myStock.getIdStock());
                purchaseHistory.setPrice(stock.getLastStockHistory().getCotacao());
                purchaseHistory.setQuantity(myStock.getQuantity());
                purchaseHistory.setTransactionType(PurchaseHistory.TRANSACTION_TYPE_SELL);
                purchaseHistory.setSourceType(PurchaseHistory.SOURCE_TYPE_ROBOT);
                purchaseHistory.setDtTransaction(new Date());
                // Insere o historico
                purchaseHistory.insert();
            }
        }
        // Retorna a acao
        return stock;
    }
}
