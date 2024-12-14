package LogIn;

import Search.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;

public class PlayerInfo {
    private String playerName;
    private Button Sell;
    private Button View;


/*    public PlayerInfo(Player player) {
        this.playerName = player.getName();
        this.Sell = new Button("Sell");
        Sell.setCursor(Cursor.HAND);*/
private static Player player;

    public PlayerInfo(Player player, NetworkUtil networkUtil) {
        this.playerName = player.getName();
        this.Sell = new Button("Sell");
        this.Sell.setCursor(Cursor.HAND);
        Sell.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Price Tag");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SetPrice.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ioException) {
                System.out.println("Exception in playerInfo class");
            }
            setPriceController controller = loader.getController();
            controller.setNetworkUtilandPlayer(networkUtil, player);

            stage.setScene(new Scene(root, 368, 208));
            stage.show();
        });

        this.View = new Button("View");
        View.setCursor(Cursor.HAND);
        View.setOnAction(e->{
                    TableView<Button> table = new TableView<>();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Player info");
                    stage.setWidth(440);
                    stage.setHeight(400);

                    table.setEditable(true);

                    TableColumn firstNameCol = new TableColumn("Properties");
                    firstNameCol.setMinWidth(200);
                    firstNameCol.setCellValueFactory(
                            new PropertyValueFactory<>("col1"));

                    TableColumn lastNameCol = new TableColumn("Attributes");
                    lastNameCol.setMinWidth(200);
                    lastNameCol.setCellValueFactory(
                            new PropertyValueFactory<>("col2"));

                    ObservableList data = FXCollections.observableArrayList(new ShowPlayerProperty("Name",player.getName()),
                            new ShowPlayerProperty("Country", player.getCountry()),
                            new ShowPlayerProperty("Age (in years)", String.valueOf(player.getAge())),
                            new ShowPlayerProperty("Height (in metre)", String.valueOf(player.getHeight())),
                            new ShowPlayerProperty("Club", String.valueOf(player.getClub())),
                            new ShowPlayerProperty("Position", String.valueOf(player.getPosition())),
                            new ShowPlayerProperty("Number", String.valueOf(player.getNumber())),
                            new ShowPlayerProperty("Weekly Salary ($)", String.valueOf(player.getWeeklySalary()))
                    );
                    table.setItems(data);
                    table.getColumns().addAll(firstNameCol, lastNameCol);
                    final VBox vbox = new VBox();
                    vbox.setSpacing(5);
                    vbox.setPadding(new Insets(10, 0, 0, 10));
                    vbox.getChildren().addAll(table);

                    ((Group) scene.getRoot()).getChildren().addAll(vbox);
                    scene.getStylesheets().add(getClass().getResource("TableDesign.css").toExternalForm());

                    stage.setScene(scene);
                    stage.show();

                }
        );
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Button getSell() {
        return Sell;
    }

    public void setSell(Button sell) {
        Sell = sell;
    }

    public Button getView() {
        return View;
    }

    public void setView(Button view) {
        View = view;
    }
}
