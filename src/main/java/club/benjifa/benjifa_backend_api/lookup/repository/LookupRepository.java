package club.benjifa.benjifa_backend_api.lookup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;

import java.util.List;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Integer> {

    List<Lookup> findAllByCategoryId(int categoryId);

    List<Lookup> findAllByParentId(int parentId);

    List<Lookup> findAllByTitleContaining(String title);

    Lookup findByValue(String value);


}
