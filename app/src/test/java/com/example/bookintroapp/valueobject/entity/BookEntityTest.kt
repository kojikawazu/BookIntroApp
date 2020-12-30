package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.form.BookAddForm
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import org.assertj.core.api.Assertions.*

class BookEntityTest{

    @Test
    fun NormalTest0(){

        // TODO エンティティテスト1(引数無し版)
        val testEntity = BookEntity()
        assertThat(testEntity.BookId).isBlank
        assertThat(testEntity.UserId).isBlank
        assertThat(testEntity.BookName).isBlank
        assertThat(testEntity.BookTitle).isBlank
        assertThat(testEntity.SatisCnt).isEqualTo(0)
        assertThat(testEntity.NiceCnt).isEqualTo(0)
        assertThat(testEntity.Comment).isBlank
        assertThat(testEntity.Created).isNotNull
    }

    @Test
    fun NormalTest1(){
        val date = Date()

        // TODO エンティティテスト1
        val testEntity = BookEntity("0", "1", "テスト書籍",
                "テストタイトル", 0, 0, 0, 0, "テストコメント", date)
        assertThat(testEntity.BookId).isEqualTo("0")
        assertThat(testEntity.UserId).isEqualTo("1")
        assertThat(testEntity.BookName).isEqualTo("テスト書籍")
        assertThat(testEntity.BookTitle).isEqualTo("テストタイトル")
        assertThat(testEntity.SatisCnt).isEqualTo(0)
        assertThat(testEntity.SatisCntDisplay).isEqualTo("0")
        assertThat(testEntity.NiceCnt).isEqualTo(0)
        assertThat(testEntity.NiceCntDisplay).isEqualTo("0")
        assertThat(testEntity.MarkCnt).isEqualTo(0)
        assertThat(testEntity.MarkCntDisplay).isEqualTo("0")
        assertThat(testEntity.ReplyCnt).isEqualTo(0)
        assertThat(testEntity.ReplyCntDisplay).isEqualTo("0")
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
        assertThat(testEntity.SatisCntDisplay).isEqualTo("1")
        assertThat(testEntity.NiceCntDisplay).isEqualTo("0")
        assertThat(testEntity.MarkCntDisplay).isEqualTo("0")
        assertThat(testEntity.ReplyCntDisplay).isEqualTo("0")
        assertThat(testEntity.Comment).isEqualTo("")
        assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NiceCntTest(){
        // TODO エンティティテスト(いいねカウンタインクリメント)
        val testEntity = BookEntity()
        testEntity.setNiceCnt(1)
        assertThat(testEntity.NiceCntDisplay).isEqualTo("1")
    }

    @Test
    fun MarkCntTest(){
        // TODO エンティティテスト(いいねカウンタインクリメント)
        val testEntity = BookEntity()
        testEntity.setMarkCnt(1)
        assertThat(testEntity.MarkCntDisplay).isEqualTo("1")
    }
}