package net.catstack.retrotv.repository;

import net.catstack.retrotv.entity.source.SourceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<SourceModel, Long> {
    SourceModel getByName(String name);
}
