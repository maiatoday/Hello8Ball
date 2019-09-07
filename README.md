# Hello8Ball [![CircleCI](https://circleci.com/gh/maiatoday/Hello8Ball.svg?style=svg)](https://circleci.com/gh/maiatoday/Hello8Ball)

<img src="./ball.svg" width="100">

A Magic 8 Ball app to use a sampler to explore Kotlin testing. The app works as a magic 8 ball. It can also generate passwords, find a synonym for a word and check if a number is prime or not. There are [matching slides](slides/TestingKotlinCoroutines.pdf) for a presentation at [Kotlin Everywhere 2019 in Johannesburg](https://www.kotlin-everywhere.co.za/).

## How to run

### API key
This app uses an [WordsAPI](https://www.wordsapi.com/) api key for the synonym lookup. Copy `app/secrets.gradle.template` to `app/secrets.gradle` and fill in your api key.

### Import and run the app
Import the build.gradle in the root folder into Android Studio latest canary. 
Run the app.

Or

`./gradlew assembleDebug` and find the apk in `app/build/outputs/apk/debug`


## How to test

Run the tests in Android studio or use the command line.

### Unit tests

`./gradlew test`

### Detekt

`./gradlew detekt`

### Coverage

`./gradlew testDebugUnitTestCoverageVerification`

### Instrumented tests

`./gradlew connectedAndroidTest`

<img src="screenshots/Screenshot_1566145786.png" width="256">

