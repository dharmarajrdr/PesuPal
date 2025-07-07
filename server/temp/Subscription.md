### Subscription Plan Table

| id | code       | price     | currency | interval | description     | created_at | is_active | includes_trial |
|----|------------|-----------|----------|----------|-----------------|------------|-----------|----------------|
| 1  | basic      | 2999.00   | INR      | monthly  | Basic Plan      | 2023-01-01 | true      | true           |
| 2  | basic      | 29999.00  | INR      | yearly   | Basic Plan      | 2023-01-01 | true      | true           |
| 3  | pro        | 4999.00   | INR      | monthly  | Pro Plan        | 2023-01-01 | false     | false          |
| 4  | pro        | 49999.00  | INR      | yearly   | Pro Plan        | 2023-01-01 | true      | false          |
| 5  | enterprise | 6999.00   | INR      | monthly  | Enterprise Plan | 2023-01-01 | false     | false          |
| 6  | enterprise | 69999.00  | INR      | yearly   | Enterprise Plan | 2023-01-01 | true      | false          |
| 7  | endless    | 999999.00 | INR      | yearly   | Endless Plan    | 2023-01-01 | true      | false          |

### Feature Table

| plan_id | max_members | max_groups | max_admins | max_media_size |
|---------|-------------|------------|------------|----------------|
| 1       | 10          | 5          | 1          | 5000           |
| 2       | 20          | 10         | 3          | 10000          |
| 3       | 150         | 75         | 15         | 25000          |
| 4       | 200         | 100        | 25         | 30000          |
| 5       | Infinite    | Infinite   | Infinite   | 50000          |
| 6       | Infinite    | Infinite   | Infinite   | 100000         |
| 7       | Infinite    | Infinite   | Infinite   | Infinite       |
