package Network;

import Search.Club;
import util.*;
import Search.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    private List<Player> buyPlayerList;
    private List<Club> centralClubList;
    private HashMap<String, NetworkUtil> clubs;
    private HashMap<String, String> userMap;
    private HashMap<String, Boolean> logtracker;


    public ReadThreadServer(HashMap<String, Boolean> logtracker, HashMap<String, String> userMap,
                            HashMap<String, NetworkUtil> clubs,
                            List<Club> clubList, List<Player> buyPlayersList, NetworkUtil networkUtil) {
        this.logtracker = logtracker;
        this.userMap = userMap;
        this.clubs = clubs;
        this.centralClubList = clubList;
        this.buyPlayerList = buyPlayersList;
        this.networkUtil = networkUtil;

        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof Player) {
                        Player playerOb = (Player) o;
                        boolean newPlayer = true;
                        for (Player player : buyPlayerList) {
                            if (player.getName().equalsIgnoreCase(playerOb.getName())) {
                                newPlayer = false;
                            }
                        }
                        if (newPlayer) {
                            buyPlayerList.add(playerOb);
                        }
                        // System.out.println("Sell button clicked");
                        GetBuyPlayerList ob = new GetBuyPlayerList();
                        ob.setRefresh(true);
                        for (String S : clubs.keySet()) {
                            for (Player play : buyPlayerList) {
                                if (!play.getClub().equalsIgnoreCase(S)) {
                                    ob.setPlayers(play);
                                }
                            }
                            clubs.get(S).write(ob);
                            ob.getPlayers().clear();
                        }
                    }

                    if (o instanceof LoginDTO) {
                        LoginDTO logInfo = (LoginDTO) o;
                        if (logtracker.get(logInfo.getUserName()) == false) { // at first it has to be inactive
                            String password = userMap.get(logInfo.getUserName());
                            logInfo.setStatus(logInfo.getPassword().equalsIgnoreCase(password));
                            if (logInfo.isStatus()) {
                                clubs.put(logInfo.getUserName(), networkUtil);
                                logtracker.put(logInfo.getUserName(), true); // it is active now
                            }
                            networkUtil.write(logInfo);
                        }
                        else {
//                            System.out.println("Notified");
                            Notify ob = new Notify();
                            networkUtil.write(ob);
                        }
                    }

                    if (o instanceof Notify) {
                        Notify ob = (Notify) o;
                        logtracker.put(ob.getClubName(), false); // the club is not active
                    }

                    if (o instanceof GetBuyPlayerList) {
                        GetBuyPlayerList ob = (GetBuyPlayerList) o;
                        ob.setRefresh(false);
                        for (Player play : buyPlayerList) {
                            if (!play.getClub().equalsIgnoreCase(ob.getClubName())) {
                                ob.setPlayers(play);
//                                    System.out.println(play);
                            }
                        }
                        networkUtil.write(ob);
                        // GetBuyPlayerList obj is passed to every client with the refreshed list of players to be bought
                        // except those players who are of the same club
                    }

                    if (o instanceof buyPlayer) {
                        // Server gets the request of transferring player
                        buyPlayer ob = (buyPlayer) o;
                        int idx = 0;
                        for (Player player : buyPlayerList) {
                            if (player.getName().equalsIgnoreCase(ob.getPlayer().getName())) {
                                break;
                            }
                            idx++;
                        }
                        buyPlayerList.remove(idx);
                        // That player is removed from the buyPlayerList to update that in the thread of the buyViewController's Table
                        idx = 0;
                        System.out.println(ob.getPlayer().getName());
                        for (Club club : centralClubList) {
                            if (club.getClubName().equalsIgnoreCase(ob.getPlayer().getClub())) {
                                for (Player player : club.getPlayer()) {
                                    if (player.getName().equalsIgnoreCase(ob.getPlayer().getName())) {
                                        club.getPlayer().remove(idx);
                                        club.updateProperties();
                                        break;
                                    }
                                    idx++;
                                }
                            }
                        }
                        for (Club club : centralClubList) {
                            if (club.getClubName().equalsIgnoreCase(ob.getClubName())) {
                                ob.getPlayer().setClub(club.getClubName());
                                club.getPlayer().add(ob.getPlayer());
                                club.updateProperties();
                                break;
                            }
                        }

                        GetBuyPlayerList aucPlayer = new GetBuyPlayerList();
                        aucPlayer.setPlayers(buyPlayerList);
                        // This buyPlayerList will update the Observalbe list of the buy&Sell page's table
                        aucPlayer.setModifiedClubList(centralClubList);
                        // This changes the club properties of play searches so that the search options can render improved informations
                        aucPlayer.setRefresh(true);
                        // It is set true to refresh the tables of the HomePage of one client and the Buy & Sell page of another client
                        // This ob is passed to every client connected to the server so that every client can refresh their tables
                        aucPlayer.setBuyButtonClicked(true);
                        // BuyButtonClicked is set true to modify the play searches to show new results for searching

                        for (String S : clubs.keySet()) {
                            clubs.get(S).write(aucPlayer);
                            // A player is brought it is notified to every client connected so that they can refresh their tables accordingly
                        }
                        // for starting thread in every club
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



