# Quality Result

- nested JSON request validation was added;
- invalid directions and command characters are rejected explicitly;
- malformed text input now produces an HTTP 400 through a centralized error
  contract instead of returning an error string with HTTP 200;
- regression tests cover invalid commands and directions.
