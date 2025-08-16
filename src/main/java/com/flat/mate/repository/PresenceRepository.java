package com.flat.mate.repository;

import com.flat.mate.model.Presence;
import com.flat.mate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PresenceRepository extends JpaRepository<Presence, UUID> {
    List<Presence> findByUser(User user);
    List<Presence> findByInFlat(boolean inFlat); // For checking who is currently in flat
}
