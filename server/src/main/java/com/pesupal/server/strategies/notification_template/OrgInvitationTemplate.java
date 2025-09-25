package com.pesupal.server.strategies.notification_template;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.EmailTemplate;
import com.pesupal.server.model.user.OrgInvitation;

public class OrgInvitationTemplate extends EmailTemplate {

    private static final String LOGO = "https://drive.google.com/file/d/1xmyVUZpdDepGtrou7bxj7yDQ1fwPFyjZ/view";

    public OrgInvitationTemplate(OrgInvitation orgInvitation) {

        super("You're invited to join an organization", buildBody(orgInvitation.getInviter().getDisplayName(), StaticConfig.SERVER_DOMAIN + "/api/v1/org-invitations/accept/" + orgInvitation.getId()));
    }

    private static String buildBody(String inviterName, String acceptUrl) {

        String firstChar = inviterName != null && !inviterName.isEmpty()
                ? inviterName.substring(0, 1).toUpperCase()
                : "?";

        return "<div style='max-width:500px;margin:0 auto;background:#fff;border-radius:8px;overflow:hidden;box-shadow:0 2px 6px rgba(0,0,0,0.1);font-family:system-ui,sans-serif;'>"
                + "<div style='display:flex;justify-content:space-between;align-items:center;padding:16px 24px;border-bottom:1px solid #eee;'>"
                + "<img src='" + LOGO + "' alt='Logo' style='width:40px;height:40px;border-radius:50%;'/>"
                + "</div>"
                + "<div style='text-align:center;padding:32px 24px;'>"
                + "<p style='width:80px;height:80px;line-height:80px;font-size:30px;background-color:#99670b;color:#fff;font-weight:bold;border-radius:50%;display:flex;align-items:center;justify-content:center;margin:0 auto 16px auto;'>" + firstChar + "</p>"
                + "<h2 style='font-size:20px;margin:0;'>" + inviterName + "</h2>"
                + "<span style='font-weight:normal;color:#444;font-size:15px;'>invited you</span>"
                + "<p style='color:#555;margin:16px 0;font-size:14px;line-height:1.5;'>" + inviterName + " has invited you to join their organization. You can accept this invitation by clicking the button below.</p>"
                + "<a href='" + acceptUrl + "' style='display:inline-block;background:#99670b;color:#fff;padding:12px 24px;border-radius:6px;font-weight:bold;text-decoration:none;'>Accept Invitation</a>"
                + "</div>"
                + "</div>";
    }
}
