package com.second.todo.contorller;


import com.second.todo.dto.ResponseDTO;
import com.second.todo.dto.TodoDTO;
import com.second.todo.model.TodoEntity;
import com.second.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;
    private final String TEMP_USER_ID = "temporary-user";

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            
            //(1) TodoEntity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            //(2) id를 null로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문.
            entity.setId(null);
            //3. 임시 유저 아이디를 설정해준다.
            entity.setUserId(temporaryUserId);

            //4. service를 이용해 Entity를 생성한다.
            List<TodoEntity> entities = todoService.create(entity);

            // (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO 를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(dtos).build();

            // (7) ResponseDTO 를 리턴
            return ResponseEntity.ok().body(response);


        }catch (Exception e){
            String err = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(err).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    //조회
    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        List<TodoEntity> entities = todoService.retrieve(TEMP_USER_ID);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> upadateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId= "temporary-user";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(temporaryUserId);
        List<TodoEntity> entities = todoService.update(entity);
        List<TodoDTO> todoDTOS = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().resList(todoDTOS).build();

        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try {
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(TEMP_USER_ID);
            List<TodoEntity> entities = todoService.delete(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().resList(dtos).build();

            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }


    }

}
