# Cobblemon: Ride On!

***

####

***

### Server Configuration

#### General

| Option                   | Description                                                                                                                                                                         | Default | Min | Max   |
|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|-----|-------|
| globalBaseSpeedModifier  | Multiplies the speed of all Pokemon across all mediums.                                                                                                                             | 1.0     | 0.0 | 100.0 |
| globalLandSpeedModifier  | Multiplies the speed of all Pokemon traveling on land.                                                                                                                              | 1.0     | 0.0 | 100.0 |
| globalWaterSpeedModifier | Multiplies the speed of all Pokemon traveling atop or through fluids.                                                                                                               | 1.0     | 0.0 | 100.0 |
| globalAirSpeedModifier   | Multiplies the speed of all Pokemon while flying.                                                                                                                                   | 1.0     | 0.0 | 100.0 |
| underwaterSpeedModifier  | Multiplies the speed of all Pokemon while diving. Stacks with globalWaterSpeedModifier.                                                                                             | 2.0     | 0.0 | 100.0 |
| waterVerticalClimbSpeed  | The rate at which Pokemon ascend and descend in water while diving.                                                                                                                 | 2.0     | 0.0 | 100.0 |
| airVerticalClimbSpeed    | The rate at which Pokemon ascend and descend while flying.                                                                                                                          | 0.5     | 0.0 | 100.0 |
| rideSpeedLimit           | The upper limit on how fast ride Pokemon are allowed to move, in m/s (blocks per second). Useful for servers, to restrict how quickly players can move around. Set to 0 to disable. | 0.0     | 0.0 | 420.0 |
| isWaterBreathingShared   | Enables whether a Pokemon that can breathe underwater shares its water breathing with its rider.                                                                                    | true    |     |       |

#### Client

| Option              | Description                                                                                                                                                                                                                            | Default |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| canDismountInMidair | Enables whether a rider can dismount from a Pokemon while flying. Leaving this false requires riders to land before they can dismount. Riders are advised to ride responsibly and safely if they enable this.                          | false   |
| useCameraNavigation | Enables users to move in the direction they are facing when flying or diving. Ascending and descending will work as normal during this, aligning it closer to creative flight. Leave false to maintain pure lateral movement for WASD. | false   |

#### Restrictions

| Option                | Description                                                                                                                                          | Default |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|---------|
| blacklistedDimensions | List of dimensions in which Ride Pokemon cannot be mounted. Dimensions should be listed as a resource location string (e.g. "minecraft:the_nether"). | []      |

#### Speed Stat

| Option           | Description                                                                    | Default | Min              | Max   |
|------------------|--------------------------------------------------------------------------------|---------|------------------|-------|
| affectsSpeed     | Enables whether the Speed stat of a Pokemon affects its ride speed.            | true    |                  |       |
| minStatThreshold | Any Speed stat below or equal to this value yields the minimum speed modifier. | 20      | 0                | 500   |
| maxStatThreshold | Any Speed stat above or equal to this value yields the maximum speed modifier. | 400     | minStatThreshold | 500   |
| minSpeedModifier | The speed multiplier at the minimum Speed stat threshold.                      | 0.5     | 0.0              | 100.0 |
| maxSpeedModifier | The speed multiplier at the maximum Speed stat threshold.                      | 4.0     | minSpeedModifier | 100.0 |

#### Sprinting

| Option             | Description                                                                        | Default | Min | Max   |
|--------------------|------------------------------------------------------------------------------------|---------|-----|-------|
| canSprint          | Enables whether ride Pokemon can sprint.                                           | true    |     |       |
| rideSprintSpeed    | The speed multiplier applied when a Pokemon is sprinting.                          | 1.5     | 1.0 | 100.0 |
| canSprintOnLand    | Enables whether ride Pokemon can sprint on land.                                   | true    |     |       |
| canSprintInWater   | Enables whether ride Pokemon can sprint in fluids.                                 | true    |     |       |
| canSprintInAir     | Enables whether ride Pokemon can sprint in air.                                    | true    |     |       |
| canExhaust         | Enables whether ride Pokemon can become exhausted if sprinting for too long.       | true    |     |       |
| maxStamina         | The time it takes to fully deplete stamina from full to zero, in ticks.            | 200     | 1   | 6000  |
| recoveryTime       | The time it takes to fully recover stamina from zero to full, in ticks.            | 300     | 1   | 6000  |
| recoveryDelay      | The time it takes for recovery to start after a Pokemon stops sprinting, in ticks. | 20      | 0   | 6000  |
| exhaustionSpeed    | The speed multiplier applied when a Pokemon is exhausted from sprinting.           | 0.5     | 0.0 | 1.0   |
| exhaustionDuration | The ratio of recovered stamina required to clear exhaustion.                       | 1.0     | 0.0 | 1.0   |

