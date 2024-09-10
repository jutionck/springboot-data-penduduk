package com.enigmacamp.springbootdatapenduduk.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseSpecification<T> {

    public static <T> Specification<T> hasSearchQuery(String searchTerm, String[] fields, Map<String, Map<String, String>> joinFields) {
        return (root, query, criteriaBuilder) -> {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                String searchPattern = "%" + searchTerm.toLowerCase() + "%";
                List<Predicate> predicates = new ArrayList<>();

                // Pencarian pada field di entitas utama
                for (String field : fields) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), searchPattern));
                }

                // Pencarian pada field yang berhubungan melalui join
                if (joinFields != null && !joinFields.isEmpty()) {
                    for (Map.Entry<String, Map<String, String>> joinField : joinFields.entrySet()) {
                        Join<Object, Object> join = root.join(joinField.getKey(), JoinType.LEFT); // Join ke entitas terkait

                        // Iterate over fields in the joined entity
                        for (Map.Entry<String, String> field : joinField.getValue().entrySet()) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(join.get(field.getValue())), searchPattern));
                        }
                    }
                }

                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
            return criteriaBuilder.conjunction();
        };
    }
}



