package com.example.lab6.view;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Route(value = "mainPage.it")
public class MainWizardView extends HorizontalLayout {
    private static int i = 0;
    private Wizard wizard;
    private TextField txt1, txt3;
    private NumberField nf1;
    private RadioButtonGroup rb1;
    private ComboBox cb1, cb2, cb3;
    private Button btn1, btn2, btn3, btn4, btn5;
    private HorizontalLayout h1;
    private Notification n1;
    private VerticalLayout v1;
    public MainWizardView(){
        txt1 = new TextField();
        txt1.setPlaceholder("Fullname");
        rb1 = new RadioButtonGroup<String>();
        rb1.setLabel("Gender : ");
        rb1.setItems("Male", "Female");
        cb1 = new ComboBox<String>();
        cb1.setItems("Student", "Teacher");
        cb1.setPlaceholder("Position");
        nf1 = new NumberField();
        nf1.setPrefixComponent(new Span("$"));
        cb2 = new ComboBox<String>();
        cb2.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        cb2.setPlaceholder("School");
        cb3 = new ComboBox<String>();
        cb3.setItems("Gryffindor", "Ravenclaw", "Hufflepuf", "Slyther");
        cb3.setPlaceholder("House");
        btn1 = new Button("<<");
        btn2 = new Button("Create");
        btn3 = new Button("Update");
        btn4 = new Button("Delete");
        btn5 = new Button(">>");
        txt3 = new TextField();
        txt3.setLabel("Index");
        txt3.setValue(String.valueOf(i));
        n1 = new Notification();
        v1 = new VerticalLayout();
        h1 = new HorizontalLayout();
        h1.add(btn1, btn2, btn3, btn4, btn5);
        v1.add(txt1, rb1, cb1, nf1, cb2, cb3, h1, txt3, n1);
        this.add(v1);
        btn1.addClickListener(event -> {
            Wizards out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            if(i == 0){
                i=0;
            }else{
                i-=1;
            }
            wizard = out.model.get(i);
            txt3.setValue(String.valueOf(i));

            txt1.setValue(String.valueOf(out.model.get(i).getName()));
            if(String.valueOf(out.model.get(i).getSex()).equals("f")){
                rb1.setValue("Female");
            }else {
                rb1.setValue("Male");
            }
            nf1.setValue((out.model.get(i).getMoney()));

            cb1.setValue(String.valueOf(out.model.get(i).getPosition()));
            cb2.setValue(String.valueOf(out.model.get(i).getSchool()));
            cb3.setValue(String.valueOf(out.model.get(i).getHouse()));
        });
        btn5.addClickListener(event -> {
            Wizards out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            if(i == out.model.size()-1){
                i = out.model.size()-1;
            }else {
                i+=1;
            }
            wizard = out.model.get(i);
            txt3.setValue(String.valueOf(i));

            txt1.setValue(String.valueOf(out.model.get(i).getName()));
            if(String.valueOf(out.model.get(i).getSex()).equals("f")){
                rb1.setValue("Female");
            }else {
                rb1.setValue("Male");
            }
            if(i < 0){
                txt1.setValue("");
                txt3.setValue("");
                cb1.setValue(null);
                rb1.setItems("");
                cb2.setValue(null);
                cb3.setValue(null);
                nf1.setValue(null);
            }
            nf1.setValue((out.model.get(i).getMoney()));
            cb1.setValue(String.valueOf(out.model.get(i).getPosition()));
            cb2.setValue(String.valueOf(out.model.get(i).getSchool()));
            cb3.setValue(String.valueOf(out.model.get(i).getHouse()));
        });
        btn2.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("name", txt1.getValue());
            String sex = "";
            if (rb1.getValue().equals("Male")) {
                sex = "m";
            } else if (rb1.getValue().equals("Female")) {
                sex = "f";
            }
            formData.add("sex", sex);
            formData.add("school", (String) cb2.getValue());
            formData.add("house", (String) cb3.getValue());
            formData.add("money", nf1.getValue()+"");
            formData.add("position", cb1.getValue().toString().toLowerCase());

            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            n1 = Notification.show("Wizard has been Created");
        });
        btn3.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", wizard.get_id());
            formData.add("name", txt1.getValue());
            String sex = "";
            if (rb1.getValue().equals("Male")) {
                sex = "m";
            } else if (rb1.getValue().equals("Female")) {
                sex = "f";
            }
            formData.add("sex", sex);
            formData.add("school", (String) cb2.getValue());
            formData.add("house", (String) cb3.getValue());
            formData.add("money", nf1.getValue()+"");
            formData.add("position", cb1.getValue().toString().toLowerCase());

            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            n1 = Notification.show("Wizard has been Updated");
        });
        btn4.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("name", txt1.getValue());

            Boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            n1 = Notification.show("Wizard has benn Deleted");
        });
    }
}
