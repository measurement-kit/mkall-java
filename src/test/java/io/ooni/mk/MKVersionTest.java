// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk;

import io.ooni.mk.MKVersion;

public class MKVersionTest {
    public static void main(String[] args) {
        System.loadLibrary("mkall");
        System.out.println("Version: " + MKVersion.getVersion());
    }
}
