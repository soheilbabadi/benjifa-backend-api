package club.benjifa.benjifa_backend_api.person.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.person.enums.RoleEnum;
import club.benjifa.benjifa_backend_api.person.enums.UserStatusEnum;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;
import club.benjifa.benjifa_backend_api.pet.domain.PetPhotoComment;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "person", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone"),
        @UniqueConstraint(columnNames = "username")
})
public class Person implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 2693162418137228206L;


    @Id
    @Column(nullable = false, length = 36, columnDefinition = "varchar(36)")
    private String id;

    @Column(nullable = false, length = 10, columnDefinition = "varchar(10)", unique = true)
    private String nationalId;

    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)")
    private String firstName;

    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)")
    private String lastname;

    @Email(message = "Email should be valid")
    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)", unique = true)
    private String email;

    @Column(nullable = false, length = 15, columnDefinition = "varchar(15)", unique = true)
    private String phone;


    @Column(nullable = false, length = 50, columnDefinition = "varchar(50)", unique = true)
    private String username;

    @Column(nullable = false, length = 500, columnDefinition = "varchar(500)")
    private String password;

    @Column(nullable = false)
    private LocalDateTime registerAt;

    @Column(nullable = false)
    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    private boolean accountNonLocked;

    @Column(nullable = false)
    private boolean accountNonExpired;

    @Column(nullable = false)
    private boolean credentialsNonExpired;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Lookup.class)
    private Lookup city;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = PersonPhoto.class, mappedBy = "person")
    private PersonPhoto personPhoto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatusEnum status;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "person", targetEntity = Pet.class)
    private Set<Pet> petSet;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "person", targetEntity = PetPhotoComment.class)
    private List<PetPhotoComment> petPhotoComments;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role.name())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }



}
