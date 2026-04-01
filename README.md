## Tech Stack

*   **UI:** Jetpack Compose, Material Design 3
*   **Architecture:** Clean Architecture, MVVM
*   **Concurrency:** Kotlin Coroutines & Flow
*   **Networking:** Retrofit 2, OkHttp
*   **Database:** Room
*   **Pagination:** Paging 3 (with `RemoteMediator`)
*   **DI:** Hilt
*   **Navigation:** Jetpack Navigation Compose

## Design Decisions
Приложение работает без интернета, если в кеше есть данные. Всегда первым делом обращаемся к кешу.
Если подключение к интернету не нарушено, то обновляем кеш, делая запрос к API.
