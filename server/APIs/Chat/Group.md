##### Create a new group
   
```[POST] /api/v1/:org_id/chat/create_group```

##### Delete a group
   
```[DELETE] /api/v1/:org_id/chat/groups/:group_id```

##### Add user to a group
   
```[POST] /api/v1/:org_id/chat/groups/:group_id/add_user```

##### Remove user from a group
   
```[DELETE] /api/v1/:org_id/chat/groups/:group_id/remove_user```

##### Get list of groups
   
```[GET] /api/v1/:org_id/chat/groups```

##### Get list of recent groups
   
```[GET] /api/v1/:org_id/chat/groups/recent_conversations```

##### Get messages of a group
   
```[GET] /api/v1/:org_id/chat/groups/:group_id/messages```

##### Post message to a group
   
```[POST] /api/v1/:org_id/chat/groups/:group_id/message```

##### Update a group message
   
```[PUT] /api/v1/:org_id/chat/groups/:group_id/message/:message_id```

##### Delete a group message
   
```[DELETE] /api/v1/:org_id/chat/groups/:group_id/message/:message_id```

##### React to a group message
   
```[POST] /api/v1/:org_id/chat/groups/:group_id/message/:message_id/reaction```

##### View reactions on a group message
   
```[GET] /api/v1/:org_id/chat/groups/:group_id/message/:message_id/reactions```

##### Remove reaction on a group message
   
```[DELETE] /api/v1/:org_id/chat/groups/:group_id/message/:message_id/reaction```

##### Update reactions on a group message
   
```[PUT] /api/v1/:org_id/chat/groups/:group_id/message/:message_id/reaction```
