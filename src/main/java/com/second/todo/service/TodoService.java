package com.second.todo.service;


import com.second.todo.model.TodoEntity;
import com.second.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<TodoEntity> create(final TodoEntity entity){
        // 1. 저장 할 Entity가 유효한지 확인
        validate(entity);
        todoRepository.save(entity);

        log.info("Entity Id : {} is save", entity.getId());
        return todoRepository.findByUserId(entity.getUserId());
    }
    //유효성 검증
    private void validate(final TodoEntity entity){

        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }
    @Transactional
    public List<TodoEntity> update(final TodoEntity todoEntity) {
        validate(todoEntity);
        final Optional<TodoEntity> optionalTodoEntity = todoRepository.findById(todoEntity.getId());
        optionalTodoEntity.ifPresent(todo -> {
            //반환된 TodoEntity 가 존재하면 값을 새 entity 의 값으로 덮어쓴다.
            todo.setTitle(todoEntity.getTitle());
            todo.setDone(todoEntity.isDone());
        });
        return retrieve(todoEntity.getUserId());
    }
    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);

        try{
            todoRepository.delete(entity);
        }catch (Exception e){
            log.error("error", entity.getId(),e);
            throw new RuntimeException("err" + entity.getId());

        }
        return retrieve(entity.getUserId());
    }

}
