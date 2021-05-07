package com.adpro.remind.repository;

import com.adpro.remind.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {
    TodoItem findById(int id);
}
