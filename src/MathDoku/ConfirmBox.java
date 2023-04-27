package MathDoku;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    //Create variable
    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        ChoiceBox<String> choices = new ChoiceBox<>();

        choices.getItems().addAll("2x2","3x3","4x4","5x5","6x6","7x7","8x8");
        choices.setValue("6x6");

        choices.setOnAction(e -> System.out.println(choices.getValue()));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 0, 5, 0));
        vBox.setSpacing(10);

        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(15, 12, 15, 12));
        hb.setSpacing(10);
        hb.getChildren().addAll(label, choices);

        Button ok = new Button("OK");
        ok.setDisable(true);

        vBox.getChildren().addAll(hb,ok);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}