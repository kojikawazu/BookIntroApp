package com.example.bookintroapp.valueobject.entity

import org.assertj.core.api.Assertions
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class NiceEntityTest {

    @Test
    fun NormalTest0() {
        // TODO エンティティテスト1(引数無し版)
        val testEntity = NiceEntity()
        Assertions.assertThat(testEntity.NiceId).isBlank
        Assertions.assertThat(testEntity.UserId).isBlank
        Assertions.assertThat(testEntity.BookId).isBlank
        Assertions.assertThat(testEntity.Created).isNotNull
    }

    @Test
    fun NormalTest1() {
        // TODO エンティティテスト1(引数有り版)
        val date = Date()
        val testEntity = NiceEntity("1", "2", "3", date)
        Assertions.assertThat(testEntity.NiceId).isEqualTo("1")
        Assertions.assertThat(testEntity.UserId).isEqualTo("2")
        Assertions.assertThat(testEntity.BookId).isEqualTo("3")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NormalTest2() {
        // TODO エンティティテスト1(エンティティ引数版)
        val date = Date()
        val userEntity = UserEntity()
        val bookEntity = BookEntity()

        val testEntity = NiceEntity(userEntity, bookEntity, date)
        Assertions.assertThat(testEntity.NiceId).isEqualTo("0")
        Assertions.assertThat(testEntity.UserId).isEqualTo("")
        Assertions.assertThat(testEntity.BookId).isEqualTo("")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }
}