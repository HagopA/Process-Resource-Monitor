import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void displayAlertBox(String title, String message, int height, int width){
        Stage window = new Stage();
        final int SPACING_BETWEEN_ELEMENTS = 10;

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(width);
        window.setHeight(height);

        Label label = new Label();
        label.setText(message);
        Button closebutton = new Button("Close");
        closebutton.setOnAction(e -> window.close());
        VBox layout = new VBox(SPACING_BETWEEN_ELEMENTS);
        layout.getChildren().addAll(label, closebutton);
        layout.setAlignment(Pos.CENTER);

        Scene alerScene = new Scene(layout);
        window.setScene(alerScene);
        window.showAndWait();
    }
}
