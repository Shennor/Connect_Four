package connectfour


enum class CellState(val symbol: Char){
    FIRST('o'),
    SECOND('*'),
    NONE(' ')
}

var numBoardColumns = 7 // [5-9]
var numBoardRows = 6    // [5-9]
var firstPlayerName : String = "Player 1"
var secondPlayerName : String = "Player 2"
var board = (emptyList<MutableList<CellState>>()).toMutableList()
var numGames = 1
var score = Pair(0, 0)

var isFirstPlayerTurn = true

fun printBoard(){
    for(i in 1..numBoardColumns){
        print(" $i")
    }
    println(" ")
    for(i in 0 until numBoardRows){
        for(j in 0 until numBoardColumns){
            print("║${board[j][i].symbol}")
        }
        println("║")
    }
    print("╚═")
    for(i in 1 until numBoardColumns){
        print("╩═")
    }
    println("╝")
}

fun inputGameParameters(){
    println("Connect Four")
    println("First player's name:")
    var playerNameInput = readLine()!!
    if(playerNameInput != "") firstPlayerName = playerNameInput
    println("Second player's name:")
    playerNameInput = readLine()!!
    if(playerNameInput != "") secondPlayerName = playerNameInput
    var success = false
    while(!success) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        var boardSizeInput : String = readLine()!!
        boardSizeInput = boardSizeInput.replace("\\s".toRegex(), "")
        if (boardSizeInput == ""){
            success = true
        }
        else{
            if (boardSizeInput.matches("\\d+[xX]\\d+".toRegex())) {
                val nums = boardSizeInput.split("[xX]".toRegex(), 2).map{ it.toInt() }
                numBoardRows = nums[0]
                numBoardColumns = nums[1]
                if (numBoardRows !in 5..9) {
                    println("Board rows should be from 5 to 9")
                    success = false
                } else if (numBoardColumns !in 5..9) {
                    println("Board columns should be from 5 to 9")
                    success = false
                } else {
                    success = true
                }
            } else {
                println("Invalid input")
                success = false
            }
        }
    }
    success = false
    while(!success){
        println("Do you want to play single or multiple games?")
        println("For a single game, input 1 or press Enter")
        println("Input a number of games:")
        var numGamesInput = readLine()!!
        numGamesInput = numGamesInput.replace("\\s".toRegex(), "")
        if(numGamesInput == ""){
            success = true
        }
        else if(numGamesInput.contains("\\D".toRegex()) || numGamesInput.toInt() <= 0){
            println("Invalid input")
            success = false
        }
        else {
            numGames = numGamesInput.toInt()
            success = true
        }
    }
    println("$firstPlayerName VS $secondPlayerName")
    println("$numBoardRows X $numBoardColumns board")
    if(numGames == 1) println("Single game")
    else println("Total $numGames games")
}

fun createEmptyBoard(){
    board = (emptyList<MutableList<CellState>>()).toMutableList()
    for(i in 0 until numBoardColumns){
        board.add((emptyList<CellState>()).toMutableList())
        for(j in 0 until numBoardRows){
            board[i].add(CellState.NONE)
        }
    }
}

fun addValue(column : Int) : Boolean{
    for(i in (numBoardRows - 1) downTo 0){
        if(board[column - 1][i] == CellState.NONE) {
            board[column - 1][i] = if (isFirstPlayerTurn) CellState.FIRST
            else CellState.SECOND
            return true
        }
    }
    return false
}

fun turn() : Boolean{
    var success = false
    while(!success) {
        if (isFirstPlayerTurn) println("$firstPlayerName's turn:")
        else println("$secondPlayerName's turn:")
        val turnInput = readLine()!!
        if(turnInput == "end"){
            return false
        }
        else{
            if(turnInput.contains("\\D".toRegex())){
                println("Incorrect column number")
            }
            else {
                val chosenColumn = turnInput.toInt()
                if(chosenColumn !in 1..numBoardColumns){
                    println("The column number is out of range (1 - $numBoardColumns)")
                }
                else {
                    if(!addValue(chosenColumn)) {
                        println("Column $chosenColumn is full")
                    }
                    else{
                        printBoard()
                        success = true
                        isFirstPlayerTurn = !isFirstPlayerTurn
                    }
                }
            }
        }
    }
    return true
}

fun boardIsFull() : Boolean{
    var fullness = true
    for(i in 0 until numBoardColumns){
        if(board[i].contains(CellState.NONE))
            fullness = false
    }
    return fullness
}

fun found4(s : Char) : Boolean{
    var cnt = 0
    // in columns
    for(i in 0 until numBoardColumns){
        cnt = 0
        for(j in 0 until numBoardRows){
            if(board[i][j].symbol == s) cnt++
            else cnt = 0
            if(cnt == 4) return true
        }
    }
    // in rows
    for(j in 0 until numBoardRows){
        cnt = 0
        for(i in 0 until numBoardColumns){
            if(board[i][j].symbol == s) cnt++
            else cnt = 0
            if(cnt == 4) return true
        }
    }
    // first type diagonally \
    for(i in 0 until numBoardColumns){
        var delta = 0
        cnt = 0
        while(i + delta < numBoardColumns && delta < numBoardRows){
            if(board[i + delta][delta].symbol == s) cnt++
            else cnt = 0
            if(cnt == 4) return true
            delta++
        }
    }
    for(j in 0 until numBoardRows){
        var delta = 0
        cnt = 0
        while(delta < numBoardColumns && j + delta < numBoardRows){
            if(board[delta][j + delta].symbol == s) cnt++
            else cnt = 0
            if(cnt == 4) return true
            delta++
        }
    }
    //second type diagonally /
    for(i in numBoardColumns - 1 downTo 0){
        var delta = 0
        cnt = 0
        while(i - delta >= 0 && delta < numBoardRows){
            if(board[i - delta][delta].symbol == s) cnt++
            else cnt = 0
            if(cnt == 4) return true
            delta++
        }
    }
    for(j in 0 until numBoardRows){
        var delta = 0
        cnt = 0
        while(numBoardColumns - 1 - delta >= 0 && j + delta < numBoardRows){
            if(board[numBoardColumns - 1 - delta][j + delta].symbol == s) cnt++
            else cnt = 0
            if(cnt == 4) return true
            delta++
        }
    }
    return false
}


fun main() {
    inputGameParameters()
    isFirstPlayerTurn = true
    for(i in 1..numGames) {
        if(numGames != 1){
            println("Game #$i")
        }
        createEmptyBoard()
        printBoard()
        while (turn()) {
            when {
                found4(CellState.FIRST.symbol) -> {
                    println("Player $firstPlayerName won")
                    score = Pair(score.first + 2, score.second)
                    break
                }
                found4(CellState.SECOND.symbol) -> {
                    println("Player $secondPlayerName won")
                    score = Pair(score.first, score.second + 2)
                    break
                }
                boardIsFull() -> {
                    println("It is a draw")
                    score = Pair(score.first + 1, score.second + 1)
                    break
                }
                else -> {}
            }
        }
        if(numGames != 1){
            println("Score")
            println("$firstPlayerName: ${score.first} $secondPlayerName: ${score.second}")
        }
        isFirstPlayerTurn = (i % 2 == 0)
    }
    println("Game over!")

}