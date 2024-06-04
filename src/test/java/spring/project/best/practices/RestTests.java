package spring.project.best.practices;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class RestTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testFindAllMovies() {
		// Setup Basic Auth
		TestRestTemplate authenticatedRestTemplate = restTemplate.withBasicAuth("user", "123");

		// Setup URL
		URI uri = URI.create("/movies");

		// Create request entity

		// Perform request
		// for the others tests we need to use the exchange method
		ResponseEntity<String> response = authenticatedRestTemplate.getForEntity(String.valueOf(uri), String.class);

		// Assert response
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
	}

}
