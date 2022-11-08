object StringDemo {
    fun main() {
        val palindrome = "Dot saw I was Tod"
        val len: Int = palindrome.length
        val tempCharArray = CharArray(len)
        val charArray = CharArray(len)
        for (i in 0 until len) {
            tempCharArray[i] = palindrome[i]
        }
        for (j in 0 until len) {
            charArray[j] = tempCharArray[len - 1 - j]
        }
        val reversePalindrome = String(charArray)
        System.out.println(reversePalindrome)
    }
}