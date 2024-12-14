package util;

import java.io.Serializable;

public class Notify implements Serializable {
    private String clubName;

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
