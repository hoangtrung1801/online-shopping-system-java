import database.DatabaseConn;
import javafx.application.Application;
import javafx.stage.Stage;
import route.LoginRoute;

public class App extends Application{

  @Override
  public void start(Stage primaryStage) throws Exception {
    new LoginRoute();
    // new DashboardRoute();
    // new AddProductRoute();
  }

  public static void main(String[] args) {
    DatabaseConn.connect();
    launch();
  }
}