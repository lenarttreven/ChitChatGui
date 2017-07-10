package si.treven.chitchat_client;

/**
 * Created by lenarttreven on 07/06/2017.
 */
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Uporabnik {
    private String username;
    private Date lastActive;

    private Uporabnik() { }

    public Uporabnik(String username, Date lastActive) {
        this.username = username;
        this.lastActive = lastActive;
    }

    public Uporabnik(String username){
        new Uporabnik(username,new Date());
    }

    @Override
    public String toString() {
        return "Uporabnik [username=" + username + ", lastActive=" + lastActive + "]";
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("last_active")
    public Date getLastActive() {
        return this.lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }


}



