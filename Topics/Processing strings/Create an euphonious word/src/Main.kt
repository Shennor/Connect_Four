fun main() {
    // write your code here
    val word = readLine()!!
    val listOfVowels = listOf('a', 'e', 'i', 'o', 'u', 'y')
    var vowels = 0
    var consonants = 0
    var res = 0
    for(i in word){
        if (i in listOfVowels){
            consonants = 0
            vowels += 1
        }
        else{
            consonants += 1
            vowels = 0
        }
        if (consonants == 3){
            res += 1
            consonants = 1
        }
        if (vowels == 3){
            res += 1
            vowels = 1
        }
    }
    println(res)
}