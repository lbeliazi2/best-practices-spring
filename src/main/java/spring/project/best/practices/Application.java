package spring.project.best.practices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

	// remplir la base de données
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
