package Robot;

/** Esta classe serve para armazenar as configuracoes dos parametros geneticos */
public class Config {

    /** Atributos */
    private static double mutationMaxRate;
    private static int    populationSize;
    private static int    breedingPopulationSize;
    private static double startingCash;
    private static double costPerTrade;
    private static double diversityBonus;
    private static int    numGenerations;
    private static double velocityDecay;

    /** Metodos modificadores */
    public static void setMutationMaxRate(double mutationMaxRate) {
        Config.mutationMaxRate = mutationMaxRate;
    }

    public static void setPopulationSize(int populationSize) {
        Config.populationSize = populationSize;
    }

    public static void setBreedingPopulationSize(int breedingPopulationSize) {
        Config.breedingPopulationSize = breedingPopulationSize;
    }

    public static void setStartingCash(double startingCash) {
        Config.startingCash = startingCash;
    }

    public static void setCostPerTrade(double costPerTrade) {
        Config.costPerTrade = costPerTrade;
    }

    public static void setDiversityBonus(double diversityBonus) {
        Config.diversityBonus = diversityBonus;
    }

    public static void setNumGenerations(int numGenerations) {
        Config.numGenerations = numGenerations;
    }

    public static void setVelocityDecay(double velocityDecay) {
        Config.velocityDecay = velocityDecay;
    }

    /** Metodos de retorno */
    public static double getMutationMaxRate() {
        return mutationMaxRate;
    }

    public static int getPopulationSize() {
        return populationSize;
    }

    public static int getBreedingPopulationSize() {
        return breedingPopulationSize;
    }

    public static double getStartingCash() {
        return startingCash;
    }

    public static double getCostPerTrade() {
        return costPerTrade;
    }

    public static double getDiversityBonus() {
        return diversityBonus;
    }

    public static int getNumGenerations() {
        return numGenerations;
    }

    public static double getVelocityDecay() {
        return velocityDecay;
    }
}
