package datastructure.assignment.congkak;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


/**
 * Created by Chanan on 26/6/2017.
 */
public class Main extends Application{

    private int numOfPlayers;
    private int numberOfPits;
    private int numberOfSeeds;

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;
    private static final int WON = 0;
    private static final int LOSS = 1;
    private static final int DRAW = 2;
    private static final int RESET = 0;

    private boolean player1Turn = true;
    private Player player1 = null;
    private Player player2 = null;
    private Board gameBoard;
    private BoardController boardController;
    private Scene mainScene;
    private Label turnStatus;

    public static void main(String[] args) {

        launch(args);

    }
    @Override
    public void start(Stage primaryStage)
    {

        primaryStage.setTitle("Mancala Game");
        Main.this.gameBoard = Board.getInstance();
        Main.this.boardController = BoardController.getInstance(Main.this.gameBoard);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text welcome = new Text("Welcome to the Mancala Game!");
        welcome.setId("welcome");

        grid.add(welcome, 0, 0, 5, 1);

        Label numberOfPlayers = new Label("Number of players: (1-2)");
        grid.add(numberOfPlayers, 0, 3,4,1);

        TextField numPlayers = new TextField();
        grid.add(numPlayers, 4, 3,4,1);

        Label numOfSeeds = new Label("Number of seeds per pit: (4-9)");
        grid.add(numOfSeeds, 0, 5,4,1);

        TextField numSeeds = new TextField();
        grid.add(numSeeds, 4, 5,4,1);

        Label boardSize = new Label("Number of pits per player: (5-8)");
        grid.add(boardSize, 0, 6,4,1);

        TextField numOfPits = new TextField();
        grid.add(numOfPits, 4, 6,4,1);


        Button next = new Button("Start");
        next.setId("start");
        HBox hBoxNext = new HBox(10);
        hBoxNext.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxNext.getChildren().add(next);
        grid.add(hBoxNext, 6, 11);

        Text warningText = new Text();
        warningText.setId("warningText");
        grid.add(warningText,1,12);



        Runnable replayTask = new Runnable() {
            @Override
            public void run() {


                GridPane playAgain = new GridPane();
                playAgain.setAlignment(Pos.CENTER);
                playAgain.setHgap(10);
                playAgain.setVgap(10);
                playAgain.setPadding(new Insets(25, 25, 25, 25));
                Label player1Score = new Label("Player 1: " + gameBoard.getPlayerPits().get(PLAYER_1).getNumOfSeeds() + " seeds");
                String player_2_Score = null;
                if(player2.getName().equalsIgnoreCase("Computer"))
                {
                    player_2_Score = "Computer: " + gameBoard.getPlayerPits().get(PLAYER_2).getNumOfSeeds() + " seeds";
                }
                else
                {
                    player_2_Score = "Player 2: " + gameBoard.getPlayerPits().get(PLAYER_2).getNumOfSeeds() + " seeds";
                }
                Label player2Score = new Label(player_2_Score);
                String winnerText = null;
                int winner = boardController.isPlayerOneWinner();
                if(winner == WON)
                {
                    winnerText = "Player 1 Wins!";
                }
                else if(winner == DRAW)
                {
                    winnerText = "It's a draw!";
                }
                else if(winner == LOSS && player2.getName().equalsIgnoreCase("Computer"))
                {
                    winnerText = "Computer Wins";
                }
                else
                {
                    winnerText = "Player 2 Wins";
                }
                Label win = new Label(winnerText);
                win.setId("WinnerLabel");

                playAgain.add(win, 12,0);
                playAgain.add(player1Score,8,5);
                playAgain.add(player2Score,14,5);


                Label promptText = new Label("Do you want to play again?");
                promptText.setId("welcome");
                playAgain.add(promptText, 12, 10);
                Button startNewGame = new Button("Play Again!");
                startNewGame.setId("startNewGame");
                HBox hBoxStart = new HBox(10);
                hBoxStart.setAlignment(Pos.BOTTOM_CENTER);
                hBoxStart.getChildren().add(startNewGame);
                playAgain.add(hBoxStart, 12, 15);

                startNewGame.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        primaryStage.setScene(mainScene);
                        primaryStage.show();
                    }
                });

