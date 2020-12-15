package com.example.bookintroapp.valueobject.entity

import org.junit.Assert.*
import org.junit.Test
import org.assertj.core.api.Assertions.*
import java.util.*

class MarkEntityTest{

    @Test
    fun NormalTest0(){
        // TODO コンストラクタテスト0
        val markEntity = MarkEntity()
        assertThat(markEntity.MarkId).isBlank()
        assertThat(markEntity.UserId).isBlank()
        assertThat(markEntity.BookId).isBlank()
        assertThat(markEntity.Created).isNotNull()
    }

    @Test
    fun NormalTest1(){
        val date: Date = Date()

        // TODO コンストラクタテスト1
        val markEntity = MarkEntity("0", "1", "2", date)
        assertThat(markEntity.MarkId).isEqualTo("0")
        assertThat(markEntity.UserId).isEqualTo("1")
        assertThat(markEntity.BookId).isEqualTo("2")
        assertThat(markEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

}