package x.os;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Query {

    @XmlAttribute(namespace = OpenSearchDescription.NS_os)
    String role;
    @XmlAttribute(namespace = OpenSearchDescription.NS_echo)
    String instrument;
    @XmlAttribute(namespace = OpenSearchDescription.NS_echo)
    String satellite;
    @XmlAttribute
    String title;
    @XmlAttribute(namespace = OpenSearchDescription.NS_georss)
    String box;
    @XmlAttribute(namespace = OpenSearchDescription.NS_time)
    XMLGregorianCalendar start;
    @XmlAttribute(namespace = OpenSearchDescription.NS_time)
    XMLGregorianCalendar stop;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("role", role).
                toString();
    }
}
