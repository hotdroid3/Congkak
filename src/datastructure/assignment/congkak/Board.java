package datastructure.assignment.congkak;

import java.util.*;

/**
 * Created by Chanan on 25/6/2017.
 */
public class Board {

    private static final int DEFAULT_NUM_SEED = 4;
    private static final int DEFAUL_NUM_OF_PITS = 7;
    private static Board gameBoard;

    private List<List<BoardPit>> boardPitLists;
    private List<BoardPit> player_1_BoardPitList;
    private List<BoardPit> player_2_BoardPitList;
    private List<PlayerPit> playerPits;

    private Player player_1;
    private Player player_2;

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;
    private static final int EMPTY = 0;

    private Board()
    {

        //Create two arraylists for player 1 and player 2 board pits, and one boardlist to contain the two player's board pit list
        //Create one arraylist for player 1 and player 2 player pits

        setPlayer_1_BoardPitList(new ArrayList<BoardPit>());
        setPlayer_2_BoardPitList(new ArrayList<BoardPit>());
        setBoardPitLists(new ArrayList<List<BoardPit>>());
        setPlayerPits(new ArrayList<PlayerPit>());

    }

    public static Board getInstance()
    {
        if(Board.gameBoard == null)
        {
            Board.gameBoard = new Board();
        }
        gameBoard.setupGameBoard(DEFAUL_NUM_OF_PITS,DEFAULT_NUM_SEED);
        return Board.gameBoard;
    }

    public static Board getInstance(Player player_1, int size, int numOfSeeds)
    {
        if(Board.gameBoard == null)
        {
            Board.gameBoard = new Board();
        }
        gameBoard.setupGameBoard(size, numOfSeeds);
        gameBoard.setUpPlayers(player_1);
        return Board.gameBoard;
    }
    public static Board getInstance(Player player_1, Player player_2, int size, int numOfSeeds)
    {
        if(Board.gameBoard == null)
        {
            Board.gameBoard = new Board();
        }
        gameBoard.setupGameBoard(size, numOfSeeds);
        gameBoard.setUpPlayers(player_1,player_2);
        return Board.gameBoard;
    }

    public void setupGameBoard(int size, int numOfSeeds)
    {
        getPlayer_1_BoardPitList().clear();
        getPlayer_2_BoardPitList().clear();
        getBoardPitLists().clear();
        getPlayerPits().clear();
        setPlayer_1(null);
        setPlayer_2(null);

        getPlayerPits().add(PLAYER_1, new PlayerPit(EMPTY));
        getPlayerPits().add(PLAYER_2, new PlayerPit(EMPTY));

        for(int i = 0; i < size; i++)
        {
            getPlayer_1_BoardPitList().add(i, new BoardPit(numOfSeeds));
            getPlayer_2_BoardPitList().add(i, new BoardPit(numOfSeeds));
        }
        getBoardPitLists().add(PLAYER_1, getPlayer_1_BoardPitList());
        getBoardPitLists().add(PLAYER_2, getPlayer_2_BoardPitList());
    }
    public void setUpPlayers(Player player_1)
    {
        setPlayer_1(player_1);
        setPlayer_2(new Player("Computer"));
    }
    public void setUpPlayers(Player player_1, Player player_2)
    {
        setPlayer_1(player_1);
        setPlayer_2(player_2);
    }

    public void displayBoard()
    {
        System.out.println("\t\t" + getPlayer_1().getName() + ": " + getPlayerPits().get(PLAYER_1).getNumOfSeeds());
        System.out.print("\t");

        for(int i = getPlayer_1_BoardPitList().size(); i >= 1; i--){
            if(i < 10){
                System.out.print(i + "     ");
            }else
                System.out.print(i + "    ");
        }

        System.out.println("\n");
        System.out.print("\t");

        for(BoardPit pit: getPlayer_1_BoardPitList())
        {
            System.out.print(pit.getNumOfSeeds() + "     ");
        }

        System.out.println();
        System.out.print("\t");

        for(BoardPit pit: getPlayer_2_BoardPitList())
        {
            System.out.print(pit.getNumOfSeeds() + "     ");
        }

        System.out.println("\n");
        System.out.print("\t");

        for(int i = getPlayer_2_BoardPitList().size(); i >= 1; i--){
            if(i < 10){
                System.out.print(i + "     ");
            }else
                System.out.print(i + "    ");
        }


        System.out.println("\n\t\t" + getPlayer_2().getName() + ": " +  getPlayerPits().get(PLAYER_2).getNumOfSeeds());
    }

    public List<List<BoardPit>> getBoardPitLists() {
        return this.boardPitLists;
    }

    public void setBoardPitLists(List<List<BoardPit>> boardPitLists) {
        this.boardPitLists = boardPitLists;
    }

    public List<BoardPit> getPlayer_1_BoardPitList() {
        return this.player_1_BoardPitList;
    }

    public void setPlayer_1_BoardPitList(List<BoardPit> player_1_BoardPitList) {
        this.player_1_BoardPitList = player_1_BoardPitList;
    }

    public List<BoardPit> getPlayer_2_BoardPitList() {
        return this.player_2_BoardPitList;
    }

    public void setPlayer_2_BoardPitList(List<BoardPit> player_2_BoardPitList) {
        this.player_2_BoardPitList = player_2_BoardPitList;
    }

    public List<PlayerPit> getPlayerPits() {
        return this.playerPits;
    }

    public void setPlayerPits(List<PlayerPit> playerPits) {
        this.playerPits = playerPits;
    }

    public Player getPlayer_1() {
        return this.player_1;
    }

    public void setPlayer_1(Player player_1) {
        this.player_1 = player_1;
    }

    public Player getPlayer_2() {
        return this.player_2;
    }

    public void setPlayer_2(Player player_2) {
        this.player_2 = player_2;
    }
}
