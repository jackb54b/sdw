package sdw.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import sdw.advert.AdvertDataBaseInitializer
import sdw.advert.AdvertRepository
import spock.lang.Specification
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import javax.persistence.EntityManager
import java.time.LocalDate

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SdwQueryExecutorSpec extends Specification {

    @Subject
    private AdvertDataBaseInitializer advertImporter = new AdvertDataBaseInitializer(advertRepository)

    @Subject
    private SdwQueryExecutor sdwQueryExecutor = new SdwQueryExecutor(em)

    @Subject
    private AdvertRepository advertRepository = new AdvertRepository(em)

    @Autowired
    private EntityManager em

    def setup (){
        advertImporter.initialize()
    }

    def "use example sum(clicks), sum(impressions) group by campaign where daily between ( 08/22/19 ... 12/20/20)" () {
        given:
            SdwQuery sdwQuery = new SdwQueryBuilder()
                    .withSum(SdwQuery.Sum.clicks)
                    .withSum(SdwQuery.Sum.impressions)
                    .withGroupBy(SdwQuery.GroupBy.campaign)
                    .withRestriction(
                            new SdwRestriction(SdwRestriction.Operator.GREATER_THEN_DATE,
                                    LocalDate.parse("08/22/19", AdvertDataBaseInitializer.DATE_TIME_FORMATTER),
                                    SdwQuery.RestrictionColumn.daily
                            )
                    ).withRestriction(
                            new SdwRestriction(SdwRestriction.Operator.LESS_THEN_DATE,
                                    LocalDate.parse("12/20/20", AdvertDataBaseInitializer.DATE_TIME_FORMATTER),
                                    SdwQuery.RestrictionColumn.daily
                            )
                    )
            .build()
        when:
            List result = sdwQueryExecutor.execute(sdwQuery)
            println result.size()
        then:
            result.size() == 72
    }

}
