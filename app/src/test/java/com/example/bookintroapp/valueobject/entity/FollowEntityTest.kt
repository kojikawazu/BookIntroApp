package com.example.bookintroapp.valueobject.entity

import org.assertj.core.api.Assertions
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class FollowEntityTest{

    @Test
    fun NormalInit0(){

        // TODO 通常コンストラクタ
        val testEntity = FollowEntity()
        Assertions.assertThat(testEntity.FollowId).isBlank
        Assertions.assertThat(testEntity.UserId).isBlank
        Assertions.assertThat(testEntity.FollowerId).isBlank
        Assertions.assertThat(testEntity.Created).isNotNull
    }

    @Test
    fun NormalInit1(){
        val date = Date()

        // TODO 値挿入コンストラクタテスト
        val testEntity = FollowEntity("1", "1", "1", date)
        Assertions.assertThat(testEntity.FollowId).isEqualTo("1")
        Assertions.assertThat(testEntity.UserId).isEqualTo("1")
        Assertions.assertThat(testEntity.FollowerId).isEqualTo("1")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NormalInit2(){
        val date = Date()
        val userEntity = UserEntity()
        val followEntity = UserEntity()

        // TODO 値挿入コンストラクタテスト
        val testEntity = FollowEntity(userEntity, followEntity, date)
        Assertions.assertThat(testEntity.FollowId).isEqualTo("0")
        Assertions.assertThat(testEntity.UserId).isEqualTo("")
        Assertions.assertThat(testEntity.FollowerId).isEqualTo("")
        Assertions.assertThat(testEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }


}