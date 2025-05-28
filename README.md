# Arkantos
An app for Android that helps you discover fantastic games.

Demo video: https://youtu.be/JvWwdAy-qnA

## App Description

1. Fetch game info from server and show on UI.

2. 4 screens: Home, Genre Screen, Game Details Screen, Favorite games Screen.

3. Home contains 1 carousel showing 15 most popular games and 8 game containers showing games filtered by genre.

4. Genre Screen shows all games filtered by that genre.

5. Game Details screen shows game name, cover, release dates, summary etc. Games can be marked as favorite in this screen.

6. Favorite Games screen shows all games favorited by user. ( Favorite games is stored in local database. Removing app will remove favorite games)

## Technical Components

1. language: Kotlin

2. Api: "https://www.igdb.com/api"

3. Retrofit for Api calls

4. Moshi for Json Parsing (Not Gson becasue people prefer Moshi over Gson now)

5. Data Binding: Android's component for binding viewmodel's data directly to view, replace findViewById

6. Room for database ( Every recycler view first call DB for data, When db does not have enough data , Data is fetched from Api)

7. MVVM Architecture(one activity, multiple fragments, Every fragment has a view model, View model calls to Repository for data, Repository calls DB and network layer)

8. View Model(android's lifecycle aware view model component to support rotation) 

9. Livedata(UI observe viewmodel's livedatas for relative data)

10. Kotlin Coroutines for making network calls and db calls / Whenever background thread is required

11. Navigation Component (Android's new navigation component which is similar to IOS storyboard)

12. Glide (Efficiently load images and cache them)

13. Material Library for cute buttons and Cards

14. Shimmer(Facebook's shimmer feature for image loading placeholder

15. Paging(Android's Paging component for implementing pagination)

16. Shine Button(Twitter's Heart animation button for "add to favorite" button)
