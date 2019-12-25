package io.github.yuizho.generics

import org.w3c.dom.css.ViewCSS

open class Vehicle {
    fun drive() = println("ブーン=3")
}

class Car : Vehicle() {
    fun horn() = println("プップー")
}

class Driver<in T : Vehicle> {
    fun operate(t: T) {
        t.drive()
    }
}

fun drive(driver: Driver<Car>, car: Car) {
    driver.operate(car)
}

fun main() {
    drive(
            Driver<Vehicle>(), // Deiver野方パラメータにinつけてないとこれ入らない
            Car()
    )
}
