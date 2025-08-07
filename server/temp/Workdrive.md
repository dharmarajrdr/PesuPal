### Space

1. Org Space - Files and folders in this space can be accessed by all members of the organization.
2. Team Space - Files and folders in this space can be accessed by all members of the team.
3. Personal Space - Files and folders in this space can only be accessed by the owner.

### Folder Table

| id | name                     | parent_folder_id | creator_id | org_id | space_type | created_at |
|----|--------------------------|------------------|------------|--------|------------|------------|
| 1  | Users                    | NULL             | 1          | 1      | Personal   | 2023-01-01 |
| 2  | dharma-13910             | Users            | 1          | 1      | Personal   | 2023-01-01 |
| 3  | Desktop                  | dharma-13910     | 1          | 1      | Personal   | 2023-01-01 |
| 4  | Documents                | dharma-13910     | 1          | 1      | Personal   | 2023-01-01 |
| 5  | Downloads                | dharma-13910     | 1          | 1      | Personal   | 2023-01-01 |
| 6  | CRT - Sas                | NULL             | 2          | 1      | Team       | 2023-01-01 |
| 7  | Mandatory Read Documents | NuLL             | 3          | 1      | Org        | 2023-01-01 |
| 8  | Shredder                 | NULL             | 4          | 1      | Team       | 2023-01-01 |
| 9  | Recruitment - HR         | NULL             | 5          | 1      | Org        | 2023-01-01 |

### Shared Folder Table

| id | folder_id | security | readable | writable | deletable |
|----|-----------|----------|----------|----------|-----------|
| 1  | 6         | NONE     | true     | true     | false     |
| 2  | 7         | NONE     | true     | false    | false     |
| 3  | 8         | SECURED  | true     | false    | false     |
| 4  | 9         | SECURED  | false    | false    | false     |

### Team Space Table

| id | folder_id | department_id |
|----|-----------|---------------|
| 1  | 6         | 1             |
| 2  | 8         | 1             |

### Secured Folder Permissions Table

| id | folder_id | user_id | readable | writable | expiration_date |
|----|-----------|---------|----------|----------|-----------------|
| 1  | 8         | 1       | false    | true     | NULL            |
| 2  | 9         | 2       | true     | false    | 2024-01-01      |

### File Table

| id | name                    | folder_id | creator_id | security | unique_id |
|----|-------------------------|-----------|------------|----------|-----------|
| 1  | MyImage.png             | 4         | 1          | NONE     | abc123    |
| 2  | Scaler.jpg              | 3         | 1          | SECURED  | xyz456    |
| 3  | Services and Usages.pdf | 6         | 1          | NONE     | def789    |
| 4  | SKCET-2025.pdf          | 9         | 2          | SECURED  | ghi012    |

### Secured File Read Access Table

| id | file_id | user_id | expiration_date |
|----|---------|---------|-----------------|
| 1  | 2       | 1       | 2024-01-01      |
| 2  | 4       | 2       | 2024-01-01      |

### Starred File Table

| id | file_id | user_id | 
|----|---------|---------|
| 1  | 1       | 1       |
| 2  | 3       | 1       |
| 3  | 4       | 2       |
