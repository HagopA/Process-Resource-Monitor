import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProcessMonitorApp extends Application {


    ProcessOperations p = new ProcessOperations();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        final int WINDOW_WIDTH = 800;
        final int WINDOW_HEIGHT = 500;
        final int SPACING_BETWEEN_ELEMENTS = 10;
        final int BUTTON_WIDTH = 320;

        final Button runningProcessesButton = new Button("View running processes");
        final Button selectProcessToMonitorButton = new Button("Select Process to Start Monitoring");
        final Button removeProcessToMonitorButton = new Button("Select Process to Stop Monitoring");
        final Button displayCpuAndMemoryButton = new Button("Display CPU and Memory Usage of Selected Processes");
        final Button backButton = new Button("Back");
        final Button exitButton = new Button("Exit");
        final Button okButton = new Button("Ok");

        final String ERROR_ALERT_BOX_TITLE = "Error!";
        final String SUCCESS_ALERT_BOX_TITLE = "Success!";

        runningProcessesButton.setMinWidth(BUTTON_WIDTH);
        selectProcessToMonitorButton.setMinWidth(BUTTON_WIDTH);
        removeProcessToMonitorButton.setMinWidth(BUTTON_WIDTH);
        displayCpuAndMemoryButton.setMinWidth(BUTTON_WIDTH);
        backButton.setMinWidth(BUTTON_WIDTH);
        exitButton.setMinWidth(BUTTON_WIDTH);
        okButton.setMinWidth(BUTTON_WIDTH);

        Scene mainWindowScene;

        primaryStage.setTitle("Process Monitor Operations");

        GridPane mainLayout = new GridPane();
        mainLayout.add(runningProcessesButton, 0, 0);
        mainLayout.add(selectProcessToMonitorButton, 1, 0);
        mainLayout.add(removeProcessToMonitorButton, 0, 1);
        mainLayout.add(displayCpuAndMemoryButton, 1, 1);
        mainLayout.add(exitButton, 1, 2);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setHgap(SPACING_BETWEEN_ELEMENTS);
        mainLayout.setVgap(SPACING_BETWEEN_ELEMENTS);

        mainWindowScene = new Scene(mainLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(mainWindowScene);
        backButton.setOnAction(goBack -> primaryStage.setScene(mainWindowScene));

        runningProcessesButton.setOnAction(e -> {
            Text runningProcessesText = new Text(p.generateProcessList());
            GridPane runningProcessesGrid = new GridPane();
            runningProcessesGrid.add(runningProcessesText, 0 ,0);
            runningProcessesGrid.add(backButton, 0, 1);
            ScrollPane processesScrollPane = new ScrollPane(runningProcessesGrid);
            processesScrollPane.setVvalue(Byte.MIN_VALUE);
            Scene runningProcessesScene = new Scene(processesScrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(runningProcessesScene);
        });

        Stage exitStage = (Stage) exitButton.getScene().getWindow();
        exitButton.setOnAction(e -> exitStage.close());
        TextField userInput = new TextField();
        userInput.setMaxWidth(BUTTON_WIDTH);

        selectProcessToMonitorButton.setOnAction(e -> {
            okButton.setOnAction(c -> {
                if(!isInt(userInput.getText())){
                    String errorMessage = "Error: the input \"" + userInput.getText() + "\" is not a number." +
                            "\nPlease enter a valid number.";
                    AlertBox.displayAlertBox(ERROR_ALERT_BOX_TITLE, errorMessage, WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
                }
                else{
                    if(p.addProcessToMonitor(Integer.parseInt(userInput.getText()))){
                        String successMessage = "The PID \"" + userInput.getText() + "\" has successfully been\nadded to " +
                                "the list of processes being monitored.";
                        AlertBox.displayAlertBox(SUCCESS_ALERT_BOX_TITLE, successMessage, WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
                    }
                    else{
                        String errorMessage = "Error: the PID \"" + userInput.getText() + "\" cannot be monitored. Please" +
                                "\nmake sure that the PID is currently running.\nYou can verify the running processes\n" +
                                "through the main program\nwindow by clicking on \"View running processes\".";
                        AlertBox.displayAlertBox(ERROR_ALERT_BOX_TITLE, errorMessage, WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
                    }
                }
            });
            VBox layout = new VBox(SPACING_BETWEEN_ELEMENTS);
            Text text = new Text("Enter a process ID to start monitoring: ");
            layout.getChildren().setAll(text, userInput, okButton, backButton);
            layout.setAlignment(Pos.CENTER);
            Scene addMonitorScene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(addMonitorScene);
        });

        removeProcessToMonitorButton.setOnAction(e -> {
            okButton.setOnAction(c -> {
                if(!isInt(userInput.getText())){
                    String errorMessage = "Error: the input \"" + userInput.getText() + "\" is not a number." +
                            "\nPlease enter a valid number.";
                    AlertBox.displayAlertBox(ERROR_ALERT_BOX_TITLE, errorMessage, WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
                }
                else{
                    if(p.removeProcessToMonitor(Integer.parseInt(userInput.getText()))){
                        String successMessage = "The PID \"" + userInput.getText() + "\" has successfully been\nremoved from " +
                                "the list of processes being monitored.";
                        AlertBox.displayAlertBox(SUCCESS_ALERT_BOX_TITLE, successMessage, WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
                    }
                    else{
                        String errorMessage = "Error: the PID \"" + userInput.getText() + "\" cannot be removed. Please" +
                                "\nmake sure that the PID is currently being\nmonitored. You can verify the processes " +
                                "being monitored\nby clicking on \"Display CPU and Memory Usage of Selected Processes\"\n" +
                                "through the main program window.";
                        AlertBox.displayAlertBox(ERROR_ALERT_BOX_TITLE, errorMessage, WINDOW_HEIGHT/2, WINDOW_WIDTH/2);
                    }
                }
            });
            VBox layout = new VBox(SPACING_BETWEEN_ELEMENTS);
            Text text = new Text("Enter a process ID to stop monitoring: ");
            layout.getChildren().setAll(text, userInput, okButton, backButton);
            layout.setAlignment(Pos.CENTER);
            Scene addMonitorScene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);
            primaryStage.setScene(addMonitorScene);
        });

        displayCpuAndMemoryButton.setOnAction(e -> {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), c -> {
                Text displayText = new Text();
                GridPane cpuAndMemUsageGrid = new GridPane();
                cpuAndMemUsageGrid.add(displayText, 0 ,0);
                cpuAndMemUsageGrid.add(backButton, 0, 1);
                ScrollPane processesScrollPane = new ScrollPane(cpuAndMemUsageGrid);
                Scene runningProcessesScene = new Scene(processesScrollPane, WINDOW_WIDTH, WINDOW_HEIGHT);
                String appendText = "";
                for(ProcessInfo process : p.updateProcessUsage().values()){
                    appendText += "Process Name: " + process.getImageName() + "\nPID: " + process.getPid() + "\n" +
                            "Memory Usage: " + process.getMemUsage() + " K\n\n";
                }
                displayText.setText(appendText);
                primaryStage.setScene(runningProcessesScene);
            }));

            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            backButton.setOnAction(s -> {
                timeline.stop();
                primaryStage.setScene(mainWindowScene);
            });
        });

        primaryStage.show();
    }

    public boolean isInt(String input){
        try{
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
}
