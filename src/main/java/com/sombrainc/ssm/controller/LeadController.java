package com.sombrainc.ssm.controller;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.service.LeadService;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class LeadController {

    private final LeadService leadService;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping(path = "/lead/{id}/state",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public LeadStates changeState(@RequestParam("event") LeadEvents event, @PathVariable String id)
            throws Exception {
        return leadService.updateState(event, id);
    }
}
