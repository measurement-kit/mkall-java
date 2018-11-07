// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKLibrary;
import io.ooni.mk.MKGeoIPLookupSettings;
import io.ooni.mk.MKGeoIPLookupResults;

public class MKGeoIPLookupTest {
    public static void main(String[] args) {
        MKLibrary.load();
        MKGeoIPLookupSettings settings = new MKGeoIPLookupSettings();
        settings.setTimeout(14);
        settings.setCABundlePath("cacert.pem");
        settings.setCountryDBPath("country.mmdb");
        settings.setASNDBPath("asn.mmdb");
        MKGeoIPLookupResults results = settings.perform();
        System.out.println("Good      : " + results.good());
        System.out.println("Bytes sent: " + results.getBytesSent());
        System.out.println("Bytes recv: " + results.getBytesRecv());
        System.out.println("Probe IP  : " + results.getProbeIP());
        System.out.println("Probe ASN : " + results.getProbeASN());
        System.out.println("Probe CC  : " + results.getProbeCC());
        System.out.println("Probe Org : " + results.getProbeOrg());
        System.out.print(new String(results.getLogs()));
    }
}
