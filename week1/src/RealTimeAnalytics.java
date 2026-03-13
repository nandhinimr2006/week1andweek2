import java.util.*;

public class RealTimeAnalytics {


    static HashMap<String, Integer> pageViews = new HashMap<>();


    static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();


    static HashMap<String, Integer> trafficSources = new HashMap<>();


    public static void processEvent(String url, String userId, String source) {


        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);


        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);


        trafficSources.put(source,
                trafficSources.getOrDefault(source, 0) + 1);
    }


    public static void getDashboard() {

        System.out.println("\nTop Pages:");


        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        int count = 0;

        for (Map.Entry<String, Integer> e : list) {

            String url = e.getKey();
            int views = e.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println((count + 1) + ". " + url +
                    " - " + views + " views (" +
                    unique + " unique)");

            count++;
            if (count == 10) break;
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int c : trafficSources.values()) {
            total += c;
        }

        for (String src : trafficSources.keySet()) {

            int countSrc = trafficSources.get(src);
            double percent = (countSrc * 100.0) / total;

            System.out.println(src + ": " + percent + "%");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of events: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {

            System.out.print("Enter page URL: ");
            String url = sc.nextLine();

            System.out.print("Enter user ID: ");
            String user = sc.nextLine();

            System.out.print("Enter source (google/facebook/direct): ");
            String source = sc.nextLine();

            processEvent(url, user, source);
        }

        getDashboard();

        sc.close();
    }
}