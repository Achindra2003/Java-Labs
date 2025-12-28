package pharmacy.analytics;

@FunctionalInterface
public interface DomainComparator<T extends Number> {
    boolean test(T valueA, T valueB);
}