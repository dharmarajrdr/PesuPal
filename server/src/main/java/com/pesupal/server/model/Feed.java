package com.pesupal.server.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feed {

    @Id
    @SequenceGenerator(name = "feed_id_seq", sequenceName = "feed_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feed_id_seq")
    private Long feedId;

    @OneToOne
    @JoinColumn(name = "org_id") // Join column for the OneToOne relationship
    private Org org;

    @ManyToOne
    @JoinColumn(name = "user_id") // Join column for the ManyToOne relationship
    private Users user_field;

    private String message;

    @OneToOne
    @JoinColumn(name = "feedConfigId") // Join column for the OneToOne relationship
    private FeedConfig feedConfigId_field;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // OneToMany relationship
    @JoinColumn(name = "feed_id") // Join column for the OneToMany relationship
    private List<FeedMedia> media_files; // List of FeedMedia associated with this Feed
}
