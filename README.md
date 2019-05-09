<h1>Shortened URL</h1>

Shortened URL web application using Java + Spring Boot.

<h2>How to run:</h2>

1. Build the project

```
$ mvn clean install
```

2. Run the project

```
$ mvn spring-boot:run
```

<br />

# Main Class:

ProjectShortenedUrlApplication.java

# Controller

URLController.java

# /api/v1/fetch/urls

Fetching all urls that are saved in hsqldb

# /api/v1/convert?url=[URL]

Converting URL (example: https://www.facebook.com) into a shortened url

# /api/v1/process/urls

Accepting a txt file that contains URLs for conversion and then saving into the in hsqldb
