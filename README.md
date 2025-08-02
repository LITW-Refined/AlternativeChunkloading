# Alternative Chunkloading

A forge mod for Minecraft 1.7.10 that stops so called "ghost loading" or "cascade loading" of chunks.

## Technical background

The mod toggles `loadChunkOnProvideRequest` in `ChunkProviderServer` to `false`, stopping chunks from auto-loading when blocks/entities are requested (e.g. `getBlock()`, `getTileEntity()` or `getEntity()`). Chunks must now be explicitly loaded via `loadChunk()` before. This helps prevent lag caused by mods (e.g. AE2, Ender IO) unintentionally loading chunks.

This mod adds several options to allow safer chunk requests. These reduce performance issues depending on your mod setup. Grass spread, multiblocks, or pipelines can trigger unintended loading. The mod prevents this by disabling that called toggle on `WorldEvent.Load`.

## Configuration

Take a look to the in the file `alternativechunkloading.cfg` in your `config` directory. Every option is explained well there.

## Compatibility

Most mods work unchanged. I've run this mod for over a year without major issues. Slight TPS improvements during mass chunk loads. Some mods needed patches via mixins or PRs (see GitHub for details).

### Patched mods

Mods that has been patched directly or via mixin. The most fixes does even have benefit without Alternative Chunkloading installed as they now use the generally prefered way.

- [Flood Lights](https://github.com/GTNewHorizons/NaturesCompass) ([PR](https://github.com/GTNewHorizons/FloodLights/pull/8))
- [Natures Compass](https://github.com/GTNewHorizons/NaturesCompass) ([PR](https://github.com/GTNewHorizons/NaturesCompass/pull/10))
- [Erebus](https://github.com/vadis365/TheErebus) *(Mixin: Fix NullPointerException)*
- [Dimensional Doors](https://github.com/embeddedt/DimensionalDoors-1.7.10) *(Mixin: Fix portal creation)*
- [Chicken Chunks](https://github.com/LITW-Refined/ChickenChunks)
- Minecraft *(Mixin: load chunk before generating a nether portal)*

### Note for mods that relay on the vanilla behavior

Mods like FTB Utilities or Server Utilities work fine. Block-based loaders may need fixes. Consider my [Chicken Chunks fork](https://github.com/LITW-Refined/ChickenChunks) or open an issue or PR.

### Chunkloaders

Mods, like FTB Utilities or Server Utilities should work like before. But all chunkloaders that uses a block will probably need a fix in the code. Think about using [my fork of Chicken Chunks](https://github.com/LITW-Refined/ChickenChunks).

## Contribution

Feel free to open PRs for features, improvements, or compatibility fixes. I'm maintaining this at minimal effort for use on my server/modpack.
