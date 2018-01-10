# Lemur
Lemur is an android aplication for showing and visualizing the phones sensor data.
It was developed for the Mobile Aplications course at MDH.

<img src="https://i.imgur.com/tKR7MfX.png" height="500"><img src="https://i.imgur.com/mzuoJuO.png" height="500">

### Group Members
* André Caldegren 
* Anton Roslund
* Martin Bergman Törnkvist
* William Achrenius

# DVA232 Technical report
## Introduction
Smartphones come in a wide variety of models and all of them have different hardware compositions[1]. There are also many different types of operating systems for each phone excluding iOS and Android[2]. The process of choosing which platform to develop for can therefore be more difficult. We chose Android because we had more access to the platform which made debugging and general development much easier. A contributing factor was that Android has the biggest part of the global market, as such, the probability of working with the platform in the future is higher.

One of the more interesting things about smartphones is the amount of sensors that are available. Sensors like the accelerometer, GPS and the microphone give us data about the space around us. Smartphones can collect an intriguing amount of data and we wanted to make an app that took advantage of this. This in turn gives an experimental feeling and promotes curiosity. However this was not the first concept idea and only one of many.   
 
## Initial Concepts
Our first plan was to make an application for Volvos Construction Vehicles. The first idea we had were to make an application that kept track of where all of the vehicle tools are on the workplace. It would also keep track of where the vehicle operator should unload his load. We thought that this application would be useful for the operator, since if he was working on a big construction site, he would not necessarily always know where all of the tools are when he needs to change, or where the foreman wants him to put the next load.

Our second Volvo idea was an application both for the foreman and the vehicle operator. It would give the foreman the ability to direct his workers, give them orders on what to do and such. It would have looked somewhat like a top down, real time, computer strategy game. The vehicle operators would have seen their orders on a screen, and might also have had a map to direct them on where to go next.

The third Volvo idea was a communication application. It would give the vehicle operators the ability to communicate with each other and give them the ability to request things like dumper trucks.

After having tried the Volvo simulator and talked to the person that would help and guide us, we decided against doing any application for Volvo. We came to this decision for a few reasons. Firstly, it was a completely unknown, and quite old version of android. This would have restricted us to do most of our work in the lab. Secondly, being the first ones to develop for this platform, there were never any concrete documentation on how thing should be done. No one had established best practices yet. Thirdly, a project with Volvo would have required a lot of slow back and forth communication with them, since no one in the lab really knew how anything worked.

Social media is a really big part of the Smartphone marketplaces, because of that we had the idea to make our own social media application with a niche. Our idea was to have an application where you can upload posts, and only other users in the near vicinity, and around the same time every day, would be able to see those posts. We chose not to do this application because it felt to simple, and it was too much alike already existing apps.

We also explored the idea to game a game, while a lot of modern games have multiplayer support over the internet,  we wanted to make a game to get people together more like a traditional board game. We came up with what we called The Reaction Game. The game would require at least two persons in close proximity to each other. On each persons smartphone there would be a big button. The button on all players phones somehow signals that it can be pressed and the first person to press it wins. We thought that it would be fun exploring device comunucation and precise timing. 

The idea for Lemur came from us wanting to use the most amount of sensors possible in a single application. Our first plan were to include the accelerometer to measure g-force, the GPS to measure your height above the sea and your speed, the phones light sensor to measure the luminosity, microphone to measure the decibel, and lastly try to measure the room temperature. After some discussion, we made the decision to go ahead with this idea. We thought it would be fun to experiment with so many different sensor. 

Soon after starting out, we realized that it would simply not be possible to do that many sensor. Since we had no previous knowledge of the Android framework, we had to trim the amount of sensors down to the basics. We went with the accelerometer to measure acceleration instead of g forces, the GPS to measure the users height above sea level and your speed and lastly the microphone to measure decibel. 
 
## Design
To achieve a unified experience with other android applications we choose to use the Material design. Following the material design we came up with four design concepts. The we took the parts we liked from each concept and designed a proper paper prototype. We did a small scale test on some fellow students to get their input. 
  
Material design is Googles latest styling guidelines for applications on the Android marketplace. It provides a simple, paper like and playful, almost childish design for modern applications. We made the decision to follow these guidelines since they provide an in depth explanation on how to design every aspect of our app and make the app appealing to our users. There were documentation on everything from which color to use, margins and general sizing of items. This made the design process somewhat easier for us, since we are more programmers than we are application designers.

Each sensor page have a unique color, chosen from the material design color palette[3].
Sensor pages where design to show two cards, on the top a card with the current value of the sensor and below a graph displaying historical data. Once of the design concepts included max and min values for the sensors wich we came back to and implemented in a later state of development. 
The homepage where design to give the app an aesthetically pleasing landing page which allowed for easy navigation to each sensor. There where some early ideas to make the homepage more like a customizable dashboard but since the prototype only provides access to five sensor we decided that we could just link to each sensor. On the homepage there is a two column grid layout with cards for each sensor. Each card displays the tittle of the sensor and an descriptive icon.

