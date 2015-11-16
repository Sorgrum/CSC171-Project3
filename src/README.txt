README
2015-11-16

During the development of this project, I took some time to think about the best way to draw the actual trajectory of the object. I decided to use ratio's that would fit the screen size, and multiply every number by the ratio. I find the ratio by dividing by 1.3 and checking if the largest numbers in the X and Y direction will fit if multiplied by that ratio.
I also decided to stop drawing the trajectory of the firework once it has blown up because it made the most sense that once a firework has exploded, it won't keep on traveling.
For the controls, I decided to use text fields for the launch speed and time. The reason for this is that I wanted the user to be able to enter any speed or time (within reason) and because I didn't want to limit precision.
I used some cool regex to strip the numbers and reformat the field after each change.
For the launch angle I stuck a slider because I wanted to limit the angles that the projectile could be launched at. For example, negative angles wouldn't work well as well as 0 or 90 degrees.
For the color and explosion type I included combo boxes because I also wanted to limit the choices and I felt that it made more sense from a UX standpoint.
I also included a label that shows how long it would take for the projectile to hit the floor because I felt that it was kind of unintuitive to just guess and check times to see if they fit in the projectiles timing.
The "Update Graph" button is completely unnecessary, and I only added it because it was required. I wanted my program to have a responsive, AJAX-like feel to it.
I used a menu bar with it's only function of resetting the fields just because I wanted to experiment with menu bars (extra credit?)
I included some "stars" among the "night" sky. I had these update every 2.5 seconds for a pretty cool effect.