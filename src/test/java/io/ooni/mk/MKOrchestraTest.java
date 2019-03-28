// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKOrchestraSettings;
import io.ooni.mk.MKOrchestraResults;

public class MKOrchestraTest {
    public static void main(String[] args) {
        System.loadLibrary("mkall");
        MKOrchestraSettings settings = new MKOrchestraSettings();
        settings.setAvailableBandwidth("10110111");
        settings.setDeviceToken("5f2c761f-2e98-43aa-b9ea-3d34cceaab15");
        settings.setCABundlePath("cacert.pem");
        settings.setGeoIPCountryPath("country.mmdb");
        settings.setGeoIPASNPath("asn.mmdb");
        settings.setLanguage("it_IT");
        settings.setNetworkType("wifi");
        settings.setPlatform("macos");
        // Disabled so that the library will need to guess them
        //settings.setProbeASN("AS30722");
        //settings.setProbeCC("IT");
        settings.setProbeFamily("sbs");
        settings.setProbeTimezone("Europe/Rome");
        settings.setRegistryURL("https://registry.proteus.test.ooni.io");
        settings.setSecretsFile(".orchestra.json");
        settings.setSoftwareName("mkall-java");
        settings.setSoftwareVersion("0.1.0");
        settings.addSupportedTest("web_connectivity");
        settings.addSupportedTest("ndt");
        settings.setTimeout(14);
        {
            MKOrchestraResults results = settings.updateOrRegister();
            System.out.println("Good      : " + results.isGood());
            System.out.print(results.getLogs());
        }
        settings.setNetworkType("mobile");
        {
            MKOrchestraResults results = settings.updateOrRegister();
            System.out.println("Good      : " + results.isGood());
            System.out.print(results.getLogs());
        }
    }
}
