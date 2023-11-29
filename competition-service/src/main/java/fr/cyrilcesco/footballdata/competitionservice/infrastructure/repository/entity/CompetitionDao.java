package fr.cyrilcesco.footballdata.competitionservice.infrastructure.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="competitionsinformation")
public class CompetitionDao {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
}
