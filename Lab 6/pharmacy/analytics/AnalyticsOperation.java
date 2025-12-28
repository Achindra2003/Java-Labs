package pharmacy.analytics;

import java.util.Collection;

@FunctionalInterface
public interface AnalyticsOperation<V extends Number> {
    Double compute(Collection<V> data);
}