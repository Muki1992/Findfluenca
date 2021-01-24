import service.ServiceFactory

fun main(args: Array<String>) {
    val api = ServiceFactory.getService()

    api.loadProfile("dailydoseoflara").subscribe ({

        print("SUCCESS: FETCHED USER: ${it.fullName}")
    },{e->

        print("ERROR" + e.localizedMessage)


    })

    println("Hello World!")
}