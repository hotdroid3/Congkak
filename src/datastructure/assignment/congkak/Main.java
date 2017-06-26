package datastructure.assignment.congkak;

/**
 * Created by Chanan on 26/6/2017.
 */
public class Main {

    public static void main(String[] args) {
        Player player1 = new Player("Chanan");
        Player player2 = new Player("Eric");
        Board board = Board.getInstance(player1,player2, 7,2);
        BoardController boardController = BoardController.getInstance(board);


        boardController.executeTurn(6, player1);
        board.displayBoard();

        System.out.println("======================================================");
        boardController.executeTurn(5,player1);
        board.displayBoard();

    }
}
