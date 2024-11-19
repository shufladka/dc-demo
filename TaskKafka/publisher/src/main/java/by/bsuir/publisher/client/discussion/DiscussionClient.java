package by.bsuir.publisher.client.discussion;

import by.bsuir.publisher.client.discussion.model.request.NoteRequestTo;
import by.bsuir.publisher.model.dto.response.NoteResponseTo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DiscussionClient {
    NoteResponseTo save(@RequestBody @Valid NoteRequestTo entity);
    List<NoteResponseTo> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                 @RequestParam(defaultValue = "5") Integer pageSize);
    NoteResponseTo findById(@PathVariable Long id);
    NoteResponseTo update(@RequestBody @Valid NoteRequestTo entity);
    void delete(@PathVariable @Valid Long id);
}
