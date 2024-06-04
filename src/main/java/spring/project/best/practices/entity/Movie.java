package spring.project.best.practices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;


    public Movie() {

    }
}
