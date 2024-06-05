package spring.project.best.practices;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

// Spring Boot Tests point to our main application --> will be launched with the same config as our app

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
// active profile is usually used only for tests
// for normal profles we use @Profile
@ActiveProfiles("test")
@Slf4j
public class RestTests {

	// used to inject a bean that is defined somewhere else
	// Spring will look in the app context and search for the bean with this type & name
	// Autowired is used on the constructor if we have multiple ones
	@Autowired
	private TestRestTemplate restTemplate;
	// The testRestTemplate is not a real rest template
	// it accepts relative paths
	// swallows the exception so the test doesnt fail and we can assert the status
	// ignores cookies & redirections
	// it links directly to the random port we have defined before
	// ALWAYS DEFINE A WEB ENVIRONMENT WHEN YOU DEFINED A TESTRESTTEMPLATE

	// if we want to test a web layer without having to start the web container - we simulate the web env
	@Autowired
	MockMvc mockMvc;

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
