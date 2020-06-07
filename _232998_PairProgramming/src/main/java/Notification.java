import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Notification {
    private static final String topicPrefix = "Powiadomienie: ";
    private static final String contentPrefix = "Powiadomienie od ";
    private NotificationData notificationData;
    private MailService mailService;
    private static final String errorTopicPrefix = "Notification Error";
    private static final String errorContentPrefix = "Cannot send message to address: ";


    Notification(Path recipientsDataPath, Path senderDataPath, MailService mailService) {
        this.notificationData = parse(recipientsDataPath, senderDataPath);
        this.mailService = mailService;
    }

    private List<String> readFile(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file from path: " + path);
        }
    }

    void sendNotification(String topic, String content) throws RuntimeException {
        String errorEmails = "";
        for (String addressTo : notificationData.getAddressEmailRecipients()) {
            try {
                mailService.sendMessage(addressTo, topicBuilder(topic), contentBuilder(content));
            } catch (RuntimeException e) {
                errorEmails = errorEmails + addressTo + ", ";
            }
        }
        if (!errorEmails.isEmpty()) {
            errorEmails=errorEmails.substring(0,errorEmails.length()-2);
            try {
                mailService.sendMessage(notificationData.getSenderEmail(), errorTopicPrefix, errorContentPrefix + errorEmails);
            } catch (RuntimeException e) {
                throw new RuntimeException("Cannot send message to your address: " + notificationData.getSenderEmail() + " with topic: " + errorTopicPrefix + " and title: " + errorContentPrefix + errorEmails);
            }
        }
    }

    private String contentBuilder(String content) {
        return contentPrefix + notificationData.getName() + " " + notificationData.getLastName() + content;
    }

    private String topicBuilder(String topic) {
        return topicPrefix + topic;
    }

    private NotificationData parse(Path recipientsDataPath, Path senderDataPath) {
        List<String> recipientsData = readFile(recipientsDataPath);
        List<String> senderData = readFile(senderDataPath);
        NotificationData call = new NotificationData();
        String[] senderDataSplit = senderData.get(0).split("\\,");
        call.setName(senderDataSplit[0].trim());
        call.setLastName(senderDataSplit[1].trim());
        call.setSenderEmail(senderDataSplit[2].trim());
        call.setAddressEmailRecipients(recipientsData);
        return call;
    }
}
