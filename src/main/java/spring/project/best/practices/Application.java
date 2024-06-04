package spring.project.best.practices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import spring.project.best.practices.entity.Movie;
import spring.project.best.practices.repository.MovieRepository;

import java.util.stream.Stream;

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
				Movie movie = Movie.builder().name(name).build();
				movieRepository.save(movie);
			});
		};
	}

}
