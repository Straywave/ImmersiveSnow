<img src="common/src/main/resources/assets/immersivesnow/iconhq.png" width="128" align="right" />

Immersive Snow
==============

[![Mod Version](https://shields.io/modrinth/v/immersive-snow)](https://modrinth.com/mod/immersive-snow)

Small tweaks that add to Minecraft's Winter theme. Intended for use with a season mod.

Features:

- **Snow recalculation** upon chunk loading. No more winter chunks in the summer!
- **Changing day cycle duration** depending on the season. Longer nights in the winter.

### Compatibility

The following season mods are supported:

- [Serene Seasons (Forge)][1]
    - A special hook is used as it does not actually modify the biome temperature method but rather modifies all vanilla
      code that uses it.
- [Fabric Seasons (Fabric)][2]
    - Full compatibility out of the box without special tricks

_**Hourglass integration requires Forge and Serene Seasons.**_ It will overwrite your `daySpeed` and `nightSpeed` to
match the current season, however you can set a `hourglassDaySpeed` / `hourglassNightSpeed` in the configuration file if
you wish. If you want, you can disable it entirely in the configuration. (Hourglass for 1.16.5 is on CurseForge)

### Performance

Some performance degradations are a given, since the mod has to change 16x16=256 blocks per chunk upon every chunk
loading. If you see a noticeable performance impact, please file an issue in the tracker with a Spark profiler report
for investigation.

### Acknowledgements

This mod is a remake of my private mod BetterSereneSeasons, which was originally a 1.19.2 Forge remake
of [Serene Tweaks][4].

[1]: https://curseforge.com/minecraft/mc-mods/serene-seasons
[2]: https://modrinth.com/mod/fabric-seasons
[3]: https://curseforge.com/minecraft/mc-mods/hourglass
[4]: https://github.com/FIREdog5/SereneTweaks
