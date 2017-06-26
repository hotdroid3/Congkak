package datastructure.assignment.congkak;

import java.util.List;

/**
 * Created by Chanan on 26/6/2017.
 */
public class BoardController{

//    public static void main(String[] args) {
//        Board board = Board.getInstance(10,16);
//        board.displayBoard();
//        board = Board.getInstance();
//        board.displayBoard();
//    }

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;
    private static final int EMPTY = 0;
    private static final int PLAYER = 0;
    private static final int PIT = 1;

    private Board gameBoard;

    private int[] currentPit;

    private static BoardController boardController;

    private BoardController(Board board)
    {
        setGameBoard(board);
        int[] currentPit = {PLAYER_1, 0};
        setCurrentPit(currentPit);
    }

    public static BoardController getInstance(Board board)
    {
        if(BoardController.boardController == null)
        {
            BoardController.boardController = new BoardController(board);
        }
        return BoardController.boardController;
    }

    public void executeTurn(int pit, Player player)
    {
            distributeSeeds(pit, player);
            updateCurrentPit();
            scoreSeeds(player);
    }

    public boolean isGameOver(Player player)
    {
        boolean gameOver = false;
        if(!isPlayerBoardPitEmpty(player))
        {
            gameOver = false;
        }
        else
        {
            gameOver = true;
        }
        return gameOver;
    }

    public void scoreSeeds(Player player)
    {
        Board board = getGameBoard();
        Player player1 = board.getPlayer_1();
        Player player2 = board.getPlayer_2();
        List<BoardPit> player_1_BoardPits = getGameBoard().getBoardPitLists().get(PLAYER_1);
        List<BoardPit> player_2_BoardPits = getGameBoard().getBoardPitLists().get(PLAYER_2);

        int[] currentPit = getCurrentPit();
        int takenSeeds = 0;

        if(currentPit[PLAYER] == PLAYER_1)
        {
            takenSeeds = player_1_BoardPits.get(currentPit[PIT]).getNumOfSeeds();
            player_1_BoardPits.get(currentPit[PIT]).removeAllSeeds();

        }
        else if (currentPit[PLAYER] == PLAYER_2) {
            takenSeeds = player_2_BoardPits.get(currentPit[PIT]).getNumOfSeeds();
            player_2_BoardPits.get(currentPit[PIT]).removeAllSeeds();
        }
        if (player == player1) {
            board.getPlayerPits().get(PLAYER_1).addSeeds(takenSeeds);
        }
        else if(player == player2)
        {
            board.getPlayerPits().get(PLAYER_2).addSeeds(takenSeeds);
        }

    }

    public void updateCurrentPit()
    {
        int[] currentPit = getCurrentPit();
        if(currentPit[PLAYER] == PLAYER_1)
        {
            if(currentPit[PIT] == 0)
            {
                int[] updatedPit = getCurrentPit();
                updatedPit[PLAYER] = PLAYER_2;
                setCurrentPit(updatedPit);
            }
            else
            {
                int[] updatedPit = getCurrentPit();
                updatedPit[PIT] = updatedPit[PIT] - 1;
                setCurrentPit(updatedPit);
            }
        }
        else if(currentPit[PLAYER] == PLAYER_2)
        {
            if(currentPit[PIT] == getGameBoard().getBoardPitLists().get(PLAYER_2).size() - 1)
            {
                int[] updatedPit = getCurrentPit();
                updatedPit[PLAYER] = PLAYER_1;
                updatedPit[PIT] = getGameBoard().getBoardPitLists().get(PLAYER_1).size() - 1;
                setCurrentPit(updatedPit);
            }
            else
            {
                int[] updatedPit = getCurrentPit();
                updatedPit[PIT] = updatedPit[PIT] + 1;
                setCurrentPit(updatedPit);
            }
        }
    }
    public void updateCurrentPit(int pit, Player player)
    {
        if(player == getGameBoard().getPlayer_1())
        {
            int[] currentPit = {PLAYER_1, pit};
            setCurrentPit(currentPit);
        }
        else if(player == getGameBoard().getPlayer_2())
        {
            int[] currentPit = {PLAYER_2, pit};
            setCurrentPit(currentPit);
        }

    }

