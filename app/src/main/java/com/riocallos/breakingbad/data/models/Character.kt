package com.riocallos.breakingbad.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey var char_id: Int = 0,
    var name: String = "",
    var birthday: String = "",
    val occupation: Array<String> = emptyArray(),
    var img: String = "",
    var status: String = "",
    var nickname: String = "",
    var appearance: Array<Int> = emptyArray(),
    var portrayed: String = "",
    var category: String = "",
    var better_call_saul_appearance: Array<Int> = emptyArray()
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Character

        if (char_id != other.char_id) return false
        if (name != other.name) return false
        if (birthday != other.birthday) return false
        if (!occupation.contentEquals(other.occupation)) return false
        if (img != other.img) return false
        if (status != other.status) return false
        if (nickname != other.nickname) return false
        if (!appearance.contentEquals(other.appearance)) return false
        if (portrayed != other.portrayed) return false
        if (category != other.category) return false
        if (!better_call_saul_appearance.contentEquals(other.better_call_saul_appearance)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = char_id
        result = 31 * result + name.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + occupation.contentHashCode()
        result = 31 * result + img.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + appearance.contentHashCode()
        result = 31 * result + portrayed.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + better_call_saul_appearance.contentHashCode()
        return result
    }
}
