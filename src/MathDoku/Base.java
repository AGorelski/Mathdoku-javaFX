package MathDoku;

import javafx.application.Application;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Base {

    private int[][] solution;
    private int[][] initial;
    private int[][] players;

    public Base(){
        solution = new int[][]{
                {5,6,3,4,1,2},
                {6,1,4,5,2,3},
                {4,5,2,3,6,1},
                {3,4,1,2,5,6},
                {2,3,6,1,4,5},
                {1,2,5,6,3,4}
        };
        players = new int[6][6];
    }

    public int[][] getSolutions() {
        return solution;
    }

    public int[][] getPlayerAns() {
        return players;
    }


//    public void draw(GridPane gridPane,Integer col, Integer row){
//
//        StackPane stack = new StackPane();
//
//          Rectangle r = new Rectangle();
//        r.heightProperty().bind(gridPane.heightProperty().divide(xPos));
//        r.widthProperty().bind(gridPane.widthProperty().divide(yPos));
//        r.setFill(Color.WHITE);
//        r.setStroke(Color.BLACK);
//
//        //Text text = new Text("123");
//
//        stack.getChildren().addAll(r);
//
//        GridPane.setColumnIndex(stack,col);
//        GridPane.setRowIndex(stack,row);
//
//        stack.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                System.out.println("Column = " +  GridPane.getColumnIndex(stack));
//                System.out.println("Row = " +  GridPane.getRowIndex(stack));
//
//            }
//        });
//
//
//        GridPane.setConstraints(stack, col, row);
//        gridPane.getChildren().addAll(stack);
//    }


}