    public void distributeSeeds(int pit, Player player)
    {
        Player player1 = getGameBoard().getPlayer_1();
        Player player2 = getGameBoard().getPlayer_2();
        List<BoardPit> player_1_BoardPits = getGameBoard().getBoardPitLists().get(PLAYER_1);
        List<BoardPit> player_2_BoardPits = getGameBoard().getBoardPitLists().get(PLAYER_2);

        boolean finishedDistributing = false;
        int takenSeeds = takeSeeds(pit, player);
        updateCurrentPit(pit, player);
        if(takenSeeds == EMPTY)
        {
            finishedDistributing = true;
        }
        while(!finishedDistributing)
        {
            for (int i = 0; i < takenSeeds; i++)
            {
                updateCurrentPit();
                int[] currentPit = getCurrentPit();
                if (currentPit[PLAYER] == PLAYER_1)
                {
                    player_1_BoardPits.get(currentPit[PIT]).addSeed();
                }
                else if (currentPit[PLAYER] == PLAYER_2)
                {
                    player_2_BoardPits.get(currentPit[PIT]).addSeed();
                }
            }
            getGameBoard().displayBoard();
            updateCurrentPit();
            int[] currentPit = getCurrentPit();
            if (currentPit[PLAYER] == PLAYER_1)
            {
                takenSeeds = takeSeeds(currentPit[PIT], player1);
                if (!(takenSeeds > EMPTY))
                {
                    finishedDistributing = true;
                }
            }
            else if (currentPit[PLAYER] == PLAYER_2) {
                takenSeeds = takeSeeds(currentPit[PIT], player2);
                if (!(takenSeeds > EMPTY))
                {
                    finishedDistributing = true;
                }
            }
        }

    }

    public int takeSeeds(int pit, Player player)
    {
        Board board = getGameBoard();
        Player player1 = board.getPlayer_1();
        Player player2 = board.getPlayer_2();
        List<BoardPit> player_1_Pits = board.getBoardPitLists().get(PLAYER_1);
        List<BoardPit> player_2_Pits = board.getBoardPitLists().get(PLAYER_2);

        int takenSeeds = 0;

        if(player == player1)
        {
            //take seeds
            takenSeeds = player_1_Pits.get(pit).getNumOfSeeds();
            //remove seeds
            player_1_Pits.get(pit).setNumOfSeeds(EMPTY);
        }
        else if(player == player2)
        {
            //take seeds
            takenSeeds = player_2_Pits.get(pit).getNumOfSeeds();
            //remove seeds
            player_2_Pits.get(pit).setNumOfSeeds(EMPTY);
        }
        return takenSeeds;
    }

    public boolean isPlayerBoardPitEmpty(Player player)
    {
        boolean boardPitEmpty = true;
        if(player == getGameBoard().getPlayer_1())
        {
            List<BoardPit> playerPit = getGameBoard().getBoardPitLists().get(PLAYER_1);
            for(BoardPit pit: playerPit)
            {
                if(!pit.isEmpty())
                {
                   boardPitEmpty = false;
                }
            }
        }
        else if(player == getGameBoard().getPlayer_2())
        {
            List<BoardPit> playerPit = getGameBoard().getBoardPitLists().get(PLAYER_2);
            for(BoardPit pit: playerPit)
            {
                if(!pit.isEmpty())
                {
                    boardPitEmpty = false;
                }
            }
        }
        return boardPitEmpty;
    }


    public Board getGameBoard() {
        return this.gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int[] getCurrentPit() {
        return this.currentPit;
    }

    public void setCurrentPit(int[] currentPit) {
        this.currentPit = currentPit;
    }

}
