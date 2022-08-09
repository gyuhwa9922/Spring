package com.second.todo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "todouser", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid2") //ID자동생성
    @GenericGenerator(name ="uuid2",strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String username; //아이디로 사용할 유저네임 : 이메일 , 문자열 ...

    private String password;
    private String role; //권한
    private String authProvider; //GOOGLE, KAKAO 등등 ..

}
