# Repository Working Agreement

- Keep modernization work off `main`.
- Merge focused branches into `modernization/java21-spring-boot`.
- Leave the final pull request to `main` for owner review and merge.
- Preserve `/api/mow` and `/api/mow-string` behavior unless documented.
- Verify with Java 21 using `./moveitnow/gradlew -p moveitnow clean test`.
- Reject malformed commands explicitly; never silently invent mower behavior.
