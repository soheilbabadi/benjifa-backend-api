package club.benjifa.benjifa_backend_api.pet.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.exception.BenjiBusinessException;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.lookup.repository.LookupRepository;
import club.benjifa.benjifa_backend_api.person.application.PersonService;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;
import club.benjifa.benjifa_backend_api.pet.dto.PetDto;
import club.benjifa.benjifa_backend_api.pet.enums.AdoptionStatusEnum;
import club.benjifa.benjifa_backend_api.pet.enums.GenderEnum;
import club.benjifa.benjifa_backend_api.pet.enums.VaccinationStatusEnum;
import club.benjifa.benjifa_backend_api.pet.repository.PetRepository;
import club.benjifa.benjifa_backend_api.utils.UserUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl {

    private final PetRepository petRepository;
    private final LookupRepository lookupRepository;
    private final PersonService personService;
    private final UserUtils userUtils;

    public PetDto create(PetDto petDto, String token) {
        Pet pet = toEntity(petDto);
        pet.setPerson(userUtils.getPersonFromToken(token));
        pet.setCreateDate(LocalDateTime.now());
        Pet savedPet = petRepository.save(pet);
        return toDto(savedPet);
    }

    public PetDto update(PetDto petDto, String token) {
        Pet existingPet = petRepository.findById(petDto.getId())
                .orElseThrow(() -> new BenjiBusinessException.EntityNotFoundException("Pet not found"));

        String usernameFromToken = userUtils.getUsernameFromToken(token);
        if (!existingPet.getPerson().getUsername().equals(usernameFromToken)) {
            throw new BenjiBusinessException.UnauthorizedException("You are not authorized to Edit this pet");
        }
        BeanUtils.copyProperties(petDto, existingPet);
        existingPet.setPerson(userUtils.getPersonFromToken(token));
        Pet updatedPet = petRepository.save(existingPet);
        return toDto(updatedPet);
    }

    public void delete(String id, String token) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new BenjiBusinessException.EntityNotFoundException("Pet not found"));

        String usernameFromToken = userUtils.getUsernameFromToken(token);
        if (!pet.getPerson().getUsername().equals(usernameFromToken)) {
            throw new BenjiBusinessException.UnauthorizedException("You are not authorized to delete this pet");
        }
        petRepository.delete(pet);
    }

    public PetDto findById(String id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new BenjiBusinessException.EntityNotFoundException("Pet not found"));
        return toDto(pet);
    }

    public List<PetDto> findAllByOwner(String token) {
        Person person = userUtils.getPersonFromToken(token);
        List<Pet> pets = petRepository.findAllByPerson(person);
        return pets.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<PetDto> findInMyCity(String token) {
        Person searcher = userUtils.getPersonFromToken(token);
        Lookup city = searcher.getCity();
        List<Person> personsInCity = personService.findByCity(city);

        Set<Pet> petsInCity = personsInCity.stream()
                .flatMap(person -> petRepository.findAllByPerson(person)
                        .stream()).collect(Collectors.toSet());

        return petsInCity.stream().map(this::toDto)
                .collect(Collectors.toSet()).stream().limit(50).toList();
    }

    private Pet toEntity(PetDto petDto) {

        Person person = personService.findByUsername(petDto.getUsername());
        var brd = lookupRepository.findById(petDto.getBreedId())
                .orElseThrow(() -> new BenjiBusinessException.EntityNotFoundException("Breed not found"));

        return Pet.builder()
                .id(petDto.getId())
                .name(petDto.getName())
                .description(petDto.getDescription())
                .breed(brd)
                .birthDate(petDto.getBirthDate())
                .createDate(petDto.getCreateDate())
                .weight(petDto.getWeight())
                .height(petDto.getHeight())
                .gender(petDto.getGender() != null ? GenderEnum.valueOf(petDto.getGender()) : GenderEnum.UNKNOWN)
                .vaccinationStatus(petDto.getVaccinationStatus() != null ? VaccinationStatusEnum.valueOf(petDto.getVaccinationStatus()) : VaccinationStatusEnum.UNKNOWN)
                .neutered(petDto.isNeutered())
                .color(petDto.getColor())
                .adoptionStatus(petDto.getAdoptionStatus() != null ? AdoptionStatusEnum.valueOf(petDto.getAdoptionStatus()) : AdoptionStatusEnum.UNKNOWN)
                .person(person)
                .build();
    }


    private PetDto toDto(Pet pet) {

        return PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .description(pet.getDescription())
                .breed(pet.getBreed() != null ? pet.getBreed().getTitle() : null)
                .breedId(pet.getBreed().getId())
                .birthDate(pet.getBirthDate())
                .createDate(pet.getCreateDate())
                .weight(pet.getWeight())
                .height(pet.getHeight())
                .gender(pet.getGender() != null ? pet.getGender().getValue() : GenderEnum.UNKNOWN.getValue())
                .vaccinationStatus(pet.getVaccinationStatus() != null ? pet.getVaccinationStatus().getValue() : VaccinationStatusEnum.UNKNOWN.getValue())
                .neutered(pet.isNeutered())
                .color(pet.getColor())
                .adoptionStatus(pet.getAdoptionStatus() != null ? pet.getAdoptionStatus().getValue() : AdoptionStatusEnum.UNKNOWN.getValue())
                .ownerName(pet.getPerson() != null ? pet.getPerson().getFirstName() : null)
                .username(pet.getPerson().getUsername())
                .build();
    }
}
