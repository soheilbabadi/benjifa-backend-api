package club.benjifa.benjifa_backend_api.lookup.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Country implements Serializable {

    @Serial
    private static final long serialVersionUID = 5034368581474940878L;

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(6)", length = 6)
    private String id;

    @Column(name = "title", columnDefinition = "VARCHAR(20)", length = 20)
    private String title;

    @Column(columnDefinition = "VARCHAR(6)", length = 6)
    private String callingCode;


    @OneToMany(targetEntity = City.class, mappedBy = "country", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<City> cities;



}
