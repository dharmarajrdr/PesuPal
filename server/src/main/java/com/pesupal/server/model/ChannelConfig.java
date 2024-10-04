package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChannelConfig {

    @Id
    private Long channelId;

    private Integer roleId;

    private Boolean addUser;

    private Boolean removeUser;

    private Boolean editProfilePicture;

    private Boolean sendFiles;

    private Boolean sendMessages;

    private Boolean clearAllMessages;

    private Boolean editChannelInfo;

    private Boolean deleteChannel;

    private Boolean deleteMessage;

    private Boolean editMessage;

    private Boolean pinMessage;

    private Boolean promoteOthers;

    private Boolean viewParticipants;
}
