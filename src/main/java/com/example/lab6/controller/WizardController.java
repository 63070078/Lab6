package com.example.lab6.controller;

import com.example.lab6.pojo.Wizards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.lab6.pojo.Wizard;
import com.example.lab6.repository.WizardService;

import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;
//    protected Wizards wizards = new Wizards();
    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public Wizards getWizards() {
        Wizards wizards = new Wizards();
        wizards.model.addAll(wizardService.retrieveWizard());
        return wizards;
    }
    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> addWizard(@RequestBody MultiValueMap<String, String> body){
        Map<String, String> data = body.toSingleValueMap();
       Wizard n = wizardService.addWizard(
                new Wizard(null, data.get("sex"), data.get("name"), data.get("school"), data.get("house"),
                        Double.parseDouble(data.get("money")), data.get("position"))
        );
        return ResponseEntity.ok(n);
    }
    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestBody MultiValueMap<String, String> body){
        Map<String, String> data = body.toSingleValueMap();
        Wizard wizard = new Wizard(data.get("id"), data.get("sex"), data.get("name"), data.get("school"), data.get("house"),
                        Double.parseDouble(data.get("money")), data.get("position"));
        if(wizard != null){
            wizardService.updateWizard(wizard);
            return true;
        }else {
            return false;
        }
    }
    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public boolean deleteWizard(@RequestBody MultiValueMap<String, String> body){
        Map<String, String> data = body.toSingleValueMap();
        Wizard wizard = wizardService.retrieveWizardByName(data.get("name"));
        return wizardService.deleteWizard(wizard);
    }
}