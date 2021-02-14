import io.reactivex.Observable
import model.IGImagePost
import model.IGUser
import service.FindApi
import service.ServiceFactory
import java.io.*
import java.lang.Exception

fun main(args: Array<String>) {
    val api = ServiceFactory.getService()
    val user = File("iguser")
    val f = FileOutputStream(user)
    val pw = PrintWriter(f)

    val mediaFile = File("igmedia")
    val m = FileOutputStream(mediaFile)
    val mediaPW = PrintWriter(m)

    val listOfIGs = mutableListOf<Observable<IGUser>>()
    listOfIGs.add(getObsFor(api, "kaygedan"))
    listOfIGs.add(getObsFor(api, "Paul_Oberleitner"))
    listOfIGs.add(getObsFor(api, "Lizzi1402"))
    listOfIGs.add(getObsFor(api, "fit.by.leni"))
    listOfIGs.add(getObsFor(api, "prinzessin_jasmin"))
    listOfIGs.add(getObsFor(api, "wolterkevin"))
    listOfIGs.add(getObsFor(api, "bro_sep"))
    listOfIGs.add(getObsFor(api, "johannes_luckas"))
    listOfIGs.add(getObsFor(api, "fitness__kaykay"))
    listOfIGs.add(getObsFor(api, "patriciakraft"))

    try {
        pw.println(IGUser.HEADER)
        mediaPW.println(IGImagePost.HEADER)

        Observable.merge(listOfIGs).subscribe({
            print("WRITING NEW LINE")
            pw.println(it.writeToCsv())
            for (post in it.igTimelineMedia.imagePosts) {
                mediaPW.println(post.writeToCsv(it.id))
            }
        }, { e ->
            print("ERROR" + e.localizedMessage)
        })
        mediaPW.flush()
        mediaPW.close()
        m.close()

        pw.flush()
        pw.close()
        f.close()
    } catch (e: FileNotFoundException) {

    } catch (e: IOException) {

    }





    println("Hello World!")
}

fun getObsFor(api: FindApi, user: String): Observable<IGUser> {
    return api.loadProfile(user)
}

