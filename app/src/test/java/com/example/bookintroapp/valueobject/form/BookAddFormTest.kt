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
    fun isEmpty() {
        val nameMock =  mock<EditText>(name = "bookNameMock")
        nameMock.setText("テスト書籍")
        whenever(nameMock.text.toString()).thenReturn("テスト書籍")
        val titleMock =  mock<EditText>(name = "bookTitleMock")
        titleMock.setText("テストタイトル")
        //whenever(titleMock.text.toString()).thenReturn("テストタイトル")
        val commentMock =  mock<EditText>(name = "commentMock")
        commentMock.setText("テストコメント")
        //whenever(commentMock.text.toString()).thenReturn("テストコメント")
        val sppinerMock =  mock<Spinner>(name = "commentMock")

        // TODO コンストラクタテスト1
        val bookAddForm = BookAddForm(nameMock, titleMock, commentMock, sppinerMock)
        bookAddForm.SatisString = "2"
        assertThat(bookAddForm.BookNameString).isEqualTo("テスト書籍")
        assertThat(bookAddForm.CommentString).isEqualTo("テストタイトル")
        assertThat(bookAddForm.TitleString).isEqualTo("テストコメント")
        assertThat(bookAddForm.SatisString).isEqualTo("2")
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