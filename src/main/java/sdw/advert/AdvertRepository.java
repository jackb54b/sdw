package sdw.advert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Repository
public class AdvertRepository {

    private final EntityManager em;

    @Transactional
    public void save(Advert advert) {
        em.persist(advert);
    }
}
