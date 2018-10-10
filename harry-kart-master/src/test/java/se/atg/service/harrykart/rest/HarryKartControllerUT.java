package se.atg.service.harrykart.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.hamcrest.core.Is.is;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import se.atg.service.harrykart.jaxb.HarryKartType;
import se.atg.service.harrykart.jaxb.LaneType;
import se.atg.service.harrykart.jaxb.LoopType;
import se.atg.service.harrykart.jaxb.ParticipantType;
import se.atg.service.harrykart.jaxb.PowerUpsType;
import se.atg.service.harrykart.jaxb.StartListType;
import se.atg.service.harrykart.model.HarryKartRaceResult;
import se.atg.service.harrykart.model.ParticipantPerformance;
import se.atg.service.harrykart.service.HarryKartService;


@WebMvcTest(HarryKartController.class)
public class HarryKartControllerUT extends BaseUnitTest{
	
	
	@Autowired
    private MockMvc mvc;
	
	@Mock
	private StartListType startListType;
	
	
	
	private HarryKartType harryKartType;
	
	@Mock
	private ParticipantType participantType;
	
	@Mock
	private HarryKartRaceResult ranking;
	
	@Mock
	private PowerUpsType powerUpsType;
	
	@Mock
	private LoopType loopType;
	
	@MockBean
	private HarryKartController harryKartController;
	
	@MockBean
	private HarryKartService harryKartService; 
	
	
	@Test
	public void playHarryKart() throws Exception{
		//Arrange
		LoopType loopType = new LoopType();
		LaneType laneType = new LaneType();
		laneType.setNumber(new BigInteger("1"));
		laneType.setValue(new BigInteger("1"));
		ParticipantType participantType = new ParticipantType();
		participantType.setBaseSpeed(new BigInteger("10"));
		participantType.setLane(new BigInteger("1"));
		participantType.setName("Harry");
		loopType.setNumber(new BigInteger("1"));
		loopType.getLane().add(laneType);
		HarryKartType harrykartType = new HarryKartType();
		PowerUpsType powerUpsType = new PowerUpsType();
		powerUpsType.getLoop().add(loopType);
		StartListType startListType = new StartListType();
		startListType.getParticipant().add(participantType);
		harrykartType.setNumberOfLoops(new BigInteger("1"));
		harrykartType.setStartList(startListType);
		harrykartType.setPowerUps(powerUpsType);
		HarryKartRaceResult harryKartRaceResult = new HarryKartRaceResult();
		
		List<ParticipantPerformance> participantPerformances = new ArrayList<ParticipantPerformance>();
		ParticipantPerformance participantPerformance = new ParticipantPerformance();
		participantPerformance.setHorse("Harry");
		participantPerformance.setPosition(1);
		participantPerformances.add(participantPerformance);
		harryKartRaceResult.setRanking(participantPerformances);
	//	ResponseEntity<HarryKartRaceResult> response = new ResponseEntity<HarryKartRaceResult>(harryKartRaceResult,HttpStatus.OK);
		//when(harryKartService.processHarryKartRace(harrykartType)).thenReturn(participantPerformances);
		//doNothing().when(ranking).setRanking(participantPerformances);
		//Act
		given(harryKartController.playHarryKart(harrykartType)).willReturn(new ResponseEntity<HarryKartRaceResult>(harryKartRaceResult,HttpStatus.OK));
		/*mvc.perform(post("/api/play").contentType(APPLICATION_XML))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].horse", is("Harry")));*/
		
		MockHttpServletResponse response = mvc.perform(post("/api/play").contentType(MediaType.APPLICATION_XML)).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		
	}
	

}
