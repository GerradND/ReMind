package com.adpro.remind.repository;

import com.adpro.remind.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Integer>{
    TodoList findById(int id);
}
