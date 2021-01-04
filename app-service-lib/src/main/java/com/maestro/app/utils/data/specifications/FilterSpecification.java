package com.maestro.app.utils.data.specifications;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class FilterSpecification implements Specification {
    private List<SearchField> criterias;

    public FilterSpecification() {
        this.criterias = new ArrayList<>();
    }

    public void addCriteria(SearchField criteria) {
        this.criterias.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);
        return predicates.size() > 0
                ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
                : null;
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        criterias.forEach(condition -> {
            Predicate predicate = FilterUtils.buildPredicate(condition, root, criteriaQuery, criteriaBuilder);
            if (predicate != null)
                        predicates.add(predicate);
        });
        return predicates;
    }
}
