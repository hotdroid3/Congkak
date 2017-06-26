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

    private Board gameBoard;

    private static BoardController boardController;

    private BoardController(Board board)
    {
        setGameBoard(board);
    }

    public static BoardController getInstance(Board board)
    {
        if(BoardController.boardController == null)
        {
            BoardController.boardController = new BoardController(board);
        }
        return BoardController.boardController;
    }

    public void distributeSeeds(int pit, Player player)
    {
        Player player1 = getGameBoard().getPlayer_1();
        Player player2 = getGameBoard().getPlayer_2();
        List<BoardPit> player_1_BoardPits = getGameBoard().getBoardPitLists().get(PLAYER_1);
        List<BoardPit> player_2_BoardPits = getGameBoard().getBoardPitLists().get(PLAYER_2);

        int takenSeeds = takeSeeds(pit, player);

        if(player == player1)
        {

            int pointer = pit - 1; //because we start distributing anti-clockwise on the next hole
            while(takenSeeds > 0)
            {
                for(int i = takenSeeds; i < )
                {

                }
            }
        }
        else if(player == player2)
        {

        }

    }

    public boolean isEven(int num)
    {
        if((num % 2) == 0)
        {
            return true;
        }
        else{
            return false;
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

    public boolean isBoardPitEmpty(Player player)
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
        if(player == getGameBoard().getPlayer_2())
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

}
