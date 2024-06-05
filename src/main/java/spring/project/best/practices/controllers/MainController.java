package spring.project.best.practices.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import spring.project.best.practices.entity.MovieEntity;
import spring.project.best.practices.service.MovieService;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

// combines Controller and ResponseBody

@RestController
@RequiredArgsConstructor
public class MainController {

    // if we have multiple beans of the name movieService
    // we would need to add a qualifier in the constructor to add the name of the bean to make sure we have the right one

    public final MovieService movieService;

    // use records to do the link with the entities
    // much quicker way than creating a class
    public record Movie(UUID id, String name) {}

    @GetMapping("/movies")
    private ResponseEntity<Page<Movie>> findAll(@PageableDefault(value = 1, page = 0) Pageable p) {
        Page<Movie> movies = this.movieService.findAll(p);
        return ResponseEntity.ok(movies);
    }


    @GetMapping("/movie/{id}")
    // use @RequestParam if we are in this format ?userid=1234
    // you can write required = false if you want to make it optional
    private ResponseEntity<Movie> findMovie(@PathVariable("id") UUID id) {
        Optional<Movie> movieOptional = this.movieService.findById(id);
        // use this
        // you can also use orElseThrow to throw a specific error
        return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/movie")
    private ResponseEntity<Movie> findMovie(Movie movie, UriComponentsBuilder builder) {
        URI location = this.movieService.save(movie, builder);
        return ResponseEntity.created(location).build();
    }

    // DONT FORGET
    // for GET --> ResponseEntity.ok
    // for POST --> ResponseEntity.created(urlLocation).build()

}
