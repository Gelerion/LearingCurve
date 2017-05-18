package com.denis.learning;

import java.util.*;
import java.util.stream.Collectors;

public class CodeFight {
    public static void main(String[] args) {
        String[][] messages = new String[][]{
                {"Sale today!", "2837273"},
                {"Unique offer!", "3873827"},
                {"Only today and only for you!", "2837273"},
                {"Sale today!", "2837273"},
                {"Unique offer!", "3873827"}};
        String[] spamSignals = new String[]{"sale",
                "discount",
                "offer"};

/*        String[][] messages = new String[][]{
                {"Check Codefights out", "7284736"},
                {"Check Codefights out", "7462832"},
                {"Check Codefights out", "3625374"},
                {"Check Codefights out", "7264762"}};
        String[] spamSignals = new String[]{"sale",
                "discount",
                "offer"};*/

/*        String[][] messages = new String[][]{
                {"spam", "1"},
                {"meh", "2"},
                {"teh", "3"},
                {"speh", "4"}
        };
        String[] spamSignals = new String[]{"spam"};*/

/*        String[][] messages = new String[][]{
                {"a", "1"}
        };
        String[] spamSignals = new String[]{"b"};*/

        String[] result = new CodeFight().spamDetection(messages, spamSignals);
        System.out.println("result = " + Arrays.toString(result));
    }

    String[] spamDetection(String[][] messages, String[] spamSignals) {
        long startMillis = System.currentTimeMillis();
        int totalMessages = messages.length;
        int totalUnderFiveWords = 0;
        int totalSpamMessages = 0;
        int totalMessagesWithSameConent = 0;

        HashSet<String> spamWords = new HashSet<>();
        Collections.addAll(spamWords, spamSignals);

        HashMap<Integer, Integer> recipientMessagesCount = new HashMap<>();
        HashMap<String, Integer> sameMessagesContentCount = new HashMap<>();
        HashSet<String> currentlyExistingMessage = new HashSet<>();
        //recipientId -> uniqueMessage - countSame
        HashMap<Integer, HashMap<String, Integer>> recipientIdToMessages = new HashMap<>();
        Set<String> foundSpamWords = new HashSet<>();

        for (String[] message : messages) {
            String content = message[0];
            int recipientId = Integer.parseInt(message[1]);

            String[] words = content.split(" ");
            int wordsCount = words.length;
            if (wordsCount < 5) totalUnderFiveWords++;


            if (currentlyExistingMessage.contains(content)) {
                totalMessagesWithSameConent++;
            }
            else {
                currentlyExistingMessage.add(content);
            }

            if (recipientIdToMessages.containsKey(recipientId)) {
                HashMap<String, Integer> uniqueMessageCounts = recipientIdToMessages.get(recipientId);
                uniqueMessageCounts.merge(content, 1, (existing, toAdd) -> existing + toAdd);
            }
            else {
                HashMap<String, Integer> uniqueMessageCounts = new HashMap<>();
                uniqueMessageCounts.put(content, 1);
                recipientIdToMessages.put(recipientId, uniqueMessageCounts);
            }

            ///

            if (Arrays.stream(words).map(String::toLowerCase).map(word -> word.replaceAll("[^a-zA-Z]", "")).anyMatch(spamWords::contains)) {
                totalSpamMessages++;
                String spamWord = Arrays.stream(words).map(String::toLowerCase).map(word -> word.replaceAll("[^a-zA-Z]", ""))
                        .filter(spamWords::contains).findFirst().get();
                foundSpamWords.add(spamWord);
            }


            recipientMessagesCount.merge(recipientId, 1, (existing, toAdd) -> existing + toAdd);

            if (sameMessagesContentCount.containsKey(content)) {
                sameMessagesContentCount.merge(content, 1, (existing, toAdd) -> existing + toAdd);
            }
            else {
                sameMessagesContentCount.put(content, 1);
            }
        }

        String[] result = new String[4];

        //% fewer than five words
        double fewerPercent = (double) totalUnderFiveWords / totalMessages;
        if(fewerPercent == 1) result[0] = "failed: 1/1";
        else if (fewerPercent > 0.9) result[0] = "failed: " + totalUnderFiveWords + "/" + totalMessages;
        else result[0] = "passed";

        Map<Integer, Integer> moreThanOneMessageToRecipient = recipientMessagesCount.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //more than 50 % of messages to any one user had the same content
        List<Integer> spammedRecipients = recipientIdToMessages.entrySet()
                .stream()
                //those who have more than 1 message in total
                .filter(entry -> moreThanOneMessageToRecipient.containsKey(entry.getKey()))
                .map(entry -> {
                    int resultRecipientId = -1;
                    Integer totalMessagesToRecipient = moreThanOneMessageToRecipient.get(entry.getKey());
                    HashMap<String, Integer> contentCount = entry.getValue();
                    long moreThanHalfMessages = contentCount.values().stream()
                            .filter(count -> (double) count / totalMessagesToRecipient > 0.5)
                            .count();
                    if (moreThanHalfMessages > 0) {
                        resultRecipientId = entry.getKey();
                    }

                    return resultRecipientId;
                })
                .filter(recipientId -> recipientId > 0)
                .collect(Collectors.toList());

        if (spammedRecipients.size() >= 1)
            result[1] = "failed: " + spammedRecipients.stream().map(String::valueOf)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.joining(" "));
        else result[1] = "passed";

        if(totalMessages > 1) {
            List<String> spamMessage = sameMessagesContentCount.entrySet().stream()
                    .filter(entry -> (double) entry.getValue() / totalMessages > 0.5)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (spamMessage.size() >= 1) result[2] = "failed: " + spamMessage.get(0);
            else result[2] = "passed";
        }
        else {
            result[2] = "passed";
        }

        if ((double) totalSpamMessages / totalMessages > 0.5)
            result[3] = "failed: " + foundSpamWords.stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.joining(" "));
        else result[3] = "passed";

        System.out.println("Time took: " + (System.currentTimeMillis() - startMillis) + "ms");
        return result;
    }
}
