# RoadMap for the GlassPane framework

---
## GUIs:
- [ ] pre-built styles
    - [ ] Vanilla
    - [ ] Thermal Mods
    - [ ] Futuristic
- [ ] pre-built accept/cancel, yes/no
- [ ] easy lists
- [ ] warning for mismatching fingerprints "Continue loading at your own risk" or "Shutdown MC instance"
- [ ] easy text list
- [x] color picker utility
- [x] sliders

---
## basic tools, weapons, other items:
- [x] base item
- [ ] food
- [ ] shield
- [ ] sword
- [ ] tools
- [ ] multi tool
- [ ] hoe (enchantable!)
- [ ] shears
- [x] armor
- [ ] Bow
- [ ] Custom Arrows
- [ ] Mod GuideBook
    - [ ] JSON-based
    - [ ] Markdown-based
    - [ ] HTML-based
- [x] skinnable items
-----
##Structures:
- [ ] nbt
- [ ] schematic
- [ ] custom format (--> st0rm)
- [ ] importer (--> file selector)
- [ ] exporter (--> save to external file)
- [ ] placement preview

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
- [ ] Update-Checker
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
    - [ ] Enchantability Event
- [ ] Server Rescue
    - List of compromised Block positions to be cleared at world load
        ex:
        0:(0, 0, 0)           - single pos (0, 0, 0) in the overworld
        -1:(0, 0, 0):(1, 1, 1) - area from (0, 0, 0) to (1, 1, 1) in the nether
- [ ] JSON renderer
- [ ] auto-generate block and item models in hte dev workspace
- [ ] dependency downloader (json-based)
- [ ] installer (will install latest Forge and the mod)
       
---
## Vanity
- [ ] basic user matcher
- [ ] modular feature sets for users

---
## Additions
- [ ] Double Chests break completely when mined with axe
- [ ] Baby-Zombies burn in daylight
- [ ] Death Messages for named, non-player Entities
- [ ] Creepers explode when set on fire
- [ ] Bow Infinity Fix
- [ ] bed sleep bug fix
- [ ] slab over chest toggle
- [ ] Chat Color Code Support by typing `&0-f` [Colors](https://minecraft.gamepedia.com/Formatting_codes "Formatting Codes - Minecraft Wiki")
- [ ] CobWebs can be crafted with 5 Strings
