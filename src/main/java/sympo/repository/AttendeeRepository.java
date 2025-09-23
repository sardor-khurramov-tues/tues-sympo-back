package sympo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sympo.entity.AttendeeEntity;

@Repository
public interface AttendeeRepository extends JpaRepository<AttendeeEntity, Long> {

    boolean existsByEmail(String phone);

}
