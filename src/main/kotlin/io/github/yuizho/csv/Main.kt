package io.github.yuizho.csv

import com.opencsv.bean.CsvToBeanBuilder
import com.opencsv.bean.StatefulBeanToCsvBuilder
import java.io.StringReader
import java.io.StringWriter
import java.time.LocalDate

fun main() {
    // export from Entity class
    val entities = listOf<CsvEntity>(
            CsvEntity(1, "山田 太郎", 100, LocalDate.now()),
            CsvEntity(2, "山田 花子", 200, LocalDate.now(), """
                少し落ち込んでいる様子
                ケアしてあげる必要あり
            """.trimIndent())
    )
    val csv = StringWriter().use { writer ->
        val entityToCsv = StatefulBeanToCsvBuilder<CsvEntity>(writer).build()
        entityToCsv.write(entities)
        writer.toString()
    }
    println(csv)

    // read from csv string
    val eintites = StringReader(csv).use { reader ->
        val csvToEntity = CsvToBeanBuilder<CsvEntity>(reader)
                .withType(CsvEntity::class.java)
                // 普通にやるとデフォルトコンストラクタが必要だった....(Immutableなクラスだと普通には出来ない……)
                .withMappingStrategy(ImmutableColumnPositionMappingStrategy(CsvEntity::class.java))
                .build()
        csvToEntity.parse()
    }
    println(entities)
}