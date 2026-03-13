import java.util.*;

public class SocialMedia {

    static HashMap<String, Integer> usernameMap = new HashMap<>();
    static HashMap<String, Integer> attemptMap = new HashMap<>();

    // Check availability
    public static boolean checkAvailability(String username) {

        attemptMap.put(username, attemptMap.getOrDefault(username, 0) + 1);

        return !usernameMap.containsKey(username);
    }

    // Suggest alternatives
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username + "123");
        suggestions.add(username.replace("_", "."));

        return suggestions;
    }

    // Find most attempted username
    public static String getMostAttempted() {

        String popular = "";
        int max = 0;

        for (String name : attemptMap.keySet()) {
            if (attemptMap.get(name) > max) {
                max = attemptMap.get(name);
                popular = name;
            }
        }

        return popular + " (" + max + " attempts)";
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Pre-existing users
        usernameMap.put("john_doe", 101);
        usernameMap.put("alex123", 102);

        System.out.print("Enter username to check: ");
        String username = sc.nextLine();

        if (checkAvailability(username)) {

            System.out.println("Username is available");

            System.out.print("Enter User ID to register: ");
            int id = sc.nextInt();

            usernameMap.put(username, id);
            System.out.println("User registered successfully");

        } else {

            System.out.println("Username already taken");
            System.out.println("Suggested usernames: " + suggestAlternatives(username));
        }

        System.out.println("Most attempted username: " + getMostAttempted());

        sc.close();
    }
}

