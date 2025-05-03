### Templates for Adding New Ride Pokemon

This folder contains examples as templates for creating datapacks for this mod! All you need to do is:

1. Duplicate the folder for the datapack template you want.
2. Make your changes to the existing data files or add your own.
3. Zip the contents.
4. Drop it into the "datapacks" folder of your world.
5. Test out your new additions!

If you wish to add Pokedex support, you will also need to overwrite the existing dex file for this mod.

1. Create your entries under "data/<packidhere>/dex_entries". Use whatever you want for "packidhere", they're YOUR
   entries! Examples of entries can be found over
   in [the Cobblemon repo](https://gitlab.com/cable-mc/cobblemon/-/tree/main/common/src/main/resources/data/cobblemon/dex_entries)!
2. Add "data/cobbleride/dexes/rideable.json" to your datapack.
3. Copy over the existing dex
   from [here](https://gitlab.com/StarliteHeart/cobbleride/-/blob/main/common/src/main/resources/data/cobbleride/dexes/rideable.json).
4. Add your entries to the rideable dex using whatever "id" you set for them! Order matters, it determines where they
   appear in the actual dex!
5. Finish your datapack, zip and add it, and check if your entries appear in the dex!

If you wish to update or change existing Ride Pokemon, be sure to create an updated file under "
data/cobbleride/rideable_species" with the same name as the original. If you wish to remove support for a Pokemon, it's
recommended that you update the file with "enabled" set to false.

Also, whatever entities you wish to add using datapacks should ideally be in the Cobblemon data registries already, if
you aren't adding them yourself! Make sure that what you're adding **does** exist!