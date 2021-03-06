package mc03.view;

import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class SoftwareNotification
{
  public static void notifyError(String errorMsg)
  {
    Notifications.create().title("Error").text(errorMsg).position(Pos.TOP_RIGHT).showError();
  }
  
  public static void notifySuccess(String successMsg)
  {
    Notifications.create().title("Success").text(successMsg).position(Pos.TOP_RIGHT).showInformation();
  }
}
