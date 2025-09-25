package com.example.obs.service;

import com.example.obs.model.Todo;
import com.example.obs.repo.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TodoService {
    private final TodoRepository repo;
    public TodoService(TodoRepository repo) { this.repo = repo; }

    public List<Todo> all() { return repo.findAll(); }
    public Todo get(Long id) { return repo.findById(id).orElseThrow(); }
    public Todo create(Todo t) { return repo.save(t); }
    public Todo update(Long id, Todo t) {
        Todo existing = get(id);
        existing.setTitle(t.getTitle());
        existing.setDone(t.isDone());
        return repo.save(existing);
    }
    public void delete(Long id) { repo.deleteById(id); }
}
