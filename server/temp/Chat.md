### Chat Messages Table

| id | sender_id | receiver_id | content      | created_at          | contains_media | is_deleted | read_status |
|----|-----------|-------------|--------------|---------------------|----------------|------------|-------------|
| 1  | 101       | 102         | Hello!       | 2023-10-01 10:00:00 | false          | false      | read        |
| 2  | 102       | 101         | Hi there!    | 2023-10-01 10:01:00 | true           | false      | unread      |
| 3  | 101       | 103         | How are you? | 2023-10-01 10:02:00 | false          | true       | read        |

### Chat Reactions Table

| id | message_id | user_id | reaction_id | created_at          |
|----|------------|---------|-------------|---------------------|
| 1  | 1          | 101     | like        | 2023-10-01 10:00:05 |
| 2  | 3          | 102     | love        | 2023-10-01 10:01:05 |
| 3  | 2          | 103     | laugh       | 2023-10-01 10:02:05 |

### Media Files Table

| id | message_id | file_type | file_size | file_url                       | uploaded_at         |
|----|------------|-----------|-----------|--------------------------------|---------------------|
| 1  | 2          | image     | 204800    | https://example.com/image1.jpg | 2023-10-01 10:01:00 |
| 2  | 1          | video     | 1048576   | https://example.com/video1.mp4 | 2023-10-01 10:00:00 |
| 3  | 3          | audio     | 512000    | https://example.com/audio1.mp3 | 2023-10-01 10:02:00 |

