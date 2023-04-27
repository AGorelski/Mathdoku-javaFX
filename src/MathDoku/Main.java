package MathDoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    private Scene scene1,scene2,scene3;
    private Stage window;

    private Button undo;
    private Button redo;
    private Button clear;
    private Button show_mistakes;

    private Grid board;
    private GridPane grid;

    private int chosenNum = 2;

    public static void main(String[] args) {
        launch(args);
    }

    public Background background(){
        Image image = new Image("712990.jpg");

        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        return new Background(backgroundimage);
    }

    //layout1 things
    public HBox welcomeMessage(){
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setPadding(new Insets(15, 12, 15, 12));
        hBox1.setSpacing(10);

        Text welcomeMessage = new Text("Welcome to my Mathdoku!");
        welcomeMessage.setFont(Font.font("American Typewriter", FontWeight.BOLD, 25));
        welcomeMessage.setFill(Color.WHITESMOKE);

        hBox1.getChildren().add(welcomeMessage);

        return hBox1;
    }

    public BorderPane rules(){
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(0, 10, 0, 10));

        VBox rules = new VBox();
        rules.setAlignment(Pos.CENTER);
        rules.setPadding(new Insets(5, 0, 5, 0));
        rules.setSpacing(10);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        Image image = new Image("MathDokuRules4.gif");

        Text title = new Text("Rules of the game: ");
        title.setFont(Font.font("American Typewriter", FontWeight.BOLD, 22));
        title.setFill(Color.WHEAT);

        hBox.getChildren().addAll(new ImageView(image), title);

        Text line1 = new Text("You need to fill the cells in an NxN square border with the numbers 1 to N\n " +
                "(one number per cell), while adhering to the following constraints:");
        line1.setFont(Font.font("American Typewriter",FontWeight.SEMI_BOLD,18));
        line1.setFill(Color.WHEAT);
        Text line2 = new Text("Each number must appear exactly once in each row.\n" +
                "Each number must appear exactly once in each column.");
        line2.setFont(Font.font("Bangla MN", 15));
        line2.setFill(Color.RED);
        Text line3 = new Text("There are groups of adjacent cells called cages,which are highlighted on the border by thicker boundaries.\n" +
                "   Within each cage is a label showing a target number followed by an arithmetic operator (+, -, x, ÷).\n" +
                "                       There is an additional constraint associated with these cages:");
        line3.setFont(Font.font("American Typewriter",FontWeight.SEMI_BOLD,18));
        line3.setFill(Color.WHEAT);
        Text line5 = new Text("It must be possible to obtain the target by applying the arithmetic operator to the numbers in that cage. ");
        line5.setFont(Font.font("American Typewriter",FontWeight.SEMI_BOLD,18));
        line5.setFill(Color.WHEAT);
        Text line6 = new Text("Note: If a cage consists of a single cell, then no arithmetic operator is shown.\n " +
                "       The label simply shows the number that must be in that cell.");
        line6.setFont(Font.font("Bangla MN", 18));
        line6.setFill(Color.GOLD);

        rules.getChildren().addAll(hBox,line1,line2,line3,line5,line6);

        border.setCenter(rules);

        return border;
    }

    public HBox buttons(){
        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(15, 12, 15, 12));
        buttonsBox.setSpacing(10);

        Button newGame = new Button("New Game");
        newGame.setPrefSize(120, 30);
        newGame.setStyle("-fx-background-color:\n" +
                "            linear-gradient(#ffd65b, #e68400),\n" +
                "            linear-gradient(#ffef84, #f2ba44),\n" +
                "            linear-gradient(#ffea6a, #efaa22),\n" +
                "            linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
                "            linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));");
        newGame.setTextFill(Color.BLUEVIOLET);
        newGame.setFont(Font.font("Bangla MN"));
        newGame.setOnAction(e -> window.setScene(scene2));

        buttonsBox.getChildren().addAll(newGame);

        return buttonsBox;
    }
    //layout2 things
    public FlowPane addButtons(){
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setPrefWrapLength(150);
        flow.setAlignment(Pos.CENTER);

        Button[] buttons = new Button[10];

        Button button_one = new Button("1");
        button_one.setPrefSize(50,50);
        buttons[0] = button_one;
        flow.getChildren().add(buttons[0]);

        Button button_two = new Button("2");
        button_two.setPrefSize(50,50);
        buttons[1] = button_two;
        flow.getChildren().add(buttons[1]);

        Button button_three = new Button("3");
        button_three.setPrefSize(50,50);
        buttons[2] = button_three;
        flow.getChildren().add(buttons[2]);

        Button button_four = new Button("4");
        button_four.setPrefSize(50,50);
        buttons[3] = button_four;
        flow.getChildren().add(buttons[3]);

        Button button_five = new Button("5");
        button_five.setPrefSize(50,50);
        buttons[4] = button_five;
        flow.getChildren().add(buttons[4]);

        Button button_six = new Button("6");
        button_six.setPrefSize(50,50);
        buttons[5] = button_six;
        flow.getChildren().add(buttons[5]);

        Button button_seven = new Button("7");
        button_seven.setPrefSize(50,50);
        buttons[6] = button_seven;
        flow.getChildren().add(buttons[6]);

        Button button_eight = new Button("8");
        button_eight.setPrefSize(50,50);
        buttons[7] = button_eight;
        flow.getChildren().add(buttons[7]);

        undo = new Button("Undo");
        undo.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1); ");
        undo.addEventHandler(ActionEvent.ANY, e -> {
            try {
                board.undo();
                if (redo.isDisabled()) {
                    redo.setDisable(false);
                }
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        });
        buttons[8] = undo;
        flow.getChildren().add(buttons[8]);

        redo = new Button("Redo");
        redo.setStyle("-fx-background-color: linear-gradient(#2A5058, #61a2b1); ");
        redo.addEventHandler(ActionEvent.ANY, e -> {
            board.redo();
            if (board.getRemovedCells().size() == 0) {
                redo.setDisable(true);
            }
        });
        buttons[9] = redo;
        flow.getChildren().add(buttons[9]);

        return flow;
    }

    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        show_mistakes = new Button("Show Mistakes");
        show_mistakes.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);");
        show_mistakes.setPrefSize(120, 30);
        show_mistakes.addEventHandler(ActionEvent.ANY, e -> {board.checkForWin(); board.changeColour("Mistakes");});

        clear = new Button("Clear");
        clear.setStyle("-fx-background-color:\n" +
                "        linear-gradient(#f0ff35, #a9ff00),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);");
        clear.setPrefSize(100, 30);
        clear.addEventHandler(ActionEvent.ANY, e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to clear the board?");
            alert.setTitle("Clear the board");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                board.clear();
            }
        });

        hbox.getChildren().addAll(show_mistakes, clear);

        return hbox;
    }
    public MenuBar addMenu(){
        Menu options = new Menu("Options");

        MenuItem chooseSize = new MenuItem("Generate Grid");
        chooseSize.addEventHandler(ActionEvent.ANY, e -> {
            List<String> choices = new ArrayList<>();
            choices.add("2x2");
            choices.add("3x3");
            choices.add("4x4");
            choices.add("6x6");
            choices.add("7x7");
            choices.add("8x8");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("6x6",choices);
            dialog.setTitle("Mathdoku size");
            dialog.setHeaderText("Grid Size");
            dialog.setContentText(
                    "Please choose size of the grid:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                chosenNum = Integer.parseInt(result.get().substring(0,1));
                if (chosenNum == 2){
                    board.readFromString("1 1\n5+ 2,3,4");
                    create();
                } else if (chosenNum == 3){
                    board.readFromString("6x 1,4\n6+ 2,3,6\n3x 5,7,8\n2 9");
                    create();
                }else if (chosenNum == 4){
                    board.readFromString("10+ 1,2,5\n4+ 3,7\n2÷ 4,8\n24x 6,10,11\n3x 9,13,14\n24x 12,15,16");
                    create();
                }else if (chosenNum == 5){
                    board.readFromString("9+ 1,6\n1- 2,7\n2- 3,4\n7+ 5,9,10,15\n4 8\n12x 11,16,17\n7+ 12,13\n9+ 14,18,19\n100x 20,24,25\n6+ 21,22,23");
                    create();
                }else if (chosenNum == 6){
                    board.readFromString("11+ 1,7\n2÷ 2,3\n20x 4,10\n6x 5,6,12,18\n3- 8,9\n3÷ 11,17\n240x 13,14,19,20\n6x 15,16\n6x 21,27\n7+ 22,28,29\n30x 23,24\n6x 25,26\n9+ 30,36\n8+ 31,32,33\n2÷ 34,35");
                    create();
                }else if (chosenNum == 7){
                    board.readFromString("6x 1,2\n112x 3,9,10\n7 4\n18x 5,6,13\n13+ 7,14,21\n1- 8,15\n8+ 11,12\n18+ 16,22,23\n6+ 17,18,25\n3- 19,20\n20+ 24,30,31,32,38\n10+ 26,33,40\n4 27\n14+ 28,34,35\n10+ 29,36,37\n24x 39,45,46\n14+ 41,47,48\n12x 42,49\n7+ 43,44");
                    create();
                }else if (chosenNum == 8){
                    board.readFromString("8 1\n14x 2,10,11\n12x 3,4\n19+ 5,6,7,14\n2 8\n210x 9,17,18,25\n12x 12,19,20\n4 13\n480x 15,22,23,31\n192x 16,24,32\n432x 21,28,29,30,37\n12+ 26,27\n17+ 33,34,41\n70x 35,36,44\n8x 38,46\n10+ 39,40,48\n19+ 42,49,50,51\n8 43\n23+ 45,52,53,54\n2÷ 47,55\n35x 56,64\n3- 57,58\n14+ 59,60\n30x 61,62,63");
                    create();
                }

            }
        });

        MenuItem goBack = new MenuItem("Initial page");
        goBack.setOnAction(e -> window.setScene(scene1));
        MenuItem solve = new MenuItem("Solve Mathdoku");
        solve.addEventHandler(ActionEvent.ANY, e -> {
            try {
                board.clear();
                board.solve();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR, "I can't solve it, Sorry..");
                Optional<ButtonType> result = alert.showAndWait();

            }
        });
        options.getItems().addAll(chooseSize,new SeparatorMenuItem(),solve,goBack);

        Menu load = new Menu("Load");

        MenuItem loadFile = new MenuItem("Load From File");
        loadFile.addEventHandler(ActionEvent.ANY, e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File to Load");
            FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text files",
                    "*.txt");
            fileChooser.getExtensionFilters().add(txtFilter);
            File file = fileChooser.showOpenDialog(window);
            if (file != null && file.exists() && file.canRead()) {
                try {
                    BufferedReader buffered = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                    String line;
                    String content = "";
                    while ((line = buffered.readLine()) != null) {
                        content += (line + "\n");
                    }
                    if(board.ValidInput(content)) {
                        board.readFromString(content);
                        create();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data!");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    buffered.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        MenuItem loadText = new MenuItem("Load From Text");
        loadText.addEventHandler(ActionEvent.ANY, e -> {
            TextArea area = new TextArea();

            Button submit = new Button("Submit");
            submit.setPrefSize(100,30);

            Button cancelButton = new Button("Cancel");
            cancelButton.setPrefSize(100,30);

            VBox buttonsBox = new VBox();
            buttonsBox.setSpacing(10);

            BorderPane dialogPane = new BorderPane();
            buttonsBox.getChildren().addAll(submit, cancelButton);

            dialogPane.setRight(buttonsBox);
            dialogPane.setCenter(area);
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(dialogPane));
            dialogStage.show();

            submit.addEventHandler(ActionEvent.ANY, ex -> {
                if(board.ValidInput(area.getText())) {
                    board.readFromString(area.getText());
                    create();
                    dialogStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Provided data is invalid!");
                    Optional<ButtonType> result = alert.showAndWait();
                }
            });
            cancelButton.addEventHandler(ActionEvent.ANY, ex -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Everything written will be lost! Do you still want to proceed");
                alert.setTitle("Closing the dialog window");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK) {
                    dialogStage.close();
                }
            });
        });

        load.getItems().addAll(loadFile,loadText);

        MenuBar menu = new MenuBar();
        menu.getMenus().addAll(options,load);

        return menu;
    }
    public void create(){
        grid.getChildren().clear();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell;
                cell = (board.getCells())[i][j];
                TextField field = (board.getCells())[i][j].getField();
                field.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("^[1-9]$"))
                    {
                        newValue = newValue.replaceAll("^0", "");
                        newValue = newValue.replaceAll("[^\\d]", "");
                        if(newValue.length() > 0) {
                            newValue = newValue.substring(0,1);
                        }
                        field.setText(newValue);
                    }
                    if (newValue.length() > 0) {
                        cell.setValue(newValue);
                        cell.setField(newValue);
                        board.addChangedCells(cell.getCellID());
                        if (undo.isDisabled()) {
                            undo.setDisable(false);
                            clear.setDisable(false);
                        }
                        if(board.getChangedCells().size() == board.getSize() * board.getSize()) {
                            if(show_mistakes.isDisabled()) {
                                show_mistakes.setDisable(false);
                            }
                        }
                    } else {
                        cell.setValue("0");
                        cell.setField("");
                        board.removeChangedCells(cell.getCellID());
                        board.changeColour("Casual");
                        if (board.getChangedCells().size() == 0) {
                            undo.setDisable(true);
                            clear.setDisable(true);
                        }
                        if(board.getChangedCells().size() < board.getSize()*board.getSize()) {
                            show_mistakes.setDisable(true);
                        }
                    }
                });
                grid.add((board.getCells())[i][j].getBox(), j, i);
            }
        }
    }



    @Override
    public void start(Stage primaryStage) {
         window = primaryStage;

        BorderPane layout1 = new BorderPane();
        layout1.setBackground(background());
        layout1.setTop(welcomeMessage());
        layout1.setCenter(rules());
        layout1.setBottom(buttons());

        scene1 = new Scene(layout1);

        BorderPane layout2 = new BorderPane();
        layout2.setPrefSize(550,550);

        grid = new GridPane();
        board = new Grid();
        board.readFromString("9+ 1,6\n1- 2,7\n2- 3,4\n7+ 5,9,10,15\n4 8\n12x 11,16,17\n7+ 12,13\n9+ 14,18,19\n100x 20,24,25\n6+ 21,22,23");

        create();

        layout2.setCenter(grid);
        layout2.setTop(addMenu());
        layout2.setRight(addButtons());
        layout2.setBottom(addHBox());

        scene2 = new Scene(layout2);

        primaryStage.setScene(scene1);
        primaryStage.setTitle("MathDoku");
        primaryStage.show();
    }
}
