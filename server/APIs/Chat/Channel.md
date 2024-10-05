##### Create a new channel
   
```[POST] /api/v1/:org_id/chat/create_channel```

##### Delete a channel
   
```[DELETE] /api/v1/:org_id/chat/channel/:channel_id```

##### Add user to a channel
   
```[POST] /api/v1/:org_id/chat/channel/:channel_id/add_user```

##### Remove user from a channel
   
```[DELETE] /api/v1/:org_id/chat/channel/:channel_id/remove_user```

##### Get channels of user
   
```[GET] /api/v1/:org_id/chat/channels```

##### Get recent channels of user
   
```[GET] /api/v1/:org_id/chat/channels/recent_conversations```

##### Get messages of a channel
   
```[GET] /api/v1/:org_id/chat/channel/:channel_id/messages```

##### Post message to a channel
   
```[POST] /api/v1/:org_id/chat/channel/:channel_id/message```

##### Update a channel message
   
```[PUT] /api/v1/:org_id/chat/channel/:channel_id/message/:message_id```

##### Delete a channel message
   
```[DELETE] /api/v1/:org_id/chat/channel/:channel_id/message/:message_id```

##### React to a channel message
   
```[POST] /api/v1/:org_id/chat/channel/:channel_id/message/:message_id/reaction```

##### View reactions on a channel message
   
```[GET] /api/v1/:org_id/chat/channel/:channel_id/message/:message_id/reactions```

##### Remove reaction on a channel message
   
```[DELETE] /api/v1/:org_id/chat/channel/:channel_id/message/:message_id/reaction```

##### Update reactions on a channel message
   
```[PUT] /api/v1/:org_id/chat/channel/:channel_id/message/:message_id/reaction```

##### Get all users of a channel
   
```[GET] /api/v1/:org_id/chat/channel/:channel_id/users```
