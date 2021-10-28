fun main() {
    // write your code here
    val s : String = readLine()!!
    var sum1 = 0
    for(i in 0 until (s.length / 2)){
        sum1 += s[i].toInt()
    }
    var sum2 = 0
    for(i in (s.length / 2) until s.length){
        sum2 += s[i].toInt()
    }
    if(sum1 == sum2){
        println("YES")
    }
    else println("NO")
}