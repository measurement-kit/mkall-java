// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKCollectorResubmitSettings;
import io.ooni.mk.MKCollectorResubmitResults;

public class MKCollectorResubmitTest {
    // This is a compelling argument to always use Kotlin
    static String origMeasurement = "{\n" +
            "\"data_format_version\": \"0.2.0\",\n" +
            "\"input\": \"torproject.org\",\n" +
            "\"measurement_start_time\": \"2016-06-04 17:53:13\",\n" +
            "\"probe_asn\": \"AS0\",\n" +
            "\"probe_cc\": \"ZZ\",\n" +
            "\"probe_ip\": \"127.0.0.1\",\n" +
            "\"software_name\": \"measurement_kit\",\n" +
            "\"software_version\": \"0.2.0-alpha.1\",\n" +
            "\"test_keys\": {\"failure\": null,\"received\": [],\"sent\": []},\n" +
            "\"test_name\": \"tcp_connect\",\n" +
            "\"test_runtime\": 0.253494024276733,\n" +
            "\"test_start_time\": \"2016-06-04 17:53:13\",\n" +
            "\"test_version\": \"0.0.1\"\n" +
        "}\n";

    public static void main(String[] args) {
        System.loadLibrary("mkall");
        MKCollectorResubmitSettings settings = new MKCollectorResubmitSettings();
        settings.setTimeout(14);
        settings.setCABundlePath("cacert.pem");
        settings.setSerializedMeasurement(origMeasurement);
        MKCollectorResubmitResults results = settings.perform();
        System.out.println("Good         : " + results.isGood());
        System.out.println("Measurement  : " + results.getUpdatedSerializedMeasurement());
        System.out.print(results.getLogs());
    }
}
