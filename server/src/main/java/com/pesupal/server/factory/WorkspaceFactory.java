package com.pesupal.server.factory;

import com.pesupal.server.enums.Workspace;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class WorkspaceFactory {

    private final Map<String, WorkdriveSpace> strategyMap;

    /**
     * Returns the appropriate WorkdriveSpace implementation based on the workspace type.
     *
     * @param workspace
     * @return
     */
    public WorkdriveSpace getFactory(Workspace workspace) {

        return strategyMap.get(workspace.name());
    }
}
