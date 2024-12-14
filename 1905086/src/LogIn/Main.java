package LogIn;

import Search.Country;
import Search.PlaySearches;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.GetBuyPlayerList;
import util.LoginDTO;
import util.NetworkUtil;
import util.Notify;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class Main extends Application {
    private NetworkUtil networkUtil;

    private Stage stage;
    private PlaySearches playSearches;

    public Stage getStage() {
        return stage;
    }

    private LoginDTO loginDTO;
    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    private String clubName;
    public String getClubName() {
        return clubName;
    }

    private GetBuyPlayerList ob;
    // This ob is modified whenever any client clicks the sell or buy button
    // At the first time after login, ob is null and it is checked in the controller
    // ob is not null whenever a client clicks the sell or buy button
    // set refresh list is called whenever a client clicks the sell or buy button
    public void setRefreshList(GetBuyPlayerList ob) {
        this.ob = ob;
    }
    public GetBuyPlayerList getRefreshList() {
        return ob;
    }
    // GetBuyPlayerList is called from the BuyViewController to update the ObservableList in the parallel Thread


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connectToServer();
        showLoginPage();
        stage.setOnCloseRequest(e ->{
            Notify obj = new Notify();
            obj.setClubName(clubName);
            try{
                networkUtil.write(obj);
            }catch (IOException ioException){
                ioException.printStackTrace();
            }
        });
    }


    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LoginController controller = loader.getController();
        controller.setMain(this);
        controller.loadimage();
        controller.setPlaySearches(playSearches);

        // Set the primary stage
        stage.setTitle("Login Page");
        stage.setScene(new Scene(root, 942, 532));
        stage.show();
    }

    public void showHomePage(String clubName) throws Exception {
        this.clubName = clubName;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HomePageController controller = loader.getController();
        controller.setMain(this);
        controller.setPlaySearches(playSearches);
        controller.setClubName(clubName);
        controller.loadimage();

        // Set the primary stage
        stage.setTitle("Home");
        stage.setScene(new Scene(root, 942, 532));
        stage.show();
    }
    public void showBuyPage(GetBuyPlayerList ob) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BuyView.fxml"));
        Parent root = loader.load();

        // Loading the controller
        BuyViewController controller = loader.getController();
        //controller.init(userName);
        controller.setMain(this);
        controller.load(ob.getPlayers());
        controller.loadImage();
        // Set the primary stage
        stage.setTitle("Buy Players");
        stage.setScene(new Scene(root, 942, 532));
        stage.show();
    }
    public void NewTableLoader(List<Country> country) throws IOException {
        Stage stage= new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TableView.fxml"));
        Parent root = loader.load();

        // Loading the controller
        TableViewController controller = loader.getController();
        controller.loadTable(country);
        /*TableView table=controller.getTableView();
        table.prefHeightProperty().bind(stage.heightProperty());
        table.prefWidthProperty().bind(stage.widthProperty());
        stage.setTitle("Country-wise-player");*/
        stage.setScene(new Scene(root, 514, 496));
        stage.show();
    }
    public void viewTotalSalary(double totalSalary) {
        Stage showSalary = new Stage();
        showSalary.setTitle("Total Yearly Salary");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 200);
        root.setBackground(new Background(new BackgroundFill(Color.web("#f0d38b"), CornerRadii.EMPTY, Insets.EMPTY)));
        showSalary.setScene(scene);

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(4);
        Text hello = new Text("TOTAL YEARLY SALARY\n\n" + "Total Yearly Salary : " + df.format(totalSalary));
        hello.setFont(Font.font("HoloLens MDL2 Assets", FontWeight.BOLD, 25));
        hello.setFill(Color.BLACK);
        root.getChildren().add(hello);
        showSalary.show();
    }


    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }
    public void alreadyLoggedInAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Club is already logged in");
        alert.setHeaderText("This club is already logged in");
        alert.setContentText("The username and password you provided is in use right now");
        alert.show();
    }

    public static void main(String[] args) {
        // This will launch the JavaFX application
        launch(args);
    }

    public void connectToServer() throws IOException, ClassNotFoundException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        playSearches = (PlaySearches) networkUtil.read();
        new Z_ReadThread(this);
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void modifyPlaySearches(GetBuyPlayerList ob) {
        playSearches.modifyClubList(ob);
    }

}
