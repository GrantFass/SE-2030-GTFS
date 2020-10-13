package SE2030TransitProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Start the program and load all relevant controllers and windows
     * @param stage The initial stage to load
     * @throws Exception if something goes wrong
     * @author Grant Fass
     */
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainProgram.fxml"));
        loader.load();
        Controller controller = loader.getController();
        controller.setDefaultValues();
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("General Transit Feed Specification Tool");
        stage.show();

        Stage dataDisplayStage = new Stage();
        FXMLLoader dataDisplayLoader = new FXMLLoader(getClass().getResource("DataDisplay.fxml"));
        dataDisplayLoader.load();
        DataDisplayController dataDisplayController = dataDisplayLoader.getController();
        dataDisplayStage.setScene(new Scene(dataDisplayLoader.getRoot()));
        dataDisplayStage.setTitle("Data Display");
        dataDisplayStage.hide();

        controller.setStage(stage);
        controller.setDataDisplayController(dataDisplayController);
        controller.setDataDisplayStage(dataDisplayStage);

        dataDisplayController.setStage(dataDisplayStage);
        dataDisplayController.setPrimaryController(controller);
        dataDisplayController.setPrimaryStage(stage);

        dataDisplayStage.setOnCloseRequest(event -> {
            controller.viewDataDisplayButton.setText("Show Data Display");
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
