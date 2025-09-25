package com.example.obs.web;

import com.example.obs.model.Todo;
import com.example.obs.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService service;
    public TodoController(TodoService service) { this.service = service; }

    @GetMapping
    public List<Todo> all() { return service.all(); }

    @GetMapping("/{id}")
    public Todo one(@PathVariable Long id) { return service.get(id); }

    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo t) {
        Todo saved = service.create(t);
        return ResponseEntity.created(URI.create("/api/todos/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @Valid @RequestBody Todo t) { return service.update(id, t); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
