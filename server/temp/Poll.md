### Poll table

| id | question                     | voters_visibility | votes_updatable | post_id |
|----|------------------------------|-------------------|-----------------|---------|
| 1  | What is your favorite color? | PUBLIC            | true            | 101     |
| 2  | Best programming language?   | PRIVATE           | false           | 102     |

### Poll options table

| id | poll_id | option     |
|----|---------|------------|
| 1  | 1       | Red        |
| 2  | 1       | Blue       |
| 3  | 2       | JavaScript |
| 4  | 2       | Java       |

### Poll Voters table

| id | user_id | option_id |
|----|---------|-----------|
| 1  | 201     | 1         |
| 2  | 202     | 2         |
| 3  | 203     | 3         |