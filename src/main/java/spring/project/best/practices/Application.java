package spring.project.best.practices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import spring.project.best.practices.entity.MovieEntity;
import spring.project.best.practices.repository.MovieRepository;

import java.util.stream.Stream;

// concatenates the ComponentScan, Configuration and EnableAutoConfiguration
// starts a tomcat server
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	// Here we initialize a bean that will have the name of the function so here initialize
	// by default a bean has a singleton scope, it is initialized once and then cache in the app context
	// we can change that because it isnt thread safe ie @Scope (scopeName = "new")
	// CommandLineRunners are beans that are detected by Boot and invoked before returning run and after beans are configured
	@Bean
	CommandLineRunner initialize(MovieRepository movieRepository) {
		return args -> {
			Stream.of("Star wars", "Indiana Jones", "The notebook").forEach(name -> {
				MovieEntity movieEntity = MovieEntity.builder().name(name).build();
				movieRepository.save(movieEntity);
			});
		};
	}

}
