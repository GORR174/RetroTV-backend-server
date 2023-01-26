package net.catstack.retrotv.repository;

import net.catstack.retrotv.entity.heading.HeadingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadingRepository extends JpaRepository<HeadingModel, Long> {
    HeadingModel getByName(String name);
}
