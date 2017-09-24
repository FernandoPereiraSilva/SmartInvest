package Robot;

/** Esta classe serve para gerar as populacoes do algoritmo genetico */
import java.util.ArrayList;
import java.util.List;

public class Population {

    /** Atributos */
    private ArrayList<Strategy> members;

    /** Metodos contrutores */
    public Population() {
        setMembers(new ArrayList<Strategy>());
    }

    /** Metodos modificadores */
    public void setMembers(ArrayList<Strategy> members) {
        this.members = members;
    }

    /** Metodos de retorno */
    public ArrayList<Strategy> getMembers() {
        return members;
    }

    /** Metodos principais */
    // Este metodo tem como funcao inicializar as geracoes, uma sempre diferente da outra
    public void initialize() {
        // Executa um loop baseado na quantidade de individuos desejada
        for(int i = 0; i < Config.getPopulationSize(); i++) {
            // Cria uma estrategia para esta populacao
            Strategy strategy = new Strategy();
            // Realiza a mutacao desta estrategia
            strategy.mutate();
            // Adiciona ela nos membros
            getMembers().add(strategy);
        }
    }

    // Este metodo tem como funcao evoluir a populacao
    public void evolve() {
        // Realiza uma ordenagem nos membros
        sort();
        // Cria uma outra lista com os melhores desta populacao (o tamanho desta lista e definida nas configuracoes)
        List<Strategy> members = getMembers().subList(getMembers().size() - Config.getBreedingPopulationSize(), getMembers().size());
        // Limpa os membros anteriores
        setMembers(new ArrayList<Strategy>());
        // Adiciona todos os membros de foram selecionados
        getMembers().addAll(members);
        // Executa um loop para adicionar os que foram excluidos
        for(int i = 0; i < (Config.getPopulationSize() - Config.getBreedingPopulationSize()); i++) {
            // Sorteia um membro dos selecionados para ser o parente 1
            Strategy parent1 = getMembers().get((int) (Math.random() * 3));
            // Sorteia um membro dos selecionados para ser o parente 2
            Strategy parent2 = getMembers().get((int) (Math.random() * 3));
            // Cria uma estrategia
            Strategy strategy = new Strategy();
            // Passa os genes dos dois selecionados para criar um membro novo
            strategy.initialize(parent1, parent2);
            // Adiciona ele nos membros
            getMembers().add(strategy);
        }
    }

    /** Metodos auxiliares */
    // Este metodo tem como funcao ordenar uma populacao
    private void sort() {
        // Cria uma lista para armazenar os membros
        ArrayList<Strategy> members = new ArrayList<Strategy>();
        // Enquanto existir membros na lista antiga
        while(getMembers().size() > 0) {
            // Cria um indice 0
            int index = 0;
            // Informa que o menor valor sera o valor do item 0
            double value = getMembers().get(0).getScore();
            // Faz um loop com os itens restantes
            for(int i = 1; i < getMembers().size(); i++) {
                // Verifica se o score deste item do loop e maior do que o que foi salvo anteriormente
                if(getMembers().get(i).getScore() < value) {
                    // Se for significa que este e o novo indice e o novo valor para analisar posteriormente
                    index = i;
                    value = getMembers().get(i).getScore();
                }
            }
            // Adiciona a lista dos novos membros o membro da lista anterior que foi elegido como o com o maior score
            members.add(getMembers().get(index));
            // Remove da lista anterior o membro
            getMembers().remove(index);
        }
        // Adiciona a lista da classe a nova lista ordenada
        setMembers(members);
    }
}
