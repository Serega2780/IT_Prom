package org.test.hrdept;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.test.hrdept.domain.Profession;

import java.util.List;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfessionRestControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void getProfessionById_test() {
        // act
        Profession profession = restTemplate.getForObject("http://localhost:" + port + "/rest/prof/edit/?id=1", Profession.class);
        // assert
        assertThat(profession)
                .extracting(Profession::getId, Profession::getName, Profession::getDescription)
                .containsExactly(1, "sys.admin.", "sys.admin.desc.");
    }

    @Test
    @Order(2)
    void getProfessionByName_test() {
        // act
        final String prof = "chief engineer";
        Profession profession = restTemplate.getForObject("http://localhost:" + port + "/rest/prof/prof/?name=" + prof,
                Profession.class);
        // assert
        assertThat(profession)
                .extracting(Profession::getId, Profession::getName, Profession::getDescription)
                .containsExactly(3, "chief engineer", "chief engineer desc.");
    }

    @Test
    @Order(3)
    void getProfessions_test() {
        ResponseEntity<List<Profession>> response =
                restTemplate
                        .exchange(
                                "http://localhost:" + port + "/rest/prof/profs",
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<Profession>>() {
                                });

        assertThat(response.getStatusCode().equals(HttpStatus.OK));

        List<Profession> professions = response.getBody();
        assertThat(professions.get(2))
                .extracting(Profession::getId, Profession::getName, Profession::getDescription)
                .containsExactly(3, "chief engineer", "chief engineer desc.");
    }

    @Test
    @Order(4)
    void insertProfession_test() {
        final String prof = "newName";

        Profession profession = new Profession(prof, "newDescription");

        ResponseEntity<Profession> response =
                restTemplate.postForEntity("http://localhost:" + port + "/rest/prof/insert", profession, Profession.class);

        assertThat(response.getStatusCode().equals(HttpStatus.OK));

        profession = restTemplate.getForObject("http://localhost:" + port + "/rest/prof/prof/?name=" + prof,
                Profession.class);

        assertThat(profession!=null);
        assertThat(profession.getName().equals("newName"));

    }

    @Test
    @Order(5)
    void updateProfession_test() {
        final String prof = "newName";
        final String newProf = "newNewName";


        insertProfession_test();

        Profession profession = restTemplate.getForObject("http://localhost:" + port + "/rest/prof/prof/?name=" + prof,
                Profession.class);
        profession.setName("newNewName");

        restTemplate.put("http://localhost:" + port + "/rest/prof/update", profession);

        profession = restTemplate.getForObject("http://localhost:" + port + "/rest/prof/prof/?name=" + newProf,
                Profession.class);
        assertThat(profession!=null);
        assertThat(profession.getName().equals("newNewName"));

    }

    @Test
    @Order(6)
    void deleteProfession_test() {
        updateProfession_test();

        restTemplate.delete("http://localhost:" + port + "/rest/prof/delete?id=4");
        Profession profession = restTemplate.getForObject("http://localhost:" + port + "/rest/prof/edit/?id=4",
                Profession.class);
        assertThat(profession==null);
    }
}
