import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConn;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test {
  public Test() throws IOException {
    VBox root = new VBox();
    root.getChildren().add(new Button("click me"));

    List<Item> items = new ArrayList<>();
    try {
      Statement stmt = DatabaseConn.conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from test");
      while (rs.next()) {
        Item item = new Item();
        item.setPhoto(rs.getBinaryStream(1).readAllBytes());
        items.add(item);

      }

      for(Item item : items) {
        System.out.println(item.getPhoto().length);
        Image image = new Image(new ByteArrayInputStream(item.getPhoto()));
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);
      }

      Scene scene = new Scene(root);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
}

class Item {
  private byte[] photo;

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }
}
