# Clean Architecture (Jetpack Compose)

Android sample app structured in three layers — `domain`, `data`, `presentation` — with Hilt for
dependency injection, Retrofit for networking, and Compose + Navigation for the UI. The sample
feature lists users from `https://jsonplaceholder.typicode.com/` and opens a detail screen.

## Package structure

```
com.example.cleanarchitecture
├── core/                 Resource<T> wrapper shared by all layers
├── domain/               Pure Kotlin, no Android/framework dependencies
│   ├── model/            User
│   ├── repository/       UserRepository (interface)
│   └── usecase/          GetUsersUseCase, GetUserByIdUseCase
├── data/                 Implements the domain contracts
│   ├── remote/           UserApi (Retrofit)
│   ├── remote/dto/       UserDto, AddressDto, CompanyDto
│   ├── mapper/           UserDto -> User
│   └── repository/       UserRepositoryImpl
├── di/                   NetworkModule, RepositoryModule (Hilt)
└── presentation/         MVVM + unidirectional data flow
    ├── navigation/       Screen routes, AppNavGraph
    ├── common/           Loading / Error / Empty state composables
    ├── users/            UserListScreen, UserListViewModel, UserListState
    └── userdetail/       UserDetailScreen, UserDetailViewModel, UserDetailState
```

Dependency rule: `presentation → domain ← data`. The domain layer knows nothing about Retrofit,
Compose, or Android; the UI talks to use cases only, never to the repository implementation.

## Build and test

```bash
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest
```

The API base URL is a `BuildConfig` field (`BASE_URL`) defined in `app/build.gradle.kts`.
