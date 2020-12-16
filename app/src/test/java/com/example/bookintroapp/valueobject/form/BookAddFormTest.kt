package com.example.bookintroapp.valueobject.form

import android.widget.EditText
import android.widget.Spinner
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.Assert.*
import org.assertj.core.api.Assertions.*

class BookAddFormTest {

    @Test
    fun NormaiTest0(){

        // TODO コンストラクタテスト0
        val bookAddForm = BookAddForm()
        assertThat(bookAddForm.BookNameString).isBlank()
        assertThat(bookAddForm.CommentString).isBlank()
        assertThat(bookAddForm.TitleString).isBlank()
        assertThat(bookAddForm.SatisString).isEqualTo("1")
    }

    @Test
    fun NormaiTest1(){

    }

    @Test
    fun isEmpty() {

    }

    @Test
    fun checkBookName() {
    }

    @Test
    fun checkTitle() {
    }

    @Test
    fun checkComment() {
    }
}

