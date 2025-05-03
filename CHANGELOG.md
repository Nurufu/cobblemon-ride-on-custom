## Changelog

***

### v0.2.5 (Released April 27, 2025)

- Added assorted Mega Showdown items to the `cobbleride:no_mount_items` tag.
- Updated the minimum supported version of Cobblemon to 1.6.1 (mainly to resolve Kotlin issues in the IDE).

***

### v0.2.4 (Released April 24, 2025)

- Changed mounting behavior to allow players to mount with a non-empty hand once more, but with whitelisted item tags
  added to block mounting when specific items are held. This is a compromise to hopefully fix mounting issues introduced
  with the last patch, as well as to test a new means to allow for item interactions to bypass mounting logic. Items
  that should always be prioritized over mounting can be added to the `cobbleride:no_mount_items` tag via datapacks. The
  `cobbleride:no_mount_battle_items` tag has also been added, for items which should only block mounting interactions
  during battle.
- Minor adjustment in server/client-sided logic for mounting, such that the player's rotations should now be set to
  those of the Pokemon on both sides during the mount.

***

### v0.2.3 (Released April 20, 2025)

- Fixed bug where loading a Pokedex where a Pokemon with multiple forms is listed - but where none of the currently
  visible forms are in that dex (e.g. if you had normal Exeggutor but not Alolan and tried to access the Alola dex) -
  would cause a crash.
- Fixed bug where certain mods would not work well with Ride On! due to improperly-coded network mixins. This primarily
  affected Mega Showdown. (Thanks to Yakat_Kaul, the dev of Mega Showdown, for helping debug this one.)
- Fixed bug where evolving a Pokemon would not correctly update its move behaviours. This would cause Pokemon that
  obtained rideability or whose ride behaviours changed post-evolution to not work as intended.
- Reverted behaviour for mounting such that right-clicking while holding an item will no longer trigger a mount. This is
  intended to resolve issues where some items, particularly those which built atop the Item class in Minecraft (such as
  Mega Showdown items), would be overridden by the user mounting their Pokemon instead. While this change effectively
  means you cannot mount Pokemon anymore unless you have an empty hand, I've decided that this will be the better
  solution for now to ensure that other mods and their items work as intended. This change is a temporary compromise
  until the next major update, when planned changes will make this issue obsolete.

***

### v0.2.2 (Released January 15, 2025)

- Fixed issue where some flying Pokemon were inconsistently faster in air versus others. The cause was due to how ride
  speeds were calculated using movement speed values defined per medium in the Cobblemon data files. While most Pokemon
  use default values, some have very different values defined, some approximately twice what the base value is. This
  often led to some Pokemon much faster than others, in spite of what individual modifiers and speed stats should have
  allowed for. To resolve this matter and level the field a bit better, the ride speed formula has been reworked to no
  longer account for walking speeds defined within the Cobblemon base files. As of now, the only values external to the
  mod that the speed formula uses is the speed stat of the Pokemon and the base movement speed of the entity, which
  allows it to remain influenced by effects such as speed potions.
- Fixed issue where experience bar and level number were not being properly hidden while the stamina meter is being
  rendered.
- Fixed issue where compatibility with Pet Your Cobblemon broke after a recent update by the mod.

***

### v0.2.1 (Released January 13, 2025)

- Adjusted dismounting such that it should be more responsive. So long as one key isn't released before the other is
  registered as pressed down, dismounting should work as intended.
- Fixed issue where setting canExhaust to false was disabling sprinting altogether.
- Fixed issue where battles would not end correctly.

***

### v0.2.0 (Released January 13, 2025)

#### Updated controls system!

