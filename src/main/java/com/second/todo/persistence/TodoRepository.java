package com.second.todo.persistence;


import com.second.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userId);
    
    // ?1의 의미는 메서드의 매개벼수 순서 위치를 의미
    @Query("SELECT t FROM TodoEntity t WHERE t.userId = ?1")
    TodoEntity findByUserIdQuery(String userId);
}
