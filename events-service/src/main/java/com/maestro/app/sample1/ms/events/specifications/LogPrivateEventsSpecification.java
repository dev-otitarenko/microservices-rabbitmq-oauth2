package com.maestro.app.sample1.ms.events.specifications;

import com.maestro.app.sample1.ms.events.entities.LogPrivateEvents;
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
 * LogPrivateEventSpecification
 **/
public class LogPrivateEventsSpecification implements Specification<LogPrivateEvents> {
    private final List<String> _types;
    private final Map<String, SearchField> _criterias;
    private final AuthUser authUser;;

    /**
     * the constructor LogPrivateEvents
     *
     * @param user The information of the authenticated user
     * @param search The parsed search criteria
     */
    public LogPrivateEventsSpecification (final AuthUser user, final Map<String, SearchField> search) {
        this._criterias = search;

        _types = new ArrayList<>();
        _types.add("code");
        _types.add("dateRec");
        _types.add("mode");
        _types.add("iddoc");
        _types.add("cdoc");
        _types.add("iduser");

        this.authUser = user;
    }

    /**
     * Forming the predicate for LogPrivateEvents
     *
     * @param root The root (entity)
     * @param cq The criteria query passed from the system
     * @param cb The criteria builder passed from the system
     */
    @Override
    public Predicate toPredicate(Root<LogPrivateEvents> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
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
        return predicates.size() > 0 ? cb.and(predicates.toArray(new Predicate[0])) : null;
    }
}
