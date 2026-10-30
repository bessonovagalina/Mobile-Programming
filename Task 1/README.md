# AstraLite Mood Tracker — Kotlin Console

A small Kotlin/JVM console application that records daily moods, validates entries, analyzes scores, and displays a daily tip asynchronously. No Android components are used.

## How to run

Open the project folder in IntelliJ IDEA, import it as a Gradle project, select JDK 17 or newer, and run `Main.kt` (the `main()` function). Alternatively, with Gradle installed, run `gradle run` in the project folder.

## Homework requirements

| Requirement | Where demonstrated |
| --- | --- |
| Variables, data types, conditions, loops | `nextId`, `score`, `if`, `for` in `Main.kt` |
| List, Set, Map | `entries`, `unique`, `counts` |
| `map`, `filter`, `reduce` | `summary()`, `positiveMoods()` |
| Functions, higher-order functions, lambdas | `displayReport()` and its lambda call |
| Classes and objects | `Tracker`, `MoodTracker`, `tracker` |
| Inheritance | `MoodTracker : Tracker` |
| Interfaces and polymorphism | `SummaryProvider`, `displayReport(provider: SummaryProvider)` |
| Data class | `MoodEntry` |
| Sealed class | `AddResult.Success`, `AddResult.Error` |
| Suspend function and coroutine | `loadDailyTip()`, `runBlocking`, `launch` |

## Output

The program prints sample mood entries, an invalid-input error, positive moods, a summary with average score, and an asynchronous daily tip.
