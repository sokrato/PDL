package com.xux.elib.form

import com.xux.elib.HashHelperImpl
import javax.validation.constraints.Min
import javax.validation.constraints.Pattern

@Phone(msg = "first", names = ["a"])
data class FiletForm(
        @Min(0)
        var id: Int = 0,
        var name: String = ""
)


@Pattern.List(
        Pattern(regexp = "1234"),
        Pattern(regexp = "abc")
)
@Target(AnnotationTarget.CLASS)
//@Retention(AnnotationRetention.SOURCE)
@Repeatable
annotation class Phone(
        val msg: String = "",
        val names: Array<String>
)

fun main() {
    for (ann in HashHelperImpl::class.java.getAnnotationsByType(Min::class.java))
        println(ann)

    for (ann in FiletForm::class.annotations)
        println("xx: " + ann.annotationClass)
}