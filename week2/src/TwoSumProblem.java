import java.util.*;

public class TwoSumProblem {

    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;

        Transaction(int id, int amount, String merchant, String account) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
        }
    }

    public static void findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                Transaction other = map.get(complement);

                System.out.println("Pair Found: (id:" + other.id +
                        ", id:" + t.id + ") → " +
                        other.amount + " + " + t.amount);
            }

            map.put(t.amount, t);
        }
    }


    public static void detectDuplicates(List<Transaction> transactions) {

        HashMap<String, List<String>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "-" + t.merchant;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.account);
        }

        for (String key : map.keySet()) {

            List<String> accounts = map.get(key);

            if (accounts.size() > 1) {

                String[] parts = key.split("-");

                System.out.println("Duplicate Detected:");
                System.out.println("Amount: " + parts[0] +
                        ", Merchant: " + parts[1]);
                System.out.println("Accounts: " + accounts);
            }
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, 500, "StoreA", "acc1"));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2"));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3"));
        transactions.add(new Transaction(4, 500, "StoreA", "acc4"));

        System.out.println("Two-Sum Results:");
        findTwoSum(transactions, 500);

        System.out.println("\nDuplicate Detection:");
        detectDuplicates(transactions);
    }
}