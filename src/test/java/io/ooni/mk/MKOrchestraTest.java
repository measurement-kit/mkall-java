// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKLibrary;
import io.ooni.mk.MKOrchestraClient;
import io.ooni.mk.MKOrchestraResult;

public class MKOrchestraTest {
    public static void main(String[] args) {
        MKLibrary.load();
        MKOrchestraClient client = new MKOrchestraClient();
        client.setAvailableBandwidth("10110111");
        client.setDeviceToken("5f2c761f-2e98-43aa-b9ea-3d34cceaab15");
        client.setCABundlePath("cacert.pem");
        client.setGeoIPCountryPath("country.mmdb");
        client.setGeoIPASNPath("asn.mmdb");
        client.setLanguage("it_IT");
        client.setNetworkType("wifi");
        client.setPlatform("macos");
        // Disabled so that the library will need to guess them
        //client.setProbeASN("AS30722");
        //client.setProbeCC("IT");
        client.setProbeFamily("sbs");
        client.setProbeTimezone("Europe/Rome");
        client.setRegistryURL("https://registry.proteus.test.ooni.io");
        client.setSecretsFile(".orchestra.json");
        client.setSoftwareName("mkall-java");
        client.setSoftwareVersion("0.1.0");
        client.addSupportedTest("web_connectivity");
        client.addSupportedTest("ndt");
        client.setTimeout(14);
        {
            MKOrchestraResult result = client.sync();
            System.out.println("Good      : " + result.good());
            System.out.print(new String(result.getBinaryLogs()));
        }
        client.setNetworkType("mobile");
        {
            MKOrchestraResult result = client.sync();
            System.out.println("Good      : " + result.good());
            System.out.print(new String(result.getBinaryLogs()));
        }
    }
}
