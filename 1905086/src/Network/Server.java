package Network;

import Search.Club;
import Search.Player;
import Search.PlaySearches;
import util.NetworkUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private static final String INPUT_FILE ="players.txt";

    // ------------------------------------------------
    private static List<Player> playerList;
    private List<Club> clubList;

    private PlaySearches playSearches;
    private HashMap<String, String> userMap;
    private HashMap<String, NetworkUtil> clubs;
    private HashMap<String, Boolean> tracker;
    private List<Player> buyPlayerList;

    Server() {
        try {
            playerList = new ArrayList<>();
            clubList = new ArrayList<>();
            readFromFile();

            tracker = new HashMap<>();
            userMap = new HashMap<>();
            for (Club club : clubList) {
                userMap.put(club.getClubName(), "123");
                tracker.put(club.getClubName(), false); // by default every club is logged out
            }
            clubs = new HashMap<>();
            buyPlayerList = new ArrayList<>();

            playSearches = new PlaySearches();
            playSearches.setPlayerList(playerList);
            playSearches.setClubList(clubList);

            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connects ...... ");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        networkUtil.write(playSearches);
        new ReadThreadServer(tracker, userMap, clubs, clubList, buyPlayerList, networkUtil);
    }

    public static void main(String[] args) throws Exception {
        new Server();
        writeToFile();
    }

    public void readFromFile() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
        while(true){
            String line= br.readLine();
            if(line==null)break;
            String [] tokens =line.split(",");
            Player p =new Player();
            p.setName(tokens[0]);
            p.setCountry(tokens[1]);
            p.setAge(Integer.parseInt(tokens[2]));
            p.setHeight(Double.parseDouble(tokens[3]));
            p.setClub(tokens[4]);
            p.setPosition(tokens[5]);
            p.setNumber(Integer.parseInt(tokens[6]));
            p.setWeeklySalary(Double.parseDouble(tokens[7]));
            playerList.add(p);
            addPlayerToClub(p);

        }
        br.close();
    }

    public static void writeToFile() throws Exception{
        BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_FILE));
        for (Player p : playerList) {
            bw.write(p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," +
                    p.getClub() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeeklySalary());
            bw.write("\n");
        }
        bw.close();
    }

    public void addPlayerToClub(Player p){
        boolean newClub = true;
        for(Club club : clubList){
            if(club.getClubName().equalsIgnoreCase(p.getClub())){
                club.setPlayer(p);
                club.setPlayerCount(1);
                if(p.getWeeklySalary()>club.getMaxSalary())club.setMaxSalary(p.getWeeklySalary());
                if(p.getAge()>club.getMaxAge())club.setMaxAge(p.getAge());
                if(p.getHeight()>club.getMaxHeight())club.setMaxHeight(p.getHeight());
                newClub=false;
            }
        }
        if(newClub) {
            Club ob = new Club();
            ob.setClubName(p.getClub());
            ob.setMaxSalary(p.getWeeklySalary());
            ob.setMaxHeight(p.getHeight());
            ob.setMaxAge(p.getAge());
            ob.setPlayer(p);
            ob.setPlayerCount(1);
            clubList.add(ob);
        }
    }
}
