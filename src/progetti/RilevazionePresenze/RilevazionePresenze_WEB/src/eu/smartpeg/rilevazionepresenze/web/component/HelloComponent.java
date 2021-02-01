package eu.smartpeg.rilevazionepresenze.web.component;

import java.io.IOException;
import java.util.Date;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import it.webred.utils.DateFormat;

@FacesComponent(value="HelloComponent")
public class HelloComponent extends UIComponentBase {
  @Override
  public String getFamily() {
      return "Greeting";
  }

  @Override
  public void encodeBegin(FacesContext context) throws IOException {
      String message = (String) getAttributes().get("message");
      Date time = (Date) getAttributes().get("time");
      String formattedTime = DateFormat.dateToString(time, "yyyy.MM.dd G 'at' HH:mm:ss z\"");

      ResponseWriter writer = context.getResponseWriter();
      writer.startElement("p", this);
      writer.write("Message: " + message);
      writer.endElement("p");

      writer.startElement("p", this);
      writer.write("Time: " + formattedTime);
      writer.endElement("p");
  }
}