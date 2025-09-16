package com.pesupal.server.dto.request.org;

import com.pesupal.server.model.org.Banner;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateBannerDto {

    private String message;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Banner toBanner() {

        Banner banner = new Banner();
        banner.setMessage(this.message);
        banner.setStartTime(this.startTime);
        banner.setEndTime(this.endTime);
        return banner;
    }
}
