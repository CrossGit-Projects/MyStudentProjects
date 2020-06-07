import java.util.List;

class NotificationData {

    private String name;
    private String lastName;
    private String senderEmail;
    private List<String> addressEmailRecipients;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getSenderEmail() {
        return senderEmail;
    }

    void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    List<String> getAddressEmailRecipients() {
        return addressEmailRecipients;
    }

    void setAddressEmailRecipients(List<String> addressEmailRecipients) {
        this.addressEmailRecipients = addressEmailRecipients;
    }
}
