### Group Messages Table

| id | group_id | sender_id | content       | created_at          | contains_media | is_deleted | 
|----|----------|-----------|---------------|---------------------|----------------|------------|
| 1  | 201      | 101       | Welcome!      | 2023-10-01 11:00:00 | false          | false      |
| 2  | 201      | 102       | Hi everyone!  | 2023-10-01 11:01:00 | true           | false      |
| 3  | 202      | 103       | Good morning! | 2023-10-01 11:02:00 | false          | true       |

### Group Chat Reactions Table

| id | message_id | user_id | reaction_id | created_at          |
|----|------------|---------|-------------|---------------------|
| 1  | 1          | 101     | like        | 2023-10-01 10:00:05 |
| 2  | 1          | 102     | love        | 2023-10-01 10:01:05 |
| 3  | 2          | 103     | laugh       | 2023-10-01 10:02:05 |

### Group Metadata Table

| id | group_id | name      | description          | created_at          | created_by | status   | visibility | show_old_messages_to_new_members |
|----|----------|-----------|----------------------|---------------------|------------|----------|------------|----------------------------------|
| 1  | 201      | Dev Team  | Group for developers | 2023-10-01 09:00:00 | 101        | active   | public     | false                            |
| 2  | 202      | Design    | Group for designers  | 2023-10-01 09:30:00 | 102        | active   | private    | true                             |
| 3  | 203      | Marketing | Group for marketers  | 2023-10-01 10:00:00 | 103        | inactive | public     | true                             |

### Group Configurations Table

| id | group_id | role_id | add_members | remove_members | change_name | change_description | delete_group | change_profile_picture | post_message | delete_message | clear_chat |
|----|----------|---------|-------------|----------------|-------------|--------------------|--------------|------------------------|--------------|----------------|------------|
| 1  | 201      | admin   | true        | false          | false       | true               | true         | false                  | true         | false          | false      |
| 2  | 201      | member  | false       | false          | false       | false              | false        | false                  | true         | false          | false      |
| 3  | 202      | admin   | true        | true           | true        | true               | false        | true                   | true         | false          | true       |

### Group Members Table

| id | group_id | user_id | role   | joined_at           |
|----|----------|---------|--------|---------------------|
| 1  | 201      | 101     | admin  | 2023-10-01 09:00:00 |
| 2  | 201      | 102     | member | 2023-10-01 09:05:00 |
| 3  | 202      | 103     | member | 2023-10-01 09:10:00 |
