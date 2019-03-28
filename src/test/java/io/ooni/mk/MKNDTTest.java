// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKEvent;
import io.ooni.mk.MKTask;

public class MKNDTTest {
    public static void main(String[] args) {
        System.loadLibrary("mkall");
        String settings = new String();
        {
            settings += "{\n";
            settings += "  \"name\": \"Ndt\",\n";
            settings += "  \"log_level\": \"INFO\",\n";
            settings += "  \"options\": {\n";
            settings += "    \"net/ca_bundle_path\": \"cacert.pem\",\n";
            settings += "    \"geoip_country_path\": \"country.mmdb\",\n";
            settings += "    \"geoip_asn_path\": \"asn.mmdb\"\n";
            settings += "  }\n";
            settings += "}\n";
        }
        MKTask task = MKTask.start(settings);
        while (!task.isDone()) {
            String event = task.waitForNextEvent();
            System.out.println(event);
        }
    }
}
