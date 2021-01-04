package com.maestro.app.utils.data.specifications;

import javax.persistence.criteria.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtils {
    public static FilterSpecification build(String search) {
        FilterSpecification specification = new FilterSpecification();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<>|>|>:|<|<:|!:|~:|\\^:|\\$:)(.*?),", Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                String val = matcher.group(3);
                if (val == null || val.trim().isEmpty()) {
                    continue;
                }
                specification.addCriteria(new SearchField(matcher.group(1), SearchOperator.getOperationByValue(matcher.group(2)), matcher.group(3)));
            }
        }
        return specification;
    }

    public static List<SearchField> buildCriterias(String search) {
        List<SearchField> criterias = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<>|>|>:|<|<:|!:|~:|\\^:|\\$:)(.*?),", Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                String val = matcher.group(3);
                if (val == null || val.trim().isEmpty()) {
                    continue;
                }
                criterias.add(new SearchField(matcher.group(1), SearchOperator.getOperationByValue(matcher.group(2)), matcher.group(3)));
            }
        }
        return criterias;
    }

    public static Map<String, SearchField> buildMapCriterias(String search) {
        Map<String, SearchField> criterias = new HashMap<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<>|>|>:|<|<:|!:|~:|\\^:|\\$:)(.*?),", Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                String val = matcher.group(3);
                if (val == null || val.trim().isEmpty()) {
                    continue;
                }
                criterias.put(matcher.group(1), new SearchField(matcher.group(1), SearchOperator.getOperationByValue(matcher.group(2)), matcher.group(3)));
            }
        }
        return criterias;
    }

    @SuppressWarnings ({ "unchecked", "rawtypes" })
    public static Predicate buildPredicate(SearchField field, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Path tt = root.get(field.getKey());
        Class<?> javaType = tt.getJavaType();
        if (!classCompatibleWithOperator(javaType, field.getOperation())) {
            throw new RuntimeException("operator incompatible with field");
        }

        Object valueObject = convertStringValueToObject(field.getValue(), javaType);
        switch (field.getOperation()) {
            case EQ:
                //if (field.getValue().contains("|")) {
                //    String [] values = field.getValue().split('|');
                //    return tt.in(values);
                //} else {
                    return criteriaBuilder.equal(tt, valueObject);
                //}
            case LT:
                return criteriaBuilder.lessThan(tt, (Comparable)valueObject);
            case LTE:
                return criteriaBuilder.lessThanOrEqualTo(tt, (Comparable)valueObject);
            case GT:
                return criteriaBuilder.greaterThan(tt, (Comparable)valueObject);
            case GTE:
                return criteriaBuilder.greaterThanOrEqualTo(tt, (Comparable)valueObject);
            case NE:
                return criteriaBuilder.notEqual(tt, valueObject);
            case EMPTY:
                return criteriaBuilder.isNull(tt);
            case CONTAINS:
                return criteriaBuilder.like(criteriaBuilder.lower(tt), "%" + field.getValue().toLowerCase() +"%"); // criteriaBuilder.lower(root.get(createria.getKey()))
            case STARTS:
                return criteriaBuilder.like(criteriaBuilder.lower(tt), field.getValue().toLowerCase() + "%"); // criteriaBuilder.lower(root.get(createria.getKey()))
            case ENDS:
                return criteriaBuilder.like(criteriaBuilder.lower(tt), "%" + field.getValue().toLowerCase()); // criteriaBuilder.lower(root.get(createria.getKey()))
            default:
                return null;
        }
    }

    @SuppressWarnings ({ "rawtypes", "unchecked" })
    private static Enum safeEnumValueOf(Class enumType, String name) {
        Enum enumValue = null;
        if (name != null) {
            try {
                enumValue = Enum.valueOf(enumType, name);
            } catch (Exception e) {
                enumValue = null;
            }
        }
        return enumValue;
    }

    private static Object convertStringValueToObject(String value, Class<?> clazz) {
        Object convertedValue = null;
        if (clazz.isEnum()) {
            convertedValue = safeEnumValueOf(clazz, value);
        } else if (Date.class.isAssignableFrom(clazz)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                convertedValue = sdf.parse(value);
            } catch (ParseException ex) {
                convertedValue = null;
                convertedValue = new Date(Long.parseLong(value));
            }
        } else if ((clazz.isPrimitive() && !clazz.equals(boolean.class))
                || (Number.class.isAssignableFrom(clazz))){
            try {
                convertedValue = NumberFormat.getInstance().parse(value);
            } catch (ParseException ex) {
                convertedValue = null;
            }
        } else {
            convertedValue = value;
        }
        if (convertedValue != null){
            return convertedValue;
        } else {
            throw new RuntimeException("Wrong format for value " + value);
        }
    }

    private static boolean classCompatibleWithOperator(Class<?> clazz, SearchOperator operator) {
        if (operator == null) {
            return true;
        } else {
            switch (operator) {
                case NE:
                case EQ:
                    return true;
                case GT:
                case GTE:
                case LT:
                case LTE:
                    return (Date.class.isAssignableFrom(clazz)
                            || (clazz.isPrimitive() && !clazz.equals(boolean.class))
                            || Number.class.isAssignableFrom(clazz));
                case EMPTY:
                case STARTS:
                case CONTAINS:
                case ENDS:
                    return String.class.equals(clazz);
                default:
                    return false;
            }
        }
    }
}
