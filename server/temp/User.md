### Users Table

| id | email                      | password_hash | created_at          |
|----|----------------------------|---------------|---------------------|
| 1  | dharmaraj.171215@gmail.com | $2y$10$eW...  | 2023-01-01 10:00:00 | 
| 2  | johndoe@gmail.com          | $2y$10$eW...  | 2023-01-02 10:00:00 | 
| 3  | janesmith@gmail.com        | $2y$10$eW...  | 2023-01-03 10:00:00 | 

### Org Members Table

| id | org_id | employee_id | user_id | managed_by | role  | joined_at  | status | user_name    | display_name | display_picture              | is_removed |
|----|--------|-------------|---------|------------|-------|------------|--------|--------------|--------------|------------------------------|------------|
| 1  | 1      | 1           | 101     | 103        | admin | 2023-01-05 | active | dharmaraj.rd | Dharmaraj R  | https://example.com/pic1.jpg | false      |
| 2  | 1      | 2           | 102     | 104        | user  | 2023-01-06 | active | mohan.rmk    | Mohan        | https://example.com/pic2.jpg | false      |
| 3  | 2      | 1           | 101     | 102        | user  | 2023-01-07 | active | dharmaraj.rd | Dharma       | https://example.com/pic1.jpg | false      |