## User Tests/Feedback
We got outside feedback at two stages of development. Firstly after we've completed the designed we get feedback on the design by fellow students. And the whiled we demoed our app at the exhibition we collected feedback from everyone who decided to test out app. 

After initial design feedback we decided to make the title of the cards the same height as the top navbar. We Also changed the color of the speedometer to not be too close to the color of the altimeter. Initially the accelerometer senor page had cards for each of the axis which required the user to scroll the get to the axis the wanted. We had to rethink the design decided to add buttons on the card title bar to change the axis.

## Technical 
After we had a general idea of the design we started with the layout xml files in android studio. The xml files are quite straight forward in android studio and the process went smooth. But there is where the smooth road ends and the rest of the coding was accompanied by almost constant struggles. 

We are forced to use at least API 21 since this is the first API that supports Material Design. With this API we will still reach about 40% of the devices. 

Problems with the sensors on different phone models made completion of the Altimeter and Speedometer difficult. Testing revealed an inconsistency of functioning behavior on most sensor pages. For example, the Decibel sensor works by measuring the power output of the microphone and converts it into decibel through a mathematical formula. Since phones might have different microphones installed the measure values can differ a great deal. We came to realize that it is hard to get an exact value over all different phones because of this problem. In essence, if you want exact values for every phone you would have to find out what hardware the phone is using and then change the formulas to match that exact hardware. There are even possibilities that microphone performance deteriorates with age [ResearchGate, Testing the accuracy of smartphones and sound level]. These are all possibilities that we considered but in the end couldn't do much about, it would require more resources and time than we had available for this project. 

Probably the most substantial problem that we had throughout the development was android permissions. The android documentation states that permissions are easy to use since you just have to include them in the android manifest file [developer.android.com]. But despite this, our development was haunted by a permission bug that we for a long time never managed to find the source for. Permissions would work one hour just to completely stop the next without any change in the code what so ever. We finally managed to find the bug towards the end of the project and the process progressed more effortless after that. 

We used Googles Cloud Vision API to classify images either from the phones gallery of from the camera. The process consisted of uploading an image from the phone to the Google Cloud, where Google ran its machine learnt software on the image to find out useful information about it. It would then return labels of what Google thought the image contained, safe search classifications and what colors the image consisted of. Our part of this process were to represent this data to the user in an elegant and easily understandable way. 

Overall the experience of programming in android studio was quite exhaustive. The methods used for very simple things could be overly complex. For example, accessing sensors sometimes required several inherited classes and functions that you had no way to know of yourself. It required heavy reading from the android development documentation. Thinks that were explained with hundreds of rows of code could be implemented with just a few if you knew how to do it yourself. It seemed like the way android studio works overcomplicated the coding. 

## Discussion
Choosing Google's Material Design made decisions regarding color and item layout easier. Less conflicting discussions made the design process progress faster. It also decreased the time in before coding could start.

As the design was mostly uniform throughout the development process, most things stayed as they were initially created. The accelerometer page was the only page to go through major changes, having all axis visible through scrolling at all times, to buttons relaying the different axis's as options. This change was decided upon quite late and turned out to be more interactive than the former. A decision this late could have been bad for the overall process as time was sparse. 

Active decisions were taken regarding noise reducing in each of the sensor pages. We wanted to display values closer to that of the raw data coming from the sensors. As a result the data became harder to read for users.
 
## Conclusion
Favoring raw data to a more regulated data output made the app look more unrefined, graphs helped refine it to an extent but not completely. Users were at a disadvantage trying to get a good read off of the values displayed.

### Future development
The app is intended to be an experimental tool for those who want to know a little about the data that exists around them. The app is also a prototype version built on a concept that can be expanded upon. Further development could include the ability to record and save information gathered by the app as well as a way to personally customize the layouts and menus to the users liking.
 
## References
[1] https://www.statista.com/statistics/271496/global-market-share-held-by-smartphone-vendors-since-4th-quarter-2009/, 2017-01-13
[2] https://www.statista.com/statistics/266219/global-smartphone-sales-since-1st-quarter-2009-by-operating-system/. 2017-01-13
[3] https://material.io/guidelines/style/color.html
[ResearchGate] https://www.researchgate.net/publication/289502743_Testing_the_accuracy_of_smartphones_and_sound_level_meter_applications_for_measuring_environmental_noise 2017-01-14 12:40
[developer.android.com] https://developer.android.com/guide/topics/manifest/manifest-intro.html#perms 2017-01-14 13:20

