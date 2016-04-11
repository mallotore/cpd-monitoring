package grooid.app.messages;
import groovy.transform.CompileStatic

@CompileStatic
class ReceivedMessage {

    String title
    String message
    String date

    @Override
    public String toString() {
        return "${title} - ${date} - ${message}"
    }
}