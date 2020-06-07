import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

class NotificationsTests {

    private MailService mailService;

    NotificationsTests() {
        mailService = mock(MailService.class);
    }

    @Test
    void correctSendNotificationMessage() {
        Notification call = new Notification(getPathToFile("recipients.txt"), getPathToFile("sender.txt"), mailService);
        call.sendNotification("", "");
        verifySentMessage(Arrays.asList("karol@o2.pl", "kuba@gmail.com", "dawid@wp.pl"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        verify(mailService, times(3)).sendMessage(anyString(), anyString(), anyString());
    }

    @Test
    void testNotificationExceptionReturnedMessageSentCorrect() {
        willThrowException(Collections.singletonList("weronika@o2.pl"), "Powiadomienie: ", "Powiadomienie od Łukasz Kawecki");
        Notification call = new Notification(getPathToFile("recipients1.txt"), getPathToFile("sender1.txt"), mailService);
        call.sendNotification("", "");
        verifySentMessage(Arrays.asList("weronika@o2.pl", "julia@wp.pl", "asia@gmail.com"), "Powiadomienie: ", "Powiadomienie od Łukasz Kawecki");
        verifySentMessage(Collections.singletonList("ukawecki@gmail.com"), "Notification Error", "Cannot send message to address: weronika@o2.pl");
        verify(mailService, times(4)).sendMessage(anyString(), anyString(), anyString());
    }

    @Test
    void testNotificationExceptionReturnedMessageSentCorrectForOtherData() {
        willThrowException(Collections.singletonList("karol@o2.pl"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        Notification call = new Notification(getPathToFile("recipients.txt"), getPathToFile("sender.txt"), mailService);
        call.sendNotification("", "");
        verifySentMessage(Arrays.asList("karol@o2.pl", "kuba@gmail.com", "dawid@wp.pl"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        verifySentMessage(Collections.singletonList("dawid.krawczyk@gmail.com"), "Notification Error", "Cannot send message to address: karol@o2.pl");
        verify(mailService, times(4)).sendMessage(anyString(), anyString(), anyString());
    }


    @Test
    void testNotificationExceptionMessageNotSentAndReturnedEmailException() {
        willThrowException(Collections.singletonList("dawid.krawczyk@gmail.com"), "Notification Error", "Cannot send message to address: karol@o2.pl");
        willThrowException(Collections.singletonList("karol@o2.pl"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        checkExceptionMessage(4, "Cannot send message to your address: dawid.krawczyk@gmail.com with topic: Notification Error and title: Cannot send message to address: karol@o2.pl");
    }

    void checkExceptionMessage(int wantedNumberOfInvocations, String exceptionMessage) {
        Notification call = new Notification(getPathToFile("recipients.txt"), getPathToFile("sender.txt"), mailService);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            call.sendNotification("", "");
        });
        verify(mailService, times(wantedNumberOfInvocations)).sendMessage(anyString(), anyString(), anyString());
        assertEquals(thrown.getMessage(), exceptionMessage);
    }

    @Test
    void testNotificationExceptionMessageToMoreRecipientsAndNotSentReturnedEmail() {
        willThrowException(Collections.singletonList("dawid.krawczyk@gmail.com"), "Notification Error", "Cannot send message to address: karol@o2.pl, kuba@gmail.com");
        willThrowException(Arrays.asList("karol@o2.pl", "kuba@gmail.com"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        checkExceptionMessage(4, "Cannot send message to your address: dawid.krawczyk@gmail.com with topic: Notification Error and title: Cannot send message to address: karol@o2.pl, kuba@gmail.com");
        verifySentMessage(Arrays.asList("karol@o2.pl", "kuba@gmail.com", "dawid@wp.pl"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        verifySentMessage(Collections.singletonList("dawid.krawczyk@gmail.com"), "Notification Error", "Cannot send message to address: karol@o2.pl, kuba@gmail.com");
    }

    @Test
    void testNotificationExceptionMessageToAllRecipientsAndNotSentReturnedEmail() {
        willThrowException(Collections.singletonList("dawid.krawczyk@gmail.com"), "Notification Error", "Cannot send message to address: karol@o2.pl, dawid@wp.pl, kuba@gmail.com");
        willThrowException(Arrays.asList("karol@o2.pl", "dawid@wp.pl", "kuba@gmail.com"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        checkExceptionMessage(4, "Cannot send message to your address: dawid.krawczyk@gmail.com with topic: Notification Error and title: Cannot send message to address: karol@o2.pl, dawid@wp.pl, kuba@gmail.com");
        verifySentMessage(Arrays.asList("karol@o2.pl", "kuba@gmail.com", "dawid@wp.pl"), "Powiadomienie: ", "Powiadomienie od Dawid Krawczyk");
        verifySentMessage(Collections.singletonList("dawid.krawczyk@gmail.com"), "Notification Error", "Cannot send message to address: karol@o2.pl, dawid@wp.pl, kuba@gmail.com");
    }

    private Path getPathToFile(String fileName) {
        return Paths.get(this.getClass().getResource(fileName).getPath());
    }

    private void willThrowException(List<String> addressesTo, String topic, String content) {
        for (String addressTo : addressesTo) {
            willThrow(new RuntimeException()).given(mailService).sendMessage(addressTo, topic, content);
        }
    }

    private void verifySentMessage(List<String> addressesTo, String topic, String content) {
        for (String addressTo : addressesTo) {
            verify(mailService).sendMessage(addressTo, topic, content);
        }
    }
}
