<img src="common/src/main/resources/assets/immersivesnow/iconhq.png" width="128" align="right" />

Immersive Snow
==============

[![Mod Version](https://shields.io/modrinth/v/immersive-snow)](https://modrinth.com/mod/immersive-snow)

Small tweaks that add to Minecraft's Winter theme. Intended for use with a season mod.

Features:

- **Snow recalculation** upon chunk loading. No more winter chunks in the summer!
- **Changing day cycle duration** depending on the season. Longer nights in the winter.

### Compatibility

The mod is not intended for use on its own. Whilst it will run by itself, it won't do much. The following mods are supported for integrations:

- **Season Mods:** Snow will follow season temperature and recalculate in all chunks when the season changes.
  - [Serene Seasons (Fabric/Forge/NeoForge)][1]
  - [Fabric Seasons (Fabric)][2]
- **Changing Day Cycle:** requires both a Season Mod above, and Better Days.
  - [Better Days (Fabric/NeoForge)][3] (note: currently the integration is implemented by rewriting the `daySpeed` and `nightSpeed` configuration at runtime. To configure the day and night speed, use this mod's `betterDaysDaySpeed` and `betterDaysNightSpeed` options.)
- Snow recalculation is also compatible with [Snow Real Magic][4], in that blocks like grass, flowers, fences, walls, etc. will be replaced with their snowy variant.

### Acknowledgements

This mod is a remake of my private mod BetterSereneSeasons, which was originally a 1.19.2 Forge remake
of [Serene Tweaks][5].

[1]: https://modrinth.com/mod/serene-seasons
[2]: https://modrinth.com/mod/fabric-seasons
[3]: https://modrinth.com/mod/betterdays
[4]: https://modrinth.com/mod/snow-real-magic
[5]: https://github.com/FIREdog5/SereneTweaks
