
# Chess application for COSC360 - Jarrod Baker

This application was developed for COSC360. This readme is relevant for the first
submission step focusing on backend development. 

The app will work by a user creating an account. They will then have the option of 
either creating a new game or continuing a previous, unfinished game. The
new game option will take them to an options page where they can select
their settings and invite a friend to play. 

If the friend is registered they will get an invitation linking them to the game. If
the friend isn't registered the app will ask them to be signed up before joining
a game. 

The game will be rendered on the front end, with the back end providing support to say 
how pieces can move, keeping track of the game, etc. The two-player version will
communicate over a web socket. Each move will be sent to the server, with a revised 
game state sent to the client. I considered writing the game in JavaScript, but 
decided against it as this way if the connection is interrupted the game data is 
preserved.
 
Currently users can register, and create a new game instance in the database. Most of
the backend is in place for the logic of the game: how the peices move
and whatnot. The early front end has been put together using bootstrap.

Moving forward: 
I need to complete the frontend interface to the game, improve the visuals of the
application, implement the web socket, and finish up the game logic to make sure
that all the moves are legal, etc. I also need to finish implementing returning
a database instance of a game to an in memory version.

I plan to use React and TypeScript for the client-side. 

##challenges so far
I have spent the most time trying to get the database working correctly.
This is an ongoing struggle. 

I expect the visuals will also prove challenging to implement as I do not 
have any prior experience in this area. 

## The plumbing:

## Running

Run this using [sbt](http://www.scala-sbt.org/). 
```
sbt run
```

And then go to http://localhost:9000 to see the running web application.

There must be an instance of mongod available to connect to. So far this
connection is hard-coded and not error checked, so please be careful.

## Controllers

- HomeController.scala:

  Handles the landing page
  
- UserController.scala:
  User sign-in, registration, etc.
  
- AdminController.scala:
  Creates new game instances
  
- GameController.scala:
    Will be used to run the game itself


## Components

- User API
Functions to create and update users

- Game API
Create and save games
Make changes to games: add moves, etc.

- Pieces API
Each piece (King, Queen, ...) is implemented as a class which extends 
the ChessPiece trait. Every piece will have a color and location, as well as a method
to move. 

- Notation API
Can be given a piece and a move and return standard chess notation.


