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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by Chanan on 26/6/2017.
 */
public class Main extends Application{

    private int numOfPlayers;
    private int numberOfPits;
    private int numberOfSeeds;

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;

    private boolean player1Turn = true;
    private Player player1 = null;
    private Player player2 = null;
    private Board gameBoard;
    private BoardController boardController;


    public static void main(String[] args) {

        launch(args);



//
//        boardController.executeTurn(6, player1);
//        board.displayBoard();
//
//        System.out.println("======================================================");
//        boardController.executeTurn(5,player1);
//        board.displayBoard();

    }
    @Override
    public void start(Stage primaryStage)
    {
        //Parent root = null;
        primaryStage.setTitle("Mancala Game");
        Main.this.gameBoard = Board.getInstance();
        Main.this.boardController = BoardController.getInstance(Main.this.gameBoard);

//        try
//        {
//            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//        }
//        catch (IOException e)
//        {
//            System.out.println(e.getMessage());
//        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Text welcome = new Text("Welcome to the Mancala Game!");
        welcome.setId("welcome");
//        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(welcome, 0, 0, 5, 1);

        Label numberOfPlayers = new Label("Number of players: (1-2)");
        grid.add(numberOfPlayers, 0, 1,4,1);

        TextField numPlayers = new TextField();
        grid.add(numPlayers, 4, 1,4,1);

        Label numOfSeeds = new Label("Number of seeds per pit: (4-9)");
        grid.add(numOfSeeds, 0, 2,4,1);

        TextField numSeeds = new TextField();
        grid.add(numSeeds, 4, 2,4,1);

        Label boardSize = new Label("Number of pits per player: (7-10)");
        grid.add(boardSize, 0, 3,4,1);

        TextField numOfPits = new TextField();
        grid.add(numOfPits, 4, 3,4,1);


        Button next = new Button("Start");
        HBox hBoxNext = new HBox(10);
        hBoxNext.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxNext.getChildren().add(next);
        grid.add(hBoxNext, 8, 7);

        Text warningText = new Text();
        warningText.setId("warningText");
        grid.add(warningText,1,9);


        Runnable task = new Runnable()
        {
            @Override
            public void run() {
                BorderPane rootBorderPane = new BorderPane();
                GridPane boardGridPane = new GridPane();
                boardGridPane.setAlignment(Pos.CENTER);
                boardGridPane.setHgap(10);
                boardGridPane.setVgap(10);
                boardGridPane.setPadding(new Insets(25, 25, 25, 25));
                boardGridPane.setGridLinesVisible(true);

                for(int i = 0; i < 2; i++)
                {
                    for(int j = 0; j < Main.this.getNumberOfPits(); j++)
                    {
                        Button button = new Button();
                        int seeds = gameBoard.getBoardPitLists().get(i).get(j).getNumOfSeeds();
                        button.setText(String.valueOf(seeds));
                        button.setId(String.valueOf(j));
                        button.setStyle("-fx-font: 60 arial; -fx-background-radius: 100%; -fx-pref-width: 300px; -fx-pref-height: 300px ");
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(player1Turn)
                                {
                                    if(!boardController.isPlayerBoardPitEmpty(player1))
                                    {
                                        boardController.executeTurn(Integer.parseInt(button.getId()),Main.this.player1);
                                        setPlayer1Turn(false);
                                    }
                                    else
                                    {
                                        //implement finish game
                                    }

                                }
                                else
                                {
                                    if(!boardController.isPlayerBoardPitEmpty(player2))
                                    {
                                        boardController.executeTurn(Integer.parseInt(button.getId()),Main.this.player2);
                                        setPlayer1Turn(true);
                                    }
                                    else
                                    {
                                        //implement finish game
                                    }

                                }
                                refreshPits(boardGridPane, rootBorderPane);


                            }
                        });
                        boardGridPane.add(button, j,i);
                    }
                }
                Label turnStatus = new Label(isPlayer1Turn() ? "Player 1's Turn:" : "Player 2's Turn");
                Button player1Pit = new Button();
                Button player2Pit = new Button();
                player1Pit.setStyle("-fx-font: 60 arial; -fx-background-radius: 50%; -fx-pref-width: 150px; -fx-pref-height:150px");
                player1Pit.setText(" ");
                player2Pit.setStyle("-fx-font: 60 arial; -fx-background-radius: 50%;-fx-pref-width: 150px; -fx-pref-height:150px");
                player2Pit.setText(" ");
                rootBorderPane.setTop(turnStatus);
                rootBorderPane.setLeft(player1Pit);
                rootBorderPane.setRight(player2Pit);
                rootBorderPane.setCenter(boardGridPane);
                refreshPits(boardGridPane,rootBorderPane);
                rootBorderPane.setPadding(new Insets(25));
                Scene scene = new Scene(rootBorderPane, 1300,800);
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
                    if(!(numberOfSeeds>= 4 && numberOfSeeds <=9))
                    {
                        throw new IllegalArgumentException("Number of seeds in pit must be from 4-9!");
                    }
                    else
                    {
                        setNumberOfSeeds(numberOfSeeds);
                    }
                    if(!(numberOfPits>= 7 && numberOfPits <=10))
                    {
                        throw new IllegalArgumentException("Number of pits per player should be from 7-10!");
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



        Scene scene = new Scene(grid, 1300, 800);
        scene.getStylesheets().add
                (Main.class.getResource("Main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void refreshPits(GridPane layout,BorderPane root){
        ObservableList<Node> children = layout.getChildren();
        Button player1Pit =(Button)root.getLeft();
        Button player2Pit =(Button)root.getRight();
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
