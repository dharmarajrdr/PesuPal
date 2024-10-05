<!-- Direct message Module -->

##### Get recent chats of user
   
```[GET] /api/v1/:org_id/chat/direct_messages/recent_conversations```

##### Get pinned chats of user
   
```[GET] /api/v1/:org_id/chat/pinned_users```

##### Get chat messages of a user
   
```[GET] /api/v1/:org_id/chat/direct_messages/:user_id/messages```

##### Post a new chat message
   
```[POST] /api/v1/:org_id/chat/direct_messages/:user_id/message```

##### Update a chat message
   
```[PUT] /api/v1/:org_id/chat/direct_messages/:user_id/message/:message_id```

##### Delete a chat message
   
```[DELETE] /api/v1/:org_id/chat/direct_messages/:user_id/message/:message_id```

##### Reactions on a chat message
   
```[POST] /api/v1/:org_id/chat/direct_messages/:user_id/message/:message_id/reaction```
