package se.atg.service.harrykart.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.File;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import se.atg.service.harrykart.Exception.RestResponseEntityExceptionHandler;
import se.atg.service.harrykart.jaxb.HarryKartType;
import se.atg.service.harrykart.model.HarryKartRaceResult;
import se.atg.service.harrykart.service.HarryKartService;

public class HarryKartControllerUnitTest extends BaseUnitTest {

    private MockMvc mvc;

    @Mock
    private HarryKartService harryKartService;

    @InjectMocks
    private HarryKartController sut;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testPlayHarryKart() throws Exception {
        // Arrange
        HarryKartType harryKartType = setupHarryKartType();
        ResponseEntity<HarryKartRaceResult> responseEntity = new ResponseEntity<HarryKartRaceResult>(
                setupHarryKartRaceResult(), HttpStatus.OK);
        when(harryKartService.harryKartRaceResult(harryKartType))
                .thenReturn(responseEntity);
        // Act
        responseEntity = sut.playHarryKart(harryKartType);
        // Verify
        assertThat(responseEntity.getStatusCodeValue())
                .isEqualTo(HttpStatus.OK.value());
        verify(harryKartService, times(1)).harryKartRaceResult(harryKartType);
    }

    @Test
    public void tesPlayHarryKartAcceptXML() throws Exception {
        // Arrange
        File xmlContent = new File(this.getClass().getClassLoader()
                .getResource("input_1.xml").getFile());
        String body = new String(Files.readAllBytes(xmlContent.toPath()));
        // when
        MockHttpServletResponse response = mvc
                .perform(post("/api/play").content(body)
                        .contentType(MediaType.APPLICATION_XML))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void tesPlayHarryKartPostType() throws Exception {
        // Arrange
        File xmlContent = new File(this.getClass().getClassLoader()
                .getResource("input_1.xml").getFile());
        String body = new String(Files.readAllBytes(xmlContent.toPath()));
        // when
        MockHttpServletResponse response = mvc
                .perform(get("/api/play").content(body)
                        .contentType(MediaType.APPLICATION_XML))
                .andReturn().getResponse();
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

}
