Immersive Atmosphere
====================

Recalculates snow upon entering a chunk, a great companion to season mods!

The only dependency is Fabric API (on Fabric). This mod does not specifically link into other season mods, you can even
use it without one!

The following mods are supported:

- [Serene Seasons (Forge)][1]
  - A special hook is used as it does not actually modify the biome temperature method but rather modifies all vanilla code that uses it.
- [Fabric Seasons (Fabric)][2]
  - Full compatibility out of the box without special tricks

Do note that snow recalculation causes some lag if the blocks need to be changed. Combine this mod with performance mods
for best results. Further performance improvements are coming in the future.

### Future Plans

- (Optional) Integration with [Hourglass][3] on Forge to change day duration to match season from [Serene Seasons][1].

### Acknowledgements

This mod is a remake of my private mod BetterSereneSeasons, which was originally a 1.19.2 Forge remake of [Serene Tweaks][4].

[1]: https://curseforge.com/minecraft/mc-mods/serene-seasons
[2]: https://curseforge.com/minecraft/mc-mods/fabric-seasons
[3]: https://www.curseforge.com/minecraft/mc-mods/hourglass
[4]: https://github.com/FIREdog5/SereneTweaks
