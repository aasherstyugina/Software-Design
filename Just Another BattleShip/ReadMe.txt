At the start of the application the you have to define the size of the Ocean (number of rows and columns) and the ships number (0 or above) for each ship type.
This data is seven integer non-negative values (two for the Ocean size and five for ships number).

!!! Rows and columns numbering starts at 0 !!!

If you use command line arguments, your should enter data in one line, all arguments separated by space:
<numOfRows> <numOfCols> <numOfCarriers> <numOfBattleships> <numOfCruisers> <numOfDestroyers> <numOfSubmarines>
For example:
4 5 0 1 0 0 2
means that Ocean has 4 rows and 5 columns, fleet consists of 1 battleship and 2 submarines.

If you use console, each number is entered on a separate line. You will see promts during the entering.

Rules:
At each game turn the you have to perform a shot to an Ocean cell and specify the row and column number of it.

You should enter shot like two numbers separated by space:
<0..rowNum - 1> <0..colNum - 1>
For example:
3 2
means shot to an Ocean cell with row 3 and column 2.

After each shot, the computer redisplays the current view of the Ocean and responds with
a single bit of information: "hit" or "miss".
A ship is "sunk" when all the cells it occupies are hit.

The representation of an Ocean cell for a human player is one of the following:
not-fired is "0"
fired-miss is "-"
fired-hit is "+"
sunk is "*"

Your goal is to sink all the Fleet with as few shots as possible.

Also you can activate two modes:

1. Torpedo firing mode
When a ship is hit with a torpedo, it becomes sunk entirely, with no respect to its state and size.

You should enter shot like english T and two numbers, all arguments separated by space:
<T> <0..rowNum - 1> <0..colNum - 1>
For example:
T 3 2
means a torpedo shot to an Ocean cell with row 3 and column 2.

2. Ship recovery mode
When this mode is enabled and the user hits a ship, which is not sunk yet, the user has to hit the same
ship with the next shot. Otherwise, the ship gets recovered to its initial state (that it had before the
first hit in it). It means that to sink a ship the user has to hit it by every shot starting from the first
hit and until the ship is sunk; otherwise the user needs to repeat the attack from the very beginning.