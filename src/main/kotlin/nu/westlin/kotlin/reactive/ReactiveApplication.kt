package nu.westlin.kotlin.reactive

import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.toFlux

@SpringBootApplication
class ReactiveApplication

val logger: Logger = LoggerFactory.getLogger(ReactiveApplication::class.java)

// Extension function for Logger so that we can call it with a nullable object
fun Logger.info(param: Any?) {
    logger.info(param!!.toString())
}

fun main(args: Array<String>) {
    val ctx = SpringApplicationBuilder()
            .sources(ReactiveApplication::class.java)
            .initializers(
                    beans {
                        bean {
                            val userRepository = ref<UserRepository>()
                            val users: Publisher<User> = listOf("Camilla", "Peter", "Adam", "Felix")
                                    .toFlux()
                                    .map { User(name = it) }
                                    .flatMap { userRepository.save(it) }

                            userRepository
                                    .deleteAll()
                                    .thenMany(users)
                                    .thenMany(userRepository.findAll())
                                    .subscribe { logger.info("Created user = $it") }
                        }
                    }
            )
            .run(*args)

    ctx.getBean(UserRepository::class.java)
            .findAll(Sort(Sort.Order.asc("name")))
            .subscribe { logger.info("User in database = $it") }
}


interface UserRepository : ReactiveMongoRepository<User, String>

@Document data class User(@Id var id: String? = null, var name: String? = null)