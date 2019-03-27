// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKGeoIPLookupSettings;
import io.ooni.mk.MKGeoIPLookupResults;

public class MKGeoIPLookupTest {
    public static void main(String[] args) {
        System.loadLibrary("mkall");
        MKGeoIPLookupSettings settings = new MKGeoIPLookupSettings();
        settings.setTimeout(14);
        settings.setCABundlePath("cacert.pem");
        settings.setCountryDBPath("country.mmdb");
        settings.setASNDBPath("asn.mmdb");
        MKGeoIPLookupResults results = settings.perform();
        System.out.println("Good      : " + results.isGood());
        System.out.println("Probe IP  : " + results.getProbeIP());
        System.out.println("Probe ASN : " + results.getProbeASN());
        System.out.println("Probe CC  : " + results.getProbeCC());
        System.out.println("Probe Org : " + results.getProbeOrg());
        System.out.print(results.getLogs());
    }
}
