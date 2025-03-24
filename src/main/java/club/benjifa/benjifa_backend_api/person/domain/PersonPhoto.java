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
public class PersonPhoto implements Serializable {
    @Serial
    private static final long serialVersionUID = -493915467670722691L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private byte[] fileContent;

    @Column(length = 50, nullable = false)
    private String mimeType;

    private String fileName;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private LocalDateTime createAt;


    @Column(nullable = false, length = 100, columnDefinition = "varchar(100)")
    private String photoUrl;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Person.class)
    private Person person;
}
