// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKEvent;
import io.ooni.mk.MKLibrary;
import io.ooni.mk.MKTask;

public class MKNDTTest {
    public static void main(String[] args) {
        MKLibrary.load();
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
        MKTask task = MKTask.startNettest(settings);
        while (!task.isDone()) {
            MKEvent event = task.waitForNextEvent();
            System.out.println(event.serialize());
        }
    }
}
