package com.example.bookintroapp.valueobject.form

import com.example.bookintroapp.valueobject.form.form.BookAddForm
import org.junit.Test
import org.assertj.core.api.Assertions.*

class BookAddFormTest {

    @Test
    fun NormaiTest0(){

        // TODO コンストラクタテスト0
        val bookAddForm = BookAddForm()
        assertThat(bookAddForm.BookNameString).isBlank
        assertThat(bookAddForm.CommentString).isBlank
        assertThat(bookAddForm.TitleString).isBlank
        assertThat(bookAddForm.SatisString).isEqualTo("1")
    }


}

