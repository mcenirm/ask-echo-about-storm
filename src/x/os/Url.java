package x.os;

import javax.xml.bind.annotation.XmlAttribute;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Url {

    @XmlAttribute
    public String type;
    @XmlAttribute
    public int indexOffset;
    @XmlAttribute
    public String template;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("type", type).
                toString();
    }
}
