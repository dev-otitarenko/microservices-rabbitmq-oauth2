package com.maestro.app.sample1.ms.events.specifications;

import com.maestro.app.sample1.ms.events.entities.LogPublicEvents;
import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.data.specifications.FilterUtils;
import com.maestro.app.utils.data.specifications.SearchField;
import com.maestro.app.utils.types.TypeAdmin;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * LogPublicEventSpecification
 **/
public class LogPublicEventsSpecification implements Specification<LogPublicEvents> {
    private final List<String> _types;
    private final Map<String, SearchField> _criterias;
    private final AuthUser authUser;

    /**
     * the constructor LogPublicEvents
     *
     * @param user The information of the authenticated user
     * @param search The parsed search criteria
     */
    public LogPublicEventsSpecification (AuthUser user, Map<String, SearchField> search) {
        this._criterias = search;

        _types = new ArrayList<>();
        _types.add("code");
        _types.add("dateRec");
        _types.add("mode");
        _types.add("iddoc");
        _types.add("cdoc");
        _types.add("ipaddress");
        _types.add("countryCode");
        _types.add("countryName");
        _types.add("cityName");

        this.authUser = user;
    }

    /**
     * Forming the predicate for LogPublicEvents
     *
     * @param root The root (entity)
     * @param cq The criteria query passed from the system
     * @param cb The criteria builder passed from the system
     */
    @Override
    public Predicate toPredicate(Root<LogPublicEvents> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (authUser.getTypeAdmin() != TypeAdmin.NONE.getValue()) {
            this._types.forEach((field) -> {
                SearchField condition = this._criterias.get(field);
                if (condition != null) {
                    Predicate predicate = FilterUtils.buildPredicate(condition, root, cq, cb);
                    if (predicate != null) predicates.add(predicate);
                }
            });
        } else {
            predicates.add(cb.equal(root.get("code"), "*"));
        }
        return predicates.size() > 0 ? cb.and(predicates.toArray(new Predicate[predicates.size()])) : null;
    }
}
