# Pass In Java Spring Rest API

## Used libraries:
- Spring
- Lombok

## How to run:

### Prerequisites:

- Any JDK (Java Development Kit) Distribution - Version 17 or higher recommended;
- Git installed: [Git Download](https://git-scm.com/downloads)

## Configuration Steps:

### 1. Clone the repository:
```bash
git clone https://github.com/alvarogonoring/pass-in.git
cd pass-in
```
### 2. Start the application in your favorite IDE or:
```bash
mvn spring-boot:run
```

## Available Endpoints:

To fetch any api call, just apoint the url for http://localhost:8080

### POST /events

Request Body:
```bash
{
  String title;
  String details;
  Integer maximumAttendees;
}
```

### GET /events/:id

### GET /events/attendees/:id

### GET /events/:attendeeId/badge

### POST /events/:eventId/attendees

Request body:
```bash
{
  String name;
  String email;
}
```

### POST /events/:attendeeId/check-in
