package club.benjifa.benjifa_backend_api.lookup.dto;

public record LookupDto(
        int id,
        int categoryId,
        String categoryTitle,
        String title,
        String description,
        String value,
        Integer parentId
) {
}