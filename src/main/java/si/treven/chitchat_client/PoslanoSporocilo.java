package si.treven.chitchat_client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lenarttreven on 07/06/2017.
 */

/**
 * Razred, s katerim predstavimo poslano sporočilo
 */
public class PoslanoSporocilo {
    private String vsebina;
    private Boolean global;
    private String prejemnik;


    private PoslanoSporocilo() { }

    public PoslanoSporocilo(String prejemnik, String vsebina){
        this.global = false;
        this.prejemnik = prejemnik;
        this.vsebina = vsebina;
    }

    public PoslanoSporocilo(String vsebina){
        this.global = true;
        this.vsebina = vsebina;
    }

    /**
     * @return "PoslanoSporocilo [" +"vsebina='" + vsebina + '\'' +", global=" + global +", prejemnik='" + prejemnik + '\'' +']';
     */
    @Override
    public String toString() {
        return "PoslanoSporocilo [" +
                "vsebina='" + vsebina + '\'' +
                ", global=" + global +
                ", prejemnik='" + prejemnik + '\'' +
                ']';
    }

    /**
     * @return vsebina
     */
    @JsonProperty("text")
    public String getVsebina() {
        return vsebina;
    }

    /**
     * @param vsebina
     */
    public void setVsebina(String vsebina) {
        this.vsebina = vsebina;
    }

    /**
     * @return true če je sporočilo globalno, false sicer
     */
    @JsonProperty("global")
    public Boolean getGlobal() {
        return global;
    }

    /**
     * @param global
     */
    public void setGlobal(Boolean global) {
        this.global = global;
    }

    /**
     * @return prejmenik sporočila
     */
    @JsonProperty("recipient")
    public String getPrejemnik() {
        return prejemnik;
    }

    /**
     * @param prejemnik
     */
    public void setPrejemnik(String prejemnik) {
        this.prejemnik = prejemnik;
    }
}
