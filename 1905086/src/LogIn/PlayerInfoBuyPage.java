package LogIn;

import Search.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.NetworkUtil;
import util.buyPlayer;

import java.io.IOException;


public class PlayerInfoBuyPage {
    private String playerName;
    private String price;
    private Button BuyButton;
    private Button ViewButton;

    public PlayerInfoBuyPage(Player player, NetworkUtil networkUtil, String clubName) {
        this.playerName = player.getName();
        this.price=player.getPrice();
        this.BuyButton = new Button("Buy");
        BuyButton.setCursor(Cursor.HAND);
        BuyButton.setOnAction(k -> {
            buyPlayer ob = new buyPlayer();
            ob.setClubName(clubName);
            // clubName of the club which requested to buy this player
            ob.setPlayer(player);
            ob.setBuyButtonClicked(true);
            try {
                networkUtil.write(ob); // Request is sent to server
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.ViewButton = new Button("View");
        ViewButton.setCursor(Cursor.HAND);

        ViewButton.setOnAction( e -> {
                    TableView<ShowPlayerProperty> table = new TableView<>();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Table View Sample");
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

                    ObservableList data = FXCollections.observableArrayList(new ShowPlayerProperty("Name", player.getName()),
                            new ShowPlayerProperty("Country", player.getCountry()),
                            new ShowPlayerProperty("Age", String.valueOf(player.getAge())),
                            new ShowPlayerProperty("Height", String.valueOf(player.getHeight())),
                            new ShowPlayerProperty("Club", String.valueOf(player.getClub())),
                            new ShowPlayerProperty("Position", String.valueOf(player.getPosition())),
                            new ShowPlayerProperty("Number", String.valueOf(player.getNumber())),
                            new ShowPlayerProperty("Weekly Salary", String.valueOf(player.getWeeklySalary()))
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


    public Button getViewButton() {
        return ViewButton;
    }

    public void setViewButton(Button viewButton) {
        ViewButton = viewButton;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Button getBuyButton() {
        return BuyButton;
    }

    public void setBuyButton(Button buyButton) {
        BuyButton = buyButton;
    }
}
