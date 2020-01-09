package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.*

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Component
@Transactional
class Initializer(val personRepository: PersonRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun run() {
        this.personRepository.findAll().forEach {
            println("the id ${it.id} belongs to ${it.name} who has ${it.photos.size} photos")
        }
    }
}

interface PersonRepository : JpaRepository<Person, Int>

@Entity
class Photo(@GeneratedValue @Id val id: Int, val fileName: String)

@Entity
class Person(@GeneratedValue @Id val id: Int, val name: String, @OneToMany val photos: Set<Photo>)