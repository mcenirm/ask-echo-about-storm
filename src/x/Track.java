package x;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Track {

    @XmlAttribute
    XMLGregorianCalendar date;
    @XmlAttribute
    String category;
    @XmlAttribute
    BigDecimal latitude;
    @XmlAttribute
    BigDecimal longitude;
    @XmlAttribute
    int windspeed;
    @XmlAttribute
    int pressure;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("date", date).
                append("category", category).
                append("latitude", latitude).
                append("longitude", longitude).
                append("windspeed", windspeed).
                append("pressure", pressure).
                toString();
    }
}
