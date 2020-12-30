package com.example.bookintroapp.valueobject.entity

import org.assertj.core.api.Assertions
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class NiceReplyEntityTest{

    @Test
    fun NormalTest0() {
        // TODO エンティティテスト1(引数無し版)
        val testEntity = NiceReplyEntity()
        Assertions.assertThat(testEntity.NiceRId).isBlank
        Assertions.assertThat(testEntity.UserId).isBlank
        Assertions.assertThat(testEntity.ReplyId).isBlank
        Assertions.assertThat(testEntity.Created).isNotNull
    }

    @Test
    fun NormalTest1() {
        // TODO エンティティテスト1(引数有り版)
        val date = Date()
        val testEntity = NiceReplyEntity("1", "2", "3", date)
        Assertions.assertThat(testEntity.NiceRId).isEqualTo("1")
        Assertions.assertThat(testEntity.ReplyId).isEqualTo("2")
        Assertions.assertThat(testEntity.UserId).isEqualTo("3")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NormalTest2() {
        // TODO エンティティテスト1(引数エンティティ版)
        val date = Date()
        val replyEntity = ReplyEntity()
        val userEntity = UserEntity()

        val testEntity = NiceReplyEntity(replyEntity, userEntity, date)
        Assertions.assertThat(testEntity.NiceRId).isEqualTo("0")
        Assertions.assertThat(testEntity.UserId).isEqualTo("")
        Assertions.assertThat(testEntity.ReplyId).isEqualTo("")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

}