package com.rainmaker.rainmaker.entity;

import com.rainmaker.rainmaker.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "sender_id")
    private Member sender;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @NotNull
    private String message;
}
