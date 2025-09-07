package com.pesupal.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedRetriever {

    SIMPLE_FEED_RETRIEVER_ALGORITHM("Simple Feed Retriever Algorithm"),
    ADVANCED_FEED_RETRIEVER_ALGORITHM("Advanced Feed Retriever Algorithm"),
    MACHINE_LEARNING_FEED_RETRIEVER_ALGORITHM("Machine Learning Feed Retriever Algorithm"),
    PERSONALIZED_FEED_RETRIEVER_ALGORITHM("Personalized Feed Retriever Algorithm");

    private final String description;
}
