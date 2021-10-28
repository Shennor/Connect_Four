fun main() {
    val list = readLine()!!.split(" ")
    var max = 0
    var index = 0
    for(i in list.indices){
        if(list[i].length > max){
            max = list[i].length
            index = i
        }
    }
    println(list[index])
}
