package x;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Storm {

    @XmlAttribute
    String stormid;
    @XmlAttribute
    String name;
    @XmlAttribute
    XMLGregorianCalendar from;
    @XmlAttribute
    XMLGregorianCalendar thru;
    @XmlAttribute
    BigDecimal north;
    @XmlAttribute
    BigDecimal south;
    @XmlAttribute
    BigDecimal west;
    @XmlAttribute
    BigDecimal east;
    @XmlAttribute
    int maxwind;
    @XmlAttribute
    int minpressure;
    @XmlAttribute
    String maxcategory;
    @XmlAttribute
    String basin;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("stormid", stormid).
                append("name", name).
                append("from", from).
                append("thru", thru).
                append("north", north).
                append("south", south).
                append("west", west).
                append("east", east).
                append("maxwind", maxwind).
                append("minpressure", minpressure).
                append("maxcategory", maxcategory).
                append("basin", basin).
                toString();
    }
}
