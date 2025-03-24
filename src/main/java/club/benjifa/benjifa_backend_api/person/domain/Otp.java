package club.benjifa.benjifa_backend_api.person.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Otp implements Serializable {
    @Serial
    private static final long serialVersionUID = -5681560227281055259L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 8, columnDefinition = "VARCHAR(8)")
    private String otp;
    @Column(nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private boolean used;
}
