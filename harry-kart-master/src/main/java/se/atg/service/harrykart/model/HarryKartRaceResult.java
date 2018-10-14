package se.atg.service.harrykart.model;

import java.io.Serializable;
import java.util.List;

public class HarryKartRaceResult implements Serializable {

    private static final long serialVersionUID = -4935713277830891259L;

    List<ParticipantPerformance> ranking;

    /**
     * @return the ranking
     */
    public List<ParticipantPerformance> getRanking() {
        return ranking;
    }

    /**
     * @param ranking
     *            the ranking to set
     */
    public void setRanking(List<ParticipantPerformance> ranking) {
        this.ranking = ranking;
    }

}
