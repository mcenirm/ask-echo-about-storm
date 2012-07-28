package x;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement
public class StormSearchResponse {

    @XmlElement(name = "Storm")
    Storm[] storms;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("storms", storms).
                toString();
    }
}
