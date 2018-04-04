# SecondConscience
## Inspiration
With the advent and accessibility of technology, kids are joining social media networks at increasingly younger ages. According to the i-SAFE foundation, around half of the adolescents on these sites are victims of cyber bullying. Cyber bullying can have many negative effects on children, including low self esteem and the feeling of isolation. This software is designed to be a deterrent for the young, potential bully, who may try to attack another child anonymously. It provides a second chance for the potential bully to decide to retract a message with cyber bullying sentiment before it is posted, acting like a second conscience. 
## What it does
We decided to create software that can be integrated with current social media platforms to help with the current cyber bullying issue. The software will ask a user if they are sure they want to post a message if the message sentiment correlates with common cyber bullying statements. The ultimate intention is for the software to also notify a user when he/she is tagged in a post that contains cyber bullying sentiment and immediately prompts him/her to either report the message/user or ignore the message. This will not only protect the victim of a cyber bullying attack by providing an immediate line of defense, but also acts as a second conscience for the bully and give them the opportunity to reevaluate their actions.
## How we built it
As a proof of concept, we built an Android app in Android Studio that displays a text field where a message is inputted and submitted for analysis. The message is analyzed for cyber bullying sentiment using Microsoft's Azure Text Analytics API in Cognitive Services and Microsoft's Language Understanding Intelligent Service (LUIS) API.
## To Try
To try, please unzip the SecondConscienceFINAL.zip, and open the project in Android Studio. Running the project will allow you to run the app and demo the software. 
