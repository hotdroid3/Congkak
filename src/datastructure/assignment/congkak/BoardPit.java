package datastructure.assignment.congkak;

/**
 * Created by Chanan on 25/6/2017.
 */
public class BoardPit extends Pit {

    private static final int DEFAULT_ADD_VALUE = 1;
    private static final int REMOVE_ALL_SEED = 0;
    private static final int DEFAULT_NUM_SEED = 4;

    public BoardPit()
    {
        super(DEFAULT_NUM_SEED);
    }

    public BoardPit(int seed)
    {
        super(seed);
    }

    public void addSeed()
    {
        setNumOfSeeds(getNumOfSeeds() + DEFAULT_ADD_VALUE);
    }

    public void removeAllSeeds()
    {
        setNumOfSeeds(REMOVE_ALL_SEED);
    }

    public boolean isEmpty()
    {
        if(getNumOfSeeds() == REMOVE_ALL_SEED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
