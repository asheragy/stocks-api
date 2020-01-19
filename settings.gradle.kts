rootProject.name = "stocks-api"

include(":stocks-core")
project(":stocks-core").projectDir = File(settingsDir, "../Stocks/core")