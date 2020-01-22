package com.viannnosrat.montyhall.contoller;

import com.viannnosrat.montyhall.model.GameResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MontyHallControllerTest {

    @Value("${local.server.port}")
    private int localServerPort;

    @Test
    public void testSwitchGame() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:{port}/play";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("port", String.valueOf(localServerPort));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("iterations", "10000")
                .queryParam("strategy", "SWITCH");

        ResponseEntity<GameResult> response = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(), HttpMethod.POST,
                null, GameResult.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().getNumberOfWins(), greaterThan(5000));
        assertThat(response.getBody().getGameStrategy().toString(), is("SWITCH"));
    }

    @Test
    public void testKeepGame() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:{port}/play";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("port", String.valueOf(localServerPort));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("iterations", "10000")
                .queryParam("strategy", "KEEP");

        ResponseEntity<GameResult> response = restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(), HttpMethod.POST,
                null, GameResult.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().getNumberOfWins(), lessThan(5000));
        assertThat(response.getBody().getGameStrategy().toString(), is("KEEP"));
    }

    @Test
    public void return400InvalidStategy() throws IllegalArgumentException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:{port}/play";

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("port", String.valueOf(localServerPort));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("iterations", "10000")
                .queryParam("strategy", "KEE");
        try{
            restTemplate.exchange(builder.buildAndExpand(uriParams).toUri(), HttpMethod.POST,
                    null, GameResult.class);
        }catch(HttpClientErrorException e){
            assertEquals(400, e.getRawStatusCode());
        }


    }

    }



