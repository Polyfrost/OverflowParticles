package cc.polyfrost.oneconfig.internal.config.core;

import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigCore {
    public static List<Mod> mods = new ArrayList<>();
    public static HashMap<Mod, List<Mod>> subMods = new HashMap<>();
}