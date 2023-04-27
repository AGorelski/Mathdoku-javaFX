package MathDoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Cell implements Cloneable{
    //StackPane pane = new StackPane();
    VBox vbox = new VBox();
    private Label cageLabel;
    TextField field = new TextField();
    Color cellColour;

    private int cellID;
    private int value = 0;

    private Insets borderInsets = new Insets(0,0,0,0);

    private Cage cage;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Cell cloned = (Cell) super.clone();
        cloned.setValue(String.valueOf(value));
        cloned.field = new TextField(this.field.getText());
        cloned.setCellID(this.cellID);
        cloned.cage = (this.cage);
        return cloned;
    }

    public Cell(int cellID, Color cellColour, Cage cage){
        this.cageLabel = new Label("");
        this.cellID = cellID;
        this.cellColour = cellColour;
        this.cage = cage;
    }

    public Cell(Label cageLabel, int cellID, Color cellColour, Cage cage){
        this.cageLabel = cageLabel;
        this.cellID = cellID;
        this.cellColour = cellColour;
        this.cage = cage;
    }

    public Cage getCage(){
        return cage;
    }

    public void setCellID(int iD){
        this.cellID = iD;
    }

    public int getCellID() {
        return cellID;
    }

    public void setField(String s) {
        field.setText(s);
    }

    public String getText() {
        return field.getText();
    }

    public TextField getField() {
        return field;
    }

    public void setValue(String s){
        this.value = Integer.parseInt(s);
    }

    public int getValue() {
        return value;
    }

    public void setColor(String s){
        if (s.equals("Mistakes")){
            vbox.setStyle("-fx-background-color: red; overflow:hidden; -fx-background-insets: 1 1 1 1");
            field.setStyle("-fx-background-color: red; overflow:hidden; -fx-background-insets: 1 1 1 1");
        }else if(s.equals("Casual")){
            vbox.setStyle("-fx-background-color: white; overflow:hidden; -fx-background-insets: 1 1 1 1");
            field.setStyle("-fx-background-color: white; overflow:hidden; -fx-background-insets: 1 1 1 1");
        }else if (s.equals("Win")){
            vbox.setStyle("-fx-background-color: yellow; overflow:hidden; -fx-background-insets: 1 1 1 1");
            field.setStyle("-fx-background-color: yellow; overflow:hidden; -fx-background-insets: 1 1 1 1");
        }
    }

    public Pane getBox(){
        vbox.setBorder(new Border(new BorderStroke(cellColour,cellColour,cellColour,cellColour,
                BorderStrokeStyle.SOLID,BorderStrokeStyle.SOLID,BorderStrokeStyle.SOLID,BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(3), borderInsets)));

        GridPane.setVgrow(field,Priority.ALWAYS);

        vbox.getChildren().addAll(cageLabel,field);

        cageLabel.setAlignment(Pos.CENTER);

        setColor("Casual");

        return vbox;
    }
}
