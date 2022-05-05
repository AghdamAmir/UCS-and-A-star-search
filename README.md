# UCS and A* search

This repository contains the java implementation of two famous search algorithms called Uniform Cost Search(UCS) and A*, used for optimal routing problem on the map of Romania.

## Algorithms

* **Uniform Cost Search (UCS)**

In this algorithm from the starting state we will visit the adjacent states and will choose the least costly state then we will choose the next least costly state from the all un-visited and adjacent states of the visited states, in this way we will try to reach the goal state, even if we reach the goal state we will continue searching for other possible paths. 

We will keep a **priority queue** which will give the least costliest next state from all the adjacent states of visited states.

![UCS](https://raw.githubusercontent.com/AghdamAmir/UCS-and-A-star-search/main/ucs-gif.gif)


* **A star Search**

A* Search algorithm is one of the best and popular technique used in path-finding and graph traversals. 

The algorithm is roughly the same as the UCS except for the fact that it also **utilizes problem-specific heuristics** in node expansion which results in much faster convergence.

## Problem

The problem on which the algorithms are applied is finding the optimal path between two cities on the map of Romania.

Moreover, **Straight-line heuristic** function has been employed for fast convergence in A* search.

![Romania Map](https://raw.githubusercontent.com/AghdamAmir/UCS-and-A-star-search/main/romania-map.png)
