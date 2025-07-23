### On Direct Message Received

- **Topic** : /topic/directMessage.{userId}
- **Payload** :

```json
{
  "id": 64,
  "createdAt": "2025-07-21T22:43:22.708658",
  "sender": {
    "id": 3,
    "displayName": "Dharmaraj R",
    "displayPicture": "https://images.contentstack.io/v3/assets/bltcedd8dbd5891265b/blt134818d279038650/6668df6434f6fb5cd48aac34/beautiful-flowers-rose.jpeg",
    "archived": false
  },
  "message": "Please refer the screenshot shared.",
  "deleted": false,
  "readReceipt": "READ",
  "reactions": {}
}
```

---

### On Group Message Received

- **Topic** : /topic/groupMessage.{userId}
- **Payload** :

```json
{
  "id": 64,
  "createdAt": "2025-07-21T22:43:22.708658",
  "sender": {
    "id": 3,
    "displayName": "Dharmaraj R",
    "displayPicture": "https://images.contentstack.io/v3/assets/bltcedd8dbd5891265b/blt134818d279038650/6668df6434f6fb5cd48aac34/beautiful-flowers-rose.jpeg",
    "archived": false
  },
  "message": "Please refer the screenshot shared.",
  "deleted": false,
  "readReceipt": "READ",
  "reactions": {}
}
```

---

### On Direct Message Delete

- **Topic** : /topic/messageDelete.{userId}
- **Payload** :
```json
{
  "chatId": "1_2_1",
  "messageId": 64
}
```

---

### On Group Message Delete

- **Topic** : /topic/groupMessageDelete.{userId}
- **Payload** :
```json
{
  "chatId": "5",
  "messageId": 64
}
```
