package se.atg.service.harrykart.rest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.atg.service.harrykart.jaxb.HarryKartType;
import se.atg.service.harrykart.jaxb.LaneType;
import se.atg.service.harrykart.jaxb.LoopType;
import se.atg.service.harrykart.jaxb.ParticipantType;
import se.atg.service.harrykart.jaxb.PowerUpsType;
import se.atg.service.harrykart.jaxb.StartListType;
import se.atg.service.harrykart.model.HarryKartRaceResult;
import se.atg.service.harrykart.model.ParticipantPerformance;

@RunWith(MockitoJUnitRunner.class)
@Ignore public class BaseUnitTest {

    protected static final  String horseName = "Harry";
    protected static final int position = 1;
    protected static final double time = 100.0;
    protected static final BigInteger numberOfLoops = new BigInteger("3");
    protected static int totalTrackLength = 1000 * numberOfLoops.intValue();
    protected static BigInteger initialBaseSpeed = new BigInteger("10");

    protected HarryKartType setupHarryKartType() {

        LaneType laneType = setupLaneType(new BigInteger("1"),
                new BigInteger("0"));
        LoopType loopType = setupLoopType(new BigInteger("1"), laneType);

        ParticipantType participantType = setupParticipantType(initialBaseSpeed,
                new BigInteger("1"), horseName);

        PowerUpsType powerUpsType = new PowerUpsType();
        StartListType startListType = new StartListType();

        HarryKartType harrykartType = new HarryKartType();

        powerUpsType.getLoop().add(loopType);
        startListType.getParticipant().add(participantType);
        harrykartType.setNumberOfLoops(numberOfLoops);
        harrykartType.setStartList(startListType);
        harrykartType.setPowerUps(powerUpsType);

        return harrykartType;
    }

    protected LoopType setupLoopType(BigInteger number, LaneType laneType) {
        LoopType loopType = new LoopType();
        loopType.setNumber(number);
        loopType.getLane().add(laneType);
        return loopType;
    }

    protected LaneType setupLaneType(BigInteger number, BigInteger value) {
        LaneType laneType = new LaneType();
        laneType.setNumber(number);
        laneType.setValue(value);
        return laneType;
    }

    protected ParticipantType setupParticipantType(BigInteger speed,
            BigInteger lane, String name) {
        ParticipantType participantType = new ParticipantType();
        participantType.setBaseSpeed(speed);
        participantType.setLane(lane);
        participantType.setName(name);
        return participantType;
    }

    protected HarryKartRaceResult setupHarryKartRaceResult() {
        HarryKartRaceResult harryKartRaceResult = new HarryKartRaceResult();
        List<ParticipantPerformance> ranking = new ArrayList<ParticipantPerformance>();
        ParticipantPerformance participantPerformance = new ParticipantPerformance();
        participantPerformance.setHorse(horseName);
        participantPerformance.setPosition(1);
        ranking.add(participantPerformance);
        harryKartRaceResult.setRanking(ranking);

        return harryKartRaceResult;
    }

}
