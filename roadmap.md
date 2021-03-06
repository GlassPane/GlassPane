# RoadMap for the GlassPane framework

---
## GUIs:
- [ ] pre-built styles
    - [ ] Vanilla
    - [ ] Thermal Mods
    - [ ] Futuristic
- [ ] pre-built accept/cancel, yes/no
- [ ] easy lists
- [x] warning for mismatching fingerprints "Continue loading at your own risk" or "Shutdown MC instance"
- [x] easy text list
- [x] color picker utility
- [x] sliders

---
## basic tools, weapons, other items:
- [x] base item
- [x] food
- [ ] shield
- [x] sword
- [x] tools
- [x] hoe
- [ ] shears
- [x] armor
- [ ] Bow
- [ ] Custom Arrows
- [ ] Mod GuideBook
    - [ ] JSON-based
    - [ ] Markdown-based
    - [ ] HTML-based
    - [x] guide item
- [x] base skinnable item

---
## MISC
- [ ] EULA (?)
- [ ] enchantment
- [ ] biome
- [ ] dimension
- [ ] block
- [ ] silverfish block //TODO make silverfishes hide in there --> forge issue?
- [ ] tileentity
- [ ] Creative Tab
    - [ ] random icon from tab items
    - [ ] random icon from predefined list
- [x] Update-Checker
- [ ] World Generation
    - [ ] basic ore generation (single block type per vein)
    - [ ] custom generation
    - [ ] (large) structure generation
    - [ ] randomized tree generation
- [ ] Inventory Handling
    - [ ] Inventory Wrapper for ItemStacks (capability-based)   
- [ ] Extra Events
    - [ ] Biome Temperature Event (requires manual activation)
    - [ ] Sword SweepEvent (WIP)
    - [ ] Enchantability Event //will probably be added to Forge instead
    - [x] Event for registering additional render layers for entities
    - [x] event informing about outdated mods (for the Update-Checker)
- [ ] Server Rescue
    - List of compromised Block positions to be cleared at world load
        ex:
        0:(0, 0, 0)           - single pos (0, 0, 0) in the overworld
        -1:(0, 0, 0):(1, 1, 1) - area from (0, 0, 0) to (1, 1, 1) in the nether
- [ ] JSON renderer
- [ ] auto-generate block and item models in the dev workspace
- [ ] dependency downloader (json-based)
- [ ] installer (will install latest Forge and the mod)
- [x] convenient registration methods using an Annotation
    - [x] post-processing registered elements
- [x] simple capability providers
- [x] simple color palettes
- [x] folder resourcepack
- [x] convenient loading of additional resources from the `/resources` folder
- [x] JSON (De)Serializer for utility
- [ ] easy structure loading
    - [ ] nbt
    - [ ] schematic
    - [ ] custom format (--> st0rm)
    - [ ] importer (--> file selector)
    - [ ] exporter (--> save to external file)
    - [ ] placement preview

---
## Vanity
- [x] basic user matcher
- [x] modular feature sets for users

---
## Additions
- [ ] Double Chests break completely when mined with axe
- [ ] Baby-Zombies burn in daylight
- [ ] Death Messages for named, non-player Entities
- [ ] Creepers explode when set on fire
- [ ] Bow Infinity Fix
- [ ] bed sleep bug fix
- [ ] slab over chest toggle
- [x] Chat Color Code Support by typing `&0-f` [Colors](https://minecraft.gamepedia.com/Formatting_codes "Formatting Codes - Minecraft Wiki")
- [ ] CobWebs can be crafted with 5 Strings
