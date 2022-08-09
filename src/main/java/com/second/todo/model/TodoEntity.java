package com.second.todo.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity             // 자바클래스를 엔티티(Entity)로 지정
@Table(name="todo") // 테이블 이름을 지정
public class TodoEntity {

    //오브젝트 아이디 @id 는 기본 키가 될 필드에 저장한다.
    @Id
    @GeneratedValue(generator = "uuid2") //id 자동생성 (Universally Unique IDentifier)
    @GenericGenerator(name = "uuid2",strategy = "uuid2")
    private String id;

    private String userId;
    private String title;
    private boolean done;
}
