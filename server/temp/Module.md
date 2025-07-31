### Field type:

| id | name        |
|----|-------------|
| 1  | string      |
| 2  | integer     |
| 3  | boolean     |
| 4  | datetime    |
| 5  | double      |
| 6  | user        |
| 7  | percent     |
| 8  | file        |
| 9  | text        |
| 10 | select      |
| 11 | multiselect |
| 12 | relation    |
| 13 | link        |

### Filter Operators table:

| id | field_id | operator     |
|----|----------|--------------|
| 1  | 1        | Equals       |
| 2  | 1        | Not Equals   |
| 3  | 1        | Contains     |
| 4  | 1        | Starts With  |
| 5  | 1        | Ends With    |
| 6  | 2        | Equals       |
| 7  | 2        | Not Equals   |
| 8  | 2        | Greater Than |
| 9  | 2        | Less Than    |
| 10 | 3        | Is True      |
| 11 | 3        | Is False     |
| 12 | 4        | Before       |
| 13 | 4        | After        |
| 14 | 4        | On           |
| 15 | 4        | Between      |

### Predefined Fields table:

| id | name        | attribute   | field_type_id | is_editable |
|----|-------------|-------------|---------------|-------------|
| 1  | Created At  | created_at  | 4             | false       |
| 2  | Updated At  | updated_at  | 4             | false       |
| 3  | Created By  | created_by  | 6             | false       |
| 4  | Updated By  | updated_by  | 6             | false       |
| 5  | Subject     | subject     | 1             | true        |
| 6  | Description | description | 9             | true        |
| 7  | Owner       | owner       | 6             | true        |

### Module table:

| id | name    | org_id | created_by |
|----|---------|--------|------------|
| 1  | Task    | 4      | 2          |
| 2  | Project | 7      | 3          |

### Module Members table:

| id | module_id | user_id | role       | is_active |
|----|-----------|---------|------------|-----------|
| 1  | 1         | 2       | owner      | true      |
| 2  | 1         | 3       | maintainer | true      |
| 3  | 2         | 4       | member     | true      |
| 4  | 2         | 5       | member     | true      |
| 5  | 1         | 6       | member     | false     |

### Module permission table:

| id | module_id | role       | create_record | read_record | update_record | delete_record | manage_members |
|----|-----------|------------|---------------|-------------|---------------|---------------|----------------|
| 1  | 1         | owner      | true          | true        | true          | true          | true           |
| 2  | 1         | maintainer | true          | true        | true          | false         | true           |
| 3  | 1         | member     | false         | true        | false         | false         | false          |

### Module Field table:

| id | module_id | name       | attribute  | field_type_id | is_required | is_searchable | is_filterable | is_sortable | is_editable | show_in_list | show_in_detail |
|----|-----------|------------|------------|---------------|-------------|---------------|---------------|-------------|-------------|--------------|----------------|
| 1  | 1         | Subject    | subject    | 1             | true        | true          | true          | true        | true        | true         | true           |
| 2  | 1         | Due Date   | due_date   | 4             | false       | false         | true          | true        | true        | false        | true           |
| 3  | 1         | Priority   | priority   | 10            | false       | true          | true          | true        | true        | true         | true           |
| 4  | 1         | Status     | status     | 10            | false       | true          | true          | true        | true        | true         | true           |
| 5  | 1         | Created At | created_at | 4             | false       | false         | false         | false       | false       | true         | true           |
| 6  | 1         | Updated At | updated_at | 4             | false       | false         | false         | false       | false       | false        | false          |
| 7  | 1         | Created By | created_by | 6             | false       | false         | false         | false       | false       | true         | true           |
| 8  | 1         | Updated By | updated_by | 6             | false       | false         | false         | false       | false       | false        | false          |
| 9  | 1         | Owner      | owner      | 6             | false       | false         | false         | false       | true        | true         | true           |

### Record Field Values table:

| id | record_id  | module_field_id | value                           |
|----|------------|-----------------|---------------------------------|
| 1  | b27a7c56cb | 1               | Fix the bug in the login module |
| 2  | b27a7c56cb | 2               | 2023-10-01 12:00:00             |
| 5  | b27a7c56cb | 5               | 2023-10-01 10:00:00             |
| 6  | b27a7c56cb | 6               | 2023-10-01 11:00:00             |

### Record Timeline table:

| id | record_field_id | action_performed_by | previous_value | new_value   | created_at          |
|----|-----------------|---------------------|----------------|-------------|---------------------|
| 1  | 3               | 2                   | Low            | High        | 2023-10-01 10:40:00 |
| 2  | 4               | 3                   | To do          | In Progress | 2023-10-01 11:10:00 |
| 3  | 9               | 2                   | 2              | 4           | 2023-10-01 12:20:00 |

### Record User Relation table:

| id | record_id  | module_field_id | user_id |
|----|------------|-----------------|---------|
| 1  | b27a7c56cb | 7               | 2       |
| 2  | b27a7c56cb | 8               | 3       |
| 3  | b27a7c56cb | 9               | 4       |

### Select Options table:

| id | module_field_id | value       | score | is_default |
|----|-----------------|-------------|-------|------------|
| 1  | 3               | High        | 0     | false      |
| 2  | 3               | Medium      | 50    | true       |
| 3  | 3               | Low         | 100   | false      |
| 4  | 4               | Backlog     | 0     | true       |
| 5  | 4               | To do       | 10    | false      |
| 6  | 4               | In Progress | 50    | false      |
| 7  | 4               | Done        | 100   | false      |

### Record Select Relation table:

| id | record_id  | module_field_id | select_option_id |
|----|------------|-----------------|------------------|
| 1  | b27a7c56cb | 3               | 1                |
| 2  | b27a7c56cb | 4               | 6                |
