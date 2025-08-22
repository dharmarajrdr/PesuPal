package com.pesupal.server.controller.module;

import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/module/permissions")
public class ModulePermissionController {

    private final ModulePermissionService modulePermissionService;
}
