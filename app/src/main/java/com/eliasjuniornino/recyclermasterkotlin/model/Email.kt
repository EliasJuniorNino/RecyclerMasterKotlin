package com.eliasjuniornino.recyclermasterkotlin.model

data class Email(
    val user: String,
    val subject: String,
    val preview: String,
    val date: String,
    val stared: Boolean,
    val unread: Boolean,
    var selected: Boolean = false
)

class EmailBuilder {
    var user: String = ""
    var subject: String = ""
    var preview: String = ""
    var date: String = ""
    var stared: Boolean = false
    var unread: Boolean = false

    fun build() : Email = Email(user, subject, preview, date, stared, unread, false)
}

fun email(block: EmailBuilder.() -> Unit): Email = EmailBuilder().apply(block).build()

fun fakeEmails() = mutableListOf(
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "1 Jan"
        stared = true
    },
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "2 Jan"
        stared = true
    },
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "3 Jan"
        stared = true
    },
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "4 Jan"
        stared = true
    },
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "5 Jan"
        stared = true
    },
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "6 Jan"
        stared = true
    },
    email {
        user = "Facebook"
        subject = "Recomendação de amigos"
        preview = "Oi Elias estou te incomodando novamente"
        date = "7 Jan"
        stared = true
    },
)