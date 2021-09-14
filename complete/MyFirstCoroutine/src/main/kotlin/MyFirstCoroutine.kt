import kotlinx.coroutines.*

fun main() = runBlocking {

    val scope = CoroutineScope(Job())


    //Unhandled exception.
    scope.launch {
        throw RuntimeException("failed for some reason")
    }

    // To run the various code example just uncomment the relevant code.
/*
    //We can use the kotlin try/catch
    scope.launch {
        try {
            throw RuntimeException("failed for some reason")
        } catch (e: Exception) {
            logMessage("try/catch handling Exception: $e")
        }

    }
*/

/*
    //We can use the Kotlin runCatching
    scope.launch {
        val result = runCatching {
            throw RuntimeException("failed for some reason")
        }
        if (result.isSuccess) {
            logMessage("I am Happy!!")
        }
        if (result.isFailure) {
            logMessage("I am Sad :( ")
        }
    }
*/

/*
    //We need to be thoughtful about where to place the exception handling code.
    //This will NOT catch any Exceptions raise from with the launch code block!
    try {
        scope.launch {
            try {
                launch {

                    throw RuntimeException("failed for some reason")

                }
            } catch (e: Exception) {
                logMessage("try/catch handling Exception: $e")
            }
        }
    } catch (e: Exception) {
        logMessage("try/catch handling Exception: $e")
    }
*/

/*
    //We wrap the code which potentially can cause an exception not the coroutine
    // builder functions themselves.
    scope.launch {

        launch {
            logMessage("Inner child coroutine")
        }
        try {
            throw RuntimeException("failed for some reason")
        } catch (e: Exception) {
            logMessage("try/catch handling Exception: $e")
        }
    }
*/

/*
    //Ensure the async{} is creates a 'Root' Coroutine, in other words its called off the CoroutineScope instance
    //in ths case held by the scope variable.
    scope.launch {

        //This version uses the scope = CoroutineScope(Job()) and therefore a 'Root' Coroutines is created.
        //val deferred = scope.async {

        //This version uses the outer Coroutine as the parent and is therefore not creating a 'Root' Coroutine.
        val deferred = async {
            "Hello World!"
            throw RuntimeException("failed for some reason")
        }

        try {
            val result = deferred.await()
            logMessage(result)
        } catch (e: Exception) {
            logMessage("try/catch handling Exception: $e")
        }
    }
*/


/*
    //By building the async coroutine from within a supervisorScope, we ensure it's a 'Root' coroutine.
    scope.launch {

        supervisorScope {
            val deferred = async {
                "Hello World!"
                throw RuntimeException("failed for some reason")
            }

            try {
                val result = deferred.await()
                logMessage(result)
            } catch (e: Exception) {
                logMessage("try/catch handling Exception: $e")
            }
        }
    }
*/

/*
    //By building the async coroutine from within a supervisorScope, we ensure it's a 'Root' coroutine,
    // however, this is not the case with  coroutineScope{}.
    scope.launch {

        coroutineScope {
            val deferred = async {
                "Hello World!"
                throw RuntimeException("failed for some reason")
            }

            try {
                val result = deferred.await()
                logMessage(result)
            } catch (e: Exception) {
                logMessage("try/catch handling Exception: $e")
            }
        }
    }
*/

    delay(1000)
}

fun logMessage(msg: String) {
    println("Running on: [${Thread.currentThread().name}] | $msg")
}

