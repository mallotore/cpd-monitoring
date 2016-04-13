package grooid.app.alerts;
import groovy.transform.CompileStatic

@CompileStatic
class Alert {

    String title
    String message
    String date

    @Override
    public String toString() {
        return "${title} - ${date} - ${message}"
    }
}