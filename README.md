# Shortened URL

Shortened URL web application using Java + Spring Boot.

# How to run:

$ mvn spring-boot:run

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
