package com.liu.brookl

open class Parent(){}
class Son():Parent(){}
class Daughter():Parent(){}

interface Eat<out T>{
    fun eat():T
    fun doFood(t:T)
}

class Home:Eat<Son>{
    override fun eat(): Son {
        return Son()
    }

    override fun doFood(t: Son) {

    }

}

val eat:Eat<Parent> = Home()