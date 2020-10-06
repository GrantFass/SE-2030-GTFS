package SE2030TransitProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainProgram.fxml"));
        loader.load();
        Controller controller = loader.getController();
        stage.setScene(new Scene(loader.getRoot()));
        stage.setTitle("General Transit Feed Specification Tool");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
