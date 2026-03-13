import java.util.*;

public class MultiLevelCacheSystem {


    static LinkedHashMap<String, String> L1 =
            new LinkedHashMap<>(10000, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                    return size() > 10000;
                }
            };


    static LinkedHashMap<String, String> L2 =
            new LinkedHashMap<>(100000, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, String> e) {
                    return size() > 100000;
                }
            };


    static HashMap<String, String> L3 = new HashMap<>();

    static int l1Hits = 0, l2Hits = 0, l3Hits = 0;


    public static void getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 Cache HIT (0.5ms)");
            return;
        }

        System.out.println("L1 Cache MISS");

        if (L2.containsKey(videoId)) {
            l2Hits++;
            System.out.println("L2 Cache HIT (5ms)");


            L1.put(videoId, L2.get(videoId));
            System.out.println("Promoted to L1");
            return;
        }

        System.out.println("L2 Cache MISS");

        if (L3.containsKey(videoId)) {
            l3Hits++;
            System.out.println("L3 Database HIT (150ms)");

            L2.put(videoId, L3.get(videoId));
            System.out.println("Added to L2");
        } else {
            System.out.println("Video not found");
        }
    }


    public static void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        if (total == 0) {
            System.out.println("No requests yet");
            return;
        }

        System.out.println("\nCache Statistics:");

        System.out.println("L1 Hits: " + l1Hits);
        System.out.println("L2 Hits: " + l2Hits);
        System.out.println("L3 Hits: " + l3Hits);

        double hitRate = (total * 100.0) / total;

        System.out.println("Total Requests: " + total);
        System.out.println("Overall Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        L3.put("video_123", "Movie Data A");
        L3.put("video_999", "Movie Data B");

        System.out.print("Enter video ID: ");
        String id = sc.nextLine();

        getVideo(id);

        System.out.println("\nSecond request:");
        getVideo(id);

        getStatistics();

        sc.close();
    }
}
