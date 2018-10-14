package se.atg.service.harrykart.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.atg.service.harrykart.jaxb.HarryKartType;
import se.atg.service.harrykart.model.HarryKartRaceResult;
import se.atg.service.harrykart.service.HarryKartService;

@RestController
@RequestMapping("/api")
public class HarryKartController {

    @Autowired
    HarryKartService harryKartService;

    @RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
    public ResponseEntity<HarryKartRaceResult> playHarryKart(
            @RequestBody HarryKartType harryKartType) throws Exception {

        return harryKartService.harryKartRaceResult(harryKartType);
    }

}
