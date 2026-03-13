import java.util.*;

public class DNSCache {


    static class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, int ttl) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + ttl * 1000;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    static HashMap<String, DNSEntry> cache = new HashMap<>();
    static int hits = 0;
    static int misses = 0;


    public static String resolve(String domain) {

        if (cache.containsKey(domain)) {

            DNSEntry entry = cache.get(domain);

            if (!entry.isExpired()) {
                hits++;
                System.out.println("Cache HIT → " + entry.ip);
                return entry.ip;
            }
            else {
                cache.remove(domain);
                System.out.println("Cache EXPIRED");
            }
        }


        misses++;
        String ip = queryUpstream(domain);

        cache.put(domain, new DNSEntry(ip, 10)); // TTL 10 seconds
        System.out.println("Cache MISS → Upstream IP: " + ip);

        return ip;
    }


    public static String queryUpstream(String domain) {
        Random r = new Random();
        return "172.217.14." + r.nextInt(255);
    }


    public static void getCacheStats() {
        int total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits * 100.0 / total);

        System.out.println("Cache Hits: " + hits);
        System.out.println("Cache Misses: " + misses);
        System.out.println("Hit Rate: " + hitRate + "%");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter domain: ");
        String domain = sc.nextLine();

        resolve(domain);

        System.out.print("Enter domain again: ");
        String domain2 = sc.nextLine();

        resolve(domain2);

        getCacheStats();

        sc.close();
    }
}
