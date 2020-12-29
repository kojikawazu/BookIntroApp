package com.example.bookintroapp.valueobject.entity

import com.example.bookintroapp.valueobject.form.form.SignupForm
import org.junit.Test
import org.assertj.core.api.Assertions.*
import java.util.*

class UserEntityTest{

    @Test
    fun NormalTest0(){

        // TODO コンストラクタテスト(引数無し版)
        val userEntity = UserEntity()
        assertThat(userEntity.UserId).isBlank()
        assertThat(userEntity.UserName).isBlank()
        assertThat(userEntity.Email).isBlank()
        assertThat(userEntity.ForgotPasswd).isBlank()
        assertThat(userEntity.Created).isNotNull()
    }

    @Test
    fun NormalTest1(){
        val date: Date = Date()

        // TODO コンストラクタテスト
        val userEntity = UserEntity("0", "テストユーザー", "test@example.com", "1111", date)
        assertThat(userEntity.UserId).isEqualTo("0")
        assertThat(userEntity.UserName).isEqualTo("テストユーザー")
        assertThat(userEntity.Email).isEqualTo("test@example.com")
        assertThat(userEntity.ForgotPasswd).isEqualTo("1111")
        assertThat(userEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }

    @Test
    fun NormalTest2(){
        val date: Date = Date()
        val signupForm = SignupForm()

        // TODO コンストラクタテスト
        val userEntity = UserEntity(signupForm, date)
        assertThat(userEntity.UserId).isEqualTo("0")
        assertThat(userEntity.UserName).isEqualTo("")
        assertThat(userEntity.Email).isEqualTo("")
        assertThat(userEntity.ForgotPasswd).isEqualTo("")
        assertThat(userEntity.Created.time.toString()).isEqualTo(date.time.toString())
    }


}