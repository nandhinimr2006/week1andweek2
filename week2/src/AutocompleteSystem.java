import java.util.*;

public class AutocompleteSystem {


    static HashMap<String, Integer> queryFreq = new HashMap<>();


    public static void updateFrequency(String query) {
        queryFreq.put(query, queryFreq.getOrDefault(query, 0) + 1);
        System.out.println(query + " → Frequency: " + queryFreq.get(query));
    }

    public static void search(String prefix) {

        List<Map.Entry<String, Integer>> matches = new ArrayList<>();


        for (Map.Entry<String, Integer> entry : queryFreq.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                matches.add(entry);
            }
        }


        matches.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Suggestions for \"" + prefix + "\":");

        int count = 0;

        for (Map.Entry<String, Integer> e : matches) {

            System.out.println((count + 1) + ". " +
                    e.getKey() + " (" + e.getValue() + " searches)");

            count++;

            if (count == 10) break;
        }

        if (count == 0) {
            System.out.println("No suggestions found");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        updateFrequency("java tutorial");
        updateFrequency("javascript");
        updateFrequency("java download");
        updateFrequency("java tutorial");
        updateFrequency("java tutorial");
        updateFrequency("java 21 features");

        System.out.print("Enter search prefix: ");
        String prefix = sc.nextLine();

        search(prefix);

        System.out.print("\nEnter new search query: ");
        String newQuery = sc.nextLine();

        updateFrequency(newQuery);

        sc.close();
    }
}