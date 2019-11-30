package io.github.yuizho.csv

import com.opencsv.bean.CsvBindByPosition
import com.opencsv.bean.CsvCustomBindByPosition
import com.opencsv.bean.CsvDate
import java.time.LocalDate

class CsvEntity(id: Int,
                name: String,
                amount: Int,
                created: LocalDate,
                freeText: String?) {
    constructor(id: Int,
                name: String,
                amount: Int,
                created: LocalDate): this(id, name, amount, created, null) {}

    @CsvBindByPosition(position = 0, required = true)
    val id: Int = id

    @CsvBindByPosition(position = 1, required = true)
    val name: String = name
    // field持たせたいときはこんな感じ
    //  get() = field + "さん"

    @CsvBindByPosition(position = 2, required = true)
    val amount: Int = amount

    @CsvBindByPosition(position = 3, required = true)
    @CsvDate("yyyyMMdd")
    val created: LocalDate = created

    @CsvBindByPosition(position = 4)
    val freeText: String? = freeText

    override fun toString(): String {
        return "CsvEntity(id=$id, name='$name', amount=$amount, created=$created, freeText=$freeText)"
    }
}