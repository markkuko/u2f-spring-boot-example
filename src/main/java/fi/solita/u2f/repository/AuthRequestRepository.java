package fi.solita.u2f.repository;


import fi.solita.u2f.domain.U2FAuthRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRequestRepository extends CrudRepository<U2FAuthRequest, String> {

    List<U2FAuthRequest> findByUsername(String username);

    void deleteById(String id);
}
