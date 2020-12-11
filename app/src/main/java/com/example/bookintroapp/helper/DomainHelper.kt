package com.example.bookintroapp.helper

class DomainHelper {

    init{

    }

    // TODO static
    companion object{

        fun IsRange(num : Int, max : Int) : Boolean{
            // TODO 範囲内マッチ
            return ( num > 0 && num <= max )
        }

        fun IsEmailMatch(emailString : String) : Boolean{
            // TODO メールアドレス形式パターンマッチ
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"
            val regax = Regex(pattern = emailPattern)
            return regax.matches(input = emailString)
        }

        fun IsSame(word1: String, word2: String) : Boolean{
            // TODO 一致マッチ
            return word1.equals(word2)
        }

    }
}