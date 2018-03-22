package fi.solita.u2f.repository;


import fi.solita.u2f.domain.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface U2FDeviceRepository extends CrudRepository<Device, String> {

    List<Device> findByUsername(String username);
    void deleteById(String id);

}
