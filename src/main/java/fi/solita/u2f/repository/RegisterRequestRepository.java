package fi.solita.u2f.repository;


import fi.solita.u2f.domain.U2FRegisterRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRequestRepository extends CrudRepository<U2FRegisterRequest, String> {

    List<U2FRegisterRequest> findByUsername(String username);

}
