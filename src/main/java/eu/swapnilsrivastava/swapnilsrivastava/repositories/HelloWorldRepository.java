package eu.swapnilsrivastava.swapnilsrivastava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.swapnilsrivastava.swapnilsrivastava.models.HelloWorldModel;

@Repository
public interface HelloWorldRepository extends JpaRepository<HelloWorldModel, Long> {
    
}
