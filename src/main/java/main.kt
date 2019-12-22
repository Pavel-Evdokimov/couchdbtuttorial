import com.cloudant.client.api.ClientBuilder
import java.net.URL

class Test(val hello: String, val name: String)

fun main(args: Array<String>) {

    val client = ClientBuilder.url(URL("http://localhost:59842"))
        .username("admin")
        .password("admin")
        .build()

    val testDb = client.database("test", false)

    val t: List<Test> = testDb
        .search("s/s")
        .includeDocs(true)
        .query("name:Павел", Test::class.java)

    t.forEach {
        println("Check out what I've found ${it.name}")
        println(it.hello)
    }

    val changesTestDb = testDb.changes()
        .since("now")
        .includeDocs(true)
        .continuousChanges()

    while (changesTestDb.hasNext()) {
        var feed = changesTestDb.next()
        val docId = feed.id
        val doc = feed.doc
        println(doc)
    }

}