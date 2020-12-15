package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.BookAddForm
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import org.assertj.core.api.Assertions.*

class BookEntityTest{

    @Test
    fun NormalTest0(){

        // TODO エンティティテスト1(引数無し版)
        val testEntity = BookEntity()
        assertThat(testEntity.BookId).isBlank()
        assertThat(testEntity.UserId).isBlank()
        assertThat(testEntity.BookName).isBlank()
        assertThat(testEntity.BookTitle).isBlank()
        assertThat(testEntity.SatisCnt).isEqualTo(0)
        assertThat(testEntity.NiceCnt).isEqualTo(0)
        assertThat(testEntity.Comment).isBlank()
        assertThat(testEntity.Created).isNotNull()
    }

    @Test
    fun NormalTest1(){
        val date: Date = Date()

        // TODO エンティティテスト1
        val testEntity = BookEntity("0", "1", "テスト書籍",
                "テストタイトル", 0, 0, "テストコメント", date)
        assertThat(testEntity.BookId).isEqualTo("0")
        assertThat(testEntity.UserId).isEqualTo("1")
        assertThat(testEntity.BookName).isEqualTo("テスト書籍")
        assertThat(testEntity.BookTitle).isEqualTo("テストタイトル")
        assertThat(testEntity.SatisCntDisplay).isEqualTo("0")
        assertThat(testEntity.NiceCntDisplay).isEqualTo("0")
        assertThat(testEntity.Comment).isEqualTo("テストコメント")
        assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NormalTest2(){
        val date: Date = Date()
        val userEntity = UserEntity()
        val bookAddForm = BookAddForm()

        // TODO エンティティテスト2
        val testEntity = BookEntity(userEntity, bookAddForm, date)
        assertThat(testEntity.BookId).isEqualTo("0")
        assertThat(testEntity.UserId).isEqualTo("")
        assertThat(testEntity.BookName).isEqualTo("")
        assertThat(testEntity.BookTitle).isEqualTo("")
        assertThat(testEntity.SatisCntDisplay).isEqualTo("0")
        assertThat(testEntity.NiceCntDisplay).isEqualTo("0")
        assertThat(testEntity.Comment).isEqualTo("")
        assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun increment_NiceCntTest(){
        // TODO エンティティテスト(いいねカウンタインクリメント)
        val testEntity = BookEntity()
        testEntity.plus_niceCnt()
        assertThat(testEntity.NiceCntDisplay).isEqualTo("1")
    }
}