                Scene scene = new Scene(playAgain, 1700, 800);
                scene.getStylesheets().add
                        (Main.class.getResource("PlayAgain.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };


        Runnable task = new Runnable()
        {
            @Override
            public void run() {
                BorderPane rootBorderPane = new BorderPane();
                GridPane boardGridPane = new GridPane();
                boardGridPane.setAlignment(Pos.CENTER);
                boardGridPane.setHgap(10);
                boardGridPane.setVgap(10);
                boardGridPane.setPadding(new Insets(10, 10, 10, 10));
                //boardGridPane.setGridLinesVisible(true);
                Main.this.turnStatus = new Label("Player 1 Turn");

                HBox hBoxTurn = new HBox(10);
                hBoxTurn.setAlignment(Pos.TOP_CENTER);
                hBoxTurn.getChildren().add(turnStatus);

                Button player1Pit = new Button();
                Button player2Pit = new Button();
                player1Pit.setStyle("-fx-min-width:150px; -fx-min-height: 150px; -fx-max-width: 150px; -fx-max-height:150px; -fx-font:40 Montserrat; -fx-background-radius: 5em; -fx-pref-width: 150px; -fx-pref-height: 150px;");
                player1Pit.setText(" ");
                player2Pit.setStyle("-fx-min-width:150px; -fx-min-height: 150px; -fx-max-width: 150px; -fx-max-height:150px; -fx-font:40 Montserrat; -fx-background-radius: 5em; -fx-pref-width: 150px; -fx-pref-height: 150px;");
                player2Pit.setText(" ");

                VBox vBoxLeft = new VBox(10);
                vBoxLeft.setAlignment(Pos.CENTER);
                vBoxLeft.getChildren().add(player1Pit);

                VBox vBoxRight = new VBox(10);
                vBoxRight.setAlignment(Pos.CENTER);
                vBoxRight.getChildren().add(player2Pit);

                rootBorderPane.setTop(hBoxTurn);
                rootBorderPane.setLeft(vBoxLeft);
                rootBorderPane.setRight(vBoxRight);
                rootBorderPane.setPadding(new Insets(25));

                for(int i = 0; i < 2; i++)
                {
                    for(int j = 0; j < Main.this.getNumberOfPits(); j++)
                    {
                        Button button = new Button();
                        int seeds = gameBoard.getBoardPitLists().get(i).get(j).getNumOfSeeds();
                        button.setText(String.valueOf(seeds));
                        button.setId(String.valueOf(j));
                        button.setStyle("-fx-min-width:150px; -fx-min-height: 150px; -fx-max-width: 150px; -fx-max-height:150px; -fx-font:40 Montserrat; -fx-background-radius: 5em; -fx-pref-width: 150px; -fx-pref-height: 150px;");
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(player1Turn)
                                {
                                    if(!boardController.isPlayerBoardPitEmpty(player1))
                                    {
                                        boardController.executeTurn(Integer.parseInt(button.getId()),Main.this.player1);
                                        setPlayer1Turn(false);
                                        turnStatus.setText(isPlayer1Turn() ? "Player 1 Turn" : "Player 2 Turn");

                                        if(Main.this.player2.getName().equalsIgnoreCase("Computer"))
                                        {
                                            int pit = 0;
                                            int max = 0;
                                            List<BoardPit> list = gameBoard.getBoardPitLists().get(PLAYER_2);
                                            int size = list.size();

                                            for(int i = 0; i < size; i++)
                                            {
                                                if(max < list.get(i).getNumOfSeeds())
                                                {
                                                    max = list.get(i).getNumOfSeeds();
                                                    pit = i;
                                                }
                                            }
                                            Main.this.boardController.executeTurn(pit,Main.this.player2);
                                            setPlayer1Turn(true);
                                            turnStatus.setText(isPlayer1Turn() ? "Player 1 Turn" : "Player 2 Turn");
                                            if(boardController.isPlayerBoardPitEmpty(Main.this.player2))
                                            {
                                                reset();
                                                Platform.runLater(replayTask);
                                            }
                                        }

                                        if(boardController.isPlayerBoardPitEmpty(player1))
                                        {
                                            reset();
                                            Platform.runLater(replayTask);
                                        }
                                    }

                                }
                                else
                                {
                                    if(!boardController.isPlayerBoardPitEmpty(player2))
                                    {
                                        boardController.executeTurn(Integer.parseInt(button.getId()),Main.this.player2);
                                        setPlayer1Turn(true);
                                        turnStatus.setText(isPlayer1Turn() ? "Player 1 Turn" : "Player 2 Turn");
                                        if(boardController.isPlayerBoardPitEmpty(player2))
                                        {
                                            reset();
                                            Platform.runLater(replayTask);
                                        }
                                    }

                                }
                                refreshPits(boardGridPane, rootBorderPane);


                            }
                        });
                        boardGridPane.add(button, j,i);
                    }
                }

                rootBorderPane.setCenter(boardGridPane);
                refreshPits(boardGridPane,rootBorderPane);
                Scene scene = new Scene(rootBorderPane, 1700,800);
                scene.getStylesheets().add
                        (Main.class.getResource("Board.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();

            }
        };



        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String temp = numPlayers.getText();
                String temp1 = numSeeds.getText();
                String temp2 = numOfPits.getText();
                int numOfPlayers;
                int numberOfSeeds;
                int numberOfPits;
                try
                {
                    numOfPlayers = Integer.parseInt(temp);
                    numberOfSeeds = Integer.parseInt(temp1);
                    numberOfPits = Integer.parseInt(temp2);
                    if(!(numOfPlayers == 1 || numOfPlayers == 2))
                    {
                        throw new IllegalArgumentException("Please select 1 or 2 players!");
                    }
                    else{
                        setNumOfPlayers(numOfPlayers);
                    }
                    if(!(numberOfSeeds >= 4 && numberOfSeeds <= 9))
                    {
                        throw new IllegalArgumentException("Number of seeds in pit must be from 4-9!");
                    }
                    else
                    {
                        setNumberOfSeeds(numberOfSeeds);
                    }
                    if(!(numberOfPits >= 5 && numberOfPits <= 8))
                    {
                        throw new IllegalArgumentException("Number of pits per player should be from 5-8!");
                    }
                    else {
                        setNumberOfPits(numberOfPits);
                    }
                    if(numOfPlayers == 1)
                    {
                        Main.this.player1 = new Player("Player 1");
                        Main.this.player2 = new Player("Computer");

                    }
                    else if (numOfPlayers == 2)
                    {
                        Main.this.player1 = new Player("Player 1");
                        Main.this.player2 = new Player("Player 2");
                    }
                    Main.this.gameBoard = Board.getInstance(Main.this.player1,Main.this.player2, getNumberOfPits(),getNumberOfSeeds());
                    Main.this.boardController = BoardController.getInstance(Main.this.gameBoard);

                    Platform.runLater(task);
                }
                catch (NumberFormatException e)
                {
                    warningText.setText("Please enter a valid number in the range!");
                    //warningText.setFill(Color.FIREBRICK);
                }
                catch (IllegalArgumentException e)
                {
                    warningText.setText(e.getMessage());
                    //warningText.setFill(Color.FIREBRICK);
                }
            }
        });



        mainScene= new Scene(grid, 1700, 800);
        mainScene.getStylesheets().add
                (Main.class.getResource("Main.css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }



    public void refreshPits(GridPane layout,BorderPane root){
        ObservableList<Node> children = layout.getChildren();
        Button player1Pit = (Button) ((VBox)root.getLeft()).getChildren().get(0);
        Button player2Pit =(Button) ((VBox)root.getRight()).getChildren().get(0);
        int player1Seeds = gameBoard.getPlayerPits().get(PLAYER_1).getNumOfSeeds();
        int player2Seeds = gameBoard.getPlayerPits().get(PLAYER_2).getNumOfSeeds();
        player1Pit.setText(String.valueOf(player1Seeds));
        player2Pit.setText(String.valueOf(player2Seeds));
        int playerRow=0;
        for(Node node: children){
            if(node instanceof Button){
                Button button=(Button)node;
                playerRow=layout.getRowIndex(node);
                int seeds = gameBoard.getBoardPitLists().get(playerRow).get(Integer.parseInt(button.getId())).getNumOfSeeds();

                button.setText(String.valueOf(seeds));
                button.setDisable(false);

                if(isPlayer1Turn() && playerRow == 1){
                    button.setDisable(true);
                }else if(!isPlayer1Turn() && playerRow ==0){
                    button.setDisable(true);
                }

                if(seeds == 0){
                    button.setDisable(true);
                }
            }
        }
    }

    public void reset()
    {
        setPlayer1Turn(true);
    }


    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public int getNumberOfPits() {
        return this.numberOfPits;
    }

    public void setNumberOfPits(int numberOfPits) {
        this.numberOfPits = numberOfPits;
    }

    public int getNumberOfSeeds() {
        return this.numberOfSeeds;
    }

    public void setNumberOfSeeds(int numberOfSeeds) {
        this.numberOfSeeds = numberOfSeeds;
    }

    public boolean isPlayer1Turn() {
        return this.player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }
}
