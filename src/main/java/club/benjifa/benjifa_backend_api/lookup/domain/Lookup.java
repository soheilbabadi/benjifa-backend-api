package club.benjifa.benjifa_backend_api.lookup.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.person.domain.Person;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "lookup", indexes = {
        @Index(name = "lookup_category_id_index", columnList = "category_id,category_title"),})

public class Lookup implements Serializable {
    @Serial
    private static final long serialVersionUID = -3496216606072499139L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int categoryId;

    @Column(nullable = false, columnDefinition = "varchar(50)", length = 50)
    private String categoryTitle;

    @Column(nullable = false, columnDefinition = "varchar(50)", length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "varchar(500)", length = 500)
    private String description;

    @Column(nullable = false, columnDefinition = "varchar(50)", length = 50)
    private String value;

    private Integer parentId;

    private int sortOrder;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Person.class, mappedBy = "city")
    private List<Person> personCity;




}