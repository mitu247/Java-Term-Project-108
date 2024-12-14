package LogIn;

import java.io.IOException;

import javafx.application.Platform;
import util.GetBuyPlayerList;
import util.LoginDTO;
import util.Notify;


public class Z_ReadThread implements Runnable {
    private Thread thr;
    private Main main;

    public Z_ReadThread(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o instanceof LoginDTO) {
                    LoginDTO logInfo = (LoginDTO) o;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (logInfo.isStatus()) {
                                System.out.println("success");
                                try {
                                    main.setLoginDTO(logInfo);
                                    main.showHomePage(logInfo.getUserName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                main.showAlert();
                            }
                        }
                    });
                }
                if (o instanceof GetBuyPlayerList) {
                    // showing the buy player page
                    Platform.runLater(() -> {
                        try {
                            GetBuyPlayerList ob = (GetBuyPlayerList) o;
                            // When user clicks the buy option in (Buy & Sell) page
                            if (ob.isBuyButtonClicked()) {
                                main.modifyPlaySearches(ob);
                            }
                            // When user clicks the sell button in (HomePage)
                            // When user clicks the buy button in (Buy & Sell) page
                            // When any of the two button is clicked a refresh list is set in ob
                            if (ob.isRefresh()) {
                                // This list is set in main class, because this list is needed to update the
                                // Observalbe list in the HomepageController and BuyViewController's Thread
                                main.setRefreshList(ob);
                            }
                            else {
                                // After clicking the Buy & Sell page button
                                // A refresh list is also passed with information about the players
                                // who are auctioned
                                main.setRefreshList(ob);
                                main.showBuyPage(ob);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                if (o instanceof Notify) {
//                    System.out.println("Notified");
                    Platform.runLater(() -> {
                        try {
                            main.alreadyLoggedInAlert();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
