package by.bsuir.taskjpa.repository.impl;

import by.bsuir.taskjpa.model.entity.Tweet;
import by.bsuir.taskjpa.repository.common.InMemoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("in-memory-repositories")
public class TweetRepository extends InMemoryRepository<Tweet> {
}
