package club.benjifa.benjifa_backend_api.lookup.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.person.domain.PersonAddress;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

public class City implements Serializable {

    @Serial
    private static final long serialVersionUID = 5034368581474940878L;


    @Id
    private long id;

    @Column(name = "title", columnDefinition = "VARCHAR(50)", length = 50)
    private String title;

    @Column(name = "timezone", columnDefinition = "VARCHAR(30)", length = 30)
    private String timezone;

    @Column(name = "coordinate", columnDefinition = "VARCHAR(50)", length = 50)
    private String coordinate;

    @ManyToOne(targetEntity = Country.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Country country;

    @OneToMany(targetEntity = PersonAddress.class, mappedBy = "city", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PersonAddress> personAddresses;
}
