package x;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ToStringBuilder;

@XmlRootElement
public class StormTrackResponse {

    @XmlAttribute
    String stormid;
    @XmlAttribute
    String name;
    @XmlAttribute
    String pnumber;
    @XmlAttribute
    String hnumber;
    @XmlAttribute
    String from;
    @XmlAttribute
    String thru;
    @XmlAttribute
    BigDecimal minlat;
    @XmlAttribute
    BigDecimal maxlat;
    @XmlAttribute
    BigDecimal minlon;
    @XmlAttribute
    BigDecimal maxlon;
    @XmlAttribute
    int maxwind;
    @XmlAttribute
    int minpress;
    @XmlAttribute
    String maxcat;
    @XmlAttribute
    String basin;
    @XmlElement(name = "Track")
    Track[] tracks;

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("stormid", stormid).
                append("name", name).
                append("pnumber", pnumber).
                append("hnumber", hnumber).
                append("from", from).
                append("thru", thru).
                append("minlat", minlat).
                append("maxlat", maxlat).
                append("minlon", minlon).
                append("maxlon", maxlon).
                append("maxwind", maxwind).
                append("minpress", minpress).
                append("maxcat", maxcat).
                append("basin", basin).
                append("tracks", tracks.length).
                toString();
    }
}
