package nicestring

fun String.isNice(): Boolean {




    val checkCondition1=!(this.contains("ba")||this.contains("bu")||this.contains("be"))
    val checkCondition3 = this.count { it in setOf('a', 'e', 'i', 'o', 'u') } >= 3

    var checkCondition2=false
    for (i in 'a'..'z')
    {
        if (this.contains(i.toString()+i.toString()))
        {
            checkCondition2=true
            break
        }
    }


    return (checkCondition1 && checkCondition2) || (checkCondition1 && checkCondition3) || (checkCondition2 && checkCondition3)

}