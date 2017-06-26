package datastructure.assignment.congkak;

/**
 * Created by Chanan on 25/6/2017.
 */
public abstract class Pit {

    private int numOfSeeds;

    protected Pit(int seed)
    {
        setNumOfSeeds(seed);
    }

    public int getNumOfSeeds() {
        return this.numOfSeeds;
    }

    public void setNumOfSeeds(int numOfSeeds) {
        this.numOfSeeds = numOfSeeds;
    }
}
