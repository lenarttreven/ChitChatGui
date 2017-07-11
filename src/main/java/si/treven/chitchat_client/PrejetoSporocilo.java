package si.treven.chitchat_client;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lenarttreven on 07/06/2017.
 */

/**
 * Razred s katerim predstavimo prejetno sporočilo
 */
public class PrejetoSporocilo {
    private Boolean global;
    private String recipient;
    private String sender;
    private String text;
    private String sentAt;

    private PrejetoSporocilo(){}

    public PrejetoSporocilo(Boolean global, String recipient, String sender, String text, String sentAt) {
        this.global = global;
        this.recipient = recipient;
        this.sender = sender;
        this.text = text;
        this.sentAt = sentAt;
    }

    /**
     * @return true, če je sporočilo globalno, false sicer
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
     * @return prejemnik
     */
    @JsonProperty("recipient")
    public String getRecipient() {
        return recipient;
    }

    /**
     * @param recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return pošiljatelj
     */
    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }

    /**
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return vsebina sporočila
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return čas, ko je bilo sporočilo poslano
     */
    @JsonProperty("sent_at")
    public String getSentAt() {
        return sentAt;
    }

    /**
     * @param sentAt
     */
    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}
