package com.example.coroutinesflowibmskilsbuild

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Flow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        // show()
        runBlocking {
            runAsynchronous().forEach { i -> println(i) }
        }*/

        // Primeiro Flow

       /* runBlocking {
            launch {
                for (j: Int in 1..3) {
                    println("No estoy bloqueado $j")
                    kotlinx.coroutines.delay(1000)
                }
            }

            firstFlow().collect { value -> println(value) }
        }*/

        // Flow Cold Stram

      /*  runBlocking {
            println("Llamando Flow...")
            var flow: kotlinx.coroutines.flow.Flow<Int> = firstFlow()
            println("Collect...")
            flow.collect { value -> println(value) }
            println("collect again...")
            flow.collect { value -> println(value) }
        }*/

        // cancelando Flow

       /* runBlocking {
            withTimeoutOrNull(2500) {
                firstFlow().collect { value -> println(value) }
            }
            println("Finalizando")
        }*/

        // Segundo e terceiro Flow

        /*runBlocking {
           // secondFlow().collect { value -> println(value) }
            thirdFlow().collect { value -> println(value) }
        }*/

        // Operador map
        /*runBlocking {
            (1..3).asFlow()
                .map{request->performRequest(request)}
                .collect { response -> println(response) }
        }*/

        // Operador filter

        /*runBlocking {
            (1..3).asFlow()
                .filter { request->request>1 }
                .map{request->performRequest(request)}
                .collect { response -> println(response) }
        }*/

        // Operador transform
        /*runBlocking {
            (1..3).asFlow()
                .transform {
                    request->
                    emit("Making request $request")
                    emit(performRequest(request))
                }
                .collect {
                    response->
                    println(response)
                }
        }*/
        // operador take
        /*runBlocking {
            (1..3).asFlow()
                .take(2)
                .collect { response-> println(response)}
        }*/

        // operador terminal toList

       /* runBlocking {
            var lista: List<Int> = (1..3).asFlow().toList()
            println(lista)
        }*/

        // operador terminal first
        /*runBlocking {
           var numero:Int =  (6..90).asFlow()
                .first()
            println(numero)
        }*/

        // operador reduce

        /*runBlocking {
            var resultado: Int = (1..3).asFlow()
                .reduce { a, b -> a + b }
            println(resultado)
        }*/
        // Flow es Secuencial
       /* runBlocking {
            (1..5).asFlow()
                .filter {
                    i-> println("Filtrando $i")
                    i%2==0
                }
                .map {
                    i-> println("Map $i")
                    "String $i"
                }
                .collect {
                i-> println("collect $i")
                }
        }*/

        // Buffer
       /* runBlocking {
            val time: Long = measureTimeMillis {
                firstFlow()
                    .buffer()
                    .collect {
                            value ->
                        kotlinx.coroutines.delay(3000)
                        println(value)
                    }
            }

            println("$time ms")
        }*/

        // conflate

        /*runBlocking {
            val time: Long = measureTimeMillis {
                firstFlow()
                    .conflate()
                    .collect {
                            value ->
                        kotlinx.coroutines.delay(3000)
                        println(value)
                    }
            }

            println("$time ms")
        }*/

        // CollectLastest
       /* runBlocking {
            val time: Long = measureTimeMillis {
                firstFlow()
                    .collectLatest {
                            value ->
                        println("collecting $value")
                        kotlinx.coroutines.delay(3000)
                        println(value)
                    }
            }

            println("$time ms")
        }*/

        // Operador zip
        /*val nums: kotlinx.coroutines.flow.Flow<Int> = (1..3).asFlow()
        val strs: kotlinx.coroutines.flow.Flow<String> = flowOf("uno", "Dos", "Tres")
        runBlocking {
            nums.zip(strs) {
                    a, b ->
                "Zip: $a -> $b"
            }.collect { println(it) }
        }*/

        // Flattening Flow
        /*runBlocking {
            var ejemplo: kotlinx.coroutines.flow.Flow<kotlinx.coroutines.flow.Flow<String>> = (1..3).asFlow().map { requestFlow(it) }
        }*/

        // FlatMapConcat
        /*runBlocking {
            val startTime: Long = System.currentTimeMillis()
            (1..3).asFlow().onEach { kotlinx.coroutines.delay(100) }
                .flatMapConcat { requestFlow(it) }
                .collect { value ->
                    println("$value at ${System.currentTimeMillis() - startTime} ns from start")
                }
        }*/

        // FlatMapMerge
        runBlocking {
            val startTime: Long = System.currentTimeMillis()
            (1..3).asFlow().onEach { kotlinx.coroutines.delay(100) }
                .flatMapMerge { requestFlow(it) }
                .collect { value ->
                    println("$value at ${System.currentTimeMillis() - startTime} ns from start")
                }
        }
    }

    fun show() {
        // listar().forEach { i -> println(i) }
        secuencial().forEach { i -> println(i) }
    }

    fun listar(): List<Int> = listOf(3, 78, 90)

    fun secuencial(): Sequence<Int> = sequence {
        for (i: Int in 1..3) {
            Thread.sleep(1000)
            yield(i)
        }
    }

    suspend fun runAsynchronous(): List<Int> {
        return runBlocking {
            kotlinx.coroutines.delay(1000)
            return@runBlocking listOf(1, 2, 3)
        }
    }

    fun firstFlow(): kotlinx.coroutines.flow.Flow<Int> = flow {
        for (i: Int in 1..3) {
            kotlinx.coroutines.delay(1000)
            emit(i)
        }
    }

    fun secondFlow(): kotlinx.coroutines.flow.Flow<Int> {
        return flowOf(1, 2, 3)
    }

    fun thirdFlow(): kotlinx.coroutines.flow.Flow<Int> {
        return (1..3).asFlow()
    }

    suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response $request"
    }

    fun requestFlow(i: Int): kotlinx.coroutines.flow.Flow<String> = flow {
        emit("$i: First")
        kotlinx.coroutines.delay(5000)
        emit("$i: Second")
    }
}
