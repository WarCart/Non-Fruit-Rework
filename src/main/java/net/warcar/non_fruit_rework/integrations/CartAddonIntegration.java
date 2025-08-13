package net.warcar.non_fruit_rework.integrations;

import net.warcar.non_fruit_rework.screens.ScientistScreen;
import net.MrMagicalCart.cartaddon.init.CartRaces;

public class CartAddonIntegration {
    public static void init() {
        ScientistScreen.PristineRaces.create("LUNARIAN", CartRaces.LUNARIAN, entity -> false);
        ScientistScreen.PristineRaces.create("ONI", CartRaces.ONI, entity -> false);
    }
}
