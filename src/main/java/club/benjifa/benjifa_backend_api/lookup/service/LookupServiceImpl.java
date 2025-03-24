package club.benjifa.benjifa_backend_api.lookup.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.lookup.dto.LookupDto;
import club.benjifa.benjifa_backend_api.lookup.repository.LookupRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LookupServiceImpl implements LookupService {

    private final LookupRepository lookupRepository;

    @Override
    public List<LookupDto> findAllByCategoryId(int categoryId) {
        return lookupRepository.findAllByCategoryId(categoryId)
                .stream().map(this::convertToDto).toList();
    }

    @Override
    public List<LookupDto> findAllByParentId(int parentId) {
        return lookupRepository.findAllByParentId(parentId)
                .stream().map(this::convertToDto).toList();
    }

    @Override
    public LookupDto findByValue(String value) {
        return convertToDto(lookupRepository.findByValue(value));
    }

    @Override
    public LookupDto findById(int id) {
        return convertToDto(Objects.requireNonNull(lookupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""))));
    }

    @Override
    public List<LookupDto> findAllByTitleContaining(String title) {
        return lookupRepository.findAllByTitleContaining(title)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public LookupDto create(LookupDto lookupDto) {
        Lookup lookup = Lookup.builder()
                .categoryId(lookupDto.categoryId())
                .parentId(lookupDto.parentId())
                .title(lookupDto.title())
                .value(lookupDto.value())
                .categoryTitle(lookupDto.categoryTitle())
                .description(lookupDto.description())
                .build();

        Lookup savedLookup = lookupRepository.save(lookup);

        return convertToDto(savedLookup);
    }

    @Override
    public LookupDto update(LookupDto lookupDto) {
        Lookup lookup = convertToEntity(lookupDto);
        return convertToDto(lookupRepository.save(lookup));
    }

    @Override
    public void delete(int id) {
        lookupRepository.deleteById(id);
    }

    private Lookup convertToEntity(LookupDto lookupDto) {
        Lookup lookup = new Lookup();
        BeanUtils.copyProperties(lookupDto, lookup);
        return lookup;

    }

    private LookupDto convertToDto(Lookup lookup) {
        return new LookupDto(
                lookup.getId(),
                lookup.getCategoryId(),
                lookup.getCategoryTitle(),
                lookup.getTitle(),
                lookup.getDescription(),
                lookup.getValue(),
                lookup.getParentId()
        );
    }

}
