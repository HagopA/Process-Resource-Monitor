import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProcessMonitorApp extends Application {


    ProcessOperations p = new ProcessOperations();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        int windowWidth = 800;
        int windowHeight = 500;
        int hGap = 50;
        int vGap = 50;

        final Button runningProcessesButton = new Button("View running processes");
        final Button button2 = new Button("Button2");
        final Button button3 = new Button("Button3");
        final Button button4 = new Button("Button4");
        final Button backButton = new Button("Back");

        Scene mainWindowScene;
        Scene runningProcessesScene;

        primaryStage.setTitle("Process Monitor Operations");

        GridPane mainLayout = new GridPane();
        mainLayout.add(runningProcessesButton, 0, 0);
        mainLayout.add(button2, 1, 0);
        mainLayout.add(button3, 0, 1);
        mainLayout.add(button4, 1, 1);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setHgap(hGap);
        mainLayout.setVgap(vGap);

        mainWindowScene = new Scene(mainLayout, windowWidth, windowHeight);
        primaryStage.setScene(mainWindowScene);
        Text runningProcessesText = new Text(p.generateProcessList());
        GridPane gridPane2 = new GridPane();
        backButton.setOnAction(goBack -> primaryStage.setScene(mainWindowScene));

        gridPane2.add(runningProcessesText, 0 ,0);
        gridPane2.add(backButton, 0, 1);

        ScrollPane processesScrollPane = new ScrollPane(gridPane2);
        processesScrollPane.setVvalue(Byte.MIN_VALUE);

        runningProcessesScene = new Scene(processesScrollPane, windowWidth, windowHeight);
        runningProcessesButton.setOnAction(e -> primaryStage.setScene(runningProcessesScene));

        primaryStage.show();
    }
}
