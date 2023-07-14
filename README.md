# HarderMC

A plugin that makes various things *harder*

## Changes

1. eye of ender requires 2 blaze powder
2. End portals close in 10 minutes
3. Ender pearls are removed from piglin barter
4. Evokers don't drop totems of undying making them unobtainable
5. Mining below 30 in the nether increases durability usage
6. Enchantment table now requires crying obsidian and 2 ghast tears
7. Elytra now uses 40x more durability
8. Armor progression
9. mending is now 10x less effective
10. Shield recipe requires 2 scutes now
11. 2 minute pvp cooldown
12. Villager changes
    - Armor removed from trades
    - 1 in 7 chance for curing to fail
    - tools from villagers are damaged
    - limit emerald trades to 4 usages
13. Beds no longer explode in the nether / end and respawn anchors don't either
14. End crystals can only be placed on bedrock
15. replace all loot armor with enchantment books
16. Limit some good enchantments to a lower level
17. A written book with all the changes

## TODO

1. Make explosions less op
2. Change shulkers to have 1 row of items instead of 3 (blocked)
3. respawn end crystals after a couple of minutes if dragon fight is still going

## Server Plugin features

1. Custom spawn logic
    - Players spawn 800 blocks away from spawn in 32 directions
    - Players get another random 128 blocks around that point
2. Custom server icon / MOTD

## Server Plugin TODO:

8. change end world daily
9. vanish
9. turn players into zombies when leaving for up to 1h

## Config

The config will automatically be generated on startup this is a snapshot of what it could look like

```yml
feature:
    CustomRecipeManager:
        customShieldEnabled: true
        customEyeOfEnderEnabled: true
        customEnchantmentTableEnabled: true
        helpBookEnabled: true
        enabled: true
        customArmorEnabled: true
    LimitEnchantments:
        enabled: true
    MendingNerf:
        enabled: true
        factor: 10.0
    NoExplodieBed:
        enabled: true
        disableEndAnchor: true
        disableEndBed: true
        disableNetherBed: true
        disableOverworldAnchor: true
        enableObsidianEndCrystal: false
    ChestLootToBooks:
        enabled: true
    SmallerShulkers:
        enabled: true
    NoTotem:
        enabled: true
    PortalCloser:
        enabled: true
        delayTicks: 12000
    PVPRegenCooldown:
        enabled: true
        cooldownSeconds: 120
    VillagerTradeModifier:
        enabled: true
    NetherMining:
        enabled: true
        depth: 30
        factor: 5
    MoreElytraDamage:
        enabled: true
        factor: 40
    PiglinEnderPearlRemover:
        enabled: true
    MoreHorseLeather:
        enabled: true
        extraLeatherCount: 4
        extraLeatherCountRandom: 4
    CustomSpawnLogic:
        enabled: true
        numSpawnPoints: 32
        spawnDistance: 800
        spawnRadius: 128
    ServerMotD:
        enabled: true
        maxPlayers: 0
        serverName: HarderMC
```

## License

[License](./LICENSE)

