package com.pesupal.server.dto.response;

import lombok.Data;

@Data
public class KanbanViewDto<K, V> {

    private K key;

    private V value;
}
