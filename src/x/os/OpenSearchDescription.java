package x.os;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement(namespace = OpenSearchDescription.NS_os)
public class OpenSearchDescription {

    public static final String NS_os = "http://a9.com/-/spec/opensearch/1.1/";
    public static final String NS_echo = "http://www.echo.nasa.gov/esip";
    public static final String NS_georss = "http://a9.com/-/opensearch/extensions/geo/1.0/";
    public static final String NS_time = "http://a9.com/-/opensearch/extensions/time/1.0/";
    @XmlElement(name = "ShortName", namespace = NS_os)
    public String shortName;
    @XmlElement(name = "Description", namespace = NS_os)
    public String description;
    @XmlElement(name = "Tags", namespace = NS_os)
    public String tags;
    @XmlElement(name = "Contact", namespace = NS_os)
    public String contact;
    @XmlElement(name = "Url", namespace = NS_os)
    public Url[] urls;
    @XmlElement(name = "Query", namespace = NS_os)
    public Query[] queries;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("ShortName", shortName).
                toString();
    }
}
