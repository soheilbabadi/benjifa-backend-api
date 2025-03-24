package club.benjifa.benjifa_backend_api.lookup.service;


import club.benjifa.benjifa_backend_api.lookup.dto.LookupDto;

import java.util.List;

/**
 * @author soheil babadi
 */
public interface LookupService {

    /**
     * Returns a list of basic info by a category ID.
     *
     * @param categoryId: the ID of the category
     * @return List<LookupDto>: a list of lookup data
     */
    List<LookupDto> findAllByCategoryId(int categoryId);

    /**
     * Returns a list of basic info by a parent ID.
     *
     * @param parentId: the ID of the parent
     * @return List<LookupDto>: a list of lookup data
     */
    List<LookupDto> findAllByParentId(int parentId);

    /**
     * Find a lookup data item by value.
     *
     * @param value: like hidden code of lookup
     * @return LookupDto: a lookup data item
     */
    LookupDto findByValue(String value);

    /**
     * Find a lookup data item by ID.
     *
     * @param id: the ID of the lookup data, like 1
     * @return LookupDto: a lookup data item
     */
    LookupDto findById(int id);

    /**
     * Search for lookup data by title containing.
     *
     * @param title: a part of the title of the lookup data, like 'Tehr' for Tehran.
     * @return List<LookupDto>: a list of lookups that contains the title.
     */
    List<LookupDto> findAllByTitleContaining(String title);

    /**
     * Create a new lookup item if user has enough permission
     *
     * @param lookupDto: a lookup data item
     * @return LookupDto: same lookup data item with persistence ID
     */
    LookupDto create(LookupDto lookupDto);

    /**
     * Update lookup item if user has enough permission
     *
     * @param lookupDto: a lookup data item
     * @return LookupDto: same lookup data item after persistence
     */
    LookupDto update(LookupDto lookupDto);

    /**
     * Deletes a lookup data item by ID.
     *
     * @param id: the ID of the lookup data
     */
    void delete(int id);
}