***

### Pokemon Configuration

#### Definition

| Property           | Description                                                                                                                    | Default     |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------|-------------|
| name               | The name of the Pokemon. Required in form objects to define the form to align the data to.                                     | "Normal"    |
| enabled            | Enables whether this Pokemon can be ridden.                                                                                    | true        |
| offsets            | A map of vectors, defining passenger attachment point offsets for the rider. See below for valid offset names.                 | { 0, 0, 0 } |
| shouldRiderSit     | Enables whether the rider should be sitting or standing/walking while riding.                                                  | true        |
| baseSpeedModifier  | Multiplies the speed of this Pokemon across all mediums.                                                                       | 1.0         |
| landSpeedModifier  | Multiplies the speed of this Pokemon traveling on land.                                                                        | 1.0         |
| waterSpeedModifier | Multiplies the speed of this Pokemon traveling atop or through fluids.                                                         | 1.0         |
| airSpeedModifier   | Multiplies the speed of this Pokemon while flying.                                                                             | 1.0         |
| forms              | Array of additional objects, each reflecting a different form or variant of the Pokemon. Uses all of the same fields as above. | []          |

#### Rider Offset Types

All offsets listed below are assumed to be offset from the DEFAULT. A Pokemon in a WALKING state, for example, will use
the DEFAULT offset plus the WALKING offset. All offsets are mutually exclusive unless otherwise specified; for instance,
a HOVERING offset would override a FLYING offset while a Pokemon is flying and idle, and a FLYING Pokemon that is moving
would not add both the FLYING and WALKING offsets to the DEFAULT.

| Type      | Description                                                                            | Fallback    |
|-----------|----------------------------------------------------------------------------------------|-------------|
| DEFAULT   | The default offset, sets the base atop which all other offsets are compounded.         | { 0, 0, 0 } |
| WALKING   | Used when the Pokemon is walking.                                                      | DEFAULT     |
| SWIMMING  | Used when a Pokemon is idle or moving on the surface of a fluid.                       | DEFAULT     |
| FLOATING  | Used when a Pokemon is idle on the surface of a fluid.                                 | SWIMMING    |
| DIVING    | Used when a Pokemon is idle or moving while submerged in fluid.                        | DEFAULT     |
| SUSPENDED | Used when a Pokemon is idle while submerged in fluid.                                  | SUSPENDED   |
| FLYING    | Used when a Pokemon is idle or moving while flying.                                    | DEFAULT     |
| HOVERING  | Used when a Pokemon is idle while flying.                                              | HOVERING    |
| SHEARED   | Used for Pokemon which are shearable and have been sheared. Stacks with other offsets. | DEFAULT     |

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to
structure it however you want - this is just a starting point!). Thanks
to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README

Every project is different, so consider which of these sections apply to yours. The sections used in the template are
suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long
is better than too short. If you think your README is too long, consider utilizing another form of documentation rather
than cutting out information.

## Name

Choose a self-explaining name for your project.

## Description

Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be
unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your
project, this is a good place to list differentiating factors.

## Badges

On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the
project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals

Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see
GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation

Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew.
However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing
specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a
specific context like a particular programming language version or operating system or has dependencies that have to be
installed manually, also add a Requirements subsection.

## Usage

Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of
usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably
include in the README.

## Support

Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address,
etc.

## Roadmap

If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing

State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started.
Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps
explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce
the likelihood that the changes inadvertently break something. Having instructions for running tests is especially
helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment

Show your appreciation to those who have contributed to the project.

## License

For open source projects, say how it is licensed.

## Project status

If you have run out of energy or time for your project, put a note at the top of the README saying that development has
slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or
owner, allowing your project to keep going. You can also make an explicit request for maintainers.
