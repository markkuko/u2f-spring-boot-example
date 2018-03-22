package fi.solita.u2f.repository;

import fi.solita.u2f.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);
}
