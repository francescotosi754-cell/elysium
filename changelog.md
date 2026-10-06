- 0.6.1
-
#### Changes
- [DISABLED] Added potion for cleanser effect
- Added subcommand to get a player's last saved anchor time
- Angelic Aura claim argument now doesn't give regen to untrusted players
- Made ALL crafting screens obey the `elysium:remove_recipes` power
- Make admin claims able to override each other if overlapping
- Make `minecraft:horse` and related mobs implement the `Tameable` interface
- Make boats implement the `Tameable` interface
- Update `mixin-extras` to `0.2.0-rc.5`
- Add potions for the SpellPower status effects
- New contributor @DoctorWho

#### Dependencies
- origins
- trinkets
- arrp
- numismatic-overhaul
- archers-arsenal
- supplementaries
- goml-reserved
- vanish
- spell-power
#### Breaks
- memoryleakfix: `1.1.1`

0.6.0
-
#### Changes
- Replaced `spawnRadius` with `distanceRadiusBetween` and renamed `spawnCenterPosition` to `spawnPosition`
- Removed linear tax algorithm and related options
- Removed unused `summon-random` command
- Small bait item rework
- Made anti-cheat a bit smarter
- Add log when an anchor is missing time data
- Fix dimensional gamemode not changing upon death
- Small internal refactor
- Merge changes from DreamingCodes's branch
- Block unwanted block interactions on the client rather than the server
- Log `goml escape`
- Fix claims losing timestamp data and add backup system
- Log when claims drop for tax causes

#### Dependencies
- origins
- trinkets
- arrp
- numismatic-overhaul
- archers-arsenal
- supplementaries
- goml-reserved
- vanish

0.5.10
-
#### Changes
- Make origin reload not invoke power events
- Make Admin Claim immune to modifications
- Make distance between claims configurable

#### Dependencies
- origins
- trinkets
- arrp
- numismatic-overhaul
- archers-arsenal
- supplementaries
- goml-reserved
- vanish

0.5.9
-
#### Changes
- Fix system messages not being disabled
- Unify debug commands ( crash, broadcast )
- Update MixinExtras
- Fix claims being too close to each other
- Fix vanished players getting hidden system messages
- Replace Wildfire female gender mod's player list screen with player's settings screen
- Added missing power translations
- Remove doors mixins used to fix crawling
- Send current (~10b) claim box to client
- Fix `elysium:longer_potions` stacking
- Make anchors drop when empty and a configurable time is passed ( defaults to 2 days )
- Remove broken feature: "Allow players to break empty anchors"
- Fix time reset on anchor upgrading

#### Dependencies
- origins
- trinkets
- arrp
- numismatic-overhaul
- archers-arsenal
- supplementaries
- goml-reserved
- vanish ( new )
- female-gender ( new )
