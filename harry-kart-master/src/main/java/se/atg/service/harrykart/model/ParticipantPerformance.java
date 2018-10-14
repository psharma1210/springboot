package se.atg.service.harrykart.model;

import java.io.Serializable;

public class ParticipantPerformance implements Serializable {

    private static final long serialVersionUID = 1467920872228625830L;

    private int position;

    private String horse;

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the horse
     */
    public String getHorse() {
        return horse;
    }

    /**
     * @param horse
     *            the horse to set
     */
    public void setHorse(String horse) {
        this.horse = horse;
    }

}
