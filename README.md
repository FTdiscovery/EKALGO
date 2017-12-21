# EKALGO
EKAL GO - the High School Version of a self-learning Go playing engine
<br>

<h2>Concept: </h2>
<h3> Step 0: Prerequisite Information </h3>
The computer will have perfect information of the rules of the game and the scoring required for a win. Other than that, EKAL begins with no information about the game whatsoever, and needs to use regression (this is to speed up the process given our limited computing power, but hopefully it will not need to in the future) and its own playouts against itself to find the optimal strategies in this ancient game. 

<h3> Step 1: Creating a Prediction Network </h3>
The code will make use of one (or maybe two, depending on its success) neural network and a Monte Carlo Tree Search Array. The first step of the code is to read the multiple games, and pair each state of the board up with the action (move) played by the professional. This prediction network should not consider the winner of the game, since the evaluation of that should only come in during the second step of Reinforcement learning.

<br>There are two possible ways to scale this prediction network. <br><br>The first method is to treat it as also a value network for the probability of a win. By setting all the moves played by the professional are automatically given the number of 0.5, we assume that every move that is played by a professional is equally valid in the first place, and the strength of it can be evaluated during the second step of reinforcement learning.
<br><br>The second method is to treat more popular moves as stronger ones, and thus should be the moves that are searched more. (i.e. if a move is played 4 times in 5 games, it gets a score of 0.8). While this is a valid method, this creates too large of an imbalance for the reinforcement learning to overcome; that being said, it is possible to test both these scales and their success.


<br>
<br>
<h2> Timeline: </h2>
<b> Day 1 - Dec 20 2017 </b> <br>
Created idea, Main.java created along with the main Artificial Neural Network and ugly mxjava functions. Break begins today, will have roughly two weeks to create this. Considering using a different language...
