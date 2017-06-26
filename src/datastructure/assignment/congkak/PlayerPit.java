package datastructure.assignment.congkak;

/**
 * Created by Chanan on 25/6/2017.
 */
public class PlayerPit extends Pit{

    private static final int DEFAULT_NUM_SEED = 0;

    public PlayerPit()
    {
        super(DEFAULT_NUM_SEED);
    }
    public PlayerPit(int seed)
    {
        super(seed);
    }

    public void addSeeds(int seeds)
    {
        setNumOfSeeds(getNumOfSeeds() + seeds);
    }

}
