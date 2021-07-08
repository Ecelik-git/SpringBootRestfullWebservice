package restfullwebservice03;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SF03StudentBeanRepository extends JpaRepository<SF03StudentBean, Long> {

}
