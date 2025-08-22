### Group Chat table

| id | org_id | group_name     | group_description   | created_At | created_by | show_old_messages |
|----|--------|----------------|---------------------|------------|------------|-------------------|
| 1  | 1      | Dev Team       | Team for developers | 2023-10-01 | 101        | true              |
| 2  | 1      | Design Team    | Team for designers  | 2023-10-02 | 102        | false             |
| 3  | 1      | Marketing Team | Team for marketers  | 2023-10-03 | 103        | true              |

### Group Chat Configuration table

| id | group_id | role   | add_member | remove_member | send_message | pin_message | delete_message | react_message | view_members | delete_group | clear_messages | leave_group | role_update |
|----|----------|--------|------------|---------------|--------------|-------------|----------------|---------------|--------------|--------------|----------------|-------------|-------------|
| 1  | 1        | admin  | true       | true          | true         | true        | true           | true          | true         | true         | true           | true        | true        |
| 2  | 1        | member | false      | false         | true         | false       | false          | true          | true         | false        | false          | true        | false       |

### Group Chat Members table

| id | group_id | user_id | role   | joined_at  | last_read_message_id | status  |
|----|----------|---------|--------|------------|----------------------|---------|
| 1  | 1        | 101     | admin  | 2023-10-01 | 1                    | active  |
| 2  | 1        | 102     | member | 2023-10-01 | 2                    | active  |
| 3  | 1        | 103     | member | 2023-10-01 |                      | removed |

### Group Chat Messages table

| id | group_id | sender_id | content  | created_at | contains_media | is_deleted | read_status |
|----|----------|-----------|----------|------------|----------------|------------|-------------|
| 1  | 1        | 101       | Hello!   | 2023-10-01 | false          | false      | read        |
| 2  | 1        | 102       | Hi team! | 2023-10-01 | true           | false      | unread      |
| 3  | 1        | 103       | Howdy!   | 2023-10-01 | false          | true       | read        |

### Group Chat Reactions Table

| id | group_message_id | user_id | reaction_id | created_at          |
|----|------------------|---------|-------------|---------------------|
| 1  | 1                | 101     | like        | 2023-10-01 10:00:05 |
| 2  | 2                | 102     | love        | 2023-10-01 10:01:05 |
| 3  | 2                | 103     | laugh       | 2023-10-01 10:02:05 |

### Group Media Files Table

| id | group_message_id | file_type | file_size | file_url                       | uploaded_at         |
|----|------------------|-----------|-----------|--------------------------------|---------------------|
| 1  | 2                | image     | 204800    | https://example.com/image1.jpg | 2023-10-01 10:01:00 |
| 2  | 1                | video     | 1048576   | https://example.com/video1.mp4 | 2023-10-01 10:00:00 |
| 3  | 3                | audio     | 512000    | https://example.com/audio1.mp3 | 2023-10-01 10:02:00 |

### Group chats Pinned Table

| id | org_id | user_id | group_id | order_index |
|----|--------|---------|----------|-------------|
| 1  | 1      | 101     | 102      | 1           |
| 2  | 1      | 101     | 103      | 2           |

### Group Messages Pinned Table

| id | org_id | group_message_id | pinned_by_user_id |
|----|--------|------------------|-------------------|
| 1  | 1      | 1                | 101               |
| 2  | 1      | 2                | 102               |

