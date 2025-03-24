package club.benjifa.benjifa_backend_api.lookup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CountryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2631946453207990518L;

    private String id;

    private String title;

    private String callingCode;

}
