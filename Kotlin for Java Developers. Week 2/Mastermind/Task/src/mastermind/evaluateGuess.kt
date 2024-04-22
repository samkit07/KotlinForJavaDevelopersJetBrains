package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rp = 0
    var wp = 0
    var total = 0
    val sec = sortStringToMutableList(secret)
    val gue = sortStringToMutableList(guess)
    val indexList = mutableListOf<Int>()
    for (i in 0 until sec.size){
        for (j in 0 until gue.size){
            if (sec[i] == gue[j] && j !in indexList){
                indexList.add(j)
                total+=1
                break
            }
        }
        if (secret[i] == guess[i]){
            rp+=1
        }
    }
    wp = total - rp
    return Evaluation(rp,wp)
}

fun sortStringToMutableList(input: String): MutableList<Char> {
    val sortedChars = input.toCharArray().sorted()
    return sortedChars.toMutableList()
}
