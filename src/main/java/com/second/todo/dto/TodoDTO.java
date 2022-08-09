package com.second.todo.dto;

import com.second.todo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data //@Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode를 한번에 설정해주는 어노테이션 @Datae
public class TodoDTO {
    /**
     * 아이템 생성 , 수정 , 삭제
     * */
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }
    public static TodoEntity toEntity(final TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
