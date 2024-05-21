# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

My Phase 2 Diagram:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAA5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJcYNdLfnJuVVk8R03W2gj1N9hPKFvsuZygAmJxObr7vOjK8nqZnseXmBjxvdAOFMLxfH8AJoHYckYB5CBoiSAI0gyLJkHMNkjl3ao6iaVoDHUBI0HfAYDy-T4nhtJZdj6A4sJBQoN13D8a3In9nmDW0aIBc5NyVbUhxgBAEKQNBYXgxDUXRWJsUHXVe2TMkKTNOEAxQIMFiWQsmWTN0OXIXl+X9MjDHOGAqPecVJWrGAAHUWErLlzM4pYYAAXjc5zNPeHsi106cB2VQSVMZCcpwJBSWVTY1MkzbNc1YgtfJ0hVS3071fWMz91M7BtzzbKMYxHZKXRLfjJxVXLs3ytAwoTGdeN3CTRJXNdMCYkFyuY0jsu-R5fzy-8+to29OsKB8MGfV8SLU4bT0Gi9hoOECwO8PxAi8FA2wk3xmGQ9JMkwCbmC6+cKmkQz6i5ZoWgI1QiO6P8L1GtkOuHBbxw6zCgvKYT7F28SEN2qSMVkoL5L8qKYHJMA4pzJ70G00q9M5QyBSq0cLysiApWKpNUvK8EMZqurDETSKDQ4FBuFihsEqtYn-yRvs0vKC6+Su6sGYRpIJRxjHSa3N64KBrNWoQddGrG7cSiuOjijvAL4HQyaYBfN9emWzhVogwJIQ4ODoRgABxfNWX21CjpVk7Ap3M7ja5PCWnsfNHo+2r6NeqX3uq-92ql76BN+6FTdGVRxJDs2QZkwXychg0Ybh2EeeZ-zCjLbk+XRnnsdxhsSpZwnKp52OIvjlMhMjsPYVT1L0-0zOjOhs3c+b0YC-8ovBOQWIYAgMUTfzUK5KV4WeSrtRxcloFFdOq4phdsPxkqfpF5QD1pGXgBGJ8AGYABYnhQzIVLuFYvh0BBQGbU-DwmL4169fN772GBGnlu372tqaNZ6BezeXhUVe+YN7bz3ofKYx9TQmXPk8S+19b5sQfvmJ+owX5vy1qBTwa1ILYB8FAbA3B4AxUMKHQwFtDrHUDrLSotQGg3TXm7X2F4SKPzvqsD+xwGoz3nCXT23C5xXD6GwtiN4vpd2iumCkKAyGwjTCaMh0csSlxkBTCuic6Ypw7nXdkqMs6ChznzPO3Z8auiVoOH2mNxwjzLilA08jMiyLXhvWYUDEE5XgSAG+JlZgiJymvWuZj66egrFWZx0hXEHWgdlWYnjvExLbigVB-j8zthjGvbRZiJGD1GMPcGo9vaGhIYotQbVxFzz3P-UYoDyg7wPjAThs9xrfzVtNXoVT16b1qeAhpZhtbYN1gESw1NhLJBgAAKQgKJHJ5C4lWxyDbGW2EqiUidow4I7sSKEOAMMqAcAIDCSgCseyHorotAAEI8gUHAAA0sg6pXS+h1P3iNfhjFCl8IVgxM4PChHbN2fsw5xyWCnK5Bcq5tz7mdJWM8ka4jbYVUEgAKymWgWRKLRIlLRKDFRpj1EUiTlovFKMDL6MZljIxAtiXmJ+uS6x+TbGlSUmAJxIDpCBJLME0lTdwmRNQu42JV8vECsSck3xqTKUZOpdksheSBIQzsRXPwWhHH5lhLK7QHKSWUiqNIBQnM3E+JgHEkVa9W4ZJHgI4E5QMVovzFPf2PCtz0Tli9JWx0f7vkwTrdaAQvA7OVumWAwBsCEMIPERIKQonzIwhU86l1rqtGMG695vzDTcDwB1R1c5qFExABmuE+ag1KLBvKuOiryhUxptEqA4ctWsxkAmmACgeQ8hgCFbQrcbi4rUZW6m0iyF1syZy3RjaOZORbW2jV8hzX5h7eXPt1aFAaiTvWrl7NHYTtbc2hsjJW7FUtamwR6bi2lIltmviFSeiNOlh61pv9vVAA