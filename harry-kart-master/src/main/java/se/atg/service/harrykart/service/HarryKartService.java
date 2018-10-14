package se.atg.service.harrykart.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import se.atg.service.harrykart.jaxb.HarryKartType;
import se.atg.service.harrykart.jaxb.LaneType;
import se.atg.service.harrykart.jaxb.LoopType;
import se.atg.service.harrykart.jaxb.ParticipantType;
import se.atg.service.harrykart.jaxb.StartListType;
import se.atg.service.harrykart.model.HarryKartRaceResult;
import se.atg.service.harrykart.model.ParticipantPerformance;

@Service
public class HarryKartService {

    /**
     * Initialize your logger here.
     */

    /**
     * return the list of participants performance in harrykart race.
     * 
     * @param harryKartType
     * @return
     * @throws Exception
     */
    public List<ParticipantPerformance> processHarryKartRace(
            HarryKartType harryKartType) throws Exception {

        List<ParticipantPerformance> ParticipantPerformances = null;

        try {

            StartListType startListType = harryKartType.getStartList();
            List<ParticipantType> participants = startListType.getParticipant();
            Map<Double, String> participantTime = new TreeMap<Double, String>();
            List<BigInteger> speedInEachLane = null;

            int loops = harryKartType.getNumberOfLoops().intValue();

            long totalTrackLength = 1000 * loops;

            for (ParticipantType participant : participants) {

                String participantName = participant.getName();
                speedInEachLane = new ArrayList<BigInteger>();
                BigInteger participantSpeed = participant.getBaseSpeed();

                for (int loopCount = 0; loopCount < loops; loopCount++) {

                    // Skip the first loop as its run with base speed

                    if ((loopCount > 0) && (loopCount <= harryKartType
                            .getPowerUps().getLoop().size())) {
                        LoopType loopType = harryKartType.getPowerUps()
                                .getLoop().get(loopCount - 1);
                        participantSpeed = harryKartParticipantPerformance(
                                participantSpeed, participant, loopType);
                    }
                    speedInEachLane.add(participantSpeed);
                }

                participantTime.put(calculateHarryKartResult(speedInEachLane,
                        totalTrackLength), participantName);
            }

            ParticipantPerformances = harryKartRanking(participantTime);

        } catch (Exception ex) {
            /**
             * We can handle different exception as per
             * RestResponseEntityExceptionHandler
             */
            throw new Exception(
                    "Bad Request Input XML is not proper" + ex.getCause());
        }

        return ParticipantPerformances;
    }

    /**
     * Process the participant performance for each lane.
     * 
     * @param participantSpeed
     * @param participantType
     * @param loopType
     * @return
     */
    public BigInteger harryKartParticipantPerformance(
            BigInteger participantSpeed, ParticipantType participantType,
            LoopType loopType) {

        BigInteger initialBaseSpeed = participantSpeed;

        for (LaneType laneType : loopType.getLane()) {

            if (participantType.getLane().equals(laneType.getNumber())) {
                initialBaseSpeed = initialBaseSpeed.add(laneType.getValue());
                break;
            }
        }
        return initialBaseSpeed;
    }

    /**
     * Return time taken by each participant to complete the race.
     * 
     * @param speedInEachLane
     * @param totalTrackLength
     * @return
     */
    public double calculateHarryKartResult(List<BigInteger> speedInEachLane,
            long totalTrackLength) {
        double timeTaken = 0;
        BigInteger totalSpeed = new BigInteger("0");

        for (BigInteger speed : speedInEachLane) {
            totalSpeed = totalSpeed.add(speed);
        }

        timeTaken = (double) totalTrackLength / totalSpeed.intValue();

        return timeTaken;
    }

    /**
     * Set ranking of race on basis of time taken by participant to complete the
     * race .
     * 
     * @param participantTime
     * @return
     */

    public List<ParticipantPerformance> harryKartRanking(
            Map<Double, String> participantTime) {

        List<ParticipantPerformance> ParticipantPerformances = new ArrayList<ParticipantPerformance>();

        int position = 1;

        for (Map.Entry<Double, String> participant : participantTime
                .entrySet()) {

            ParticipantPerformance participantPerformance = new ParticipantPerformance();
            participantPerformance.setHorse(participant.getValue());
            participantPerformance.setPosition(position++);
            ParticipantPerformances.add(participantPerformance);
        }

        return ParticipantPerformances;
    }

    /**
     * Return the ResponseEntity with defined return format {ranking: []}
     * 
     * @param harryKartyType
     * @return
     * @throws Exception
     */
    public ResponseEntity<HarryKartRaceResult> harryKartRaceResult(
            HarryKartType harryKartType) throws Exception {
        HarryKartRaceResult ranking = new HarryKartRaceResult();
        ranking.setRanking(processHarryKartRace(harryKartType));
        return new ResponseEntity<HarryKartRaceResult>(ranking, HttpStatus.OK);
    }

}
