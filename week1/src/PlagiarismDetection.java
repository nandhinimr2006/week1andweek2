import java.util.*;

public class PlagiarismDetection {

    static HashMap<String, Set<String>> ngramMap = new HashMap<>();
    static int N = 5;


    public static List<String> getNGrams(String text) {
        List<String> grams = new ArrayList<>();
        String[] words = text.split(" ");

        for (int i = 0; i <= words.length - N; i++) {
            String gram = "";
            for (int j = i; j < i + N; j++) {
                gram += words[j] + " ";
            }
            grams.add(gram.trim());
        }
        return grams;
    }


    public static void addDocument(String docId, String text) {
        List<String> grams = getNGrams(text);

        for (String g : grams) {
            ngramMap.putIfAbsent(g, new HashSet<>());
            ngramMap.get(g).add(docId);
        }

        System.out.println(docId + " stored with " + grams.size() + " n-grams");
    }


    public static void analyzeDocument(String docId, String text) {

        List<String> grams = getNGrams(text);
        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String g : grams) {
            if (ngramMap.containsKey(g)) {

                for (String doc : ngramMap.get(g)) {
                    if (!doc.equals(docId)) {
                        matchCount.put(doc,
                                matchCount.getOrDefault(doc, 0) + 1);
                    }
                }
            }
        }

        System.out.println("Extracted " + grams.size() + " n-grams");

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);
            double similarity = (matches * 100.0) / grams.size();

            System.out.println("Found " + matches +
                    " matching n-grams with " + doc);

            System.out.println("Similarity: " + similarity + "%");

            if (similarity > 50) {
                System.out.println("PLAGIARISM DETECTED");
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        addDocument("essay_089.txt",
                "data structures and algorithms are important in computer science");

        addDocument("essay_092.txt",
                "data structures and algorithms are important in modern computer science applications");

        System.out.println("Enter new essay text:");
        String text = sc.nextLine();

        analyzeDocument("essay_123.txt", text);

        sc.close();
    }
}