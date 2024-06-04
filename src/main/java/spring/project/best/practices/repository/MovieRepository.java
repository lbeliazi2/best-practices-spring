package spring.project.best.practices.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import spring.project.best.practices.entity.Movie;

import java.util.Optional;
import java.util.UUID;

// if you want a normal repository you can use CRUDRepository --> doesnt automatically have PagingAndSorting
// if you want both you can also use Jpa
// if you want pages and sorting you can use PagingAndSorting
@Repository
public interface MovieRepository extends CrudRepository<Movie, UUID>,PagingAndSortingRepository<Movie, UUID> {

    Optional<Movie> findMovieById(UUID id);

}
