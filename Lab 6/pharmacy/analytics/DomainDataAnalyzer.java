package pharmacy.analytics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DomainDataAnalyzer<K, V extends Number> {

    private final Map<K, V> dataStore;
    private final String dataName;

    public DomainDataAnalyzer(String dataName) {
        this.dataName = dataName;
        this.dataStore = new HashMap<>();
    }

    public void addData(K key, V value) {
        this.dataStore.put(key, value);
        System.out.println("-> Added " + this.dataName + ": {" + key + " = " + value + "}");
    }

    public void displayData() {
        System.out.println("\n--- Full Dataset: " + this.dataName + " ---");
        if (dataStore.isEmpty()) {
            System.out.println("[Dataset is empty]");
            return;
        }
        dataStore.forEach((key, value) -> 
            System.out.println("  - Key: " + key + ", Value: " + value)
        );
    }
    
    public boolean isEmpty() {
        return dataStore.isEmpty();
    }
    
    public String getDataName() {
        return this.dataName;
    }

    public Double performAnalysis(AnalyticsOperation<V> operation) {
        if (dataStore.isEmpty()) {
            return 0.0;
        }
        return operation.compute(this.dataStore.values());
    }
    
    public Optional<Map.Entry<K, V>> findFirstMatch(DomainComparator<V> comparator, V targetValue) {
        for (Map.Entry<K, V> entry : dataStore.entrySet()) {
            if (comparator.test(entry.getValue(), targetValue)) {
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    public Map<K, V> filterByValue(Predicate<V> filterLambda) {
        return this.dataStore.entrySet().stream()
            .filter(entry -> filterLambda.test(entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}