// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKEvent;
import io.ooni.mk.MKTask;

public class MKWebConnectivityTest {
    public static void main(String[] args) {
        System.loadLibrary("mkall");
        String settings = new String();
        {
            settings += "{\n";
            settings += "  \"name\": \"WebConnectivity\",\n";
            settings += "  \"inputs\": [\n";
            settings += "      \"https://www.torproject.org\",\n";
            settings += "      \"https://x.org\",\n";
            settings += "      \"https://slashdot.org\"\n";
            settings += "  ],\n";
            settings += "  \"log_level\": \"INFO\",\n";
            settings += "  \"options\": {\n";
            settings += "    \"net/ca_bundle_path\": \"cacert.pem\",\n";
            settings += "    \"geoip_country_path\": \"country.mmdb\",\n";
            settings += "    \"geoip_asn_path\": \"asn.mmdb\"\n";
            settings += "  }\n";
            settings += "}\n";
        }
        MKTask task = MKTask.startNettest(settings);
        while (!task.isDone()) {
            MKEvent event = task.waitForNextEvent();
            System.out.println(event.serialize());
        }
    }
}
