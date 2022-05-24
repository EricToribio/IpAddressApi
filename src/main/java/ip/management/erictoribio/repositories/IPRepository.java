package ip.management.erictoribio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ip.management.erictoribio.models.IPModel;

@Repository
public interface IPRepository extends CrudRepository<IPModel, Long>{

    List<IPModel> findAll();

    Optional<IPModel> findByip(String Ip);
    
}
