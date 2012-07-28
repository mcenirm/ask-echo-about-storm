package x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXB;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class X {

    /**
     *
     * @param args
     * @throws ConfigurationException
     */
    public static void main(String[] args) throws ConfigurationException, MalformedURLException, IOException {
        Configuration config = new PropertiesConfiguration("x.properties");
        File out = new File(config.getString("out", "out"));
        out.mkdirs();
        int fromYear = config.getInt("fromYear", 2000);
        int thruYear = config.getInt("thruYear", 2000);
        double stormRadiusMiles = config.getDouble("stormRadiusMiles");
        double milesPerDegreeAtEquator = config.getDouble("milesPerDegreeAtEquator");
        int stormTimeDeltaHours = config.getInt("stormTimeDeltaHours");

        String servicesSearchUrl = config.getString("servicesSearchUrl");
        String servicesTrackUrl = config.getString("servicesTrackUrl");
        /*
         Iterator<String> osddKeys = config.getKeys("osdd");
         String nextOsddKey = osddKeys.next();
         String nextOsddName = nextOsddKey.replaceFirst("osdd\\.", "");
         URL nextOsddUrl = new URL(config.getString(nextOsddKey));
         File nextOsddFile = new File(out, nextOsddKey + ".xml");
         download(nextOsddUrl, nextOsddFile);
         OpenSearchDescription nextOsdd = JAXB.unmarshal(nextOsddFile, OpenSearchDescription.class);
         System.out.println(nextOsdd);
         for (Url url : nextOsdd.urls) {
         if ("application/atom+xml".equals(url.type)) {
         System.out.println(url.template);
         URL template = new URL(url.template);
         String q = template.getQuery();
         System.out.println(q);
         String qq = q.replaceAll("[{][^?]*\\?}", "");
         System.out.println(qq);
         }
         }
         */
        String echoUrlTemplate = config.getString("echo.url");

        File servicesSearchFile = new File(out, String.format(Locale.US, "StormSearchResponse-%d-%d.xml", fromYear, thruYear));
        download(new URL(servicesSearchUrl), servicesSearchFile);

        Track mehTrack = null;
        File mehTrackDir = null;

        StormSearchResponse o = JAXB.unmarshal(servicesSearchFile, StormSearchResponse.class);
        for (Storm storm : o.storms) {
            File stormDir = new File(out, "storm-" + storm.stormid);
            stormDir.mkdirs();
            File stormFile = new File(stormDir, "storm-" + storm.stormid + ".xml");
            JAXB.marshal(storm, stormFile);
            File servicesTrackFile = new File(stormDir, "StormTrackResponse-" + storm.stormid + ".xml");
            download(new URL(servicesTrackUrl + storm.stormid), servicesTrackFile);
            StormTrackResponse p = JAXB.unmarshal(servicesTrackFile, StormTrackResponse.class);
            for (Track track : p.tracks) {
                if (track.date.getHour() % 6 != 0 || track.date.getMinute() != 0 || track.date.getSecond() != 0) {
                    Logger.getLogger(X.class.getName()).log(Level.WARNING, "weird time: {0} {1}", new Object[]{storm.stormid, track.date});
                }
                String dateStr = track.date.toString().replaceAll(":.*", "");
                File trackDir = new File(stormDir, "track-" + dateStr);
                trackDir.mkdirs();
                File trackFile = new File(trackDir, "track-" + storm.stormid + "-" + dateStr + ".xml");
                JAXB.marshal(track, trackFile);
                //System.out.println("" + trackFile + " " + trackFile.length());
                if (mehTrack == null) {
                    mehTrack = track;
                    mehTrackDir = trackDir;
                }
            }
        }

        double latitude = mehTrack.latitude.doubleValue();
        double longitude = mehTrack.longitude.doubleValue();
        double deltaLatitude = stormRadiusMiles / milesPerDegreeAtEquator;
        double deltaLongitude = deltaLatitude / Math.cos(Math.toRadians(latitude));
        //System.out.println(String.format("%.1f ± %.2f , %.1f ± %.2f", latitude, deltaLatitude, longitude, deltaLongitude));
        String box = String.format("%.1f,%.1f,%.1f,%.1f", longitude - deltaLongitude, latitude - deltaLatitude, longitude + deltaLongitude, latitude + deltaLatitude);
        mehTrack.date.setTimezone(0);
        GregorianCalendar startDate = mehTrack.date.toGregorianCalendar();
        startDate.add(Calendar.HOUR, -stormTimeDeltaHours);
        String startTime = DatatypeConverter.printDateTime(startDate);
        GregorianCalendar endDate = mehTrack.date.toGregorianCalendar();
        endDate.add(Calendar.HOUR, stormTimeDeltaHours);
        String endTime = DatatypeConverter.printDateTime(endDate);
        String mehEchoUrl = echoUrlTemplate;
        mehEchoUrl = mehEchoUrl.replaceAll("\\{georss:box\\?}", box);
        mehEchoUrl = mehEchoUrl.replaceAll("\\{time:start\\?}", startTime);
        mehEchoUrl = mehEchoUrl.replaceAll("\\{time:end\\?}", endTime);
        mehEchoUrl = mehEchoUrl.replaceAll("[{][^?]*\\?}", "");
        System.out.println(mehEchoUrl);
        File mehEchoFile = new File(mehTrackDir, "echo1.xml");
        download(new URL(mehEchoUrl), mehEchoFile);
        System.out.println("" + mehEchoFile + " " + mehEchoFile.length());
    }

    private static void download(URL from, File to) throws IOException {
        if (!to.exists()) {
            Logger.getLogger(X.class.getName()).log(Level.WARNING, "downloading {0}", from);
            InputStream in = from.openStream();
            OutputStream out = new FileOutputStream(to);
            byte[] b = new byte[1000];
            int n;
            while ((n = in.read(b)) > -1) {
                out.write(b, 0, n);
            }
            out.close();
            in.close();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(X.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
