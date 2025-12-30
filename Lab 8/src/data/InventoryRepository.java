package data;

import model.Medicine;
import exception.PharmacyStorageException;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class InventoryRepository {
    private final Path rootPath;

    public InventoryRepository(String rootDirectory) {
        this.rootPath = Paths.get(rootDirectory);
    }

    // FUNCTION 1: Directory Traversal using Files.walk()
    // Purpose: Simply lists structure for audit/debugging (Self-Learning Component)
    public void auditDirectoryStructure() throws PharmacyStorageException {
        System.out.println("--- Auditing Data Directory: " + rootPath + " ---");
        
        if (!Files.exists(rootPath) || !Files.isDirectory(rootPath)) {
            throw new PharmacyStorageException("Root directory missing: " + rootPath, null);
        }

        // try-with-resources ensures the Stream closes
        try (Stream<Path> paths = Files.walk(rootPath)) {
            paths.filter(Files::isRegularFile)
                 .forEach(p -> System.out.println("Found file: " + p.getFileName()));
        } catch (IOException e) {
            throw new PharmacyStorageException("Error traversing directory structure", e);
        }
    }

    // FUNCTION 2: Targeted Search using Files.find()
    // Purpose: Specifically looks for .csv files to load data
    public Map<String, Medicine> loadInventory() throws PharmacyStorageException {
        Map<String, Medicine> inventory = new HashMap<>();

        try (Stream<Path> pathStream = Files.find(rootPath, 5, 
                (path, attr) -> String.valueOf(path).endsWith(".csv"))) { // Matcher

            // Iterate over every CSV file found
            Iterator<Path> iterator = pathStream.iterator();
            while (iterator.hasNext()) {
                Path csvFile = iterator.next();
                loadFileIntoMap(csvFile, inventory);
            }

        } catch (IOException e) {
            // Wrapping IOException into our Domain Exception
            throw new PharmacyStorageException("Critical failure accessing data files.", e);
        }
        return inventory;
    }

    // Helper method to parse a single file
    private void loadFileIntoMap(Path file, Map<String, Medicine> map) {
        String category = file.getParent().getFileName().toString(); // Use folder name as category

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty() || line.startsWith("#")) continue; // Skip comments

                String[] parts = line.split(",");
                // Basic validation (Unchecked logic handled here or allowed to fail)
                if (parts.length == 5) {
                    Medicine m = new Medicine(
                        parts[0].trim(),
                        parts[1].trim(),
                        Integer.parseInt(parts[2].trim()),
                        Double.parseDouble(parts[3].trim()),
                        LocalDate.parse(parts[4].trim()),
                        category
                    );
                    map.put(m.getId(), m);
                }
            }
        } catch (Exception e) {
            // Self-Learning: Partial Failure Strategy
            // If one file is corrupt, log it but don't crash the whole system.
            System.err.println("Warning: Failed to parse file " + file.getFileName() + ": " + e.getMessage());
        }
    }
}