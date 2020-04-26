package com.switchfully.eurder.domain.item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@Transactional
public interface ItemRepository extends CrudRepository<Item, UUID> {
}
