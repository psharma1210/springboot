package se.atg.service.harrykart.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import se.atg.service.harrykart.jaxb.HarryKartType;
import se.atg.service.harrykart.jaxb.LaneType;
import se.atg.service.harrykart.jaxb.LoopType;
import se.atg.service.harrykart.jaxb.ParticipantType;
import se.atg.service.harrykart.model.HarryKartRaceResult;
import se.atg.service.harrykart.model.ParticipantPerformance;
import se.atg.service.harrykart.service.HarryKartService;


public class HarryKartServiceUnitTest extends BaseUnitTest {

    @Spy
    @InjectMocks
    private HarryKartService sut;

    @Test
    public void testProcessHarryKartRace() throws Exception {
        // Arrange
        List<BigInteger> speedInEachLane = new ArrayList<>();
        speedInEachLane.add(initialBaseSpeed);
        speedInEachLane.add(initialBaseSpeed);
        speedInEachLane.add(initialBaseSpeed);
        Map<Double, String> participantTime = new TreeMap<Double, String>();
        participantTime.put(time, horseName);
        HarryKartType harryKartType = setupHarryKartType();
        ParticipantType participantType = harryKartType.getStartList()
                .getParticipant().get(0);
        LoopType loopType = harryKartType.getPowerUps().getLoop().get(0);
        // Act
        List<ParticipantPerformance> participantPerformances = sut
                .processHarryKartRace(harryKartType);
        // Verify
        assertThat(participantPerformances.get(0).getHorse())
                .isEqualTo(horseName);
        assertThat(participantPerformances.get(0).getPosition())
                .isEqualTo(position);
        assertThat(harryKartType.getNumberOfLoops()).isEqualTo(numberOfLoops);
        verify(sut, times(1)).harryKartRanking(participantTime);
        verify(sut, times(1)).harryKartParticipantPerformance(initialBaseSpeed,
                participantType, loopType);
        verify(sut, atLeastOnce()).calculateHarryKartResult(speedInEachLane,
                totalTrackLength);
    }

    @Test
    public void testHarryKartParticipantPerformance() {
        // Arrange
        LaneType laneType = setupLaneType(new BigInteger("1"),
                new BigInteger("1"));
        LoopType loopType = setupLoopType(new BigInteger("1"), laneType);
        BigInteger actualSpeed = new BigInteger("11");
        ParticipantType participantType = setupParticipantType(initialBaseSpeed,
                new BigInteger("1"), horseName);
        // Act
        BigInteger modifiedSpeed = sut.harryKartParticipantPerformance(
                initialBaseSpeed, participantType, loopType);

        assertThat(modifiedSpeed).isEqualTo(actualSpeed);

    }

    @Test
    public void testCalculateHarryKartResult() {
        List<BigInteger> speedInEachLane = new ArrayList<BigInteger>();
        speedInEachLane.add(new BigInteger("30"));
        double actualTimeTaken = 100.0;
        double timeTaken = sut.calculateHarryKartResult(speedInEachLane,
                totalTrackLength);
        assertThat(timeTaken).isEqualTo(actualTimeTaken);
    }

    @Test
    public void testHarryKartRanking() {
        Map<Double, String> participantTime = new TreeMap<Double, String>();
        participantTime.put(9.5, "Harry");
        participantTime.put(10.00, "Kart");
        List<ParticipantPerformance> ParticipantPerformances = sut
                .harryKartRanking(participantTime);
        assertThat(ParticipantPerformances.size()).isEqualTo(2);
        assertThat(ParticipantPerformances.get(0).getHorse())
                .isEqualTo("Harry");
        assertThat(ParticipantPerformances.get(0).getPosition()).isEqualTo(1);
    }

    @Test
    public void testHarryKartRaceResult() throws Exception {
        // Arrange
        List<ParticipantPerformance> participantPerformances = new ArrayList<ParticipantPerformance>();
        ParticipantPerformance participantPerformance = new ParticipantPerformance();
        participantPerformance.setHorse(horseName);
        participantPerformance.setPosition(position);
        participantPerformances.add(participantPerformance);
        HarryKartType harryKartType = setupHarryKartType();
        HarryKartRaceResult ranking = Mockito.mock(HarryKartRaceResult.class);
        ResponseEntity<HarryKartRaceResult> response = new ResponseEntity<HarryKartRaceResult>(
                ranking, HttpStatus.OK);
        when(sut.processHarryKartRace(harryKartType))
                .thenReturn(participantPerformances);
        // Act
        response = sut.harryKartRaceResult(harryKartType);

        assertEquals(response.getBody().getRanking().size(), 1);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

}
