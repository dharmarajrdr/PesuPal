package com.pesupal.server.repository.post;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {

    List<PostMedia> findAllByPost_Org(Org postOrg);
}
