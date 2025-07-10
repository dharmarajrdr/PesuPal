### Post Table

| id | org_id | owner_id | created_time | title            | description       | media | status | commentable | shareable | bookmarkable |
|----|--------|----------|--------------|------------------|-------------------|-------|--------|-------------|-----------|--------------|
| 1  | 1      | 1        | 2023-10-01   | Leaving Zoho     | A new beginning   | true  | active | true        | true      | true         |
| 2  | 1      | 1        | 2023-10-02   | Turf Cricket     | It was fun        | false | active | true        | true      | true         |
| 3  | 1      | 1        | 2023-10-03   | Dream come true! | Happy to announce | true  | active | true        | true      | true         |

### Post Media Table

| id | post_id | media_id                             |
|----|---------|--------------------------------------|
| 1  | 1       | db5ac86b-81c2-4700-af37-24e3a549d66f |
| 2  | 1       | 1b2c3d4e-5f6a-7b8c-9d0e-1f2a3b4c5d6e |
| 3  | 3       | 2b3c4d5e-6f7a-8b9c-0d1e-2f3a4b5c6d7e |

### Post Comment Table

| id | post_id | commentator_id | message   | created_time |
|----|---------|----------------|-----------|--------------|
| 1  | 1       | 2              | Nice!     | 2023-10-01   |
| 2  | 1       | 3              | Congrats! | 2023-10-01   |
| 3  | 2       | 1              | Great!    | 2023-10-02   |

### Post Reply Table

| id | post_comment_id | replier_id | message        | created_time |
|----|-----------------|------------|----------------|--------------|
| 1  | 1               | 3          | Thanks!        | 2023-10-01   |
| 2  | 2               | 2          | Appreciate it! | 2023-10-01   |

### Tag Table

| id | name       |
|----|------------|
| 1  | #zoho      |
| 2  | #cricket   |
| 3  | #microsoft |

### Post Tag Table

| id | post_id | tag_id |
|----|---------|--------|
| 1  | 1       | 1      |
| 2  | 1       | 2      |

### Post Likes Table

| id | post_id | liker_id |
|----|---------|----------|
| 1  | 1       | 2        |
| 2  | 1       | 3        |
| 3  | 2       | 1        |

