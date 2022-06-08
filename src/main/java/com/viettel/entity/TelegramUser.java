package com.viettel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TELEGRAM_USER")
public class TelegramUser implements Serializable {
    @Id
    @Column(name = "CHAT_ID", nullable = false)
    private String chatId;

    @Column(name = "STEP")
    private String step;

    @Column(name = "MSG")
    private String msg;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    public TelegramUser() {
    }

    public TelegramUser(String chatId, String step, String msg, Date createdDate) {
        this.chatId = chatId;
        this.step = step;
        this.msg = msg;
        this.createdDate = createdDate;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TelegramUser (CHAT_ID = "+ this.chatId +", STEP = " + this.step + ")";
    }
}