- Reworked the previous controls system to now anchor onto the keybinds provided by Cobblemon and vanilla Minecraft.
  This means there are no longer any separate keybinds for the mod itself, but this should allow for better
  out-of-the-box functionality without a reliance on any mods to resolve keybind conflicts! The updated controls are as
  follows:
    - "Jump" to ascend, fly... and jump! (default Space)
    - "Sneak" to descend and dive (default LShift)
    - "Sprint" to... sprint! But with STYLE! (default LCtrl)
    - "Throw Selected Pokemon" while sneaking to dismount (default LShift + R) if your Ride Pokemon is selected. Kinda
      like when you want to get a shoulder-mounted Pokemon off your shoulder, only YOU'RE the one on THEIR shoulder!
      Except that you also have to be sneaking. This is mainly to ensure that dismounting is a very deliberate action,
      while also freeing up the "Throw Selected Pokemon" key for other actions such as starting battles or interacting
      with other players while mounted. Note that you also cannot recall your Pokemon unless you are dismounted from it.
- Reduced the number of packets sent between client and server as a result of the changes above. Which SHOULD mean
  servers have less data to juggle, which should keep TPS healthy. Haven't had any evidence to suggest that this could
  be a potential issue, but it never hurts to take some preventative measures!
- Adjusted raytracing for Cobblemon such that it will ignore mounts when drawing a line between you and a target for
  certain interactions. This works for vanilla mounts as well! So now even the largest of Pokemon will not block your
  ability to start a battle!
- Adjusted Ride Pokemon interaction to now let you mount even while holding an item! This only applies to items that do
  not have some special effect on the Pokemon when used, such as potions or evolution items.
- Fixed an issue where server configs were not being correctly synced to connecting clients.
- Added new movement-type option for diving and flying, where you will move in the direction your camera is facing in,
  not unlike creative flight. Ascending and descending via key presses will still work, but this mode allows for
  vertical movement without needing to use them beyond initializing flying or diving.
- Dimension blacklisting! All dimensions are allowed by default, but if you wish to restrict riding in specific
  dimensions, just add their resource locations (e.g. "minecraft:the_nether") to the list to prevent riding in those
  dimensions!
- Added safety check to make sure you can't mount a Ride Pokemon that belongs to an NPC entity.
- Added checks to make sure that Ride Pokemon are not dismounted if swapped out in battle (unless they faint). Ride
  Pokemon will still be recalled, however, at the start and end of any level adjusted trainer battle. This is for safety
  reasons, so I have no plans of changing this.
- Added checks to make sure that Ride Pokemon cannot be evolved while mounted, nor can players mount Pokemon while they
  are evolving.
- Reduced default underwater modifier from 2.0 to 1.0. Underwater Pokemon were a bit TOO fast.
- Increased maximum limit for all speed-related modifiers from 5.0 to 100.0. Because some folks WANT to go too fast.
- Increased maximum limit for ride speed limit from 120.0 to 420.0. Because you'll be blazing at those speeds.
- Added option to toggle off sprinting on land, so that sprinting can be more selectively toggled for each medium.
- Set default state for sprinting in air to true. In hindsight, it's more reasonable to make this consistent across all
  mediums and allowing servers to decide if any of these need to be selectively disabled.
- Fixed Torterra's offset. Added Rhyhorn. More planned, just wanted to square these two away since they were on my mind.
- Added compatibility with Pet Your Cobblemon. Right-clicking to mount will work normally while the mod's interaction
  mode is disabled, and will be disabled while interaction mode is enabled.
- Minor adjustments throughout the code, to make things either more readable or a touch more standardized.

***

### v0.1.0 (Released January 7, 2025)

#### Initial release!

- Base feature set for mounting and dismounting, with initial support for 140 Ride Pokemon!
- Icons in the summary, PC and Pokedex screens mark which Pokemon can be ridden, while the Rideable subset in the
  Pokedex shows all available mounts!
- Movement speed that scales with Speed, with config options!
- Sprint with your Ride Pokemon, with config options!
- Toggleable shared water breathing and midair dismount!
- Global and individual modifiers for movement speed, whether in general or across specific mediums!
- Config for speed limiting, for server safety!
- Data registry and addon support for adding and updating Ride Pokemon!