package spring.project.best.practices;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import spring.project.best.practices.controllers.MainController;
import spring.project.best.practices.entity.MovieEntity;
import spring.project.best.practices.mappers.MovieMapper;
import spring.project.best.practices.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// Spring Boot Tests point to our main application --> will be launched with the same config as our app

// don't forget the two mocks if you want to autowire the mock mvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class RestTestsWithMockMvc {

    // if we want to test a web layer without having to start the web container - we simulate the web env
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private MovieMapper movieMapper;


    @Test
    // to simulate the security filter chain with a specific user
    @WithMockUser(username = "user", password = "123", roles = "ADMIN")
    public void testFindAllMovies() throws Exception {
        // Préparer les objets de test
        MovieEntity movieEntity1 = new MovieEntity();
        MovieEntity movieEntity2 = new MovieEntity();
        List<MovieEntity> movieEntities = Arrays.asList(movieEntity1, movieEntity2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<MovieEntity> moviePage = new PageImpl<>(movieEntities, pageable, movieEntities.size());

        // Simuler le comportement des dépendances
        when(movieRepository.findAll(any(Pageable.class))).thenReturn(moviePage);
        when(movieMapper.toDto(any(MovieEntity.class))).thenAnswer(invocation -> {
            MovieEntity entity = invocation.getArgument(0);
            return new MainController.Movie(entity.getId(), entity.getName()); // Transformer l'entité en DTO
        });

        // Effectuer un appel MockMvc
        mockMvc.perform(get("/movies") // Remplacer par l'URL réelle de votre endpoint
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(movieEntities.size()))
                .andExpect(jsonPath("$.content[0].id").value(movieEntity1.getId()))
                .andExpect(jsonPath("$.content[1].id").value(movieEntity2.getId()));
    }


}
