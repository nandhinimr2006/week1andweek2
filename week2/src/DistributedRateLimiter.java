import java.util.*;

public class DistributedRateLimiter {

    static class TokenBucket {
        int tokens;
        int maxTokens;
        long lastRefillTime;
        int refillRate; // tokens per hour

        TokenBucket(int maxTokens, int refillRate) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }

        void refill() {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRefillTime;

            int tokensToAdd = (int)((elapsed / 3600000.0) * refillRate);

            if (tokensToAdd > 0) {
                tokens = Math.min(maxTokens, tokens + tokensToAdd);
                lastRefillTime = now;
            }
        }

        boolean allowRequest() {
            refill();

            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }
    }

    static HashMap<String, TokenBucket> clientBuckets = new HashMap<>();

    static int LIMIT = 1000; // requests per hour

    public static void checkRateLimit(String clientId) {

        clientBuckets.putIfAbsent(clientId,
                new TokenBucket(LIMIT, LIMIT));

        TokenBucket bucket = clientBuckets.get(clientId);

        if (bucket.allowRequest()) {
            System.out.println("Allowed (" + bucket.tokens +
                    " requests remaining)");
        } else {
            System.out.println("Denied (0 requests remaining, retry later)");
        }
    }

    public static void getRateLimitStatus(String clientId) {

        if (!clientBuckets.containsKey(clientId)) {
            System.out.println("No usage yet");
            return;
        }

        TokenBucket bucket = clientBuckets.get(clientId);

        int used = bucket.maxTokens - bucket.tokens;

        System.out.println("Used: " + used +
                ", Limit: " + bucket.maxTokens +
                ", Remaining: " + bucket.tokens);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Client ID: ");
        String clientId = sc.nextLine();

        System.out.print("Enter number of API requests: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            checkRateLimit(clientId);
        }

        getRateLimitStatus(clientId);

        sc.close();
    }
}