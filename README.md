# Alternative Chunkloading

A forge mod for Minecraft 1.7.10 that stops so called "ghost loading" or "cascade loading" of chunks.

## Technical background

It make use of a vanilla toggle on each `ChunkProviderServer` instance to stop automatic chunkloading whenever a block or entity is requested. Those chunks can then only be loaded explicitely.

Two experimental options for loading chunks on request has been added.
This can be used to prevent Mods (and core Minecraft itself) from loading chunks whenever a block is requested there. This also means "ghost loading" or "cascade loading" chunks.
However, this will help with performance just slightly. It hightly depends on what other mods you use and how you use the mods.

Basically for a Mod, if it needs a Block at Position XYZ, it calls `getBlock()` or `getTileEntity()` or `getEntity`. By default, this methods will load the chunk where the block or entity is loaced in. Often this is not expected and in some cases can cause lags. G.g. AE2 or Ender IO Conduits or any other multiblock structure can be a cause for such a behavior. Even Minecraft's Grass block that wants to extend its Grass to nearby dirt blocks.
There is one configruation field in the `ChunkProviderServer` class called `loadChunkOnProvideRequest` that is true by default. Setting this to false on each instance on `WorldEvent.Load` event only loads chunks when explicitely loaded via e.g. `loadChunk(x, y, z)`.

## Configuration

If `disableChunkLoadingOnRequest` is `true` (default is `true`) then the mod sets the vanilla toggle (see above) to `false`.
Think about switching [to my fork of Chicken Chunks](https://github.com/Pilzinsel64/ChickenChunks/). I added a fix to [load the chunk before requesting the block](https://github.com/LITW-Refined-New-Stories/ChickenChunks/commit/f0b54095567591b799a90fc2bade1ba5ad5e3c96) (that's how it should be done in general).

Also set `autoLoadChunksOnTicketCreation` to `true` (default ist `true`) if you don't know what you do!
This continues functionality of chunk loading tickers. So anytime whenever a chunk loading ticket is created by a Mod, the chunks will be also loaded (Forge itself doesn't that automatically).

## Compatibility

Basically all mods should work like before. I'm using this since over a year now now without bad side-effects. The general performance is slightly better while the TPS may increase slightly when loading/unloading many chunks at the same time (like when flying into one direction).

Remember that this way mods or players usages that relay on the feature to automatically load a chunk when just requesting a block (like technic mods that allows you very long pipelines for example) will not work anymore. You now need to ensure that each chunk wich contains relevant things to be chunkloaded.

Mods, like FTB Utilities or Server Utilities should work like before. But all chunkloaders that uses a block will probably need a fix in the code. Think about using [my fork of Chicken Chunks](https://github.com/Pilzinsel64/ChickenChunks/).
