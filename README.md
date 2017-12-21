# EKALGO
EKAL GO - the High School Version of a self-learning Go playing engine
<br>

<h2>Concept: </h2>
<h3> Step 0: Prerequisite Information </h3>
The computer will have perfect information of the rules of the game and the scoring required for a win. Other than that, EKAL begins with no information about the game whatsoever, and needs to use regression (this is to speed up the process given our limited computing power, but hopefully it will not need to in the future) and its own playouts against itself to find the optimal strategies in this ancient game. 

<h3> Step 1: Creating a Prediction Network </h3>
The code will make use of one (or maybe two, depending on its success) neural network and a Monte Carlo Tree Search Array. The first step of the code is to read the multiple games, and pair each state of the board up with the action (move) played by the professional. This prediction network should not consider the winner of the game, since the evaluation of that should only come in during the second step of Reinforcement learning.

<br>There are two possible ways to scale this prediction network. <br><br>The first method is to treat it as also a value network for the probability of a win. By setting all the moves played by the professional are automatically given the number of 0.5, we assume that every move that is played by a professional is equally valid in the first place, and the strength of it can be evaluated during the second step of reinforcement learning.
<br><br>The second method is to treat more popular moves as stronger ones, and thus should be the moves that are searched more. (i.e. if a move is played 4 times in 5 games, it gets a score of 0.8). While this is a valid method, this creates too large of an imbalance for the reinforcement learning to overcome. That being said, it is possible to test both these scales and their success. 

To sum it up, the neural network outputs a probability board for each state, narrowing down all the possible moves to a few top ones that it thinks a professional would play in that position. Similar positional features will be rotated in order to find a consistent regression pattern.

Since multiple trained neural networks with the exact same data still may not obtain the exact same values for the probability boards (i.e. 0.48 vs 0.52), we will pair these different prediction networks against each other for a clash of the still dumb titans.

<h4> Board Representation </h4>
For the features, we have a straight up 19x19 board representation of the board, as well as the colour (black/white) to move at that position. Possible convolutions and kernels can be used to represent the data.
<br><br>
Given my status as a patzer, a poor Go player with only around 5 games of experience, I have a very limited knowledge of the game regardless. 

<h3> Step 1A: Monte Carlo Tree Search Creation </h3>
While the information and data set is created for the prediction network, a Monte Carlo Tree Search is created, where we use a formula that factors in the popularity and success rate of each move in the data set to give each move a value. These values will be used to evaluate the position and choose the next move whenever a state is recognized in actual competitions, but not during training (we want the computer to tweak its prediction network whenever possible). An Alpha Beta search can be used, and we can test the success rate of it, but my instincts tell me that this only slows down the engine without significant advantage.

Once our neural networks can successfully predict the professional moves pass a certain threshold, all the playouts by that engine will update this MCTS tree.

<h3> Step 2: Reinforcement Learning </h3>
This is the most interesting part of the project of EKAL, and is very important for the improvement of the prediction network to determine the best moves. We set a round-robin tournament of separately trained neural networks with the same data, and allow them to compete against each other. They should play exact replicas of the professional games at first. 

<br><br> With a certain alpha value (perhaps 0.005 based off intuition), we add the alpha value to the moves that eventually result in a win, and subtract an alpha value to the moves that eventually result in a loss. Each new move played in a position gets a value of 0.5 in the dataset, and will be subjected to the same alpha value change.

<br><br> To create a variance of different games, random openings (i.e. we create 10 random initial moves for both sides) will be created. The computer will then officially be in uncharted territory and will have to use the regression model of professional moves to try and choose the best possible moves in those areas. 

<br><br> Similarly, the computer can make random moves in the middle of a game for both sides, and then get the networks to play on from those positions. Only the moves after the random choices will have their ratings tweaked.





<br>
<br>
<h2> Timeline: </h2>
<b> Day 1 - Dec 20 2017 </b> <br>
Created idea, Main.java created along with the main Artificial Neural Network and ugly mxjava functions. Break begins today, will have roughly two weeks to create this. Considering using a different language...
