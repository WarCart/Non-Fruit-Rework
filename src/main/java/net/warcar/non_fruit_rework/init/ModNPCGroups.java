package net.warcar.non_fruit_rework.init;

import xyz.pixelatedw.mineminenomi.api.crew.Crew;

import java.time.Instant;
import java.util.UUID;

public class ModNPCGroups {
    public static final Crew BEAST_PIRATES = registerCrew(new Crew("Beasts Pirates", UUID.fromString("a1993dbf-fb81-46cc-8961-207085d5a31a"), "CapitanMarker", Instant.now().getEpochSecond()));

    private static Crew registerCrew(Crew crew) {
        return crew;
    }

    public static void init() {
    }
}
