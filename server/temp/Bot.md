### Bot table:

| id | public_id | name         | description                                 | display_picture              |
|----|-----------|--------------|---------------------------------------------|------------------------------|
| 1  | bot_001   | Task         | A bot for chatting and assistance           | https://example.com/bot1.jpg |
| 2  | bot_002   | Workdrive    | A bot for notifications and file management | https://example.com/bot2.jpg |
| 3  | bot_003   | Subscription | A bot for subscription management           | https://example.com/bot3.jpg |
| 4  | bot_004   | Support      | A bot for customer support                  | https://example.com/bot4.jpg |

### Bot Messages table:

| id | bot_id | receiver_id | title                     | message                               | created_at          | contains_media |
|----|--------|-------------|---------------------------|---------------------------------------|---------------------|----------------|
| 1  | 1      | 101         | New task assigned to you! | Dharma has created a new task.        | 2023-10-01 10:00:00 | true           |
| 2  | 2      | 102         | File uploaded             | A new file has been uploaded.         | 2023-10-01 10:01:00 | true           |
| 3  | 3      | 103         | Subscription renewal      | Your subscription is due for renewal. | 2023-10-01 10:02:00 | true           |
| 4  | 4      | 104         | Support ticket created    | Your support ticket has been created. | 2023-10-01 10:03:00 | false          |

### Bot Messages Media table:

| id | message_id | name              | media_id | extension | size    |
|----|------------|-------------------|----------|-----------|---------|
| 1  | 1          | Screenshot.jpg    | 201      | jpg       | 204800  |
| 2  | 2          | Screen-record.mp4 | 202      | mp4       | 1048576 |
| 3  | 3          | invoice.pdf       | 203      | pdf       | 512000  |