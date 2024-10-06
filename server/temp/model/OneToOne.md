### 1. **Unidirectional `@OneToOne` Relationship**

In a **unidirectional** relationship, only one side of the relationship is aware of the other. In this case, **Person** knows about **Passport**, but **Passport** does not know about **Person**.

#### Example: `Person` owns the relationship (Person knows about Passport).

#### Person Model (Unidirectional)
```java
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "passport_id")  // Defines the foreign key column "passport_id" in the "Person" table
    private Passport passport;

    // Getters and Setters
}
```

- **`@OneToOne`**: Indicates a one-to-one relationship.
- **`@JoinColumn(name = "passport_id")`**: This defines the foreign key in the `Person` table, linking it to the primary key of the `Passport` table.

#### Passport Model (Unidirectional)
```java
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passportNumber;

    // No reference back to Person, making it unidirectional

    // Getters and Setters
}
```

- The `Passport` model does not reference `Person`. It has no knowledge of the `Person` entity, making this a **unidirectional** relationship.

#### Schema:
- **Person Table**: `id`, `name`, `passport_id (FK)`
- **Passport Table**: `id`, `passport_number`

In this case, the `Person` table will have a foreign key `passport_id` pointing to the `Passport` table, but the `Passport` table will not have any reference to the `Person` table.

---

### 2. **Bidirectional `@OneToOne` Relationship**

In your example, both the `Student` and `Contact` tables have foreign keys referencing each other. This is a bidirectional **`@OneToOne`** relationship where both sides have foreign keys.

Let’s define this in a way that reflects the **schema** you provided:

- `Student` has `student_id`, `name`, and `contact_id` (foreign key to `Contact`).
- `Contact` has `contact_id`, `phone`, and `student_id` (foreign key to `Student`).

In this scenario, we need to carefully define which side is the **owning side** and which side is the **inverse (non-owning) side**.

#### **Choosing the Owning Side**

In JPA, even though both tables have foreign keys, only **one side** of the relationship should be considered the **owning side**. The other side is the **inverse side**. For simplicity, let’s assume the `Student` is the **owning side**.

#### **Entity Classes for Bidirectional `@OneToOne` Relationship**

##### **Student Entity** (Owning Side)

The `Student` class will own the relationship, meaning it will have a `@JoinColumn` specifying the foreign key to the `Contact` entity (`contact_id`).

```java
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")  // Owning side: This column refers to the "contact_id" in Contact table
    private Contact contact;

    // Getters and Setters
}
```

- **`@JoinColumn(name = "contact_id")`**: This specifies that the foreign key `contact_id` in the `Student` table references the `Contact` table.
- **Owning Side**: `Student` is the owning side since it has the foreign key `contact_id`.

##### **Contact Entity** (Inverse Side)

The `Contact` class is the inverse side, meaning it doesn’t hold the foreign key for the relationship but it refers to the owning entity (`Student`) through the `mappedBy` attribute.

```java
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    private String phone;

    @OneToOne(mappedBy = "contact")  // Inverse side: "contact" is the field in Student that owns the relationship
    private Student student;

    // Getters and Setters
}
```

- **`@OneToOne(mappedBy = "contact")`**: This indicates that `Contact` is the inverse side of the relationship and `Student` owns it. `mappedBy` refers to the `contact` field in the `Student` class.


##### **Student Table**:
| student_id | name   | contact_id (FK) |
|------------|--------|-----------------|
| 1          | John   | 101             |

##### **Contact Table**:
| contact_id | phone      | student_id (FK) |
|------------|------------|-----------------|
| 101        | 123-456789 | 1               |

- **`Student`**: Holds a foreign key `contact_id` pointing to the `Contact` table.
- **`Contact`**: Holds a foreign key `student_id` pointing back to the `Student` table.

#### **Uniqueness of Foreign Keys**
In a `@OneToOne` relationship, both `contact_id` in `Student` and `student_id` in `Contact` must be unique, ensuring the one-to-one relationship.

### Summary:

- **Owning Side (Student)**: Has the foreign key `contact_id` and uses `@JoinColumn` to establish the relationship.
- **Inverse Side (Contact)**: Uses `mappedBy` to indicate that `Student` owns the relationship, and it holds a reference back to `Student`.

---

### Key Differences Between Unidirectional and Bidirectional:
1. **Unidirectional**: Only the **Person** knows about the **Passport**. The `Person` table contains the foreign key, and `Passport` has no reference to `Person`.
2. **Bidirectional**: Both **Student** and **Contact** know about each other. The `Student` table still holds the foreign key, but the `Contact` entity references `Student` using `mappedBy`.

---
