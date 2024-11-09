package by.bsuir.taskjpa.service;

import by.bsuir.taskjpa.model.dto.request.TweetRequestTo;
import by.bsuir.taskjpa.model.dto.response.TweetResponseTo;

import java.util.List;

public interface TweetService {
    TweetResponseTo save(TweetRequestTo entity);
    List<TweetResponseTo> findAll();
    TweetResponseTo findById(Long id);
    TweetResponseTo update(TweetRequestTo updateRequest);
    void delete(Long id);
}
