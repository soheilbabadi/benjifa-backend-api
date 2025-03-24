package club.benjifa.benjifa_backend_api.person.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.lookup.domain.City;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class PersonAddress implements Serializable {
    @Serial
    private static final long serialVersionUID = 6999360515731899520L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)")
    private String address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = City.class)
    private City city;


    @Column(nullable = false, length = 20, columnDefinition = "varchar(20)")
    private String postalCode;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Person.class)
    private Person person;



}
