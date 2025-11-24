# Job Title Standardiser

A Spring Boot application that normalises job titles by mapping input strings to standardised titles. The application uses **Cosine similarity** with optional context keywords (programming languages, certifications, etc.) to provide robust matching. It also supports caching using **Caffeine** for performance.

---

## Features

- Normalises job titles to a predefined list of standardised titles.
- Supports **context keywords** for more accurate matching (e.g., "Java engineer" → "Software engineer").
- REST API for easy integration.
- Caching via Caffeine with configurable expiry and size.
- Unit and integration tests included.

## REST API

The application exposes a REST endpoint to normalize job titles.

---

### Normalize a job title

**Endpoint:** GET /api/v1/jobs/normalise

**Query Parameters:**

| Parameter | Type   | Description                   | Required |
|-----------|--------|-------------------------------|----------|
| title     | string | The input job title to normalise | Yes      |

**Example Request:**

```bash
curl -X GET "http://localhost:8080/api/v1/jobs/normalise?title=java%20engineer"

