# Epam ESM task

# Table of Contents

* [Used technologies](#Used-technologies)
* [How to use this repository](#how-to-use-this-repository)

# Used technologies

* [Spring Framework](https://docs.spring.io/spring-framework/docs/current/reference/html/)
* [JDK 11](https://docs.oracle.com/en/java/javase/11/)
* [Spring Framework JDBC](https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html)
* [Swagger2](https://swagger.io/docs/specification/2-0/basic-structure/)
* [MapStruct](https://mapstruct.org/)
* [Gradle 7.4.2](https://docs.gradle.org/7.4.2/javadoc/)
* [Spring WebMVC](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)
* [PostgreSQL 8.2+](https://www.postgresql.org/)
* [H2](https://www.h2database.com/html/quickstart.html)
* [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
* [Mockito](https://site.mockito.org/)

# How to use this repository

It's recommended creating a fork of this repository to work on tasks independently. In this case you'll have your own
copy of the repository and all your implementations will stay in your own repository. Of course, this approach
has both benefits and drawbacks:

* Benefit - nobody sees your code except yourself.
* Drawback - nobody sees your code except yourself.

## How to make a fork of this repository

To create a fork of this repository press a `Fork` button on the top right of this page. GitHub will ask you about
the location of a newly created repository and next you'll be able to clone the repository to your local machine:

```shell script
$ git clone https://github.com/your-account-name/epam-task-2
``` 

As a result, you'll have a folder called `epam-java-courses` locally.

## How to get updates of a remote repository

When the fork is created it'll not receive updates automatically, it's necessary making some manual configuration
for your local repository - you need to add a new remote to your local repository. To do it, execute the following
command:

```shell script
$ git remote add -t main epam-task http://github.com/NodirUmarov/epam-task-2/
```
This command will associate your local repository with one additional remote repository - mine repository. It means
that you can send and receive updates from both remote locations - from mine and from your.

The following command will show what remotes are associated with your local repository:

```shell script
$ git remote show

epam-task
origin
```  

`origin` is a default name for your remote (`https://github.com/your-account-name/epam-task-2`), the `epam-task`
remote is an association with my remote repository (`https://github.com/NodirUmarov/epam-task-2`).

The next step is to create a branch that will get updates from my repository. The following command will create
such kind of branch:

```shell script
$ git checkout -b epam_main --track epam-task/main
```

This command will create a new branch called `epam_main` that receives updates from my repository. You can see the
list of all your branches by executing the following command:

```shell script
$ git branch -a

epam_main
main
```

When you would like to get updates, you need to pull updates from my repository:

```shell script
$ git checkout epam_main
$ git pull
```

And next merge my changes to your `main` branch:

```shell script
$ git checkout main
$ git pull
$ git merge epam_main
```

As a result, your `main` branch will receive updates and new tasks if they're present.

Don't forget to update your remote `main`:

```shell script
$ git push origin main
```

Final step to get you project work is to add .env files
```shell script
$ cat > data/src/main/resources/.env
DRIVER=Your JDBC Driver
URL=Your Database URL
USER=Your Username
PASSWORD=Your Password
SCHEMA=public
```
and

```shell script
$ cat > data/src/test/resources/.env
DRIVER=Your JDBC Test Driver
URL=Your Test Database URL
USER=Your Username For Test Database
PASSWORD=Your Password For Test Database
SCHEMA=Your Schema For Test Database
```

To make .env file work install [EnvFile](https://plugins.jetbrains.com/plugin/7861-envfile) plugin and in application configurations enable it as shown below
[<img alt="alt_text" width="40px" src="images/image.PNG" />](https://plugins.jetbrains.com/files/7861/screenshot_19713.png)
