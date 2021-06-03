# Anti-aircraft Gunner

_**A simple OpenGL 2d game written in Java where you control a vehicle that shoots projectiles against enemy planes that try to destroy you and the city buildings 😯**_

![Game Screenshot](/docs/sample.png)

# Prerequisites 🔨

* Java 11 or newer
* Windows, MacOS or Linux OS

# Usage 💻

The `.jar` and `.exe` files can be downloaded directly from the _Releases section_ ![right here](https://github.com/sarah-lacerda/anti-aircraft-gunner/releases).
####
#### If you would like to clone this repository, play around and recompile it:
This is a Gradle project, so by just running `./gradlew build` should get you all set and have everything compiled without needing to take care of any dependencies.

# How to play 🎮

<img src="/docs/sampleAnimation.gif" width="450"/> <img src="/docs/gameOver.gif" width="500"/>


| Action  | Description                                                                                                         |
| ------- | ------------------------------------------------------------------------------------------------------------------- |
|<img src="/docs/move.png" width="450"/>|Use the `LEFT` and `RIGHT` arrow keys to move the horizontally player across the screen:|
| ![Player rotation](/docs/playerRotation.png)  | Use the `UP` and `DOWN` arrow keys to rotate the player around (-80 to +80 degrees) |
| <img src="/docs/shiftKey.png" width="150"/> <img src="/docs/equals.png" width="50"/> <img src="/docs/loadAnimation.gif" width="150"/> | Hold the `SHIFT` key to charge the rocket launcher |
| <img src="/docs/shiftPlusSpace.png" width="250"/> <img src="/docs/equals.png" width="30"/> <img src="/docs/shootAnimation.gif" width="100"/> | Press `SPACEBAR` while still holding `SHIFT` to shoot a projectile |

### NOTES:

* The longer you hold the `SHIFT` key the more powerful your projectile will be when you shoot.
Unfortunately, since excess of power leads to corruption, there's a limit for how powerful your shot can be (yeah I know 😥).  
You will see when your launcher has already reached its full capacity when the arrow turns red, like the image below.

<img src="/docs/fullPower.png" width="80"/>

* There's also a minimum power that your launcher has to have in order for you to be able to shoot, so you can't just hit `SPACE` and shoot.
* And yeah, you have unlimited projectiles at your disposal. Just be careful about crashing your computer by spawning too many of them (remember, don't abuse power! 😉)


# Authors 👩‍💻

* ![Sarah Lacerda](https://github.com/sarah-lacerda)

* ![Julia Maia](https://github.com/juAlberti)


This game was made as part of a computer science college assignment related to the subject of Computer Graphics.
