package club.benjifa.benjifa_backend_api.lookup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -320586669868048042L;


    private long id;

    private String title;

    private String timezone;

    private String coordinate;

    private CountryDto country;

}
