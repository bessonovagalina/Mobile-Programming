import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Data class stores a single mood entry.
data class MoodEntry(val id: Int, val mood: String, val score: Int)

// Sealed class represents possible application results.
sealed class AddResult {
    data class Success(val entry: MoodEntry) : AddResult()
    data class Error(val message: String) : AddResult()
}

interface SummaryProvider {
    fun summary(): String
}

open class Tracker(val name: String) {
    open fun description(): String = "Tracker: $name"
}

class MoodTracker(name: String) : Tracker(name), SummaryProvider {
    private val entries = mutableListOf<MoodEntry>()
    private var nextId = 1

    override fun description(): String = "AstraLite mood tracker: $name"

    fun addMood(mood: String, score: Int): AddResult {
        if (mood.isBlank() || score !in 1..5) return AddResult.Error("Mood must be nonempty and score 1..5")
        val entry = MoodEntry(nextId++, mood, score)
        entries.add(entry)
        return AddResult.Success(entry)
    }

    fun positiveMoods(): List<MoodEntry> = entries.filter { it.score >= 4 }

    override fun summary(): String {
        if (entries.isEmpty()) return "No moods recorded"
        val names: List<String> = entries.map { it.mood }
        val unique: Set<String> = names.toSet()
        val counts: Map<String, Int> = names.groupingBy { it }.eachCount()
        val total: Int = entries.map { it.score }.reduce { acc, value -> acc + value }
        return "Entries: ${entries.size}, unique moods: $unique, counts: $counts, average score: ${"%.2f".format(total.toDouble() / entries.size)}"
    }

    fun printEntries() {
        for (entry in entries) println("#${entry.id}: ${entry.mood} (${entry.score}/5)")
    }
}

fun processResult(result: AddResult) {
    when (result) {
        is AddResult.Success -> println("Saved: ${result.entry.mood}")
        is AddResult.Error -> println("Error: ${result.message}")
    }
}

// Higher-order function accepts a lambda.
fun displayReport(provider: SummaryProvider, printer: (String) -> Unit) {
    printer(provider.summary())
}

suspend fun loadDailyTip(): String {
    delay(300)
    return "Daily tip: Take a short break and reflect on your mood."
}

fun main() = runBlocking {
    val tracker = MoodTracker("My Daily Moods")
    println(tracker.description())

    val sampleMoods = listOf("Happy" to 5, "Calm" to 4, "Tired" to 2, "Happy" to 5)
    for ((mood, score) in sampleMoods) processResult(tracker.addMood(mood, score))
    //processResult(tracker.addMood("", 9)) // Demonstrates error handling.

    println("\nAll moods:")
    tracker.printEntries()
    println("\nPositive moods: ${tracker.positiveMoods().map { it.mood }}")
    displayReport(tracker) { report -> println("\nSummary: $report") }

    val job = launch { println(loadDailyTip()) }
    job.join()
}
