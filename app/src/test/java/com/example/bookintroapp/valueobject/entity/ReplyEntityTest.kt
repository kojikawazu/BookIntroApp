package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.form.BookReplyForm
import org.assertj.core.api.Assertions
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class ReplyEntityTest{

    @Test
    fun NormalTest0() {
        // TODO エンティティテスト1(引数無し版)
        val testEntity = ReplyEntity()
        Assertions.assertThat(testEntity.ReplyId).isBlank
        Assertions.assertThat(testEntity.UserId).isBlank
        Assertions.assertThat(testEntity.BookId).isBlank
        Assertions.assertThat(testEntity.Comment).isBlank
        Assertions.assertThat(testEntity.NiceCnt).isEqualTo(0)
        Assertions.assertThat(testEntity.NiceCntDisplay).isEqualTo("0")
        Assertions.assertThat(testEntity.Satis).isEqualTo(0)
        Assertions.assertThat(testEntity.SatisDisplay).isEqualTo("0")
        Assertions.assertThat(testEntity.Created).isNotNull
    }

    @Test
    fun NormalTest1() {
        // TODO エンティティテスト1(引数有り版)
        val date = Date()
        val testEntity = ReplyEntity("1", "2", "3",
                            "テストコメント", 1, 1, date)
        Assertions.assertThat(testEntity.ReplyId).isEqualTo("1")
        Assertions.assertThat(testEntity.UserId).isEqualTo("2")
        Assertions.assertThat(testEntity.BookId).isEqualTo("3")
        Assertions.assertThat(testEntity.Comment).isEqualTo("テストコメント")
        Assertions.assertThat(testEntity.NiceCnt).isEqualTo(1)
        Assertions.assertThat(testEntity.NiceCntDisplay).isEqualTo("1")
        Assertions.assertThat(testEntity.Satis).isEqualTo(1)
        Assertions.assertThat(testEntity.SatisDisplay).isEqualTo("1")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NormalTest2() {
        // TODO エンティティテスト1(引数エンティティ版)
        val date = Date()
        val replyEntity = UserEntity()
        val bookEntity = BookEntity()
        val bookReplyForm = BookReplyForm()

        val testEntity = ReplyEntity(replyEntity, bookEntity, bookReplyForm, date)
        Assertions.assertThat(testEntity.ReplyId).isEqualTo("")
        Assertions.assertThat(testEntity.UserId).isEqualTo("")
        Assertions.assertThat(testEntity.BookId).isEqualTo("")
        Assertions.assertThat(testEntity.Comment).isEqualTo("")
        Assertions.assertThat(testEntity.NiceCnt).isEqualTo(0)
        Assertions.assertThat(testEntity.NiceCntDisplay).isEqualTo("0")
        Assertions.assertThat(testEntity.Satis).isEqualTo(1)
        Assertions.assertThat(testEntity.SatisDisplay).isEqualTo("1")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }
}