package aiss.gitminer.repository;

import aiss.gitminer.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRespository extends JpaRepository<Label, Long> {
}
