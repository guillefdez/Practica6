package com.icai.practicas.e2e;

import com.icai.practicas.controller.ProcessController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.BDDAssertions.then;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessControllerTest {

    @LocalServerPort
    private int port;

    private String fullName = "Rafa Nadal";
    private String dni = "77777777B";
    private String telefono = "+34 666666666";

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrlProcess(){
        return "http://localhost:"+port+"/api/v1/process-step1";
    }

    private String baseUrlProcessLegacy(){
        return "http://localhost:" + port + "/api/v1/process-step1-legacy";
    }

    private void test_login_ok(ProcessController.DataRequest dataPost){
        String address = this.baseUrlProcess();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(dataPost, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

//        comprobamos que en el json va la palabra KO
        then(result.getBody()).isEqualTo("{\"result\":\"OK\"}");
    }

    @Test
    public void test_login_ok() {

        ProcessController.DataRequest dataPrueba = new ProcessController.DataRequest(
                fullName,
                dni,
                telefono
        );

        this.test_login_ok(dataPrueba);
    }

    private void test_login_not_ok(ProcessController.DataRequest dataPost){
        String address = this.baseUrlProcess();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(dataPost, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

//        comprobamos que en el json va la palabra KO
        then(result.getBody()).isEqualTo("{\"result\":\"KO\"}");
    }

    @Test
    public void test_login_ko() {

        ProcessController.DataRequest dataPost = new ProcessController.DataRequest(
                fullName,
                dni + "8888",
                telefono
        );

        this.test_login_not_ok(dataPost);

        dataPost = new ProcessController.DataRequest(
                fullName,
                dni,
                "88888888888888888"
        );

        this.test_login_not_ok(dataPost);
    }

    private MultiValueMap<String, String> getDataPost(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("fullName", "Jorge");
        data.add("dni", "77777777B");
        data.add("telefono", "+34 666666666");
        return data;
    }

    @Test
    public void test_login_ok_legacy() {
        String address = this.baseUrlProcessLegacy();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(this.getDataPost(), headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        then(result.getBody()).contains("Muchas gracias por enviar los datos");
    }

    @Test
    public void test_login_ko_legacy() {
        String address = this.baseUrlProcessLegacy();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> dataPost = this.getDataPost();

        // le damos un valor incorrecto de telefono (corto)
        dataPost.set("telefono", "888");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(dataPost, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        then(result.getBody()).contains("Hemos tenido un problema con su solicitud");
    }
}

