package sdw.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sdw.advert.Advert;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static sdw.api.SdwRestriction.Operator.*;

@RequiredArgsConstructor
@Component
public class SdwQueryExecutor {

    private final EntityManager entityManager;

    public List execute(SdwQuery sdwQuery) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);

        Root<Advert> advert = criteriaQuery.from(Advert.class);

        List<Selection<?>> selections = new ArrayList<>();

        selections.addAll(
            sdwQuery.getSums().stream().map(
                it -> criteriaBuilder.sum(advert.get(it.toString()))
            ).collect(Collectors.toList()));

        selections.addAll(
            sdwQuery.getGroupByList().stream().map(
                it ->  advert.get(it.toString())
            ).collect(Collectors.toList())
        );

        criteriaQuery.multiselect(selections);

        criteriaQuery.groupBy(
                sdwQuery.getGroupByList().stream().map(
                    it -> advert.get(it.toString())
                ).toArray(Expression[]::new)
        );
        criteriaQuery.where(
                sdwQuery.getSdwRestrictions().stream().map(
                    it -> restrictionToPredicate(criteriaBuilder, advert, it)
                ).toArray(Predicate[]::new)
        );
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Predicate restrictionToPredicate (CriteriaBuilder criteriaBuilder, Root<Advert> advert, SdwRestriction sdwRestriction) {
        if (sdwRestriction.getOperator() == EQUAL) {
            return criteriaBuilder.equal(advert.get(sdwRestriction.getRestrictionColumn().toString()), sdwRestriction.getValue());
        }
        if (sdwRestriction.getOperator() == LESS_THEN_DATE) {
            return criteriaBuilder.lessThan(advert.get(sdwRestriction.getRestrictionColumn().toString()).as(LocalDate.class), (LocalDate) sdwRestriction.getValue());
        }
        if (sdwRestriction.getOperator() == GREATER_THEN_DATE) {
            return criteriaBuilder.greaterThan(advert.get(sdwRestriction.getRestrictionColumn().toString()).as(LocalDate.class), (LocalDate) sdwRestriction.getValue());
        }
        throw new RuntimeException("Not implemented yet");
    }

}
