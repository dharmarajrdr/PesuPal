package com.pesupal.server.model.module;

public enum ModuleView {

    LIST_VIEW,
    DETAIL_VIEW;

    public boolean canShowField(ModuleField moduleField) {
        switch (this.name()) {
            case "LIST_VIEW": {
                return moduleField.isShowInList();
            }
            case "DETAIL_VIEW": {
                return moduleField.isShowInDetail();
            }
            default: {
                throw new IllegalArgumentException("Unknown ModuleView: " + this.name());
            }
        }
    }
}
